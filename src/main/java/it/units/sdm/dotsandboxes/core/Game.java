package it.units.sdm.dotsandboxes.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Game {

    private final List<Player> players;
    private final Board gameBoard;
    private final ArrayList<Move> moves;
    private final List<Point> completedBoxes = new ArrayList<>();

    public Game(int boardWidth, int boardHeight, List<Player> players) {
        this.players = Objects.requireNonNull(players); // why have this condition if we check the size immediately after?
        if (this.players.size() < 2) {
            throw new RuntimeException("Game requires a minimum of 2 players.");
        }
        this.players.forEach(Objects::requireNonNull);
        gameBoard = new Board(boardWidth, boardHeight);
        moves = new ArrayList<>();
    }

    public Game(int boardWidth, int boardHeight, Player... players) {
        this(boardWidth, boardHeight, Arrays.asList(players));
    }

    /**
     * Creates game with two players and a default 5x5 board
     *
     * @param player1
     * @param player2
     */
    public Game(Player player1, Player player2) {
        this(5, 5, player1, player2);
    }

    public Player nextPlayer() {    // why isn't this the current player? the player loses control once it makes a move
        // we chose to make the first player entered start first
        if (moves.isEmpty()) {
            return players.getFirst();
        }
        int lastPlayerIndex = players.indexOf(moves.getLast().player());
        int nextPlayerIndex = (lastPlayerIndex + 1) % players.size();
        return this.players.get(nextPlayerIndex);
    }

    public Player currentPlayer() {
        return moves.getLast().player();
    }

    public void makeNextMove(Line line) {
        Line lineCandidate = new Line(nextPlayer().color(), line.p1(), line.p2());
        Move moveCandidate = new Move(nextPlayer(), line);
        gameBoard.addLine(lineCandidate);
        moves.add(moveCandidate);
    }

    public Board board() {
        return gameBoard;
    }

    public boolean hasEnded() {
        return gameBoard.isBoardFull();
    }

    public void updateScore() {
        for (int i = 0; i < gameBoard.width() - 1; i++) {
            for (int j = 0; j < gameBoard.height() - 1; j++) {
                Point currentPoint = new Point(i, j);
                if (gameBoard.isBoxCompleted(currentPoint) && !completedBoxes.contains(currentPoint)) {
                    currentPlayer().increaseScoreByOne();
                    completedBoxes.add(new Point(i, j));
                }
            }
        }
    }
}
