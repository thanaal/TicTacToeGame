package TicTacToeGame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;


public class Main extends Application {


    //set an array to store the 9 Buttons
    Button[] btn = new Button[9];

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Initialize the buttons
        for (int i = 0; i< 9; i++) {
            btn[i] = new Button();
            btn[i].setText(" ");
            int finalI = i;
            btn[i].setOnAction(event -> {
                PlayGame(finalI+1, btn[finalI]);
            });
        }

        //set the GridPane
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25,25,25,25));

        //add the buttons to the GridPane
        int k=0;
        for (int i=0; i<9; i+=3){
            for (int j=0; j<3; j++) {
                k = i / 3;
             //   System.out.println(String.valueOf(k) + " " + String.valueOf(j));
                root.add(btn[i+j],j ,k );
            }
        }

        //set the scene
        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());
        primaryStage.setTitle("TicTacToy Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    //set the player to user
    int player = 1;

    //define 2 array lists for the buttons each player clicked
    ArrayList<Integer> player1Sel = new ArrayList<Integer>();
    ArrayList<Integer> player2Sel = new ArrayList<Integer>();

    void PlayGame (int cellId, Button selectedButton){

        //System.out.println("cellId " + cellId);
        if (player == 1) {
            selectedButton.setText("X");
            player1Sel.add(cellId);
            player = 2;  //set player to computer
            computerPlay();
        }
        else if (player == 2){
            selectedButton.setText("O");
            player2Sel.add(cellId);
            player = 1;
        }

        //disable the button
        selectedButton.setDisable(true);

        //check if we have a winner
        checkWinner(false);
      }

    void checkWinner(Boolean tie){
        int winner = -1;

        //if a player gets 3 sucessive horizontal cells
        for (int i=0; i<9; i+=3) {
                if (player1Sel.contains(i + 1) && player1Sel.contains(i + 2) && player1Sel.contains(i + 3)) {
                winner = 1;
            } else if (player2Sel.contains(i + 1) && player2Sel.contains(i + 2) && player2Sel.contains(i + 3)) {
                    winner = 2;
            }
        }
        //if a player get 3 sucessive vertical cells
        for (int i=0; i<3; i++){
                if (player1Sel.contains(i+1) && player1Sel.contains(i+4) && player1Sel.contains(i+7)){
                    winner = 1;
            }
            else if (player2Sel.contains(i+1) && player2Sel.contains(i+4) && player2Sel.contains(i+7)){
                    winner = 2;
            }
        }

        //if a player gets a diagonal
        if ((player1Sel.contains(1) && player1Sel.contains(5) && player1Sel.contains(9)) || ((player1Sel.contains(3) && player1Sel.contains(5) && player1Sel.contains(7)))){
            winner = 1;
        }
        else if ((player2Sel.contains(1) && player2Sel.contains(5) && player2Sel.contains(9)) || ((player2Sel.contains(3) && player2Sel.contains(5) && player2Sel.contains(7)))){
            winner = 2;
        }


        // make the winner msg
        if ((winner==1) || (winner==2) && (!tie)){
            String msg = ("winner is player").join(String.valueOf(winner));
            endGame(msg);
        }

        else if (tie) {
            String msg = "we got a tie!";
            endGame(msg);
        }


    }

    void computerPlay(){
        ArrayList<Integer>  notSelectedCells = new ArrayList<Integer>();

        //update the ArrayList only with the current free cells
        for (int i=0; i<9; i++){
            if ((!player1Sel.contains(i+1)) && (!player2Sel.contains(i+1))){
                notSelectedCells.add(i+1);
            }
        }
        System.out.println(notSelectedCells);
        System.out.println(notSelectedCells.size());

        //if there are no options left for the computer to select, we check for winner
        if (notSelectedCells.size()==0){
            checkWinner(true);
        }

        Random r = new Random();
        int randIdx = r.nextInt(notSelectedCells.size()); //rand = (max - min) + min
        System.out.println("random choice " + randIdx);

        int cell = notSelectedCells.get(randIdx);
        System.out.println("return value " + (cell));
        Button selBut = btn[cell-1]; //subtrack 1 due to zero indexing
        PlayGame(cell, selBut);
    }

    void endGame(String msg){
        System.out.printf(msg);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game info");
        alert.setContentText(msg + ". Press OK to exit");
        alert.setHeaderText("Play info");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}