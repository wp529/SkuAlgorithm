### SkuAlgorithm

​	SkuAlgorithm一般指代电商项目中商品规格属性选择时根据用户选择的不同规格属性，我们需要计算出其他规格是否可选动态的展示给用户，由此衍生出的Sku算法。

​	举个例子，比如商品A现有四维规格属性

| 颜色:规格id | 尺寸:规格id | 重量:规格id | 套餐:规格id |
| :---------: | :---------: | :---------: | :---------: |
|  红色:1001  |  5斤:3001   | 套餐一:4001 |             |
|  蓝色:1002  |   M:2001    |  10斤:3002  | 套餐二:4002 |
|             |             |             | 套餐三:4003 |

​	其中A商品以下三种规格组合才有库存

* 红色  	S   	5斤   	套餐一
* 红色  	M      5斤       套餐二
* 蓝色  	M      10斤     套餐三

​    那么当用户选择红色时

* 10斤和套餐三这两个规格属性应展示不可选择状态给用户
* 再选择S，蓝色、10斤、套餐二、套餐三这些规格属性应展示不可选择状态给用户
* 再选择套餐一，蓝色、M、10斤、套餐二、套餐三这些规格属性应展示不可选择状态给用户

​    没接触过此类业务场景的开发凭着第一感觉可能会觉得实现起来并不复杂，但如果深入实现起来就会发现随着规格属性维数的增加，复杂度成倍上升。Github和相关的文章都讲得晦涩难懂，现成的库又只能成套使用，没有将Sku选择的实现抽离出来，使用上很尴尬。故分享Sku选择的实现思路并提供kotlin版实现代码，使用kotlin语言的开发者可直接使用，代码只处理Sku选择的实现，不涉及任何UI选择和业务逻辑，可以自定义各种个性化需求。

​	对应上表四维规格商品，各类业务后端返回数据结构都大同小异，现举例如下，若出入太大可骂到后端返回成此结构或者自行转换成此结构

```json
{
	"skuList":[{
    "skuId":"xxx",
    "storageCount":100,
    "skuPrice":19.99,
    //购买上限
    "upperLimitCount":10,
    //此字段表示构成这个sku的规格id组合,配合上表为红色,S,5斤,套餐1，此字段表现形式多种，大同小异
    "specCombinationId":"1001|2001|3001|4001"
  }],
  "specGroup":[
    {
      "specGroupId":"101",
      "specGroupName":"颜色",
      "specList":[
        {
          "specId":"1001",
          "specName":"红色"
        }
        ...
      ]
    },
    {
      "specGroupId":"102",
      "specGroupName":"尺寸",
      "specList":[
        {
          "specId":"2001",
          "specName":"S"
        }
        ...
      ]
    }
    ...
  ]
}
```

库中提供了两种实现方法

* 循环法
* 无向图法

在此只描述最简单易懂的循环法，按此思路可实现各类语言版本。

##### 核心思路

```
以上表4维规格举例,当前选中了蓝色，10斤
1.构造规格groupId数组A [101,102,103,104]
2.构造一个长度为4的空数组B
3.将已选中的规格，按规格组顺序填充空数组B，例如10斤所在的规格组是103，在数组A中是第2位，那么10斤这个规格id就该放在数组B的第2位，填充完毕的数组B [1002,null,3002,null]
4.遍历所有规格
 4.1 将B数组内容复制出来构造一个temp数组 [1002,null,3002,null] 再按规格组顺序填充temp数组
 4.2 填充temp数组的意思:例如遍历到第一个规格红色,红色所在规格组是101,在数组A中是第0位，那么temp[0]=红色规格id,得到temp数组为[1001,null,3002,null]
 4.3 得到填充后的temp数组后，遍历skuList,将specCombinationId拆分成数组C,按位匹配，若temp数组中的非null位都能与数组C每一位对应上且此sku有库存，那么规格红色可以被选择，若skuList遍历完都不能满足条件，则规格红色不可以被选择。
 4.4 重复4.1-4.3直到所有规格遍历完毕，即完成一次sku选择算法
```

无向图法思路可见: [无向图法](https://blog.csdn.net/weixin_39875031/article/details/111284152)

* 循环法，此解法时间复杂度高,空间复杂度低(小米6实测9维规格,每维规格99个,单次选择计算耗时35毫秒左右)，优点是若不同组下的规格id出现相同的话也能正常工作，缺点是Sku的specCombinationId若不按规格组顺序排列的话此算法不能正常工作
* 无向图法，此解法时间复杂度低,空间复杂度高(小米6实测9维规格,每维规格99个,单次选择耗时1毫秒左右)，优点是Sku的specCombinationId若不按规格组顺序排列的话此算法能正常工作，缺点是若不同规格组下的规格id出现相同那么不能正常工作

​    **那么怎么选择呢？若不同组下的规格id可能会出现相同的情况那么选择循环法，若Sku的specCombinationId不按规格组顺序排列的话那么选择无向图法，若上述两种情况都出现那么你最好是把键盘给后端，叫后端来开发。若上述两种情况都不出现，那肯定无向图法优于循环法。**

##### Android使用

**添加依赖:**

```groovy
allprojects {
   repositories {
      ...
      maven { url 'https://jitpack.io' }
   }
}
dependencies {

}
```

##### 具体使用方式详见Demo

