package com.tesan.belajarnavigationbuttomfloating.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tesan.belajarnavigationbuttomfloating.R
import com.tesan.belajarnavigationbuttomfloating.ResponseModel.DataItem
import kotlinx.android.synthetic.main.item_profile.view.*

class ProfilesAdapter(val items: List<DataItem>) : RecyclerView.Adapter<ProfilesAdapter.ViewHolder>() {

    private var onItemClickListener: onAdapterListener? = null

    interface onAdapterListener {
        fun onClick(list: DataItem)
    }

    fun setOnItemClick(onItemClickCalback: onAdapterListener){
        this.onItemClickListener = onItemClickCalback
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data : DataItem){
            with(itemView) {
                itemView.item_profiles.text = (items.indexOf(data)+1).toString()+". "+data.name
                itemView.contentProfiles.setOnClickListener { onItemClickListener?.onClick(data) }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_profile,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


}
