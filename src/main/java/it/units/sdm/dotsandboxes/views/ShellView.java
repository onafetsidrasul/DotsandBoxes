package it.units.sdm.dotsandboxes.views;

import it.units.sdm.dotsandboxes.core.Board;
import it.units.sdm.dotsandboxes.core.Line;

public class ShellView {
    public void printBoard(Board board, int[] dimensions) {
        int width = dimensions[0];
        int height = dimensions[1];

        printColumnNumber(width);

        System.out.println("  ┏" + "━".repeat(width * 4 - 1) + "┓");
        for (int i = 0; i < height; i++) {
            boardInside(board, i, width, height);
        }
        System.out.println("  ┗" + "━".repeat(width * 4 - 1) + "┛");
    }

    private void boardInside(Board board, int i, int width, int height) {
        StringBuilder sb;
        sb = new StringBuilder(i + " ┃");
        for (int j = 0; j < width; j++) {
            sb.append(" ● ");
            if (j < width - 1) { rowsWithHorizontalLines(board, j, i, sb);}
        }
        System.out.println(sb + "┃");
        if (i < height - 1) {
            sb = new StringBuilder("  ┃");
            for (int j = 0; j < width; j++) { rowsWithVerticalLines(board, j, i, sb, width);}
            System.out.println(sb + "┃");
        }
    }

    private void rowsWithVerticalLines(Board board, int j, int i, StringBuilder sb, int width) {
        Line searched;
        searched = new Line(j, i, j, i + 1);
        if (board.getLines().containsKey(searched.hashCode())) {
            sb.append(board.getLines().get(
                    searched.hashCode()).color().getFormat().format(" ‖ "));
        } else {
            sb.append("   ");
        }
        if (j < width - 1) {
            sb.append(" ");
        }
    }

    private void rowsWithHorizontalLines(Board board, int j, int i, StringBuilder sb) {
        Line searched;
        searched = new Line(j, i, j + 1, i);
        if (board.getLines().containsKey(searched.hashCode())) {
            sb.append(board.getLines().get(
                    searched.hashCode()).color().getFormat().format("="));
        } else {
            sb.append(" ");
        }
    }

    private void printColumnNumber(int width) {
        StringBuilder sb;
        sb = new StringBuilder("   ");
        for (int j = 0; j < width; j++) {
            sb.append(" ").append(j).append("  ");
        }
        System.out.println(sb);
    }
}