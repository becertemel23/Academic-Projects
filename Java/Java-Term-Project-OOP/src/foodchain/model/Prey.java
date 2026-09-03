package foodchain.model;

import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;


public class Prey extends Animal {

    private boolean eatingDisabled = false;
    /**
     * Constructor
     * @param name
     * @param x
     * @param y
     * @param era
     */
    public Prey(String name, int x, int y, Era era) {
        super(name, x, y, Type.PREY, era);
    }
    
    /**
     * Heuristic Evaluation Engine
     * @param threat
     * @param food
     * @param model
     */
    private void bestMove(Animal threat, Food food, Simulation model) {
        int currentDistanceThreat = getDistance(this.x, this.y, threat.getX(), threat.getY());
        boolean safe = currentDistanceThreat > 4; 

        List<int[]> candidates = getValidNeighbors(model);
        
        if (this.abilityCoolDown == 0) {
            if (this.era == Era.PAST) {
                candidates.addAll(getLMoves(model));
            }
            else if (this.era == Era.PRESENT) {
                candidates.addAll(getHopMoves2(model));
            }
            else if (this.era == Era.FUTURE) {
                candidates.addAll(getHopMoves3(model));
            }
        }

        int[] bestMoves = null;
        double maxScore = -Double.MAX_VALUE;

        for (int[] p : candidates) {
            double score = calculateScore(p, threat, food, safe);
            
            if (score > maxScore) {
                maxScore = score;
                bestMoves = p;
            }
        }

        boolean ability = false;

        if (bestMoves != null) {
            int moveDistance = Math.max(Math.abs(bestMoves[0] - this.x), Math.abs(bestMoves[1] - this.y));
            
            if (moveDistance >= 2) {
                ability = true;
                
                if (this.era == Era.PRESENT) {
                    this.setCoolDown(3); 
                } 
                else if (this.era == Era.PAST) {
                    this.setCoolDown(2); 
                }
                else if (this.era == Era.FUTURE) {
                    this.setCoolDown(2); 
                    this.eatingDisabled = true; 
                }
            }
            
            setPosition(bestMoves[0], bestMoves[1]);
        }
        
        manualCooldownTick(ability);
    }
    
