package com.example.splash

import android.content.Intent
import android.provider.Settings.Secure.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

import com.example.splash.databinding.IlanCardViewBinding
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val adventList: ArrayList<Advent>) : RecyclerView.Adapter<RecyclerViewAdapter.AdventHolder>() {
private lateinit var db : AdventDatabase
private lateinit var adventDao: AdventDao
    class AdventHolder(val binding: IlanCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdventHolder {
        val binding = IlanCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdventHolder(binding)
    }

    override fun getItemCount(): Int {
        return adventList.size

    }

    override fun onBindViewHolder(holder: AdventHolder, position: Int) {
        holder.binding.ilanBaslik.text = adventList.get(position).Baslik
        holder.binding.ilanFiyat.text = adventList.get(position).Kirafiyati
        holder.binding.ilanKonum.text = adventList.get(position).cities
        holder.binding.ilanPeriyot.text = adventList.get(position).Periyot
        Picasso.get().load(adventList.get(position).downloadUrl).into(holder.binding.ilanGorseli)
        db = Room.databaseBuilder(holder.binding.root.context.applicationContext,AdventDatabase::class.java,"Advents")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        adventDao = db.adventDao()

        holder.binding.ilanCardView.setOnClickListener {
            val gecis = MainFragmentDirections.actionMainFragmentToIlanDetayFragment(position, adventList.get(position).Baslik,
                adventList.get(position).Kirafiyati.toInt(),adventList.get(position).Periyot,
                adventList.get(position).cities,adventList.get(position).districts,
                adventList.get(position).quarters,
                adventList.get(position).towns,adventList.get(position).Aciklama,adventList.get(position).downloadUrl.toUri())

            Navigation.findNavController(it).navigate(gecis)

        }
        holder.binding.favoriteButton.setOnClickListener {
            val advent = Advent(
                adventList.get(position).Aciklama,
                adventList.get(position).Baslik,
                adventList.get(position).Kirafiyati,
                adventList.get(position).Periyot,
                adventList.get(position).cities,
                adventList.get(position).districts,
                adventList.get(position).downloadUrl,
                adventList.get(position).quarters,
                adventList.get(position).towns

            )
            adventDao.insert(advent)

            Toast.makeText(holder.itemView.context, "Favorilere Eklendi", Toast.LENGTH_SHORT).show()



        }


    }




}

