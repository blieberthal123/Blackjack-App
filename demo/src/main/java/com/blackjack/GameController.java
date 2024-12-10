package com.blackjack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameController implements Initializable {

    @FXML
    private Label betLabel, playerLabel, dealerLabel, outcomeLabel;
    @FXML
    private Slider betSlider;
    @FXML
    private Button startButton, homeButton, hitButton, standButton;
    @FXML
    private ImageView playerCard1, playerCard2, playerCard3, playerCard4, playerCard5, 
                      dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5;
  
    private int betAmount = 0;
    private final Deck deck = new Deck();
    private final ArrayList<Card> playerHand = new ArrayList<>();
    private final ArrayList<Card> dealerHand = new ArrayList<>();
    private int currentCardView = 1;
    private int playerCount = 0;
    
    //Set all javafx objects to starting point
    @FXML
    public void start(@SuppressWarnings("exports") ActionEvent e) throws FileNotFoundException {
        startButton.setVisible(false);
        homeButton.setVisible(false);
        betSlider.setVisible(false);
        betLabel.setVisible(false);
        hitButton.setVisible(true);
        standButton.setVisible(true);
        playerCard1.setVisible(true);
    }

    //deal card to user on hit and check for certain values
    @FXML
    public void hit(@SuppressWarnings("exports") ActionEvent e) {
        dealToUser();
        Image cardImage = new Image(getClass().getResourceAsStream(playerHand.get(currentCardView-1).getImage()));
        if (currentCardView == 1){
            playerCard1.setVisible(true);
            playerCard1.setImage(cardImage);
        } else if (currentCardView == 2) {
            playerCard2.setVisible(true);
            playerCard2.setImage(cardImage);
        } else if (currentCardView == 3) {
            playerCard3.setVisible(true);
            playerCard3.setImage(cardImage);
        } else if (currentCardView == 4) {
            playerCard4.setVisible(true);
            playerCard4.setImage(cardImage);
        } else if (currentCardView == 5) {
            playerCard5.setVisible(true);
            playerCard5.setImage(cardImage);

            //automatic win if user has 5 cards and still under 21
            if (playerCount + playerHand.get(currentCardView-1).getNumberValue() < 21) {
                playerCount += playerHand.get(currentCardView-1).getNumberValue();
                playerLabel.setText(Integer.toString(playerCount));
                App.setMoney(App.getMoney()+betAmount);
                hitButton.setVisible(false);
                standButton.setVisible(false);
                homeButton.setVisible(true);
                outcomeLabel.setText("5-Card Charlie! You win $" + betAmount);
            }
        }

        //change value of ace to 1 if it would put total value over 21
        if (playerHand.get(currentCardView-1).getValue().equals("ace") && 
            playerCount + playerHand.get(currentCardView-1).getNumberValue() > 21) {
                playerHand.get(currentCardView-1).setNumberValue(1);
        }

        playerCount += playerHand.get(currentCardView-1).getNumberValue();
        playerLabel.setText(Integer.toString(playerCount));
        
        //check if user busts or has blackjack
        if (playerCount > 21) {
            App.setMoney(App.getMoney()-betAmount);
            hitButton.setVisible(false);
            standButton.setVisible(false);
            homeButton.setVisible(true);
            outcomeLabel.setText("BUST! You lose $" + betAmount);
        } else if (playerCount == 21) {
            App.setMoney(App.getMoney()+betAmount);
            hitButton.setVisible(false);
            standButton.setVisible(false);
            homeButton.setVisible(true);
            outcomeLabel.setText("Blackjack! You win $" + betAmount);
        }
        currentCardView++;
    }

    //deal cards to dealer, check for certain values, and decide who wins
    @FXML
    public void stand(@SuppressWarnings("exports") ActionEvent e) throws InterruptedException {
        currentCardView = 1;
        int dealerCount = 0;
        hitButton.setVisible(false);
        standButton.setVisible(false);
        while (dealerCount <= 17) {
            dealToDealer();
            Image cardImage = new Image(getClass().getResourceAsStream(dealerHand.get(currentCardView-1).getImage()));
            if (currentCardView == 1){
                dealerCard1.setVisible(true);
                dealerCard1.setImage(cardImage);
            } else if (currentCardView == 2) {
                dealerCard2.setVisible(true);
                dealerCard2.setImage(cardImage);
            } else if (currentCardView == 3) {
                dealerCard3.setVisible(true);
                dealerCard3.setImage(cardImage);
            } else if (currentCardView == 4) {
                dealerCard4.setVisible(true);
                dealerCard4.setImage(cardImage);
            } else if (currentCardView == 5) {
                dealerCard5.setVisible(true);
                dealerCard5.setImage(cardImage);
            }

            //change value of ace to 1 if it would put total value over 21
            if (dealerHand.get(currentCardView-1).getValue().equals("ace") && 
                dealerCount + dealerHand.get(currentCardView-1).getNumberValue() > 21) {
                    dealerHand.get(currentCardView-1).setNumberValue(1);
            }
            
            dealerCount += dealerHand.get(currentCardView-1).getNumberValue();
            dealerLabel.setText(Integer.toString(dealerCount));
            if (dealerCount > playerCount)
                break;
            currentCardView++;
        }

        //check if dealer busts, who wins, or if there is a tie
        if (dealerCount > 21) {
            App.setMoney(App.getMoney()+betAmount);
            outcomeLabel.setText("Dealer Bust! You win $" + betAmount);
        } else if (playerCount > dealerCount) {
            App.setMoney(App.getMoney()+betAmount);
            outcomeLabel.setText("You Win $" + betAmount);
        } else if (dealerCount > playerCount) {
            App.setMoney(App.getMoney()-betAmount);
            outcomeLabel.setText("You Lose $" + betAmount);
        } else {
            outcomeLabel.setText("Tie! $" + betAmount + " returned");
        }

        homeButton.setVisible(true);
    }

    //add card to player's hand
    private void dealToUser() {
        playerHand.add(this.deck.dealCard());
    }

    //add card to dealer's hand
    private void dealToDealer() {
        dealerHand.add(this.deck.dealCard());
    }

    //set scene to home
    @FXML
    public void switchToHome() throws IOException {
        App.setRoot("home");
    }

    //initialize certain javafx objects, and update betLabel based on betSlider value
    @SuppressWarnings("unused")
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        betSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number t, Number t1) -> {
            if (App.getMoney() > 0) {
                betSlider.setMax(App.getMoney());
                betAmount = (int) betSlider.getValue();
                betLabel.setText("$" + betAmount);
                if (betAmount > 0) {
                    startButton.setVisible(true);
                } else {
                    startButton.setVisible(false);
                }
            } else {
                betLabel.setText("Get some $$$ brokie");
            }
        });
        playerLabel.setText("0");
        dealerLabel.setText("0");
    }

}
