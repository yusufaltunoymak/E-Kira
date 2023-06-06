package com.example.splash

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.splash.api.RestApiService
import com.example.splash.api.models.City
import com.example.splash.api.models.District
import com.example.splash.api.models.Quarter
import com.example.splash.api.models.Town
import com.example.splash.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class AddFragment : Fragment() {
    var selectedPicture : Uri? = null
    var selectedBitmap : Bitmap? = null
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private val periyot = ArrayList<String>()
    private lateinit var veriAdaptoru : ArrayAdapter<String>

    val Cities : MutableMap<String, Int> = mutableMapOf()
    val Towns : MutableMap<String, Int> = mutableMapOf()
    val Districts : MutableMap<String, Int> = mutableMapOf()
    val Quarters : MutableMap<String, Int> = mutableMapOf()
    var selectedQuarter : Int = 0

    private val REQUEST_CODE_ADD_IMAGE: Int = 1

    private val selectedImages: ArrayList<Uri> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectImage.setOnClickListener { selectImage(view) }
        val apiService = this@AddFragment.context?.let { RestApiService(it) }

        UpdateCitySpinner(true)

        val bundle: AddFragmentArgs by navArgs()
        val gelenPeriyot = bundle.periyot

        periyot.add("Günlük")
        periyot.add("Aylık")
        periyot.add("Yıllık")

        veriAdaptoru = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,android.R.id.text1,periyot)
        binding.periyotSpinner.adapter = veriAdaptoru

        veriAdaptoru.insert("Kira Periyodu Seçin",0)
        binding.periyotSpinner.adapter = veriAdaptoru
        binding.periyotSpinner.setSelection(0)

        binding.periyotSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.submit.setOnClickListener {
            val action = AddFragmentDirections.actionAddFragmentToMainFragment(veriAdaptoru.toString())
            Navigation.findNavController(it).navigate(action)

            // duzenlenecek
            Toast.makeText(context, "İlanınız başarıyla oluşturuldu.", Toast.LENGTH_LONG).show()
        }


        apiService?.getCities() {
            if(it?.isSuccess == true) {
                (it?.result as ArrayList<City>).forEach { city ->
                    Cities.set(city.name, city.id)
                }
                UpdateCitySpinner()
            }
        }

        var citySpinner : Spinner = binding.cities
        var townSpinner : Spinner = binding.towns
        var districtSpinner : Spinner = binding.districts
        var quarterSpinner : Spinner = binding.quarters
        citySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                var id : Int? = Cities.get(selectedItem)
                if(position > 0 && id != null && id > 0) {
                    Towns.clear()
                    Districts.clear()
                    Quarters.clear()
                    UpdateDistrictSpinner()
                    UpdateQuarterSpinner()
                    UpdateTownSpinner(true)
                    apiService?.getTowns(id) {
                        if(it?.isSuccess == true) {
                            (it?.result as List<Town>).forEach { town ->
                                Towns[town.name] = town.id
                            }
                            UpdateTownSpinner()
                        }
                        UpdateTownSpinner()
                    }
                } else {
                    Towns.clear()
                    Quarters.clear()
                    Districts.clear()
                    UpdateQuarterSpinner()
                    UpdateDistrictSpinner()
                    UpdateTownSpinner()
                }
                binding.submit.isEnabled = false
            }
        }
        townSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                var id : Int? = Towns[selectedItem]
                if(position > 0 && id != null && id > 0) {
                    println(id)
                    Districts.clear()
                    Quarters.clear()
                    UpdateQuarterSpinner()
                    UpdateDistrictSpinner(true)
                    apiService?.getDistricts(id) {
                        if(it?.isSuccess == true) {
                            (it.result as List<District>).forEach { district ->
                                Districts[district.name] = district.id
                            }
                            UpdateDistrictSpinner()
                        }
                        UpdateDistrictSpinner()
                    }
                } else {
                    Quarters.clear()
                    Districts.clear()
                    UpdateQuarterSpinner()
                    UpdateDistrictSpinner()
                }
                binding.submit.isEnabled = false
            }
        }
        districtSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                var id : Int? = Districts.get(selectedItem)
                if(position > 0 && id != null && id > 0) {
                    println(id)
                    Quarters.clear()
                    UpdateQuarterSpinner(true)
                    apiService?.getQuarters(id) {
                        if (it?.isSuccess == true) {
                            (it.result as List<Quarter>).forEach { quarter ->
                                Quarters[quarter.name] = quarter.id
                            }
                            UpdateQuarterSpinner()
                        }
                    }
                } else {
                    Quarters.clear()
                    UpdateQuarterSpinner()
                }
                binding.submit.isEnabled = false
            }
        }
        quarterSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var selectedItem = parent?.getItemAtPosition(position).toString()
                var id : Int? = Quarters.get(selectedItem)
                if(position > 0 && id != null && id > 0) {
                    binding.submit.isEnabled = true
                    selectedQuarter = id
                }
            }
        }

    }

    fun selectImage(view: View) {
        val PERMISSION = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if(!EasyPermissions.hasPermissions(
                requireActivity().applicationContext,
                PERMISSION
        )) {
            EasyPermissions.requestPermissions(
                this,
                "You need to give permission to access gallery",
                REQUEST_CODE_ADD_IMAGE,
                PERMISSION
            )
        } else{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            activityResultLauncher.launch(Intent.createChooser(intent, "Resimleri Seçin"))
        }
    }

    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Resimler Seçildi",
                    Toast.LENGTH_SHORT
                ).show()

                println(result.data?.clipData?.itemCount)

                println("resimler secildi")

                // print file name and size
                var imageChanged = false
                var imageViews = ArrayList<ImageView>(
                    arrayListOf(
                        binding.miniImage1,
                        binding.miniImage2,
                        binding.miniImage3,
                        binding.miniImage4,
                        binding.miniImage5,
                        binding.miniImage6,
                    )
                )

                for(imageView in imageViews) {
                    imageView.visibility = View.GONE
                }

                // Image previews
                for (i in 0 until result.data?.clipData?.itemCount!!) {
                    val uri: Uri? = result.data?.clipData?.getItemAt(i)?.uri
                    if (uri != null) {
                        selectedImages.add(uri)

                        if(!imageChanged) {
                            imageChanged = true
                            binding.selectImage.scaleType = ImageView.ScaleType.CENTER_CROP
                            binding.selectImage.setImageURI(uri)
                        } else {
                            val imageview = imageViews[i - 1]
                            imageview.setImageURI(uri)
                            imageview.visibility = View.VISIBLE
                            imageview.scaleType = ImageView.ScaleType.CENTER_CROP
                        }
                    }
                }
            }
        }
    }

    fun UpdateCitySpinner(loading : Boolean = false) {
        var citySpinner : Spinner = binding.cities
        var cities = ArrayList<String>()
        if(loading) {
            cities.add("Yükleniyor...")
            citySpinner.adapter = this@AddFragment.context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    cities
                )
            }
            citySpinner.visibility = View.VISIBLE
            return
        }
        cities.add("İl Seçin")
        Cities.forEach { (name) ->
            cities.add(name)
        }
        citySpinner.adapter = this@AddFragment.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                cities
            )
        }
    }

    fun UpdateTownSpinner(loading : Boolean = false) {
        var townSpinner : Spinner = binding.towns
        var towns = ArrayList<String>()
        if(loading) {
            towns.add("Yükleniyor...")
            townSpinner.adapter = this@AddFragment.context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    towns
                )
            }
            townSpinner.visibility = View.VISIBLE
            return
        }
        towns.add("İlçe Seçin")
        if(Towns.isEmpty()) townSpinner.visibility = View.GONE
        else townSpinner.visibility = View.VISIBLE
        binding.scrollView.scrollTo(0, binding.scrollView.bottom)
        Towns.forEach { (name) ->
            towns.add(name)
        }
        townSpinner.adapter = this@AddFragment.context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, towns) }
    }

    fun UpdateDistrictSpinner(loading : Boolean = false) {
        var districtSpinner : Spinner = binding.districts
        var districts = ArrayList<String>()
        if(loading) {
            districts.add("Yükleniyor...")
            districtSpinner.adapter = this@AddFragment.context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    districts
                )
            }
            districtSpinner.visibility = View.VISIBLE
            return
        }
        districts.add("Semt Seçin")
        if(Districts.isEmpty()) districtSpinner.visibility = View.GONE
        else districtSpinner.visibility = View.VISIBLE
        binding.scrollView.scrollTo(0, binding.scrollView.bottom)
        Districts.forEach { (name) ->
            districts.add(name)
        }
        districtSpinner.adapter = this@AddFragment.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                districts
            )
        }
    }

    fun UpdateQuarterSpinner(loading : Boolean = false) {
        var quarterSpinner : Spinner = binding.quarters
        var quarters = ArrayList<String>()
        if(loading) {
            quarters.add("Yükleniyor...")
            quarterSpinner.adapter = this@AddFragment.context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    quarters
                )
            }
            quarterSpinner.visibility = View.VISIBLE
            return
        }
        quarters.add("Mahalle Seçin")
        if(Quarters.isEmpty()) quarterSpinner.visibility = View.GONE
        else quarterSpinner.visibility = View.VISIBLE
        binding.scrollView.scrollTo(0, binding.scrollView.bottom)
        binding.scrollView.scrollTo(0, binding.scrollView.bottom)
        Quarters.forEach { (name) ->
            quarters.add(name)
        }
        quarterSpinner.adapter = this@AddFragment.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                quarters
            )
        }
    }

}