package ca.ammar.website.application.games.GoFish;

import java.util.Scanner;

public class HumanPlayer extends Player {


    Scanner in = new Scanner(System.in);

    /**
     * A constructor that allows you to set the player's unique ID
     *
     * @param name the unique ID to assign to this player.
     * @param deck is the same deck everyone uses
     */
    public HumanPlayer(String name, Deck deck) {
        super(name, deck);
    }

    /**
     * main play method for Human Player
     */
    @Override
    public void play() {
        System.out.println("Player: " + getPlayerID() + "\nPoints: " + getPoints() + "\n");
        System.out.println(playerHand.display());
        int handSize = playerHand.getHandSize();
        System.out.print("Please enter The Card you would like to request: ");
        int reqNum = in.nextInt();
        Card request;
        Card temp;

        while ( reqNum <= 1 || reqNum > handSize) {
            System.out.print("Error: You must enter one of the number provided"
                    + " above.\nPlease enter The Card you would like to request: ");
            reqNum = in.nextInt();
        }
        
        while (game.requestCard(request = playerHand.getCard(reqNum - 1), this)) {

            System.out.println("Request Completed you have taken a card from computer request again: "
                    + playerHand.getCard(playerHand.getHandSize() - 1) + "\n");
            delay(1000);

            temp = playerHand.getCard(playerHand.getHandSize() - 1);

            if (playerHand.checkBook(temp)) {
                addPoint();
                playerHand.extractCard(temp.rank);
                System.out.println("Points Awarded\nNew Points: " + getPoints() + " For " + getPlayerID());
                System.out.println("for card " + temp.rank.name() + "\n");
            }

            System.out.println(playerHand.display());
            System.out.print("Request: ");

        }
        
        


        System.out.println("Request Failed Now you will draw a card");
        playerHand.addDrawnCard();
        System.out.println("You have Drawn A card: "
                + playerHand.getCard(playerHand.getHandSize() - 1) + "\n");
       
        if (playerHand.checkRequest(request)){
            System.out.println("You drew the same card you requested. Request Again!");
            play();   
        }
        
        temp = playerHand.getCard(playerHand.getHandSize() - 1);

        if(playerHand.checkBook(temp)){
            addPoint();
            playerHand.extractCard(temp.rank);
            System.out.println("Points Awarded\nNew Points: " + getPoints() + " For " + getPlayerID());
            System.out.println("for card " + temp.rank.name() + "\n");
            play();
        }

    }

}