package ca.ammar.website.application.games.GoFish;

public class Game {
    
    private static Game onlyGame = null;
    private ComputerPlayer computerPlayer;
    private HumanPlayer player1;
    private Deck deck;

    /**
     * main constructor for game
     */
    private Game() {
        deck = Deck.getInstance();
    }

    /**
     * makes sure there is only one game
     * and allows everyone to use it
     * @return the game
     */
    public static Game getInstance(){
        if (onlyGame == null){
            onlyGame = new Game();
        }
        return onlyGame;
    }

    /**
     * play method that runs the players play methods
     */
    public void play() {
        while ((player1.getPoints() + computerPlayer.getPoints()) != 13) {
            player1.play();
            computerPlayer.play();
        }
    }

    /**
     * makes the new players that are going to play this game
     * @param name is the unique name for the human player
     */
    public void initialize(String name){
        computerPlayer = new ComputerPlayer(deck);
        player1 = new HumanPlayer(name, deck);
    }

    /**
     * draws a card from the deck
     * @return a random card from the deck
     */
    public Card drawCard() {
        return deck.getCards();
    }
    


    /**
     * request card method
     * @param card for the card the user requested
     * @param requester for who requested the card
     * @return if request was successful or not
     */
    public boolean requestCard(Card card, Player requester) {
        Player opponent = null;
        if (requester != computerPlayer){
            opponent = computerPlayer;
        }else{
            opponent = player1;
        }
            if(opponent.playerHand.checkCard(card)){
                requester.playerHand.addCards(opponent.playerHand.extractCard(card.rank));
                return true;
            }else{
                return false;
            }
    }

    /**
     * compares both players points
     * @return the player with more points
     */
    public Player checkWinner() {
        if (player1.getPoints() > computerPlayer.getPoints()) {
            return player1;
        }
        return computerPlayer;
    }
}
