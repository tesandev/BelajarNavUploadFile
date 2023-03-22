package com.tesan.belajarnavigationbuttomfloating.ResponseModel

import com.google.gson.annotations.SerializedName

data class ResponseCRUD(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Message")
	val message: String? = null
)
