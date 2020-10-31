package pallav.bakcet.schoolmanagement.network
import android.content.Context
import android.os.Build
import android.util.Base64
import androidx.annotation.NonNull
import android.util.Log

import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.Response
import pallav.bakcet.schoolmanagement.BuildConfig
import pallav.bakcet.schoolmanagement.utils.token


import java.io.IOException

internal class
TokenRenewInterceptor(private val mContext: Context) : Interceptor, okhttp3.Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original=chain.request()
        val request=original.newBuilder()
                .method(original.method(),original.body())
                .addHeader("Authorization",mContext.token())



//        if(BuildConfig.API_DEBUG){
//
//            val credentials = "dev-server:Pass@123"
//            val auth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
//            request.addHeader("Authorization", auth)
//
//        }
        val r=request.build()
        Log.d(TAG, r.headers().toString())
        val response = chain.proceed(r)

        val newToken = response.header("token")


        if (newToken != null) {
            mContext.token(newToken)
        }

        return response
    }

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: okhttp3.Interceptor.Chain): okhttp3.Response {
        val original=chain.request()
        val request=original.newBuilder()
                .method(original.method,original.body)
                .addHeader("Authorization",mContext.token())


//        if(BuildConfig.API_DEBUG){
//
//            val credentials = "dev-server:Pass@123"
//            val auth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
//            request.addHeader("Authorization", auth)
//
//        }

        val r=request.build()
        Log.d(TAG, r.headers.toString())
        val response = chain.proceed(r)

        val newToken = response.header("token")

        if (newToken != null) {
            mContext.token(newToken)
        }

        return response
    }

    companion object {
        private val TAG = "TokenRenewInterceptor"
    }
}