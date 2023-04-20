package com.example.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.splash.api.RestApiService
import com.example.splash.api.SessionManager
import com.example.splash.databinding.FragmentAddBinding
import com.example.splash.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        val view2 = binding.root
        // Inflate the layout for this fragment


        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val nameTextView = view.findViewById(R.id.full_name) as TextView
        val emailTextView = view.findViewById(R.id.user_email) as TextView
        var firstName = this@ProfileFragment.activity?.intent?.extras?.getString("firstName")
        val lastName = this@ProfileFragment.activity?.intent?.extras?.getString("lastName")
        val email = this@ProfileFragment.activity?.intent?.extras?.getString("email")
        if(lastName?.length!! > 0) {
            firstName = "$firstName $lastName"
        }
        nameTextView.text = firstName
        emailTextView.text = email

        return view2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profilduzenle.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToProfilDuzenleFragment()
            Navigation.findNavController(it).navigate(action)
        }




    fun profile_image() {

    }
}
}

