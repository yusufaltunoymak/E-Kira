package com.example.splash

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.splash.api.RestApiService
import com.example.splash.databinding.FragmentProfileBinding
import com.example.splash.utils.CircleTransform
import com.example.splash.utils.FileUtils
import com.squareup.picasso.Picasso
import com.vmadalin.easypermissions.EasyPermissions
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.io.File


class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private val REQUEST_CODE_PROFILE_IMAGE = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        val view2 = binding.root

        binding.profileImage.setOnClickListener {
            selectImage()
        }

        // Inflate the layout for this fragment
        var firstName = this@ProfileFragment.activity?.intent?.extras?.getString("firstName")
        val lastName = this@ProfileFragment.activity?.intent?.extras?.getString("lastName")
        val email = this@ProfileFragment.activity?.intent?.extras?.getString("email")
        val phoneNumber = this@ProfileFragment.activity?.intent?.extras?.getString("phone")
        val id = this@ProfileFragment.activity?.intent?.extras?.getString("id")
        val profileImage = this@ProfileFragment.activity?.intent?.extras?.getString("profileImage")
        if(lastName?.length!! > 0) {
            firstName = "$firstName $lastName"
        }
        binding.fullName.text = firstName
        binding.userEmail.text = email
        binding.userPhone.text = phoneNumber
        binding.textView7.text = id

        if (profileImage != null) {
            if(profileImage.isNotEmpty()) {
                Picasso.get().load(profileImage).transform(CircleTransform())
                    .into(binding.profileImage)
                // circle crop
            }
        }

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
                    R.id.ilanlarim -> {
                        val action = ProfileFragmentDirections.actionProfileFragmentToIlanlarimFragment()
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


    fun selectImage() {
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
                REQUEST_CODE_PROFILE_IMAGE,
                PERMISSION
            )
        } else{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(Intent.createChooser(intent, "Resimleri Seçin"))
        }
    }

    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {

                val apiService = RestApiService(requireContext())

                val selectedImage: Uri? = result.data?.data



                if (selectedImage != null) {
                    val filePath: String = FileUtils.GetRealPathFromUri(requireContext(), selectedImage)
                    if(filePath.isEmpty()) {
                        Toast.makeText(requireContext(), "Resim seçilemedi", Toast.LENGTH_SHORT).show()
                        return@registerForActivityResult
                    }
                    val extension = filePath.substring(filePath.lastIndexOf("."))
                    val fileName = "profileImage$extension"
                    val mimeType: String? = requireContext().contentResolver.getType(selectedImage)
                    val file = File(filePath)
                    val requestFile = RequestBody.create(
                        mimeType?.let { MediaType.parse(it) },
                        file
                    )
                    val filePart = MultipartBody.Part.createFormData("image", fileName, requestFile)
                    apiService.setProfileImage(filePart) {
                        if(it?.isSuccess == true) {
                            Toast.makeText(requireContext(), "Profil resmi güncellendi", Toast.LENGTH_SHORT).show()
                            Picasso.get().load(selectedImage).transform(CircleTransform())
                                .into(binding.profileImage)
                        } else {
                            println(it?.headers)
                            Toast.makeText(requireContext(), "Profil resmi güncellenemedi: " + it?.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}





