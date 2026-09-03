package foodchain.model;

import java.util.ArrayList;


public class Predator extends Animal {
	/**
	 * Constructor
	 * @param name
	 * @param x
	 * @param y
	 * @param era
	 */
    public Predator(String name, int x, int y, Era era) {
        super(name, x, y, Type.PREDATOR, era);
        this.score = 0;
    }
    /**
     * Move Calculation Engine
     */
    public ArrayList<int[]> getMoves(int boardSize, Simulation model) {
        ArrayList<int[]> moves = new ArrayList<>();
       
        addMoves(moves, model, 1, 0, false); 
        
        if (this.era == Era.PAST && this.abilityCoolDown == 0) {
            addMoves(moves, model, 2, 1, true); 
        }
        else if (this.era == Era.FUTURE && this.abilityCoolDown == 0) {
            addMoves(moves, model, 2, 1, false);
        }
        else if (this.era == Era.PRESENT) {
            if (model.isApexClose(this)) {
                addTwoMoves(moves, model); 
            }
        }
        
        return moves;
    }
    /**
     * Complex Pathfinding Helper 
     * @param moves
     * @param model
     */
    private void addTwoMoves(ArrayList<int[]> moves, Simulation model) {
        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};

        for (int[] d1 : directions) {
            int x1 = this.x + d1[0];
            int y1 = this.y + d1[1];

            if (isCorrectMove(x1, y1, model)) {
                
                for (int[] d2 : directions) {
                    int x2 = x1 + d2[0];
                    int y2 = y1 + d2[1];
                    
                    if (x2 == this.x && y2 == this.y) {
                    	continue;
                    }

                    if (isCorrectMove(x2, y2, model)) {
                        if (!containsMove(moves, x2, y2)) {
                            moves.add(new int[]{x2, y2, 1});
                        }
                    }
                }
            }
        }
    }
    /**
     * Generic Movement Generator
     * @param moves
     * @param model
     * @param range
     * @param isSpecial
     * @param straightLineOnly
     */
    private void addMoves(ArrayList<int[]> moves, Simulation model, int range, int isSpecial, boolean straightLineOnly) {
        
        int[][] directions;
        
        if (straightLineOnly) {
            directions = new int[][]{{0,1}, {0,-1}, {1,0}, {-1,0}};
        } 
        else {
            directions = new int[][]{{0,1}, {0,-1}, {1,0}, {-1,0}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
        }
        
        for (int[] dirrection : directions) {
            int targetX = this.x + (dirrection[0] * range);
            int targetY = this.y + (dirrection[1] * range);
            
            if (isCorrectMove(targetX, targetY, model)) {
                
                boolean pathBlocked = false;
                for (int k = 1; k < range; k++) {
                    int middleX = this.x + (dirrection[0] * k);
                    int middleY = this.y + (dirrection[1] * k);
                    
                    if (model.hasObstacle(middleX, middleY)) {
                        pathBlocked = true;
                        break;
                    }
                }
                
                if (!pathBlocked) {
                    if (!containsMove(moves, targetX, targetY)) {
                        moves.add(new int[]{targetX, targetY, isSpecial});
                    }
                }
            }
        }
    }
    
    private boolean containsMove(ArrayList<int[]> moves, int x, int y) {
        for (int[] m : moves) {
            if (m[0] == x && m[1] == y) return true;
        }
        return false;
    }
    /**
     * Validation Layer. 
     * @param x
     * @param y
     * @param model
     * @return
     */
    private boolean isCorrectMove(int x, int y, Simulation model) {
        if (x < 0 || x >= model.getBoardSize() || y < 0 || y >= model.getBoardSize()) return false;
        if (model.hasObstacle(x, y)) return false;
        
        Animal other = model.getAnimalAt(x, y);
        if (other != null) {
            if (other == this) {
            	return false;
            }
            if (other.getType() == Type.APEX_PREDATOR) {
            	return false;
            }
            if (other.getType() == Type.PREDATOR) {
            	return false;
            }
        }
        return true;
    }
    /**
     * Prototype Pattern Implementation
     */
    @Override
    public Animal copy() {
        Predator predator = new Predator(this.name, this.x, this.y, this.era);
        predator.score = this.score;
        predator.abilityCoolDown = this.abilityCoolDown;
        return predator;
    }
    
    @Override
    public void move(Animal target, Food food, Simulation model) {
    }
}
