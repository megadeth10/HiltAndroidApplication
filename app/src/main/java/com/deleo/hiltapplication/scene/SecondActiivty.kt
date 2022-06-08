package com.deleo.hiltapplication.scene

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deleo.hiltapplication.R
import com.deleo.hiltapplication.base.BaseActivity
import com.deleo.hiltapplication.databinding.ActivitySecondBinding
import com.deleo.hiltapplication.store.DataStore
import com.deleo.hiltapplication.store.UserStore
import com.deleo.hiltapplication.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SecondActivity : BaseActivity<ActivitySecondBinding>(), View.OnClickListener {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        this.contentBinding.btnAction.setOnClickListener(this)
        this.contentBinding.btnAction2.setOnClickListener(this)
        this.userViewModel.setModelData(this.tag)
        this.userViewModel.modelData.observe(this, Observer {
            Log.e(tag, "나 불렀니 modelData: $it")
        })
    }

    override fun onResume() {
        super.onResume()
        userViewModel.currentState()
        Log.e(tag, "onResume() modelData: ${this.userViewModel.modelData.value}")
    }

    override fun getLogName() = SecondActivity::class.simpleName
    override fun getContentLayoutId() = R.layout.activity_second

    override fun onClick(p0 : View?) {
        when (p0?.id) {
            this.contentBinding.btnAction.id -> {
                this.userViewModel.setUser(null)
//                this.dataStore.setData("you")
                finish()
            }
            this.contentBinding.btnAction2.id -> {
                this.userViewModel.setModelData("모델아 데이터 바뀌었다.")
            }
        }
    }
}