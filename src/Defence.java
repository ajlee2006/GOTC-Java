import java.util.*;

public final class Defence extends Card {
    private final String defence; // char?
    private final int effect;
    private final String[] effectNames = {
        "Draw 1 additional card",
        "[1 CSC:] Draw 2 additional cards OR Take any card from your discard pile and place it back into your hand",
        "Look at the cards in your opponent's hand. [2 CSC:] Draw 1 additional card AND play 1 additional card this turn",
        "Play 1 additional card this turn",
        "Draw an additional card. [1 CSC:] Draw ANOTHER additional card",
        "Look at the cards in your opponent's hand. [2 CSC:] Draw 1 additional card AND play 1 additional card this turn",
        "If your opponent has 1 or less Community Support points, discard any 2 Defence cards from your opponent's field",
        "If your opponent has 2 or less Community Support points, discard any 1 card from your opponent's field",
        "If your opponent has no Community Support points, look at your opponent's hand and discard 1 card from there"
    };

    public Defence(int id, String name, String type, int effect) {
        this.id = id;
        this.name = name;
        this.defence = type;
        this.effect = effect;

        this.type = "Defence";
    }

    public String getDefence() {
        return defence;
    }

    public int getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return type + " card - " + name + "\nEffect: " + effectNames[effect-1];
    }
}

