package ru.movie.searching.ui.main.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.movie.searching.data.entity.*
import ru.movie.searching.repositories.MoviesRepository

class CreateMoviesViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val topRatedMoviesRepository: MoviesRepository = MoviesRepository(application)
    private val popularMoviesRepository: MoviesRepository = MoviesRepository(application)
    private val nowPlayingMoviesRepository: MoviesRepository = MoviesRepository(application)
    private val upcomingMoviesRepository: MoviesRepository = MoviesRepository(application)

    private val searchingMoviesRepository: MoviesRepository = MoviesRepository(application)

    var topRatedMoviesLiveData = MutableLiveData<List<MovieModel>>()
    var popularMoviesLiveData = MutableLiveData<List<MovieModel>>()
    var nowPlayingMoviesLiveData = MutableLiveData<List<MovieModel>>()
    var upcomingMoviesLiveData = MutableLiveData<List<MovieModel>>()

    var searchingMoviesLiveData = MutableLiveData<List<MovieModel>>()

    var refreshInProgressLiveData = MutableLiveData<Boolean>()

    init {

    }

    fun getMoviesFromServer(manualRefresh: Boolean) {
        if (manualRefresh) {
            refreshInProgressLiveData.postValue(true)
        }

        viewModelScope.launch {
            val topRatedMoviesResponse = topRatedMoviesRepository.getTopRatedMoviesRemote()
            val popularMoviesResponse = popularMoviesRepository.getPopularMoviesRemote()
            val nowPlayingMoviesResponse = nowPlayingMoviesRepository.getNowPlayingMoviesRemote()
            val upcomingPlayingMoviesResponse = upcomingMoviesRepository.getUpcomingMoviesRemote()
            launch(Dispatchers.IO) {
                if (topRatedMoviesResponse.isSuccessful) {
                    topRatedMoviesLiveData.postValue(topRatedMoviesResponse.body()?.results)
                    topRatedMoviesRepository.putTopRatedMoviesListLocal(
                        convertTopRatedMovieModelToMovieData(
                            topRatedMoviesResponse.body()?.results
                        )
                    )
                    refreshInProgressLiveData.postValue(false)
                }
                if (popularMoviesResponse.isSuccessful) {
                    popularMoviesLiveData.postValue(popularMoviesResponse.body()?.results)
                    popularMoviesRepository.putPopularMoviesListLocal(
                        convertPopularMovieModelToMovieData(
                            popularMoviesResponse.body()?.results
                        )
                    )
                    refreshInProgressLiveData.postValue(false)
                }
                if (nowPlayingMoviesResponse.isSuccessful) {
                    nowPlayingMoviesLiveData.postValue(nowPlayingMoviesResponse.body()?.results)
                    nowPlayingMoviesRepository.putNowPlayingMoviesListLocal(
                        convertNowPlayingMovieModelToMovieData(
                            nowPlayingMoviesResponse.body()?.results
                        )
                    )
                    refreshInProgressLiveData.postValue(false)
                }
                if (upcomingPlayingMoviesResponse.isSuccessful) {
                    upcomingMoviesLiveData.postValue(upcomingPlayingMoviesResponse.body()?.results)
                    upcomingMoviesRepository.putUpcomingMoviesListLocal(
                        convertUpcomingMovieModelToMovieData(
                            upcomingPlayingMoviesResponse.body()?.results
                        )
                    )
                    refreshInProgressLiveData.postValue(false)
                }
            }
        }
    }

    fun findMovieByTitle(searchingTitle: String?) {
        viewModelScope.launch {
            val searchingMoviesResponse =
                searchingMoviesRepository.getSearchingMoviesRemote(searchingTitle)
            launch(Dispatchers.IO) {
                if (searchingMoviesResponse.isSuccessful) {
                    searchingMoviesLiveData.postValue(searchingMoviesResponse.body()?.results)
                }
            }
        }
    }

    fun getLocallySavedMovies() {
        val moviesTopRatedList = mutableListOf<MovieModel>()
        val moviesPopularList = mutableListOf<MovieModel>()
        val moviesNowPlayingList = mutableListOf<MovieModel>()
        val moviesUpcomingList = mutableListOf<MovieModel>()

        moviesTopRatedList.addAll(convertTopRatedMovieDataToMovieModel(topRatedMoviesRepository.getTopRatedMoviesListLocal()))
        moviesPopularList.addAll(convertPopularMovieDataToMovieModel(popularMoviesRepository.getPopularMoviesListLocal()))
        moviesNowPlayingList.addAll(convertNowPlayingMovieDataToMovieModel(nowPlayingMoviesRepository.getNowPlayingMoviesListLocal()))
        moviesUpcomingList.addAll(convertUpcomingMovieDataToMovieModel(upcomingMoviesRepository.getUpcomingMoviesListLocal()))

        if (!moviesTopRatedList.isEmpty()) {
            topRatedMoviesLiveData.postValue(moviesTopRatedList)
        }
        if (!moviesPopularList.isEmpty()) {
            popularMoviesLiveData.postValue(moviesPopularList)
        }
        if (!moviesNowPlayingList.isEmpty()) {
            nowPlayingMoviesLiveData.postValue(moviesNowPlayingList)
        }
        if (!moviesUpcomingList.isEmpty()) {
            upcomingMoviesLiveData.postValue(moviesUpcomingList)
        }
    }

    /**
     * Конвертирование.
     */

    // TODO: Сделать универсальные методы конвертирования

    private fun convertTopRatedMovieModelToMovieData(listOfMovie: List<MovieModel>?): List<TopRatedMovieData> {
        val topRatedMovieData = mutableListOf<TopRatedMovieData>()

        if (listOfMovie != null) {
            for (item in listOfMovie) {
                val newItem = TopRatedMovieData(item.poster_path, item.title, item.vote_average)
                topRatedMovieData.add(newItem)
            }
        }
        return topRatedMovieData
    }

    private fun convertTopRatedMovieDataToMovieModel(listOfMovie: List<TopRatedMovieData>?): List<MovieModel> {
        val listMovieData = mutableListOf<MovieModel>()

        if (listOfMovie != null) {
            for (item in listOfMovie) {
                val newItem = MovieModel(item.poster_path, item.title, item.vote_average)
                listMovieData.add(newItem)
            }
        }
        return listMovieData
    }

    private fun convertPopularMovieModelToMovieData(listOfMovie: List<MovieModel>?): List<PopularMovieData> {
        val listMovieData = mutableListOf<PopularMovieData>()

        if (listOfMovie != null) {
            for (item in listOfMovie) {
                val newItem = PopularMovieData(item.poster_path, item.title, item.vote_average)
                listMovieData.add(newItem)
            }
        }
        return listMovieData
    }

    private fun convertPopularMovieDataToMovieModel(listOfMovie: List<PopularMovieData>?): List<MovieModel> {
        val listMovieData = mutableListOf<MovieModel>()

        if (listOfMovie != null) {
            for (item in listOfMovie) {
                val newItem = MovieModel(item.poster_path, item.title, item.vote_average)
                listMovieData.add(newItem)
            }
        }
        return listMovieData
    }

    private fun convertNowPlayingMovieModelToMovieData(listOfMovie: List<MovieModel>?): List<NowPlayingMovieData> {
        val listMovieData = mutableListOf<NowPlayingMovieData>()

        if (listOfMovie != null) {
            for (item in listOfMovie) {
                val newItem = NowPlayingMovieData(item.poster_path, item.title, item.vote_average)
                listMovieData.add(newItem)
            }
        }
        return listMovieData
    }

    private fun convertNowPlayingMovieDataToMovieModel(listOfMovie: List<NowPlayingMovieData>?): List<MovieModel> {
        val listMovieData = mutableListOf<MovieModel>()

        if (listOfMovie != null) {
            for (item in listOfMovie) {
                val newItem = MovieModel(item.poster_path, item.title, item.vote_average)
                listMovieData.add(newItem)
            }
        }
        return listMovieData
    }

    private fun convertUpcomingMovieModelToMovieData(listOfMovie: List<MovieModel>?): List<UpcomingMovieData> {
        val listMovieData = mutableListOf<UpcomingMovieData>()

        if (listOfMovie != null) {
            for (item in listOfMovie) {
                val newItem = UpcomingMovieData(item.poster_path, item.title, item.vote_average)
                listMovieData.add(newItem)
            }
        }
        return listMovieData
    }

    private fun convertUpcomingMovieDataToMovieModel(listOfMovie: List<UpcomingMovieData>?): List<MovieModel> {
        val listMovieData = mutableListOf<MovieModel>()

        if (listOfMovie != null) {
            for (item in listOfMovie) {
                val newItem = MovieModel(item.poster_path, item.title, item.vote_average)
                listMovieData.add(newItem)
            }
        }
        return listMovieData
    }

}