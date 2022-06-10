package com.deleo.hiltapplication.scene

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deleo.hiltapplication.R
import com.deleo.hiltapplication.base.BaseActivity
import com.deleo.hiltapplication.databinding.ActivitySecondBinding
import com.deleo.hiltapplication.store.DataStore
import com.deleo.hiltapplication.store.UserStore
import com.deleo.hiltapplication.viewmodel.CategoryViewModel
import com.deleo.hiltapplication.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

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