package com.example.splash

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.splash.api.models.IlanModel
import com.example.splash.databinding.IlanCardViewBinding

class RecyclerViewAdapter(private val ilanListesi: ArrayList<IlanModel>, private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(ilanModel: IlanModel) {
        }
    }
    class RowHolder(private val binding : IlanCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ilanModel: IlanModel, listener: Listener) {
            binding.root.setOnClickListener {
                listener.onItemClick(ilanModel)
            }
            binding.ilanBaslik.text = ilanModel.ilanBaslik
            binding.ilanFiyat.text = ilanModel.ilanFiyat
            binding.ilanGorseli.setImageBitmap(BitmapFactory.decodeResource(binding.root.resources, ilanModel.ilanGorseli))
            binding.ilanKonum.text = ilanModel.ilanKonum
            binding.ilanPeriyot.text = ilanModel.ilanPeriyot



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = IlanCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val ilanModel = ilanListesi[position]
        holder.bind(ilanModel,listener)
    }

    override fun getItemCount(): Int {
        return ilanListesi.size
    }
}