package com.wp.skualgorithm.mock

import com.wp.skualgorithm.model.SingleSpec
import com.wp.skualgorithm.model.Sku
import com.wp.skualgorithm.model.SpecGroup

/**
 * 模拟N个Sku
 */
fun mockNDSkuList(): MutableList<Sku> = arrayListOf(
    Sku(
        skuId = "10010",
        storageCount = 10,
        specCombinationId = "1000|1100|1200|1300|1400|1500|1600|1700|1800",
        skuPrice = "29.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=351945686,404002934&fm=26&gp=0.jpg",
        upperLimitCount = 5,
        lowerLimitCount = 1
    ),
    Sku(
        skuId = "10011",
        storageCount = 10,
        specCombinationId = "1001|1101|1201|1301|1401|1501|1601|1701|1801",
        skuPrice = "29.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=351945686,404002934&fm=26&gp=0.jpg",
        upperLimitCount = 5,
        lowerLimitCount = 1
    )
)


/**
 * 模拟N维规格数据,每类规格50个
 */
fun mockNDSpecGroupData(n: Int = 9): MutableList<SpecGroup> {
    val specGroupList = ArrayList<SpecGroup>()
    repeat(n) {
        val specList = ArrayList<SingleSpec>()
        repeat(10) { index ->
            specList.add(
                SingleSpec(
                    specId = "1${it}0$index",
                    specName = "第${index + 1}个",
                    specInGroupId = "10$it"
                )
            )
        }
        specGroupList.add(
            SpecGroup(
                specGroupId = "10$it",
                specGroupName = "第${it + 1}维",
                specList = specList
            )
        )
    }
    return specGroupList
}