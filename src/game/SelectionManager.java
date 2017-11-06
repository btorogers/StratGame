package game;

import java.util.*;
import static game.Main.*;

public class SelectionManager {
	public static String[] selected = new String[0];
	public static int numSelected;
	public static String[] unselected;
	public static boolean multiSelect = false;
	public static boolean multiple = false;

	public static void update() {
		numSelected = selected.length;
		if (numSelected > 1) {
			multiple = true;
		} else {
			multiple = false;
		}
		for (String x : selected) {
			objectDictionary.get(x).selected = true;
		}
		List<String> list = new ArrayList<String>(Arrays.asList(objects));
		list.removeAll(new ArrayList<String>(Arrays.asList(selected)));
		list.removeAll(Collections.singleton(null));
		unselected = list.toArray(new String[0]);
		for (String x : unselected) {
			objectDictionary.get(x).selected = false;
		}
	}
	
	public static void actionClick(int xArg, int yArg) {
		for (String x : selected) {
			objectDictionary.get(x).giveAction(xArg, yArg);
		}
	}

	public static void selectClick(int xArg, int yArg) {
		String content = grid[xArg][yArg];
		if (content != null) {
			if (multiSelect) {
				if (Arrays.asList(selected).contains(content)) {
					unselect(content);
				} else {
					select(content);
				}
			} else {
				selected = new String[]{content};
			}
		}
	}
	
	public static void select(String nameArg) {
		List<String> list = new ArrayList<String>(Arrays.asList(selected));
		list.add(nameArg);
		selected = list.toArray(new String[0]);
	}
	
	public static void unselect(String nameArg) {
		List<String> list = new ArrayList<String>(Arrays.asList(selected));
		list.remove(nameArg);
		selected = list.toArray(new String[0]);
	}
	
	public static void selectRandom() {
		selected = new String[]{objects[0]};
	}
}