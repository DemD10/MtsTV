package com.app.mtstv

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.app.domain.FilmDomain
import com.app.mtstv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SPAN_COUNT_LANDSCAPE = 5
        private const val SPAN_COUNT_PORTRAIT = 3
    }

    private lateinit var binding: ActivityMainBinding

    private val spanCount: Int
        get() {
            return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                SPAN_COUNT_LANDSCAPE
            } else {
                SPAN_COUNT_PORTRAIT
            }
        }

    private val adapter = FilmsAdapter()

    private val mainViewModel by viewModels<MainViewModel>(
        factoryProducer = { app().viewModelFactory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        mainViewModel.filmsLiveData.observe(this, {
            showFilms(it)
        })
    }

    private fun showFilms(films: List<FilmDomain>) {
        adapter.submitList(films)
    }

    private fun initViews() {
        val layoutManager = GridLayoutManager(this, spanCount)
        binding.filmsRecyclerView.adapter = adapter
        binding.filmsRecyclerView.layoutManager = layoutManager

        binding.filterSwitchCompat.setOnCheckedChangeListener { _, isChecked ->
            mainViewModel.onSwitchClicked(isChecked)
        }
    }
}