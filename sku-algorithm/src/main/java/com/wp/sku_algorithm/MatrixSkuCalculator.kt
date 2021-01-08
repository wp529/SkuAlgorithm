package com.wp.sku_algorithm

import android.util.Log
import com.wp.sku_algorithm.base.*

/**
 * 此解法时间复杂度低,空间复杂度高(小米6实测9维规格,每维规格99个,单次选择耗时1毫秒左右)
 * 优点是就算Sku不按规格组顺序排列,也能正常工作
 * eg:specGroupList中按顺序是 颜色|尺寸|重量 返回的,但在skuList的specCombinationId偏偏要乱七八糟的一会儿 尺寸|颜色|重量 一会儿 重量|尺寸|颜色,此算法可以无视此种情况也能正常工作
 *
 * 缺点是若不同规格组下的规格id出现相同那么不能正常工作
 * eg:颜色:id     尺寸:id
 *    红色:101    M:101
 *    黄色:102    L:102
 * 若会出现此种情况可以使用
 * @see com.wp.sku_algorithm.CirculationSkuCalculator
 *
 * 使用无向图(矩阵实现)Sku求解法实现的Sku算法
 * create by WangPing
 * on 2021/1/6
 */
class MatrixSkuCalculator constructor(
    specGroupList: List<BaseSpecGroup>,
    skuList: List<ISku>,
    noneSelectedSpecNeedChangeCanBeSelectedState: Boolean = true
) : BaseCalculator(specGroupList, skuList, noneSelectedSpecNeedChangeCanBeSelectedState) {
    //无向图specId顶点数组
    private val vertexSpecIdArray = specArray.map {
        it.specId
    }

    //创建矩阵
    private val matrix: Array<Array<Boolean>> = Array(vertexSpecIdArray.size) {
        Array(vertexSpecIdArray.size) { false }
    }

    init {
        val initStartTime = System.currentTimeMillis()
        skuList.filter {
            //可以被选择的Sku才需要关联其中的顶点
            !it.skuCanNotBeSelected()
        }.map { it.skuCombinationSpecIdArray().toList() }.forEach { skuCombinationSpecIdArray ->
            //根据Sku连通各顶点路径
            skuCombinationSpecIdArray.forEach { specId ->
                makeSpecIdBeRelatedWithSpecIdArray(specId, skuCombinationSpecIdArray)
            }
            //经过Sku连通各顶点路径后若自己跟自己有通路,那么就可以把自己与同组的都连起来
            val hadPathWithSelfVertexIndexList = ArrayList<Int>()
            repeat(specArray.size) {
                if (matrix[it][it]) {
                    hadPathWithSelfVertexIndexList.add(it)
                }
            }
            //将顶点index按组分出来
            val groupVertexIndexList = ArrayList<List<Int>>()
            specGroupList.forEach { specGroup ->
                groupVertexIndexList.add(hadPathWithSelfVertexIndexList.filter {
                    specArray[it].specInGroupId == specGroup.specGroupId
                })
            }
            //分完组后连通路径
            groupVertexIndexList.forEach { sameGroupSpecIds ->
                if (!sameGroupSpecIds.isNullOrEmpty()) {
                    sameGroupSpecIds.forEach {
                        makeSpecIdBeRelatedWithSpecIdArray(it, sameGroupSpecIds)
                    }
                }
            }
        }
        Log.i(
            logTag,
            "${logTag}初始化耗时: ${System.currentTimeMillis() - initStartTime}毫秒"
        )
        doWhenNoneSpecSelected()
    }

    override fun doWhenNoneSpecSelected() {
        //没选择任何规格的状态下,若自己跟自己有通路,那么此规格就是可选的
        repeat(specArray.size) {
            specArray[it].specCanBeSelected = matrix[it][it]
        }
    }

    //使用无向图(邻接矩阵实现)Sku求解法的核心处理逻辑
    override fun doCalculateSku(selectedSpec: List<BaseSpec>) {
        val calculateStartTime = System.currentTimeMillis()
        //找出所有已选顶点对应的矩阵列
        val selectMatrixColumnArray = selectedSpec.map {
            //找出specId顶点对应的矩阵列
            matrix[vertexSpecIdArray.indexOf(it.specId)]
        }
        //求出所有矩阵列的交集
        val selectMatrixColumnIntersection = Array(vertexSpecIdArray.size) { index ->
            var result = true
            selectMatrixColumnArray.forEach { matrixColumn ->
                result = result && matrixColumn[index]
            }
            result
        }
        //交集中还有路径的顶点即为可选择的顶点
        repeat(specArray.size) {
            specArray[it].specCanBeSelected = selectMatrixColumnIntersection[it]
        }
        Log.i(
            logTag,
            "${logTag}计算一次耗时: ${System.currentTimeMillis() - calculateStartTime}毫秒"
        )

    }

    //将一个specId顶点与另一组specId顶点数组连通路径
    private fun makeSpecIdBeRelatedWithSpecIdArray(specId: String, specIdArray: List<String>) {
        makeSpecIdBeRelatedWithSpecIdArray(
            vertexSpecIdArray.indexOf(specId),
            specIdArray.map { vertexSpecIdArray.indexOf(it) })
    }

    //将一个specId下标与一组顶点下标连通路径
    private fun makeSpecIdBeRelatedWithSpecIdArray(
        specVertexIndex: Int,
        specVertexIndexArray: List<Int>
    ) {
        specVertexIndexArray.forEach {
            matrix[specVertexIndex][it] = true
        }
    }
}