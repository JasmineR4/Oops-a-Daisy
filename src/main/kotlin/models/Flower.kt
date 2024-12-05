package ie.setu.models

data class Flower(
    var flowerId: Int = 0,
    var flowerName: String,
    var bloomingSeason: String,
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
    // functions to manage the variants set will go in here
}
