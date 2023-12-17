package mancala;

public class KalahRules extends GameRules {
    private static final long serialVersionUID = 1L;
    private final MancalaDataStructure gameBoard = getDataStructure();
    
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        if (!isValidMove(startPit, playerNum)) {
            throw new InvalidMoveException();
        }

        final int before = this.gameBoard.getStoreCount(playerNum);
        setPlayer(playerNum); // Set current player
        distributeStones(startPit);

        return this.gameBoard.getStoreCount(playerNum) - before;
    }

    @Override
    int distributeStones(final int startPit) {
        int numStones = this.gameBoard.removeStones(startPit);
        int finalPos;
        Countable curCountable = null;
        this.gameBoard.setIterator(startPit, getPlayer(), false /* for KalahRules */); 

        // Distributing stones
        for (int i = 0; i < numStones; i++) {
            curCountable = this.gameBoard.next();
            curCountable.addStone(); 
        }
        finalPos = this.gameBoard.getIterator(); // Number from 0-13: 6 & 13 are stores

        // Handling bonus turn
        setBonus(false);
        if (finalPos == 6 || finalPos == 13) {
            setBonus(true);
        }

        // Handling stone capturing
        if (curCountable.getStoneCount() == 1) {
            if (finalPos >= 0 && finalPos <= 5 && getPlayer() == 1 /* P1 */) {
                numStones += captureStones(finalPos + 1);
            } else if (finalPos >= 7 && finalPos <= 12 && getPlayer() == 2 /* P2 */) {
                numStones += captureStones(finalPos);
            }
        }

        return numStones;
    }

    @Override
    int captureStones(final int stoppingPoint) {
        final int opposingPoint = Math.abs(stoppingPoint - 13) % 13;
        int newStones = this.gameBoard.removeStones(opposingPoint);

        if (newStones != 0) {
            newStones += this.gameBoard.removeStones(stoppingPoint);
            this.gameBoard.addToStore(getPlayer(), newStones);
        }
        
        return newStones;
    }
}