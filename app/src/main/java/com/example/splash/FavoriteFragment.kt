package com.example.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.splash.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var adventList : List<Advent>
    private lateinit var adventAdapter : FavoriteRecyclerAdapter
    private lateinit var db: AdventDatabase
    private lateinit var adventDao: AdventDao

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Room.databaseBuilder(requireContext().applicationContext, AdventDatabase::class.java, "Advents")
            .allowMainThreadQueries()
            .build()
        adventDao = db.adventDao()

        adventAdapter = FavoriteRecyclerAdapter(adventDao.getAll() as ArrayList<Advent>)
        binding.FavoriteRc.adapter = adventAdapter

        binding.FavoriteRc.layoutManager = LinearLayoutManager(requireContext())
        adventAdapter.notifyDataSetChanged()

        binding.favoribtn.setOnClickListener {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToProfileFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    interface AdventRemovedListener {
        fun onAdventRemoved(advent: Advent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        var _binding = null
    }


}
