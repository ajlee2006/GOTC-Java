import java.util.*;

public final class Crisis extends Card {
    private final ArrayList<Character> defences;

    public Crisis(int id, String name, ArrayList<Character> defences) {
        this.id = id;
        this.name = name;
        this.defences = defences;
        Collections.sort(this.defences);

        this.type = "Crisis";
    }

    public boolean checkDefences(ArrayList<Card> defences) {
        ArrayList<Character> characters = new ArrayList<>();
        for (Card card: defences) {
            characters.add(card.getType().toCharArray()[0]);
        }

        Collections.sort(characters);
        if (this.defences.size() == defences.size()) {
            for (int i = 0; i < defences.size(); i++) {
                if (!this.defences.get(i).equals(characters.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public ArrayList<Character> getDefences() {
        return defences;
    }

    @Override
    public String toString() {
        return type + " card - " + name + "\nRequires " + defences.toString() + " defences";
    }
}
