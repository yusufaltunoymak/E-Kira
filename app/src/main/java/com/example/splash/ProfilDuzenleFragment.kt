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

    private var changePhone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilDuzenleBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        val firstName = this@ProfilDuzenleFragment.activity?.intent?.extras?.getString("firstName").toString()
        val lastName = this@ProfilDuzenleFragment.activity?.intent?.extras?.getString("lastName").toString()
        val currentPhone = this@ProfilDuzenleFragment.activity?.intent?.extras?.getString("phone").toString()

        binding.EditTextName.setText(firstName)
        binding.EditTextSurname.setText(lastName)
        if(currentPhone.isNotEmpty()) {
            binding.editTextPhone.setText(currentPhone.replace("+90", "0"))
            binding.editTextPhone.isEnabled = false
            binding.KodGonderButton.text = "Değiştir"
            changePhone = true
        }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = this@ProfilDuzenleFragment.context?.let { RestApiService(it) }

        binding.backbtn.setOnClickListener {
            val action = ProfilDuzenleFragmentDirections.actionProfilDuzenleFragmentToProfileFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.VazgecButton.setOnClickListener {
            changePhone = true
            binding.editTextPhone.isEnabled = false
            val currentPhone = this@ProfilDuzenleFragment.activity?.intent?.extras?.getString("phone").toString()
            if(currentPhone.isNotEmpty()) {
                binding.editTextPhone.setText(currentPhone.replace("+90", "0"))
                binding.editTextPhone.isEnabled = false
                binding.KodGonderButton.text = "Değiştir"
                changePhone = true
            }
            binding.VazgecButton.visibility = View.GONE
        }

        binding.KodGonderButton.setOnClickListener {
            if(changePhone) {
                changePhone = false
                binding.editTextPhone.isEnabled = true
                binding.editTextPhone.setText("")
                binding.KodGonderButton.text = "Kodu Gönder"
                binding.editTextPhone.requestFocus()
                binding.VazgecButton.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val phone = binding.editTextPhone.text

            var data = SetPhonePost(phone = phone.toString())
            apiService?.setPhone(data) {
                println(it)
                if (it?.isSuccess == true) {
                    binding.DogrulaButton.visibility = View.VISIBLE
                    binding.editTextNumber.visibility = View.VISIBLE
                    binding.editTextNumber.requestFocus()
                    Toast.makeText(requireContext(), "Telefon numaranıza doğrulama kodu gönderildi", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), it?.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.DogrulaButton.setOnClickListener {
            val phone = binding.editTextPhone.text.toString()
            var code = binding.editTextNumber.text.toString()

            binding.editTextNumber.clearFocus()
            var data = VerifyPhonePost(phone = phone, code = code)
            apiService?.verifyPhone(data) {
                if (it?.isSuccess == true) {
                    binding.DogrulaButton.visibility = View.GONE
                    binding.editTextNumber.visibility = View.GONE
                    binding.editTextPhone.setText("")
                    // hide keyboard
                    Toast.makeText(requireContext(), "Telefon numarası doğrulandı", Toast.LENGTH_LONG).show()
                } else {
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
                if (it?.isSuccess == true) {
                    val profileInfo = it.result as SetProfile
                    this@ProfilDuzenleFragment.activity?.intent?.extras?.putString("firstName", profileInfo.first_name)
                    this@ProfilDuzenleFragment.activity?.intent?.extras?.putString("lastName", profileInfo.last_name)
                    Toast.makeText(requireContext(), "Profil bilgileriniz güncellendi", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), it?.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}