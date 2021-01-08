package com.wp.sku_algorithm.base

/**
 * 实际业务中的Sku model需实现此
 * create by WangPing
 * on 2021/1/6
 */
interface ISku {

    /**
     * Sku的唯一标识
     */
    fun skuId(): String

    /**
     * Sku不能进行选择的条件,eg:库存小于1不能选,那么此方法返回 库存 < 1
     * 就可以很方便的根据业务要求进行扩展,例如某个Sku黑号用户不能买,普通用户可以买
     * 那么后端在给你返回Sku数据时就可以在Sku结构中扩展isUserInBlackList字段
     * 你就将此方法返回isUserInBlackList字段就能实现前端限制黑号用户不能选择,普通用户可以选择了
     * @return true 不可选 false可选
     */
    fun skuCanNotBeSelected(): Boolean

    /**
     * 此Sku的specId组合数组
     * eg: 颜色:id    尺寸:id    重量:id
     *     红色:1001  M:2001     5斤:3001
     *     蓝色:1002  L:2002    10斤:3002
     * 那么组成 红色,M,10斤 这个Sku商品的specId数组就为 [1001,2001,3002]
     * @return 组成的specId数组
     */
    fun skuCombinationSpecIdArray(): Array<String>
}