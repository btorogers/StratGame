package game.object;

public class NPC extends Person {
	public static int numberCreated = 0;
	private String direction = "";
	private boolean moving = false;
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	
	public NPC(int xArg, int yArg, String nameArg) {
		super(xArg, yArg, nameArg);
		numberCreated++;
	}
	
	@Override
	public void update() {
		if (game.Main.random.nextFloat()>=0.7f) {
			if (moving==true) {
				moving = false;
				up = false;
				down = false;
				left = false;
				right = false;
				direction = "";
			}
			else {
				moving = true;
				switch (game.Main.random.nextInt(8)) {
				case 0: direction = "up"; break;
				case 1: direction = "down"; break;
				case 2: direction = "left"; break;
				case 3: direction = "right"; break;
				case 4: direction = "upleft"; break;
				case 5: direction = "upright"; break;
				case 6: direction = "downleft"; break;
				case 7: direction = "downright"; break;
				}
			}
		}
		if (moving==true) {
			switch (direction) {
			case "up": up = true; break;
			case "down": down = true; break;
			case "left": left = true; break;
			case "right": right = true; break;
			case "upleft": up = true; left = true; break;
			case "upright": up = true; right = true; break;
			case "downleft": down = true; left = true; break;
			case "downright": down = true; right = true; break;
			}
		}
		if(up) {
			move(0,-1);
		}
		if(down) {
			move(0,1);
		}
		if(left) {
			move(-1,0);
		}
		if(right) {
			move(1,0);
		}
		super.update();
	}
}