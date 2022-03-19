package org.htlinn.pattern.minesweeper.control;

import org.htlinn.pattern.minesweeper.model.Playground;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Handle user clicks here and call the model
 */
public class MinesweeperControl implements EventHandler<MouseEvent> {
	private Playground field;

	public MinesweeperControl(Playground field) {
		this.field = field;
	}

	@Override
	public void handle(MouseEvent event) {
		Button b = (Button) event.getSource();
		String[] coordinates = b.getId().split(" ");
		final int y = Integer.parseInt(coordinates[0]);
		final int x = Integer.parseInt(coordinates[1]);

		if (event.getButton() == MouseButton.PRIMARY) {
			field.play(x, y, Playground.ACTIONS.CLICK);
		} else if (event.getButton() == MouseButton.SECONDARY) {
			field.play(x, y, Playground.ACTIONS.FLAG);
		}

	}
}