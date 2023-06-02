package edu.skku.cs.final_proj

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.final_proj.gameobjects.Card
import edu.skku.cs.final_proj.gameobjects.GreatAngus
import edu.skku.cs.final_proj.gameobjects._7ColoredFish
import edu.skku.cs.final_proj.gameobjects.backside
import kotlin.io.path.fileVisitor

class InGameActivity : AppCompatActivity() {
    lateinit var gamethread:GameThread
    var handlist = ArrayList<Card>()
    private var tophandlist = ArrayList<Card>()
    lateinit var decklist:ArrayList<Card>
    lateinit var cardonaction:Card
    lateinit var lowhand:RecyclerView
    lateinit var topmons1:ImageView
    lateinit var topmons2:ImageView
    lateinit var topmons3:ImageView
    lateinit var topmagic1:ImageView
    lateinit var topmagic2:ImageView
    lateinit var topmagic3:ImageView
    lateinit var lowmons1:ImageView
    lateinit var lowmons2:ImageView
    lateinit var lowmons3:ImageView
    lateinit var lowmagic1:ImageView
    lateinit var lowmagic2:ImageView
    lateinit var lowmagic3:ImageView
    lateinit var topfieldzone:ImageView
    lateinit var lowfieldzone:ImageView
    lateinit var cardlistviewer:RecyclerView
    lateinit var detailcardbtn:Button
    lateinit var detailcardbtn2:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_game)
        actionBar?.hide()
        // region findviews
        //settings
        val allfather = findViewById<ConstraintLayout>(R.id.allfather)
        val setting = findViewById<ImageButton>(R.id.setting)
        val settingscreen = findViewById<LinearLayout>(R.id.settingscreen)
        val surrender = findViewById<Button>(R.id.surrender)
        val returntogame = findViewById<Button>(R.id.returntogame)
        val turnbtn = findViewById<Button>(R.id.turnbtn)

        //field
        val topdeckbtn = findViewById<ImageView>(R.id.topdeck)
        val lowdeckbtn = findViewById<ImageView>(R.id.lowdeck)
        val topextrabtn = findViewById<ImageView>(R.id.topextradeck)
        val lowextrabtn = findViewById<ImageView>(R.id.lowextradeck)
        val topgravebtn = findViewById<ImageView>(R.id.topgrave)
        val lowgravebtn = findViewById<ImageView>(R.id.lowgrave)
        val topvoidbtn = findViewById<ImageView>(R.id.topvoid)
        val lowvoidbtn = findViewById<ImageView>(R.id.lowvoid)
         topmons1 = findViewById<ImageView>(R.id.topmon1)
         topmons2 = findViewById<ImageView>(R.id.topmon2)
         topmons3 = findViewById<ImageView>(R.id.topmon3)
         topmagic1 = findViewById<ImageView>(R.id.topmagic1)
         topmagic2 = findViewById<ImageView>(R.id.topmagic2)
         topmagic3 = findViewById<ImageView>(R.id.topmagic3)
         lowmons1 = findViewById<ImageView>(R.id.lowmon1)
         lowmons2 = findViewById<ImageView>(R.id.lowmon2)
         lowmons3 = findViewById<ImageView>(R.id.lowmon3)
         lowmagic1 = findViewById<ImageView>(R.id.lowmagic1)
         lowmagic2 = findViewById<ImageView>(R.id.lowmagic2)
         lowmagic3 = findViewById<ImageView>(R.id.lowmagic3)
         topfieldzone = findViewById<ImageView>(R.id.topfield)
         lowfieldzone = findViewById<ImageView>(R.id.lowfield)
        val topdecksize = findViewById<TextView>(R.id.topdecksize)
        val lowdecksize = findViewById<TextView>(R.id.lowdecksize)
        cardlistviewer = findViewById(R.id.cardlistviewer)
        val detailcardview = findViewById<ImageView>(R.id.detailcard)
        val fielduselayout = findViewById<LinearLayout>(R.id.fielduse)

        //hand & hand action
        val tophand = findViewById<RecyclerView>(R.id.tophand)
        lowhand = findViewById(R.id.lowhand)
        val handaction = findViewById<LinearLayout>(R.id.handaction)
        val normalsummon = findViewById<Button>(R.id.normalsummon)
        val specialsummon = findViewById<Button>(R.id.specialsummon)
        val set = findViewById<Button>(R.id.set)
        val effect = findViewById<Button>(R.id.effect)
        detailcardbtn = findViewById(R.id.seedetail)
        detailcardbtn2= findViewById(R.id.seedetail2)
        val attackbtn = findViewById<Button>(R.id.attackbtn)
        val changeposbtn=findViewById<Button>(R.id.changeposbtn)
        val effectonfieldbtn=findViewById<Button>(R.id.effectonfieldbtn)
        //endregion findviews

        //region field basic settings
        //region visivility
        settingscreen.visibility=View.GONE
        handaction.visibility=View.GONE
        cardlistviewer.visibility=View.GONE
        detailcardview.visibility=View.GONE
        fielduselayout.visibility=View.GONE
        var settingison = false
        topgravebtn.setImageDrawable(null)
        lowgravebtn.setImageDrawable(null)
        topmons1.setImageDrawable(null)
        topmons2.setImageDrawable(null)
        topmons3.setImageDrawable(null)
        topmagic1.setImageDrawable(null)
        topmagic2.setImageDrawable(null)
        topmagic3.setImageDrawable(null)
        lowmons1.setImageDrawable(null)
        lowmons2.setImageDrawable(null)
        lowmons3.setImageDrawable(null)
        lowmagic1.setImageDrawable(null)
        lowmagic2.setImageDrawable(null)
        lowmagic3.setImageDrawable(null)
        topfieldzone.setImageDrawable(null)
        lowfieldzone.setImageDrawable(null)
        //endregion visivility
        turnbtn.setOnClickListener {
            gamethread.changepage()
        }
        allfather.setOnClickListener{
            gamethread.shutdownviews()
        }
        normalsummon.setOnClickListener{
            gamethread.normalsummon(cardonaction)
        }
        set.setOnClickListener{
            gamethread.set(cardonaction)
        }
        effect.setOnClickListener{
            //gamethread.effect(cardonaction)
        }
        lowgravebtn.setOnClickListener{
            gamethread.seeviewer(ViewerType.MYGRAVE, ViewPurpose.JUSTVIEW)
        }
        topgravebtn.setOnClickListener{

        }
        lowvoidbtn.setOnClickListener{

        }
        topvoidbtn.setOnClickListener{

        }
        lowextrabtn.setOnClickListener{

        }
        topextrabtn.setOnClickListener{

        }
        detailcardbtn.setOnClickListener{
            gamethread.seedetailcardview(cardonaction)
        }
        detailcardbtn2.setOnClickListener{
            gamethread.seedetailcardview(cardonaction)
        }
        lowmons1.setOnClickListener{
            gamethread.fielduseOn(lowmons1)
        }
        lowmons2.setOnClickListener{
            gamethread.fielduseOn(lowmons2)
        }
        lowmons3.setOnClickListener{
            gamethread.fielduseOn(lowmons3)
        }
        lowmagic1.setOnClickListener{
            gamethread.fielduseOn(lowmagic1)
        }
        lowmagic2.setOnClickListener{
            gamethread.fielduseOn(lowmagic2)
        }
        lowmagic3.setOnClickListener{
            gamethread.fielduseOn(lowmagic3)
        }
        lowfieldzone.setOnClickListener{
            gamethread.fielduseOn(lowfieldzone)
        }
        handaction.setOnClickListener{
            detailcardview.visibility=View.GONE
        }
        attackbtn.setOnClickListener{
            //attack
        }
        changeposbtn.setOnClickListener {
            gamethread.changepos(cardonaction)
        }
        effectonfieldbtn.setOnClickListener {

        }
        //endregion field basic settings

        //region setting btn
        setting.setOnClickListener{
            if(settingison == false){
                settingscreen.visibility=View.VISIBLE
                settingison = true
            }
        }
        surrender.setOnClickListener{
            if(settingison == true){
                this.finish()
            }
        }
        returntogame.setOnClickListener{
            if(settingison == true){
                settingscreen.visibility=View.GONE
                settingison = false
            }
        }
        //endregion setting btn

        //region game init setting
        decklist=readfromfile(edu.skku.cs.final_proj.Constants.deckfilename)
        decklist.shuffle()
        for(i in 0..3){
            tophandlist.add(backside)
        }
        tophand.adapter = HandAdapter(this, tophandlist)
        for(i in 0..3) {
            decklist[i].posistion=CardPosition.HAND
            handlist.add(decklist[i])
            decklist.removeAt(i)
        }
        lowhand.adapter = HandAdapter(this, handlist)
        val sizestring="deck:"+decklist.size
        lowdecksize.setText(sizestring)
        //endregion game init setting

        gamethread = GameThread(this)
        gamethread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        gamethread.end()
        gamethread.join()
    }

    public fun showhandaction(card: Card){
        cardonaction = card
        val handaction = findViewById<LinearLayout>(R.id.handaction)
        val normalsummon = findViewById<Button>(R.id.normalsummon)
        val specialsummon = findViewById<Button>(R.id.specialsummon)
        val set = findViewById<Button>(R.id.set)
        val effect = findViewById<Button>(R.id.effect)
        handaction.visibility=View.VISIBLE
        detailcardbtn.visibility = View.VISIBLE
        when(card.type){
            CardType.MONSTER ->{
                if(card.name == "OceanDragonLord_Neo_Daedalus" ||
                    card.name == "Fenrir"){
                    normalsummon.visibility=View.GONE
                    set.visibility=View.GONE
                    specialsummon.visibility=View.VISIBLE
                }
                else{
                    normalsummon.visibility=View.VISIBLE
                    set.visibility=View.VISIBLE
                    specialsummon.visibility=View.GONE
                }
                if(card.haseffect == true)
                    effect.visibility=View.VISIBLE
                else
                    effect.visibility=View.GONE
            }
            CardType.MAGIC -> {
                normalsummon.visibility=View.GONE
                specialsummon.visibility=View.GONE
                set.visibility=View.VISIBLE
                effect.visibility=View.VISIBLE
            }
            CardType.TRAP -> {
                normalsummon.visibility=View.GONE
                specialsummon.visibility=View.GONE
                set.visibility=View.VISIBLE
                effect.visibility=View.GONE
            }
        }
    }

    public fun refreshmyhand(list:ArrayList<Card>){
        lowhand.adapter = HandAdapter(this, list)
    }

    public fun updatemymonsterzone(array:Array<Card?>){
        if(array[0]!=null)
            updatefieldimg(lowmons1,array[0]!!)
        if(array[1]!=null)
            updatefieldimg(lowmons2,array[1]!!)
        if(array[2]!=null)
            updatefieldimg(lowmons3,array[2]!!)
    }

    public fun updatemymagiczone(array:Array<Card?>){
        if(array[0]!=null)
            updatefieldimg(lowmagic1,array[0]!!)
        if(array[1]!=null)
            updatefieldimg(lowmagic2,array[1]!!)
        if(array[2]!=null)
            updatefieldimg(lowmagic3,array[2]!!)
    }

    public fun updateenemymonsterzone(array:Array<Card?>){
        if(array[0]!=null)
            updatefieldimg(topmons1,array[0]!!)
        if(array[1]!=null)
            updatefieldimg(topmons2,array[1]!!)
        if(array[2]!=null)
            updatefieldimg(topmons3,array[2]!!)
    }

    public fun updateenemymagiczone(array:Array<Card?>){
        if(array[0]!=null)
            updatefieldimg(topmagic1,array[0]!!)
        if(array[1]!=null)
            updatefieldimg(topmagic2,array[1]!!)
        if(array[2]!=null)
            updatefieldimg(topmagic3,array[2]!!)
    }

    public fun updateenemyfieldzone(card:Card){
        updatefieldimg(topfieldzone,card)
    }

    public fun updatemyfieldzone(card:Card){
        updatefieldimg(lowfieldzone,card)
    }

    fun updatefieldimg(view: ImageView, card:Card){
        if(card.fieldimg == Zoneimg.SET){
            view.setImageResource(R.drawable.backside)
            if(card.type == CardType.MONSTER)
                view.rotation=90f
            else
                view.rotation=0f
        }
        else if(card.fieldimg == Zoneimg.ATTACK){
            view.setImageResource(card.image)
            view.rotation=0f
        }
        else{
            view.setImageResource(card.image)
            view.rotation=90f
        }
    }

    fun turnonviewer(list:ArrayList<Card>, place:ViewerType, type:ViewPurpose){
        cardlistviewer.visibility = View.VISIBLE
        cardlistviewer.adapter = ListAdapter(this, list, place, type)
    }

    public fun showactionvalid(card: Card, place:ViewerType, type: ViewPurpose){
        cardonaction = card
        val handaction = findViewById<LinearLayout>(R.id.handaction)
        val normalsummon = findViewById<Button>(R.id.normalsummon)
        val specialsummon = findViewById<Button>(R.id.specialsummon)
        val set = findViewById<Button>(R.id.set)
        val effect = findViewById<Button>(R.id.effect)
        handaction.visibility=View.VISIBLE
        detailcardbtn.visibility = View.VISIBLE
        normalsummon.visibility=View.GONE
        specialsummon.visibility=View.GONE
        set.visibility=View.GONE
        effect.visibility=View.GONE
    }
}