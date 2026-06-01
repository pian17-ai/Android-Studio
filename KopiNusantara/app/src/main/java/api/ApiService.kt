package com.example.kopinusantara.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.kopinusantara.model.LoginResponse
import com.example.kopinusantara.model.OrderResponse
import com.example.kopinusantara.model.ProductResponse
import com.example.kopinusantara.model.ProductDetailResponse
import com.example.kopinusantara.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Header

interface ApiService {
    @POST("auth/login")
    fun login(
        @Body request: Map<String, String>
    ): Call<LoginResponse>

    @POST("auth/register")
    fun register(
        @Body request: Map<String, String>
    ): Call<LoginResponse>

    @GET("products")
    fun getProducts(): Call<ProductResponse>

    @GET("products/{id}")
    fun getProductDetail(@Path("id") id: Int): Call<ProductDetailResponse>

    @POST("orders")
    fun createOrder(
        @Header("Authorization") token: String,
        @Body body: Map<String, Int>
    ): Call<Any>

    @GET("orders")
    fun getOrders(
        @Header("Authorization") token: String
    ): Call<OrderResponse>

    @GET("users")
    fun getUser(
        @Header("Authorization") token: String
    ): Call<UserResponse>

    @POST("auth/logout")
    fun logout(
        @Header("Authorization") token: String
    ): Call<Any>
}
