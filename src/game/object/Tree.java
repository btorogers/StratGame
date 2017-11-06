package game.object;

public class Tree extends Resource {
	public static int numberCreated = 0;
	
	public Tree(int xArg, int yArg, String nameArg) {
		super(xArg, yArg, nameArg,  "./assets/Tree.png", "wood");
		numberCreated++;
	}
}
