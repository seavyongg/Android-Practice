package com.app.imageroomdataabse.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
class Images(
    var imageTitle : String,
    var imageDescription : String,
    //BLOB -> Binary Large Object
    //String -> Base64 format
    var imageString : String

) {
    @PrimaryKey(autoGenerate = true)
    var imageId : Int = 0


}