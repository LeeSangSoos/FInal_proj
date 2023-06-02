package edu.skku.cs.final_proj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.final_proj.gameobjects.Card
import edu.skku.cs.final_proj.gameobjects.GreatAngus
import edu.skku.cs.final_proj.gameobjects._7ColoredFish
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class editdeckactivity : AppCompatActivity() {

    lateinit var decklist:ArrayList<Card>
    lateinit var deckview:RecyclerView
    lateinit var decksize:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editdeckactivity)

        val cardlist:ArrayList<Card> = readfromfile(Constants.carddataname)
        decklist = readfromfile(Constants.deckfilename)

        val cardlistview = findViewById<RecyclerView>(R.id.cardlist)
        deckview = findViewById(R.id.decklist)
        val savebtn = findViewById<Button>(R.id.savebtn)
        decksize = findViewById(R.id.decksize)
        val detailshow = findViewById<LinearLayout>(R.id.detailshow)
        val backtoscreen = findViewById<Button>(R.id.backtoscreen)
        detailshow.visibility= View.GONE

        deckview.adapter = CardEntryAdapter(this, decklist, Constants.deckfilename)
        cardlistview.adapter = CardEntryAdapter(this, cardlist, Constants.carddataname)
        decksize.setText(decklist.size.toString())

        savebtn.setOnClickListener{
            writetofile(Constants.deckfilename, decklist)
            if(decklist.size>=20){
                toastmsg(this, "Deck Saved")
            }
            else{
                toastmsg(this, "Deck Saved: The deck should have a minimum of 20 cards to be playable.")
            }
        }

        backtoscreen.setOnClickListener{
            detailshow.visibility= View.GONE
        }
    }

    public fun addtodeck(card: Card){
        if(decklist.size < 30){
            var max3cards= 0
            for(entry in decklist){
                if(entry.name == card.name)
                    max3cards++
            }
            if(max3cards == 3){
                toastmsg(this, "maximum number of same card is 3")
                return
            }
            else{
                decklist.add(card)
                deckview.adapter = CardEntryAdapter(this, decklist, Constants.deckfilename)
                decksize.setText(decklist.size.toString())
            }
        }
        else{
            toastmsg(this, "The Deck can have maximum 30 cards")
            return
        }
    }
    public fun removefromdeck(card: Card){
        if(decklist.size <= 0){
            Toast.makeText(this, "no card to remove",Toast.LENGTH_SHORT).show()
            return
        }
        else{
            decklist.remove(card)
            deckview.adapter = CardEntryAdapter(this, decklist, Constants.deckfilename)
            decksize.setText(decklist.size.toString())
        }

    }
}