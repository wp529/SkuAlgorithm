package com.wp.skualgorithm.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.toColorInt
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wp.skualgorithm.R
import com.wp.skualgorithm.model.SingleSpec
import com.wp.skualgorithm.model.SpecGroup
import kotlinx.android.synthetic.main.item_goods_spec.view.*

/**
 * 规格选择列表adapter
 * create by WangPing
 * on 2021/1/6
 */
class SpecListAdapter(data: MutableList<SpecGroup>) :
    BaseQuickAdapter<SpecGroup, BaseViewHolder>(R.layout.item_goods_spec, data) {
    var onSpecClickListener: ((singleSpec: SingleSpec) -> Unit)? = null

    override fun convert(holder: BaseViewHolder, item: SpecGroup) {
        holder.itemView.apply {
            tvSpecGroupName.text = item.specGroupName
            flSpecItemContainer.removeAllViews()
            //添加规格组中的规格
            item.specList.forEach { singleSpec ->
                flSpecItemContainer.addView(buildTextView(context, singleSpec))
            }
        }
    }

    //构造规格展示TextView
    private fun buildTextView(context: Context, singleSpec: SingleSpec): TextView {
        val specText = View.inflate(context, R.layout.item_spec_text, null) as TextView
        return specText.apply {
            text = singleSpec.specName
            //这里是核心展示逻辑,如果经过SkuCalculator计算后此规格不能组合出有库存的Sku,那么会将BaseSpec的specHadStorage置为false,反之为true
            //根据SkuCalculator的计算结果展示可选中和不可选中的UI
            if (!singleSpec.specCanBeSelected) {
                setBackgroundResource(R.drawable.round_white_bg)
                setTextColor("#cdcdcd".toColorInt())
                setOnClickListener {
                    Toast.makeText(context, "无库存", Toast.LENGTH_SHORT).show()
                }
                return@apply
            }
            //当前规格是否被选中的UI
            if (singleSpec.specSelect) {
                setBackgroundResource(R.drawable.round_red_f43e37_bg)
                setTextColor("#ffffff".toColorInt())
            } else {
                setBackgroundResource(R.drawable.round_white_bg)
                setTextColor("#4a4a4a".toColorInt())
            }
            setOnClickListener {
                onSpecClickListener?.invoke(singleSpec)
            }
        }
    }
}