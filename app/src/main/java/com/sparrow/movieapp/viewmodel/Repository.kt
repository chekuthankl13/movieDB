package com.sparrow.movieapp.viewmodel


import android.util.Log
import com.sparrow.movieapp.model.MovieDetail
import com.sparrow.movieapp.model.MoviesList
import com.sparrow.movieapp.utilis.RetrofitInstance
import retrofit2.Response

class Repository {

    suspend fun loadMovies(page: Int):
            Response<MoviesList> {
        Log.i("sample","*repo")
        var k =RetrofitInstance.api.loadMovies(page)
        Log.i("sample", "res -- $k")
        return k
    }

    suspend fun movieDetail(id:Int):Response<MovieDetail>{
        return  RetrofitInstance.api.movieDetail(id)

    }
}