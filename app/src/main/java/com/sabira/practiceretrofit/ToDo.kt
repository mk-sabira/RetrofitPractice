package com.sabira.practiceretrofit

data class ToDo(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)