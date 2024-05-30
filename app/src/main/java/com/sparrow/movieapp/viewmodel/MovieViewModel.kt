package com.sparrow.movieapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparrow.movieapp.model.Results
import com.sparrow.movieapp.model.MovieDetail
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repo = Repository()

    var state by mutableStateOf(State())
    var id by mutableIntStateOf(0)
    var loading by mutableStateOf(false)



    init {
        loadPage()
    }



    fun loadPage() {
        viewModelScope.launch {
            state = state.copy(isLoading = false)
            Log.i("page", state.page.toString())
            try {
                var response = repo.loadMovies(state.page)
                if (response.isSuccessful) {

                    state = state.copy(
                        isLoading = false,
                        error = "",
                        page = state.page + 1,
                        isEnd = true,
                        movies = state.movies + response.body()!!.results
                    )

                } else {

                    when (response.code()) {
                        400 -> state = state.copy(
                            isLoading = false,
                            isEnd = true,
                            error = "reached end of the movie List !!"
                        )

                        401 -> state = state.copy(isLoading = false, error = "Invalid API Key !!")
                    }


                }
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.message!!)

            }
        }
    }

    fun getDetails() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {

                var response = repo.movieDetail(id)
                if (response.isSuccessful) {

                    state = state.copy(detail = response.body()!!, isLoading = false)
                } else {
                    when (response.code()) {
                        400 -> state = state.copy(
                            isLoading = false,
                            isEnd = true,
                            error = "server error"
                        )

                        401 -> state = state.copy(isLoading = false, error = "Invalid API Key !!")
                    }
                }
            } catch (e: Exception) {

                state = state.copy(error = e.message!!)
            }
        }
    }


}

data class State(
    val movies: List<Results> = emptyList(),
    val page: Int = 1,
    var detail: MovieDetail = MovieDetail(),
    var error: String = "",
    var isLoading: Boolean = false,
    var isEnd: Boolean = false,

    )