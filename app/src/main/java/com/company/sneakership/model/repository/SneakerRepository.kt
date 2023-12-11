package com.company.sneakership.model.repository

import android.content.res.AssetManager
import com.company.sneakership.model.Sneaker
import com.company.sneakership.utils.ApiResponse
import com.company.sneakership.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SneakerRepository {
    suspend fun getSneakers(assetManager: AssetManager): ApiResponse<List<Sneaker>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val jsonString = Utils.readJsonFromAsset(assetManager, "sneaker_list.json")
                // Simulate 400 status code
                if (jsonString.contains("Invalid query parameter")) {
                    ApiResponse.Error("Invalid query parameter supplied", 400)
                } else {
                    // Simulate 200 success status code
                    val shoes = Gson().fromJson(jsonString, Array<Sneaker>::class.java).toList()
                    ApiResponse.Success(shoes)
                }
            } catch (e: Exception) {
                // Simulate 500 status code for unexpected errors
                ApiResponse.Error("Unexpected error: ${e.message}", 500)
            }
        }
}
