package com.example.mytoptenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytoptenapp.Feed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var call: Call<Feed?>
    private lateinit var feed: Feed
    private val BASE_URL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml"

    private lateinit var feedTitleTextView: TextView
    private lateinit var appsRecyclerView: RecyclerView
    private lateinit var updateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        feedTitleTextView = findViewById(R.id.F_title)

        appsRecyclerView = findViewById(R.id.appRV)
        appsRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        updateBtn = findViewById(R.id.updateBTN)
        updateBtn.setOnClickListener {
            CoroutineScope(IO).launch {
                setData()
            }
        }

        CoroutineScope(IO).launch {
            setData()
        }
    }

    private suspend fun setData() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
        call = retrofit.create(FeedAPIInterface::class.java).feed!!
        try {
            feed = call.execute().body()!!
            setUI()
        } catch (e: Exception) {
            withContext(Main) {
                Toast.makeText(this@MainActivity, "Something went wrong!!, please try again", Toast.LENGTH_SHORT).show()
            }
            e.printStackTrace()
        }
    }

    private suspend fun setUI() {
        withContext(Main) {
            feedTitleTextView.text = feed.title
            appsRecyclerView.adapter = Adapter(feed.entries!!)
        }
    }
}