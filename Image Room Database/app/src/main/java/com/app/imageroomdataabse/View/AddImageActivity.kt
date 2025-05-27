package com.app.imageroomdataabse.View

import androidx.activity.result.ActivityResultLauncher
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.app.imageroomdataabse.Model.Images
import com.app.imageroomdataabse.Util.ControlPermission
import com.app.imageroomdataabse.Util.ConvertImage
import com.app.imageroomdataabse.ViewModel.ImageViewModel
import com.app.imageroomdataabse.databinding.ActivityAddImageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddImageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddImageBinding
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var selectedImage : Bitmap ?= null
    lateinit var imageViewModel: ImageViewModel //insert image
    var control = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //provide view model to activity
        imageViewModel = ViewModelProvider(this)[ImageViewModel::class.java]

        //register for activity result
        registerActivityForSelectImage()


        binding.imgAdd.setOnClickListener{
            if(ControlPermission.checkPermission(this)) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                //startActivityForResult -> Before API 30
                activityResultLauncher.launch(intent)

            }else{
                if(Build.VERSION.SDK_INT >= 33){
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                        1
                    )
                }else{
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                    )
                }
            }
        }

        binding.btnAdd.setOnClickListener{
            if(control){
                binding.btnAdd.text = "Uploading...Please Wait"
                binding.btnAdd.isEnabled = false
                GlobalScope.launch(Dispatchers.IO) {
                    val title = binding.editTextTitle.text.toString()
                    val description = binding.editTextDescription.text.toString()
                    val imageAsString = ConvertImage.convertToString(selectedImage!!)
                    if( imageAsString != null){
                        imageViewModel.insert(Images(title, description, imageAsString))
                        control = false
                        finish()
                    }else{
                        Toast.makeText(applicationContext, "Error converting image", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(applicationContext, "Please select an image", Toast.LENGTH_SHORT).show()
            }
        }
        binding.toolbar.setNavigationOnClickListener {
           finish()
        }
    }

    fun registerActivityForSelectImage(){
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val resultCode = result.resultCode
            val imageData = result.data
            if (resultCode == RESULT_OK && imageData != null) {
                val imageUri = imageData.data
                imageUri?.let{
                    selectedImage = if (Build.VERSION.SDK_INT >= 28){
                        val imageSource = ImageDecoder.createSource(
                            this.contentResolver,
                           it
                        )
                        ImageDecoder.decodeBitmap(imageSource)
                    }else{
                        MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    }
                    binding.imgAdd.setImageBitmap(selectedImage)
                    control = true

                }
            }
        }
    }

    //This method is called when the user grants or denies the permission like allow or deny access to gallery
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intent)
        }
    }
}