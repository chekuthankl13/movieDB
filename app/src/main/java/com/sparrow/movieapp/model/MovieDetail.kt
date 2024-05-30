package com.sparrow.movieapp.model

data class MovieDetail(
    val id: Int = 0,
    val budget: Int = 0,
    val origin_country: List<String> = emptyList(),
    val original_title: String = "",
    val overview: String = "",
    val poster_path: String = "",
    val release_date: String = "",
    val runtime: Int = 0,
    val vote_average: Number = 0.0,
    val vote_count: Int = 0,
    val genres: List<Genere> = emptyList(),
    val production_companies: List<ProductionCompany> = emptyList(),


    )


