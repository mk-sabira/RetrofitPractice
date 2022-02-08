package com.sabira.practiceretrofit

import retrofit2.Response
import retrofit2.http.GET

interface ToDoApi {

    @GET("/todos")
    suspend fun getToDos(): Response<List<ToDo>>
}