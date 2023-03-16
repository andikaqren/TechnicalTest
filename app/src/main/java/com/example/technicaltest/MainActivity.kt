package com.example.technicaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.technicaltest.databinding.ActivityMainBinding
import com.example.technicaltest.home.favourite.FragmentFavourite
import com.example.technicaltest.home.movie.MoviesFragment
import com.example.technicaltest.home.tv.TvFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        showFragment(MoviesFragment())
        binding.navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.movieFragment -> {
                    showFragment(MoviesFragment())
                    true
                }
                R.id.tvFragment->{
                    showFragment(TvFragment())
                    true
                }
                R.id.favouriteFragment->{
                    showFragment(FragmentFavourite())
                    true
                }
                else -> false
            }

        }
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }
}