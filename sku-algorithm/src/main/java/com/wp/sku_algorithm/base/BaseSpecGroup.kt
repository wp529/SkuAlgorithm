package com.wp.sku_algorithm.base


/**
 * 实际业务中的规格组model需继承于此
 * create by WangPing
 * on 2021/1/6
 */
open class BaseSpecGroup(
    open val specGroupId: String,
    open val specList: List<BaseSpec>
)