package com.example.mindpill

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), BlogItemClicked {

    private lateinit var mAdapter: BlogListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = BlogListAdapter(this)
        recyclerView.adapter = mAdapter
    }
    private fun fetchData() {
        val url = "https://saurav.tech/NewsAPI/everything/cnn.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val blogsJsonArray = it.getJSONArray("articles")
                val blogsArray = ArrayList<Blogs>()
                for(i in 0 until blogsJsonArray.length()) {
                    val blogsJsonObject = blogsJsonArray.getJSONObject(i)
                    val news = Blogs(
                        blogsJsonObject.getString("title"),
                        blogsJsonObject.getString("author"),
                        blogsJsonObject.getString("url"),
                        blogsJsonObject.getString("urlToImage")
                    )
                    blogsArray.add(news)
                }

                mAdapter.updateBlogs(blogsArray)
            },
            {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: Blogs) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}