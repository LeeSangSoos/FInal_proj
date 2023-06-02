package edu.skku.cs.final_proj.gameobjects


import edu.skku.cs.final_proj.CardPosition
import edu.skku.cs.final_proj.CardType
import edu.skku.cs.final_proj.MagicElement
import edu.skku.cs.final_proj.MonElement
import edu.skku.cs.final_proj.MonType
import edu.skku.cs.final_proj.TrapElement
import edu.skku.cs.final_proj.Zoneimg

class Card (val type:CardType, val name:String, val image: Int, val effect:String, var posistion: CardPosition){
    var magicelement: MagicElement? = null
    var trapElement: TrapElement? = null
    var monsElement: MonElement? = null
    var monType: MonType? = null
    var level: Int? = null
    var attack: Int? = null
    var defense: Int? = null
    var haseffect: Boolean? = null
    var fieldimg: Zoneimg? =null

    constructor(type:CardType, name:String, image: Int, effect:String,
                element:MagicElement,fieldimg: Zoneimg= Zoneimg.ATTACK,
                posistion: CardPosition=CardPosition.DECK):this(type, name, image, effect, posistion){
        this.magicelement=element
        this.fieldimg=fieldimg
    }

    constructor(type:CardType, name:String, image: Int, effect:String,
                element:TrapElement,fieldimg: Zoneimg= Zoneimg.ATTACK,
                posistion: CardPosition=CardPosition.DECK):this(type, name, image, effect, posistion){
        this.trapElement=element
        this.fieldimg=fieldimg
    }

    constructor(type:CardType, name:String, image: Int, effect:String,
                element:MonElement, monstertype:MonType, level:Int,
                attack:Int, defense:Int, haseffect:Boolean=false,
                fieldimg: Zoneimg= Zoneimg.ATTACK,
                posistion: CardPosition=CardPosition.DECK):this(type, name, image, effect, posistion){
        this.monsElement = element
        this.monType = monstertype
        this.level = level
        this.attack = attack
        this.defense = defense
        this.haseffect = haseffect
        this.fieldimg=fieldimg
    }

}