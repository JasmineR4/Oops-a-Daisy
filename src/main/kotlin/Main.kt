package ie.setu

import controllers.FlowerAPI
import ie.setu.models.Flower
import ie.setu.models.Variant
import ie.setu.utils.readNextInt
import ie.setu.utils.readNextLine
import ie.setu.utils.readNextChar
import kotlin.system.exitProcess

private val flowerAPI = FlowerAPI()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addFlower()
            2 -> listFlowers()
            3 -> updateFlower()
            4 -> deleteFlower()
            5 -> bloomFlower()
            6 -> addVariantToFlower()
            7 -> updateVariantContentsInFlower()
            8 -> deleteAVariant()
            9 -> markVariantStatus()
            10 -> searchFlowers()
            15 -> searchVariants()
            16 -> listAvailableVariants()
            0 -> exitApp()
            else -> println("""
════════════════════════════════════════════════════════
       🌼Invalid menu option entered: $option🌼
════════════════════════════════════════════════════════
""".trimMargin(">"))
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """
════════════════════════════════════════════════════════
      🌼🌸🌷🌺  FLOWER TRACKER APP  🌺🌷🌸🌼
════════════════════════════════════════════════════════
                    FLOWER MENU:
1)      Add a flower
2)      List flowers
3)      Update a flower
4)      Delete a flower
5)      Change flowers blooming status
════════════════════════════════════════════════════════
                   VARIANT MENU:
6)      Add variant to a flower
7)      Update variant contents on a flower
8)      Delete variant from a flower
9)      Change variant availability status
════════════════════════════════════════════════════════
            💐 REPORT MENU FOR FLOWERS:
10)     Search for all flowers (by flower name)
11)     ...
12)     ...
13)     ...
14)     ...
════════════════════════════════════════════════════════
            🌿 REPORT MENU FOR VARIANTS:
15)     Search for all variants (by variant name)
16)     List all available Variants in stock
17)     ...
18      ...
════════════════════════════════════════════════════════
0)      Exit
════════════════════════════════════════════════════════
> ==>>""".trimMargin(">")
)

//------------------------------------
//🌸THE FLOWER MENU🌸
//------------------------------------
fun addFlower() {
    val flowerName = readNextLine("What is the name of the flower? : ")
    val inSeason = readNextLine("The Flower is in season (true or false) : ").toBoolean()
    val averageHeight = readNextLine("What is the height of the flower? (in meters) : ").toDoubleOrNull() ?: 0.0
    val meaning = readNextLine("What is the symbolic meaning of the flower? : ")
    val isAdded = flowerAPI.add(Flower(flowerName = flowerName,
        inSeason = inSeason,
        averageHeight = averageHeight,
        meaning = meaning))

    if (isAdded) {
        println("""
════════════════════════════════════════════════════════
                 💐Add Successful💐
════════════════════════════════════════════════════════
""".trimMargin(">"))
    } else {
        println("""
════════════════════════════════════════════════════════
                 💐Add UnSuccessful💐
════════════════════════════════════════════════════════
""".trimMargin(">"))
    }
}

