package org.htlinn.pattern.minesweeper.model.strategy;

import org.htlinn.pattern.minesweeper.model.Playground;

public interface PlayStrategy {
	boolean set(Playground p, int x, int y);
	boolean won();
}
