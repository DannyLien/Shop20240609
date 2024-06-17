package com.tom.shop20240609

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.tom.shop20240609.databinding.ActivityParkingBinding
import java.net.URL

class ParkingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParkingBinding
    val TAG = ParkingActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParkingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val parking =
            "https://api.jsonserve.com/TyZaEe"
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
            binding.info.text = result

        }
    }

}