package ca.ammar.website.application.games.GoFish;
import java.util.ArrayList;

public class PlayerHand{

    private ArrayList<Card> cards;
    private String playerName;

    /**
     * Main Constructor for PlayerHand
     * @param deck uses the same deck as everyone
     * @param name used to display the owner of hand
     * */
    protected PlayerHand(Deck deck, String name) {
        cards = new ArrayList<>();
        takeCards(7, deck);
        playerName = name;
    }

    /**
     * takeCards takes a arraylist from deck
     * @param amount being the amount of cards
     * @param deck being the deck taken out from
     * used once for setting up hand
     * */
    private void takeCards (int amount, Deck deck){
        cards.addAll(deck.getCards(amount));
    }

    /**
     * @param card
     * takes an arraylist
     * and adds it to the hands arraylist
     * */
    public void addCards(ArrayList<Card> card){
        cards.addAll(card);
    }

    /**
     * @param card is the rank of the card
     * compares it to the hand
     * all cards found are removed from hand
     * @return all the cards found
     * */
    public ArrayList<Card> extractCard(Ranks card){
        ArrayList<Card> cardsFound = new ArrayList<>();
        for (Card i : cards){
            if(i.rank.equals(card)){
                cardsFound.add(i);
            }
        }
        cards.removeAll(cardsFound);
        return cardsFound;
    }

    /**
     * draws the card and adds it the hand
     * */
    protected void addDrawnCard(){
        cards.add(Game.getInstance().drawCard());
    }

    /**
     * checks to see if the hand has the card
     * @param card is the card compared
     * @return true if hand has card false if not
     */
    public boolean checkCard(Card card){
        return cards.contains(card);
    }

    /**
     * shows the card in that index
     * @param index is the index of the card
     * @return the card in the index
     */
    protected Card getCard(int index){
       return cards.get(index);
    }

    /**
     * @return the size of the arraylist cards
     */
    public int getHandSize(){
        return cards.size();
    }

    /**
     * @return the arraylist in order
     */
    protected String display(){
        int counter = 1;
        String cards = "";
        for (Card i : this.cards){
            cards += counter + ". " + i + "\n";
            counter++;
        }
        return cards;
    }

    /**
     * checks to see if the player hand has a book
     * @param card is the card that is being searched
     * @return returns true if you have 4 of the same card
     */
    public boolean checkBook(Card card) {
        int counter = 0;
        for(Card i : cards){
            if (i.equals(card)){
                counter++;
            }
        }
        return counter == 4;
    }
    
     /**
     * this method checks the drawn card against the initially requested Card
     * the result will determine if player goes for another round 
     * @param card
     * @return if the drawn card is the same as requested or not 
     */
    public boolean checkRequest(Card card){
        if (cards.get(cards.size()-1).equals(card)){
            return true;
        }
        return false;
    }
}
