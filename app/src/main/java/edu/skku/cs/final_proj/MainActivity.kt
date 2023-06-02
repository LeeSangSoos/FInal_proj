package edu.skku.cs.final_proj

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import edu.skku.cs.final_proj.gameobjects.Card
import edu.skku.cs.final_proj.gameobjects.GreatAngus
import edu.skku.cs.final_proj.gameobjects._7ColoredFish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.readFieldOrNull
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.hide()

        val carddata=findViewById<Button>(R.id.card_dic)
        val updatecardlist=findViewById<Button>(R.id.update)
        val editdeck=findViewById<Button>(R.id.editdeck_btn)
        val start=findViewById<Button>(R.id.start_btn)
        val close=findViewById<Button>(R.id.close_btn)

        val deckpath = this.filesDir
        Constants.deckpath = deckpath
        val deckfile = File(Constants.deckpath, Constants.deckfilename)
        deckfile.createNewFile()

        val carddatapath = this.filesDir
        Constants.carddatapath = carddatapath
        val carddatafile = File(Constants.carddatapath, Constants.carddataname)
        carddatafile.createNewFile()

        carddata.setOnClickListener{
            val startdictionary= Intent(this, DictionaryActivity::class.java)
            startActivity(startdictionary)
        }

        updatecardlist.setOnClickListener{
            val cardlist = Constants.cardlist_update
            writetofile(Constants.carddataname, cardlist)
        }

        editdeck.setOnClickListener{
            val cardlist= readfromfile(Constants.carddataname)
            if(cardlist.size<2){
                toastmsg(this, "cardlist is not made. click updatecardlist button")
            }
            else{
                val starteditdeck= Intent(this, editdeckactivity::class.java)
                startActivity(starteditdeck)
            }
        }

        start.setOnClickListener{
            val decklist= readfromfile(Constants.deckfilename)
            if(decklist.size<20){
                toastmsg(this, "deck too small. need at least 20 cards in deck")
            }
            else{
                val startgame= Intent(this, InGameActivity::class.java)
                startActivity(startgame)
            }
        }

        close.setOnClickListener{
            this.finish()
        }
    }
}