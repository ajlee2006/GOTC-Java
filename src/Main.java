public class Main {
    public static void main(String[] args) {
        Player player1 = new Player();
        Player player2 = new Player();

        // I have no idea how this is going to work though
        while (!(player1.isWon()) || player2.isWon()) {
            System.out.println("---- PLAYER1 ----");
            player1.playTurn(player2);
            if (player1.isWon()) {
                System.out.println("Player 1 won!");
                break;
            }
            System.out.println("---- PLAYER2 ----");
            player2.playTurn(player1);
            if (player2.isWon()) {
                System.out.println("Player 1 won!");
                break;
            }
        }
    }
}
