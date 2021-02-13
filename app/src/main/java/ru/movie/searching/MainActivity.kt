package ru.movie.searching

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.movie.searching.ui.main.MoviesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.root_layout, MoviesFragment.newInstance())
                .commitNow()
        }
    }
}