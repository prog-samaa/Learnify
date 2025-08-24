package com.example.learnify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.view.View

class CourseAdapter(private val items: List<Course>) :
    RecyclerView.Adapter<CourseAdapter.VH>() {class VH(v: View) : RecyclerView.ViewHolder(v) {
    val banner: ImageView = v.findViewById(R.id.ivBanner)
    val title: TextView = v.findViewById(R.id.tvCourseTitle)
    val progress: ProgressBar = v.findViewById(R.id.progress)
    val percent: TextView = v.findViewById(R.id.tvPercent)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.banner.setImageResource(item.bannerRes)
        holder.title.text = item.title
        holder.progress.progress = item.progress
        holder.percent.text = "${item.progress}%"
    }

    override fun getItemCount() = items.size
}
