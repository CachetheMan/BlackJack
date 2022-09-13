import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.DigestOutputStream;
import svu.csc213.Dialog;

public class BlackJack extends GraphicsProgram {

    //data about the game
    private int wager = 0;
    private int balance = 10000;
    private int bank = 10000;


    //labels to display info in the game
    private GLabel bankLabel, wagerLabel, balanceLabel, BlackJack;

    //buttons for controls
    private JButton wagerButton, playButton, hitButton, stayButton,  quitButton;

    // objects we are playing with
    private Deck deck;
    private GHand player, dealer;

    @Override
    public void init(){
        this.setBackground(Color.BLUE);

        deck = new Deck();


        wagerButton = new JButton("Wager");
        playButton = new JButton("Play");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        quitButton = new JButton("Quit");


        //add buttons to the screen
        add(wagerButton, SOUTH);
        add(playButton, SOUTH);
        add(hitButton, SOUTH);
        add(stayButton, SOUTH);
        add(quitButton, SOUTH);


        //TODO handle initial button visibility
        addActionListeners();

        //TODO set up your GLabels
        bankLabel = new GLabel("Bank Money "+ bank);
        wagerLabel = new GLabel("Wager "+ wager);
        balanceLabel = new GLabel("Balance "+ balance);
        BlackJack = new GLabel("BlackJack");

        //add labels to the screen

        add(bankLabel, 25,25);
        add(wagerLabel, 25,425);
        add(balanceLabel,25,400);


    }

    @Override
    public void actionPerformed(ActionEvent ae){
        switch (ae.getActionCommand()){
            case "Wager":
                wager = Dialog.getInteger("How much do you want to bet?");


                if(wager > balance){
                    Dialog.showMessage("Your bet was more than what you have, put it in again");

                }else{
                    balance -= wager;

                    balanceLabel.setLabel("Balance " + balance);
                    wagerLabel.setLabel("Wager " + wager);

                    wagerButton.setVisible(false);

                }

                break;
            case "Play":
                if(wager == 0 ){
                    Dialog.showMessage("You need to wager first.");
                } else {

                    generateCards();

                }
                break;
            case "Hit":
                player.hit();
                if(player.getTotal() > 21){
                   lost();

                }else if ( player.getTotal() == 21){
                    dealer.flipCard(0);
                    if(dealer.getTotal() == 21){
                        Dialog.showMessage("You tied");
                        remove(player);
                        remove(dealer);
                        deck.shuffle();

                    }else{
                        won();
                    }
                }

                break;
            case "Stay":

                dealer.flipCard(0);
                pause(100);

                while(dealer.getTotal() < 16){
                    dealer.hit();
                    pause(200);
                }

                if(dealer.getTotal() > 21){
                    won();
                }
                if(player.getTotal() > dealer.getTotal()){
                won();
                }else if (dealer.getTotal() > player.getTotal()){
                    lost();
                }

                break;
            case "Quit":
                System.exit(0);

        }
    }
    
    public void lostMoney(){
        if(balance == 0){
            Dialog.showMessage("You're Out Of Money Bozo");
            playButton.setVisible(false);
            hitButton.setVisible(false);
        }
    }



    public void generateCards(){
        BlackJack.setVisible(false);
        dealer = new GHand(new Hand(deck, true));
        add(dealer, 200, 100);

        player = new GHand(new Hand(deck, false));
        add(player, 200, 300);
    }
    private void won(){
        Dialog.showMessage("You Win Gg");
        Dialog.showMessage("You get $" + wager);
        remove(player);
        remove(dealer);
        deck.shuffle();
        balance += wager * 2;
        bank -= wager;
        wager = 0;
        bankLabel.setLabel("Bank Money "+ bank);
        balanceLabel.setLabel("Balance "+ balance);
        wagerLabel.setLabel("Wager " + wager);
        wagerButton.setVisible(true);
    }

    public void lost(){
        Dialog.showMessage("You lost L + Ratio + Get Good + Poor ");
        Dialog.showMessage("You Lose $" + wager);
        remove(player);
        remove(dealer);
        deck.shuffle();
        bank += wager;
        wager = 0;
        bankLabel.setLabel("Bank Money "+ bank);
        balanceLabel.setLabel("Balance "+ balance);
        wagerLabel.setLabel("Wager " + wager);
        wagerButton.setVisible(true);
    }
    @Override
    public void run(){
        add(BlackJack, getWidth()/2 -BlackJack.getWidth()/2 - 35, 50);
        BlackJack.setFont("Calibri-bold-32");
        wagerLabel.setFont("Calibri-bold-16");
        bankLabel.setFont("Calibri-bold-16");
        balanceLabel.setFont("Calibri-bold-16");
    }

    public static void main(String[] args) {
        new BlackJack().start();

    }

}
