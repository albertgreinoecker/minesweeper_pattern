package org.htlinn.pattern.minesweeper.control;

import org.htlinn.pattern.minesweeper.model.command.CommandRecorder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class UndoRedoControl implements EventHandler<ActionEvent> {

	private CommandRecorder recorder;

	public UndoRedoControl() {
		this.recorder = CommandRecorder.instance();
	}

	@Override
	public void handle(ActionEvent event) {
		if (((Node) event.getSource()).getId().equals("UNDO")) {
			System.out.println("UNDO");
			recorder.undo();
		} else {
			System.out.println("REDO");
			recorder.redo();
		}

	}

}
