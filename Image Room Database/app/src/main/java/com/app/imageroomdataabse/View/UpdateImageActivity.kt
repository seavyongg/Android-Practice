package com.app.imageroomdataabse.View

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings.Global
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.app.imageroomdataabse.Model.Images
import com.app.imageroomdataabse.R
import com.app.imageroomdataabse.Util.ConvertImage
import com.app.imageroomdataabse.ViewModel.ImageViewModel
import com.app.imageroomdataabse.databinding.ActivityAddImageBinding
import com.app.imageroomdataabse.databinding.ActivityUpdateImageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateImageBinding
    var id = -1
    lateinit var viewModel: ImageViewModel
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var imageAsString: String = ""
    lateinit var selectedImage: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ImageViewModel::class.java]

        getAndSetData()
        //register for activity result
        registerActivityForSelectImage()
        binding.imgAdd.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //startActivityForResult -> Before API 30
            activityResultLauncher.launch(intent)

        }
        binding.btnUpdate.setOnClickListener {
            binding.btnUpdate.text = "Updating...Please Wait"
            binding.btnUpdate.isEnabled = false
            GlobalScope.launch(Dispatchers.IO) {
                val imageTitle = binding.editTextTitle.text.toString()
                val imageDescription = binding.editTextDescription.text.toString()
                val newImageAsString = ConvertImage.convertToString(selectedImage)
                imageAsString = newImageAsString.toString()

                val myUpdatedImage = Images(
                    imageTitle,
                    imageDescription,
                    imageAsString
                )
                myUpdatedImage.imageId = id
                viewModel.update(myUpdatedImage)
                finish()
            }



        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    fun getAndSetData() {
        id = intent.getIntExtra("imageId", -1)
        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val Image = viewModel.getItemById(id)
                withContext(Dispatchers.Main) {
                    binding.editTextTitle.setText(Image.imageTitle)
                    binding.editTextDescription.setText(Image.imageDescription)
                    val imageAsString = Image.imageString
                    val imageAsBitmap = ConvertImage.convertToBitmap(imageAsString)
                    binding.imgAdd.setImageBitmap(imageAsBitmap)

                }
            }
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
//                    control = true

                }
            }
        }
    }
}