package edu.skku.cs.final_proj

import edu.skku.cs.final_proj.gameobjects.ALegendaryOcean
import edu.skku.cs.final_proj.gameobjects.Card
import edu.skku.cs.final_proj.gameobjects.Fenrir
import edu.skku.cs.final_proj.gameobjects.GreatAngus
import edu.skku.cs.final_proj.gameobjects.Levia_Dragon_daedalus
import edu.skku.cs.final_proj.gameobjects.MotherGrizzly
import edu.skku.cs.final_proj.gameobjects.OceanDragonLord_Neo_Daedalus
import edu.skku.cs.final_proj.gameobjects.Salvage
import edu.skku.cs.final_proj.gameobjects.SeaSerpentWarriorOfDarkness
import edu.skku.cs.final_proj.gameobjects.SpaceMambo
import edu.skku.cs.final_proj.gameobjects.TorrentialTribute
import edu.skku.cs.final_proj.gameobjects._7ColoredFish
import java.io.File


object Constants {
    lateinit var deckpath: File
    val deckfilename = "deck_file.txt"
    lateinit var carddatapath:File
    val carddataname = "card_data_file.txt"

    val cardlist_update = arrayListOf<Card>(
        _7ColoredFish,
        GreatAngus,
        SeaSerpentWarriorOfDarkness,
        OceanDragonLord_Neo_Daedalus,
        SpaceMambo,
        MotherGrizzly,
        ALegendaryOcean,
        Levia_Dragon_daedalus,
        TorrentialTribute,
        Salvage,
        Fenrir
    )
}