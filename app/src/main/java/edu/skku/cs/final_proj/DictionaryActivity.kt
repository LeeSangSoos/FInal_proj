package edu.skku.cs.final_proj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import coil.load
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException

class DictionaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        val client = OkHttpClient()
        val host = "https://db.ygoprodeck.com/api/v7/cardinfo.php?name="
        var name:String? = "Baby Dragon"

        val cardnametext = findViewById<EditText>(R.id.cardname)
        val searchbtn = findViewById<Button>(R.id.searchbtn)
        val cardimage = findViewById<ImageView>(R.id.cardimage)
        val backtomain = findViewById<Button>(R.id.returntomain)

        searchbtn.setOnClickListener{
            name = cardnametext?.text?.toString()

            val req = Request.Builder().url(host + name).build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use{
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        val str = response.body!!.string()
                        val data = Gson().fromJson(str, CardData::class.java)

                        CoroutineScope(Dispatchers.Main).launch {
                            val imgurl=data.data[0].card_images[0].image_url
                            cardimage.load(imgurl)
                        }
                    }
                }
            })

        }

        backtomain.setOnClickListener{
            this.finish()
        }
    }
}




