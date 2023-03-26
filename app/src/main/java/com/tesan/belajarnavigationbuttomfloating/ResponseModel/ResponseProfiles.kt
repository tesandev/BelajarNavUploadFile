package com.tesan.belajarnavigationbuttomfloating.ResponseModel

import com.google.gson.annotations.SerializedName

data class ResponseProfiles(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Data")
	val data: List<DataItem?>? = null
)

data class DataItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
