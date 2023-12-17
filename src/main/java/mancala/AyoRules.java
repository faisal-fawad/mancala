package mancala;

public class AyoRules extends GameRules {
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
        int curStones = this.gameBoard.removeStones(startPit);
        int numStones = curStones;
        Countable curCountable = null;
        this.gameBoard.setIterator(startPit, getPlayer(), true /* for AyoRules */); 

        // Distributing stones
        while (curStones > 0) {
            curCountable = this.gameBoard.next();
            curCountable.addStone();
            curStones -= 1;

            // Keep looping if last stone lands in a pit with stones, this is the case for AyoRules
            if (curStones == 0 && curCountable.getStoneCount() != 1 && this.gameBoard.getIterator() != 6 && this.gameBoard.getIterator() != 13) {
                curStones += curCountable.removeStones();
            } 
        }
        // Handling bonus turn, which is always false for AyoRules
        setBonus(false);

        // Handling stone capturing
        if (curCountable != null && curCountable.getStoneCount() == 1) {
            final int finalPos = this.gameBoard.getIterator(); // Number from 0-13: 6 & 13 are stores
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
        final int newStones = this.gameBoard.removeStones(opposingPoint);

        if (newStones != 0) {
            // Don't remove the one stone on players side for AyoRules
            this.gameBoard.addToStore(getPlayer(), newStones);
        }
        
        return newStones;
    }
}