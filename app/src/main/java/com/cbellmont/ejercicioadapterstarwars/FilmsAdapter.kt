package com.cbellmont.ejercicioadapterstarwars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cbellmont.ejercicioadapterstarwars.databinding.FilmLayoutBinding
import com.squareup.picasso.Picasso

class FilmsAdapter : RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder>() {

    lateinit var binding : FilmLayoutBinding

    class FilmsViewHolder(root: View, var tvNombre: TextView, var tvIntro: TextView, var ivDerecha: ImageView, var ivIzquierda: ImageView) : RecyclerView.ViewHolder(root)

    private var films = mutableListOf<Film>()

    fun updateFilms(filmsList: MutableList<Film>) {
        this.films = filmsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        binding = FilmLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return FilmsViewHolder(binding.root, binding.tvName, binding.tvIntro, binding.ivDerecha, binding.ivIzquierda)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.tvIntro.text = films[position].intro
        holder.tvNombre.text = films[position].name
        if (position % 6 > 2){
            holder.ivIzquierda.visibility = View.GONE
            holder.ivDerecha.visibility = View.VISIBLE
            Picasso.get().load(films[position].getUrlImage()).into(holder.ivDerecha)
        }  else {
            holder.ivIzquierda.visibility = View.VISIBLE
            holder.ivDerecha.visibility = View.GONE
            Picasso.get().load(films[position].getUrlImage()).into(holder.ivIzquierda)
        }
    }
}
