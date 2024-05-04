package main.java.game;5

import main.java.game.engine.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Dice Realms: Quest for the Elemental Crests!");
        CLIGameController cliGameController = new CLIGameController();
        cliGameController.startGame();
    }
}
