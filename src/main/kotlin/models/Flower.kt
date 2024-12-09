package ie.setu.models

import ie.setu.utils.formatSetString

/**
 * Represents a flower.
 *
 * @property flowerId the unique identifier for the flower. Defaults to 0.
 * @property flowerName the name of the flower.
 * @property inSeason indicates whether the flower is currently in season. Defaults to `false`.
 * @property averageHeight the average height of the flower in meters.
 * @property meaning the symbolic meaning of the flower.
 * @property variants a mutable set of `Variant` objects associated with the flower. Defaults to an empty set.
 * @constructor Creates a new `Flower` instance with the specified properties.
 */
data class Flower(
    var flowerId: Int = 0,
    var flowerName: String,
    var inSeason:  Boolean = false,
    var averageHeight: Double,
    var meaning: String,
    var variants : MutableSet<Variant> = mutableSetOf()) {

    /** The last assigned variant ID, used for generating new variant IDs. */
    private var lastVariantId = 0
    /**
     * Generates the next unique variant ID.
     * @return the next unique variant ID.
     */
    private fun getNextVariantId() = lastVariantId++

    /**
     * Adds a new `Variant` to the `variants` set.
     *
     * @param variant the `Variant` to be added.
     * @return `true` if the variant was successfully added, `false` otherwise.
     */
    fun addVariant(variant: Variant) : Boolean {
        variant.variantId = getNextVariantId()
        return variants.add(variant)
    }

    /**
     * Gives the number of variants associated with the flower.
     *
     * @return the size of the `variants` set.
     */
    fun numberOfVariants() = variants.size

    /**
     * Finds a `Variant` by its ID.
     *
     * @param id the ID of the variant to find.
     * @return the `Variant` with the specified ID, or `null` if not found.
     */
    fun findOne(id: Int): Variant?{
        return variants.find{ variant -> variant.variantId == id }
    }

    /**
     * Deletes a `Variant` by its ID.
     *
     * @param id the ID of the variant to be deleted.
     * @return `true` if the variant was found and removed, `false` otherwise.
     */
fun delete(id: Int): Boolean {
    return variants.removeIf { variant -> variant.variantId == id}
}

    /**
     * Updates the details of a `Variant` identified by its ID.
     *
     * @param id the ID of the variant to be updated.
     * @param newVariant the `Variant` object containing the new details.
     * @return `true` if the update was successful, `false` if the variant was not found.
     */
fun update(id: Int, newVariant : Variant): Boolean {
    val foundVariant = findOne(id)

    //if the object exists, use the details passed in the newVariant parameter to
    //update the found object in the Set
    if (foundVariant != null){
        foundVariant.variantName = newVariant.variantName
        foundVariant.expectedBLoomLife = newVariant.expectedBLoomLife
        foundVariant.colour = newVariant.colour
        foundVariant.isAvailable = newVariant.isAvailable
        foundVariant.price = newVariant.price
        return true
    }

    //if the object was not found, return false, indicating that the update was not successful
    return false
}

    /**
     * Lists all the variants associated with the flower.
     *
     * @return a formatted string representing the variants, or "NO VARIANTS ADDED" if none exist.
     */
    fun listVariants() =
        if (variants.isEmpty())  "\tNO VARIANTS ADDED"
        else  formatSetString(variants)

    /**
     * Returns a string representation of the `Flower` and its variants.
     *
     * @return a formatted string containing the details of the flower and its variants.
     */
    override fun toString(): String {
        val inSeason = if (inSeason) "Yes" else "No"
        val variantsList = if (variants.isEmpty())
        {"No variants added" }
        else {
            variants.joinToString("\n") { variant ->"""
         ═════════════════════════
                  🪻🪻🪻
         ID: ${variant.variantId}
         Name: ${variant.variantName},
         Expected Blooming Time (${variant.expectedBLoomLife} days),
         Colour (${variant.colour},
         Available (${if (variant.isAvailable) "Yes" else "No"}),
         Price (€${variant.price})
                  🪻🪻🪻
         ═════════════════════════
                    """.trimMargin()
            }
        }
        return """
════════════════════════════════════════════════════════
                         🌷🌷🌷
    ${flowerId}: $flowerName
         In season($inSeason),
         Average Height($averageHeight),
         meaning($meaning),
         variants: 
$variantsList
                        🌷🌷🌷
════════════════════════════════════════════════════════""".trimMargin()
    }

}