package foodchain.model;

import java.io.Serializable;

public class Obstacle implements Serializable {
    private int x;
    private int y;
    /**
     * Constructor
     * @param x
     * @param y
     */
    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //Getters
    public int getX() {
    	return x; 
    }
    public int getY() {
    	return y; 
    }
}
