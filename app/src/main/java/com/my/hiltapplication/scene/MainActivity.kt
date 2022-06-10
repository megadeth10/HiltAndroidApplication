package com.my.hiltapplication.scene

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.my.hiltapplication.R
import com.my.hiltapplication.Value
import com.my.hiltapplication.base.BaseActivity
import com.my.hiltapplication.databinding.ActivityMainBinding
import com.my.hiltapplication.scene.fragment.MainFragment
import com.my.hiltapplication.util.Log
import com.my.hiltapplication.viewmodel.DataViewModel
import com.my.hiltapplication.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {
    private var fragment: MainFragment? = null

    protected val userViewModel: UserViewModel by viewModels()
    protected val dataViewModel: DataViewModel by viewModels()
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        this.contentBinding.btnAction.setOnClickListener(this)
        this.contentBinding.btnAction2.setOnClickListener(this)
        this.contentBinding.btnAction3.setOnClickListener(this)
        this.userViewModel.setModelData(this.tag)
        this.userViewModel.modelData.observe(this, Observer {
            Log.e(tag, "나 불렀니 modelData: $it")
        })
        MainFragment().apply {
            fragment = this
            val tagName = "mainFragment"
            supportFragmentManager.beginTransaction()
                .add(R.id.layout_frame, this, tagName)
                .addToBackStack(tagName)
                .commit()
        }
        Log.e(tag, "native Value a: ${Value.a()}")
    }

    override fun onSaveInstanceState(outState : Bundle) {
        Log.e(tag, "onSaveInstanceState()")
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState : Bundle) {
        Log.e(tag, "onRestoreInstanceState()")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Log.e(tag, "onResume() modelData: ${this.userViewModel.modelData.value}")
        userViewModel.currentState()
        dataViewModel.currentState()
    }

    override fun getLogName() = MainActivity::class.simpleName
    override fun getContentLayoutId() = R.layout.activity_main

    override fun onClick(p0 : View?) {
        when (p0?.id) {
            this.contentBinding.btnAction.id -> {
                this.userViewModel.setUser("aaa")
                this.dataViewModel.setData("me")
                startActivity(Intent(this, SecondActivity::class.java))
            }
            this.contentBinding.btnAction2.id -> {
                this.userViewModel.setModelData("add text")
            }
            this.contentBinding.btnAction3.id -> {
                this.userViewModel.getVersion()
            }
        }
    }
}