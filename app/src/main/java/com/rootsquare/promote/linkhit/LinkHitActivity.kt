package com.rootsquare.promote.linkhit

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rootsquare.promote.R
import com.rootsquare.promote.Utils.MainViewModelFactory
import com.rootsquare.promote.Utils.Utils
import com.rootsquare.promote.adapter.CustomAdapter
import com.rootsquare.promote.addlinks.MainViewModel
import com.rootsquare.promote.apiclient.Repository
import com.rootsquare.promote.databinding.ActivityLinkHitBinding
import com.rootsquare.promote.model.GetlinkItem


class LinkHitActivity : AppCompatActivity() {

    private lateinit var hitViewModel: MainViewModel
    private lateinit var binding: ActivityLinkHitBinding
    private var currentUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val vmFactory = MainViewModelFactory(repository)
        hitViewModel = ViewModelProvider(this, vmFactory).get(MainViewModel::class.java)

        binding = ActivityLinkHitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<GetlinkItem>()
        val adapter = CustomAdapter(data)

        recyclerview.adapter = adapter

        if (Utils.checkForInternet(this)) {
            hitViewModel.getLinks()
        } else {
            Toast.makeText(
                this, "No Internet",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.imageView.setOnClickListener {
            binding.img.visibility = View.VISIBLE
//            val intent = Intent(this@LinkHitActivity, LinksService()::class.java)
//            startService(intent)

            // code to take activity in background and do process in background
            /*   val homeIntent = Intent(Intent.ACTION_MAIN)
               homeIntent.addCategory(Intent.CATEGORY_HOME)
               homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
               startActivity(homeIntent)*/

            hitViewModel.getLinksMutable.observe(this) {
                val arrayList = it.toList()
                for (e in arrayList) {
                    Log.d("value - ", e.toString())
                    println(e.name)

                    binding.web.loadUrl(e.name)
                    binding.web.webViewClient = object : WebViewClient() {
                        override fun onLoadResource(view: WebView?, url: String?) {
                            Toast.makeText(this@LinkHitActivity,"111",Toast.LENGTH_SHORT).show()
                            super.onLoadResource(view, url)

                        }

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            binding.web.visibility = View.INVISIBLE
                            Toast.makeText(this@LinkHitActivity,"222",Toast.LENGTH_SHORT).show()

                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            binding.img.visibility = View.INVISIBLE
                            Toast.makeText(this@LinkHitActivity,"333",Toast.LENGTH_SHORT).show()
                            currentUrl = url
                            currentUrl?.let { it1 -> Log.d("currentUrl = url= ", it1) }
                            binding.web.clearCache(true)
                            binding.web.clearHistory()
                        }


                    }
                    /*     val openURL = Intent(Intent.ACTION_VIEW)
                         openURL.data = Uri.parse(e.name)
                         startActivity(openURL)
     */
                    //        binding.web.webViewClient = LinksWebClient()
                }
            }
        }
    }
}