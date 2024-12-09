package ie.setu.utils

/**
 * Reads the next integer input from the user.
 *
 * @param prompt the message displayed to the user before input.
 * @return the integer value entered by the user.
 */
fun readNextInt(prompt: String?): Int {
    do {
        try {
            print(prompt)
            return readln().toInt()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a number please.")
        }
    } while (true)
}

/**
 * Reads the next line of input from the user.
 *
 * @param prompt the message displayed to the user before input.
 * @return the string value entered by the user.
 */
fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}

/**
 * Reads the next character input from the user.
 *
 * @param prompt the message displayed to the user before input.
 * @return the first character entered by the user.
 */
fun readNextChar(prompt: String?): Char {
    do {
        try {
            print(prompt)
            return readln().first()
        } catch (e: Exception) {
            System.err.println("\tEnter a character please.")
        }
    } while (true)
}