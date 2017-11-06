package game.object;

public class House extends Building {

	public House(int xArg, int yArg, String nameArg) {
		super(xArg, yArg, nameArg, 3, 3, new String[]{ "./assets/BaseNone.png","./assets/BaseFull.png","./assets/BaseUpgrade1.png","./assets/BaseUpgrade2.png"});
		buildRequired = new String[]{"wood", "200"};
		upgrade1Required = new String[]{"wood", "200", "stone", "100"};
		upgrade2Required = new String[]{"wood", "200", "stone", "200"};
		maxHitpoints = 500;
		levelHitpointGrowth = 500;
	}

	@Override
	public void update() {
		if (game.Main.random.nextInt(100) < level ) {
			if (game.Main.grid[x+1][y+3] == null) {
				String name = "work" + (Worker.numberCreated + 1);
				game.Main.objectDictionary.put(name, new Worker(x+1, y+3, name));
			}
		}
		super.update();
	}
}
