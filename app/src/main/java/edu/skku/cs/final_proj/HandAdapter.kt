package edu.skku.cs.final_proj

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.final_proj.gameobjects.Card

class HandAdapter(private val context: Context, val handlist : ArrayList<Card>) :
    RecyclerView.Adapter<HandAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val cardView: ImageView

        init{
            cardView = view.findViewById(R.id.entry_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return handlist.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.cardView.setImageResource(handlist[position].image)
        if(context is InGameActivity){
            viewHolder.cardView.setOnClickListener{
                (context as InGameActivity).gamethread.handactionOn(handlist[position])
            }
        }
    }
}