package com.rootsquare.promote.addlinks

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast


class LinksService  : Service(){

//    private var vm: MainViewModel
//    val repository = Repository()
//    val vmFactory = MainViewModelFactory(repository)
//     var activity: Activity? = null
//    private lateinit var lfOwner: LifecycleOwner
    private lateinit var player: MediaPlayer

//    init {
//        vm = MainViewModel(repository)
//        vm.getLinks()
//        lfOwner = this
//    }

    override fun onBind(intent: Intent?): IBinder? {
     return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /*vm.getLinksMutable.observe(this){
            val arrayList = it.toList()
            for (e in arrayList) {
                Log.d("value - ",e.toString())
                println(e.name)
                binding.web.loadUrl(e.name)
            }
        }*/

       /* player = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI)
        player.isLooping = true
        player.start()*/

        Toast.makeText(
            applicationContext, "This is a Service running in Background",
            Toast.LENGTH_SHORT
        ).show()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Toast.makeText(
            applicationContext, "The task removed from background",
            Toast.LENGTH_SHORT
        ).show()
        super.onTaskRemoved(rootIntent)
    }


}