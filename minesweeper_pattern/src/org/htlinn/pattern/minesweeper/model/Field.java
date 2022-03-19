package org.htlinn.pattern.minesweeper.model;

public abstract class Field {
	protected boolean flag;
	protected boolean open;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public String toString() {
		if (flag)
			return "F";
		if (open)
			return "O";
		return "C";
	}
}
