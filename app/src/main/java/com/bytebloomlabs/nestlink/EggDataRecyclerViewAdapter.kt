package com.bytebloomlabs.nestlink

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EggDataRecyclerViewAdapter(
    private val values: MutableList<UserData.EggDataPoints>?): RecyclerView.Adapter<EggDataRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_egg_data, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        holder.idView.text = item?.id
        //ToDo add logic for parsing telemetry
//        holder.telemetryView.text = item?.telemetry
        holder.telemetryTimestampView.text = item?.telemetryTimestamp
        holder.eggTypeView.text = item?.eggType
    }

    override fun getItemCount(): Int = values?.size ?: 0


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image)
        val idView: TextView = view.findViewById(R.id.id)
        val telemetryView: TextView = view.findViewById(R.id.telemetry)
        val telemetryTimestampView: TextView = view.findViewById(R.id.telemetry_timestamp)
        val eggTypeView: TextView = view.findViewById(R.id.egg_type)
    }

}