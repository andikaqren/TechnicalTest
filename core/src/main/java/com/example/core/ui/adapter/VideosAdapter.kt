package com.example.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.business.data.remote.model.RemoteVideo
import com.example.core.databinding.ItemProductBinding
import com.example.core.databinding.ItemVideoBinding
import com.example.core.ui.listener.ItemClickListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideosAdapter :
    BaseAdapter<RemoteVideo, VideosAdapter.ViewHolder>() {
    inner class ViewHolder(listener: ItemClickListener<RemoteVideo>, view: ItemVideoBinding) :
        BaseHolder<RemoteVideo>(listener, view.root) {
        val bind = view
        fun bind() = with(itemView) {
            itemData?.let {

                bind.youtubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val videoId = it.key
                        youTubePlayer.loadVideo(videoId, 0f)
                        youTubePlayer.pause()
                    }
                })
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            listener,
            itemBinding
        )
    }

    override fun bindViewHolder(holder: ViewHolder?, data: RemoteVideo?, position: Int) {
        holder?.bind()
    }

}