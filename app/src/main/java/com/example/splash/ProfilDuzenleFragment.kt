package com.example.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.splash.databinding.FragmentProfilDuzenleBinding
import com.example.splash.databinding.FragmentProfileBinding

class ProfilDuzenleFragment : Fragment() {
    private lateinit var binding : FragmentProfilDuzenleBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilDuzenleBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
       // return inflater.inflate(R.layout.fragment_profil_duzenle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backbtn.setOnClickListener {
            val action = ProfilDuzenleFragmentDirections.actionProfilDuzenleFragmentToMainFragment("s")
            Navigation.findNavController(it).navigate(action)
        }





    }


}