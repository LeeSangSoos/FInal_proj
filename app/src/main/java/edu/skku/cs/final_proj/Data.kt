package edu.skku.cs.final_proj

import java.util.ArrayList

enum class CardType{
    MONSTER, MAGIC, TRAP
}

enum class MonElement{
    DARK, LIGHT, EARTH, WATER, FIRE, WIND, DIVINE
}

enum class MagicElement{
    NORMAL, EQUIPMENT, FIELD, FAST, RITUAL, CONTINUOUS
}

enum class TrapElement{
    NORMAL, CONTINUOUS, COUNTER
}

enum class MonType{
    AQUA, BEAST, BEAST_WARRIOR, CYBERSE, DINOSAUR, DRAGON, FAIRY, FISH, INSECT,
    DIVINE_BEAST, MACHINE, PLANT, PSYCHIC, PYRO, REPTILE, ROCK, SEA_SERPENT,
    SPELLCASTER, THUNDER, WARRIOR, WINGED_BEAST, WYRM, ZOMBIE
}

data class CardData(var data: ArrayList<DataCard>){
    data class DataCard(var card_images: ArrayList<DataImg>){
        data class DataImg(var image_url: String ?= null)
    }
}

enum class Turn{
    DRAW, STANDBY, MAIN, BATTLE, END
}

enum class Zoneimg{
    ATTACK, SET, DEFENSE
}

enum class ViewerType{
    MYGRAVE, MYVOID, MYEXTRA, ENEMYGRAVE, ENEMYVOID, ENEMYEXTRA,
    MYDECK, ENEMYDECK, MYHAND, ENEMYHAND
}

enum class ViewPurpose{
    JUSTVIEW, FOREFFECT
}

enum class CardPosition{
    FIELDZONE, MONSTERZONE1, MONSTERZONE2, MONSTERZONE3, MAGICZONE1, MAGICZONE2, MAGICZONE3,
    GRAVE, VOID, DECK, HAND
}