package com.my.hiltapplication.scene.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.my.hiltapplication.R
import com.my.hiltapplication.base.BaseFragment
import com.my.hiltapplication.databinding.FragmentMainBinding
import com.my.hiltapplication.noupdate.response.VersionResponse
import com.my.hiltapplication.util.Log
import com.my.hiltapplication.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@WithFragmentBindings
@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>() {
    private val versionDataExtraName = "versionDataExtraName"
    protected val userViewModel : UserViewModel by activityViewModels()
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.getSerializable(versionDataExtraName)?.let {
            val response = it as VersionResponse
            userViewModel.setVersion(response)
        }
    }

    override fun onSaveInstanceState(outState : Bundle) {
        Log.e(tag, "onSaveInstanceState()")
        userViewModel.versionData.value?.let{
            outState.putSerializable(versionDataExtraName, it)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState : Bundle?) {
        Log.e(tag, "onViewStateRestored()")
//        savedInstanceState?.getSerializable(versionDataExtraName)?.let {
//            val response = it as VersionResponse
//            userViewModel.setVersion(response)
//        }
        super.onViewStateRestored(savedInstanceState)
    }

    override fun getLogName() = MainFragment::class.simpleName ?: ""

    override fun afterViewDataBinding() {
        userViewModel.versionData.observe(viewLifecycleOwner, Observer {
            val text = it?.toString() ?: "없음"
            setDisplay(text)
        })
        userViewModel.modelData.observe(requireActivity(), Observer {
            Log.e(tag, "userViewModel 나 불렀니 modelData: $it")
        })
    }

    private fun setDisplay(text:String?){
        CoroutineScope(Dispatchers.Main).launch {
            contentBinding?.tvDisplay?.text = text
        }
    }

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