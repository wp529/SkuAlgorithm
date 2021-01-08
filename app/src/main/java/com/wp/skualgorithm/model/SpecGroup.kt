package com.wp.skualgorithm.model

import com.wp.sku_algorithm.base.BaseSpecGroup
/**
 * 规格组Model，继承于BaseSpecGroup
 * 除必须字段外，可以自行根据需求扩展字段并使用
 * create by WangPing
 * on 2021/1/6
 */
class SpecGroup(
    override val specGroupId: String,
    val specGroupName: String,
    override val specList: List<SingleSpec>
) : BaseSpecGroup(specGroupId, specList)