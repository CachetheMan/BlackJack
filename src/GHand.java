import acm.graphics.GCompound;

public class GHand extends GCompound {

    // the hand DATA to display
    private Hand hand;

    // the GCards you will use to display them
    private GCard[] cards;

    public GHand(Hand hand){
        this.hand = hand;

        cards = new GCard[7];

        for (int i = 0; i < hand.getCount(); i++) {
            Card card = hand.getCard(i);
            GCard gCard = new GCard(card);

            // add the gCard to the compound
            add(gCard, i * gCard.getWidth()*1.25 + gCard. getWidth()/4, 0);

            // put the card into the array
            cards[i] = gCard;
        }
    }

    //get the total value of the hand
    public int getTotal(){
        return hand.getTotal();
    }
    // flip the first card over
    public void flipCard(int index){
        cards[index].setFaceUp(!cards[index].getFaceUp());
    }
    // draw a card from the deck (a hit)
    public void hit(){
        hand.hit();
        //make a new GCard using the card our hand just drew
        Card temp = hand.getCard(hand.getCount()-1);
        GCard gCard = new GCard(temp);
        // put that GCard in the GCard array
        System.out.println("Hand count is now: " + hand.getCount());
        cards[hand.getCount()-1] = gCard;


        // add the new gcard to the compound
        add(gCard, (hand.getCount() + 1 ) * (gCard.getWidth()*1.25) - 272, 0 );
    }
}
