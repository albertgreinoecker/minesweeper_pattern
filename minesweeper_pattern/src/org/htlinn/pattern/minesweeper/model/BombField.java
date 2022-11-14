package org.htlinn.pattern.minesweeper.model;

public class BombField extends Field {
	
	public BombField(boolean open) {
		super(open);
	}
	
	public BombField()
	{
		super();
	}
	
	public BombField(BombField b)
	{
		super(b);
	}
	@Override
	public String toString() {
		return "*" + super.toString();
	}
}
