package com.example.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.splash.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar
import java.io.IOException


class AddFragment : Fragment() {
    var selectedPicture : Uri? = null
    var selectedBitmap : Bitmap? = null
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
        print("fdslaflç")
    }


    fun selectImage(view: View) {

        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    requireActivity().applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Give Permission",
                            View.OnClickListener {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }).show()
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                val intentToGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)

            }

        }

    }

    private fun registerLauncher() {
        var activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    selectedPicture = intentFromResult.data
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                selectedPicture!!
                            )
                            selectedBitmap = ImageDecoder.decodeBitmap(source)
                            binding.selectImage.setImageBitmap(selectedBitmap)
                        } else {
                            selectedBitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,
                                selectedPicture
                            )
                            binding.selectImage.setImageBitmap(selectedBitmap)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result) {
                //permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            } else {
                //permission denied
                Toast.makeText(requireContext(), "Permisson needed!", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}