package ie.setu.models

data class Variant(
    var variantId: Int = 0,
    var variantName: String,
    var expectedBLoomLife: Int,
    var colour: String,
    var isAvailable: Boolean = false,
    var price: Double
)