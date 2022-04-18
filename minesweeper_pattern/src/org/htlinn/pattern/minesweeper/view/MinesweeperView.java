package org.htlinn.pattern.minesweeper.view;

import java.util.Observable;
import java.util.Observer;

import org.htlinn.pattern.minesweeper.control.MinesweeperControl;
import org.htlinn.pattern.minesweeper.model.MinesweeperMessage;
import org.htlinn.pattern.minesweeper.model.Playground;
import org.htlinn.pattern.minesweeper.model.strategy.NormalStrategy;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MinesweeperView extends Application implements Observer {
	private Playground model;
	private MinesweeperControl control;
	private GridPane feld = new GridPane();

	private String IMG_HIDDEN = "images/circle_transparent.png";
	private String IMG_FLAG = "images/flag.png";
	private String IMG_BOMB = "images/bomb.png";

	public MinesweeperView(Playground model, MinesweeperControl control) {
		this.model = model;
		this.control = control;
	}

	private void setImg(Button b, String imgStr) {
		Image img = new Image(imgStr);
		ImageView view = new ImageView(img);
		view.setFitHeight(40);
		view.setPreserveRatio(true);
		b.setGraphic(view);
	}

	private void restart() {
		model.init();
		feld.getChildren().clear();
		fillGrid();
	}

	private void fillGrid() {
		for (int i = 0; i < model.getHeight(); i++) {
			for (int j = 0; j < model.getWidth(); j++) {
				Button b = new Button();
				b.setStyle("-fx-background-radius: 10");
				b.setMaxHeight(80);
				b.setMaxWidth(80);
				b.setMinHeight(80);
				b.setMinWidth(80);
				setImg(b, IMG_HIDDEN);
				b.addEventHandler(MouseEvent.MOUSE_CLICKED, control);
				b.setId(String.valueOf(i) + " " + String.valueOf(j));
				feld.add(b, j, i);
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		fillGrid();
		BorderPane root = new BorderPane();
		root.setCenter(feld);

		model.addObserver(this);
		primaryStage.setTitle("Minesweeper - Spiel");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		LogView l = new LogView(model);
		primaryStage.setOnCloseRequest(event -> {
			l.close();
		});
	}

	@Override
	public void update(Observable obs, Object message) {
		MinesweeperMessage msg = (MinesweeperMessage) message;
		Button b = (Button) (getNodeByRowColumnIndex(msg.getY(), msg.getX(), feld));
		if (msg.getAction().equals(MinesweeperMessage.ACTIONS.CELL_SET)) {
			b = (Button) (getNodeByRowColumnIndex(msg.getY(), msg.getX(), feld));
			b.setGraphic(null);
			b.setText(Integer.toString(msg.getNeighbourBombs()));
			switch (msg.getNeighbourBombs()) {
			case 0:
				b.setDisable(true);
				b.setText("");
				break;
			case 1:
				b.setStyle("-fx-background-color: #f4f4f8");
				break;
			case 2:
				b.setStyle("-fx-background-color: #fed766");
				break;
			case 3:
				b.setStyle("-fx-background-color: #2ab7ca");
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				b.setStyle("-fx-background-color: #fe4a49");
				break;
			}
		} else if (msg.getAction().equals(MinesweeperMessage.ACTIONS.LOST)) {
			b.setText("");
			setImg(b, IMG_BOMB);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Minesweeper");
			alert.setHeaderText(null);
			alert.setContentText("Du hast verloren! Das Spiel wird neu gestartet");
			alert.showAndWait();
			restart();
		} else if (msg.getAction().equals(MinesweeperMessage.ACTIONS.FLAG_ON)) {
			setImg(b, IMG_FLAG);
		} else if (msg.getAction().equals(MinesweeperMessage.ACTIONS.FLAG_OFF)) {
			setImg(b, IMG_HIDDEN);
		} else if (msg.getAction().equals(MinesweeperMessage.ACTIONS.WON)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Minesweeper");
			alert.setHeaderText(null);
			alert.setContentText("Du hast gewonnen! Das Spiel wird neue gestartet");

			alert.showAndWait();
			restart();
		}
	}

	public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
		Node result = null;
		ObservableList<Node> childrens = gridPane.getChildren();

		for (Node node : childrens) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
				result = node;
				break;
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		NormalStrategy strategy = new NormalStrategy();
		Playground model = Playground.instance(15, 15, 30, strategy);
		model.toStringA();
		MinesweeperControl control = new MinesweeperControl(model);
		MinesweeperView view = new MinesweeperView(model, control);
		Platform.runLater(() -> {
			try {
				view.start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}