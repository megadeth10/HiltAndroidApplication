package com.my.hiltapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.deleo.c2cmarketplace.network.json.category.CategoryRequestBody
import com.deleo.c2cmarketplace.network.json.category.CategoryResponse
import com.my.hiltapplication.base.BaseNetworkViewModel
import com.my.hiltapplication.network.NetworkUtil
import com.my.hiltapplication.noupdate.service.CategoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryService : CategoryService
) : BaseNetworkViewModel() {
    private val apiNameCategory = "api_name_category"
    private var _categoryData : MutableLiveData<ArrayList<CategoryResponse>?> = MutableLiveData(null)
    val categoryData : MutableLiveData<ArrayList<CategoryResponse>?> = _categoryData

    init {
        Log.e(tagName, "init() categoryService: $categoryService")
    }

    private fun setCategoryData(data : Any?) {
        if (data is ArrayList<*>) {
            this._categoryData.postValue(data as ArrayList<CategoryResponse>)
        }
    }

    fun getCategoryData() {
        cancelObserver(this.apiNameCategory)
        val header = NetworkUtil.makeRequestBody(
            CategoryRequestBody("ko_KR")
        )
        addObserver(
            this.apiNameCategory,
            categoryService.setObserver(singleObserver = categoryService.getModule().getList(header),
                onSuccess = {
                    Log.e(tagName, "getList() result: $it")
                    when (it) {
                        is ArrayList<*> -> {
                            setCategoryData(it)
                        }
                        else -> {
                            setCategoryData(null)
                        }
                    }
                },
                onError = {
                    Log.e(tagName, "getList() error", it)
                }
            )
        )
    }

    override fun getLogName() = CategoryViewModel::class.simpleName
}