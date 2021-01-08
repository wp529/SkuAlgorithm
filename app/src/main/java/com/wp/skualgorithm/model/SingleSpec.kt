package com.wp.skualgorithm.model

import com.wp.sku_algorithm.base.BaseSpec

/**
 * 规格Model，继承于BaseSpec
 * 除必须字段外，可以自行根据需求扩展字段并使用
 * create by WangPing
 * on 2021/1/6
 */
class SingleSpec(
    override val specId: String,
    val specName: String,
    override val specInGroupId: String
) : BaseSpec(specId, specInGroupId, false, true)