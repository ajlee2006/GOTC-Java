import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;

public class Player {
    private static int numPlayers = 0;

    private final ArrayList<Card> deck;
    private final ArrayList<Card> hand;
    private final ArrayList<Card> discard;

    private Crisis crisis;
    private final ArrayList<Card> defences;
    private final ArrayList<Card> community;

    private boolean won;


    public Player() {
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discard = new ArrayList<>();
        this.defences = new ArrayList<>();
        this.community = new ArrayList<>();
        try {
            loadDeck();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        this.won = false;

        drawCards(5-numPlayers);
        numPlayers++;
    }

    private void loadDeck() throws FileNotFoundException {
        Random random = new Random();
        Scanner input = new Scanner(new File("data.csv"));
        ArrayList<Crisis> crises = new ArrayList<>();

        int id = 1;
        while (input.hasNextLine()) {
            String[] line = input.nextLine().split(",");
            switch (line[0]) {
                case "Crisis" -> {
                    ArrayList<Character> defences = new ArrayList<>();
                    for (char i: line[2].toCharArray()) {
                        defences.add(i);
                    }
                    crises.add(new Crisis(id, line[1], defences));
                }
                case "Defence" -> deck.add(new Defence(id, line[1], line[2], Integer.parseInt(line[3])));
                case "Community" -> deck.add(new Community(id, line[1]));
                case "Event" -> deck.add(new Event(id, line[1], Integer.parseInt(line[3])));
            }

            id++;
        }

        crisis = crises.get(random.nextInt(crises.size()));
        Collections.shuffle(deck);
    }

    public void drawCards(int n) {
        for (int i = 0; i < n; i++) {
            hand.add(deck.remove(deck.size()-1));
        }
    }

    public void drawDiscard(int n) {
        System.out.println("Please select " + n + " card(s) from the discard pile to draw:");

        ArrayList<ArrayList<Card>> source = new ArrayList<>();
        source.add(discard);
        moveCards(source, hand, e -> e != n);
    }

    private void reduceHand() {
        if (hand.size() > 7) {
            System.out.println("You have too many cards. Please choose " + (hand.size() - 7) + " card(s) to discard:");

            ArrayList<ArrayList<Card>> source = new ArrayList<>();
            source.add(hand);
            moveCards(source, discard, e -> e < hand.size()-7);
        }
    }

    public static void moveCards(ArrayList<ArrayList<Card>> source, ArrayList<Card> end, Predicate<Integer> condition) {
        ArrayList<Integer> startIndices = new ArrayList<>();
        int count = 0;
        for (ArrayList<Card> list: source) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + count) + ") " + list.get(i).toString() + "\n");
            }
            count += list.size();
            startIndices.add(count);
        }

        Scanner input = new Scanner(System.in);
        String[] selected;
        do {
            selected = input.nextLine().split("[ ,;:]+");
        } while (condition.test(selected.length));

        ArrayList<Integer> selectedInt = new ArrayList<>();
        for (String str: selected) {
            selectedInt.add(Integer.parseInt(str));
        }
        Collections.sort(selectedInt);

        for (int i = selectedInt.size()-1; i >= 0; i--) {
            int j;
            for (j = 0; j < startIndices.size(); j++) {
                if (selectedInt.get(i) < startIndices.get(j)) {
                    end.add(source.get(j).get(i - (j > 0 ? startIndices.get(j-1) : 0)));
                }
            }
        }
    }

    public void playCard(Player opponent) {
        System.out.println("Choose a card from your hand to play:\n");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + ") " + hand.get(i).toString() + "\n");
        }

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        Card card = hand.remove(choice);
        switch (card.getType()) {
            case "Community" -> community.add(card);
            case "Defence" -> {
                defences.add(card);
                Defence defence = (Defence) card;
                Effect.runEffect(this, opponent, defence.getEffect());
            }
            case "Event" -> {
                discard.add(card);
                Event event = (Event) card;
                Effect.runEffect(this, opponent, event.getEffect());
            }
        }

        if (crisis.checkDefences(defences)) {
            won = true;
        }
    }

    public void playTurn(Player opponent) {
        drawCards(1);
        playCard(opponent);
        reduceHand();
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getDiscard() {
        return discard;
    }

    public Crisis getCrisis() {
        return crisis;
    }

    public ArrayList<Card> getDefences() {
        return defences;
    }

    public ArrayList<Card> getCommunity() {
        return community;
    }

    public boolean isWon() {
        return won;
    }
}
