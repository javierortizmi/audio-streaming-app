package com.example.music_player

class Songs: ArrayList<Song>() {

    override fun toString(): String {
        var answer = ""
        for (song in this) {
            answer += song.toString()
        }
        return answer
    }
}