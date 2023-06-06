package com.example.splash.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

class FileUtils {
    companion object {
        fun GetRealPathFromUri(context: Context, uri: Uri): String {
            var realPath = ""
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.let {
                it.moveToFirst()
                val columnIndex = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                realPath = it.getString(columnIndex)
                it.close()
            }
            return realPath
        }
    }
}