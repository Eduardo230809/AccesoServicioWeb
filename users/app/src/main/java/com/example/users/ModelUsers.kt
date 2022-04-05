package com.example.users

import com.google.gson.annotations.SerializedName

data class ModelUsers(
    @SerializedName("name") var nombre:String?,
    @SerializedName("username") var usuario:String?,
    @SerializedName("email") var email:String?,
    @SerializedName("phone") var telefono:String?,
    @SerializedName("website") var web:String?,
    @SerializedName("address") var city:Address?
)
