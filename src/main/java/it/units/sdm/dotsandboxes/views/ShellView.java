package it.units.sdm.dotsandboxes.views;

import it.units.sdm.dotsandboxes.controllers.IGameController;
import it.units.sdm.dotsandboxes.core.*;
import it.units.sdm.dotsandboxes.core.Color;
import it.units.sdm.dotsandboxes.exceptions.InvalidInputException;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.Semaphore;

import static org.fusesource.jansi.Ansi.*;

public class ShellView extends TextView {

    private final PrintStream out;
    private final BufferedReader in;

    public ShellView(PrintStream out, InputStreamReader in) {
        this.out = out;
        this.in = new BufferedReader(in);
    }

    public ShellView() {
        this.out = System.out;
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public boolean init(final IGameController controllerReference) {
        boolean isInitialized = true;
        try {
            Objects.requireNonNull(controllerReference);
            this.controllerReference = controllerReference;
            AnsiConsole.systemInstall();
        } catch (Exception e) {
            isInitialized = false;
        } finally {
            this.isInitialized = isInitialized;
        }
        return this.isInitialized;
    }

    @Override
    public void run() {
        do {
            try {
                controllerReference.readyToRefreshUISem.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            eraseScreen();
            synchronized (gameStateReference.scoreBoard){
                printPlayers(gameStateReference.players, gameStateReference.scoreBoard, gameStateReference.playerColorLUT);
            }
            synchronized (gameStateReference.board.lines()){
                printBoard(gameStateReference.board);
            }
            synchronized (gameStateReference.board.lines()){
                printCurrentPlayer(gameStateReference.currentPlayer(), gameStateReference.playerColorLUT.get(gameStateReference.currentPlayer()));
            }
            controllerReference.input = promptForAction();
            controllerReference.inputHasBeenReceivedSem.release();
        } while (true);
    }


    @Override
    protected void printVerticalLineIfPresent(int j, int i, StringBuilder sb, Board gameBoard) {
        Line searched;
        try {
            searched = new Line(j, i, j, i + 1);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        Optional<ColoredLine> res = gameBoard.lines().stream().filter(coloredLine -> coloredLine.hasSameEndpointsAs(searched)).findFirst();
        if (res.isPresent()) {
            sb.append(res.get().color().format().format(" ‖ "));
        } else {
            sb.append("   ");
        }

        if (j < gameBoard.width() - 1) {
            sb.append(" ");
        }
    }

    @Override
    protected void printHorizontalLineIfPresent(int j, int i, StringBuilder sb, Board gameBoard) {
        Line searched;
        try {
            searched = new Line(j, i, j + 1, i);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        Optional<ColoredLine> res = gameBoard.lines().stream().filter(coloredLine -> coloredLine.hasSameEndpointsAs(searched)).findFirst();
        if (res.isPresent()) {
            sb.append(res.get().color().format().format("="));
        } else {
            sb.append(" ");
        }


    }

    protected void printPlayers(List<String> players, Map<String, Integer> scores, Map<String, Color> colors) {
        out.println("--- PLAYERS ---");
        for (int i = 1; i <= players.size(); i++) {
            String player = players.get(i - 1);
            out.println(ansi().a("Player " + i + " : ").fg(Ansi.Color.valueOf(colors.get(player).name())).a(player).reset());
            out.println("\tScore: " + scores.get(player));

        }
        out.println("---------------");
    }

    protected void printCurrentPlayer(String currentPlayer, Color currentPlayerColor) {
        out.println(ansi().a("Current player: ").fg(Ansi.Color.valueOf(currentPlayerColor.name())).a(currentPlayer).reset());
    }

    @Override
    public void displayMessage(String message) {
        out.println(ansi().fg(Ansi.Color.GREEN).a(message).reset());
    }

    @Override
    public void displayPrompt(String message) {
        out.print(ansi().fg(Ansi.Color.YELLOW).a(message).reset());
    }

    @Override
    public void displayWarning(String message) {
        out.println();
        out.print(ansi().fg(Ansi.Color.RED).a(message).a(". Press Enter to continue :").reset());
        try {
            in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void displayResults() {
        eraseScreen();
        out.println("Game results:");
        for (Map.Entry<String, Integer> entry : gameStateReference.scoreBoard.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).toList()) {
            displayResult(entry.getKey(), entry.getValue(), gameStateReference.playerColorLUT.get(entry.getKey()));
        }
    }

    protected void displayResult(String playerName, Integer score, Color color) {
        out.println(ansi().fg(Ansi.Color.valueOf(color.name())).a(playerName).reset().a(" : " + score));
    }


    @Override
    public void eraseScreen() {
        out.println(ansi().eraseScreen());
    }

}
