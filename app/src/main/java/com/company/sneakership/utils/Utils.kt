package com.company.sneakership.utils

import android.content.res.AssetManager
import android.content.res.Resources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object Utils {
    fun readJsonFromAsset(assetManager: AssetManager, fileName: String): String {
        try {
            val inputStream = assetManager.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()

            var line: String? = reader.readLine()
            while (line != null) {
                stringBuilder.append(line)
                line = reader.readLine()
            }

            inputStream.close()
            return stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }
    }
}