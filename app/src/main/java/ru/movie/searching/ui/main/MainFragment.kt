package ru.movie.searching.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.view.*
import ru.movie.searching.R
import ru.movie.searching.data.DefaultMoviesList
import ru.movie.searching.data.entity.Movie

class MainFragment : Fragment(), MovieListAdapter.OnMovieListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var recommendedAdapter: MovieListAdapter
    private lateinit var popularAdapter: MovieListAdapter

    private lateinit var defaultMoviesList: List<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        initRecyclerViews(view)
        return view
    }

    private fun initRecyclerViews(view: View) {
        defaultMoviesList = DefaultMoviesList.getDefaultMoviesList()

        recommendedAdapter = MovieListAdapter(this)
        popularAdapter = MovieListAdapter(this)

        val recommendedRecyclerView = view.recyclerview_recommended_movies
        recommendedRecyclerView.adapter = recommendedAdapter

        val popularRecyclerView = view.recyclerview_popular_movies
        popularRecyclerView.adapter = popularAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recommendedAdapter.setMoviesData(defaultMoviesList)
        popularAdapter.setMoviesData(defaultMoviesList)
    }

    override fun onMovieClick(movie: Movie) {
        openMovieDetails(movie)
    }

    private fun openMovieDetails(movie: Movie) {
        view?.let {
            Snackbar.make(it, "Вы выбрали фильм = " + movie.name, Snackbar.LENGTH_SHORT).show()
        }
    }

}