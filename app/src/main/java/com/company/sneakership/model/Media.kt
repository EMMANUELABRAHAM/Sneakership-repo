package com.company.sneakership.model

import com.google.gson.annotations.SerializedName


data class Media(

    @SerializedName("imageUrl") var imageUrl: String? = null,
    @SerializedName("smallImageUrl") var smallImageUrl: String? = null,
    @SerializedName("thumbUrl") var thumbUrl: String? = null

)