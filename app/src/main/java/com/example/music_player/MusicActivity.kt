package com.example.music_player

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class MusicActivity: AppCompatActivity() {

    private lateinit var ibDownArrow: ImageButton
    private lateinit var ivCurrentSongImage: ImageView
    private lateinit var tvCurrentSongTitle: TextView
    private lateinit var tvArtistName:TextView
    private lateinit var ibSongLike: ImageButton
    private lateinit var ibPlayPause: ImageButton
    private lateinit var songList: Songs
    private lateinit var player: ExoPlayer
    private lateinit var position: SeekBar
    private lateinit var tvElapsed: TextView
    private lateinit var tvRemaining: TextView
    private lateinit var ibPreviousSong: ImageButton
    private lateinit var ibNextSong: ImageButton

    private lateinit var dbRef: DatabaseReference

    private var totalTime = 0

    private lateinit var adView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        player = PlayerManager.getPlayer()

        ibDownArrow = findViewById(R.id.down_arrow)
        ivCurrentSongImage = findViewById(R.id.ivCurrentSongImage)
        tvCurrentSongTitle = findViewById(R.id.tvCurrentSongTitle)
        tvArtistName = findViewById(R.id.tvArtistName)
        ibSongLike = findViewById(R.id.ibSongLike)
        ibPlayPause = findViewById(R.id.ibPlayPause)
        tvElapsed = findViewById(R.id.elapsed)
        tvRemaining = findViewById(R.id.remaining)
        ibPreviousSong = findViewById(R.id.ibPreviousSong)
        ibNextSong = findViewById(R.id.ibNextSong)

        position = findViewById(R.id.position)

        songList = MainActivity.songList

        adView = findViewById(R.id.adView)

        updateSongUI()

        // Set up listeners
        setUpListeners()

        Thread {
            while (true) {
                runOnUiThread {
                    updateSeekBar()
                }
                Thread.sleep(1000)
            }
        }.start()
    }

    private fun updateSeekBar() {
        totalTime = player.duration.toInt()
        position.max = totalTime
        val currentPosition = player.currentPosition
        position.progress = currentPosition.toInt()
        val elapsedTime = createTimeLabel(currentPosition)
        tvElapsed.text = elapsedTime
        val remainingTime = createTimeLabel(totalTime - currentPosition)
        tvRemaining.text = getString(R.string.remainingTime, remainingTime)
    }

    private fun createTimeLabel(time: Long): String {
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        var timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    private fun updateSongUI() {
        // Set song image
        Picasso.get().load(songList[player.currentMediaItemIndex].getImageUrl()).into(ivCurrentSongImage)
        // Set song title
        tvCurrentSongTitle.text = songList[player.currentMediaItemIndex].getTitle()
        // Set song artist
        tvArtistName.text = songList[player.currentMediaItemIndex].getArtist()
        // Set song like
        if (songList[player.currentMediaItemIndex].getLike()) {
            ibSongLike.setImageResource(R.drawable.like)
        } else {
            ibSongLike.setImageResource(R.drawable.like_border)
        }
        // Set play/pause
        if (player.isPlaying) {
            ibPlayPause.setImageResource(R.drawable.pause)
        } else {
            ibPlayPause.setImageResource(R.drawable.play)
        }

        val adRequest = AdRequest.Builder().build()
        // Load the ad with the request.
        adView.loadAd(adRequest)

        updateSeekBar()
    }

    private fun setUpListeners() {
        player.addListener(
            object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    if (player.isPlaying)
                        ibPlayPause.setImageResource(R.drawable.pause)
                    else
                        ibPlayPause.setImageResource(R.drawable.play)
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    updateSongUI()
                }
            }
        )

        // Play/pause button handler
        ibPlayPause.setOnClickListener {
            // When music is playing, change to pause and pause music
            // Else change to play and play music
            if (player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
        }

        ibPreviousSong.setOnClickListener {
            player.seekToPreviousMediaItem()
            player.prepare()
            player.play()
        }

        ibNextSong.setOnClickListener {
            player.seekToNextMediaItem()
            player.prepare()
            player.play()
        }

        // Like button handler
        ibSongLike.setOnClickListener {
            if (songList[player.currentMediaItemIndex].getLike()) {
                songList[player.currentMediaItemIndex].setLike(false)
                ibSongLike.setImageResource(R.drawable.like_border)
            } else {
                songList[player.currentMediaItemIndex].setLike(true)
                ibSongLike.setImageResource(R.drawable.like)
            }
            dbRef = FirebaseDatabase.getInstance()
                .getReference("songs")
                .child(songList[player.currentMediaItemIndex].getMediaId())
                .child("like")
            dbRef.setValue(songList[player.currentMediaItemIndex].getLike())
        }

        // Down arrow button listener
        ibDownArrow.setOnClickListener {
            adView.destroy()
            // Update songList
            updateSongList()
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finishAfterTransition()
        }

        position.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // User has manually changed seek bar position
                    player.seekTo(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Called when the user starts touching the seek bar
                player.pause() // Pause the player while seeking
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Called when the user stops touching the seek bar
                player.play() // Resume playback after seeking
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        @Suppress("DEPRECATION")
        super.onBackPressed()
        adView.destroy()
        // Update songList
        updateSongList()
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finishAfterTransition()
    }

    override fun onPause() {
        super.onPause()
        val pref : SharedPreferences =
            this.getSharedPreferences(this.packageName + "_preferences", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = pref.edit()
        editor.putInt(MainActivity.CURRENT_SONG, player.currentMediaItemIndex)
        editor.apply()
    }


    private fun updateSongList() {
        MainActivity.songList = songList
    }
}