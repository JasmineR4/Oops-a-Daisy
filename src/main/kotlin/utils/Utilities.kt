package ie.setu.utils

import ie.setu.models.Variant
import ie.setu.models.Flower


/**
 * Formats a list of flowers into a single string with each flower on a new line.
 *
 * @param flowersToFormat the list of Flower objects to format.
 * @return a formatted string where each flower in the list is separated by a newline.
 */
fun formatListString(flowersToFormat: List<Flower>): String =
    flowersToFormat
        .joinToString(separator = "\n") { flower -> "$flower" }
/**
 * Formats a set of variants into a single string with each variant on a new line,
 * indented for readability.
 *
 * @param itemsToFormat the set of Variant objects to format.
 * @return a formatted string where each variant in the set is indented and separated by a newline.
 */
fun formatSetString(itemsToFormat: Set<Variant>): String =
    itemsToFormat
        .joinToString(separator = "\n") { variant -> "\t$variant" }


