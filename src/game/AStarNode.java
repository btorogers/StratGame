package game;

public class AStarNode {
	public static int cost = 10;
	public static int diagCost = 14;
	
	public int heuristic;
	public int fValue;
	public int gValue;
	public int x;
	public int y;
	private int xTarget;
	private int yTarget;
	public AStarNode parent;
	
	public AStarNode(int xArg, int yArg, int xTargetArg, int yTargetArg, AStarNode parentArg) {
		x = xArg;
		y = yArg;
		xTarget = xTargetArg;
		yTarget = yTargetArg;
		parent = parentArg;
		heuristic = calculateHeuristic(xTargetArg, yTargetArg);
		if (Math.abs(x - parent.x) + Math.abs(y - parent.y) < 2) {
			gValue = parent.gValue + cost;
		} else {
			gValue = parent.gValue + diagCost;
		}
		fValue = gValue + heuristic;
	}
	
	public AStarNode(int xArg, int yArg, int xTargetArg, int yTargetArg) {
		x = xArg;
		y = yArg;
		xTarget = xTargetArg;
		yTarget = yTargetArg;
		parent = null;
		heuristic = calculateHeuristic(xTargetArg, yTargetArg);
		fValue = 0;
	}
	
	private int calculateHeuristic(int xToArg, int yToArg) {
		int pathX = x;
		int pathY = y;
		int oldX = x;
		int oldY = y;
		int calculatedHeuristic = 0;
		while(pathX != xToArg || pathY != yToArg) {
			oldX = pathX;
			oldY = pathY;
			if (pathX < xToArg) {
				pathX++;
			} else if (pathX > xToArg) {
				pathX--;
			}
			if (pathY < yToArg) {
				pathY++;
			} else if (pathY > yToArg) {
				pathY--;
			}
			if (pathX != oldX && pathY != oldY) {
				calculatedHeuristic += diagCost;
			} else {
				calculatedHeuristic += cost;
			}
		}
		return calculatedHeuristic;
	}
}