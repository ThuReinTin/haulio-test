package tin.thurein.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tin.thurein.data.common.Constant
import java.util.concurrent.TimeUnit

object ApiBuilder {
    private var retrofit: Retrofit? = null

    init {
        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
//            .addInterceptor(WebserviceInterceptor()).build()
        retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> getService(_class: Class<T>): T {
        return retrofit!!.create(_class)
    }

}