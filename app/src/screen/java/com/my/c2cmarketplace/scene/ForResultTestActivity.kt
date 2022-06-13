package com.my.c2cmarketplace.scene

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.my.hiltapplication.R
import com.my.hiltapplication.base.BaseActivity
import com.my.hiltapplication.databinding.ActivityForResultTestBinding
import com.my.hiltapplication.util.AppActivityResultCode

/**
 * Created by YourName on 2022/06/13.
 */
class ForResultTestActivity : BaseActivity<ActivityForResultTestBinding>(), View.OnClickListener {
    companion object {
        const val resultData = "test"
    }
    override fun getContentLayoutId() = R.layout.activity_for_result_test

    override fun getLogName() = ForResultTestActivity::class.simpleName

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(Activity.RESULT_CANCELED)
        this.contentBinding.btnAction.setOnClickListener(this)
        this.contentBinding.btnAction2.setOnClickListener(this)
    }

    override fun onClick(p0 : View?) {
        when(p0?.id) {
            this.contentBinding.btnAction.id -> {
                val result = Intent().apply {
                    putExtra(resultData, "aaaa")
                }
                setResult(AppActivityResultCode.ResultCodeBack, result)
                onBackPressed()
            }
            this.contentBinding.btnAction2.id -> {

            }
        }
    }
}