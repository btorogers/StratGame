package game.object;

public class Rock extends Resource {
	public static int numberCreated = 0;
	
	public Rock(int xArg, int yArg, String nameArg) {
		super(xArg, yArg, nameArg,  "./assets/Rock.png", "stone");
		numberCreated++;
	}
}