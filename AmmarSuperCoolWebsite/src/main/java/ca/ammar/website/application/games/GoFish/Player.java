/**
 * SYST 17796 Project Winter 2021 Base code.
 * Students can modify and extend to implement their game.
 * Add your name as a modifier and the date!
 * hello
 */
package ca.ammar.website.application.games.GoFish;

/**
 * A class that models each Player in the game. Players have an identifier, which should be unique.
 * @author dancye
 */
public abstract class Player{
    
    private String playerID; //the unique ID for this player
    protected PlayerHand playerHand;
    private int playerPoints = 0;
    Game game = Game.getInstance();

    /**
     * A constructor that allows you to set the player's unique ID
     * @param name the unique ID to assign to this player.
     */
    public Player(String name, Deck deck)
    {
        playerID = name;
        playerHand = new PlayerHand(deck, playerID);
    }

    /**
     * @return the playerID
     */
    public String getPlayerID()
    {
        return playerID;
    }

    /**
     * adds a point to the user
     * using @playerPoints
     * */
    protected void addPoint(){
            playerPoints++;
    }

    /**
     * current points of the player
     * @return the playerPoints
     */
    public int getPoints (){
        return playerPoints;
    }

    /**
     * delay method to slow down the program to make it more
     * user friendly
     */
    public void delay(int amount){
        try {
            Thread.sleep(amount);
        } catch (InterruptedException ignore) {
        }
    }
    
    

    /**
     * The method to be instantiated when you subclass the Player class
     * with your specific type of Player and filled in with logic to play your game.
     */
    public abstract void play() throws InterruptedException;

}
