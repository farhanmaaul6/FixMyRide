package com.bangkit.fixmyrideapp.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ExecutionException

object ImageUtils {

    fun downloadAndSaveImage(context: Context, imageUrl: String): File? {
        try {
            val futureTarget: FutureTarget<Bitmap> = Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .submit()

            val bitmap = futureTarget.get()

            // Nama file gambar
            val fileName = "your_image_name.jpg"

            // Direktori penyimpanan lokal
            val directory = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "YourDirectoryName")

            if (!directory.exists()) {
                directory.mkdirs()
            }

            // File penyimpanan lokal
            val file = File(directory, fileName)

            // Simpan gambar ke penyimpanan lokal
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }

            return file
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
        return null
    }
}