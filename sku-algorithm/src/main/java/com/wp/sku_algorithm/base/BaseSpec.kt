package com.wp.sku_algorithm.base

/**
 * 实际业务中的规格model需继承于此
 * create by WangPing
 * on 2021/1/6
 */
open class BaseSpec(
    open val specId: String,
    open val specInGroupId: String,
    /**
     * 当前规格是否处于选中状态
     */
    open var specSelect: Boolean,
    /**
     * 如果经过SkuCalculator计算后此规格不能组合出可选的Sku,那么算法会将BaseSpec的specCanBeSelected置为false,反之为true
     */
    open var specCanBeSelected: Boolean
)