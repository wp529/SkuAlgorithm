package com.wp.sku_algorithm.base

import android.util.Log
import com.wp.sku_algorithm.logTag

/**
 * Sku算法Base
 * @param specGroupList 规格组列表
 * @param skuList sku列表
 * @param noneSelectedSpecNeedChangeCanBeSelectedState 当没有选中规格时是否需要处理规格是否可选择状态 true需要 false不需要,若为false,则未选中任何规格时全部规格都可选
 * create by WangPing
 * on 2021/1/6
 */
abstract class BaseCalculator constructor(
    protected val specGroupList: List<BaseSpecGroup>,
    protected var skuList: List<ISku>,
    private val noneSelectedSpecNeedChangeCanBeSelectedState: Boolean
) {
    //规格的维度,也就是规格组的数目
    protected val specDimensionality: Int = specGroupList.size

    //规格数组
    protected val specArray = specGroupList.let {
        val result = ArrayList<BaseSpec>()
        specGroupList.forEach { specGroup ->
            specGroup.specList.forEach { singleSpec ->
                result.add(singleSpec)
            }
        }
        result
    }

    //完成选中某个Sku后的回调
    var onSelectSku: ((sku: ISku) -> Unit)? = null

    init {
        val illegalSkuList =
            skuList.filter { it.skuCombinationSpecIdArray().size != specDimensionality }
        if (illegalSkuList.isNotEmpty()) {
            //移除非法的Sku,效果等同此Sku的skuCanNotBeSelected()方法始终返回true
            skuList -= illegalSkuList
            val exceptionText =
                "规格维数为${specDimensionality}维,skuId为${illegalSkuList.joinToString(separator = ",") { it.skuId() }}的Sku维数不符,不是合法的Sku"
            Log.e(logTag, Log.getStackTraceString(IllegalArgumentException(exceptionText)))
        }
    }

    /**
     * 设置选中某个sku,会回调onSelectSku
     * 这里没有处理传入的sku在skuList里不存在的情况,由调用方自行处理,这种情况是想选中随便一个Sku还是啥操作都不做还是想干其他啥都可以自行实现
     * @param sku 选中的sku
     */
    fun setSkuSelect(sku: ISku) {
        if (skuList.find { it.skuId() == sku.skuId() } == null) {
            //传入的sku在skuList里都不存在,那么不处理,直接return回去
            return
        }
        sku.skuCombinationSpecIdArray().forEach { selectSpecId ->
            specArray.find { it.specId == selectSpecId }?.specSelect = true
        }
        calculateSku()
        onSelectSku?.invoke(sku)
    }

    /**
     * 规格选中状态变更
     * @param spec 选中状态变更的规格
     * @param select 是否选中 true 选中, false 取消选中
     * @param calculateDone 完成选择计算后的操作,这个参数非必须,毕竟不涉及异步,只是可以更明确的告知调用方我处理完毕了,可以开始你的表演了
     */
    @JvmOverloads
    fun specSelectStateChanged(
        spec: BaseSpec,
        select: Boolean,
        calculateDone: (() -> Unit)? = null
    ) {
        if (select) {
            //从未选中变为选中,把同组的spec都置为未选中
            specArray.filter { it.specInGroupId == spec.specInGroupId }.forEach {
                it.specSelect = false
            }
        }
        //更新当前spec的选中状态
        spec.specSelect = select
        calculateSku()
        calculateDone?.invoke()
    }

    /**
     * 触发Sku选择算法计算
     */
    private fun calculateSku() {
        //找出当前所有被选中的规格
        val selectSpec = specArray.filter { it.specSelect }
        if (selectSpec.isEmpty()) {
            //一个都没选中
            if (noneSelectedSpecNeedChangeCanBeSelectedState) {
                doWhenNoneSpecSelected()
            } else {
                //若为false,则未选中任何规格时全部规格都可选
                specArray.forEach { it.specCanBeSelected = true }
            }
            return
        }
        doCalculateSku(selectSpec)
        //所有的规格组都有规格选中了,那回调选中的那个sku
        if (selectSpec.size == specDimensionality) {
            val selectSku = skuList.find { sku ->
                sku.skuCombinationSpecIdArray().toList().containsAll(selectSpec.map { it.specId })
            }
            if (selectSku != null) {
                onSelectSku?.invoke(selectSku)
            }
        }
    }

    /**
     * 当一个spec都没有选中时,计算哪些规格天然不能选择情况
     * eg:颜色:id    尺寸:id
     *    红色:101   M:201
     *    蓝色:102   L:202
     * Sku列表里能选中的Sku组合只有101|201，101|202，那么蓝色:102就属于天然不可选择
     */
    protected abstract fun doWhenNoneSpecSelected()

    /**
     * 正式发起Sku选择算法计算,Sku计算的核心算法实现,由各自实现
     */
    protected abstract fun doCalculateSku(selectedSpec: List<BaseSpec>)

}