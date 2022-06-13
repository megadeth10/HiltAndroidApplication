package com.my.hiltapplication.scene

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.collection.arrayMapOf
import androidx.core.content.FileProvider
import com.my.hiltapplication.R
import com.my.hiltapplication.base.BaseActivity
import com.my.hiltapplication.base.BaseAlertActivity
import com.my.hiltapplication.databinding.ActivityForResultTestBinding
import com.my.hiltapplication.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

/**
 * Created by YourName on 2022/06/13.
 */
class ForResultTestActivity : BaseAlertActivity<ActivityForResultTestBinding>(), View.OnClickListener {
    companion object {
        const val resultData = "test"
    }

    private var imageUri : Uri? = null
    private val activityResultPickPicker = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        Log.e(
            tag,
            "registerForActivityResult() resultCode: ${it}"
        )
        if (it) {
            imageUri?.let {
                this.contentBinding.ivImage.setImageURI(imageUri)
            }
        }
    }

    private var activityResultCameraPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        Log.e(
            tag,
            "activityResultCameraPermission() resultCode: ${it}"
        )
        var falseItem : String? = null
        for (item in it) {
            if (!item.value) {
                falseItem = item.key
                break
            }
        }
        if (falseItem == null) {
            this.callCamera()
        } else {
            showSnackbar("권환을 확인해 주세요", R.string.btn_confirm, View.OnClickListener {
                AppUtil.gotoSettingForPermission(this)
            })
        }
    }

    private fun callCamera() {
        val file = ImageFileUtil.createImageFile(this)
        val photoFile : File? = try {
            file
        } catch (ex : IOException) {
            null
        }
        // Continue only if the File was successfully created
        photoFile?.also {
            imageUri = FileProvider.getUriForFile(
                this,
                ConstValue.PROVIDER,
                it
            )
            activityResultPickPicker.launch(imageUri)
        }
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
        when (p0?.id) {
            this.contentBinding.btnAction.id -> {
                val result = Intent().apply {
                    putExtra(resultData, "aaaa")
                }
                setResult(AppActivityResultCode.ResultCodeBack, result)
                onBackPressed()
            }
            this.contentBinding.btnAction2.id -> {
                activityResultCameraPermission.launch(
                    arrayOf<String>(
                        Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
            }
        }
    }
}