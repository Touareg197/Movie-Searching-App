package ru.movie.searching.data

import ru.movie.searching.data.entity.MovieModel

class EmptyMoviesList {

    companion object {
        var emptyMoviesList = ArrayList<MovieModel>()

        fun getEmptyMoviesList(): List<MovieModel> {
            createList()
            return emptyMoviesList
        }

        private fun createList() {
            emptyMoviesList.add(MovieModel("empty", "empty", 1.0))
            emptyMoviesList.add(MovieModel("empty", "empty", 1.0))
            emptyMoviesList.add(MovieModel("empty", "empty", 1.0))
        }

    }
}