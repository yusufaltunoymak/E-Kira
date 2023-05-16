package com.example.splash

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.splash.api.RestApiService
import com.example.splash.api.SessionManager
import com.example.splash.api.models.SetPhonePost
import com.example.splash.api.models.SetProfile
import com.example.splash.api.models.VerifyPhonePost
import com.example.splash.databinding.FragmentProfilDuzenleBinding

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

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = this@ProfilDuzenleFragment.context?.let { RestApiService(it) }

        binding.backbtn.setOnClickListener {
            val action = ProfilDuzenleFragmentDirections.actionProfilDuzenleFragmentToProfileFragment()
            Navigation.findNavController(it).navigate(action)
        }

        apiService?.getCities() {  }

        binding.KodGonderButton.setOnClickListener {
            val phone = binding.editTextPhone.text

            var data = SetPhonePost(phone = phone.toString())
            apiService?.setPhone(data) {
                println(it)
                if (it?.isSuccess == true) {
                    binding.DogrulaButton.visibility = View.VISIBLE
                    binding.editTextNumber.visibility = View.VISIBLE

                    println(it?.result)
                } else {
                    println(it)
                    println(it?.error)
                   // Toast.makeText(this@ProfilDuzenleFragment, it?.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.DogrulaButton.setOnClickListener {
            val fragment = ProfilDuzenleFragment()

            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()

            transaction.replace(R.id.profilDuzenleFragment, fragment)
            transaction.addToBackStack(null)

            transaction.commit()
            val phone = binding.editTextPhone.text.toString()
            var code = binding.editTextNumber.text.toString()


            var data = VerifyPhonePost(phone = phone, code = code)
            apiService?.verifyPhone(data) {
                println(it)
                if (it?.isSuccess == true) {
                    println(it?.result)
                    Toast.makeText(requireContext(), it?.isSuccess.toString(), Toast.LENGTH_LONG).show()

                } else {
                    println(it?.error)

                Toast.makeText(requireContext(), it?.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.GuncelleButton.setOnClickListener {
            val fragment = ProfilDuzenleFragment()

            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()

            transaction.replace(R.id.profilDuzenleFragment, fragment)
            transaction.addToBackStack(null)
            var firstName = binding.EditTextName.text.toString()
            var lastName = binding.EditTextSurname.text.toString()

            val data = SetProfile(first_name = firstName, last_name = lastName)
            apiService?.setProfile(data) {
                println(it)
                if (it?.isSuccess == true) {
                    this@ProfilDuzenleFragment.activity?.intent?.extras?.putString("firstName", it?.result?.first_name)
                    this@ProfilDuzenleFragment.activity?.intent?.extras?.putString("lastName", it?.result?.last_name)
                    println(it?.result)
                } else {
                    println(it?.error)
                    // Toast.makeText(this@ProfilDuzenleFragment, it?.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}