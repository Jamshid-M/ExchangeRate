package uz.jamshid.exchangerate.data.remote

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.jamshid.exchangerate.core.factory.CurrencyDeserializer
import uz.jamshid.exchangerate.data.model.RatesResponse
import java.lang.reflect.Type
import android.content.ClipData.Item



class RetrofitClient {

    companion object {

        private const val BASE_URL = "https://api.exchangeratesapi.io"

        fun provideRetrofit(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(createGsonConverter())
                .build()
        }

        fun provideNetworkService(retrofit: Retrofit): NetworkService {
            return retrofit.create(NetworkService::class.java)
        }

        private fun createGsonConverter(): Converter.Factory{
            val type = object : TypeToken<RatesResponse>() {}.type

            val gsonBuilder = GsonBuilder()
            gsonBuilder.registerTypeAdapter(type, CurrencyDeserializer())
            val gson = gsonBuilder.create()
            return GsonConverterFactory.create(gson)
        }
    }
}