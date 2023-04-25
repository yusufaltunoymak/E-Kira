package com.example.splash

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.splash.databinding.FragmentIlanDetayBinding
import com.example.splash.databinding.FragmentKiralaBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.*


class KiralaFragment : Fragment() {
    private lateinit var binding : FragmentKiralaBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKiralaBinding.inflate(layoutInflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.editTextBaslangic.setOnClickListener {
            val calendar = Calendar.getInstance()
            val yil = calendar.get(Calendar.YEAR)
            val ay = calendar.get(Calendar.MONTH)
            val gun = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener { datePicker, y, a, g ->
                binding.editTextBaslangic.setText("$g/${a+1}/$y")
            },yil,ay,gun)
            datePicker.setTitle("Tarih Seçiniz")
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE,"AYARLA",datePicker)
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE,"İPTAL",datePicker)
            datePicker.show()

        }

        binding.editTextBitis.setOnClickListener {
            val calendar = Calendar.getInstance()
            val yil = calendar.get(Calendar.YEAR)
            val ay = calendar.get(Calendar.MONTH)
            val gun = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener { datePicker, y, a, g ->
                binding.editTextBitis.setText("$g/${a+1}/$y")
            },yil,ay,gun)
            datePicker.setTitle("Tarih Seçiniz")
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE,"AYARLA",datePicker)
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE,"İPTAL",datePicker)
            datePicker.show()

        }
        super.onViewCreated(view, savedInstanceState)

        val bundle: KiralaFragmentArgs by navArgs()
        val gelenkira = bundle.kirafiyati
        binding.fiyatKirala.text = gelenkira.toString()

        val gelenCities = bundle.cities
        binding.ilKirala.text = gelenCities

        val gelenBaslik = bundle.baslik
        binding.baslikKirala.text = gelenBaslik


        val gelenAciklama = bundle.aciklama
        binding.aciklamaKirala.text = gelenAciklama

        val gelenphoto = bundle.downloadurl
        Picasso.get().load(gelenphoto).resize(1800,1000).into(binding.ilanDetayimage)


    }
}