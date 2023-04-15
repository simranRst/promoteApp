package com.rootsquare.promote

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rootsquare.promote.databinding.ActivityAddLinksScreenBinding
import com.rootsquare.promote.addlinks.HomeActivity
import com.rootsquare.promote.linkhit.LinkHitActivity
import com.rootsquare.promote.mydomain.MyDomainActivity

class AddLinksScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAddLinksScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddLinksScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarUI)

        binding.addLinks.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.goBtn.setOnClickListener {
            val intent = Intent(this, LinkHitActivity::class.java)
            startActivity(intent)
        }
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
                val intent= Intent(this@AddLinksScreen, MyDomainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "domain Clicked", Toast.LENGTH_SHORT).show()}
            R.id.addnew -> {Toast.makeText(this, "Add click", Toast.LENGTH_SHORT).show()}
            R.id.share -> {
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this app:")
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))
                Toast.makeText(this, "share click", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "share click", Toast.LENGTH_SHORT).show()}
            R.id.logout -> {Toast.makeText(this, "logout click", Toast.LENGTH_SHORT).show()}
        }
        return super.onOptionsItemSelected(item)
    }
}