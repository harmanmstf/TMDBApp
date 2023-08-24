package com.example.tmdbapp.network

import com.example.tmdbapp.model.MovieDetailResponse
import com.example.tmdbapp.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("popular")
    suspend fun getMovieList(@Header("Authorization") token : String): Response<MovieResponse>

    @GET("{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: String, @Header("Authorization") token : String ) : Response<MovieDetailResponse>

    @GET("top_rated")
    suspend fun getTopRatedList(@Header("Authorization") token : String): Response<MovieResponse>
}