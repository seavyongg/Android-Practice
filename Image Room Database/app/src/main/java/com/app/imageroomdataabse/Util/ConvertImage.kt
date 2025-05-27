package com.app.imageroomdataabse.Util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class ConvertImage {
    companion object{


        fun convertToString(bitmap: Bitmap): String? {
            val stream = ByteArrayOutputStream()
            val resultCompress = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            if (resultCompress) {
                val byteArray = stream.toByteArray()
                val imageAsString = when {
                    byteArray.size > 2000000 -> resizeImage(bitmap, 0.1)
                    byteArray.size in 1000000..2000000 -> resizeImage(bitmap, 0.5)
                    else -> Base64.encodeToString(byteArray, Base64.DEFAULT)
                }
                return imageAsString
            }
            return null
        }

        fun resizeImage(bitmap: Bitmap, coefficient:Double): String?{
            val resizeBitmap = Bitmap.createScaledBitmap(bitmap,(bitmap.width * coefficient).toInt(), (bitmap.height * coefficient.toInt()), true)
            val newStream = ByteArrayOutputStream()
            val resultCompress = resizeBitmap.compress(Bitmap.CompressFormat.PNG, 100, newStream)
            if(resultCompress){
                val newByteArray = newStream.toByteArray()
                return Base64.encodeToString(newByteArray, Base64.DEFAULT)
            }
            return null


        }

        fun convertToBitmap(imageAsString: String?): Bitmap? {
            if (imageAsString.isNullOrEmpty()) return null
            return try {
                val byteArrayAsDecodeString = Base64.decode(imageAsString, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(byteArrayAsDecodeString, 0, byteArrayAsDecodeString.size)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    }
}