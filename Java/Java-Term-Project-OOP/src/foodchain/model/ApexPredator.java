package foodchain.model;

import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom; // SecureRandom eklendi

public class ApexPredator extends Animal {
	/**
	 * Calls the super constructor 
	 * @param name
	 * @param x
	 * @param y
	 * @param era
	 */
    public ApexPredator(String name, int x, int y, Era era) {
        super(name, x, y, Type.APEX_PREDATOR, era);
    }
    /**
     * This is the entry point for the AI's turn.
     */
    @Override
    public void move(Animal target, Food food, Simulation model) {
        
        Animal priorityTarget = determineBestTarget(model);
        
        if (priorityTarget == null) {
            moveRandomly(model);
            manualCooldownTick(false);
            return;
        }
        
        bestMove(priorityTarget, model);
    }
    /**
     * Greedy Pathfinding Algorithm
     * @param target
     * @param model
     */
    private void bestMove(Animal target, Simulation model) {

        List<int[]> candidates = getWalkingMoves(model);
        
        if (this.abilityCoolDown == 0) {
            if (this.era == Era.PAST) {
                candidates.addAll(getStraightMoves(model, 2));
            }
            else if (this.era == Era.PRESENT) {
                candidates.addAll(getStraightMoves(model, 2));
                candidates.addAll(getStraightMoves(model, 3));
            }
            else if (this.era == Era.FUTURE) {
                candidates.addAll(getRadiusMoves(model, 3));
            }
        }

        int[] bestMove = null;
        double minDistance = Double.MAX_VALUE;

        for (int[] p : candidates) {
            double dist = getDistance(p[0], p[1], target.getX(), target.getY());
            
            if (dist < minDistance) {
                minDistance = dist;
                bestMove = p;
            }
        }

        boolean abilityUsed = false;

        if (bestMove != null) {
            int distMoved = Math.max(Math.abs(bestMove[0] - this.x), Math.abs(bestMove[1] - this.y));
            
            if (distMoved > 1) {
                abilityUsed = true;
                
                if (this.era == Era.PAST) {
                    this.setCoolDown(2); 
                } 
                else if (this.era == Era.PRESENT) {
                    this.setCoolDown(3); 
                } 
                else if (this.era == Era.FUTURE) {
                    this.setCoolDown(3); 
                }
            }
            
            setPosition(bestMove[0], bestMove[1]);
        }
        
        manualCooldownTick(abilityUsed);
    }
    /**
     * This helper method manages the recharge cycle of special abilities.
     * @param abilityUsedJustNow
     */
    private void manualCooldownTick(boolean abilityUsedJustNow) {
        if (!abilityUsedJustNow && this.abilityCoolDown > 0) {
            this.abilityCoolDown--;
        }
    }
    
    /**
     * Vector-Based Movement Engine.
     * @param model
     * @param dist
     * @param straightLineOnly
     * @return
     */
    private List<int[]> getMovesByOffset(Simulation model, int dist, boolean straightLineOnly) {
        List<int[]> moves = new ArrayList<>();
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
        
        for (int[] d : dirs) {
            if (straightLineOnly && (d[0] != 0 && d[1] != 0)) {
            	continue;
            }

            int nx = this.x + (d[0] * dist);
            int ny = this.y + (d[1] * dist);
            
            if (isValidMove(nx, ny, model)) {
                
                boolean pathBlocked = false;
                for (int k = 1; k < dist; k++) {
                    int midX = this.x + (d[0] * k);
                    int midY = this.y + (d[1] * k);
                    
                    if (model.hasObstacle(midX, midY)) {
                        pathBlocked = true;
                        break;
                    }
                }
                
                if (!pathBlocked) {
                    moves.add(new int[]{nx, ny});
                }
            }
        }
        return moves;
    }
    /**
     * Era. It iterates through a square grid defined by the radius (dx, dy from -radius to +radius)
     * @param model
     * @param radius
     * @return
     */
    private List<int[]> getRadiusMoves(Simulation model, int radius) {
        List<int[]> moves = new ArrayList<>();
        
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (dx == 0 && dy == 0) continue;
                
                int nx = this.x + dx;
                int ny = this.y + dy;
                
                if (isValidMove(nx, ny, model)) {
                    moves.add(new int[]{nx, ny});
                }
            }
        }
        return moves;
    }

    /**
     * Priority System Logic
     * @param model
     * @return
     */
    private Animal determineBestTarget(Simulation model) {
        Animal bestTarget = null;
        int minDistance = Integer.MAX_VALUE;

        Animal player = model.getPlayer();
        if (player != null) {
            int d = getDistance(this.x, this.y, player.getX(), player.getY());
            if (d < minDistance) {
                minDistance = d;
                bestTarget = player;
            }
        }

        for (Animal a : model.getAnimals()) {
            if (a.getType() == Type.PREY) {
                int d = getDistance(this.x, this.y, a.getX(), a.getY());
                if (d < minDistance) {
                    minDistance = d;
                    bestTarget = a;
                }
            }
        }
        
        return bestTarget;
    }
    /**
     * It selects a random valid neighbor to keep the entity dynamic.
     * @param model
     */
    private void moveRandomly(Simulation model) {
        List<int[]> moves = getWalkingMoves(model);
        
        if (!moves.isEmpty()) {
            SecureRandom secureRandom = new SecureRandom();
            int[] p = moves.get(secureRandom.nextInt(moves.size()));
            
            setPosition(p[0], p[1]);
        }
    }
    /**
     * Checks if x and y are within [0, boardSize).
     * @param x
     * @param y
     * @param model
     * @return
     */
    private boolean isValidMove(int x, int y, Simulation model) {
        if (x < 0 || x >= model.getBoardSize() || y < 0 || y >= model.getBoardSize()) {
        	return false;
        }
        if (model.hasObstacle(x, y)) {
        	return false;
        }
        
        Animal other = model.getAnimalAt(x, y);
        if (other != null) {
            if (other.getType() == Type.APEX_PREDATOR) return false;
        }
        return true;
    }
    //Getters
    private int getDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    
    private List<int[]> getWalkingMoves(Simulation model) {
        return getMovesByOffset(model, 1, false); 
    }
    
    private List<int[]> getStraightMoves(Simulation model, int distance) {
        return getMovesByOffset(model, distance, true); 
    }
    /**
     * Creates a deep copy of the Apex Predator instance
     */
    @Override
    public Animal copy() {
        ApexPredator copy = new ApexPredator(this.name, this.x, this.y, this.era);
        copy.score = this.score;
        copy.abilityCoolDown = this.abilityCoolDown;
        return copy;
    }

	@Override
	public ArrayList<int[]> getMoves(int boardSize, Simulation model) {
		return null;
	}
	
	
}
