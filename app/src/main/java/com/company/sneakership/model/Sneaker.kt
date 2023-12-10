package com.company.sneakership.model
import com.google.gson.annotations.SerializedName


data class Sneaker (

  @SerializedName("id"          ) var id          : String? = null,
  @SerializedName("brand"       ) var brand       : String? = null,
  @SerializedName("colorway"    ) var colorway    : String? = null,
  @SerializedName("gender"      ) var gender      : String? = null,
  @SerializedName("media"       ) var media       : Media?  = Media(),
  @SerializedName("releaseDate" ) var releaseDate : String? = null,
  @SerializedName("retailPrice" ) var retailPrice : Int?    = null,
  @SerializedName("styleId"     ) var styleId     : String? = null,
  @SerializedName("shoe"        ) var shoe        : String? = null,
  @SerializedName("name"        ) var name        : String? = null,
  @SerializedName("title"       ) var title       : String? = null,
  @SerializedName("year"        ) var year        : Int?    = null

)