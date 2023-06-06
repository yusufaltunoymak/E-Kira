package com.example.splash

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.splash.api.RestApiService
import com.example.splash.api.models.CreateReservationPost
import com.example.splash.api.models.PaymentMakePost
import com.example.splash.databinding.FragmentKiralaBinding
import com.squareup.picasso.Picasso
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import java.util.*
import java.util.concurrent.TimeUnit


class KiralaFragment : Fragment() {
    private lateinit var paymentIntentClientSecret: String
    private lateinit var paymentSheet: PaymentSheet

    private lateinit var binding: FragmentKiralaBinding
    private var rentalHouseID: String = ""
    private var invalidDates: List<String> = listOf()

    private var selectedStartDate: String = ""
    private var selectedEndDate: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
    }

    private fun onPaymentSheetResult(paymentResult: PaymentSheetResult) {
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                Toast.makeText(requireContext(), "Ödeme başarılı", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Canceled -> {
                Toast.makeText(requireContext(), "Ödeme iptal edildi", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(requireContext(), "Ödeme başarısız", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKiralaBinding.inflate(layoutInflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.kirala2.setOnClickListener {
            if(selectedStartDate.isEmpty() || selectedEndDate.isEmpty()) {
                Toast.makeText(requireContext(), "Lütfen tarih seçiniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val apiService = RestApiService(requireContext())
            val postData: CreateReservationPost = CreateReservationPost(
                rentalHouseId = rentalHouseID,
                startDate = selectedStartDate,
                endDate = selectedEndDate,
                identityNumber = "12312312312"
            )
            apiService.createReservation(postData) { response ->
                if(response?.isSuccess == true) {
                    Toast.makeText(requireContext(), "Reservasyon başarıyla oluşturuldu", Toast.LENGTH_SHORT).show()
                    var result = response.result
                    println(result)
                    if(result != null) {
                        val paymentData = PaymentMakePost(
                            paymentId = result.paymentId,
                        )
                        println(paymentData.paymentId)
                        apiService.makePayment(paymentData) { pay ->
                            println(pay)
                            if (pay?.isSuccess == true) {
                                Toast.makeText(
                                    requireContext(),
                                    "Ödeme bilgileri oluşturuldu",
                                    Toast.LENGTH_SHORT
                                ).show()

                                paymentIntentClientSecret = pay?.result?.stripe?.clientSecret.toString()
                                val configuration = PaymentSheet.Configuration(
                                    "E-Kira",
                                )

                                // Present Payment Sheet
                                paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration)

                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Bir hata oluştu: " + response?.error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Bir hata oluştu: " + response?.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.baslangicTextView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val yil = calendar.get(Calendar.YEAR)
            val ay = calendar.get(Calendar.MONTH)
            val gun = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { datePicker, y, a, g ->
                    val startDate = Calendar.getInstance()
                    startDate.set(y, a, g)

                    val endDatePicker = DatePickerDialog(
                        requireContext(),
                        DatePickerDialog.OnDateSetListener { datePicker, yy, aa, gg ->
                            val endDate = Calendar.getInstance()
                            endDate.set(yy, aa, gg)

                            val ilanTuru =
                                binding.gelenPeriyotTextView.text // İlan türünü burada belirleyin

                            val difference =
                                TimeUnit.MILLISECONDS.toDays(endDate.timeInMillis - startDate.timeInMillis)


                            // Seçilen başlangıç ve bitiş tarihlerini kullanarak ilanı kaydet veya başka bir işlem yap
                            //binding.editTextBaslangic.setText("$g/${a + 1}/$y")
                            //binding.editTextBitis.setText("$gg/${aa + 1}/$yy")


                            val startDateText = String.format(
                                "%02d/%02d/%d (%s)",
                                g,
                                a + 1,
                                y,
                                getDayOfWeek(startDate, Locale("tr", "TR"))
                            )
                            val endDateText = String.format(
                                "%02d/%02d/%d (%s)",
                                gg,
                                aa + 1,
                                yy,
                                getDayOfWeek(endDate, Locale("tr", "TR"))
                            )
                            binding.baslangicTextView.text = startDateText
                            binding.bitisTextView.text = endDateText

                            selectedStartDate = String.format(
                                "%d-%02d-%02d",
                                y,
                                a + 1,
                                g
                            )
                            selectedEndDate = String.format(
                                "%d-%02d-%02d",
                                yy,
                                aa + 1,
                                gg
                            )

                        },
                        yil,
                        ay,
                        gun
                    )

                    endDatePicker.datePicker.minDate =
                        startDate.timeInMillis // Başlangıç tarihinden önceki tarihler seçilemez
                    endDatePicker.setTitle("Tarih Seçiniz")
                    endDatePicker.setButton(
                        DialogInterface.BUTTON_POSITIVE,
                        "AYARLA",
                        endDatePicker
                    )
                    endDatePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "İPTAL", endDatePicker)
                    endDatePicker.show()

                },
                yil,
                ay,
                gun
            )

            datePicker.setTitle("Tarih Seçiniz")
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "AYARLA", datePicker)
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "İPTAL", datePicker)
            datePicker.show()
        }

        super.onViewCreated(view, savedInstanceState)

        val bundle: KiralaFragmentArgs by navArgs()
        val gelenkira = bundle.kirafiyati
        binding.fiyatKirala.text = gelenkira

        rentalHouseID = bundle.id

        val gelenCities = bundle.cities
        binding.ilKirala.text = gelenCities

        val gelenBaslik = bundle.baslik
        binding.baslikKirala.text = gelenBaslik


        val gelenAciklama = bundle.aciklama
        binding.aciklamaKirala.text = gelenAciklama

        val gelenPeriyot = bundle.periyot
        binding.gelenPeriyotTextView.text = gelenPeriyot

        val gelenphoto = bundle.downloadurl
        Picasso.get().load(gelenphoto).resize(1800, 1000).into(binding.ilanDetayimage)



        binding.backbtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        var apiService = RestApiService(requireContext())
        apiService.reservedDatesRentalHouse(rentalHouseID) {
            if (it?.isSuccess == true) {
                val result = it.result
                invalidDates = result as List<String>
            }
        }
    }

    private fun getDayOfWeek(calendar: Calendar, locale: Locale): String {
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val displayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale)
        return "$displayName"
    }
}



