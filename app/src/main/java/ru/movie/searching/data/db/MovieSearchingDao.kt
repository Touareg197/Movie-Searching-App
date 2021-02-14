package ru.movie.searching.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.movie.searching.data.entity.NowPlayingMovieData
import ru.movie.searching.data.entity.PopularMovieData
import ru.movie.searching.data.entity.TopRatedMovieData
import ru.movie.searching.data.entity.UpcomingMovieData

@Dao
interface MovieSearchingDao {

    /**
     * Рекомендуемые фильмы.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putTopRatedMovieLocal(listTopRatedMovie: List<TopRatedMovieData>)

    @Query("SELECT * FROM TopRatedMovieData")
    fun getTopRatedMovieLocal(): List<TopRatedMovieData>

    @Query("DELETE FROM TopRatedMovieData")
    fun dropTopRatedMovieTable()

    /**
     * Популярные фильмы.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putPopularMovieLocal(listPopularMovie: List<PopularMovieData>)

    @Query("SELECT * FROM PopularMovieData")
    fun getPopularMovieLocal(): List<PopularMovieData>

    @Query("DELETE FROM PopularMovieData")
    fun dropPopularMovieTable()

    /**
     * Сегодня в кино.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putNowPlayingMovieLocal(listPopularMovie: List<NowPlayingMovieData>)

    @Query("SELECT * FROM NowPlayingMovieData")
    fun getNowPlayingMovieLocal(): List<NowPlayingMovieData>

    @Query("DELETE FROM NowPlayingMovieData")
    fun dropNowPlayingMovieTable()

    /**
     * Скоро в прокате.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putUpcomingMovieLocal(listPopularMovie: List<UpcomingMovieData>)

    @Query("SELECT * FROM UpcomingMovieData")
    fun getUpcomingMovieLocal(): List<UpcomingMovieData>

    @Query("DELETE FROM UpcomingMovieData")
    fun dropUpcomingMovieTable()

}