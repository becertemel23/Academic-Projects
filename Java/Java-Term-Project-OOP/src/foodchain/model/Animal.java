package foodchain.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public abstract class Animal implements Serializable{
	protected String name;
	protected int x;
	protected int y;
	protected int score;
	protected int abilityCoolDown;
	public enum Type {PREY,PREDATOR,APEX_PREDATOR};
	protected Type type;
	public enum Era{PAST,PRESENT,FUTURE};
	protected Era era;
	
	/**
	 * The constructor initializes the fundamental state of the entity. 
	 * @param name
	 * @param x
	 * @param y
	 * @param type
	 * @param era
	 */
	public Animal(String name, int x, int y, Type type, Era era) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.score = 0;
		this.abilityCoolDown = 0;
		this.type = type;
		this.era = era;
	}
	
	/**
	 * A helper method implementing a Greedy Pathfinding Logic
	 * @param targetX
	 * @param targetY
	 * @param model
	 */
    protected void moveTowards(int targetX, int targetY, Simulation model) {
        
        int currentDistance = Math.abs(this.x - targetX) + Math.abs(this.y - targetY);
        int[][] directions = { {1,0}, {-1,0}, {0,1}, {0,-1} };
        
        int bestX = -1;
        int bestY = -1;
        int minDistance = Integer.MAX_VALUE;
        
        for (int[] dir : directions) {
            int nextX = this.x + dir[0];
            int nextY = this.y + dir[1];
            
            if (nextX >= 0 && nextX < model.getBoardSize() && nextY >= 0 && nextY < model.getBoardSize()) {
                if (!model.hasObstacle(nextX, nextY)) {
                    int distance = Math.abs(nextX - targetX) + Math.abs(nextY - targetY);
                    if (distance < minDistance) {
                        minDistance = distance;
                        bestX = nextX;
                        bestY = nextY;
                    }
                }
            }
        }
        if (bestX != -1 && minDistance <= currentDistance) {
            setPosition(bestX, bestY);
        } 
        else {
             randomMove(model);
        }
    }
    /**
     * A fallback movement mechanism used when no clear target exists or paths are blocked.
     * @param model
     */
    private void randomMove(Simulation model) {
        int[][] directions = { {1,0}, {-1,0}, {0,1}, {0,-1} };
        
        java.security.SecureRandom secureRandom = new java.security.SecureRandom();
        java.util.Collections.shuffle(java.util.Arrays.asList(directions), secureRandom);
        
        for (int[] dir : directions) {
            int tryX = this.x + dir[0];
            int tryY = this.y + dir[1];

            if (tryX >= 0 && tryX < model.getBoardSize() && tryY >= 0 && tryY < model.getBoardSize()) {
                if (!model.hasObstacle(tryX, tryY)) {
                    setPosition(tryX, tryY);
                    return; 
                }
            }
        }
    }
    
	//Helper Methods
	public void addScore(int points) {
		this.score += points;
	}
	public void loseScore(int amount) {
		this.score -= amount;
	}
	
	
	public void reduceCooldown() {
		if (this.abilityCoolDown > 0) {
			this.abilityCoolDown--;
		}
	}
	
	//Abstracts methods
	public abstract void move(Animal target, Food food, Simulation model);

	public abstract ArrayList<int[]> getMoves(int boardSize, Simulation model);

    public abstract Animal copy();
    
    //Getters
    public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public String getName() {
		return this.name;
	}
	public Type getType() {
		return this.type;
	}
	public Era getEra() {
		return this.era;
	}
	public int getAbilityCoolDown() {
		return this.abilityCoolDown;
	}
	public int getScore() {
		return this.score;
	}
	public int getCooldown() {
		return this.abilityCoolDown;
	}
	
	//Setters
	public void setCoolDown(int coolDown){
		this.abilityCoolDown = coolDown;
	}
	
	public void setPosition(int newX, int newY) {
		this.x = newX;
		this.y = newY;
	}
	public void setScore(int score) {
        this.score = score;
    }
}
