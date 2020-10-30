package pallav.bakcet.schoolmanagement.network


import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pallav.bakcet.schoolmanagement.BuildConfig
import pallav.bakcet.schoolmanagement.BuildConfig.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private val baseURL = BASE_URL
    private var retrofit: Retrofit? = null

    private var logging = HttpLoggingInterceptor()
    private fun getHttpLogClient(context: Context): OkHttpClient {
        if (BuildConfig.API_DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY


        }
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(TokenRenewInterceptor(context))

        return clientBuilder.build()
    }

    fun getApi(context: Context): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpLogClient(context))
                .build()
        }
        return retrofit
    }


}