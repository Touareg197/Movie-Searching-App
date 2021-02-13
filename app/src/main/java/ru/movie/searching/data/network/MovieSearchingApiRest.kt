package ru.movie.searching.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.movie.searching.data.entity.CommonMovieResponse

interface MovieSearchingApiRest {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String,
                                  @Query("language") language: String,
                                  @Query("page") page: Int): Response<CommonMovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String,
                                 @Query("language") language: String,
                                 @Query("page") page: Int): Response<CommonMovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String,
                                    @Query("language") language: String,
                                    @Query("page") page: Int): Response<CommonMovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String,
                                  @Query("language") language: String,
                                  @Query("page") page: Int): Response<CommonMovieResponse>

}