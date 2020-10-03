package com.example.pruebasithec

import retrofit2.Call
import retrofit2.http.GET
const val API_ROUTE: String = "https://jsonplaceholder.typicode.com"

public interface GetService {
    @GET("/posts")
    fun getPost(): Call<List<Post?>?>?
    @GET("comments")
    fun getComentarios(): Call<List<Comentario?>?>?
    @GET("/users")
    fun getUsuarios(): Call<List<Usuario?>?>?
    @GET("posts")
    fun getPostByIdUser(): Call<List<Post?>?>?
}