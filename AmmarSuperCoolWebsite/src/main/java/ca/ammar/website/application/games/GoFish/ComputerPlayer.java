package ca.ammar.website.application.games.GoFish;


public class ComputerPlayer extends Player{


    /**
     * main constructor for computer player
     * @param deck makes sure is the same deck as everyone
     * @param name computer because it never changes
     */
    public ComputerPlayer(Deck deck){
        super("computer" , deck);
    }

    /**
     * main play method for computer player
     */
    @Override
    public void play()  {

        System.out.println("Player: " + getPlayerID() + "\nPoints: " + getPoints() + "\n");
        System.out.println("Computer is picking...");
        delay(3000);
        Card request; 
        Card temp;

        int numChosen = (int) Math.floor(Math.random() * (playerHand.getHandSize()-1));

        while(game.requestCard(request = playerHand.getCard(numChosen), this)) {
            numChosen = (int) Math.floor(Math.random() * (playerHand.getHandSize()-1));
            System.out.println("Request Completed Computer has taken a Card From you and will request again: "
                    + playerHand.getCard(playerHand.getHandSize() - 1));

            temp = playerHand.getCard(playerHand.getHandSize() - 1);
            if(playerHand.checkBook(temp)){
                addPoint();
                playerHand.extractCard(temp.rank);
                System.out.println("Points Awarded\nNew Points: " + getPoints() + " For " + getPlayerID());
                System.out.println("for card " + temp.rank.name() + "\n");
            }

            delay(2000);
        }
        System.out.println("Computer's Request Failed now will draw a card \n");
        playerHand.addDrawnCard();
        delay(2000);

        
        if (playerHand.checkRequest(request)){
            System.out.println("Computer drew the same card it requested!");
            delay(1500);
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
