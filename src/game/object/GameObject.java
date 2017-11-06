package game.object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

import game.Inventory;

import static game.Main.*;

public abstract class GameObject {
	public BufferedImage sprite = null;
	public String objName = "";
	public int x = 0;
	public int y = 0;
	public int maxHitpoints = 100;
	public int hitpoints = maxHitpoints;
	public double percentHitpoints = 1.0;
	public boolean selected = false;
	public boolean moveTo = false;
	public boolean moveToY = false;
	public boolean moveToX = false;
	public int xTo = 0;
	public int yTo = 0;
	public double percentLevelled = 0.0;
	public int level = 0;
	public int levelHitpointGrowth = 25;
	public int levelExperienceGrowth = 100;
	public int experience = 0;
	private int oldExperience = 0;
	public int maxExperience = 100;
	public Inventory inventory = new Inventory();
	public String[] resourceGive = new String[2];
	
	public GameObject(int xArg, int yArg, String nameArg, String spriteArg) {
		x = xArg;
		y = yArg;
		objName = nameArg;
		try {
			sprite = ImageIO.read(new File(spriteArg));
		} catch (IOException e) {
			System.out.println("Error reading sprite: " + spriteArg);
			e.printStackTrace();
		}
		grid[x][y] = objName;
	}
	
	protected String[] getSurroundings() {
		ArrayList<String> list = new ArrayList<String>();
		for (int xCheck = x - 1; xCheck < x + 2; xCheck++) {
			for (int yCheck = y - 1; yCheck < y + 2; yCheck++) {
				if (xCheck>cellsX-1 || yCheck>cellsY-1 || xCheck<0 || yCheck<0) {
				
				} else {
				list.add(grid[xCheck][yCheck]);
				}
			}
		}
		list.remove(objName);
		list.removeAll(Collections.singleton(null));
		return list.toArray(new String[0]);
	}
	
	protected int[] findNearest(String nameArg) {
		for (int area = 3; area < cellsX; area += 2) {
			for (int xCheck = x - (area / 2); xCheck < x + ((area / 2) + 1); xCheck++) {
				for (int yCheck = y - (area / 2); yCheck < y + ((area / 2) + 1); yCheck++) {
					if (xCheck>cellsX-1 || yCheck>cellsY-1 || xCheck<0 || yCheck<0) {
						
					} else {
						if (objectDictionary.get(grid[xCheck][yCheck]).getDisplayName().equals(nameArg)) {
							return new int[]{xCheck, yCheck};
						}
					}
				}
			}
		}
		return new int[]{1, 1};
	}
	
	protected int[] findNearestSpace(int xArg, int yArg) {
		for (int area = 3; area < cellsX; area += 2) {
			for (int xCheck = xArg - (area / 2); xCheck < xArg + ((area / 2) + 1); xCheck++) {
				for (int yCheck = yArg - (area / 2); yCheck < yArg + ((area / 2) + 1); yCheck++) {
					if (xCheck>cellsX-1 || yCheck>cellsY-1 || xCheck<0 || yCheck<0) {
						
					} else {
						if (grid[xCheck][yCheck] == null) {
							return new int[]{xCheck, yCheck};
						}
					}
				}
			}
		}
		return new int[]{1, 1};
	}
	
	protected int[] findNearestType(String typeArg) {
		for (int area = 3; area < cellsX; area += 2) {
			for (int xCheck = x - (area / 2); xCheck < x + ((area / 2) + 1); xCheck++) {
				for (int yCheck = y - (area / 2); yCheck < y + ((area / 2) + 1); yCheck++) {
					if (xCheck>cellsX-1 || yCheck>cellsY-1 || xCheck<0 || yCheck<0) {
						
					} else {
						if (grid[xCheck][yCheck] != null) {
							if (objectDictionary.get(grid[xCheck][yCheck]).getType().equals(typeArg)) {
								return new int[]{xCheck, yCheck};
							}
						}
					}
				}
			}
		}
		return new int[]{1, 1};
	}
	
	protected void destroySelf() {
		objectDictionary.remove(objName);
		grid[x][y] = null;
		x = -1;
		y = -1;
		game.SelectionManager.unselect(objName);
	}
	
	public void giveAction(int xArg, int yArg) {

	}

	public void update() {
		percentHitpoints = (double)hitpoints/maxHitpoints;
		if (!getType().equals("Building")) {
			percentLevelled = (double)experience/maxExperience;
		}
		if (percentLevelled > 1.0) {
			experience -= maxExperience;
			maxExperience += levelExperienceGrowth;
			maxHitpoints += levelHitpointGrowth;
			hitpoints += levelHitpointGrowth;
			percentLevelled = 0.0;
			level++;
		}
		if (hitpoints <= 0) {
			destroySelf();
		} else if (hitpoints > maxHitpoints) {
			hitpoints = maxHitpoints;
		}
	}
	
	public String getType() {
		return objectDictionary.get(objName).getClass().getSuperclass().getSimpleName();
	}
	
	public String getDisplayName() {
		return objectDictionary.get(objName).getClass().getSimpleName();
	}
	
	public void renderSelf(Graphics2D g, int xArg, int yArg) {
		if (selected) {
			g.setColor(game.Surface.selectedColor);
			g.fillRect(xArg*cellSize+1, yArg*cellSize+1, cellSize-1, cellSize-1);
			g.setColor(game.Surface.bgColor);
			g.fillRect(xArg*cellSize+3,yArg*cellSize+3,cellSize-5,cellSize-5);
		} else {
			g.fillRect(xArg*cellSize+1, yArg*cellSize+1, cellSize-1, cellSize-1);
		}
		g.drawImage(sprite.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT), xArg*cellSize, yArg*cellSize, null);
		if (percentHitpoints < 1.0) {
			g.setColor(game.Surface.red);
			g.fillRect(xArg*cellSize+1, yArg*cellSize+1, cellSize-1, 3);
			g.setColor(game.Surface.green);
			g.fillRect(xArg*cellSize+1, yArg*cellSize+1, (int)(percentHitpoints*cellSize-1), 3);
		}
		if (experience != oldExperience) {
			g.setColor(game.Surface.blue);
			g.fillRect(xArg*cellSize+1, (yArg+1)*cellSize-3, (int)(percentLevelled*cellSize-1), 3);
			oldExperience = experience;
		}
	}
}