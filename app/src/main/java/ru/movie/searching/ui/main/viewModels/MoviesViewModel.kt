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

    private val topRatedMoviesRepository: MoviesRepository = MoviesRepository(application)
    private val popularMoviesRepository: MoviesRepository = MoviesRepository(application)
    private val nowPlayingMoviesRepository: MoviesRepository = MoviesRepository(application)
    private val upcomingMoviesRepository: MoviesRepository = MoviesRepository(application)

    var topRatedMovies = MutableLiveData<List<MovieModel>>()
    var popularMovies = MutableLiveData<List<MovieModel>>()
    var nowPlayingMovies = MutableLiveData<List<MovieModel>>()
    var upcomingMoviesMovies = MutableLiveData<List<MovieModel>>()

    init {
        receiveAllMovies()
    }

    fun receiveAllMovies() {
        viewModelScope.launch {
            val topRatedMoviesResponse = topRatedMoviesRepository.getTopRatedMoviesRemote()
            val popularMoviesResponse = popularMoviesRepository.getPopularMoviesRemote()
            val nowPlayingMoviesResponse = nowPlayingMoviesRepository.getNowPlayingMoviesRemote()
            val upcomingPlayingMoviesResponse = upcomingMoviesRepository.getUpcomingMoviesRemote()
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
                    upcomingMoviesMovies.postValue(upcomingPlayingMoviesResponse.body()?.results)
                }
            }
        }
    }

}