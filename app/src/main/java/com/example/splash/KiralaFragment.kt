package com.example.splash

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.splash.databinding.FragmentIlanDetayBinding
import com.example.splash.databinding.FragmentKiralaBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.*
import java.util.concurrent.TimeUnit


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

            val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { datePicker, y, a, g ->
                val startDate = Calendar.getInstance()
                startDate.set(y, a, g)

                val endDatePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { datePicker, yy, aa, gg ->
                    val endDate = Calendar.getInstance()
                    endDate.set(yy, aa, gg)

                    val ilanTuru = binding.gelenPeriyotTextView.text // İlan türünü burada belirleyin

                    val difference = TimeUnit.MILLISECONDS.toDays(endDate.timeInMillis - startDate.timeInMillis)

                    when (ilanTuru) {
                        "Günlük" -> {
                            if (difference != 1L) {
                                Toast.makeText(requireContext(), "Lütfen sadece 1 günlük tarih seçin.", Toast.LENGTH_SHORT).show()
                                return@OnDateSetListener
                            }
                        }
                        "Haftalık" -> {
                            if (difference != 7L) {
                                Toast.makeText(requireContext(), "Lütfen sadece 1 haftalık tarih seçin.", Toast.LENGTH_SHORT).show()
                                return@OnDateSetListener
                            }
                        }
                        "Aylık" -> {
                            val daysInMonth = endDate.getActualMaximum(Calendar.DAY_OF_MONTH)
                            if (difference != daysInMonth.toLong()) {
                                Toast.makeText(requireContext(), "Lütfen sadece 1 aylık tarih seçin.", Toast.LENGTH_SHORT).show()
                                return@OnDateSetListener
                            }
                        }
                        "Yıllık" -> {
                            val startYear = startDate.get(Calendar.YEAR)
                            val endYear = endDate.get(Calendar.YEAR)
                            val isStartLeapYear = isLeapYear(startYear)
                            val isEndLeapYear = isLeapYear(endYear)
                            val daysInYear = if (isStartLeapYear || isEndLeapYear) 366 else 365
                            if (difference != daysInYear.toLong()) {
                                Toast.makeText(requireContext(), "Lütfen sadece 1 yıllık tarih seçin.", Toast.LENGTH_SHORT).show()
                                return@OnDateSetListener
                            }
                        }

                    }

                    // Seçilen başlangıç ve bitiş tarihlerini kullanarak ilanı kaydet veya başka bir işlem yap
                    binding.editTextBaslangic.setText("$g/${a + 1}/$y")
                    binding.editTextBitis.setText("$gg/${aa + 1}/$yy")


                    val startDateText = String.format("%02d/%02d/%d (%s)", g, a + 1, y, getDayOfWeek(startDate, Locale("tr", "TR")))
                    val endDateText = String.format("%02d/%02d/%d (%s)", gg, aa + 1, yy, getDayOfWeek(endDate, Locale("tr", "TR")))
                    binding.baslangicTextView.setText(startDateText)
                    binding.bitisTextView.setText(endDateText)

                }, yil, ay, gun)

                endDatePicker.datePicker.minDate = startDate.timeInMillis // Başlangıç tarihinden önceki tarihler seçilemez
                endDatePicker.setTitle("Tarih Seçiniz")
                endDatePicker.setButton(DialogInterface.BUTTON_POSITIVE, "AYARLA", endDatePicker)
                endDatePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "İPTAL", endDatePicker)
                endDatePicker.show()

            }, yil, ay, gun)

            datePicker.setTitle("Tarih Seçiniz")
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "AYARLA", datePicker)
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "İPTAL", datePicker)
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

        val gelenPeriyot = bundle.periyot
        binding.gelenPeriyotTextView.text = gelenPeriyot

        val gelenphoto = bundle.downloadurl
        Picasso.get().load(gelenphoto).resize(1800,1000).into(binding.ilanDetayimage)

        binding.backbtn.setOnClickListener {
            requireActivity().onBackPressed()
        }


    }

    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }

    private fun getDayOfWeek(calendar: Calendar, locale: Locale): String {
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val displayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale)
        return "$displayName"
    }
}