package com.deleo.hiltapplication.scene.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deleo.hiltapplication.R
import com.deleo.hiltapplication.base.BaseFragment
import com.deleo.hiltapplication.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@WithFragmentBindings
@AndroidEntryPoint
class MainFragment: BaseFragment() {
    protected lateinit var userViewModel : UserViewModel
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userViewModel.modelData.observe(requireActivity(), Observer {
            Log.e(tag, "나 불렀니 modelData: $it")
        })
    }
    override fun getLogName() = MainFragment::class.simpleName ?: ""

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        Log.e(tag, "onCreateView()")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(tag, "onDestroyView()")
    }

    override fun getContentLayoutId() = R.layout.fragment_main
}