package ru.movie.searching.data

import ru.movie.searching.data.entity.Movie

class DefaultMoviesList {

    companion object {
        var defaultMoviesList = ArrayList<Movie>()

        fun getDefaultMoviesList(): List<Movie> {
            createList()
            return defaultMoviesList
        }

        private fun createList() {
            defaultMoviesList.add(Movie("Один дома", 5))
            defaultMoviesList.add(Movie("Титаник", 4))
            defaultMoviesList.add(Movie("Начало", 3))
            defaultMoviesList.add(Movie("Довод", 2))
            defaultMoviesList.add(Movie("Зелёный слоник", 1))
        }
    }

}