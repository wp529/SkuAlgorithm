package com.wp.skualgorithm.mock

import com.wp.skualgorithm.model.SingleSpec
import com.wp.skualgorithm.model.Sku
import com.wp.skualgorithm.model.SpecGroup

/**
 * 模拟sku列表,数据如下 不在sku列表里的组合就代表无库存
 * 红色 S 5斤   套餐一 库存10 29.99 购买下限1个 上限5个
 * 红色 m 5斤   套餐二 库存20 39.99 购买下限2个 上限20个
 * 蓝色 m 10斤  套餐三 库存30 49.99 购买下限2个 上限20个
 */
fun mock4DSkuList(): MutableList<Sku> = arrayListOf(
    Sku(
        skuId = "10010",
        storageCount = 10,
        specCombinationId = "1001|2001|3001|4001",
        skuPrice = "29.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=351945686,404002934&fm=26&gp=0.jpg",
        upperLimitCount = 5,
        lowerLimitCount = 1
    ),
    Sku(
        skuId = "10011",
        storageCount = 20,
        specCombinationId = "1001|2002|3001|4002",
        skuPrice = "39.99",
        skuImageUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3414292365,3872598300&fm=26&gp=0.jpg",
        upperLimitCount = 20,
        lowerLimitCount = 2
    ),
    Sku(
        skuId = "10012",
        storageCount = 30,
        specCombinationId = "1002|2002|3002|4003",
        skuPrice = "39.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=202491719,878231213&fm=26&gp=0.jpg",
        upperLimitCount = 50,
        lowerLimitCount = 3
    )
)

/**
 * 模拟4维规格数据,数据如下
 * 颜色   红色  蓝色
 * 尺寸   S   M
 * 重量  5斤  10斤
 * 套餐  套餐一 套餐二 套餐三
 */
fun mock4DSpecGroupData(): MutableList<SpecGroup> = arrayListOf(
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
    ),
    SpecGroup(
        specGroupId = "103",
        specGroupName = "重量",
        specList = arrayListOf(
            SingleSpec(
                specId = "3001",
                specName = "5斤",
                specInGroupId = "103"
            ),
            SingleSpec(
                specId = "3002",
                specName = "10斤",
                specInGroupId = "103"
            )
        )
    ),
    SpecGroup(
        specGroupId = "104",
        specGroupName = "套餐",
        specList = arrayListOf(
            SingleSpec(
                specId = "4001",
                specName = "套餐一",
                specInGroupId = "104"
            ),
            SingleSpec(
                specId = "4002",
                specName = "套餐二",
                specInGroupId = "104"
            ),
            SingleSpec(
                specId = "4003",
                specName = "套餐三",
                specInGroupId = "104"
            )
        )
    )
)