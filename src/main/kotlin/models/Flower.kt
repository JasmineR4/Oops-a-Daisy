package ie.setu.models

data class Flower(
    var flowerId: Int = 0,
    var flowerName: String,
    var bloomingSeason: String,
    var averageHeight: Double,
    var meaning: String
)