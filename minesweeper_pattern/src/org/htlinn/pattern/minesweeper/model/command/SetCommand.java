package org.htlinn.pattern.minesweeper.model.command;

import org.htlinn.pattern.minesweeper.model.Field;
import org.htlinn.pattern.minesweeper.model.Playground;

public class SetCommand implements PlayCommandInterface {
	private Playground pg;
	private int x;
	private int y;
	private Field[][] f;
	
	public SetCommand(Playground pg, int x, int y) {
		this.pg = pg;
		this.x = x;
		this.y = y;
		this.f = pg.getMatrixDeepCopy();
	}

	@Override
	public void doIt() {
		pg.set(x, y);

	}

	@Override
	public void undo() {
		pg.setMatrix(f);

	}
}
