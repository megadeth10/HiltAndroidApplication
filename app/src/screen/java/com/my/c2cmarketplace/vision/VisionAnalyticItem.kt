package com.my.c2cmarketplace.vision

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class VisionAnalyticJson: Serializable {
    @SerializedName("data")
    var data: ArrayList<VisionAnalyticItem> = ArrayList(0)
}

class VisionAnalyticItem:Serializable {
    @SerializedName("itemid")
    var Itemid: String = ""

    @SerializedName("imageid")
    var imageid: String = ""
    constructor(_Itemid:String, _imageid:String){
        this.imageid = _imageid
        this.Itemid = _Itemid
    }
}