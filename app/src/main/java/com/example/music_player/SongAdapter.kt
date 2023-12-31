package com.example.music_player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class SongAdapter (private val songList: Songs): RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    private lateinit var myListener: OnItemClickListener
    private lateinit var dbRef: DatabaseReference

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        myListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.song_list_item, parent, false)
        return ViewHolder(itemView, myListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSong = songList[position]
        Picasso.get().load(currentSong.getImageUrl()).into(holder.ivSongImage)

        holder.tvSongName.text = currentSong.getTitle()
        holder.tvSongArtist.text = currentSong.getArtist()
        if (currentSong.getLike()) {
            holder.ibSongLike.setImageResource(R.drawable.like)
        } else {
            holder.ibSongLike.setImageResource(R.drawable.like_border)
        }
        holder.ibSongLike.setOnClickListener {
            if (currentSong.getLike()) {
                MainActivity.songList[position].setLike(false)
                holder.ibSongLike.setImageResource(R.drawable.like_border)
            } else {
                MainActivity.songList[position].setLike(true)
                holder.ibSongLike.setImageResource(R.drawable.like)
            }
            dbRef = FirebaseDatabase.getInstance().getReference("songs").child(currentSong.getMediaId()).child("like")
            dbRef.setValue(currentSong.getLike())
        }
    }

    override fun getItemCount(): Int {
        return songList.size
    }


    class ViewHolder(itemView: View, clickListener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        val tvSongName: TextView = itemView.findViewById(R.id.tvSongName)
        val tvSongArtist: TextView = itemView.findViewById(R.id.tvArtistName)
        val ivSongImage: ImageView = itemView.findViewById(R.id.ivSongImage)
        val ibSongLike: ImageButton = itemView.findViewById(R.id.ibSongLike)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}