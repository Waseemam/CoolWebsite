package ca.ammar.website.application.games.GoFish;

import java.util.ArrayList;
import java.util.Collections;

public class Deck{

    private ArrayList <Card> cards;
    private static Deck onlyDeck = null;

    /**
     * main constructor for deck
     * builds the deck
     * shuffles the deck
     */
    private Deck() {
        cards = new ArrayList<>(52);
        deckBuild();
        shuffle();
    }

    /**
     * Singleton for Deck
     * makes sure that there is only one deck in the game
     */
    public static Deck getInstance(){
        if (onlyDeck == null){
            onlyDeck = new Deck();
        }
        return onlyDeck;
    }

    /**
     * deckBuild builds the deck in order
     */
    public void deckBuild(){
        int i = 0;
        for (Suits s : Suits.values()) {
            for (Ranks r : Ranks.values()) {
                cards.add(i, new Card(s, r));
                // increment i here
                // System.out.println(cards.get(i));
                i++;
            }
        }
    }

    /**
     * shuffles the deck using collections
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * returns an arraylist of cards for the start of the game
     * @param amount amount given for hand size
     * @return card
     */
    public ArrayList<Card> getCards(int amount) {
        ArrayList<Card> card = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            card.add(cards.get(0));
            cards.remove(0);
        }
        return card;
    }

    /**
     * returns a singular card for drawing a card
     * @return card
     */
    public Card getCards() {
        Card card;
        card = (cards.get(0));
        cards.remove(0);
        return card;
    }

    @Override
    public String toString() {
        return null;
    }
}
