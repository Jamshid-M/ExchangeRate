package uz.jamshid.exchangerate.data.remote

import okhttp3.OkHttpClient

object RetrofitProvider {

    val retrofit = RetrofitClient.provideRetrofit(OkHttpClient())

    val service = RetrofitClient.provideNetworkService(retrofit)

}