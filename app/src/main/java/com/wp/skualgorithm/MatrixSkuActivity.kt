package com.wp.skualgorithm

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.wp.sku_algorithm.MatrixSkuCalculator
import com.wp.sku_algorithm.base.ISku
import com.wp.skualgorithm.adapter.SpecListAdapter
import com.wp.skualgorithm.mock.*
import com.wp.skualgorithm.model.Sku
import kotlinx.android.synthetic.main.activity_matrix_sku.*
import kotlin.math.max
import kotlin.math.min

/**
 * 使用矩阵Sku求解法实现的demo
 */
class MatrixSkuActivity : AppCompatActivity() {
    private var currentSelectedSku: Sku? = null

    @SuppressLint("SetTextI18n")
    private val onSelectSku: ((sku: ISku) -> Unit) = {
        Log.i("SkuCalculator", "被选则的sku为:$it")
        //选择了某个具体的sku
        if (it is Sku) {
            currentSelectedSku = it
            //加载对应sku数据
            Glide.with(this@MatrixSkuActivity)
                .load(it.skuImageUrl)
                .centerCrop()
                .into(ivSkuImage)
            tvSkuPrice.text = "¥${it.skuPrice}"
            tvSkuStorageCount.text = "库存:${it.storageCount}"
            tvCount.text = max(it.lowerLimitCount, 1).toString()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matrix_sku)
        val specGroupList = mock4DSpecGroupData()
        val skuList = mock4DSkuList().apply {
            //展示第一个Sku的信息
            firstOrNull()?.let {
                currentSelectedSku = it
                //加载对应sku数据
                Glide.with(this@MatrixSkuActivity)
                    .load(it.skuImageUrl)
                    .centerCrop()
                    .into(ivSkuImage)
                tvSkuPrice.text = "¥${it.skuPrice}"
                tvSkuStorageCount.text = "库存:${it.storageCount}"
                tvCount.text = max(it.lowerLimitCount, 1).toString()
            }
        }
        //构造SkuCalculator
        val skuCalculator = MatrixSkuCalculator(
            specGroupList,
            skuList,
            noneSelectedSpecNeedChangeCanBeSelectedState = true
        ).apply {
            onSelectSku = this@MatrixSkuActivity.onSelectSku
        }
        //选中的Sku不存在,一般业务场景是在商品详情页唤起规格选择弹窗时需要默认选中当前Sku,但此Sku被下架了
        skuCalculator.setSkuSelect(
            Sku(
                skuId = "10019",
                storageCount = 20,
                specCombinationId = "1001|2002|3001|4003",
                skuPrice = "39.99",
                skuImageUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3414292365,3872598300&fm=26&gp=0.jpg",
                upperLimitCount = 20,
                lowerLimitCount = 2
            )
        ) {
            it.skuCombinationSpecIdArray().forEach { specId ->
                specGroupList.forEach { specGroup ->
                    specGroup.specList.forEach { spec ->
                        if (spec.specId == specId && spec.specCanBeSelected) {
                            skuCalculator.specSelectStateChanged(spec, true)
                        }
                    }
                }
            }
        }
        //可以自行选择怎么实现规格选择UI,我选择使用RV实现的规格选择列表
        rvSpecList.adapter = SpecListAdapter(specGroupList).apply {
            onSpecClickListener = {
                //某个规格被点击了,告知SkuCalculator进行Sku选择计算
                skuCalculator.specSelectStateChanged(it, !it.specSelect) {
                    //计算完毕,刷新规格选择列表
                    notifyDataSetChanged()
                }
            }
        }
        //减少购买数量
        tvSub.setOnClickListener {
            currentSelectedSku?.let {
                val currentCount = tvCount.text.toString().toInt()
                //处理Sku购买下限问题
                if (currentCount <= max(it.lowerLimitCount, 1)) {
                    tvCount.text = max(it.lowerLimitCount, 1).toString()
                    Toast.makeText(this, "不能再少了", Toast.LENGTH_SHORT).show()
                    return@let
                }
                tvCount.text = (currentCount - 1).toString()
            }
        }
        //增加购买数量
        tvAdd.setOnClickListener {
            currentSelectedSku?.let {
                val currentCount = tvCount.text.toString().toInt()
                //处理Sku购买上限问题
                if (currentCount >= min(it.upperLimitCount, it.storageCount)) {
                    tvCount.text = min(it.upperLimitCount, it.storageCount).toString()
                    Toast.makeText(this, "不能再多了", Toast.LENGTH_SHORT).show()
                    return@let
                }
                tvCount.text = (currentCount + 1).toString()
            }
        }
    }


}