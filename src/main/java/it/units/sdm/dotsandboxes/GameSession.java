package it.units.sdm.dotsandboxes;

import it.units.sdm.dotsandboxes.controllers.IGameController;
import it.units.sdm.dotsandboxes.controllers.PostGameIntent;
import it.units.sdm.dotsandboxes.exceptions.InvalidInputException;
import it.units.sdm.dotsandboxes.exceptions.UserHasRequestedQuit;

import java.io.IOException;

/**
 * Class representing an entire game session, which can consist of multiple games.
 */
public class GameSession{

    private final IGameController controller;

    GameSession(IGameController controller) {
        this.controller = controller;
    }

    /**
     * Begins a game session.
     * @throws IOException
     */
    public void begin() throws IOException {
        PostGameIntent intent;
        do {
            if(!controller.initialize()){
                throw new IOException("Could not initialize game");
            }
            if(!controller.setUpGame()){
                throw new IOException("Could not set up game");
            }
            try {
                controller.startGame();
            } catch (InvalidInputException e) {
                throw new IOException("Game controller could not start game.", e);
            } catch (UserHasRequestedQuit e) {
                break;
            }
            try {
                intent = controller.getPostGameIntent();
            } catch (IOException e) {
                throw new IOException("Problem acquiring the post game intentions.", e);
            }
        } while (intent == PostGameIntent.NEW_GAME);
    }
}
