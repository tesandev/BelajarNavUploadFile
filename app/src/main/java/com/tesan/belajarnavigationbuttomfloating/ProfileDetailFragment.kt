package com.tesan.belajarnavigationbuttomfloating

import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import com.tesan.belajarnavigationbuttomfloating.ResponseModel.ResponseCRUD
import com.tesan.belajarnavigationbuttomfloating.databinding.FragmentProfileDetailBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileDetailFragment : Fragment() {

    private lateinit var binding: FragmentProfileDetailBinding // deklerasi binding
    private var b:Bundle? = null
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileDetailBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        binding.btnSubmit.setOnClickListener{
            val path = selectedImageUri?.let { it1 -> context?.let { it2 -> getPathFromUri(it2, it1) } }
            val file = File(path)
            Log.e("URI",file.toString())
            val fileRequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)
            val nameInput = binding.NamaProfile.text.toString()
            val textRequestBody = nameInput.toRequestBody("text/plain".toMediaTypeOrNull())

            val loading = ProgressDialog(context)
            loading.setMessage("Please wait...")
            loading.show()

            // init retrofit
            ApiConfig.instanceRetrofit.insert(textRequestBody,filePart).enqueue(object :Callback<ResponseCRUD>{
                override fun onResponse(
                    call: Call<ResponseCRUD>,
                    response: Response<ResponseCRUD>
                ) {
                    loading.dismiss()
                    Toast.makeText(context,response.body()?.message,Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).ReplaceFragment(HomeFragment())
                }

                override fun onFailure(call: Call<ResponseCRUD>, t: Throwable) {
                    loading.dismiss()
                    Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
                    Log.e("ONFAIL",t.toString())
                }

            })

        }
        binding.selectedFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)

        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100){
            selectedImageUri = data?.data
            binding.imageView.setImageURI(selectedImageUri)
            Log.e("URI",selectedImageUri.toString())
        }
    }

    private fun getPathFromUri(context: Context, uri: Uri): String? {
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