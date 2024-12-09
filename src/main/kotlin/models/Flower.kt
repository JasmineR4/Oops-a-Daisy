package ie.setu.models

import ie.setu.listAllFlowers
import ie.setu.listFlowers
import ie.setu.utils.formatSetString
import ie.setu.models.Variant

data class Flower(
    var flowerId: Int = 0,
    var flowerName: String,
    var inSeason:  Boolean = false,
    var averageHeight: Double,
    var meaning: String,
    var variants : MutableSet<Variant> = mutableSetOf()) {

    private var lastVariantId = 0
    private fun getNextVariantId() = lastVariantId++

    fun addVariant(variant: Variant) : Boolean {
        variant.variantId = getNextVariantId()
        return variants.add(variant)
    }
    fun numberOfVariants() = variants.size
    fun findOne(id: Int): Variant?{
        return variants.find{ variant -> variant.variantId == id }
    }


fun delete(id: Int): Boolean {
    return variants.removeIf { variant -> variant.variantId == id}
}

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
    fun listVariants() =
        if (variants.isEmpty())  "\tNO VARIANTS ADDED"
        else  formatSetString(variants)

    override fun toString(): String {
        val inSeason = if (inSeason) "Yes" else "No"
        val variantsList = if (variants.isEmpty())
        {"No variants added" }
        else {
            variants.joinToString("\n") { variant ->
                """ 
                |   ID: ${variant.variantId}
                |   Name: ${variant.variantName},
                |    Expected Blooming Time(${variant.expectedBLoomLife} days),
                |    Colour (${variant.colour}),
                |    Available (${if (variant.isAvailable) "Yes" else "No"}),
                |    Price: €${variant.price}
                |    
            """.trimMargin()
            }
        }
        return """$flowerId: $flowerName, In season($inSeason), Average Height($averageHeight), meaning($meaning),
            | variants [$variantsList] """.trimMargin()
    }

    fun checkVariantAvailableStatus(): Boolean {
        if (variants.isNotEmpty()) {
            for (variant in variants) {
                if (!variant.isAvailable) {
                    return false
                }
            }
        }
        return true //a note with empty items can be archived, or all items are complete
    }
}