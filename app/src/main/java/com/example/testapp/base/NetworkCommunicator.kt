package com.example.testapp.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.testapp.BuildConfig
import com.example.testapp.TestApp
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit.SECONDS
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

private const val CONTENT_TYPE = "Content-Type"
private const val APPLICATION_JSON = "application/json"
private const val SSL = "SSL"

class NetworkCommunicator {

    private var okHttpClient: OkHttpClient
    private var mInternetConnectionListener: InternetConnectionListener? = null

    fun setInternetConnectionListener(listener: InternetConnectionListener) {
        mInternetConnectionListener = listener
    }

    companion object {
        val instance = NetworkCommunicator()
    }

    init {
        if (BuildConfig.DEBUG) {

            val headerInterceptor = Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                    .build()
                chain.proceed(newRequest)
            }
            okHttpClient = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(object : NetworkConnectionInterceptor() {
                    override val isInternetAvailable: Boolean
                        get() = isInternetAvailable()

                    override fun onInternetUnavailable() {
                        mInternetConnectionListener?.onInternetUnavailable()
                    }
                })
                .sslSocketFactory(getSSLSocketFactory(getTrustManager()), getTrustManager()[0])
                .connectTimeout(60, SECONDS)
                .readTimeout(60, SECONDS)
                .writeTimeout(60, SECONDS)
                .build()
        } else {
            val headerInterceptor = Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                    .build()
                chain.proceed(newRequest)
            }
            okHttpClient = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(object : NetworkConnectionInterceptor() {
                    override val isInternetAvailable: Boolean
                        get() = isInternetAvailable()

                    override fun onInternetUnavailable() {
                        mInternetConnectionListener?.onInternetUnavailable()
                    }
                })
                .sslSocketFactory(getSSLSocketFactory(getTrustManager()), getTrustManager()[0])
                .connectTimeout(60, SECONDS)
                .readTimeout(60, SECONDS)
                .writeTimeout(60, SECONDS)
                .build()
        }
    }


    /**
     * @param clazz Api interface reference
     * @param <T>   Wild card object type
     * @return Type of Api provider class to be return
    </T> */
    fun <T> createApiClient(clazz: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(clazz)
    }

    // Create a trust manager that does not validate certificate chains
    private fun getTrustManager(): Array<X509TrustManager> {
        return arrayOf(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
    }

    private fun getSSLSocketFactory(trustManager: Array<X509TrustManager>): SSLSocketFactory {
        val sslContext = SSLContext.getInstance(SSL)
        sslContext.init(null, trustManager, SecureRandom())
        return sslContext.socketFactory
    }

    private fun isInternetAvailable(): Boolean {
        val context = TestApp.applicationContext()
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}