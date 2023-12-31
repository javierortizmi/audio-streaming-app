package com.example.music_player

class Song(
    private var artist: String = "",
    private var imageUrl: String = "",
    private var like: Boolean = false,
    private var mediaId: String = "",
    private var songUrl: String = "",
    private var title: String = ""
    ) {

    // Getters and Setters
    fun getArtist(): String {
        return artist
    }
    fun setArtist(artist: String) {
        this.artist = artist
    }
    fun getImageUrl(): String {
        return imageUrl
    }
    fun setImageUrl(imageUrl: String) {
        this.imageUrl = imageUrl
    }
    fun getLike(): Boolean {
        return like
    }
    fun setLike(like: Boolean) {
        this.like = like
    }
    fun getMediaId(): String {
        return mediaId
    }
    fun setMediaId(mediaId: String) {
        this.mediaId = mediaId
    }
    fun getSongUrl(): String {
        return songUrl
    }
    fun setSongUrl(songUrl: String) {
        this.songUrl = songUrl
    }
    fun getTitle(): String {
        return title
    }
    fun setTitle(title: String) {
        this.title = title
    }

    // toString()
    override fun toString(): String {
        return "Song ID: ${getMediaId()}, Title: ${getTitle()}, Artist: ${getArtist()}, Favourite: ${getLike()}"
    }

}