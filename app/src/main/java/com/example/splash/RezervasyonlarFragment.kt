package com.example.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.splash.databinding.FragmentProfileBinding
import com.example.splash.databinding.FragmentRezervasyonlarBinding


class RezervasyonlarFragment : Fragment() {
    private lateinit var binding : FragmentRezervasyonlarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRezervasyonlarBinding.inflate(layoutInflater,container,false)
        val view2 = binding.root


        return view2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainActivity = requireActivity() as MainActivity
        super.onViewCreated(view, savedInstanceState)

        binding.profiletoolbar.title = "E-Kira"
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.profiletoolbar)
    }
}





