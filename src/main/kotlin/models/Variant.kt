package ie.setu.models

/**
 * Represents a variant of a flower.
 *
 * @property variantId the unique identifier for the variant. Defaults to 0.
 * @property variantName the name of the variant.
 * @property expectedBLoomLife the expected blooming life of the variant in days.
 * @property colour the color of the variant.
 * @property isAvailable indicates whether the variant is currently available. Defaults to `false`.
 * @property price the price of the variant.
 * @constructor Creates a new `Variant` instance with the specified properties.
 */
data class Variant(
    var variantId: Int = 0,
    var variantName: String,
    var expectedBLoomLife: Int,
    var colour: String,
    var isAvailable: Boolean = false,
    var price: Double
)