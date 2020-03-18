package com.example.mytokdismovieapp.ui.home

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mytokdismovieapp.R
import com.example.mytokdismovieapp.ui.favorite.FavoriteActivity
import com.example.mytokdismovieapp.ui.movie.movie_now_playing.MovieNowPlayingFragment
import com.example.mytokdismovieapp.ui.movie.movie_popular.MoviePopularFragment
import com.example.mytokdismovieapp.ui.movie.movie_top_rated.MovieTopRatedFragment
import com.example.mytokdismovieapp.ui.movie.movie_upcoming.MovieUpcomingFragment
import com.example.mytokdismovieapp.utils.ConnectivityReceiver
import com.example.mytokdismovieapp.utils.UtilsConstant
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private var pageContent: Fragment? =
        MoviePopularFragment()

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menuPopular -> pageContent =
                    MoviePopularFragment()
                R.id.menuTopRated -> pageContent =
                    MovieTopRatedFragment()
                R.id.menuUpcoming -> pageContent =
                    MovieUpcomingFragment()
                R.id.menuNowPlaying -> pageContent =
                    MovieNowPlayingFragment()
            }

            pageContent?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.flameLayout, it, pageContent.toString())
                    .commit()
            }

            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        fab.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            pageContent?.let {
                supportFragmentManager.beginTransaction().replace(R.id.flameLayout, it)
                    .commit()
            }

        } else {
            pageContent =
                supportFragmentManager.getFragment(savedInstanceState, UtilsConstant.KEY_FRAGMENT)
            pageContent?.let {
                supportFragmentManager.beginTransaction().replace(R.id.flameLayout, it)
                    .commit()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            Toast.makeText(this, "You are Offline", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "You are Online", Toast.LENGTH_SHORT).show()
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        pageContent?.let {
            supportFragmentManager.putFragment(
                outState,
                UtilsConstant.KEY_FRAGMENT,
                it
            )
        }

        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
