package com.my.hiltapplication.scene

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.hiltapplication.R
import com.my.hiltapplication.adapter.SpendAdapter
import com.my.hiltapplication.base.BaseAlertActivity
import com.my.hiltapplication.databinding.ActivitySpendListBinding
import com.my.hiltapplication.room.Spend
import com.my.hiltapplication.util.Log
import com.my.hiltapplication.viewmodel.SpendViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

/**
 * Created by YourName on 2022/06/14.
 */
@AndroidEntryPoint
class SpendListActivity : BaseAlertActivity<ActivitySpendListBinding>(), View.OnClickListener {
    private val spendViewModel : SpendViewModel by viewModels()
    private val random = Random(100)
    private var spendAdapter = SpendAdapter()
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        this.contentBinding.btnAction.setOnClickListener(this)
        this.contentBinding.btnAction2.setOnClickListener(this)
        this.contentBinding.rvSpendList.adapter = this.spendAdapter
        this.contentBinding.rvSpendList.layoutManager = LinearLayoutManager(this)
        this.spendViewModel.dataList.observe(this, androidx.lifecycle.Observer {
            it?.let { list ->
                this@SpendListActivity.setList(list)
            }
        })
    }

    private fun setList(list : ArrayList<Spend>) {
        CoroutineScope(Dispatchers.Main).launch {
            val layoutManager = contentBinding.rvSpendList.layoutManager
            var firstPosition = 0
            var lastPosition = 0
            if (layoutManager is LinearLayoutManager) {
                firstPosition = layoutManager.findFirstVisibleItemPosition()
                lastPosition = layoutManager.findLastVisibleItemPosition()
            }
            Log.e(tag, "setList() first position: $firstPosition last position: $lastPosition")
            spendAdapter.setAll(list, firstPosition, lastPosition)
        }
    }

    override fun onResume() {
        super.onResume()
        this.spendViewModel.initData()
    }

    override fun getContentLayoutId() = R.layout.activity_spend_list

    override fun getLogName() = SpendListActivity::class.simpleName

    override fun onClick(p0 : View?) {
        when (p0?.id) {
            this.contentBinding.btnAction.id -> {
//                val newSpend = Spend(
//                    Date(),
//                    random.nextInt(),
//                    "aaa ${random.nextInt()}"
//                )
                val amount = this.contentBinding.etAmount.text.toString().toInt()
                val description = this.contentBinding.etDescription.text.toString()
                if (amount > 0 && description.isNotEmpty()) {
                    val newSpend = Spend(
                        Date(),
                        amount,
                        description
                    )
                    this.spendViewModel.addSpend(newSpend)
                } else {
                    showSnackbar("wrong Data")
                }
            }
            this.contentBinding.btnAction2.id -> {
                this.spendViewModel.removeSpends()
            }
        }
    }
}