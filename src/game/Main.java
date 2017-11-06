package game;

import javax.swing.JFrame;
import java.util.*;

public abstract class Main {
	public static final int cellsX = 50;
	public static final int cellsY = 35;
	public static int cellSize = 16;
	public static int cellXMin = 0;
	public static int cellYMin = 0;
	public static int cellXMax = cellsX;
	public static int cellYMax = cellsY;
	public static final int sizeX = cellsX*cellSize;
	public static final int sizeY = cellsY*cellSize;
	public static final int maxObjects = (cellsX*cellsY)/2;
	public static String[][] grid = new String[cellsX][cellsY];
	public static HashMap<String, game.object.GameObject> objectDictionary = new HashMap<String, game.object.GameObject>();
	public static Random random = new Random();
	public static String[] objects = new String[maxObjects];
	private static Surface surface = new Surface();
	
	public static void main(String[] args) {
		WorldGenerator.generateAll();
		JFrame frame = new JFrame("Ayy");
		frame.add(surface);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(sizeX+6+300, sizeY+28);
		frame.setIconImage(objectDictionary.get("work1").sprite);
		EventHandler eventHandler = new EventHandler();
		frame.addKeyListener(eventHandler);
		frame.addMouseListener(eventHandler);
		frame.addMouseWheelListener(eventHandler);
		mainLoop();
	}
	
	private static void mainLoop() {
		while(true) {
			update();
			surface.revalidate();
			surface.repaint();
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void update() {
		objectDictionary.keySet().toArray(objects);
		SelectionManager.update();
		for(String x : objects) {
			if (x != null) {
				objectDictionary.get(x).update();
			} else {
				break;
			}
		}
	}
}