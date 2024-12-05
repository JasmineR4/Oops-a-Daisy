package ie.setu.models

data class Variant(
    var variantId: Int = 0,
    var flowerId: Int,
    var variantName: String,
    var expectedLifespan: Int,
    var colour: String,
    var isAvailable: Boolean = true,
    var price: Double
)