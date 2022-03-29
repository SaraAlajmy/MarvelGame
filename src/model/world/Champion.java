package model.world;
import java.awt.Point;
import java.util.*;

import model.abilities.*;
import model.effects.*;




public class Champion {
	
	private String name;
	private int maxHP;
	private int currentHP;
	private int mana;
	private int maxActionPointsPerTurn;
	private int currentActionPoints;
	private int attackRange;
	private int attackDamage;
	private int speed;
	private ArrayList<Ability> abilities;
	private ArrayList<Effect> appliedEffects;
	private Condition condition;
	private Point location;
	public String getName() {
		return name;
	}
	public void setMana(int mn) {
		mana = mn;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public int getCurrentHP() {
		return currentHP;
	}
	public int getMana() {
		return mana;
	}
	public void setCurrentActionPoints(int pts) {
		currentActionPoints=pts;
	}
	public int getMaxActionPointsPerTurn() {
		return maxActionPointsPerTurn;
	}
	public int getCurrentActionPoints() {
		return currentActionPoints;
	}
	public int getAttackRange() {
		return attackRange;
	}
	public int getAttackDamage() {
		return attackDamage;
	}
	public int getSpeed() {
		return speed;
	}
	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
	public ArrayList<Effect> getAppliedEffects() {
		return appliedEffects;
	}
	public Condition getCondition() {
		return condition;
	}
	public Point getLocation() {
		return location;
	}
	public void setCurrentHP(int currentHP) {
		this.currentHP = Math.max(0, currentHP);
		this.currentHP = Math.min(maxHP, this.currentHP);
	}
	public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
		this.maxActionPointsPerTurn = maxActionPointsPerTurn;
	}
	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
		currentHP = Math.max(0, currentHP- attackDamage);
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public void setLocation(Point location) {
		
		this.location = location;
		
	}
	public Champion() {
		
	}
	public Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange,
			int attackDamage)
	{
		this.name = name;
		this.maxHP = maxHP;
		this.mana = mana;
		this.maxActionPointsPerTurn = maxActions;
		currentActionPoints = maxActionPointsPerTurn;
		this.speed = speed;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		abilities = new ArrayList<>();
		appliedEffects = new ArrayList<>();
		currentHP = maxHP;
		condition = Condition.ACTIVE;
	}

}
