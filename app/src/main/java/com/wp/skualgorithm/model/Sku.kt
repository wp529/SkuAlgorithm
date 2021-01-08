package com.wp.skualgorithm.model

import com.wp.sku_algorithm.base.ISku

/**
 * Sku Model，实现于ISku
 * 除必须字段外，可以自行根据需求扩展字段并使用
 * create by WangPing
 * on 2021/1/6
 */
class Sku(
    val skuId: String,
    val storageCount: Int,
    val specCombinationId: String,
    val skuPrice: String,
    val skuImageUrl: String,
    val upperLimitCount: Int,
    val lowerLimitCount: Int
) : ISku {
    //库存小于1就不能买了

    override fun skuId(): String = skuId

    override fun skuCanNotBeSelected(): Boolean = storageCount < 1

    override fun skuCombinationSpecIdArray(): Array<String> = specCombinationId.split("|").toTypedArray()

    override fun toString(): String {
        return "Sku(skuId='$skuId', storageCount=$storageCount, specCombinationId='$specCombinationId', skuPrice='$skuPrice', skuImageUrl='$skuImageUrl')"
    }
}