package game.object;

public class Depot extends Building {
	public static int numberCreated = 0;

	public Depot(int xArg, int yArg, String nameArg) {
		super(xArg, yArg, nameArg, 2, 2, new String[]{ "./assets/BaseNone.png","./assets/BaseFull.png","./assets/BaseUpgrade1.png","./assets/BaseUpgrade2.png"});
		buildRequired = new String[]{};
		upgrade1Required = new String[]{};
		upgrade2Required = new String[]{};
		numberCreated++;
	}
}
