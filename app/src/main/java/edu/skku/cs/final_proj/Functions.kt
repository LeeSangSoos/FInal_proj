package edu.skku.cs.final_proj

import android.content.Context
import android.util.Log
import android.widget.Toast
import edu.skku.cs.final_proj.gameobjects.Card
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

public fun readfromfile(filename:String):ArrayList<Card>{
    lateinit var file:File
    if(filename == Constants.deckfilename)
        file = File(Constants.deckpath, Constants.deckfilename)
    else if(filename == Constants.carddataname)
        file = File(Constants.carddatapath, Constants.carddataname)
    var result = ArrayList<Card>()
    var filecontext = file.readText()
    filecontext = filecontext.replace("[", "")

    val lines = if(filecontext.isNotEmpty()){
        filecontext.split("]").filter{it.isNotBlank()}
    }
    else{
        return result
    }

    for(line in lines){
        val words = line.split(",")
        lateinit var card:Card
        if(CardType.valueOf(words[0]) == CardType.MONSTER){
            card = Card(
                CardType.valueOf(words[0].trimStart()),
                words[1].trimStart(),
                words[2].trimStart().toInt(),
                words[3].trimStart(),
                MonElement.valueOf(words[4].trimStart()),
                MonType.valueOf(words[5].trimStart()),
                words[6].trimStart().toInt(),
                words[7].trimStart().toInt(),
                words[8].trimStart().toInt()
            )
        }
        else if(CardType.valueOf(words[0]) == CardType.MAGIC){
            card = Card(
                CardType.valueOf(words[0]),
                words[1].trimStart(),
                words[2].trimStart().toInt(),
                words[3].trimStart(),
                MagicElement.valueOf(words[4].trimStart())
            )
        }
        else if(CardType.valueOf(words[0]) == CardType.TRAP){
            card = Card(
                CardType.valueOf(words[0]),
                words[1].trimStart(),
                words[2].trimStart().toInt(),
                words[3].trimStart(),
                TrapElement.valueOf(words[4].trimStart())
            )
        }
        result.add(card)
    }

    return result
}

public fun writetofile(name: String, cardlist:ArrayList<Card>):Int{
    lateinit var file:File
    if(name == Constants.deckfilename)
        file = File(Constants.deckpath, Constants.deckfilename)
    else if(name == Constants.carddataname)
        file = File(Constants.carddatapath, Constants.carddataname)
    else{
        Log.e("data", "writetofile: wrong file name")
        return 0
    }
    file.writeText("")
    for(card in cardlist){
        var cardData=ArrayList<String>()
        cardData.add(card.type.toString())
        cardData.add(card.name)
        cardData.add(card.image.toString())
        cardData.add(card.effect)
        when(card.type){
            CardType.MONSTER -> {
                cardData.add(card.monsElement.toString())
                cardData.add(card.monType.toString())
                cardData.add(card.level.toString())
                cardData.add(card.attack.toString())
                cardData.add(card.defense.toString())
            }
            CardType.MAGIC  -> {
                cardData.add(card.magicelement.toString())
            }
            CardType.TRAP -> {
                cardData.add(card.trapElement.toString())
            }
        }
        file.appendText((cardData).toString())
    }

    return 1
}

fun toastmsg(context: Context, msg: String){
    val t = Toast.makeText(context, msg, Toast.LENGTH_SHORT )
    t.show()
    GlobalScope.launch {
        delay(1000)
        t.cancel()
    }
}

fun <T> insertinnullarray(array: Array<T>, element: T): Int {
    for (i in array.indices) {
        if (array[i] == null) {
            array[i] = element
            return i
        }
    }
    Log.e("error","no null position to insert in array")
    return -1
}

fun <T> hasnullinarray(array: Array<T>): Boolean {
    for (entry in array) {
        if (entry == null) {
            return true
        }
    }
    return false
}