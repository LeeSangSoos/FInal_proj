package edu.skku.cs.final_proj

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.final_proj.gameobjects.Card
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class GameThread(val context: Context): Thread() {
    //time
    private val FPS:Long = 30
    private var running = false

    //region needs
    private var turn:Turn = Turn.DRAW
    private var myturn = true
    private val gameactivity = context as InGameActivity
    val enemyturncolor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
    val myturncolor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blue))
    private var totalturn = 0
    private var working = false
    var turnchangerequest = false
    private var didnormalsummon = false
    //endregion needs

    //region findview
    val turnbtn = gameactivity.findViewById<Button>(R.id.turnbtn)
    lateinit var mydecklist:ArrayList<Card>
    private val handaction = gameactivity.findViewById<LinearLayout>(R.id.handaction)
    private val myhandlist = gameactivity.handlist
    private val decksize = gameactivity.findViewById<TextView>(R.id.decksize)
    val cardlistviewer = gameactivity.findViewById<RecyclerView>(R.id.cardlistviewer)
    val detailcardview=gameactivity.findViewById<ImageView>(R.id.detailcard)
    val lowgravesizeview = gameactivity.findViewById<TextView>(R.id.lowgravesize)
    val topmons1 = gameactivity.findViewById<ImageView>(R.id.topmon1)
    val topmons2 = gameactivity.findViewById<ImageView>(R.id.topmon2)
    val topmons3 = gameactivity.findViewById<ImageView>(R.id.topmon3)
    val topmagic1 = gameactivity.findViewById<ImageView>(R.id.topmagic1)
    val topmagic2 = gameactivity.findViewById<ImageView>(R.id.topmagic2)
    val topmagic3 = gameactivity.findViewById<ImageView>(R.id.topmagic3)
    val lowmons1 = gameactivity.findViewById<ImageView>(R.id.lowmon1)
    val lowmons2 = gameactivity.findViewById<ImageView>(R.id.lowmon2)
    val lowmons3 = gameactivity.findViewById<ImageView>(R.id.lowmon3)
    val lowmagic1 = gameactivity.findViewById<ImageView>(R.id.lowmagic1)
    val lowmagic2 = gameactivity.findViewById<ImageView>(R.id.lowmagic2)
    val lowmagic3 = gameactivity.findViewById<ImageView>(R.id.lowmagic3)
    val topfieldzone = gameactivity.findViewById<ImageView>(R.id.topfield)
    val lowfieldzone = gameactivity.findViewById<ImageView>(R.id.lowfield)
    val fieldaction = gameactivity.findViewById<LinearLayout>(R.id.fielduse)
    val attackbtn = gameactivity.findViewById<Button>(R.id.attackbtn)
    val fielduseview = gameactivity.findViewById<LinearLayout>(R.id.fielduse)
    //endregion findview

    //region field lists
    var top_monsfield:Array<Card?> = arrayOfNulls(3)
    var top_magicfield:Array<Card?> = arrayOfNulls(3)
    var top_fieldmagic:Card? = null
    var top_deck = ArrayList<Card>()
    var top_grave = ArrayList<Card>()
    var top_extra = ArrayList<Card>()

    var low_monsfield:Array<Card?> = arrayOfNulls(3)
    var low_magicfield:Array<Card?> = arrayOfNulls(3)
    var low_fieldmagic:Card? = null
    var low_grave = ArrayList<Card>()
    var low_extra = ArrayList<Card>()
    //endregion field lists

    public override fun run(){
        var loopstart:Long
        var loopend:Long
        var lefttime:Long
        while(running){
            loopstart = System.currentTimeMillis()
            //region game loop
            if(myturn){
                when(turn){
                    Turn.DRAW ->{
                        totalturn++
                        gameactivity.runOnUiThread{
                            turnbtn.backgroundTintList = myturncolor
                        }
                        if(totalturn>1){
                            draw()
                        }
                        didnormalsummon=false
                        changepage()
                    }
                    Turn.STANDBY ->{
                        changepage()
                    }
                    Turn.MAIN ->{

                    }
                    Turn.BATTLE ->{

                    }
                    Turn.END ->{

                        changepage()
                    }
                }
            }
            else{
                when(turn){
                    Turn.DRAW ->{
                        totalturn++
                        gameactivity.runOnUiThread{
                            turnbtn.backgroundTintList = enemyturncolor
                        }
                    }
                    Turn.STANDBY ->{

                    }
                    Turn.MAIN ->{

                    }
                    Turn.BATTLE ->{

                    }
                    Turn.END ->{

                    }
                }
            }
            //endregion game loop
            loopend = System.currentTimeMillis()
            lefttime = (1000/FPS) - (loopend - loopstart)
            try{
                sleep(lefttime)
            }
            catch (_: Exception){
                Log.e("gamethread", "cannot sleep")
            }
        }
    }

    public override fun start() {
        super.start()
        running = true
        mydecklist=gameactivity.decklist
    }

    public fun end(){
        running=false
    }

    public fun handactionOn(card: Card){
        if(!working){
            gameactivity.runOnUiThread{
                gameactivity.showhandaction(card)
            }
        }
    }

    private fun draw(){
        turnoffotheractions()
        if(mydecklist.isEmpty()){
            //loose
        }
        else{
            mydecklist[0].posistion=CardPosition.HAND
            myhandlist.add(mydecklist[0])
            mydecklist.removeAt(0)
            val sizestring="deck:"+mydecklist.size
            gameactivity.runOnUiThread{
                decksize.setText(sizestring)
                gameactivity.refreshmyhand(myhandlist)
            }
        }
        turnonotheractions()
    }

    private fun turnoffotheractions(){
        while(working){
            toastmsg(gameactivity, "other work is running")
        }
        working=true
        gameactivity.runOnUiThread{
            handaction.visibility = View.GONE
            cardlistviewer.visibility = View.GONE
            detailcardview.visibility = View.GONE
            fielduseview.visibility=View.GONE
        }
    }

    private fun turnonotheractions(){

        working=false
    }

    fun changepage(){
        turnoffotheractions()
        gameactivity.runOnUiThread{
            toastmsg(gameactivity, "next page -> ")
        }
        sleep(1000)
        when(turn){
            Turn.DRAW ->{
                turn=Turn.STANDBY
                gameactivity.runOnUiThread{
                    turnbtn.setText("STANBY")
                }
            }
            Turn.STANDBY ->{
                turn=Turn.MAIN
                gameactivity.runOnUiThread{
                    turnbtn.setText("MAIN")
                }
            }
            Turn.MAIN ->{
                turn=Turn.BATTLE
                gameactivity.runOnUiThread{
                    turnbtn.setText("BATTLE")
                }
            }
            Turn.BATTLE ->{
                turn=Turn.END
                gameactivity.runOnUiThread{
                    turnbtn.setText("END")
                }
            }
            Turn.END ->{
                changemyturn()
                turn=Turn.DRAW
                gameactivity.runOnUiThread{
                    turnbtn.setText("DRAW")
                }
            }
        }
        turnonotheractions()
    }

    fun changemyturn(){
        if(myturn){
            myturn=false
            //send request to enemy
        }
        else{
            //got request from enemy
            myturn=true
        }
    }

    fun normalsummon(targetcard:Card){
        turnoffotheractions()
        if(didnormalsummon){
            toastmsg(gameactivity, "you have done normal summon this turn")
        }
        else{
            if(hasnullinarray(low_monsfield)){
                val pos = insertinnullarray(low_monsfield, targetcard)
                if(pos==0)
                    low_monsfield[pos]!!.posistion = CardPosition.MONSTERZONE1
                else if(pos==1)
                    low_monsfield[pos]!!.posistion = CardPosition.MONSTERZONE2
                else
                    low_monsfield[pos]!!.posistion = CardPosition.MONSTERZONE3
                val iterator = myhandlist.iterator()
                while (iterator.hasNext()) {
                    val card = iterator.next()
                    if (card.name == targetcard.name) {
                        iterator.remove()
                        break
                    }
                }

                gameactivity.runOnUiThread{
                    gameactivity.refreshmyhand(myhandlist)
                    gameactivity.updatemymonsterzone(low_monsfield)
                }
                didnormalsummon = true
            }
            else{
                toastmsg(gameactivity, "your monster zone is full")
            }
        }
        turnonotheractions()
    }

    fun specialsummon(targetcard:Card){
        turnoffotheractions()
        if(hasnullinarray(low_monsfield)){
            insertinnullarray(low_monsfield, targetcard)
            for(card in myhandlist){
                if(card.name == targetcard.name){
                    myhandlist.remove(card)
                    break
                }
            }
            gameactivity.runOnUiThread{
                gameactivity.refreshmyhand(myhandlist)
                gameactivity.updatemymonsterzone(low_monsfield)
            }
        }
        else{
            toastmsg(gameactivity, "your monster zone is full")
            return
        }
        turnonotheractions()
    }

    fun set(targetcard:Card){
        turnoffotheractions()
        if(targetcard.type==CardType.MONSTER){
            if(didnormalsummon){
                toastmsg(gameactivity, "you have done normal summon this turn")
            }
            else{
                if(hasnullinarray(low_monsfield)){
                    targetcard.fieldimg = Zoneimg.SET
                    val pos = insertinnullarray(low_monsfield, targetcard)
                    if(pos==0)
                        low_monsfield[pos]!!.posistion = CardPosition.MONSTERZONE1
                    else if(pos==1)
                        low_monsfield[pos]!!.posistion = CardPosition.MONSTERZONE2
                    else
                        low_monsfield[pos]!!.posistion = CardPosition.MONSTERZONE3
                    val iterator = myhandlist.iterator()
                    while (iterator.hasNext()) {
                        val card = iterator.next()
                        if (card.name == targetcard.name) {
                            iterator.remove()
                            break
                        }
                    }
                    gameactivity.runOnUiThread{
                        gameactivity.refreshmyhand(myhandlist)
                        gameactivity.updatemymonsterzone(low_monsfield)
                    }
                    didnormalsummon = true
                }
                else{
                    toastmsg(gameactivity, "your monster zone is full")
                }
            }
        }
        else{
            if(targetcard.magicelement==MagicElement.FIELD){
                if(low_fieldmagic != null) {
                    low_fieldmagic!!.posistion=CardPosition.GRAVE
                    low_grave.add(low_fieldmagic!!)
                }
                targetcard.posistion=CardPosition.FIELDZONE
                targetcard.fieldimg = Zoneimg.SET
                low_fieldmagic = targetcard
                val iterator = myhandlist.iterator()
                while (iterator.hasNext()) {
                    val card = iterator.next()
                    if (card.name == targetcard.name) {
                        iterator.remove()
                        break
                    }
                }
                gameactivity.runOnUiThread {
                    gameactivity.refreshmyhand(myhandlist)
                    gameactivity.updatemyfieldzone(low_fieldmagic!!)
                    lowgravesizeview.text = low_grave.size.toString()
                }
            }
            else{
                if(hasnullinarray(low_magicfield)) {
                    targetcard.fieldimg = Zoneimg.SET
                    val pos = insertinnullarray(low_magicfield, targetcard)
                    if(pos==0)
                        low_magicfield[pos]!!.posistion = CardPosition.MAGICZONE1
                    else if(pos==1)
                        low_magicfield[pos]!!.posistion = CardPosition.MAGICZONE2
                    else
                        low_magicfield[pos]!!.posistion = CardPosition.MAGICZONE3
                    for (card in myhandlist) {
                        if (card.name == targetcard.name) {
                            myhandlist.remove(card)
                            break
                        }
                    }
                    gameactivity.runOnUiThread {
                        gameactivity.refreshmyhand(myhandlist)
                        gameactivity.updatemymagiczone(low_magicfield)
                    }
                }
                else{
                    toastmsg(gameactivity, "your magic/trap zone is full")
                }
            }
        }
        turnonotheractions()
    }

    fun seedetailcardview(card:Card){
        if(working){
            toastmsg(gameactivity, "working other job")
        }
        else{
            detailcardview.visibility=View.VISIBLE
            detailcardview.setImageResource(card.image)
        }
    }

    fun seeviewer(place: ViewerType, type: ViewPurpose){
        var list =ArrayList<Card>()
        when(place){
            ViewerType.MYGRAVE -> {
                list=low_grave
            }
            else -> {

            }
        }
        if(type==ViewPurpose.FOREFFECT){
            gameactivity.turnonviewer(list, place,type)
        }
        else{
            if(!working){
                gameactivity.turnonviewer(list, place,type)
            }
        }
    }

    fun vieweractionon(card:Card, place:ViewerType, type: ViewPurpose){
        gameactivity.showactionvalid(card, place, type)
    }

    fun shutdownviews(){
        if(!working){
            if(detailcardview.visibility == View.VISIBLE){
                detailcardview.visibility=View.GONE
            }
            else{
                handaction.visibility=View.GONE
                cardlistviewer.visibility=View.GONE
                fielduseview.visibility=View.GONE
            }
        }
        else{
            if(detailcardview.visibility == View.VISIBLE){
                detailcardview.visibility=View.GONE
                fielduseview.visibility=View.GONE
            }
        }
    }

    fun fielduseOn(view: ImageView){
        turnoffotheractions()
        if(view==lowmons1){
            if(low_monsfield[0] != null){
                gameactivity.cardonaction= low_monsfield[0]!!
                fieldaction.visibility=View.VISIBLE
                if(low_monsfield[0]?.fieldimg ==Zoneimg.ATTACK)
                    attackbtn.visibility=View.VISIBLE
                else
                    attackbtn.visibility=View.GONE
            }
        }
        else if(view == lowmons2){
            if(low_monsfield[1] != null){
                gameactivity.cardonaction= low_monsfield[1]!!
                fieldaction.visibility=View.VISIBLE
                if(low_monsfield[1]?.fieldimg ==Zoneimg.ATTACK)
                    attackbtn.visibility=View.VISIBLE
                else
                    attackbtn.visibility=View.GONE
            }
        }
        else if(view == lowmons3){
            if(low_monsfield[2] != null){
                gameactivity.cardonaction= low_monsfield[2]!!
                fieldaction.visibility=View.VISIBLE
                if(low_monsfield[2]?.fieldimg ==Zoneimg.ATTACK)
                    attackbtn.visibility=View.VISIBLE
                else
                    attackbtn.visibility=View.GONE
            }
        }
        else if(view == lowmagic1){
            if(low_magicfield[0] != null){
                gameactivity.cardonaction= low_magicfield[0]!!
                fieldaction.visibility=View.VISIBLE
                attackbtn.visibility=View.GONE
            }
        }
        else if(view == lowmagic2){
            if(low_magicfield[1] != null){
                gameactivity.cardonaction= low_magicfield[1]!!
                fieldaction.visibility=View.VISIBLE
                attackbtn.visibility=View.GONE
            }
        }
        else if(view == lowmagic3){
            if(low_magicfield[2] != null){
                gameactivity.cardonaction= low_magicfield[2]!!
                fieldaction.visibility=View.VISIBLE
                attackbtn.visibility=View.GONE
            }
        }
        else if(view == lowfieldzone){
            if(low_fieldmagic != null){
                gameactivity.cardonaction= low_fieldmagic!!
                fieldaction.visibility=View.VISIBLE
                attackbtn.visibility=View.GONE
            }
        }
        turnonotheractions()
    }

    fun changepos(card:Card){
        turnoffotheractions()
        when(card.posistion){
            CardPosition.FIELDZONE -> {
                val state = low_fieldmagic!!.fieldimg
                when(state){
                    Zoneimg.ATTACK -> {

                    }
                    Zoneimg.SET ->{
                        low_fieldmagic!!.fieldimg=Zoneimg.ATTACK
                    }
                    Zoneimg.DEFENSE -> {

                    }

                    else -> {}
                }
                gameactivity.updatemyfieldzone(low_fieldmagic!!)
            }
            CardPosition.MONSTERZONE1 -> {

            }
            CardPosition.MONSTERZONE2 -> {

            }
            CardPosition.MONSTERZONE3 -> {

            }
            CardPosition.MAGICZONE1 -> {

            }
            CardPosition.MAGICZONE2 ->{

            }
            CardPosition.MAGICZONE3 -> {

            }
            else -> {
                Log.e("error", "cannot change pos that is not on field")
            }
        }
        turnonotheractions()
    }

    fun attack(card:Card){

    }
}