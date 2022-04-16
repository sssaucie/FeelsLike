package com.emilycodes.feelslike.model.entity

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emilycodes.feelslike.utilities.ImageUtil
import java.io.Serializable

@Entity(tableName="favorite_list")
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var favorites_entity_id : Long? = null,

    var favorite_id : String? = null,

    var place_name : String = "",

    var place_lat : Double = 0.0,

    var place_lon : Double = 0.0
) : Serializable
{
    fun setImage(image : Bitmap, context : Context)
    {
        favorites_entity_id?.let {
            ImageUtil.saveBitmapToFile(context, image, generateImageFilename(it))
        }
    }

    companion object
    {
        fun generateImageFilename(id : Long) : String
        {
            return "favorite$id.png"
        }
    }
}