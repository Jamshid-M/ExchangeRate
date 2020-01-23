package uz.jamshid.exchangerate.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import uz.jamshid.exchangerate.data.model.RatesResponse

interface NetworkService {

    @GET("latest")
    fun getExchangeRate(@Query("base") inCurrency: String,
                        @Query("symbols") outCurrency: String): Single<RatesResponse>
}