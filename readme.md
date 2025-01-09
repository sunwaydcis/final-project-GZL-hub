# Trading Game Scala

## Overview

This project is a game interface developed using Scala and JavaFX. It includes various controllers to manage different aspects of the game, such as the market, bank, moneylender, and location selection. Each controller handles user interactions and updates the game state accordingly.

## Features

- **Market Interface**: Allows players to buy and sell items.
- **Bank Interface**: Enables players to deposit and withdraw money.
- **Moneylender Interface**: Facilitates borrowing and repaying money.
- **Location Selection**: Lets players select different locations in the game.
- **Player Interface**: Displays character stats and handles navigation to other interfaces.
- **Winning Alert**: Notifies players when they reach a net worth of 1,000,000.

## Technologies Used

- **Scala**: The primary programming language used for the project.
- **JavaFX**: Used for building the user interface.
- **FXML**: Used for defining the layout of the user interfaces.
- **SBT**: The build tool used for managing dependencies and building the project.

## Project Structure

- `src/main/scala/ch/makery/address/controller`: Contains the controller classes for different game interfaces.
- `src/main/scala/ch/makery/address/model`: Contains the model classes and game state management.
- `src/main/resources/ch/makery/address/view`: Contains the FXML files for the user interface layouts.
