package com.example.splash

import android.os.Bundle
import android.view.*
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splash.api.models.IlanModel
import com.example.splash.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var adapter : RecyclerViewAdapter
    private lateinit var binding : FragmentMainBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)

        return binding.root




    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        val card = view.findViewById<TextView>(R.id.submit)

        arguments?.let {
            val ilanPeriyodu = MainFragmentArgs.fromBundle(it).periyot
             card.text = ilanPeriyodu



        }

        val ilanListesi = listOf(
            IlanModel("Sefiller", "Victor Hugo",R.drawable.logo,"250 TL","Aylık"),
            IlanModel("Beyaz Diş", "Jack London",R.drawable.logo,"360 TL","Yıllık"),
            IlanModel("Suç ve Ceza", "Fyodor Dostoevsky",R.drawable.logo,"222 TL","Günlük")
        )




        adapter = RecyclerViewAdapter(ArrayList(ilanListesi), object: RecyclerViewAdapter.Listener {
            override fun onItemClick(ilanModel: IlanModel) {
                // Tıklanan ilan modeline göre yapılacak işlemler
            }
        })
        binding.recyclerView.adapter = adapter


    }



    }
