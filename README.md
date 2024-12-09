Oops a Daisy Console App

Overview

  This Flower Management System is a Kotlin-based console application that manages flower data and their variants for a small Flower shop called Oops a daisy.
  The application allows users to create, read, update, delete (CRUD), and perform various operations such as searching and listing flowers and variants. 
  This system is useful for maintaining a catalog of flowers, including their seasonal availability and different variants.

Features
  CRUD Operations for Flower objects and their Variant details.
  Listing of all flowers and available variants.
  Searching for flowers by name.
  Searching for variants by name.
  Counting and showing the available variants.
  Counting and showing the flowers currently in season.
  Update and Delete operations for individual flower and variant objects.

Project Structure

1. models Package
Variant Class: Represents different variants of a flower, including attributes like name, colour, expected blooming life, availability, and price.
Flower Class: Represents a flower and includes attributes like name, average height, in season status, and a set of Variant objects. It also provides methods for adding, updating, finding, and deleting variants.

2. utils Package
Helper Functions:
readNextInt(prompt: String?): Reads an integer from the user with error handling for non-numeric input.
readNextLine(prompt: String?): Reads a string input from the user.
readNextChar(prompt: String?): Reads a single character from the user with error handling.

4. controllers Package
FlowerAPI Class: Manages a collection of Flower objects and provides CRUD operations, as well as methods for searching and counting. This class acts as an API for interacting with flowers and their variants.

How to Use

Prerequisites
  Use a software for Kotlin and JDK, I used IntelliJ community edition.

Running the Application
  Clone or download the repository
  Run the main.kt file.

Example Commands
  Add a new flower: Use the provided methods to input flower details and add it to the system.
  List all flowers: Select the option from the menu by typing in the associated number.
  Search for flowers by name: Select the option from the menu to find and list flowers that match the name you input.
  List available variants: Select the option from the menu by typing in the associated number.
