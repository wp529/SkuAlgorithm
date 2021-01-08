package com.wp.skualgorithm.mock

import com.wp.skualgorithm.model.SingleSpec
import com.wp.skualgorithm.model.Sku
import com.wp.skualgorithm.model.SpecGroup

/**
 * 模拟sku列表,数据如下 不在sku列表里的组合就代表无库存
 * 红色 S  库存0  29.99 购买下限1个 上限5个
 * 蓝色 m  库存20 39.99 购买下限2个 上限20个
 * 黄色 m  库存30 39.99 购买下限3个 上限50个
 * 紫色 m  库存40 49.99 购买下限3个 上限50个
 * 红色 m  库存40 49.99 购买下限3个 上限50个
 */
fun mock2DSkuList(): MutableList<Sku> = arrayListOf(
    Sku(
        skuId = "10010",
        storageCount = 0,
        specCombinationId = "1001|2001",
        skuPrice = "29.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=351945686,404002934&fm=26&gp=0.jpg",
        upperLimitCount = 5,
        lowerLimitCount = 1
    ),
    Sku(
        skuId = "10011",
        storageCount = 20,
        specCombinationId = "1002|2002",
        skuPrice = "39.99",
        skuImageUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3414292365,3872598300&fm=26&gp=0.jpg",
        upperLimitCount = 20,
        lowerLimitCount = 2
    ),
    Sku(
        skuId = "10012",
        storageCount = 30,
        specCombinationId = "1003|2002",
        skuPrice = "39.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=202491719,878231213&fm=26&gp=0.jpg",
        upperLimitCount = 50,
        lowerLimitCount = 3
    ),
    Sku(
        skuId = "10013",
        storageCount = 40,
        specCombinationId = "1004|2002",
        skuPrice = "49.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=202491719,878231213&fm=26&gp=0.jpg",
        upperLimitCount = 50,
        lowerLimitCount = 3
    ),
    Sku(
        skuId = "10014",
        storageCount = 40,
        specCombinationId = "1001|2002",
        skuPrice = "49.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=202491719,878231213&fm=26&gp=0.jpg",
        upperLimitCount = 50,
        lowerLimitCount = 3
    )
)

/**
 * 模拟2维规格数据,数据如下
 * 颜色   红色  蓝色
 * 尺寸   S   M
 */
fun mock2DSpecGroupData(): MutableList<SpecGroup> = arrayListOf(
    SpecGroup(
        specGroupId = "101",
        specGroupName = "颜色",
        specList = arrayListOf(
            SingleSpec(
                specId = "1001",
                specName = "红色",
                specInGroupId = "101"
            ),
            SingleSpec(
                specId = "1002",
                specName = "蓝色",
                specInGroupId = "101"
            ),
            SingleSpec(
                specId = "1003",
                specName = "黄色",
                specInGroupId = "101"
            ),
            SingleSpec(
                specId = "1004",
                specName = "紫色",
                specInGroupId = "101"
            )
        )
    ),
    SpecGroup(
        specGroupId = "102",
        specGroupName = "尺寸",
        specList = arrayListOf(
            SingleSpec(
                specId = "2001",
                specName = "S",
                specInGroupId = "102"
            ),
            SingleSpec(
                specId = "2002",
                specName = "M",
                specInGroupId = "102"
            )
        )
    )
)