fun listFlowers() {
    if (flowerAPI.numberOfFlowers() > 0) {

        val option = readNextInt(
            """
════════════════════════════════════════════════════════
1)      💐View all flowers 
2)      🌼View Currently blooming flowers     
════════════════════════════════════════════════════════
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllFlowers()
            2 -> listBloomFlowers()
            else -> println("""
════════════════════════════════════════════════════════
         🌼Invalid option entered: $option🌼
════════════════════════════════════════════════════════
""".trimMargin(">"))
        }
    } else {
        println("""
════════════════════════════════════════════════════════
         🌺Option Invalid - No flowers stored🌺
════════════════════════════════════════════════════════
""".trimMargin(">"))
    }
}

fun listAllFlowers() = println(flowerAPI.listAllFlowers())

fun listBloomFlowers() =println(flowerAPI.listBloomFlowers())


fun updateFlower() {
    listFlowers()
    if (flowerAPI.numberOfFlowers() > 0) {
        // only ask the user to choose the flower if flowers exist
        val id = readNextInt("Enter the id of the flower to update: ")
        if (flowerAPI.findFlower(id) != null) {
            val flowerName = readNextLine("What is the name of the flower? : ")
            val inSeason = readNextLine("The Flower is in season (true or false) : ").toBoolean()
            val averageHeight = readNextLine("What is the height of the flower? (in meters) : ").toDoubleOrNull() ?: 0.0
            val meaning = readNextLine("What is the symbolic meaning of the flower? : ")

            // pass the index of the flower and the new flower details to FlowerAPI for updating and check for success.
            if (flowerAPI.update(id, Flower(0,
                    flowerName,
                    inSeason,
                    averageHeight,
                    meaning))){
                println("""
════════════════════════════════════════════════════════
                🌺Update Successful🌺
════════════════════════════════════════════════════════
""".trimMargin(">"))
            } else {
                println("""
════════════════════════════════════════════════════════
                🌺Update UnSuccessful🌺
════════════════════════════════════════════════════════
""".trimMargin(">"))
            }
        } else {
            println("""
════════════════════════════════════════════════════════
     🌺There are no flowers for this ID number🌺
════════════════════════════════════════════════════════
""".trimMargin(">"))
        }
    }
}

fun deleteFlower() {
    listFlowers()
    if (flowerAPI.numberOfFlowers() > 0) {
        // only ask the user to choose the flower to delete if flowers exist
        val id = readNextInt("Enter the id of the flower to delete: ")
        // pass the index of the flower to FlowerAPI for deleting and check for success.
        val flowerToDelete = flowerAPI.delete(id)
        if (flowerToDelete) {
            println("""
════════════════════════════════════════════════════════
                🏵️Delete Successful🏵️
════════════════════════════════════════════════════════
""".trimMargin(">"))
        } else {
            println("""
════════════════════════════════════════════════════════
               🏵️Delete UnSuccessful🏵️  
════════════════════════════════════════════════════════
""".trimMargin(">"))
        }
    }
}
fun bloomFlower() {
    val flower: Flower? = askUserToChooseFlower()
    if (flower != null) {

        var changeStatus = 'X'
        if (flower.inSeason) {
            changeStatus = readNextChar("The flower is currently in season... do you want to mark it as out of season? (Y for yes): ")
            if ((changeStatus == 'Y') ||  (changeStatus == 'y')){
                flower.inSeason = false
                println(
                    """
═════════════════════════════════════════════════════════
🌹You have changed this flowers status to out of season🌹
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
            }
            else if ((changeStatus == 'N') ||  (changeStatus == 'n')){
                println("""
═════════════════════════════════════════════════════════
      🌹You have not changed this flowers status🌹
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
            }
            else {
                println("""
═════════════════════════════════════════════════════════
                   🌹Invalid Option🌹
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
            }
        }
        else {
            changeStatus = readNextChar("The flower is currently out of season... do you want to mark it as in season? (Y for yes): ")
            if ((changeStatus == 'Y') ||  (changeStatus == 'y')){
                flower.inSeason = true
                println("""
═════════════════════════════════════════════════════════
🪷You have changed this flowers status to in season🪷
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
            }
            else if ((changeStatus == 'N') ||  (changeStatus == 'n')){
                println("""
═════════════════════════════════════════════════════════
      🪷You have not changed this flowers status🪷
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
            }
            else {
                println("""
═════════════════════════════════════════════════════════
                   🪷Invalid Option🪷
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
            }
        }
    }
}

//-------------------------------------------
//🌸VARIANT MENU🌸
//-------------------------------------------

private fun addVariantToFlower() {
    val flower: Flower? = askUserToChooseFlower()
    if (flower != null) {
        if (flower.addVariant(Variant(
                variantName = readNextLine("\t What is the Variants Name? : "),
                expectedBLoomLife = readNextInt("\t What is the expected Blooming Time? : "),
                colour = readNextLine("\t What is the Colour?: "),
                isAvailable = readNextLine("\t Is it available? (true or false) : ").toBoolean(),
                price = readNextLine("\t What is the Price? : ").toDoubleOrNull() ?: 0.0
            )))
            println("""
═════════════════════════════════════════════════════════
                   🌸Add Successful!🌸
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
        else println("""
═════════════════════════════════════════════════════════
                 🌸Add NOT Successful🌸
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
    }
}
fun updateVariantContentsInFlower() {
    val flower: Flower? = askUserToChooseFlower()
    if (flower != null) {
        val variant: Variant? = askUserToChooseVariant(flower)
        if (variant != null) {
            //val newContents = readNextLine("Enter new contents: ")
            if (flower.update(variant.variantId, Variant(
                    variantName = readNextLine("\t What is the Variants Name? : "),
                    expectedBLoomLife = readNextInt("\t What is the expected Blooming Time? : "),
                    colour = readNextLine("\t What is the Colour?: "),
                    isAvailable = readNextLine("\t Is it available? (true or false) : ").toBoolean(),
                    price = readNextLine("\t What is the Price? : ").toDoubleOrNull() ?: 0.0
                ))) {
                println("""
═════════════════════════════════════════════════════════
              🌻Variant contents updated🌻
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
            } else {
                println("""
═════════════════════════════════════════════════════════
             🌻Variant contents NOT updated🌻
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
            }
        } else {
            println("""
═════════════════════════════════════════════════════════
                  🌻Invalid Variant Id🌻
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
        }
    }
}

fun deleteAVariant() {
    val flower: Flower? = askUserToChooseFlower()
    if (flower != null) {
        val variant: Variant? = askUserToChooseVariant(flower)
        if (variant != null) {
            val isDeleted = flower.delete(variant.variantId)
            if (isDeleted) {
                println("""
═════════════════════════════════════════════════════════
                 🏵️Deleted Successfully!🏵️
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
            } else {
                println("""
═════════════════════════════════════════════════════════
               🏵️Delete NOT Successfully!🏵️
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
            }
        }
    }
}

fun markVariantStatus() {
    val flower: Flower? = askUserToChooseFlower()
    if (flower != null) {
        val variant: Variant? = askUserToChooseVariant(flower)
        if (variant != null) {
            var changeStatus = 'X'
            if (variant.isAvailable) {
                changeStatus = readNextChar("The Variant is currently available...do you want to mark it as unavailable? (Y for yes): ")
                if ((changeStatus == 'Y') ||  (changeStatus == 'y')) {
                    variant.isAvailable = false
                    println("""
═════════════════════════════════════════════════════════
🌹You have changed this variants status to unavailable🌹
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
                }
                else if ((changeStatus == 'N') ||  (changeStatus == 'n')){
                    println("""
═════════════════════════════════════════════════════════
        🌹This variants status has not changed🌹
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
                }
                else {
                    println("""
═════════════════════════════════════════════════════════
                    🌹invalid option🌹
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
                }
            }
            else {
                changeStatus = readNextChar("The Variant is currently unavailable...do you want to mark it as available? (Y for yes): ")
                if ((changeStatus == 'Y') ||  (changeStatus == 'y')){
                    variant.isAvailable = true
                    println("""
═════════════════════════════════════════════════════════
🌹You have changed this variants status to available🌹
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
                }
                else if ((changeStatus == 'N') ||  (changeStatus == 'n')){
                    println("""
═════════════════════════════════════════════════════════
        🌹This variants status has not changed🌹
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
                }
                else {
                    println("""
═════════════════════════════════════════════════════════
                    🌹invalid option🌹
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
                }
            }
        }
    }
}

//------------------------------------
//🌸FLOWER REPORTS MENU🌸
//------------------------------------

fun searchFlowers() {
    val searchName = readNextLine("Enter the Flower name to search by: ")
    val searchResults = flowerAPI.searchFlowersByName(searchName)
    if (searchResults.isEmpty()) {
        println("""
═════════════════════════════════════════════════════════
                    🌻No Flower Found🌻
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
    } else {
        println(searchResults)
    }
}

//------------------------------------
//🌸VARIANT REPORTS MENU🌸
//------------------------------------
fun searchVariants() {
    val searchName = readNextLine("Enter the Variant Name to search by: ")
    val searchResults = flowerAPI.searchVariantsByName(searchName)
    if (searchResults.isEmpty()) {
        println("""
═════════════════════════════════════════════════════════
                    🌻No Variants Found🌻
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
    } else {
        println(searchResults)
    }
}

fun listAvailableVariants() {
    if (flowerAPI.numberOfAvailableVariants() > 0) {
        println("Total Available Variants: ${flowerAPI.numberOfAvailableVariants()}")
    }
    println(flowerAPI.listAvailableVariants())
}

//------------------------------------
// 🌸Exit App🌸
//------------------------------------
fun exitApp() {
    println("""
═════════════════════════════════════════════════════════
                  🌻Exiting, Goodbye🌻
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
    exitProcess(0)
}

//🌸HELPER FUNCTIONS🌸

private fun askUserToChooseFlower(): Flower? {
    listFlowers()
    if (flowerAPI.numberOfFlowers() > 0) {
        val flower = flowerAPI.findFlower(readNextInt("\nEnter the id of the flower: "))
        if (flower != null) {

            return flower //chosen flower is active
        }
        else {
            println("""
═════════════════════════════════════════════════════════
                🌸Flower id is not valid🌸
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
        }
    }
    return null //selected flower is not active
}

private fun askUserToChooseVariant(flower: Flower): Variant? {
    if (flower.numberOfVariants() > 0) {
        print(flower.listVariants())
        return flower.findOne(readNextInt("\nEnter the id of the variant: "))
    }
    else{
        println ("""
═════════════════════════════════════════════════════════
            🌸No variants for chosen Flower🌸
═════════════════════════════════════════════════════════
         """.trimMargin(">"))
        return null
    }
}