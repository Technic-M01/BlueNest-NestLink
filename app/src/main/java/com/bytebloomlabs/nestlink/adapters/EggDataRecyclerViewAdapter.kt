package com.bytebloomlabs.nestlink.adapters

import android.content.Context
import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.bytebloomlabs.nestlink.R
import com.bytebloomlabs.nestlink.models.UserData
import com.google.android.material.card.MaterialCardView

class EggDataRecyclerViewAdapter(
    private val values: MutableList<UserData.EggDataPoints>?): RecyclerView.Adapter<EggDataRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_egg_data, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)

        val ctx: Context = holder.itemView.context

        val iconRes = when (item?.eggType) {
            ctx.getString(R.string.egg_type_temp) -> R.drawable.ic_egg_temp
            ctx.getString(R.string.egg_type_weather) -> R.drawable.ic_egg_weather
            "TestEgg" -> R.drawable.ic_egg_light

            else -> R.drawable.ic_egg_undefined
        }

        val telem = item?.telemetry?.toString(Charsets.UTF_8)

        holder.eggTypeIconIv.setImageResource(iconRes)
        holder.idTv.text = item?.id
        //ToDo add logic for parsing telemetry
        holder.telemetryTv.text = telem
        holder.telemetryTimestampTv.text = item?.telemetryTimestamp
        holder.eggTypeTv.text = item?.eggType

        with (holder) {
            val cv = cardView

            holder.itemView.setOnClickListener {
                Log.i("Backend", ">> on click for: ${item?.eggType} - ${item?.telemetryTimestamp} ")

                if (holder.extraDetailsGp.visibility == View.VISIBLE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(cv, AutoTransition())
                    }
                    holder.extraDetailsGp.visibility = View.GONE
                    expandArrowIv.setImageResource(R.drawable.ic_arrow_down)
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(cv, AutoTransition())
                    }
                    holder.extraDetailsGp.visibility = View.VISIBLE
                    expandArrowIv.setImageResource(R.drawable.ic_arrow_up)
                }
            }
        }



    }

    override fun getItemCount(): Int = values?.size ?: 0

    private fun setContext(ctx: Context) {
        mContext = ctx
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eggTypeIconIv: ImageView = view.findViewById(R.id.iv_egg_type_icon)
        val idTv: TextView = view.findViewById(R.id.tv_id)
        val telemetryTv: TextView = view.findViewById(R.id.tv_telemetry)
        val telemetryTimestampTv: TextView = view.findViewById(R.id.tv_telemetry_timestamp)
        val eggTypeTv: TextView = view.findViewById(R.id.tv_egg_type)

        val cardView: MaterialCardView = view.findViewById(R.id.cv_egg_data)
        val extraDetailsGp: Group = view.findViewById(R.id.gp_extra_details)
        val expandArrowIv: ImageView = view.findViewById(R.id.iv_expand_arrow)
    }

}