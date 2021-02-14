package ru.movie.searching.repositories

import android.app.Application
import retrofit2.Response
import ru.movie.searching.data.db.MovieSearchingDatabase
import ru.movie.searching.data.entity.*
import ru.movie.searching.data.network.MovieSearchingService
import ru.movie.searching.utils.Constants

class MoviesRepository(
        application: Application
) {

    /**
     * Сетевые методы.
     */
    suspend fun getNowPlayingMoviesRemote(): Response<CommonMovieResponse> {
        return MovieSearchingService.apiRest.getNowPlayingMovies(Constants.API_KEY, "ru-RU", 1)
    }

    suspend fun getPopularMoviesRemote(): Response<CommonMovieResponse> {
        return MovieSearchingService.apiRest.getPopularMovies(Constants.API_KEY, "ru-RU", 1)
    }

    suspend fun getTopRatedMoviesRemote(): Response<CommonMovieResponse> {
        return MovieSearchingService.apiRest.getTopRatedMovies(Constants.API_KEY, "ru-RU", 1)
    }

    suspend fun getUpcomingMoviesRemote(): Response<CommonMovieResponse> {
        return MovieSearchingService.apiRest.getUpcomingMovies(Constants.API_KEY, "ru-RU", 1)
    }

    suspend fun getSearchingMoviesRemote(searchingTitle: String?): Response<CommonMovieResponse> {
        return MovieSearchingService.apiRest.getSearchingMoviesRemote(Constants.API_KEY, "ru-RU", 1, searchingTitle)
    }

    /**
     * Локальные методы.
     */
    private val movieSearchingDao = MovieSearchingDatabase.getDatabase(application).movieSearchingDao()

    fun putTopRatedMoviesListLocal(topRatedMoviesList: List<TopRatedMovieData>) {
        movieSearchingDao.putTopRatedMovieLocal(topRatedMoviesList)
    }

    fun getTopRatedMoviesListLocal(): List<TopRatedMovieData> {
        return movieSearchingDao.getTopRatedMovieLocal()
    }

    fun dropTopRatedMoviesListLocal() {
        movieSearchingDao.dropTopRatedMovieTable()
    }

    fun putPopularMoviesListLocal(popularMoviesList: List<PopularMovieData>) {
        movieSearchingDao.putPopularMovieLocal(popularMoviesList)
    }

    fun getPopularMoviesListLocal(): List<PopularMovieData> {
        return movieSearchingDao.getPopularMovieLocal()
    }

    fun dropPopularMoviesListLocal() {
        movieSearchingDao.dropPopularMovieTable()
    }

    fun putNowPlayingMoviesListLocal(nowPlayingMoviesList: List<NowPlayingMovieData>) {
        movieSearchingDao.putNowPlayingMovieLocal(nowPlayingMoviesList)
    }

    fun getNowPlayingMoviesListLocal(): List<NowPlayingMovieData> {
        return movieSearchingDao.getNowPlayingMovieLocal()
    }

    fun dropNowPlayingMoviesListLocal() {
        movieSearchingDao.dropNowPlayingMovieTable()
    }

    fun putUpcomingMoviesListLocal(upcomingMoviesList: List<UpcomingMovieData>) {
        movieSearchingDao.putUpcomingMovieLocal(upcomingMoviesList)
    }

    fun getUpcomingMoviesListLocal(): List<UpcomingMovieData> {
        return movieSearchingDao.getUpcomingMovieLocal()
    }

    fun dropUpcomingMoviesListLocal() {
        movieSearchingDao.dropUpcomingMovieTable()
    }

}