package ru.movie.searching.data

import ru.movie.searching.data.entity.MovieModel

class DefaultMoviesList {

    companion object {
        var defaultMoviesList = ArrayList<MovieModel>()

        fun getDefaultMoviesList(): List<MovieModel> {
            createList()
            return defaultMoviesList
        }

        private fun createList() {
            defaultMoviesList.add(MovieModel("empty", "Один дома", 5.0))
            defaultMoviesList.add(MovieModel("empty", "Титаник", 4.0))
            defaultMoviesList.add(MovieModel("empty", "Начало", 3.0))
            defaultMoviesList.add(MovieModel("empty", "Довод", 2.0))
            defaultMoviesList.add(MovieModel("empty", "Зелёный слоник", 1.0))
        }
    }

}