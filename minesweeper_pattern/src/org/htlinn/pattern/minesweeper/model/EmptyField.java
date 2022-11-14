package org.htlinn.pattern.minesweeper.model;

public class EmptyField extends Field {
	private int bombsCnt;

	public EmptyField(EmptyField f) {
		super(f);
		this.bombsCnt = f.getBombsCnt();
	}
	
	public EmptyField(int bombsCnt) {
		this.bombsCnt = bombsCnt;
	}

	public EmptyField(int bombsCnt, boolean open) {
		this.bombsCnt = bombsCnt;
		this.open = open;
	}
	
	public EmptyField() {
		super();
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
