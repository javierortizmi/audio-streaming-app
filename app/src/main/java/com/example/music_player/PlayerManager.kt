package com.example.music_player

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer

object PlayerManager {

    private lateinit var player: ExoPlayer

    fun initializePlayer(context: Context) {
        player = ExoPlayer.Builder(context).build()
    }

    fun releasePlayer() {
        player.release()
    }

    fun getPlayer(): ExoPlayer {
        return player
    }

    /* fun updatePlayer(player: ExoPlayer) {
        this.player.release()
        this.player = player
    } */

}