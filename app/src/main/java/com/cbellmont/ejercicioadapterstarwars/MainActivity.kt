package com.cbellmont.ejercicioadapterstarwars

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbellmont.ejercicioadapterstarwars.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var adapter : FilmsAdapter = FilmsAdapter()
    private lateinit var model :MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        createRecyclerView()
        downloadAll()
    }

    private fun createRecyclerView() {
        binding.filmRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.filmRecyclerView.adapter = adapter
    }


    private fun downloadAll(){
        lifecycleScope.launch {
            val list = loadFilmInBackground()
            setAdapterOnMainThread(list)
        }
    }

    private suspend fun loadFilmInBackground() : MutableList<Film>{
        // El withContext(Dispatchers.IO) no es estrictamente necesario. Lo ponemos solo por seguridad.
        return withContext(Dispatchers.IO) {
            return@withContext model.getFilms()
        }
    }

    private suspend fun setAdapterOnMainThread(filmsList: MutableList<Film>) {
        withContext(Dispatchers.Main) {
            adapter.updateFilms(filmsList)
            binding.pbLoading.visibility = View.GONE
        }
    }
}