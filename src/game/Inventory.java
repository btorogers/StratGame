package game;

import java.util.HashMap;

import javax.naming.InsufficientResourcesException;

public class Inventory {
	private HashMap<String, Integer> values = new HashMap<String, Integer>(){
		private static final long serialVersionUID = 1L;
		{
			put("wood", 0);
			put("stone", 0);
		}
	};
	
	public void transferAll(Inventory arg) {
		arg.add(getAsArray());
		remove(getAsArray());
	}
	
	public int get(String arg) {
		return values.get(arg);
	}
	
	public String[] getAsArray() {
		String[] array = new String[values.size() * 2];
		String[] keys = values.keySet().toArray(new String[0]);
		for (int x = 0; x < array.length; x += 2)  {
			array[x] = keys[x/2];
			array[x + 1] = String.valueOf(values.get(keys[x/2]));
		}
		return array;
	}
	// the typeArg is whether the list will be added or subtracted from the current inventory; it's true for addition.
	private void change(boolean typeArg, String[] args) throws ArrayIndexOutOfBoundsException, InsufficientResourcesException {
		if (args.length % 2 != 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		if (typeArg) {
			for (int x = 0; x < args.length; x += 2) {
				values.replace(args[x], values.get(args[x]) + Integer.valueOf(args[x+1]));
			}
		} else {
			for (int x = 0; x < args.length; x += 2) {
				int newValue = values.get(args[x]) - Integer.valueOf(args[x+1]);
				if (newValue < 0) {
					throw new InsufficientResourcesException();
				}
				values.replace(args[x], values.get(args[x]) - Integer.valueOf(args[x+1]));
			}
		}
	}
	
	public void add(String resourceArg, int amountArg) {
		try {
			change(true, new String[]{resourceArg, String.valueOf(amountArg)});
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
			System.out.println("The array supplied to the inventory wasn't formatted correctly.");
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InsufficientResourcesException e) {
			System.out.println("There were insufficient resources in the inventory.");
			e.printStackTrace();
		}
	}
	public void add(String[] arg) {
		try {
			change(true, arg);
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
			System.out.println("The array supplied to the inventory wasn't formatted correctly.");
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InsufficientResourcesException e) {
			System.out.println("There were insufficient resources in the inventory.");
			e.printStackTrace();
		}
	}
	public void remove(String resourceArg, int amountArg) {
		try {
			change(false, new String[]{resourceArg, String.valueOf(amountArg)});
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
			System.out.println("The array supplied to the inventory wasn't formatted correctly.");
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InsufficientResourcesException e) {
			System.out.println("There were insufficient resources in the inventory.");
			e.printStackTrace();
		}
	}
	public void remove(String[] arg) {
		try {
			change(false, arg);
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
			System.out.println("The array supplied to the inventory wasn't formatted correctly.");
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InsufficientResourcesException e) {
			System.out.println("There were insufficient resources in the inventory.");
			e.printStackTrace();
		}
	}


}