    /**
     * Instead of writing separate logic for 2-tile and 3-tile jumps, this method accepts a distance parameter to calculate coordinates in 8 directions.
     * @param model
     * @param dist
     * @return
     */
    private List<int[]> getMovesOffset(Simulation model, int dist) {
        List<int[]> moves = new ArrayList<>();
        int[][] jumps = {
            {0, dist}, {0, -dist}, {dist, 0}, {-dist, 0}, 
            {dist, dist}, {dist, -dist}, {-dist, dist}, {-dist, -dist} 
        };
        
        for (int[] j : jumps) {
            int nx = this.x + j[0];
            int ny = this.y + j[1];
            
            if (isValidMove(nx, ny, model)) {
                
                boolean pathBlocked = false;
                int dx = Integer.signum(j[0]);
                int dy = Integer.signum(j[1]);
                
                for (int k = 1; k < dist; k++) {
                    int midX = this.x + (dx * k);
                    int midY = this.y + (dy * k);
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
     * Generates moves in an L pattern
     * @param model
     * @return
     */
    private List<int[]> getLMoves(Simulation model) {
        List<int[]> moves = new ArrayList<>();
        int[][] jumps = {
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}, 
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1}
        };
        for (int[] j : jumps) {
            int x = this.x + j[0];
            int y = this.y + j[1];
            if (isValidMove(x, y, model)) {
                moves.add(new int[]{x, y});
            }
        }
        return moves;
    }
    /**
     * It assigns a numerical value to a potential move based on weighted factors.
     * @param p
     * @param threat
     * @param food
     * @param isSafe
     * @param isFutureHop
     * @return
     */
    private double calculateScore(int[] p, Animal threat, Food food, boolean isSafe) {
        int distToThreat = getDistance(p[0], p[1], threat.getX(), threat.getY());
        int distToFood = (food != null) ? getDistance(p[0], p[1], food.getX(), food.getY()) : 0;
        
        if (!isSafe) {
            return (distToThreat * 10.0) - (distToFood * 1.0);
        } 
        else {
            return (distToThreat * 1.0) - (distToFood * 5.0);
        }
    }
    /**
     * Iterates through the entire animals list to find the closest entity of type PREDATOR or APEX_PREDATOR using Manhattan distance calculations.
     * @param model
     * @return
     */
    private Animal getNearestThreat(Simulation model) {
        Animal nearest = null;
        int minimumDistance = Integer.MAX_VALUE;
        for (Animal a : model.getAnimals()) {
            if (a.getType() == Type.PREDATOR || a.getType() == Type.APEX_PREDATOR) {
                int d = getDistance(this.x, this.y, a.getX(), a.getY());
                if (d < minimumDistance) {
                    minimumDistance = d;
                    nearest = a;
                }
            }
        }
        return nearest;
    }

    /**
     * This private method allows the Prey to navigate towards a specific destination.
     * @param targetX
     * @param targetY
     * @param model
     */
    private void moveTo(int targetX, int targetY, Simulation model) {
        int bestX = this.x;
        int bestY = this.y;
        int minimumDistance = Integer.MAX_VALUE;
        for (int[] p : getValidNeighbors(model)) {
            int d = getDistance(p[0], p[1], targetX, targetY);
            if (d < minimumDistance) {
                minimumDistance = d;
                bestX = p[0];
                bestY = p[1];
            }
        }
        setPosition(bestX, bestY);
    }
    /**
     * A fallback mechanism used when the Prey detects no threats and no food sources.
     * @param model
     */
    private void moveRandom(Simulation model) {
        List<int[]> moves = getValidNeighbors(model);
        if (!moves.isEmpty()) {
            SecureRandom secureRandom = new SecureRandom();
            int[] p = moves.get(secureRandom.nextInt(moves.size()));
            setPosition(p[0], p[1]);
        }
    }
    /**
     * This private helper acts as the final gatekeeper for all movement calculations.
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
        if (other != null && other != this) {
        	return false;
        }
        return true;
    }

    @Override
    public ArrayList<int[]> getMoves(int boardSize, Simulation model) {
        return null; 
    }
    /**
     * Creates a deep copy of the Prey instance
     */
    @Override
    public Animal copy() {
        Prey newPrey = new Prey(this.name, this.x, this.y, this.era);
        newPrey.score = this.score;
        newPrey.abilityCoolDown = this.abilityCoolDown;
        return newPrey;
    }
    /**
     * This overridden method acts as the central intelligence node for the entity, determining its high-level strategy for the current turn.
     */
    @Override
    public void move(Animal target, Food food, Simulation model) {
        Animal nearestPredator = getNearestThreat(model);
        
        if (nearestPredator == null) {
            if (food != null) moveTo(food.getX(), food.getY(), model);
            else moveRandom(model);
            
            manualCooldownTick(false);
            return;
        }

        bestMove(nearestPredator, food, model);
    }
    
    //Helper Methods
    public void resetEatingStatus() {
        this.eatingDisabled = false;
    }
    
    public boolean canEat() {
        return !eatingDisabled; 
    }
    
    private void manualCooldownTick(boolean abilityUsedJustNow) {
        if (!abilityUsedJustNow && this.abilityCoolDown > 0) {
            this.abilityCoolDown--;
        }
    }
    
    //Getters
    private List<int[]> getHopMoves2(Simulation model) {
        return getMovesOffset(model, 2);
    }

    private List<int[]> getHopMoves3(Simulation model) {
        return getMovesOffset(model, 3);
    }
    
    private List<int[]> getValidNeighbors(Simulation model) {
        return getMovesOffset(model, 1);
    }
    
    private int getDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
