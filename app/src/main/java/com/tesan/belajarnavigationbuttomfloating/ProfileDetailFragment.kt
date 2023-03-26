package com.tesan.belajarnavigationbuttomfloating

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tesan.belajarnavigationbuttomfloating.Helper.GetFileProperties
import com.tesan.belajarnavigationbuttomfloating.ResponseModel.ResponseCRUD
import com.tesan.belajarnavigationbuttomfloating.databinding.FragmentProfileDetailBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileDetailFragment : Fragment() {

    private lateinit var binding: FragmentProfileDetailBinding // deklerasi binding
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileDetailBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        binding.btnSubmit.setOnClickListener{
            if (selectedImageUri == null){
                Toast.makeText(context,"Silahkan pilih gambar terlebih dahulu",Toast.LENGTH_SHORT).show()
            }else{
                val path = selectedImageUri?.let { it1 -> context?.let { it2 -> GetFileProperties.getFilePath(it2, it1) } }
                val file = File(path)
                Log.e("URI",file.toString())
                val fileRequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)
                val nameInput = binding.NamaProfile.text.toString()
                val textRequestBody = nameInput.toRequestBody("text/plain".toMediaTypeOrNull())
                // init retrofit
                insert(textRequestBody,filePart)
            }
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
            val path = selectedImageUri?.let { it1 -> context?.let { it2 -> GetFileProperties.getFilePath(it2, it1) } }
            Log.e("URI",selectedImageUri.toString()+" Path : "+path)
        }
    }

    fun insert(textRequestBody: RequestBody,filePart: MultipartBody.Part){
        val loading = ProgressDialog(context)
        loading.setMessage("Please wait...")
        loading.show()
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
}