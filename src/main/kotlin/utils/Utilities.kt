package ie.setu.utils

import ie.setu.models.Variant
import ie.setu.models.Flower


fun formatListString(flowersToFormat: List<Flower>): String =
    flowersToFormat
        .joinToString(separator = "\n") { flower -> "$flower" }

fun formatSetString(itemsToFormat: Set<Variant>): String =
    itemsToFormat
        .joinToString(separator = "\n") { variant -> "\t$variant" }


