package com.rootsquare.promote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rootsquare.promote.R
import com.rootsquare.promote.model.GetlinkItem

class CustomAdapter(private val mList: ArrayList<GetlinkItem>) :
    RecyclerView.Adapter<CustomAdapter.LinkHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_get_links, parent, false)

        return LinkHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: LinkHolder, position: Int) {

        val linkItems = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.textView.text = linkItems.name

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class LinkHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}
