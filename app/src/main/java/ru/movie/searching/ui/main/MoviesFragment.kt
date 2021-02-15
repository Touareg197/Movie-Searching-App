package ru.movie.searching.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.android.synthetic.main.movies_fragment.view.*
import ru.movie.searching.MainActivity
import ru.movie.searching.R
import ru.movie.searching.data.EmptyMoviesList
import ru.movie.searching.data.entity.MovieModel
import ru.movie.searching.ui.main.viewModels.CreateMoviesViewModel
import ru.movie.searching.ui.main.viewModels.CreateMoviesViewModelFactory


class MoviesFragment : Fragment(), MovieListAdapter.OnMovieListener {

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private lateinit var viewModel: CreateMoviesViewModel

    private lateinit var topRatedAdapter: MovieListAdapter
    private lateinit var popularAdapter: MovieListAdapter
    private lateinit var nowPlayingAdapter: MovieListAdapter
    private lateinit var upcomingAdapter: MovieListAdapter

    private lateinit var searchingAdapter: MovieListAdapter

    private lateinit var defaultMoviesList: List<MovieModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.movies_fragment, container, false)
        initRecyclerViews(view)
        viewModel = ViewModelProvider(
            this,
            CreateMoviesViewModelFactory(
                requireActivity().application
            )
        ).get(CreateMoviesViewModel::class.java)
        return view
    }

    private fun initRecyclerViews(view: View) {
        defaultMoviesList = EmptyMoviesList.getEmptyMoviesList()

        topRatedAdapter = MovieListAdapter(this)
        popularAdapter = MovieListAdapter(this)
        nowPlayingAdapter = MovieListAdapter(this)
        upcomingAdapter = MovieListAdapter(this)

        searchingAdapter = MovieListAdapter(this)

        val topRatedRecyclerView = view.rv_top_rated_movies
        topRatedRecyclerView.adapter = topRatedAdapter
        val popularRecyclerView = view.rv_popular_movies
        popularRecyclerView.adapter = popularAdapter
        val nowPlayingRecyclerView = view.rv_now_playing_movies
        nowPlayingRecyclerView.adapter = nowPlayingAdapter
        val upcomingRecyclerView = view.rv_upcoming_movies
        upcomingRecyclerView.adapter = upcomingAdapter

        val searchingRecyclerView = view.rv_searching_movies
        searchingRecyclerView.adapter = searchingAdapter

        topRatedAdapter.setMoviesData(defaultMoviesList)
        popularAdapter.setMoviesData(defaultMoviesList)
        nowPlayingAdapter.setMoviesData(defaultMoviesList)
        upcomingAdapter.setMoviesData(defaultMoviesList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.topRatedMovies.observe(
            viewLifecycleOwner,
            Observer { topRatedAdapter.setMoviesData(it) }
        )
        viewModel.popularMovies.observe(
            viewLifecycleOwner,
            Observer { popularAdapter.setMoviesData(it) }
        )
        viewModel.nowPlayingMovies.observe(
            viewLifecycleOwner,
            Observer { nowPlayingAdapter.setMoviesData(it) }
        )
        viewModel.upcomingMovies.observe(
            viewLifecycleOwner,
            Observer { upcomingAdapter.setMoviesData(it) }
        )
        viewModel.searchingMovies.observe(
            viewLifecycleOwner,
            Observer {
                searchingAdapter.setMoviesData(it)
            }
        )

        initMoreButtons()

        viewModel.getLocallySavedMovies()
        if ((activity as MainActivity?)!!.isNetworkConnected()) {
            viewModel.getMoviesFromServer()
        } else {
            view?.let {
                Snackbar.make(it, "Нет доступа в Интернет", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun initMoreButtons() {
//        search_view.setOnFocusChangeListener { v, hasFocus ->
//            println(hasFocus)
//        }

        more_top_rated_movies.setOnClickListener {
            view?.let {
                Snackbar.make(it, "Вы нажали Ещё у Рекомендуем", Snackbar.LENGTH_SHORT).show()
            }
        }
        more_popular_movies.setOnClickListener {
            view?.let {
                Snackbar.make(it, "Вы нажали Ещё у Популярные", Snackbar.LENGTH_SHORT).show()
            }
        }
        more_now_playing_movies.setOnClickListener {
            view?.let {
                Snackbar.make(it, "Вы нажали Ещё у Сегодня в кино", Snackbar.LENGTH_SHORT).show()
            }
        }
        more_upcoming_movies.setOnClickListener {
            view?.let {
                Snackbar.make(it, "Вы нажали Ещё у Скоро в прокате", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchingMovie: String?): Boolean {
                (activity as MainActivity?)!!.closeKeyBoard()
                return false
            }

            override fun onQueryTextChange(searchingMovie: String?): Boolean {
                if (searchingMovie.equals("")) {
                    search_view.clearFocus()
                    scroll_view.visibility = View.VISIBLE
                    founded_movies.visibility = View.GONE
                } else {
                    founded_movies.visibility = View.VISIBLE
                    scroll_view.visibility = View.GONE

                    if ((activity as MainActivity?)!!.isNetworkConnected()) {
                        viewModel.findMovieByTitle(searchingMovie)
                    } else {
                        view?.let {
                            Snackbar.make(it, "Нет доступа в Интернет", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                return false
            }
        })
    }

    override fun onMovieClick(movie: MovieModel) {
        openMovieDetails(movie)
    }

    private fun openMovieDetails(movie: MovieModel) {
        view?.let {
            Snackbar.make(it, "Вы выбрали фильм = " + movie.title, Snackbar.LENGTH_SHORT).show()
        }
    }

}