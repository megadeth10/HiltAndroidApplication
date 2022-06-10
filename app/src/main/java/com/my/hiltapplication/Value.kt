package com.my.hiltapplication

object Value {
    init {
        System.loadLibrary("native-lib")
    }

    /**
     * @return
     */
    external fun a() : String?

}
