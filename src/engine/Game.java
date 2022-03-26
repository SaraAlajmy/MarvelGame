package engine;

import java.awt.Point;
import java.io.*;
import java.util.*;
import model.abilities.*;
import model.effects.*;
import model.world.*;

public class Game {

	private Player firstPlayer;
	private Player secondPlayer;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private Object[][] board;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private PriorityQueue turnOrder;
	final private static int BOARDHEIGHT = 5;
	final private static int BOARDWIDTH = 5;

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public Object[][] getBoard() {
		return board;
	}

	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}

	public Game(Player first, Player second) {
		this.firstPlayer = first;
		this.secondPlayer = second;
		availableChampions = new ArrayList<Champion>(3);
		availableAbilities = new ArrayList<Ability>(3);
		turnOrder = new PriorityQueue(6); // not sure of size
		board = new Object[BOARDHEIGHT][BOARDWIDTH];
		placeChampions();
		placeCovers();
	}

	private void placeCovers() {
		int cnt = 0;
		while (cnt < 5) {

			int row = (int) (Math.random() * 3) + 1;
			int col = (int) (Math.random() * 4);
			if (board[row][col] == null) {
				cnt++;
				board[row][col] = new Cover(row, col);
				
			}

		}

	}

	private void placeChampions() {
		ArrayList<Champion> teamFirst = firstPlayer.getTeam();
		ArrayList<Champion> teamSecond = secondPlayer.getTeam();
		for (int i = 1; i < 4; i++) {
			if(teamFirst.size()>=i) {
				board[4][i] =teamSecond.get(i - 1) ;
				teamSecond.get(i-1).setLocation(new Point(4,i));
			}
			if(teamSecond.size()>=i) {
			board[0][i] = teamFirst.get(i - 1);
			
			teamFirst.get(i-1).setLocation(new Point(0,i));
			}
		}

	}

	public static void loadAbilities(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String thisLine = br.readLine();
		while (thisLine != null) {
			String[] attrib = thisLine.split(",");
			String name = attrib[1];
			int manaCost = Integer.parseInt(attrib[2]);
			int castRange = Integer.parseInt(attrib[3]);
			int baseCoolDown = Integer.parseInt(attrib[4]);
			AreaOfEffect area = AreaOfEffect.valueOf(attrib[5]);
			int req = Integer.parseInt(attrib[6]);
			String abilityType = attrib[0];

			if (abilityType.equals("CC")) {

				String effectName = attrib[7];
				int duration = Integer.parseInt(attrib[8]);
				Effect effect = new Effect();
				if (effectName.equals("Shield"))
					effect = new Shield( duration);
				else if (effectName.equals("Root"))
					effect = new Root( duration);
				else if (effectName.equals("PowerUp"))
					effect = new PowerUp(duration);
				else if (effectName.equals("Silence"))
					effect = new Silence( duration);
				else if (effectName.equals("Stun"))
					effect = new Stun( duration);
				else if (effectName.equals("SpeedUp"))
					effect = new SpeedUp( duration);
				else if (effectName.equals("Dodge"))
					effect = new Dodge( duration);
				else if (effectName.equals("Disarm"))
					effect = new Disarm(duration);
				else if (effectName.equals("Embrace"))
					effect = new Embrace( duration);
				else if (effectName.equals("Shock"))
					effect = new Shock(duration);

				Ability cc = new CrowdControlAbility(name, manaCost, baseCoolDown, castRange, area, req, effect);
				availableAbilities.add(cc);
			} else if (abilityType.equals("DMG")) {
				int dAmount = Integer.parseInt(attrib[7]);
				Ability dmg = new DamagingAbility(name, manaCost, baseCoolDown, castRange, area, req, dAmount);
				availableAbilities.add(dmg);
			} else if (abilityType.equals("HEL")) {
				int hAmount = Integer.parseInt(attrib[7]);
				Ability hel = new HealingAbility(name, manaCost, baseCoolDown, castRange, area, req, hAmount);
				availableAbilities.add(hel);

			}

			thisLine = br.readLine();
		}
	}

	public static void loadChampions(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String thisLine = br.readLine();
		while (thisLine != null && thisLine.length()!=0) {
			String[] attrib = thisLine.split(",");
			String type = attrib[0];
			String name = attrib[1];
			int maxHP = Integer.parseInt(attrib[2]);
			int mana = Integer.parseInt(attrib[3]);
			int actions = Integer.parseInt(attrib[4]);
			int speed = Integer.parseInt(attrib[5]);
			int attackRange = Integer.parseInt(attrib[6]);
			int attackDamage = Integer.parseInt(attrib[7]);
			String ability1 = attrib[8];
			String ability2 = attrib[9];
			String ability3 = attrib[10];
			Champion champ = new Champion();
			if (type.equals("H"))
				champ = new Hero(name, maxHP, mana, actions, speed, attackRange, attackDamage);

			else if (type.equals("A"))
				champ = new AntiHero(name, maxHP, mana, actions, speed, attackRange, attackDamage);
			else if (type.equals("V"))
				champ = new Villain(name, maxHP, mana, actions, speed, attackRange, attackDamage);
			availableChampions.add(champ);
			 ArrayList<Ability> abilities = champ.getAbilities();
			 abilities.add(getMatchingAbility(ability1));
			 abilities.add(getMatchingAbility(ability2));
			 abilities.add(getMatchingAbility(ability3));
			thisLine = br.readLine();
		}
	}
	public static Ability getMatchingAbility(String abilityX) {
		for(int i=0;i<availableAbilities.size();i++) {
			if(availableAbilities.get(i).getName().equals(abilityX))
				return availableAbilities.get(i);
		}
		return null;
	}

}
