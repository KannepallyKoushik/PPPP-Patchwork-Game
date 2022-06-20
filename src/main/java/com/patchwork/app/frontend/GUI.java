package com.patchwork.app.frontend;

import com.patchwork.app.backend.Inputs.GameInput;
import com.patchwork.app.backend.Move;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class GUI extends GameInput implements IWritable {

    private Scene scene;
    private TextArea textArea;

    private final Map<KeyCode, Move> keyCodeMoveMap = makeKeyCodeMap();

    public GUI(Stage stage) {
        makeTextArea();
        makeScene();
        stage.setScene(scene);
        stage.show();
    }

    private void makeTextArea() {
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPrefRowCount(20);
        textArea.setStyle("-fx-font-family: 'monospaced';");
    }

    private void makeScene() {
        scene = new Scene(textArea, 800, 800);

        scene.setOnKeyPressed(e -> {
            if(e.getCode() != null) {
                handleKeyCode(e.getCode());
            }
        });
    }

    private void handleKeyCode(KeyCode keyCode) {
        if (!keyCodeMoveMap.containsKey(keyCode)) {
            // Ignore unknown moves
            return;
        }

        notify(keyCodeMoveMap.get(keyCode));
    }

    @Override
    public String getHelpText() {
        return "Controls:\n" +
                "    up, down, left, right -> w, s, a, d\n" +
                "    rotate left, rotate right -> q, e\n" +
                "    confirm -> enter";
    }

    @Override
    public void write(String text) {
        Platform.runLater(() -> {
            textArea.appendText(text);
            textArea.setScrollTop(Double.MAX_VALUE);
        });
    }

    @Override
    public void clear() {
        Platform.runLater(() -> {
            textArea.clear();
        });
    }

    private Map<KeyCode, Move> makeKeyCodeMap() {
        return Map.of(
               KeyCode.A, Move.MOVE_LEFT,
                KeyCode.D, Move.MOVE_RIGHT,
                KeyCode.W, Move.MOVE_UP,
                KeyCode.S, Move.MOVE_DOWN,
                KeyCode.Q, Move.ROTATE_CLOCKWISE,
                KeyCode.E, Move.ROTATE_COUNTERCLOCKWISE,
                KeyCode.H, Move.HELP,
                KeyCode.ENTER, Move.CONFIRM
        );
    }
}
