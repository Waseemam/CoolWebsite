package ca.ammar.website.application.games.GoFish;


import java.util.Scanner;

public class GoFish {

    /**
     * the main start method for the game
     */
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        System.out.println("welcome to GoFish a game against the computer");
        System.out.print("please enter a Player Name: ");
        // makes a new game
        Game game = Game.getInstance();
        // initializes the game also takes input for username
        game.initialize(in.nextLine());
        // plays the game
        game.play();
        // after the game is finished checks to see the winner
        System.out.println(game.checkWinner().getPlayerID() + " is the WINNER. HOORAY!!");

    }
}
