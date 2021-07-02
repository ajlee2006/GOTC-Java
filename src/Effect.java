import java.util.*;

public class Effect {
    public static void runEffect(Player player, Player opponent, int effect) {
        switch (effect) {

            // defence cards
            case 1 ->
                    player.drawCards(1);
            case 2 -> {
                if (player.getCommunity().size() >= 1) {
                    System.out.println("Choose one from below:");
                    System.out.println("1) Draw 2 additional cards");
                    System.out.println("2) Take any card from your discard pile and place it back into your hand");

                    Scanner input = new Scanner(System.in);
                    int choice = input.nextInt();
                    if (choice == 1) {
                        player.drawCards(2);
                    } else if (choice == 2) {
                        player.drawDiscard(1);
                    }
                }
            }
            case 3,6 -> {
                // Look at the cards in your opponent's hand.
                System.out.println("Cards in your opponent's hand:");
                for (int i = 0; i < opponent.getHand().size(); i++) {
                    System.out.println(i + ") " + opponent.getHand().get(i).getName());
                }
                if (player.getCommunity().size() >= 2) {
                    player.drawCards(1);
                    player.playCard(opponent);
                }
            }
            case 4 -> {
                player.playCard(opponent);
            }
            case 5 -> player.drawCards(1 + (player.getCommunity().size() >= 2 ? 1 : 0)); //bro

            // event cards
            case 7 -> {
                // If your opponent has 1 or less Community Support points. Discard any 2 Defence cards from your opponent's field
                if (opponent.getCommunity().size() <= 1) {
                    System.out.println("Choose 2 Defence cards from your opponent's field to remove:");

                    ArrayList<ArrayList<Card>> source = new ArrayList<>();
                    source.add(opponent.getDefences());
                    Player.moveCards(source, opponent.getDiscard(), e -> e != 2);
                }
            }
            case 8 -> {
                // If your opponent has 2 or less Community Support points. Discard any 1 card from your opponent's field
                if (opponent.getCommunity().size() <= 2) {
                    System.out.println("Choose 1 card from your opponent's field to discard:");

                    ArrayList<ArrayList<Card>> source = new ArrayList<>();
                    source.add(opponent.getDefences());
                    source.add(opponent.getCommunity());
                    Player.moveCards(source, opponent.getDiscard(), e -> e != 1);
                }
            }
            case 9 -> {
                // If your opponent has no Community Support points. Look at your opponent's hand and discard 1 card from there
                if (opponent.getCommunity().size() == 0) {
                    System.out.println("Choose 1 card from your opponent's to discard:");

                    ArrayList<ArrayList<Card>> source = new ArrayList<>();
                    source.add(opponent.getHand());
                    Player.moveCards(source, opponent.getDiscard(), e -> e != 1);
                }
            }
        }
    }
}
