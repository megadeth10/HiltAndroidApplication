package com.my.c2cmarketplace.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.hiltapplication.R
import com.my.hiltapplication.databinding.ActivityShortCutBinding
import com.my.hiltapplication.scene.MainActivity
import com.my.hiltapplication.scene.SecurePreferenceActivity
import com.my.hiltapplication.util.AppUtil
import com.my.hiltapplication.util.Log

class ShortCutActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var contentBinding: ActivityShortCutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_cut)
        this.contentBinding = DataBindingUtil.setContentView(this, R.layout.activity_short_cut)
        this.contentBinding.lifecycleOwner = this
        this.setView()
    }

    private fun setView() {
        this.contentBinding.list.adapter = ActivityAdapter().apply {
            setAll(getArray().toCollection(ArrayList()))
            onClickListener = this@ShortCutActivity
        }
        this.contentBinding.list.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    /**
     * 실행할 activity 옵션
     */
    private fun getArray(): Array<ActivityItem> {
        return arrayOf(
//            ActivityItem(
//                name = "Pass 본인인증",
//                callback = {
//                    AppUtil.goPassAuthActivity(this, PassAuthActivity.ComeFrom.ChangePhoneNumber)
//                }
//            ),
            ActivityItem(
                "첫화면",
                MainActivity::class.java,
            ),
            ActivityItem(
                "로컬 보안 저장소",
                SecurePreferenceActivity::class.java,
            ),
        )
    }

    override fun onClick(v: View?) {
        v?.let {
            val tag = v.tag
            tag?.let {
                if (it is ActivityItem) {
                    if (it.callback != null) {
                        it.callback?.invoke()
                    } else {
                        it.cls?.let { cls ->
                            val intent = AppUtil.makeIntent(this, cls, it.options)
                            if (it.requestCode == null) {
                                this.startActivity(intent)
                            } else {
                                this.startActivityForResult(intent, it.requestCode!!)
                            }
                        }
                    }

                }
            }
        }
    }

    class ActivityItem(
        var name: String = "",
        var cls: Class<*>? = null,
        var options: HashMap<String, Any>? = null,
        var requestCode: Int? = null,
        var callback: (() -> Unit)? = null
    )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e(
            "ShortCutActivity",
            "onActivityResult() requestCode: $requestCode resultCode: $resultCode"
        )
        super.onActivityResult(requestCode, resultCode, data)
    }
}