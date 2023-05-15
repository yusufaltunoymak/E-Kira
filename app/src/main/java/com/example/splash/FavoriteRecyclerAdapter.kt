package com.example.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.splash.databinding.FavoriteCardViewBinding
import com.squareup.picasso.Picasso


class FavoriteRecyclerAdapter(private val adventList:ArrayList<Advent>) : RecyclerView.Adapter<FavoriteRecyclerAdapter.AdventHolder>() {
    private lateinit var db : AdventDatabase
    private lateinit var adventDao: AdventDao


    class AdventHolder (val binding : FavoriteCardViewBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdventHolder {
        val binding = FavoriteCardViewBinding.inflate(LayoutInflater.from(parent.context))
        return AdventHolder(binding)
    }

    override fun getItemCount(): Int {
        return if(adventList.isEmpty()) 1 else adventList.size
    }

    override fun onBindViewHolder(holder: AdventHolder, position: Int) {
        if(adventList.isEmpty()) {
            holder.binding.noFavoriteText.text = "Favori ilanınız bulunmamaktadır"
            holder.binding.noFavoriteText.visibility = View.VISIBLE
            holder.binding.ilanCardView.visibility = View.GONE
        } else {
            holder.binding.noFavoriteText.visibility = View.GONE
            holder.binding.ilanCardView.visibility = View.VISIBLE
            holder.binding.ilanBaslik.text = adventList[position].Baslik
            holder.binding.ilanFiyat.text = adventList[position].Kirafiyati
            holder.binding.ilanKonum.text = adventList[position].cities
            holder.binding.ilanPeriyot.text = adventList[position].Periyot
            Picasso.get().load(adventList[position].downloadUrl).into(holder.binding.ilanGorseli)
            db = Room.databaseBuilder(holder.binding.root.context, AdventDatabase::class.java, "Advents")
                .allowMainThreadQueries()
                .build()
            adventDao = db.adventDao()

            holder.binding.DeleteFavorite.setOnClickListener{
                adventDao.delete(adventList[position])
                adventList.removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(holder.itemView.context,"Favorilerden Silindi",Toast.LENGTH_SHORT).show()
            }
        }
    }

}
