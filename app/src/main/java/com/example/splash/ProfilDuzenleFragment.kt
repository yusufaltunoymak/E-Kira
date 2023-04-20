package com.example.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.splash.databinding.FragmentProfilDuzenleBinding
import com.example.splash.databinding.FragmentProfileBinding

class ProfilDuzenleFragment : Fragment() {

    private var _binding: FragmentProfilDuzenleBinding? = null
    private val binding get() = _binding!!
    private lateinit var veriAdaptoru : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil_duzenle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.Guncelle.setOnClickListener {
            val action = ProfilDuzenleFragmentDirections.duzenletoprofil()
            Navigation.findNavController(it).navigate(action)
        }
    }
}