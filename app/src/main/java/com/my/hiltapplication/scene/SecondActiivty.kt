package com.my.hiltapplication.scene

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.my.hiltapplication.R
import com.my.hiltapplication.base.BaseActivity
import com.my.hiltapplication.databinding.ActivitySecondBinding
import com.my.hiltapplication.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecondActivity : BaseActivity<ActivitySecondBinding>(), View.OnClickListener {
    private val categoryViewModel : CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        this.contentBinding.btnAction.setOnClickListener(this)
        this.contentBinding.btnAction2.setOnClickListener(this)
        this.categoryViewModel.categoryData.observe(this, Observer {
            var text : String = ""
            if (it == null) {
                this.categoryViewModel.getCategoryData()
                text = "없음"
            } else if (it.size > 0) {
                text = "있음"
            }
            this.setText(text)
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun getLogName() = SecondActivity::class.simpleName
    override fun getContentLayoutId() = R.layout.activity_second

    private fun setText(text : String) {
        CoroutineScope(Dispatchers.Main).launch {
            contentBinding.tvDisplay.text = text
        }
    }

    override fun onClick(p0 : View?) {
        when (p0?.id) {
            this.contentBinding.btnAction.id -> {
            }
            this.contentBinding.btnAction2.id -> {
            }
        }
    }
}