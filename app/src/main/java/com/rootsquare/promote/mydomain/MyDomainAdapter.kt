package com.rootsquare.promote.mydomain

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rootsquare.promote.model.Getlinks

class MyDomainAdapter(
    private val context: Context, private val mQuestions: List<Getlinks>,
    private val mRowLayout: Int
) : RecyclerView.Adapter<MyDomainAdapter.DomainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DomainHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DomainHolder, position: Int) {
        TODO("Not yet implemented")
    }


    class DomainHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    }
}



