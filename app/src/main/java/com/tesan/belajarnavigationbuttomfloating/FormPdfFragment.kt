package com.tesan.belajarnavigationbuttomfloating

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tesan.belajarnavigationbuttomfloating.Helper.GetFileProperties
import com.tesan.belajarnavigationbuttomfloating.ResponseModel.ResponseCRUD
import com.tesan.belajarnavigationbuttomfloating.databinding.FragmentFormPdfBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class FormPdfFragment : Fragment() {

    private val CHOOSE_PDF_FROM_DEVICE:Int = 111
    private lateinit var binding:FragmentFormPdfBinding
    private var selectedFileUri: Uri? = null
    private var pdfName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormPdfBinding.inflate(inflater,container,false)

        binding.btnSave.setOnClickListener {

            val file = File(selectedFileUri?.path)
            val fileRequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            val nameInput = binding.deskripsi.text.toString()
            val textRequestBody = nameInput.toRequestBody("text/plain".toMediaTypeOrNull())

            val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)

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

        binding.selectFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "application/pdf"
            startActivityForResult(intent,CHOOSE_PDF_FROM_DEVICE)
        }
        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100){
            selectedFileUri = data?.data
            binding.selectFile.text = selectedFileUri?.lastPathSegment

        }
    }
}