package ru.movie.searching.ui.main.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.movie.searching.data.entity.MovieModel
import ru.movie.searching.repositories.MoviesRepository

class CreateMoviesViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val topRatedMoviesRepository: MoviesRepository = MoviesRepository()
    private val popularMoviesRepository: MoviesRepository = MoviesRepository()
    private val nowPlayingMoviesRepository: MoviesRepository = MoviesRepository()
    private val upcomingMoviesRepository: MoviesRepository = MoviesRepository()
    ////////////
    private val searchingMoviesRepository: MoviesRepository = MoviesRepository()
    ////////////

    var topRatedMovies = MutableLiveData<List<MovieModel>>()
    var popularMovies = MutableLiveData<List<MovieModel>>()
    var nowPlayingMovies = MutableLiveData<List<MovieModel>>()
    var upcomingMovies = MutableLiveData<List<MovieModel>>()
    ////////////
    var searchingMovies = MutableLiveData<List<MovieModel>>()
    ////////////

    init {
        receiveAllMovies()
    }

    fun receiveAllMovies() {
        viewModelScope.launch {
            val topRatedMoviesResponse = topRatedMoviesRepository.getTopRatedMoviesRemote()
            val popularMoviesResponse = popularMoviesRepository.getPopularMoviesRemote()
            val nowPlayingMoviesResponse = nowPlayingMoviesRepository.getNowPlayingMoviesRemote()
            val upcomingPlayingMoviesResponse = upcomingMoviesRepository.getUpcomingMoviesRemote()
            ////////////
            val searchingMoviesResponse = searchingMoviesRepository.getSearchingMoviesRemote("Avengers")
            ////////////
            launch(Dispatchers.IO) {
                if (topRatedMoviesResponse.isSuccessful) {
                    topRatedMovies.postValue(topRatedMoviesResponse.body()?.results)
                }
                if (popularMoviesResponse.isSuccessful) {
                    popularMovies.postValue(popularMoviesResponse.body()?.results)
                }
                if (nowPlayingMoviesResponse.isSuccessful) {
                    nowPlayingMovies.postValue(nowPlayingMoviesResponse.body()?.results)
                }
                if (upcomingPlayingMoviesResponse.isSuccessful) {
                    upcomingMovies.postValue(upcomingPlayingMoviesResponse.body()?.results)
                }
                ////////////
                if (searchingMoviesResponse.isSuccessful) {
                    searchingMovies.postValue(searchingMoviesResponse.body()?.results)
                }
                ////////////
            }
        }
    }

    suspend fun findMovieByTitle(searchingTitle : String?) {
        val searchingMoviesRepository = searchingMoviesRepository.getSearchingMoviesRemote(searchingTitle)
    }

}