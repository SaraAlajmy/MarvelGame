package model.world;

import java.awt.Point;

public class Cover {

	private int currentHP;
	private Point location;
	
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	public int getCurrentHP() {
		return currentHP;
	}
	public Point getLocation() {
		return location;
	}
	
	public Cover(int x, int y)
	{
		location = new Point(x,y);
		currentHP = (int)(Math.random()*900 + 100);
	} 
}
