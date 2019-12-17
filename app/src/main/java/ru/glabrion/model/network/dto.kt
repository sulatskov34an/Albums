package ru.glabrion.model.network

class BaseResponse<T>(
    val id: T?,
    val result: Boolean? = true
)

class AuthResponse(
    val id: String = "",
    val result: Boolean = true
)