package com.wp.sku_algorithm

import android.util.Log
import com.wp.sku_algorithm.base.*

/**
 * 此解法时间复杂度高,空间复杂度低(小米6实测9维规格,每维规格99个,单次选择计算耗时35毫秒左右)
 * 优点是若不同组下的规格id出现相同的话也能正常工作
 * eg:颜色:id     尺寸:id
 *    红色:101    M:101
 *    黄色:102    L:102
 *
 * 缺点是若Sku不按规格组顺序排列的话此算法不能正常工作
 * eg:specGroupList中按顺序是 颜色|尺寸|重量,但在skuList的skuCombinationSpecIdArray顺序是 尺寸|颜色|重量 或 重量|尺寸|颜色,那么此算法不能正常工作
 * 若规格组合顺序会出现此种无序的情况可以使用
 * @see com.wp.sku_algorithm.MatrixSkuCalculator
 *
 * 使用Sku循环求解法实现的Sku算法
 * create by WangPing
 * on 2021/1/6
 */
class CirculationSkuCalculator @JvmOverloads constructor(
    specGroupList: List<BaseSpecGroup>,
    skuList: List<ISku>,
    noneSelectedSpecNeedChangeCanBeSelectedState: Boolean = true
) : BaseCalculator(specGroupList, skuList, noneSelectedSpecNeedChangeCanBeSelectedState) {

    //用于后续填充规格在数组中的位置判定,代表README中的[101,102,103,104]例子
    private val groupIdOrder: List<String>

    init {
        val initStartTime = System.currentTimeMillis()
        groupIdOrder = specGroupList.map { iSpecGroup ->
            iSpecGroup.specGroupId
        }
        Log.i(
            logTag,
            "${logTag}初始化耗时: ${System.currentTimeMillis() - initStartTime}毫秒"
        )
        doWhenNoneSpecSelected()
    }

    //此算法若没有选择任何规格,也是再计算一遍即可
    override fun doWhenNoneSpecSelected() {
        doCalculateSku(arrayListOf())
    }

    //Sku循环求解法的核心处理逻辑,是README中核心思路的代码实现
    override fun doCalculateSku(selectedSpec: List<BaseSpec>) {
        val calculateStartTime = System.currentTimeMillis()
        //查找出目前选中规格id的数组,按规格组顺序排列
        val selectSpecCombinationId = arrayOfNulls<String>(specDimensionality)
        selectedSpec.forEach { singleSpec ->
            if (singleSpec.specSelect) {
                selectSpecCombinationId[groupIdOrder.indexOf(singleSpec.specInGroupId)] =
                    singleSpec.specId
            }
        }
        //计算此次选中状态变更后,各规格在当前选择下是否还能组合出有库存的sku
        repeat(specGroupList.size) { groupIndex ->
            specGroupList[groupIndex].specList.forEach { singleSpec ->
                //模拟要是选择了当前规格的选中情况
                val mockSelectCurrentSpecCombinationId = arrayOfNulls<String>(specDimensionality)
                //将当前选中的规格先复制一份到待模拟的数组中
                repeat(selectSpecCombinationId.size) {
                    mockSelectCurrentSpecCombinationId[it] = selectSpecCombinationId[it]
                }
                //模拟在现有规格选中情况下再选择此规格的情况
                mockSelectCurrentSpecCombinationId[groupIndex] = singleSpec.specId
                singleSpec.specCanBeSelected = skuList.find { sku ->
                    if (sku.skuCanNotBeSelected()) {
                        return@find false
                    }
                    //模拟选中此规格后,若当前组合的规格在sku列表里的能一一对应上则代表此规格就是有库存的,可选择的
                    val skuSpecCombinationIdSplit = sku.skuCombinationSpecIdArray()
                    repeat(specDimensionality) { index ->
                        if (mockSelectCurrentSpecCombinationId[index] != null && mockSelectCurrentSpecCombinationId[index] != skuSpecCombinationIdSplit[index]) {
                            return@find false
                        }
                    }
                    return@find true
                } != null
            }
        }
        Log.i(
            logTag,
            "${logTag}计算一次耗时: ${System.currentTimeMillis() - calculateStartTime}毫秒"
        )
    }
}