package com.app.afinal.view

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.afinal.databinding.ActivityProductListBinding
import com.app.afinal.model.Product

open class BaseActivity: AppCompatActivity() {
    protected fun showDialog(title: String, message: String){
        val dialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK"){
                    dialog, which -> dialog.dismiss()
            }
        dialog.show()
    }


}