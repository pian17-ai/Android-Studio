package com.example.kopinusantara.model

data class UserResponse(
    val status: String,
    val message: String,
    val data: User
)

data class User(
    val id: Int,
    val full_name: String,
    val email: String,
    val created_at: String
)