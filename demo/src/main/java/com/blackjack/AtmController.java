package com.blackjack;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AtmController implements Initializable{
    
    @FXML
    private TextField atmInput;

    @FXML
    private Label atmMoneyLabel;

    private int amount;

    //add amount of money user inputs to total money, with exceptions for errors and ranges of numbers
    public void withdraw(@SuppressWarnings("exports") ActionEvent event) {
        try {
            amount = Integer.parseInt(atmInput.getText());
            if (amount < 0) {
                atmMoneyLabel.setText("$" + App.getMoney() + " - NO DEPOSITS");
            } else if (amount > 10000) {
                atmMoneyLabel.setText("$" + App.getMoney() + " - Bro's not robbin a bank");
            } else {
                App.setMoney(App.getMoney() + amount);
                atmMoneyLabel.setText("$" + App.getMoney());
            }
        } catch (NumberFormatException e) {
            atmMoneyLabel.setText("$" + App.getMoney() + " - ERROR ERROR ERROR");
        } catch (Exception e) {
            atmMoneyLabel.setText("error");
        }
        atmInput.clear();
    }

    //set scene to home
    @FXML
    public void switchToHome() throws IOException {
        App.setRoot("home");
    }

    //initialize money label to current value of money
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atmMoneyLabel.setText("$" + App.getMoney());
    }

}
