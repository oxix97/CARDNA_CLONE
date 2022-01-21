package org.cardna.data.remote.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ApiService {
    private val BASE_URL = "https://asia-northeast3-cardna-29f5b.cloudfunctions.net/api/"

    var gson = GsonBuilder().setLenient().create()

    private val Retrofit: Retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient(AppInterceptor()))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val authService: AuthService = Retrofit.create(AuthService::class.java)

    val cardService: CardService = Retrofit.create(CardService::class.java)
    val friendService: FriendService = Retrofit.create(FriendService::class.java)
    val insightService: InsightService = Retrofit.create(InsightService::class.java)
    val likeService: LikeService = Retrofit.create(LikeService::class.java)
    val myPageService: MyPageService = Retrofit.create(MyPageService::class.java)
}

private fun provideOkHttpClient(
    interceptor: AppInterceptor
): OkHttpClient = OkHttpClient.Builder()
    .run {
        addInterceptor(interceptor)
        build()
    }

class AppInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain)
        : okhttp3.Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MywiZW1haWwiOiJqaXdvb0BnbWFpbC5jb20iLCJuYW1lIjoi7KeA7JqwIiwiZmlyZWJhc2VJZCI6ImVpSk9qSHJMTWtVOEpiRTg4TUQxWGp6UWhYRzIiLCJpYXQiOjE2NDI2NjA2MjAsImV4cCI6MTY0NTI1MjYyMCwiaXNzIjoiY2FyZG5hIn0.ZW9Ot20SauGijUFjKv5BDb8Ix48RFONvk0Jn_Z0MQYg")
            .build()
        proceed(newRequest)
    }
}
