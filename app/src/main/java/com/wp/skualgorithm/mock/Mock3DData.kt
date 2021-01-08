package com.wp.skualgorithm.mock

import com.wp.skualgorithm.model.SingleSpec
import com.wp.skualgorithm.model.Sku
import com.wp.skualgorithm.model.SpecGroup

/**
 * 模拟sku列表,数据如下 不在sku列表里的组合就代表无库存
 * 红色 S 5斤   库存10 29.99 购买下限1个 上限5个
 * 蓝色 m 10斤  库存20 39.99 购买下限2个 上限20个
 * 黄色 L 20斤  库存30 39.99 购买下限3个 上限50个
 * 紫色 XL 30斤 库存40 49.99 购买下限3个 上限50个
 * 橙色 XXL 40斤 库存50 59.99 购买下限3个 上限50个
 * 绿色 M 50斤   库存60 69.99 购买下限3个 上限50个
 * 黑色 L 30斤 库存70 79.99 购买下限3个 上限50个
 */
fun mock3DSkuList(): MutableList<Sku> = arrayListOf(
    Sku(
        skuId = "10010",
        storageCount = 10,
        specCombinationId = "1001|2001|3001",
        skuPrice = "29.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=351945686,404002934&fm=26&gp=0.jpg",
        upperLimitCount = 5,
        lowerLimitCount = 1
    ),
    Sku(
        skuId = "10011",
        storageCount = 20,
        specCombinationId = "1002|2002|3002",
        skuPrice = "39.99",
        skuImageUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3414292365,3872598300&fm=26&gp=0.jpg",
        upperLimitCount = 20,
        lowerLimitCount = 2
    ),
    Sku(
        skuId = "10012",
        storageCount = 30,
        specCombinationId = "1003|2003|3003",
        skuPrice = "39.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=202491719,878231213&fm=26&gp=0.jpg",
        upperLimitCount = 50,
        lowerLimitCount = 3
    ),
    Sku(
        skuId = "10013",
        storageCount = 40,
        specCombinationId = "1004|2004|3004",
        skuPrice = "49.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=202491719,878231213&fm=26&gp=0.jpg",
        upperLimitCount = 50,
        lowerLimitCount = 3
    ),
    Sku(
        skuId = "10014",
        storageCount = 50,
        specCombinationId = "1005|2005|3005",
        skuPrice = "59.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=202491719,878231213&fm=26&gp=0.jpg",
        upperLimitCount = 50,
        lowerLimitCount = 3
    ),
    Sku(
        skuId = "10015",
        storageCount = 60,
        specCombinationId = "1006|2002|3006",
        skuPrice = "79.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=202491719,878231213&fm=26&gp=0.jpg",
        upperLimitCount = 50,
        lowerLimitCount = 3
    ),
    Sku(
        skuId = "10016",
        storageCount = 70,
        specCombinationId = "1007|2003|3004",
        skuPrice = "79.99",
        skuImageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=202491719,878231213&fm=26&gp=0.jpg",
        upperLimitCount = 50,
        lowerLimitCount = 3
    )
)

/**
 * 模拟3维规格数据,数据如下
 * 颜色   红色  蓝色
 * 尺寸   S   M
 * 重量  5斤  10斤
 */
fun mock3DSpecGroupData(): MutableList<SpecGroup> = arrayListOf(
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
            ),
            SingleSpec(
                specId = "1005",
                specName = "橙色",
                specInGroupId = "101"
            ),
            SingleSpec(
                specId = "1006",
                specName = "绿色",
                specInGroupId = "101"
            ),
            SingleSpec(
                specId = "1007",
                specName = "黑色",
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
            ),
            SingleSpec(
                specId = "2003",
                specName = "L",
                specInGroupId = "102"
            ),
            SingleSpec(
                specId = "2004",
                specName = "XL",
                specInGroupId = "102"
            ),
            SingleSpec(
                specId = "2005",
                specName = "XXL",
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
            ),
            SingleSpec(
                specId = "3003",
                specName = "20斤",
                specInGroupId = "103"
            ),
            SingleSpec(
                specId = "3004",
                specName = "30斤",
                specInGroupId = "103"
            ),
            SingleSpec(
                specId = "3005",
                specName = "40斤",
                specInGroupId = "103"
            ),
            SingleSpec(
                specId = "3006",
                specName = "50斤",
                specInGroupId = "103"
            )
        )
    )
)