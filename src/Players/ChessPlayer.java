package Players;

import Board.Move;
import Game.Game;
import Pieces.Piece;
import Pieces.Piece.Color;

public abstract class ChessPlayer {
    protected Color playerColor;
    protected Game game;
    
    abstract public Move move();
    
    public final void setPlayerColor(Piece.Color playerColor) {
        this.playerColor = playerColor;
    }
    
    public final void setGame(Game game) {
        this.game = game;
    }
    
    public Color getPlayerColor() {
        return playerColor;
    }

    @Override
    public String toString() {
        return "Unidentified Chess Player playing " + playerColor;
    }
}
