package com.example.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginStart
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.splash.api.RestApiService
import com.example.splash.api.models.GetRentPeriod
import com.example.splash.api.models.RentalHouseDetails
import com.example.splash.api.models.RentalHouseList
import com.example.splash.databinding.FragmentIlanDetayBinding
import com.example.splash.utils.CircleTransform
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class ilanDetayFragment : Fragment() {

    private var _binding: FragmentIlanDetayBinding? = null
    private val binding get() = _binding!!

    private lateinit var rentalHouseInfo: RentalHouseDetails

    override fun onCreateView( inflater: LayoutInflater,
                               container: ViewGroup?,
                               savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentIlanDetayBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        binding.rentalTime2.visibility = View.GONE
        binding.relativeLayout1.visibility = View.GONE
        binding.relativeLayout2.visibility = View.GONE
        binding.rentalTime.visibility = View.GONE

        binding.userEmail.setOnClickListener {
            val href = "mailto:${rentalHouseInfo.creator.email}"
            val uris = Uri.parse(href)
            val intents = Intent(Intent.ACTION_VIEW, uris)
            val b = Bundle()
            b.putBoolean("new_window", true)
            intents.putExtras(b)
            startActivity(intents)
        }

        binding.userPhone.setOnClickListener {
            if(rentalHouseInfo.creator.phone.startsWith("+90")) {
                val href = "tel:${rentalHouseInfo.creator.phone}"
                val uris = Uri.parse(href)
                val intents = Intent(Intent.ACTION_VIEW, uris)
                val b = Bundle()
                b.putBoolean("new_window", true)
                intents.putExtras(b)
                startActivity(intents)
            }
        }

        val bundle: ilanDetayFragmentArgs by navArgs()
        getDataAPI(bundle.rentalHouseID)

        binding.kirala.setOnClickListener {
            var gelenphoto = ""
            if (rentalHouseInfo.images.isNotEmpty()) {
                if(rentalHouseInfo.images[0].isNotEmpty()) {
                    gelenphoto = rentalHouseInfo.images[0][0].url
                }
            }
            val df = DecimalFormat("#.##")
            val price = df.format(rentalHouseInfo.price)
            val action = ilanDetayFragmentDirections.actionIlanDetayFragmentToKiralaFragment(gelenphoto,rentalHouseInfo.title,price.toString(),rentalHouseInfo.description,rentalHouseInfo.address.district.town.city.name,
                    GetRentPeriod(rentalHouseInfo.rentPeriod),
                    rentalHouseInfo.id
                )
            Navigation.findNavController(it).navigate(action)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.backbtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun updateView() {
        binding.loadingRelative.visibility = View.GONE
        if(rentalHouseInfo.id == "") {
            Toast.makeText(requireContext(), "İlan bulunamadı", Toast.LENGTH_SHORT).show()
            return
        }

        binding.gelenFiyattextView.text = rentalHouseInfo.price.toString()
        binding.gelencitestextView.text = rentalHouseInfo.address.district.town.city.name
        binding.gelenbasliktextView.text = rentalHouseInfo.title
        binding.gelenperiyottextView.text = GetRentPeriod(rentalHouseInfo.rentPeriod)
        binding.gelenilcetextView.text = rentalHouseInfo.address.district.town.name
        val textDistrictQuarter =
            "${rentalHouseInfo.address.district.name} / ${rentalHouseInfo.address.name}"
        binding.gelenmahalleikitextView.text = textDistrictQuarter
        binding.gelenaciklamatextView.text = rentalHouseInfo.description

        var firstName = rentalHouseInfo.creator.firstName
        val lastName = rentalHouseInfo.creator.lastName

        if(lastName.isNotEmpty()) {
            firstName = "$firstName $lastName"
        }
        binding.fullName.text = firstName
        binding.userEmail.text = rentalHouseInfo.creator.email
        binding.userPhone.text = rentalHouseInfo.creator.phone

        binding.rentalTime2.visibility = View.VISIBLE
        binding.relativeLayout1.visibility = View.VISIBLE
        binding.relativeLayout2.visibility = View.VISIBLE
        binding.rentalTime.visibility = View.VISIBLE

        if(rentalHouseInfo.creator.profileImage != null) {
            Picasso.get().load(rentalHouseInfo.creator.profileImage).transform(CircleTransform())
                .into(binding.profileImage)
            // circle crop
        }

        if (rentalHouseInfo.images.isNotEmpty()) {
            val firstImage = rentalHouseInfo.images[0]
            if (firstImage.isNotEmpty()) {
                binding.ilanDetayimage.visibility = View.VISIBLE
                Picasso.get().load(firstImage[0].url)
                    .into(binding.ilanDetayimage)
            } else {
                binding.ilanDetayimage.visibility = View.GONE
            }

            if (rentalHouseInfo.images.size > 1) {
                for (i in 1 until rentalHouseInfo.images.size) {
                    val image = rentalHouseInfo.images[i]
                    if (image.isNotEmpty()) {
                        val imageView = ImageView(requireContext())
                        imageView.layoutParams = LinearLayout.LayoutParams(
                            400,
                            250
                        )
                        val param =
                            imageView.layoutParams as ViewGroup.MarginLayoutParams
                        param.setMargins(26, 15, 0, 0)
                        imageView.adjustViewBounds = true
                        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                        imageView.setPadding(0, 0, 10, 0)
                        Picasso.get().load(image[0].url).into(imageView)
                        binding.miniImages.addView(imageView)
                    }
                }
            }
        } else {
            binding.ilanDetayimage.visibility = View.GONE
        }
    }

    private fun getDataAPI(rentalHouseID : String) {
        val apiService = RestApiService(requireContext())
        apiService.getRentalHouseDetails(rentalHouseID) {
            if (it?.isSuccess == true && it.result != null) {
                rentalHouseInfo = it.result!!
                updateView()
            } else {
                Toast.makeText(requireContext(), it?.error.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

