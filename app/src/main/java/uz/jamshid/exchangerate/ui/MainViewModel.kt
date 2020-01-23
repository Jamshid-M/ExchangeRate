package uz.jamshid.exchangerate.ui

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.jamshid.exchangerate.core.base.BaseViewModel
import uz.jamshid.exchangerate.data.AppDataManager
import uz.jamshid.exchangerate.data.model.Rates
import uz.jamshid.exchangerate.data.model.RatesResponse
import java.net.UnknownHostException

class MainViewModel: BaseViewModel() {

    val ratesLiveData = MutableLiveData<RatesResponse>()

    fun loadRates(inRate: String, outRate: String){
        disposable.add(
          AppDataManager.getExchangeRate(inRate, outRate)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe({
              ratesLiveData.value = it
          },{
              errorLiveData.value = it.message
          })
        )

    }

    fun loadFromCacheOrNet(inRate: String, outRate: String){
        disposable.add(
            AppDataManager.loadFromCacheOrNet(inRate, outRate).subscribe({
                ratesLiveData.value = RatesResponse(inRate, Rates(it))
            },{
                if (it is UnknownHostException)
                    errorLiveData.value = "Check your internet connection"
                else
                    errorLiveData.value = it.message
            })
        )
    }
}