package it.units.sdm.dotsandboxes.core;

public class Move {
    private final Player player;
    private final int uncoloredNormalizedLineHash;

    public Move(Player player, Line line) {
        this.player = player;
        uncoloredNormalizedLineHash = LineUtils.stripOfColor(LineUtils.normalize(line)).hashCode();
    }

    public Player player() {
        return player;
    }

    public int uncoloredNormalizedLineHash() {
        return uncoloredNormalizedLineHash;
    }
}
