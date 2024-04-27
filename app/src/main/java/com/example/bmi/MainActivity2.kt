package com.example.bmi

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity2 : AppCompatActivity() {

// later assign value
//    private lateinit var mediaPlayer: MediaPlayer
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var seekBar: SeekBar
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        mediaPlayer = MediaPlayer.create(this,R.raw.goat_baa)
        val startSoundButton = findViewById<Button>(R.id.startSoundButton)
        val floatingButtonPlay = findViewById<FloatingActionButton>(R.id.fab_play)
        val floatingButtonPause = findViewById<FloatingActionButton>(R.id.fab_pause)
        val floatingButtonStop = findViewById<FloatingActionButton>(R.id.fab_stop)

        seekBar = findViewById<SeekBar>(R.id.seekBar)
        handler = Handler(Looper.getMainLooper())

        startSoundButton.setOnClickListener {
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this,R.raw.alex_goat)
            }
            mediaPlayer?.start()
        }

        floatingButtonPlay.setOnClickListener{
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this,R.raw.alex_goat)
                seekBarInit()
            }
            mediaPlayer?.start()
        }
        floatingButtonPause.setOnClickListener{
            mediaPlayer?.pause()
        }
        floatingButtonStop.setOnClickListener{
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0
        }
    }

    private fun seekBarInit() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) mediaPlayer?.seekTo(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        seekBar.max = mediaPlayer!!.duration
        val tvPlayed = findViewById<TextView>(R.id.tvplayed)
        val tvDue = findViewById<TextView>(R.id.tvdue)
        runnable = Runnable {
            seekBar.progress = mediaPlayer!!.currentPosition
            val playedTime = mediaPlayer!!.currentPosition/1000
            val durationTime = mediaPlayer!!.duration/1000
            tvPlayed.text = "$playedTime sec"
            val due = durationTime - playedTime
            tvDue.text = "$due sec"
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
    }
}