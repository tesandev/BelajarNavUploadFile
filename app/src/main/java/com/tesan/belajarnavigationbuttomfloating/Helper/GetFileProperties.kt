package com.tesan.belajarnavigationbuttomfloating.Helper

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.File

class GetFileProperties {
    companion object {
        fun getFilePath(context: Context, uri: Uri): String? {
            var filePath: String? = null
            val projection = arrayOf(MediaStore.Images.Media.DATA)

            // Check if the uri scheme is content
            if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
                val cursor = context.contentResolver.query(uri, projection, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                        filePath = it.getString(columnIndex)
                    }
                }
            }

            // Check if the uri scheme is file
            if (uri.scheme == ContentResolver.SCHEME_FILE) {
                filePath = uri.path
            }

            return filePath
        }
    }

}
