package org.htlinn.pattern.minesweeper.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.htlinn.pattern.minesweeper.model.MinesweeperMessage;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class LogView extends Stage implements Observer {
	Label txt = new Label();
	public LogView(Observable o) {
		o.addObserver(this);
		ScrollPane root = new ScrollPane();
		txt =  new Label();
	    txt.setWrapText(true);
	    this.setWidth(400);
	    this.setHeight(400);
	    root.setContent(txt);
	    this.setScene(new Scene(root));
	    this.setX(100);
	    this.setY(100);
	    this.setTitle("Minesweeper Logging");
		this.show();
	}

	@Override
	public void update(Observable observable, Object msgO) {
		MinesweeperMessage message = (MinesweeperMessage) msgO;
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		txt.setText(time + ":" + message + "\n" + txt.getText());
	}
}
