package ru.movie.searching.repositories

import retrofit2.Response
import ru.movie.searching.data.entity.CommonMovieResponse
import ru.movie.searching.data.network.MovieSearchingService
import ru.movie.searching.utils.Constants

class MoviesRepository {

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

}