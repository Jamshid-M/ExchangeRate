package uz.jamshid.exchangerate.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel() {

    val disposable = CompositeDisposable()
    val errorLiveData = MutableLiveData<String>()

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}