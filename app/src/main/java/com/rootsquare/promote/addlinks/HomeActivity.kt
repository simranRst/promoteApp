package com.rootsquare.promote.addlinks

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rootsquare.promote.R
import com.rootsquare.promote.Utils.MainViewModelFactory
import com.rootsquare.promote.apiclient.Repository
import com.rootsquare.promote.databinding.ActivityHomeBinding
import com.rootsquare.promote.linkhit.LinkHitActivity
import com.rootsquare.promote.mydomain.MyDomainActivity
import java.util.regex.Matcher

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var loader: Boolean = false
    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val vmFactory = MainViewModelFactory(repository)
        vm = ViewModelProvider(this, vmFactory).get(MainViewModel::class.java)

        //    vm.demo("simran", "developer")

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarUI)
        /*if(!loader){
            binding.loader.visibility = View.INVISIBLE
            binding.loadText.visibility = View.INVISIBLE
        }*/


        binding.enterUrl.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int) {
                val m: Matcher = vm.p.matcher(s)
                if (binding.enterUrl.text.isNotEmpty() && m.matches()) {
                    binding.indicator.isEnabled = true
                }
                if (binding.enterUrl.text.isEmpty() || !m.matches())
                 {
                    binding.indicator.isEnabled = false
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })

//  *************** temporary commented this section it needs to uncomment later **************//
      /*  if (Utils.checkForInternet(this)) {
            vm.getLinks()

        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
        }*/

        binding.indicator.setOnClickListener {
            vm.postLinks(binding.enterUrl.text.toString())
            binding.enterUrl.text.clear()
            /*binding.loader.visibility = View.VISIBLE
            binding.loadText.visibility = View.VISIBLE
            binding.loadText.text= resources.getText(R.string.loadingText)
            Glide.with(this).load(R.drawable.running).into(binding.loader)*/
        }

//        val quotesApi = ApiHelper.getInstance().create(ApiInterface::class.java)
//        // launching a new coroutine
//        GlobalScope.launch {
//            val result = quotesApi. getLinks()
//            Log.d("results: ", result.body().toString())
//        }

       /* binding.goText.setOnClickListener {

//            binding.goText.visibility = View.GONE
//            binding.homeTitle.visibility = View.GONE
//            binding.enterUrl.visibility = View.GONE
//            binding.indicator.visibility = View.GONE
//
//            val intent = Intent(this@HomeActivity, LinksService()::class.java)
//            startService(intent)
            val intent = Intent(this@HomeActivity, LinkHitActivity()::class.java)
            startActivity(intent)

            // code to take activity in background and do process in background
//            val homeIntent = Intent(Intent.ACTION_MAIN)
//            homeIntent.addCategory(Intent.CATEGORY_HOME)
//            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(homeIntent)

//            vm.getLinksMutable.observe(this) {
//                val arrayList = it.toList()
//                for (e in arrayList) {
//                    Log.d("value - ", e.toString())
//                    println(e.name)
//                    binding.web.loadUrl(e.name)
//           //        binding.web.webViewClient = LinksWebClient()
//                }
//
//            }
        }*/
    }
    // method to inflate the options menu when
    // the user opens the menu for the first time
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // methods to control the operations that will
    // happen when user clicks on the action buttons
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myProfile -> {Toast.makeText(this, "profile Clicked", Toast.LENGTH_SHORT).show()}
            R.id.myDomain -> {
                val intent= Intent(this@HomeActivity, MyDomainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "domain Clicked", Toast.LENGTH_SHORT).show()}
            R.id.addnew -> {Toast.makeText(this, "Add click", Toast.LENGTH_SHORT).show()}
            R.id.share -> {
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this app:")
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))
                Toast.makeText(this, "share click", Toast.LENGTH_SHORT).show()}
            R.id.logout -> {Toast.makeText(this, "logout click", Toast.LENGTH_SHORT).show()}
        }
        return super.onOptionsItemSelected(item)
    }
}