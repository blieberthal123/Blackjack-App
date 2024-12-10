package com.blackjack;
import java.io.FileNotFoundException;

public class Card {

    private final String suit;
    private final String value;
    private final String imgPath;
    private int numberValue;
    
        //Creates a new card with given suit, value, and number value
        public Card(String suit, String value) throws FileNotFoundException {
            this.suit = suit;
            this.value = value;
            this.imgPath = "images/" + value + "_of_" + suit + ".png";
            switch (value) {
                case "jack":
                case "queen":
                case "king":
                    this.numberValue = 10;
                    break;
                case "ace":
                    this.numberValue = 11;
                    break;
                default:
                    this.numberValue = Integer.parseInt(value);
                    break;
            }
        }
    
        //return suit of card
        public String getSuit() {
            return this.suit;
        }
    
        //return value of card
        public String getValue() {
            return this.value;
        }
        
        //return image path of card
        public String getImage() {
            return this.imgPath;
        }
    
        //return numerical value of card
        public int getNumberValue() {
            return this.numberValue;
        }

        //allow numerical value to be changed (for aces)
        public void setNumberValue(int value) {
            this.numberValue = value;
    }
}