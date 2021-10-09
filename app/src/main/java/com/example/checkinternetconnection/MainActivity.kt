package com.example.checkinternetconnection

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity() {
    lateinit var main_constraintLayout: ConstraintLayout
    lateinit var video_info: Array<Array<String>>
    lateinit var button_recycleView: RecyclerView

    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var player: YouTubePlayer
    private var currentVideo = 0
    private var timeStamp = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        if (activeNetwork?.isConnectedOrConnecting == true) {
            Toast.makeText(this,"Internet Connect Successfully",Toast.LENGTH_SHORT).show()

            video_info = arrayOf(
                arrayOf("Numbers Game","VZnLB-q55tw"), arrayOf("Guess The Phrase","qzcGfN9S_QY") ,
                arrayOf("Calculator","c6m9XkE4YVo"), arrayOf("Study App","sCUC1tvsHFY"),
                arrayOf("JSON App","c6m9XkE4YVo"), arrayOf("Weather App","4a6TdwIb7H4")
            )

            youTubePlayerView = findViewById(R.id.youtube_player)
            youTubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    player = youTubePlayer
                    player.loadVideo(video_info[currentVideo][1], timeStamp)

                    button_recycleView = findViewById(R.id.videos_recycleView)
                    button_recycleView.adapter = RV_VedioAdapter(video_info, player)
                    button_recycleView.layoutManager = LinearLayoutManager(this@MainActivity)
                    button_recycleView.setHasFixedSize(true)
                }
            })
        }
        else
        {
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            youTubePlayerView.enterFullScreen()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            youTubePlayerView.exitFullScreen()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentVideo", currentVideo)
        outState.putFloat("timeStamp", timeStamp)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        currentVideo = savedInstanceState.getInt("currentVideo", 0)
        timeStamp = savedInstanceState.getFloat("timeStamp", 0f)
    }
}