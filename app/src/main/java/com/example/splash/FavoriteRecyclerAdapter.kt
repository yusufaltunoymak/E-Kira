package com.example.splash

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.splash.api.RestApiService
import com.example.splash.api.models.GetRentPeriod
import com.example.splash.api.models.RentalHouseList
import com.example.splash.databinding.FavoriteCardViewBinding
import com.example.splash.databinding.FragmentFavoriteBinding
import com.squareup.picasso.Picasso


class FavoriteRecyclerAdapter(private var adventList: RentalHouseList, private var fragmentBinding: FragmentFavoriteBinding) : RecyclerView.Adapter<FavoriteRecyclerAdapter.AdventHolder>() {
    private lateinit var data: RentalHouseList

    class AdventHolder (val binding : FavoriteCardViewBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdventHolder {
        val binding = FavoriteCardViewBinding.inflate(LayoutInflater.from(parent.context))
        return AdventHolder(binding)
    }

    override fun getItemCount(): Int {
        return adventList.results.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun UpdateData(newData: RentalHouseList) {
        adventList = newData
        notifyDataSetChanged()
        checkData()
    }

    fun checkData() {
        if(adventList.pagination.fullCount == 0 && adventList.fetched) {
            fragmentBinding.notFound.visibility = View.VISIBLE
            fragmentBinding.FavoriteRc.visibility = View.GONE
        } else {
            fragmentBinding.notFound.visibility = View.GONE
            fragmentBinding.FavoriteRc.visibility = View.VISIBLE
        }
    }

    override fun onBindViewHolder(holder: AdventHolder, position: Int) {
        val apiService = RestApiService(holder.binding.root.context)

        holder.binding.ilanCardView.visibility = View.VISIBLE
        holder.binding.ilanBaslik.text = adventList.results[position].title
        holder.binding.ilanFiyat.text = adventList.results[position].price.toString()
        val formatText = adventList.results[position].address.cityName + "\n" + adventList.results[position].address.districtName
        holder.binding.ilanKonum.text = formatText
        holder.binding.ilanPeriyot.text = GetRentPeriod(adventList.results[position].rentPeriod)

        if(adventList.results[position].images.isNotEmpty()) {
            val firstItem = adventList.results[position].images.first()
            val lastItemInFirstItem = firstItem.last()
            Picasso.get().load(lastItemInFirstItem.url).into(holder.binding.ilanGorseli)
        } else {
            holder.binding.ilanGorseli.setImageResource(R.drawable.ekira)
        }

        holder.binding.DeleteFavorite.setOnClickListener{
            apiService.unfavoriteRentalHouse(adventList.results[position].id) {
                if(it?.isSuccess == true) {
                    adventList.results.removeAt(position)
                    adventList.pagination.fullCount -= 1
                    adventList.pagination.totalCount -= 1
                    UpdateData(adventList)
                    Toast.makeText(holder.itemView.context,"Favorilerden Silindi",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(holder.itemView.context, "Favorilerden kaldırılırken bir hata oluştu", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
