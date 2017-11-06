package game.object;

public abstract class Resource extends GameObject {
	private String primaryResource;
	
	public Resource(int xArg, int yArg, String nameArg, String spriteArg, String primaryResourceArg) {
		super(xArg, yArg, nameArg, spriteArg);
		primaryResource = primaryResourceArg;
		for(int x = 0; x < 6; x++) {
			if (game.Main.random.nextFloat() > 0.7f) {
				level++;
			} else {
				break;
			}
		}
		inventory.add(primaryResource, (game.Main.random.nextInt(90) + 10) * (2 * level + 1));
		resourceGive[0] = primaryResource;
		resourceGive[1] = String.valueOf(level+1);
		maxHitpoints = inventory.get(primaryResource);
	}
	
	public void update() {
		if (game.Main.random.nextFloat() > 0.98f) {
			inventory.add(primaryResource, level + 1);
		}
		hitpoints = inventory.get(primaryResource);
		if (hitpoints > maxHitpoints) {
			maxHitpoints = hitpoints;
		} else if (inventory.get(primaryResource) <= level) {
			hitpoints = 0;
		}
		super.update();
	}
}
