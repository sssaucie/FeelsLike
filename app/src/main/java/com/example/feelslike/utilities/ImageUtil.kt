package com.example.feelslike.utilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object ImageUtil
{
    private var TAG = ImageUtil::class.java.simpleName
    fun saveBitmapToFile(context : Context, bitmap : Bitmap, filename : String)
    {
        val stream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        val bytes = stream.toByteArray()

        saveBytesToFile(context, bytes, filename)
        Log.i(TAG, "$filename saved")
    }

    private fun saveBytesToFile(context : Context, bytes : ByteArray, filename : String)
    {
        val outputStream : FileOutputStream

        try
        {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
            outputStream.write(bytes)
            outputStream.close()
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            Log.e(TAG, "Output stream error: $e")
        }
    }

    fun loadBitmapFromFile(context : Context, filename : String) : Bitmap?
    {
        val filePath = File(context.filesDir, filename).absolutePath
        Log.i(TAG, "File path created: $filePath")
        return BitmapFactory.decodeFile(filePath)
    }
}