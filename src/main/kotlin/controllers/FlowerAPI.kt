package controllers

import ie.setu.models.Flower
import ie.setu.utils.formatListString
import java.util.ArrayList

/**
 * Manages operations related to flowers.
 *
 * This class provides CRUD (Create, Read, Update, Delete) operations,
 * as well as methods for listing, searching, and counting flowers and their variants.
 */
class FlowerAPI {

    // List of all stored flowers.
    private var flowers = ArrayList<Flower>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------

    // The last assigned flower ID, used for generating new IDs.
    private var lastId = 0
    /**
     * Generates the next unique ID for a flower.
     *
     * @return the next unique ID.
     */
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR FLOWER ArrayList
    // ----------------------------------------------

    /**
     * Adds a new flower to the list.
     *
     * @param flower the `Flower` object to be added.
     * @return `true` if the flower was successfully added, `false` otherwise.
     */
    fun add(flower: Flower): Boolean {
        flower.flowerId = getId()
        return flowers.add(flower)
    }
    /**
     * Deletes a flower by its ID.
     *
     * @param id the ID of the flower to be deleted.
     * @return `true` if the flower was removed, `false` otherwise.
     */
    fun delete(id: Int) = flowers.removeIf { flower -> flower.flowerId == id }

    /**
     * Updates an existing flower's details based on the provided flower object.
     *
     * @param id the ID of the flower to update.
     * @param flower the `Flower` object containing updated details.
     * @return `true` if the update was successful, `false` if the flower was not found or the input was invalid.
     */
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

    // ----------------------------------------------
    //  LISTING METHODS FOR FLOWER ArrayList
    // ----------------------------------------------

    /**
     * Lists all flowers currently stored in the list.
     *
     * @return a formatted string representation of all flowers, or a message indicating no flowers are stored.
     */
    fun listAllFlowers() =
        if (flowers.isEmpty()) """
════════════════════════════════════════════════════════
                 🌻No flowers stored🌻
════════════════════════════════════════════════════════
            """.trimMargin(">")
        else formatListString(flowers)

    /**
     * Lists all flowers that are currently in season.
     *
     * @return a formatted string representation of in-season flowers, or a message indicating no flowers are blooming.
     */
    fun listBloomFlowers() =
        if (numberOfBloomFlowers() == 0) """
════════════════════════════════════════════════════════
        No currently blooming flowers stored
════════════════════════════════════════════════════════
        """.trimMargin()
        else formatListString(flowers.filter { flower -> flower.inSeason })

    // ----------------------------------------------
    //  COUNTING METHODS FOR FLOWER ArrayList
    // ----------------------------------------------

    /**
     * Returns the total number of flowers in the list.
     *
     * @return the count of flowers.
     */
    fun numberOfFlowers() = flowers.size

    /**
     * Returns the number of flowers that are currently in season.
     *
     * @return the count of in-season flowers.
     */
    fun numberOfBloomFlowers(): Int = flowers.count { flower: Flower -> flower.inSeason }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    /**
     * Finds a flower by its ID.
     *
     * @param flowerId the ID of the flower to find.
     * @return the `Flower` object with the specified ID, or `null` if not found.
     */
    fun findFlower(flowerId: Int) = flowers.find { flower -> flower.flowerId == flowerId }

    /**
     * Searches for flowers based on a name string.
     *
     * @param searchString the name string to search for.
     * @return a formatted string of flowers that match the search criteria.
     */
    fun searchFlowersByName(searchString: String) =
        formatListString(flowers.filter { flower -> flower.flowerName.contains(searchString, ignoreCase = true) })

    /**
     * Searches for variants across all flowers based on a name string.
     *
     * @param searchString the name string to search for in the variant names.
     * @return a formatted string of flowers and their variants that match the search criteria, or a message if no variants are found.
     */
    fun searchVariantsByName(searchString: String): String {
        return if (numberOfFlowers() == 0) { """
════════════════════════════════════════════════════════
                🌻No flowers stored🌻
════════════════════════════════════════════════════════
            """.trimMargin()
        } else {
            var listOfFlowers = ""
            for (flower in flowers) {
                for (variant in flower.variants) {
                    if (variant.variantName.contains(searchString, ignoreCase = true)) {
                        listOfFlowers += """
════════════════════════════════════════════════════════
                         🌷🌷🌷
    ${flower.flowerId}: ${flower.flowerName}
         ID: ${variant.variantId}
         Name: ${variant.variantName},
         Expected Blooming Time (${variant.expectedBLoomLife} days),
         Colour (${variant.colour},
         Available (${if (variant.isAvailable) "Yes" else "No"}),
         Price (€${variant.price})
                        🌷🌷🌷
════════════════════════════════════════════════════════
                    """.trimMargin()
                    }
                }
            }
            if (listOfFlowers == "") "No variants found for: $searchString"
            else listOfFlowers
        }
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR ITEMS
    // ----------------------------------------------

    /**
     * Lists all available variants across all flowers.
     *
     * @return a formatted string of available variants, or a message indicating no variants are available.
     */
    fun listAvailableVariants(): String =
        if (numberOfFlowers() == 0) "No Flowers stored"
        else {
            var listOfAvailableVariants = ""
            for (flower in flowers) {
                for (variant in flower.variants) {
                    if (variant.isAvailable) {
                        listOfAvailableVariants += """
════════════════════════════════════════════════════════
                        🪻🪻🪻
    ${flower.flowerId}: ${flower.flowerName}
         ID: ${variant.variantId}
         Name: ${variant.variantName},
         Expected Blooming Time (${variant.expectedBLoomLife} days),
         Colour (${variant.colour},
         Available (${if (variant.isAvailable) "Yes" else "No"}),
         Price (€${variant.price})
                        🪻🪻🪻
════════════════════════════════════════════════════════
                    """.trimMargin()
                    }
                }
            }
            listOfAvailableVariants
        }

    // ----------------------------------------------
    //  COUNTING METHODS FOR ITEMS
    // ----------------------------------------------

    /**
     * Returns the total number of available variants across all flowers.
     *
     * @return the count of available variants.
     */
    fun numberOfAvailableVariants(): Int {
        var numberOfAvailableVariants = 0
        for (flower in flowers) {
            for (variant in flower.variants) {
                if (variant.isAvailable) {
                    numberOfAvailableVariants++
                }
            }
        }
        return numberOfAvailableVariants
    }
}