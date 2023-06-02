package edu.skku.cs.final_proj

import android.annotation.SuppressLint
import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.final_proj.gameobjects.Card

class CardEntryAdapter(private val context: Context, val cardlist : ArrayList<Card>,
val listname: String) :
    RecyclerView.Adapter<CardEntryAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val cardView: ImageView
        val cardname: TextView
        val cardattck: TextView
        val carddefense: TextView

        init{
            cardView = view.findViewById(R.id.entry_image)
            cardname = view.findViewById(R.id.entry_name)
            cardattck = view.findViewById(R.id.entry_attack)
            carddefense = view.findViewById(R.id.entry_defense)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardlistentry, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cardlist.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.cardView.setImageResource(cardlist[position].image)
        viewHolder.cardname.setText(cardlist[position].name)
        if(cardlist[position].type == CardType.MONSTER){
            val atk="ATK: "+cardlist[position].attack.toString()
            val def="DEF: "+cardlist[position].defense.toString()
            viewHolder.cardattck.setText(atk)
            viewHolder.carddefense.setText(def)
        }
        else if(cardlist[position].type == CardType.MAGIC){
            viewHolder.cardattck.setText(cardlist[position].magicelement.toString())
            viewHolder.carddefense.setText("")
        }
        else{
            viewHolder.cardattck.setText(cardlist[position].trapElement.toString())
            viewHolder.carddefense.setText("")
        }

        if(context is editdeckactivity){
            viewHolder.cardView.setOnClickListener{
                if(listname == Constants.deckfilename){
                    (context as editdeckactivity).removefromdeck(cardlist[position])
                }
                else if(listname == Constants.carddataname){
                    (context as editdeckactivity).addtodeck(cardlist[position])
                }
            }
            viewHolder.cardname.setOnClickListener{
                val img = (context as editdeckactivity).findViewById<ImageView>(R.id.detailimg)
                img.setImageResource(cardlist[position].image)
                val showdetail = (context as editdeckactivity).findViewById<LinearLayout>(R.id.detailshow)
                showdetail.visibility= View.VISIBLE
            }
            viewHolder.cardattck.setOnClickListener{
                val img = (context as editdeckactivity).findViewById<ImageView>(R.id.detailimg)
                img.setImageResource(cardlist[position].image)
                val showdetail = (context as editdeckactivity).findViewById<LinearLayout>(R.id.detailshow)
                showdetail.visibility= View.VISIBLE
            }
            viewHolder.carddefense.setOnClickListener{
                val img = (context as editdeckactivity).findViewById<ImageView>(R.id.detailimg)
                img.setImageResource(cardlist[position].image)
                val showdetail = (context as editdeckactivity).findViewById<LinearLayout>(R.id.detailshow)
                showdetail.visibility= View.VISIBLE
            }
        }
    }
}