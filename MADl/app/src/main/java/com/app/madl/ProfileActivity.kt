package com.app.madl

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.app.madl.databinding.ActivityProfileBinding
import java.io.Serializable

class ProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var newFirstname: String? = null
    private var newLastname: String? = null
    private var newGroup: String? = null

    // to update profile data in ProfileActivity
    private val editProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    newFirstname = data.getStringExtra(KEY_FIRSTNAME)
                    newLastname = data.getStringExtra(KEY_LASTNAME)
                    newGroup = data.getStringExtra(KEY_GROUP)
                    updateProfileData()
                }
            }
        }
    private fun updateProfileData() {
        binding.txtFirstname.text = newFirstname
        binding.txtLastname.text = newLastname
        binding.txtGroup.text = "ITE-G$newGroup"
    }
    //companion object to define keys for intent extras
    companion object {
        const val KEY_FIRSTNAME = "firstname"
        const val KEY_LASTNAME = "lastname"
        const val KEY_GROUP = "group"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newLastname = intent.getStringExtra(KEY_LASTNAME) ?: binding.txtLastname.text.toString()
        newFirstname =intent.getStringExtra(KEY_FIRSTNAME) ?: binding.txtFirstname.text.toString()
        newGroup = intent.getStringExtra(KEY_GROUP) ?: binding.txtGroup.text.toString().split("G")[1].trim()
        binding.icEdit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra(KEY_LASTNAME, newLastname)
            intent.putExtra(KEY_FIRSTNAME, newFirstname)
            intent.putExtra(KEY_GROUP, newGroup)
            editProfileLauncher.launch(intent)
        }

    }

}