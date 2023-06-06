package com.example.splash

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.splash.api.RestApiService
import com.example.splash.api.models.GetRentPeriod
import com.example.splash.api.models.RentalHouseList
import com.example.splash.databinding.IlanCardViewBinding
import com.squareup.picasso.Picasso

class RecyclerViewAdapterOwned(private var adventList: RentalHouseList) : RecyclerView.Adapter<RecyclerViewAdapterOwned.AdventHolder>() {
    inner class AdventHolder(val binding: IlanCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdventHolder {
        val binding = IlanCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdventHolder(binding)
    }

    override fun getItemCount(): Int {
        return adventList.results.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun UpdateData(newData: RentalHouseList) {
        adventList = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AdventHolder, position: Int) {
        holder.binding.ilanBaslik.text = adventList.results[position].title
        holder.binding.ilanFiyat.text = adventList.results[position].price.toString()
        val formatText = adventList.results[position].address.cityName + "\n" + adventList.results[position].address.districtName
        holder.binding.ilanKonum.text = formatText
        holder.binding.ilanPeriyot.text = GetRentPeriod(adventList.results[position].rentPeriod)

        val apiService = RestApiService(holder.binding.root.context)

        if(adventList.results[position].images.isNotEmpty()) {
            val firstItem = adventList.results[position].images.first()
            val lastItemInFirstItem = firstItem.last()
            Picasso.get().load(lastItemInFirstItem.url).into(holder.binding.ilanGorseli)
        } else {
            holder.binding.ilanGorseli.setImageResource(R.drawable.ekira)
        }

        if(adventList.results[position].favorite) {
            holder.binding.favoriteButton.setImageResource(R.drawable.ic_filled_heart)
        } else {
            holder.binding.favoriteButton.setImageResource(R.drawable.favorite)
        }

        holder.binding.ilanCardView.setOnClickListener {
            var imageUrl = ""
            if(adventList.results[position].images.isNotEmpty()) {
                val firstItem = adventList.results[position].images.first()
                val firstItemInFirstItem = firstItem.first()
                imageUrl = firstItemInFirstItem.url
            }

            val gecis = IlanlarimFragmentDirections.actionIlanlarimFragmentToIlanDetayFragment(
                adventList.results[position].id
            )

            Navigation.findNavController(it).navigate(gecis)
        }

        // Favori durumunu kontrol et ve ikonu güncelle
        holder.binding.favoriteButton.setOnClickListener {
            if(adventList.results[position].favorite) {
                apiService.unfavoriteRentalHouse(adventList.results[position].id) {
                    if(it?.isSuccess == true) {
                        holder.binding.favoriteButton.setImageResource(R.drawable.favorite)
                        adventList.results[position].favorite = false
                    } else {
                        Toast.makeText(holder.itemView.context, "Favorilerden kaldırılırken bir hata oluştu", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                apiService.favoriteRentalHouse(adventList.results[position].id) {
                    if(it?.isSuccess == true) {
                        holder.binding.favoriteButton.setImageResource(R.drawable.ic_filled_heart)
                        adventList.results[position].favorite = true
                    } else {
                        Toast.makeText(holder.itemView.context, "Favorilere eklenirken bir hata oluştu: ${it?.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
