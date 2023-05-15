package com.example.splash

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.Navigation
import com.example.splash.databinding.FragmentProfileBinding
import androidx.appcompat.app.AppCompatActivity


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainActivity = requireActivity() as MainActivity
        super.onViewCreated(view, savedInstanceState)

        binding.profiletoolbar.title = "E-Kira"
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.profiletoolbar)

        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.profil_duzenle, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.profili_guncelle -> {
                        val action = ProfileFragmentDirections.actionProfileFragmentToProfilDuzenleFragment()
                        Navigation.findNavController(binding.root).navigate(action)
                        true
                    }
                    R.id.favori_ilan -> {
                        val action = ProfileFragmentDirections.actionProfileFragmentToFavoriIlan()
                        Navigation.findNavController(binding.root).navigate(action)
                        true
                    }
                    R.id.bakiye -> {
                        val action = ProfileFragmentDirections.actionProfileFragmentToBakiyeFragment()
                        Navigation.findNavController(binding.root).navigate(action)
                        true
                    }
                    R.id.logout -> {
                        mainActivity.logout(view)
                        true
                    }
                }
                return false
            }
        }, viewLifecycleOwner)
    }

}





