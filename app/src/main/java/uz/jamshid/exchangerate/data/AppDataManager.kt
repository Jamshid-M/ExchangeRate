package uz.jamshid.exchangerate.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.jamshid.exchangerate.data.local.SharedPrefManager
import uz.jamshid.exchangerate.data.model.RatesResponse
import uz.jamshid.exchangerate.data.remote.RetrofitProvider

object AppDataManager : DataManager {

    private val service = RetrofitProvider.service

    override fun saveInputRate(rate: String) {
        SharedPrefManager.writePrefs(SharedPrefManager.INPUT_RATE, rate)
    }

    override fun saveInputValue(value: Float) {
        SharedPrefManager.writePrefs(SharedPrefManager.INPUT_VALUE, value)
    }

    override fun saveOutputRate(rate: String) {
        SharedPrefManager.writePrefs(SharedPrefManager.OUTPUT_RATE, rate)
    }

    override fun saveOutputValue(value: Float) {
        SharedPrefManager.writePrefs(SharedPrefManager.OUTPUT_VALUE, value)
    }

    override fun readInputRate(): Single<String> {
        return Single.just(SharedPrefManager.readString(SharedPrefManager.INPUT_RATE))
    }

    override fun readInputValue(): Single<Float> {
        return Single.just(SharedPrefManager.readFloat(SharedPrefManager.INPUT_VALUE))
    }

    override fun readOutputRate(): Single<String> {
        return Single.just(SharedPrefManager.readString(SharedPrefManager.OUTPUT_RATE))
    }

    override fun readOutputValue(): Single<Float> {
        return Single.just(SharedPrefManager.readFloat(SharedPrefManager.OUTPUT_VALUE))
    }


    override fun getExchangeRate(inCurrency: String, outCurrency: String): Single<RatesResponse> {

        return service.getExchangeRate(inCurrency, outCurrency)
    }

    fun loadFromCacheOrNet(inCurrency: String, outCurrency: String): Observable<Float> {

        val local = readOutputValue().subscribeOn(Schedulers.computation())
        val remote = service.getExchangeRate(inCurrency, outCurrency)
            .map {rate ->
                Completable.create {
                    saveOutputValue(rate.rates.rate)
                    it.onComplete()
                }.subscribeOn(Schedulers.computation()).subscribe()

                rate.rates.rate
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        return Observable.concat(local.toObservable(), remote.toObservable()).observeOn(AndroidSchedulers.mainThread())
    }

}