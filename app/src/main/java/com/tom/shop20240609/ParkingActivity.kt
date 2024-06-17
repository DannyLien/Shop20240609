package com.tom.shop20240609

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.gson.Gson
import com.tom.shop20240609.databinding.ActivityParkingBinding
import java.net.URL


class ParkingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParkingBinding
    val TAG = ParkingActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParkingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val parking = "https://api.jsonserve.com/TyZaEe"
        ParkingTask().execute(parking)

    }

    inner class ParkingTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val url = URL(params[0])
            val json = url.readText()
            Log.d(TAG, "doInBackground: $json")
            return json

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(this@ParkingActivity, "Got it", Toast.LENGTH_LONG).show()
            parseGson(result)
            binding.info.text = result

        }
    }

    private fun parseGson(json: String?) {
        val parking = Gson().fromJson<Parking>(json, Parking::class.java)
        val jsonSize = parking.parkingLots.size
        parking.parkingLots.forEach {
            Log.d(TAG, "parseGson: = ${it.areaId},${it.areaName},${it.parkName},${it.totalSpace}")
        }
    }

    data class Parking(
        val parkingLots: List<ParkingLot>
    )

    data class ParkingLot(
        val address: String,
        val areaId: String,
        val areaName: String,
        val introduction: String,
        val parkId: String,
        val parkName: String,
        val payGuide: String,
        val surplusSpace: String,
        val totalSpace: Int,
        val wgsX: Double,
        val wgsY: Double
    )

}

/*
{
    "parkingLots": [
        {
            "areaId": "3",
            "areaName": "八德區",
            "parkName": "大湳公有停車場",
            "totalSpace": 207,
            "surplusSpace": "112",
            "payGuide": "小型車-臨停:30/時，小型車-月租:3600/月",
            "introduction": "八德區公所管轄之公有停車場",
            "address": "廣福路42號(廣福段258地號)",
            "wgsX": 24.959,
            "wgsY": 121.2985,
            "parkId": "P-BD-001"
        }
    ]
}
*/