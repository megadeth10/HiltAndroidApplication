package com.my.hiltapplication.scene

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.my.hiltapplication.R
import com.my.hiltapplication.base.BaseActivity
import com.my.hiltapplication.databinding.ActivitySaveStateHandlerBinding
import com.my.hiltapplication.viewmodel.SaveStateHandlerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by YourName on 2022/07/07.
 */
class SaveStateHandlerActivity:BaseActivity<ActivitySaveStateHandlerBinding>(), View.OnClickListener {
    private val viewModel: SaveStateHandlerViewModel by viewModels()
    override fun getContentLayoutId()  = R.layout.activity_save_state_handler

    override fun getLogName() = SaveStateHandlerActivity::class.simpleName

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        this.contentBinding.btnAction.setOnClickListener(this)
    }

    companion object {
        val ParamName = "name"
    }

    override fun onClick(v : View?) {
        when(v?.id) {
            this.contentBinding.btnAction.id -> {
                viewModel
            }
        }
    }
}