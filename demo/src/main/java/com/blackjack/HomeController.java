package com.blackjack;

import java.io.IOException;

import javafx.fxml.FXML;

public class HomeController {

    //set scene to atm
    @FXML
    public void switchToAtm() throws IOException {
        App.setRoot("atm");
    }

    //set scene to game
    @FXML
    public void switchToGame() throws IOException {
        App.setRoot("game");
    }

}
