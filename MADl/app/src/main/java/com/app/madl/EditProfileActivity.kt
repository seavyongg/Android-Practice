package com.app.madl


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.madl.databinding.ActivityEditProfileBinding

class EditProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edtFirstname.setText(intent.getStringExtra(ProfileActivity.KEY_FIRSTNAME))
        binding.edtLastname.setText(intent.getStringExtra(ProfileActivity.KEY_LASTNAME))
        binding.edtGroup.setText(intent.getStringExtra(ProfileActivity.KEY_GROUP))

        binding.btnSave.setOnClickListener {
            val resultIntent = intent
            resultIntent.putExtra(ProfileActivity.KEY_FIRSTNAME, binding.edtFirstname.text.toString())
            resultIntent.putExtra(ProfileActivity.KEY_LASTNAME, binding.edtLastname.text.toString())
            resultIntent.putExtra(ProfileActivity.KEY_GROUP, binding.edtGroup.text.toString())
            setResult(RESULT_OK, resultIntent)
            finish()
        }


    }
}
