package uz.jamshid.exchangerate.data

import io.reactivex.Single
import uz.jamshid.exchangerate.data.remote.NetworkService

interface DataManager: NetworkService {

    fun saveInputRate(rate: String)
    fun saveInputValue(value: Float)
    fun saveOutputRate(rate: String)
    fun saveOutputValue(value: Float)

    fun readInputRate(): Single<String>
    fun readInputValue(): Single<Float>
    fun readOutputRate(): Single<String>
    fun readOutputValue(): Single<Float>
}