package edu.skku.cs.final_proj.gameobjects

import edu.skku.cs.final_proj.CardType
import edu.skku.cs.final_proj.MagicElement
import edu.skku.cs.final_proj.MonElement
import edu.skku.cs.final_proj.MonType
import edu.skku.cs.final_proj.R
import edu.skku.cs.final_proj.TrapElement

val backside = Card(CardType.MONSTER, "backside",
    R.drawable.backside,
    "none",
    MonElement.WATER, MonType.FISH, 0, 0, 0)

val nocard = Card(CardType.MONSTER, "nocard",
    R.drawable.backside,
    "none",
    MonElement.WATER, MonType.FISH, 0, 0, 0)

val _7ColoredFish = Card(CardType.MONSTER, "7 Colored Fish",
    R.drawable._7coloredfish,
    "none",
    MonElement.WATER, MonType.FISH, 4, 1800, 800)

val GreatAngus = Card(CardType.MONSTER, "Great Angus",
    R.drawable.greatangus,
    "none",
    MonElement.FIRE, MonType.BEAST,4, 1800, 600)

val ALegendaryOcean = Card(CardType.MAGIC, "A Legendary Ocean",
    R.drawable.alegendaryocean,
    "(This card is always treated as \"Umi\")" +
            "All WATER monsters on the field gain 200 ATK/DEF. Reduce the Level of all WATER monsters in both players hands and on the field by 1",
    MagicElement.FIELD)

val SeaSerpentWarriorOfDarkness = Card(CardType.MONSTER, "Sea Serpent Warrior of Darkness",
    R.drawable.seaserpentwarriorofdarkness,
    "none",
    MonElement.WATER, MonType.SEA_SERPENT,4, 1800, 1500)

val OceanDragonLord_Neo_Daedalus=Card(CardType.MONSTER, "Ocean Dragon Lord-Neo-Daedalus",
    R.drawable.oceandragonlordneodaedalus,
    "This card cannot be Normal Summoned or Set. This card cannot be Special Summoned except by Tributing 1 "
    +"\"Levia-Dragon-Daedalus\". You can send \"Umi\" you control to the Graveyard to send all"+
    "cards in both players hands and on the field to the Graveyard except this card.",
    MonElement.WATER, MonType.SEA_SERPENT,8, 2900, 1600 ,true)

val SpaceMambo=Card(CardType.MONSTER, "Space Mambo",
    R.drawable.spacemambo,
    "none",
    MonElement.WATER, MonType.FISH,4, 1700, 1000)

val MotherGrizzly=Card(CardType.MONSTER, "Mother Grizzly",
    R.drawable.mothergrizzily,
    "When this card is destroyed by battle and sent to the Graveyard: You can Special Summon 1 WATER"
    +"monster with 1500 or less ATK from your Deck in face-up Attack Position.",
    MonElement.WATER, MonType.BEAST_WARRIOR,4, 1400, 1000, true)

val Levia_Dragon_daedalus = Card(CardType.MONSTER, "Levia-Dragon-Daedalus",
    R.drawable.leviadragondaedalus,
    "You can send 1 face-up \"Umi\" you control to the GY: destroy all other cards on the field.",
    MonElement.WATER, MonType.SEA_SERPENT,7, 2600, 1500, true)

val TorrentialTribute = Card(CardType.TRAP, "Torrential Tribute",
    R.drawable.torrentialtribute,
    "When a monster(s) is Summoned: Destroy all monsters on the field.",
    TrapElement.NORMAL)

val Salvage = Card(CardType.MAGIC, "Salvage",
    R.drawable.salvage,
    "Target 2 WATER monsters with 1500 or less ATK in your GY: add those targets to your hand.",
    MagicElement.NORMAL)

val Fenrir=Card(CardType.MONSTER, "Fenrir",
    R.drawable.fenrir,
    "This card cannot be Normal Summoned or Set. This card can only be Special Summoned by"+
    "removing from play 2 WATER monsters in your Graveyard. When this card destroys an opponent's monster"+
    "as a result of battle: your opponent skips their next Draw Phase.",
    MonElement.WATER, MonType.BEAST,4, 1400, 1200, true)