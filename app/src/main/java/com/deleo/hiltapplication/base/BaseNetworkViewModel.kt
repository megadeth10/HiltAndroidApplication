package com.deleo.hiltapplication.base

import androidx.annotation.CallSuper
import com.deleo.hiltapplication.network.NetworkInterface
import io.reactivex.rxjava3.observers.DisposableSingleObserver

abstract class BaseNetworkViewModel: BaseViewModel(), NetworkInterface {
    private var networkDisposable = HashMap<String, DisposableSingleObserver<*>>(0)

    /**
     * 옵져버 추가
     */
    override fun addObserver(name:String, observer: DisposableSingleObserver<*>){
        this.networkDisposable[name] = observer
    }

    /**
     * 통신 종료
     */
    override fun cancelObserver(name:String){
        val observer = this.networkDisposable[name]
        observer?.let{
            it.dispose()
            this.networkDisposable.remove(name)
        }
    }

    /**
     * 모든 통신 종료
     */
    override fun cancelNetworkAll(){
        this.networkDisposable.forEach {
            it.value.dispose()
        }
    }

    @CallSuper
    override fun onCleared() {
        this.cancelNetworkAll()
        super.onCleared()
    }
}