package game;

import static game.Main.*;
import static game.SelectionManager.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

public class Surface extends JPanel {
	private static final long serialVersionUID = 1L;
	public static Color gridColor = new Color(50,25,0);
	public static Color bgColor = new Color(0,100,0);
	public static Color selectedColor = new Color(200,200,0);
	public static Color progressColor = new Color(0,200,0);
	public static Color white = new Color(255,255,255);
	public static Color green = new Color(0,255,0);
	public static Color red = new Color(255,0,0);
	public static Color blue = new Color(0,0,255);
	public static Font normalFont = new Font("SansSerif", Font.PLAIN, 16);
	public static Font titleFont = new Font("SansSerif", Font.BOLD, 32);

	private void render(Graphics g1) {
		if (cellXMin < 0) {
			cellXMin = 0;
		}
		if (cellYMin < 0) {
			cellYMin = 0;
		}
		cellXMax = (int) Math.ceil((double)sizeX / cellSize + cellXMin);
		cellYMax = (int) Math.ceil((double)sizeY / cellSize + cellYMin);
		while (cellXMax > cellsX || cellYMax > cellsY) {
			if (cellXMin != 0) {
				cellXMin--;
			} else if (cellYMin != 0) {
				cellYMin--;
			} else {
				cellSize++;
			}
			cellXMax = (int) Math.ceil((double)sizeX / cellSize + cellXMin);
			cellYMax = (int) Math.ceil((double)sizeY / cellSize + cellXMin);
		}
		
		Graphics2D g = (Graphics2D) g1;
		//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(normalFont);
		g.setColor(gridColor);
		for(int x = 0; x < sizeX; x += cellSize) {
			g.drawLine(x, 0, x, sizeY);
		}
		for(int y = 0; y < sizeY; y += cellSize) {
			g.drawLine(0, y, sizeX, y);
		}
		g.setColor(bgColor);
		for(int x = cellXMin; x < cellXMax; x++) {
			for(int y = cellYMin; y < cellYMax; y++) {
				String content = grid[x][y];
				if (content == null) {
					g.fillRect((x*cellSize-cellSize*cellXMin)+1, (y*cellSize-cellSize*cellYMin)+1, cellSize-1, cellSize-1);
				} else {
					objectDictionary.get(content).renderSelf(g, x-cellXMin, y-cellYMin);
					g.setColor(bgColor);
				}
			}
		}
		g.setColor(gridColor);
		g.fillRect(sizeX,0,300,sizeY);
		renderPanel(g);
	}
	
	public static void renderPanel(Graphics2D g) {
		try {
			if (multiple) {
				for (int y = 0; y < numSelected; y++) {
					int yPos = 1 + (y * 33);
					g.setColor(Surface.white);
					g.fillRect(sizeX + 20, yPos, cellSize, cellSize);
					g.drawImage(objectDictionary.get(selected[y]).sprite.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT), sizeX + 20, yPos, null);
					g.drawString(objectDictionary.get(selected[y]).getDisplayName(), sizeX + 54, yPos + cellSize);
					g.drawString("Level: " + objectDictionary.get(selected[y]).level, sizeX + 110, yPos + cellSize);
					g.setColor(Surface.red);
					g.fillRect(sizeX + 170, yPos, 128, 24);
					g.setColor(Surface.green);
					g.fillRect(sizeX + 170, yPos, (int)(128*objectDictionary.get(selected[y]).percentHitpoints), 24);
					g.setColor(Surface.white);
					g.drawString("HP: "+objectDictionary.get(selected[y]).hitpoints+" / "+
					objectDictionary.get(selected[y]).maxHitpoints, sizeX + 174, yPos + 16);
				}
			} else {
				g.setColor(Surface.white);
				g.fillRect(sizeX + 16, 16, 128, 128);
				if (objectDictionary.get(selected[0]).sprite.getWidth() > 128 || objectDictionary.get(selected[0]).sprite.getHeight() > 128) {
					g.drawImage(objectDictionary.get(selected[0]).sprite.getScaledInstance(128, 128, Image.SCALE_SMOOTH), sizeX + 16, 16, null);
				} else {
					g.drawImage(objectDictionary.get(selected[0]).sprite,
							sizeX+80-objectDictionary.get(selected[0]).sprite.getWidth()/2,
							80-objectDictionary.get(selected[0]).sprite.getHeight()/2, null);
				}
				g.setFont(Surface.titleFont);
				g.drawString(objectDictionary.get(selected[0]).getDisplayName(), sizeX + 160, 48);
				g.setFont(Surface.normalFont);
				g.setColor(Surface.red);
				g.fillRect(sizeX + 160, 64, 128, 24);
				g.fillRect(sizeX + 160, 120, 128, 24);
				g.setColor(Surface.green);
				g.fillRect(sizeX + 160, 64, (int)(128*objectDictionary.get(selected[0]).percentHitpoints), 24);
				g.setColor(Surface.blue);
				g.fillRect(sizeX + 160, 120, (int)(128*objectDictionary.get(selected[0]).percentLevelled), 24);
				g.setColor(Surface.white);
				g.drawString("HP: "+objectDictionary.get(selected[0]).hitpoints+" / "+
						objectDictionary.get(selected[0]).maxHitpoints, sizeX + 164, 80);
				g.drawString("Level: "+objectDictionary.get(selected[0]).level, sizeX + 164, 112);
				g.drawString("Exp: "+objectDictionary.get(selected[0]).experience+" / "+
						objectDictionary.get(selected[0]).maxExperience, sizeX + 164, 136);
				String[] inventory = objectDictionary.get(selected[0]).inventory.getAsArray();
				for (int x = 0; x < inventory.length; x += 2) {
					int yPos = 160 + (x * 16);
					g.drawString(Character.toUpperCase(inventory[x].charAt(0)) + inventory[x].substring(1) + ": " + 
							inventory[x + 1], sizeX + 20, yPos);
				}
			}
		} catch (Exception e) {
			if (selected.length == 0) {
				g.setColor(Surface.white);
				g.setFont(Surface.titleFont);
				g.drawString("Nothing selected.", sizeX + 20, 200);
			} else {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		render(g);
	}
}