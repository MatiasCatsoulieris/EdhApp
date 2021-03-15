package android.example.com.matsusmagic.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.example.com.matsusmagic.R
import android.example.com.matsusmagic.databinding.ItemYtvideoBinding
import android.example.com.matsusmagic.model.YouTubeVideo
import android.example.com.matsusmagic.util.getProgressDrawable
import android.example.com.matsusmagic.util.loadImage
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_ytvideo.view.*


class YouTubeListAdapter(private val videoList: ArrayList<YouTubeVideo>) :
    RecyclerView.Adapter<YouTubeListAdapter.VideoViewHolder>(), OnCardListener {

    class VideoViewHolder(var view: ItemYtvideoBinding) : RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemYtvideoBinding>(
                inflater,
                R.layout.item_ytvideo,
                parent,
                false
            )
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.view.videoData = videoList[position]
        holder.view.videoIdtv.text = videoList[position].snippet?.resourceId?.get("videoId")
        holder.view.listener = this
        holder.view.videothumbnail.loadImage(
            videoList[position].snippet?.thumbnails?.get("default")?.url,
            getProgressDrawable(holder.view.videothumbnail.context)
        )

    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    fun updateCardList(newVideoList: List<YouTubeVideo>) {
        videoList.clear()
        videoList.addAll(newVideoList)
        notifyDataSetChanged()
    }

    override fun onCardClicked(v: View) {
        val videoId = v.videoIdtv.text
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$videoId"))
        try {
        startActivity(v.context, appIntent, null)
        } catch (ex: ActivityNotFoundException) {
        startActivity(v.context, webIntent, null)
        }
        super.onCardClicked(v)
    }

}