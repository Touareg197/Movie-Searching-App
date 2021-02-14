package ru.movie.searching.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PopularMovieData(
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null,
        val poster_path: String,
        val title: String,
        val vote_average: Double,
) {

    constructor(poster_path: String, title: String, vote_average: Double) :
            this(null, poster_path, title, vote_average)

    constructor () : this(null, "", "", 0.0)

    fun toMovieModel() = MovieModel(poster_path, title, vote_average)
}