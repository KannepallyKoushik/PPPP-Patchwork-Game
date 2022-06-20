package com.patchwork.app.frontend;

import com.patchwork.app.backend.model.Patch;
import com.patchwork.app.backend.model.PatchFactory;
import com.patchwork.app.backend.model.QuiltBoard;
import com.patchwork.app.backend.model.TimeBoard;
import com.patchwork.app.testutils.AbstractGameTest;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class TUITest extends AbstractGameTest {

    public TUITest() {
        super();
        autostart_game_controller = false;
    }

    @Test
    public void test_drawQuiltBoard_shape() {
        QuiltBoard quiltBoard = new QuiltBoard();
        
        tui.drawQuiltBoard(quiltBoard);

        validate_quiltboard_spaces();
        validate_quiltboard_lines();
    }

    @Test
    public void test_drawQuiltBoardWithPatch_shape() {
        PatchFactory patchFactory = new PatchFactory();

        QuiltBoard quiltBoard = new QuiltBoard();

        tui.drawQuiltBoardWithPatch(quiltBoard, patchFactory.getPatches().get(0), 0, 0);

        /* Test Number of spaces */
        validate_quiltboard_spaces();
        validate_quiltboard_lines();
    }

    @Test
    public void test_drawTimeBoard() {
        /* Find characteristics of game */
        int numPatches = 0;
        int numButtons = 0;
        int numPlayers = 0;
        for (List<TimeBoard.SpaceElement> spaceElements : game.timeBoard.spaces) {
            for (TimeBoard.SpaceElement spaceElement : spaceElements) {
                if (spaceElement.type == TimeBoard.SpaceElementType.PATCH) numPatches++;
                if (spaceElement.type == TimeBoard.SpaceElementType.BUTTON) numButtons++;
                if (spaceElement.type == TimeBoard.SpaceElementType.PLAYER) numPlayers++;
            }
        }

        tui.drawTimeBoard();

        validate_timeboard_patches(numPatches);
        validate_timeboard_buttons(numButtons);
        validate_timeboard_players(numPlayers);
    }

    @Test
    public void test_drawPatches() {
        List<Patch> availablePatches = game.patchList.getAvailablePatches();

        tui.drawPatches(availablePatches, -1);

        /* Check that the patches have been drawn properly */
        validate_button_cost(availablePatches);
        validate_time_cost(availablePatches);
        validate_income(availablePatches);
    }

    private void validate_quiltboard_spaces() {
        Pattern pattern = Pattern.compile(
                String.format(
                        "[%c%c%c]",
                        TUI.QUILTBOARD_PATCH_SPACE_CHAR,
                        TUI.QUILTBOARD_EMPTY_SPACE_CHAR,
                        TUI.QUILTBOARD_HIGHLIGHTED_PATCH_SPACE_CHAR
                )
        );
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(tuiOutput.toString(), 9 * 9, count);
    }

    private void validate_quiltboard_lines() {
        Pattern pattern = Pattern.compile("\n");
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(9, count);
    }

    private void validate_button_cost(List<Patch> availablePatches) {
        Pattern pattern = Pattern.compile("Button cost:(\\s)*" + availablePatches.get(0).buttonCost + "(\\s)*" + availablePatches.get(1).buttonCost + "(\\s)*" + availablePatches.get(2).buttonCost);
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);
    }

    private void validate_time_cost(List<Patch> availablePatches) {
        Pattern pattern = Pattern.compile("Time cost:(\\s)*" + availablePatches.get(0).timeTokenCost + "(\\s)*" + availablePatches.get(1).timeTokenCost + "(\\s)*" + availablePatches.get(2).timeTokenCost);
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);
    }

    private void validate_income(List<Patch> availablePatches) {
        Pattern pattern = Pattern.compile("Income:(\\s)*" + availablePatches.get(0).buttonScore + "(\\s)*" + availablePatches.get(1).buttonScore + "(\\s)*" + availablePatches.get(2).buttonScore);
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);
    }

    private void validate_timeboard_patches(int expected) {
        Pattern pattern = Pattern.compile("PATCH");
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(expected, count);
    }

    private void validate_timeboard_buttons(int expected) {
        Pattern pattern = Pattern.compile("BUTTN");
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(expected, count);
    }

    private void validate_timeboard_players(int expected) {
        Pattern pattern = Pattern.compile("Play");
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(expected, count);
    }
}
