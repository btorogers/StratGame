package game.object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public abstract class Building extends GameObject {
	protected BufferedImage spriteLevel0 = null;
	protected BufferedImage spriteLevel1 = null;
	protected BufferedImage spriteLevel2 = null;
	protected BufferedImage spriteLevel3 = null;
	protected int sizeX = 1;
	protected int sizeY = 1;
	protected int levelHitpointGrowth = 100;
	protected String[] buildRequired = new String[]{"wood", "100"};
	protected String[] upgrade1Required = new String[]{"wood", "100", "stone", "50"};
	protected String[] upgrade2Required = new String[]{"wood", "100", "stone", "100"};

	public Building(int xArg, int yArg, String nameArg, int sizeXArg, int sizeYArg, String[] spritesArg) {
		super(xArg, yArg, nameArg, spritesArg[0]);
		
		sizeX = sizeXArg;
		sizeY = sizeYArg;

		try {
			spriteLevel0 = ImageIO.read(new File(spritesArg[0]));
			spriteLevel1 = ImageIO.read(new File(spritesArg[1]));
			spriteLevel2 = ImageIO.read(new File(spritesArg[2]));
			spriteLevel3 = ImageIO.read(new File(spritesArg[3]));
		} catch (IOException e) {
			System.out.println("Error reading sprite: " + Arrays.toString(spritesArg));
			e.printStackTrace();
		}
		
		for (int x = this.x; x < this.x + sizeX; x++) {
			for (int y = this.y; y < this.y + sizeY; y++) {
				game.Main.grid[x][y] = objName;
			}
		}
		maxExperience = 0;
	}
	
	@Override
	public void update() {
		if (level != 3) {
			percentLevelled = calculatePercentLevelled();
			if (percentLevelled >= 1) {
				inventory.remove(getRequiredMaterials());
				maxHitpoints += levelHitpointGrowth;
				hitpoints += levelHitpointGrowth;
				percentLevelled = 0.0;
				switch (level) {
				case 0: sprite = spriteLevel1; break;
				case 1: sprite = spriteLevel2; break;
				case 2: sprite = spriteLevel3; break;
				}
				level++;
			}
		}
		super.update();
	}
	
	private double calculatePercentLevelled() {
		String[] materials = getRequiredMaterials();
		if (materials.length % 2 != 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		double segmentValue = 1 / (double) (materials.length/2);
		double returnValue = 0.0;
		for (int x = 0; x < materials.length; x += 2) {
			double amount = (double)inventory.get(materials[x])/Double.valueOf(materials[x + 1]);
			if (amount > 1) {
				amount = 1;
			}
			returnValue += amount * segmentValue;
		}
		return returnValue;
	}
	
	private String[] getRequiredMaterials() {
		switch (level) {
		case 0: return buildRequired;
		case 1: return upgrade1Required;
		case 2: return upgrade2Required;
		default: return null;
		}
	}
	
	@Override
	public void renderSelf(Graphics2D g, int xArg, int yArg) {
		int cellSize = game.Main.cellSize;
		int x = this.x - game.Main.cellXMin;
		int y = this.y - game.Main.cellYMin;
		g.fillRect(x*cellSize+1, y*cellSize+1, sizeX*cellSize-1, sizeY*cellSize-1);
		if (level != 3) {
			g.setColor(game.Surface.progressColor);
			g.fillRect(x*cellSize+1, y*cellSize+1, (int)(sizeX*cellSize*percentLevelled), sizeY*cellSize-1);
		}
		g.drawImage(sprite.getScaledInstance(sizeX * cellSize, sizeY * cellSize, Image.SCALE_DEFAULT), x*cellSize, y*cellSize, null);
		if (percentHitpoints < 1.0) {
			g.setColor(game.Surface.red);
			g.fillRect(x*cellSize+1, y*cellSize+1, sizeX*cellSize-1, 3);
			g.setColor(game.Surface.green);
			g.fillRect(x*cellSize+1, y*cellSize+1, (int)(sizeX*percentHitpoints*cellSize-1), 3);
		}
	}
}