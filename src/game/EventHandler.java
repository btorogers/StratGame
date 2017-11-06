package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import static game.Main.*;

public class EventHandler implements MouseListener, MouseWheelListener, KeyListener {
	private static int xPosStart;
	private static int yPosStart;
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int xPos = (int) Math.floor((e.getX()-3) / cellSize + cellXMin);
		int yPos = (int) Math.floor((e.getY()-25) / cellSize + cellYMin);
		if (e.getButton() == 3) {
		SelectionManager.actionClick(xPos, yPos);
		} else if (e.getButton() == 1) {
			cellXMin += xPosStart - xPos;
			cellYMin += yPosStart - yPos;
			SelectionManager.selectClick(xPos, yPos);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 16) {
			SelectionManager.multiSelect = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 16) {
			SelectionManager.multiSelect = false;
		} else if (e.getKeyCode() == 37) {
				cellXMin--;
		} else if (e.getKeyCode() == 38) {
				cellYMin--;
		} else if (e.getKeyCode() == 39) {
				cellXMin++;
		} else if (e.getKeyCode() == 40) {
				cellYMin++;
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		cellSize -= e.getWheelRotation();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			xPosStart = (int) Math.floor((e.getX()-3) / cellSize + cellXMin);
			yPosStart = (int) Math.floor((e.getY()-25) / cellSize + cellYMin);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}
}
