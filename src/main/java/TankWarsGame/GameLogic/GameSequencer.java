package TankWarsGame.GameLogic;

public enum GameSequencer {

    INIT,

    // only if multi player mode is active
    CHECK_IF_OPPONENT_IS_WAITING_FOR_CONNECTION,
    WAIT_UNTIL_OPPONENT_CONNECTS,

    // used for multi and single player mode
    OWN_TURN,
    SET_OPPONENT_TURN,
    WAIT_OPPONENT_TURN_IS_DONE,
    CHECK_IF_WON_AFTER_OWN_TURN,
    CHECK_IF_LOST_AFTER_OPPONENT_TURN,

    //  game finished
    GAME_OVER

}
