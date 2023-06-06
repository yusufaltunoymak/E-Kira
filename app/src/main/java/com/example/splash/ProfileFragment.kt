package com.example.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.splash.databinding.FragmentProfileBinding
import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        val view2 = binding.root


        // Inflate the layout for this fragment
        var firstName = this@ProfileFragment.activity?.intent?.extras?.getString("firstName")
        val lastName = this@ProfileFragment.activity?.intent?.extras?.getString("lastName")
        val email = this@ProfileFragment.activity?.intent?.extras?.getString("email")
        val phoneNumber = this@ProfileFragment.activity?.intent?.extras?.getString("phone")
        val id = this@ProfileFragment.activity?.intent?.extras?.getString("id")
        println("firstName: $firstName")
        println("lastName: $lastName")
        println("email: $email")
        println("phoneNumber: $phoneNumber")
        if(lastName?.length!! > 0) {
            firstName = "$firstName $lastName"
        }
        binding.fullName.text = firstName
        binding.userEmail.text = email
        binding.userPhone.text = phoneNumber
        binding.textView7.text = id

        // rfc3339 parse
        val registerDate =  this@ProfileFragment.activity?.intent?.extras?.getString("registerDate")
        if(registerDate != null) {
            println(registerDate)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            val parsedDateTime = OffsetDateTime.parse(registerDate).toLocalDateTime()
            val formattedDateTime = parsedDateTime.format(formatter)
            binding.textView3.text = formattedDateTime
        }
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
                        val action = ProfileFragmentDirections.actionProfileFragmentToFavoriteFragment()
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





