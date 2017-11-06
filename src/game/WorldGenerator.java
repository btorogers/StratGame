package game;

import static game.Main.*;

public class WorldGenerator {
	private static final int workerNumber = 3;
	private static final int NPCNumber = 0;
	private static final int treeNumber = 100;
	private static final int rockNumber = 50;
	//area arrays are formated in xMin, xMax, yMin, yMax
	private static final int[] treeSpawnArea = new int[]{0,cellsX,0,cellsY};
	private static final int[] rockSpawnArea = new int[]{0,cellsX,0,cellsY};
	
	public static void generateAll() {
		generateBuildings();
		generatePeople();
		generateTrees();
		generateRocks();
	}

	public static void generateBuildings() {
		objectDictionary.put("huse1", new game.object.House(random.nextInt(cellsX-4), random.nextInt(cellsY-4), "huse1"));
		objectDictionary.put("medc1", new game.object.Medic(random.nextInt(cellsX-4), random.nextInt(cellsY-4), "medc1"));
		objectDictionary.put("dpot0", new game.object.Depot(random.nextInt(cellsX-4), random.nextInt(cellsY-4), "dpot0"));
	}
	
	public static void generatePeople() {
		for(int x = 1; x < workerNumber+1; x++) {
			String nameGen = "work"+x;
			while (true) {
				int xPos = random.nextInt(cellsX-1);
				int yPos = random.nextInt(cellsY-1);
				if (grid[xPos][yPos] == null) {
					objectDictionary.put(nameGen, new game.object.Worker(xPos, yPos, nameGen));
					break;
				}
			}
		}
		for(int x = 1; x < NPCNumber+1; x++) {
			String nameGen = "npc_"+x;
			while (true) {
				int xPos = random.nextInt(cellsX-1);
				int yPos = random.nextInt(cellsY-1);
				if (grid[xPos][yPos] == null) {
					objectDictionary.put(nameGen, new game.object.NPC(xPos, yPos, nameGen));
					break;
				}
			}
		}
	}
	
	public static void generateTrees() {
		for(int x = 1; x < treeNumber+1; x++) {
			String nameGen = "tree"+x;
			while (true) {
				int xPos = random.nextInt(treeSpawnArea[1]-treeSpawnArea[0])+treeSpawnArea[0];
				int yPos = random.nextInt(treeSpawnArea[3]-treeSpawnArea[2])+treeSpawnArea[2];
				if (grid[xPos][yPos] == null) {
					objectDictionary.put(nameGen, new game.object.Tree(xPos, yPos, nameGen));
					break;
				}
			}
		}
	}
	
	public static void generateRocks() {
		for(int x = 1; x < rockNumber+1; x++) {
			String nameGen = "rock"+x;
			while (true) {
				int xPos = random.nextInt(rockSpawnArea[1]-rockSpawnArea[0])+rockSpawnArea[0];
				int yPos = random.nextInt(rockSpawnArea[3]-rockSpawnArea[2])+rockSpawnArea[2];
				if (grid[xPos][yPos] == null) {
					objectDictionary.put(nameGen, new game.object.Rock(xPos, yPos, nameGen));
					break;
				}
			}
		}
	}
}