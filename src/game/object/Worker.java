package game.object;

import static game.Main.*;

public class Worker extends Person {
	public static int numberCreated = 0;
	private int xAction = 0;
	private int yAction = 0;
	private String[] fullReq = new String[]{"wood", "200", "stone", "100"};

	public Worker(int xArg, int yArg, String nameArg) {
		super(xArg, yArg, nameArg);
		numberCreated++;
		xAction = xArg;
		yAction = yArg;
	}
	
	@Override
	public void update() {
		if (behaviour == 1) {
			if (grid[xAction][yAction] != null) {
				if (objectDictionary.get(grid[xAction][yAction]).getType().equals("Resource") == false) {
					int[] results = findNearestType("Resource");
					xAction = results[0];
					yAction = results[1];
					startMoveTo(xAction, yAction);
				}
			} else {
				int[] results = findNearestType("Resource");
				xAction = results[0];
				yAction = results[1];
				startMoveTo(xAction, yAction);
			}
		}
		move();
		interactWithSurroundings();
		super.update();
	}
	
	private boolean checkFull() {
		for (int x = 0; x < fullReq.length; x += 2) {
			if (inventory.get(fullReq[x]) > Integer.valueOf(fullReq[x+1])) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void giveAction(int xArg, int yArg) {
		if (grid[xArg][yArg] == null) {
			behaviour = 0;
		} else if (objectDictionary.get(grid[xArg][yArg]).getType().equals("Resource")) {
			behaviour = 1;
		}
		xAction = xArg;
		yAction = yArg;
		startMoveTo(xArg, yArg);
	}
	
	private void interactWithSurroundings() {
		String[] surroundings = getSurroundings();
		for (String x : surroundings) {
			GameObject o = objectDictionary.get(x);
			if (behaviour == 0) {
				if (o.getDisplayName().equals("Medic")) {
					if (hitpoints < maxHitpoints) {
						hitpoints += (o.level + 1);
					}
				}
				if (o.getType().equals("Building")) {
					if (o.percentHitpoints < 1.0) {
						o.hitpoints++;
					}
				}
			} else if (behaviour == 1) {
				if (o.getType().equals("Resource")) {
					if (checkFull()) {
						startMoveTo(objectDictionary.get("dpot0").x, objectDictionary.get("dpot0").y);
					} else {
						inventory.add(o.resourceGive);
						o.inventory.remove(o.resourceGive);
						experience++;
					}
				} else if (o.getDisplayName().equals("Depot")) {
					inventory.transferAll(o.inventory);
					startMoveTo(xAction, yAction);
				}
			}
		}
	}
}