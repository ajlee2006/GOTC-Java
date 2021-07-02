public class Community extends Card{
    public Community(int id, String name) {
        this.id = id;
        this.name = name;

        this.type = "Community";
    }

    @Override
    public String toString() {
        return "Community support card";
    }
}
