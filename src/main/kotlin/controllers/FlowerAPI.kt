package controllers

import ie.setu.models.Flower
import ie.setu.models.Variant
import ie.setu.utils.formatListString
import java.util.ArrayList

class FlowerAPI() {

    private var flowers = ArrayList<Flower>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR FLOWER ArrayList
    // ----------------------------------------------
    fun add(flower: Flower): Boolean {
        flower.flowerId = getId()
        return flowers.add(flower)
    }

    fun delete(id: Int) = flowers.removeIf { flower -> flower.flowerId == id }

    fun update(id: Int, flower: Flower?): Boolean {
        // find the flower object by the index number
        val foundFlower = findFlower(id)

        // if the flower exists, use the flower details passed as parameters to update the found flower in the ArrayList.
        if ((foundFlower != null) && (flower != null)) {
            foundFlower.flowerName = flower.flowerName
            foundFlower.inSeason = flower.inSeason
            foundFlower.averageHeight = flower.averageHeight
            foundFlower.meaning = flower.meaning
            return true
        }

        // if the flower was not found, return false, indicating that the update was not successful
        return false
    }

   /* fun archiveFlower(id: Int): Boolean {
        val foundFlower = findFlower(id)
        if (( foundFlower != null) && (!foundFlower.isFlowerArchived)
        //  && ( foundFlower.checkFlowerCompletionStatus())
        ){
            foundFlower.isFlowerArchived = true
            return true
        }
        return false
    }
*/
    // ----------------------------------------------
    //  LISTING METHODS FOR FLOWER ArrayList
    // ----------------------------------------------
    fun listAllFlowers() =
        if (flowers.isEmpty()) "No flowers stored"
        else formatListString(flowers)

    /*fun listActiveFlowers() =
        if (numberOfActiveFlowers() == 0) "No active flowers stored"
        else formatListString(flowers.filter { flower -> !flower.isFlowerArchived })
*/
    fun listBloomFlowers() =
        if (numberOfBloomFlowers() == 0) "No currently blooming flowers stored"
        else formatListString(flowers.filter { flower -> flower.inSeason })

    // ----------------------------------------------
    //  COUNTING METHODS FOR FLOWER ArrayList
    // ----------------------------------------------
    fun numberOfFlowers() = flowers.size

    fun numberOfBloomFlowers(): Int = flowers.count { flower: Flower -> flower.inSeason }

   // fun numberOfActiveFlowers(): Int = flowers.count { flower: Flower -> !flower.meaning }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findFlower(flowerId : Int) =  flowers.find{ flower -> flower.flowerId == flowerId }

    fun searchFlowersByName(searchString: String) =
        formatListString(flowers.filter { flower -> flower.flowerName.contains(searchString, ignoreCase = true) })

}