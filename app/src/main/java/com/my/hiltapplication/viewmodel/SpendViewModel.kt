package com.my.hiltapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.my.hiltapplication.base.BaseViewModel
import com.my.hiltapplication.room.Spend
import com.my.hiltapplication.room.datatracker.SpendsTrackerDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by YourName on 2022/06/14.
 */
@HiltViewModel
class SpendViewModel @Inject constructor(
    private val dataTracker : SpendsTrackerDataSource
) : BaseViewModel() {
    private val _dataList : MutableLiveData<ArrayList<Spend>?> = MutableLiveData(null)
    val dataList : MutableLiveData<ArrayList<Spend>?> = this._dataList

    fun initData() {
        if (this._dataList.value == null) {
            getDbData()
        }
    }

    fun getDbData() {
        viewModelScope.launch {
            val list = dataTracker.getLast20Spends()
            if (list.isNotEmpty()) {
                setData(list as ArrayList<Spend>)
            }
        }
    }

    private fun setData(newList : ArrayList<Spend>) {
        viewModelScope.launch {
            if (_dataList.value == null) {
                _dataList.postValue(newList)
            } else {
                val currentList = dataList.value
                currentList?.addAll(newList)
                currentList?.let {
                    _dataList.postValue(it)
                }
            }
        }
    }

    fun addSpend(newSpend : Spend) {
        CoroutineScope(Dispatchers.IO).launch {
            this@SpendViewModel.dataTracker.addSpend(newSpend)
            _dataList.postValue(null)
            this@SpendViewModel.initData()
        }
    }

    override fun getLogName() = SpendViewModel::class.simpleName
}