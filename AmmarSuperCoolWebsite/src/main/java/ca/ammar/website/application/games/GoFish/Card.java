/**
 * SYST 17796 Project Winter 2021 Base code.
 * Students can modify and extend to implement their game.
 * Add your name as a modifier and the date!
 */
package ca.ammar.website.application.games.GoFish;

/**
 * A class to be used as the base Card class for the project. Must be general
 * enough to be instantiated for any Card game. Students wishing to add to the code
 * should remember to add themselves as a modifier.
 * @author dancye
 * @Modifier Ammar, soheila, omar
 */
public class Card
{
    Suits suit;
    Ranks rank;
    //Constructor

    /**
     * Main Constructor For card
     * @param suit takes a suit from enum list
     * @param rank takes a rank from enum list
     */
    public Card(Suits suit, Ranks rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * @return displays rank and suit of said card
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    /**
     * override because we only compare rank
     */
    @Override
    public boolean equals(Object o){
        Card card = (Card) o;
        return card.rank == this.rank;
    }

}