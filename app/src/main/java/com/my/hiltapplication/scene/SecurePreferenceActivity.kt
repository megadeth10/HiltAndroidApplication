package com.my.hiltapplication.scene

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.my.hiltapplication.R
import com.my.hiltapplication.base.BaseActivity
import com.my.hiltapplication.databinding.ActivitySecurepreferenceBinding
import com.my.hiltapplication.util.Util
import com.my.hiltapplication.viewmodel.SecurePreferenceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecurePreferenceActivity : BaseActivity<ActivitySecurepreferenceBinding>(), View.OnClickListener {
    private val securePreferenceViewModel : SecurePreferenceViewModel by viewModels()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        this.contentBinding.btnAction.setOnClickListener(this)

        this.securePreferenceViewModel.data.observe(this, Observer {
            this.setText(it)
        })
        this.securePreferenceViewModel.progressState.observe(this, Observer {
            it?.let { newState ->
                setProgress(newState)
            }
        })
    }

    override fun onResume() {
        securePreferenceViewModel.initData()
        super.onResume()
    }

    private fun setText(newText : String?) {
        CoroutineScope(Dispatchers.Main).launch {
            this@SecurePreferenceActivity.contentBinding.etInput.setText(newText)
        }
    }

    private fun setProgress(newState : Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            Util.setVisible(contentBinding.flProgress, newState)
        }
    }

    override fun getContentLayoutId() = R.layout.activity_securepreference
    override fun getLogName() = SecurePreferenceActivity::class.simpleName
    override fun onClick(p0 : View?) {
        when (p0?.id) {
            contentBinding.btnAction.id -> {
                this.contentBinding.etInput.text.toString().let {
                    if (it.isNotEmpty()) {
                        this.securePreferenceViewModel.save(it)
                    }
                }
            }
        }
    }
}