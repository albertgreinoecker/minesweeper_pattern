package org.htlinn.pattern.minesweeper.model;

public class EmptyField extends Field {
	private int bombsCnt;

	public EmptyField(int bombsCnt) {
		this.bombsCnt = bombsCnt;
	}

	public EmptyField() {
	}

	public int getBombsCnt() {
		return bombsCnt;
	}

	public void setBombsCnt(int bombsCnt) {
		this.bombsCnt = bombsCnt;
	}

	@Override
	public String toString() {
		if (bombsCnt == 0)
			return "_" + super.toString();
		return bombsCnt + super.toString();
	}
}
