//package com.sparrow.movieapp.utilis
//
//import okhttp3.Interceptor
//import okhttp3.Response
//
//class Myinterceptor : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request =
//            chain.request().newBuilder().addHeader("accept", "application/json")
//                .addHeader("Authorization", "Bearer ${Util.Token}").build()
//        return chain.proceed(request)
//    }
//}