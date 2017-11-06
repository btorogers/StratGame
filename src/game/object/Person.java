package game.object;

import static game.Main.cellsX;
import static game.Main.cellsY;

import java.util.ArrayList;
import java.util.Stack;

import game.AStarNode;

public abstract class Person extends GameObject {
	// behaviour 0 is do nothing, 1 is collect, 2 is build
	protected int behaviour = 0;
	protected int[] path;
	private int pathIndex = 0;
	
	public Person(int xArg, int yArg, String nameArg) {
		super(xArg, yArg, nameArg, "./assets/Player.png");
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	protected void followPath() {
		boolean onTrack = false;
		if (x == path[pathIndex] && y == path[pathIndex+1]) {
			if(moveAbsolute(path[pathIndex+2], path[pathIndex+3])) {
				onTrack = true;
				pathIndex += 2;
			} else {
				generatePath(xTo, yTo);
			}
		}
		if(onTrack == false) {
			generatePath(xTo, yTo);
		}
	}
	
	protected boolean moveAbsolute(int xArg, int yArg) {
		if(xArg < 0 || yArg < 0 || xArg >= game.Main.cellsX || yArg >= game.Main.cellsY || game.Main.grid[xArg][yArg] != null) {
			return false;
		} else {
			game.Main.grid[x][y] = null;
			x = xArg;
			y = yArg;
			game.Main.grid[x][y] = objName;
			return true;
		}
	}
	protected boolean move(int xArg, int yArg) {
		int newX = x + xArg;
		int newY = y + yArg;
		if(newX < 0 || newY < 0 || newX >= game.Main.cellsX || newY >= game.Main.cellsY || game.Main.grid[newX][newY] != null) {
			return false;
		} else {
			game.Main.grid[x][y] = null;
			x += xArg;
			y += yArg;
			game.Main.grid[x][y] = objName;
			return true;
		}
	}
	
	protected void generatePath(int xToArg, int yToArg) {
		xTo = xToArg;
		yTo = yToArg;
		//generateLinearPath(xToArg, yToArg);
		generateAStarPath(xToArg, yToArg);
		pathIndex = 0;
	}
	
	protected void move() {
		if (x == xTo && y == yTo) {
			moveTo = false;
			pathIndex = 0;
		}
		if (moveTo == true) {
			followPath();
			/*if (x - xTo != 0) {
				moveToX = true;
			}
			if (y - yTo != 0) {
				moveToY = true;
			}
			if (moveToX == true) {
				if (x > xTo) {
					if (move(-1, 0) == false && moveToY == false) {
						if (move(0, -1) == false) {
							if (move(0, 1) == false) {
								System.out.println(objName + " stuck.");
							}
						}
					}
				} else if (x < xTo) {
					if (move(1, 0) == false && moveToY == false) {
						if (move(0, -1) == false) {
							if (move(0, 1) == false) {
								System.out.println(objName + " stuck.");
							}
						}
					}
				} else {
					moveToX = false;
				}
			}
			if (moveToY == true) {
				if (y > yTo) {
					if (move(0, -1) == false && moveToX == false) {
						if (move(1, 0) == false) {
							if (move(-1, 0) == false) {
								System.out.println(objName + " stuck.");
							}
						}
					}
				} else if (y < yTo) {
					if (move(0, 1) == false && moveToX == false) {
						if (move(1, 0) == false) {
							if (move(-1, 0) == false) {
								System.out.println(objName + " stuck.");
							}
						}
					}
				} else {
					moveToY = false;
				}
			}
			if (moveToX == false && moveToY == false) {
				moveTo = false;
			}*/
		}
	}
	
	protected void startMoveTo(int xArg, int yArg) {
		if (game.Main.grid[xArg][yArg] == null) {
			xTo = xArg;
			yTo = yArg;
			generatePath(xArg, yArg);
			moveTo = true;
		} else {
			int[] nearest = findNearestSpace(xArg, yArg);
			xTo = nearest[0];
			yTo = nearest[1];
			generatePath(nearest[0], nearest[1]);
			moveTo = true;
		}
	}
	
	private void generateLinearPath(int xToArg, int yToArg) {
		ArrayList<Integer> pathPoints = new ArrayList<Integer>();
		int pathX = x;
		int pathY = y;
		int index = 0;
		while(pathX != xToArg || pathY != yToArg) {
			pathPoints.add(index, pathX);
			pathPoints.add(index+1, pathY);
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
			index+=2;
		}
		pathPoints.add(index, pathX);
		pathPoints.add(index+1, pathY);
		path = new int[pathPoints.size()];
		for (int count = 0; count < pathPoints.size(); count++) {
			path[count] = pathPoints.get(count);
		}
	}
	
	private void generateAStarPath(int xToArg, int yToArg) {
		ArrayList<AStarNode> openList = new ArrayList<AStarNode>();
		ArrayList<AStarNode> closedList = new ArrayList<AStarNode>();
		Stack<AStarNode> pathNodes = new Stack<AStarNode>();
		AStarNode currentNode = new AStarNode(x, y, xToArg, yToArg);
		boolean targetFound = false;
		openList.add(currentNode);
		while (targetFound == false) {
			currentNode = pickNode(openList);
			if (currentNode.x == xToArg && currentNode.y == yToArg) {
				targetFound = true;
			}
			for (int xCheck = currentNode.x - 1; xCheck < currentNode.x + 2; xCheck++) {
				for (int yCheck = currentNode.y - 1; yCheck < currentNode.y + 2; yCheck++) {
					if (xCheck>cellsX-1 || yCheck>cellsY-1 || xCheck<0 || yCheck<0) {
						
					} else {
						if (game.Main.grid[xCheck][yCheck] == null &&
							checkInList(xCheck, yCheck, openList, closedList) == false) {
							openList.add(new AStarNode(xCheck, yCheck, xToArg, yToArg, currentNode));
						}
					}
				}
			}
			openList.remove(currentNode);
			closedList.add(currentNode);
		}
		while (currentNode.parent != null) {
			pathNodes.push(currentNode);
			currentNode = currentNode.parent;
		}
		path = new int[pathNodes.size()*2 + 2];
		path[0] = x;
		path[1] = y;
		for (int count = 2; count < path.length - 3; count+=2) {
			currentNode = pathNodes.pop();
			path[count] = currentNode.x;
			path[count+1] = currentNode.y;
		}
		path[path.length-2] = xToArg;
		path[path.length-1] = yToArg;
	}

	private AStarNode pickNode(ArrayList<AStarNode> listArg) {
		AStarNode currentLowest = listArg.get(0);
		for (AStarNode check : listArg) {
			if (check.fValue < currentLowest.fValue) {
				currentLowest = check;
			}
		}
		return currentLowest;
	}
	
	private boolean checkInList(int xArg, int yArg, ArrayList<AStarNode> list1Arg, ArrayList<AStarNode> list2Arg) {
		for (AStarNode check : list1Arg) {
			if (check.x == xArg && check.y == yArg) {
				return true;
			}
		}
		for (AStarNode check : list2Arg) {
			if (check.x == xArg && check.y == yArg) {
				return true;
			}
		}
		return false;
	}
}