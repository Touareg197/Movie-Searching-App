package ru.movie.searching.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.movie.searching.data.entity.NowPlayingMovieData
import ru.movie.searching.data.entity.PopularMovieData
import ru.movie.searching.data.entity.TopRatedMovieData
import ru.movie.searching.data.entity.UpcomingMovieData

@Database(
    entities = [TopRatedMovieData::class, PopularMovieData::class, NowPlayingMovieData::class, UpcomingMovieData::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class MovieSearchingDatabase : RoomDatabase() {

    abstract fun movieSearchingDao(): MovieSearchingDao

    companion object {
        @Volatile
        private var INSTANCE: MovieSearchingDatabase? = null

        fun getDatabase(context: Context): MovieSearchingDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieSearchingDatabase::class.java,
                    "movie_searching_database.db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}