package com.example.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.splash.databinding.IlanCardViewBinding
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val adventList: ArrayList<Advent>) : RecyclerView.Adapter<RecyclerViewAdapter.AdventHolder>() {
    private lateinit var db: AdventDatabase
    private lateinit var adventDao: AdventDao


    inner class AdventHolder(val binding: IlanCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdventHolder {
        val binding = IlanCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdventHolder(binding)
    }

    override fun getItemCount(): Int {
        return adventList.size
    }

    override fun onBindViewHolder(holder: AdventHolder, position: Int) {
        holder.binding.ilanBaslik.text = adventList[position].Baslik
        holder.binding.ilanFiyat.text = adventList[position].Kirafiyati
        holder.binding.ilanKonum.text = adventList[position].cities
        holder.binding.ilanPeriyot.text = adventList[position].Periyot
        Picasso.get().load(adventList[position].downloadUrl).into(holder.binding.ilanGorseli)
        db = Room.databaseBuilder(
            holder.binding.root.context.applicationContext,
            AdventDatabase::class.java,
            "Advents"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        adventDao = db.adventDao()

        holder.binding.ilanCardView.setOnClickListener {
            val gecis = MainFragmentDirections.actionMainFragmentToIlanDetayFragment(
                position,
                adventList[position].Baslik,
                adventList[position].Kirafiyati.toInt(),
                adventList[position].Periyot,
                adventList[position].cities,
                adventList[position].districts,
                adventList[position].quarters,
                adventList[position].towns,
                adventList[position].Aciklama,
                adventList[position].downloadUrl.toUri()
            )

            Navigation.findNavController(it).navigate(gecis)
        }

        // Favori durumunu kontrol et ve ikonu güncelle
        val advent = adventList[position]
        val isFavorite = isAdventInFavorites(advent)
        if (isFavorite) {
            holder.binding.favoriteButton.setImageResource(R.drawable.ic_filled_heart)
        } else {
            holder.binding.favoriteButton.setImageResource(R.drawable.favorite)
        }

        holder.binding.favoriteButton.setOnClickListener {
            val isFavorite = isAdventInFavorites(advent)

            if (isFavorite) {
               // removeAdventFromFavorites(advent) // Favorilerden kaldır
               //   holder.binding.favoriteButton.setImageResource(R.drawable.favorite)
                Toast.makeText(holder.itemView.context, "Favorilerden Kaldırıldı", Toast.LENGTH_SHORT).show()
            } else {
                addAdventToFavorites(advent) // Favorilere ekle
                holder.binding.favoriteButton.setImageResource(R.drawable.ic_filled_heart)
                Toast.makeText(holder.itemView.context, "Favorilere Eklendi", Toast.LENGTH_SHORT).show()
            }

        }
    }



    private fun isAdventInFavorites(advent: Advent): Boolean {
        return adventDao.isAdventExist(advent.Baslik)
    }

    private fun addAdventToFavorites(advent: Advent) {
        adventDao.insert(advent)
    }

    private fun removeAdventFromFavorites(advent: Advent) {
        adventDao.delete(advent)
        adventList.remove(advent)
        notifyDataSetChanged()
    }
}
