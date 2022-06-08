package com.deleo.hiltapplication.scene

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deleo.hiltapplication.R
import com.deleo.hiltapplication.base.BaseActivity
import com.deleo.hiltapplication.databinding.ActivityMainBinding
import com.deleo.hiltapplication.scene.fragment.MainFragment
import com.deleo.hiltapplication.store.DataStore
import com.deleo.hiltapplication.store.UserStore
import com.deleo.hiltapplication.viewmodel.DataViewModel
import com.deleo.hiltapplication.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {
    private var fragment: MainFragment? = null

    protected lateinit var userViewModel: UserViewModel
    protected lateinit var dataViewModel: DataViewModel
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]
        this.contentBinding.btnAction.setOnClickListener(this)
        this.contentBinding.btnAction2.setOnClickListener(this)
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
    }

    override fun onResume() {
        super.onResume()
        userViewModel.currentState()
        dataViewModel.currentState()
        Log.e(tag, "onResume() modelData: ${this.userViewModel.modelData.value}")
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
        }
    }
}