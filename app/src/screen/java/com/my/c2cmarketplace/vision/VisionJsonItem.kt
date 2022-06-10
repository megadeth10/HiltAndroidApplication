package com.my.c2cmarketplace.demo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 구글 비전 이미지 분석 결과 맵핑 아이템
 */
class VisionJsonRootItem: Serializable {
    @SerializedName("data")
    var data = ArrayList<VisionJsonItem>(0)
}

class VisionJsonItem: Serializable {
    @SerializedName("visionid")
    var visionid: Int = 0

    @SerializedName("objectname")
    var objectname: String = ""

    @SerializedName("objectnameko1")
    var objectnameko1: String = ""

    @SerializedName("objectnameko2")
    var objectnameko2: String = ""

    @SerializedName("objectnameko3")
    var objectnameko3: String = ""

    @SerializedName("category1")
    var category1: Int = 0

    @SerializedName("category2")
    var category2: Int = 0

    @SerializedName("category3")
    var category3: Int = 0
}

class VisionMapItem{
    var visionid: Int = 0
    var objectname: String = ""
    var objectnamekos: ArrayList<String> = ArrayList(0)
    var category = ArrayList<Int>(0)
    var stringCategory = ArrayList<String>(0)
    constructor(item:VisionJsonItem){
        this.visionid = item.visionid
        this.objectname = item.objectname
        if(item.objectnameko1 != null && item.objectnameko1.isNotEmpty()){
            this.objectnamekos.add(item.objectnameko1)
        }
        if(item.objectnameko2 != null && item.objectnameko2.isNotEmpty()){
            this.objectnamekos.add(item.objectnameko2)
        }
        if(item.objectnameko3 != null && item.objectnameko3.isNotEmpty()){
            this.objectnamekos.add(item.objectnameko3)
        }
        if(item.category1 > 0){
            this.category.add(item.category1)
            this.stringCategory.add(item.category1.toString())
        }
        if(item.category2 > 0){
            this.category.add(item.category2)
            this.stringCategory.add(item.category2.toString())
        }
        if(item.category3 > 0){
            this.category.add(item.category3)
            this.stringCategory.add(item.category3.toString())
        }
    }
}