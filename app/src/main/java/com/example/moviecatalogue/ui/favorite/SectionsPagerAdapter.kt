package com.example.moviecatalogue.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.moviecatalogue.R
import com.example.moviecatalogue.ui.favorite.movie.movie_now_playing.FavoriteMovieNowPlayingFragment
import com.example.moviecatalogue.ui.favorite.movie.movie_popular.FavoriteMoviePopularFragment
import com.example.moviecatalogue.ui.favorite.movie.movie_top_rated.FavoriteMovieTopRatedFragment
import com.example.moviecatalogue.ui.favorite.movie.movie_upcoming.FavoriteMovieUpcomingFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitle = intArrayOf(
        R.string.popular,
        R.string.top_rated,
        R.string.upcoming,
        R.string.now_playing)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment =
                FavoriteMoviePopularFragment()
            1 -> fragment =
                FavoriteMovieTopRatedFragment()
            2 -> fragment =
                FavoriteMovieUpcomingFragment()
            3 -> fragment =
                FavoriteMovieNowPlayingFragment()
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitle[position])
    }

    override fun getCount(): Int {
        return 4
    }
}
