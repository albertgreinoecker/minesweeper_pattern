package org.htlinn.pattern.minesweeper.model;

import java.util.Arrays;
import java.util.Observable;

public class Playground extends Observable {
	public enum ACTIONS {
		CLICK, FLAG
	};

	private Field[][] matrix;
	private int width;
	private int height;
	private int bombs;

	public Playground(int width, int height, int bombs) {
		matrix = new Field[height][width];
		this.width = width;
		this.height = height;
		this.bombs = bombs;
		init();
	}

	public void init() {
		for (Field[] row : matrix) {
			Arrays.fill(row, new EmptyField());
		}

		for (int i = bombs; i > 0; i--) {
			int xRand = (int) (Math.random() * (width - 1));
			int yRand = (int) (Math.random() * (height - 1));

			if (matrix[yRand][xRand] instanceof EmptyField) {
				matrix[yRand][xRand] = new BombField();
			} else {
				i++;
			}
		}

		// count Bombs
		for (int i = 0; i < height; i++) {
			for (int h = 0; h < width; h++) {
				if (matrix[i][h] instanceof EmptyField) {
					int bombsCnt = cntNeighbourBombs(i, h);
					matrix[i][h] = new EmptyField(bombsCnt);
				}
			}
		}
	}

	public void openNeighbour(int x, int y) {
		if (matrix[y][x] instanceof EmptyField && ((EmptyField) matrix[y][x]).getBombsCnt() == 0) {
			for (int row = y - 1; row <= y + 1; row++) {
				for (int col = x - 1; col <= x + 1; col++) {
					try {
						if (!matrix[row][col].isOpen()) {
							matrix[row][col].setOpen(true);
							setChanged();
							notifyObservers(new MinesweeperMessage(MinesweeperMessage.ACTIONS.CELL_SET, col, row,
									((EmptyField) matrix[row][col]).getBombsCnt()));
							if (matrix[row][col] instanceof EmptyField
									&& ((EmptyField) matrix[row][col]).getBombsCnt() == 0) {
								openNeighbour(col, row);
							}
						}
					} catch (IndexOutOfBoundsException e) {
					}
				}
			}
		}

	}

	private int cntNeighbourBombs(int x, int y) {
		int count = 0;
		for (int row = x - 1; row <= x + 1; row++) {
			for (int col = y - 1; col <= y + 1; col++) {
				count += isBomb(row, col);
			}
		}
		return count;
	}

	private int isBomb(int x, int y) {
		try {
			if (matrix[x][y] instanceof BombField) {
				return 1;
			}
		} catch (IndexOutOfBoundsException e) {
			return 0;

		}
		return 0;
	}

	public void flagging(int x, int y) {
		matrix[y][x].setFlag(!matrix[y][x].isFlag());
		setChanged();
		if (matrix[y][x].isFlag()) {
			notifyObservers(new MinesweeperMessage(MinesweeperMessage.ACTIONS.FLAG_ON, x, y));
		} else {
			notifyObservers(new MinesweeperMessage(MinesweeperMessage.ACTIONS.FLAG_OFF, x, y));
		}
	}

	public boolean set(int x, int y) {
		matrix[y][x].setOpen(true);
		setChanged();
		notifyObservers(new MinesweeperMessage(MinesweeperMessage.ACTIONS.CELL_SET, x, y,
				matrix[y][x] instanceof EmptyField ? ((EmptyField) matrix[y][x]).getBombsCnt() : -1));
		if (matrix[y][x] instanceof BombField) {
			return false;
		}
		openNeighbour(x, y);
		return true;
	}

	public void play(int width, int height, ACTIONS action) {
		boolean ok = true;
		if (action.equals(ACTIONS.CLICK)) {
			ok = set(width, height);
		} else if (action.equals(ACTIONS.FLAG)) {
			flagging(width, height);
		}

		if (won()) {
			setChanged();
			notifyObservers(new MinesweeperMessage(MinesweeperMessage.ACTIONS.WON));
		} else if (!ok) {
			setChanged();
			notifyObservers(new MinesweeperMessage(MinesweeperMessage.ACTIONS.LOST, width, height));
		}

	}

	public boolean won() {
		int flags = 0;
		for (int i = 0; i < getHeight(); i++) {
			for (int h = 0; h < getWidth(); h++) {
				if (matrix[i][h] instanceof BombField) {
					if (matrix[i][h].isFlag()) {
						flags++;
					}
				}
			}
		}

		if (flags == bombs) {
			return true;
		}
		return false;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void toStringA() {
		for (Field[] f : matrix) {
			for (Field a : f) {
				System.out.print(a.toString());
			}
			System.out.println();
		}

	}
}
