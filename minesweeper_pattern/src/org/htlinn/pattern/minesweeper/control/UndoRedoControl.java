package org.htlinn.pattern.minesweeper.control;

import org.htlinn.pattern.minesweeper.model.command.CommandRecorder;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class UndoRedoControl implements EventHandler<MouseEvent> {

	private CommandRecorder recorder;

	public UndoRedoControl() {
		this.recorder = CommandRecorder.instance();
	}

	@Override
	public void handle(MouseEvent ev) {
		if (((Node) ev.getSource()).getId().equals("UNDO")) {
			System.out.println("UNDO");
			recorder.undo();
		} else {
			System.out.println("REDO");
			recorder.redo();
		}

	}

}
