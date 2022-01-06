package com.example.apptestediego

import com.example.apptestediego.model.AlbumsResponseModel
import com.example.apptestediego.model.PostsResponseModel
import com.example.apptestediego.model.TodosResponseModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class NetworkUtils {
    companion object {
        /** Retorna uma Instância do Client Retrofit para Requisições
         * @param path Caminho Principal da API
         */
        fun getRetrofitInstance(path : String) : Retrofit {
            return Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    interface EndpointPosts {
        @GET("posts")
        fun getPosts() : Call<List<PostsResponseModel>>
    }
    interface EndpointAlbums {
        @GET("albums")
        fun getAlbums() : Call<List<AlbumsResponseModel>>
    }
    interface EndpointTodos {
        @GET("todos")
        fun getTodos() : Call<List<TodosResponseModel>>
    }
}