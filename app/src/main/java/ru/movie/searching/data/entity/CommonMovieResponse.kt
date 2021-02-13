package ru.movie.searching.data.entity

data class CommonMovieResponse(
        val page: Int,
        val results: List<MovieModel>,
        val dates: DatesModel,
        val total_pages: Int,
        val total_results: Int
)