package org.htlinn.pattern.minesweeper.model;

public class BombField extends Field {
	@Override
	public String toString() {
		return "*" + super.toString();
	}
}
