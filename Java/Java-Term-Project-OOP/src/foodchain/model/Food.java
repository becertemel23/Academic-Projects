package foodchain.model;

import java.io.Serializable;

public class Food implements Serializable{
	
	private String name;
	private int x;
	private int y;
	/**
	 * Constructor
	 * @param name
	 * @param x
	 * @param y
	 */
	public Food(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	//Getters
	public String getName() {
		return this.name;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	//Setters
	public void setPosition(int newX, int newY) {
		this.x = newX;
		this.y = newY;
	}
}
