package com.example.kopinusantara.model

data class LoginResponse(
    val status: String,
    val message: String,
    val data: User,
    val token: String
)
