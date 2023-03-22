package com.tesan.belajarnavigationbuttomfloating.Api

import com.tesan.belajarnavigationbuttomfloating.ResponseModel.ResponseCRUD
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Interface {
    @Multipart
    @POST("insert")
    fun insert(
        @Part("name") name:RequestBody,
        @Part file: MultipartBody.Part
    ): Call<ResponseCRUD>
}