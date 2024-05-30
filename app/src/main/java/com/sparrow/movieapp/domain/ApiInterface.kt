package com.sparrow.movieapp.domain

import com.sparrow.movieapp.model.MovieDetail
import com.sparrow.movieapp.model.MoviesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("3/discover/movie?api_key=ce1c8bfe4fdb9682f793a037a1fefa33")
    suspend fun loadMovies(
        @Query("page") page:Int
    ):Response<MoviesList>


    @GET("3/movie/{id}?api_key=ce1c8bfe4fdb9682f793a037a1fefa33")
    suspend fun movieDetail(
        @Path("id") id:Int
    ):Response<MovieDetail>
}