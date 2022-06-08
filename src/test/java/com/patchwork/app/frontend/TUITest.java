package com.patchwork.app.frontend;

import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.PatchFactory;
import com.patchwork.app.backend.QuiltBoard;
import com.patchwork.app.backend.TimeBoard;
import com.patchwork.app.testUtils.AbstractGameTest;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class TUITest extends AbstractGameTest {

    @Test
    public void test_drawQuiltBoard_shape() {
        QuiltBoard quiltBoard = new QuiltBoard();

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        tui.drawQuiltBoard(quiltBoard, new PrintStream(outStream));

        validate_quiltboard_spaces(outStream);
        validate_quiltboard_lines(outStream);
    }

    @Test
    public void test_drawQuiltBoardWithPatch_shape() {
        PatchFactory patchFactory = new PatchFactory();

        QuiltBoard quiltBoard = new QuiltBoard();

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        tui.drawQuiltBoardWithPatch(quiltBoard, patchFactory.getPatches().get(0), 0, 0, new PrintStream(outStream));

        /* Test Number of spaces */
        validate_quiltboard_spaces(outStream);
        validate_quiltboard_lines(outStream);
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

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        tui.drawTimeBoard(new PrintStream(outStream));

        validate_timeboard_patches(numPatches, outStream);
        validate_timeboard_buttons(numButtons, outStream);
        validate_timeboard_players(numPlayers, outStream);
    }

    @Test
    public void test_drawPatches() {
        List<Patch> availablePatches = game.patchList.getAvailablePatches();

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        tui.drawPatches(availablePatches, -1, new PrintStream(outStream));

        /* Check that the patches have been drawn properly */
        validate_button_cost(availablePatches, outStream);
        validate_time_cost(availablePatches, outStream);
        validate_income(availablePatches, outStream);
    }

    private void validate_quiltboard_spaces(ByteArrayOutputStream out) {
        Pattern pattern = Pattern.compile(String.valueOf(TUI.SPACE_CHAR));
        Matcher matcher = pattern.matcher(out.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(9 * 9, count);
    }

    private void validate_quiltboard_lines(ByteArrayOutputStream out) {
        Pattern pattern = Pattern.compile("\n");
        Matcher matcher = pattern.matcher(out.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(9, count);
    }

    private void validate_button_cost(List<Patch> availablePatches, ByteArrayOutputStream out) {
        Pattern pattern = Pattern.compile("Button cost:(\\s)*" + availablePatches.get(0).buttonCost + "(\\s)*" + availablePatches.get(1).buttonCost + "(\\s)*" + availablePatches.get(2).buttonCost);
        Matcher matcher = pattern.matcher(out.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);
    }

    private void validate_time_cost(List<Patch> availablePatches, ByteArrayOutputStream out) {
        Pattern pattern = Pattern.compile("Time cost:(\\s)*" + availablePatches.get(0).timeTokenCost + "(\\s)*" + availablePatches.get(1).timeTokenCost + "(\\s)*" + availablePatches.get(2).timeTokenCost);
        Matcher matcher = pattern.matcher(out.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);
    }

    private void validate_income(List<Patch> availablePatches, ByteArrayOutputStream out) {
        Pattern pattern = Pattern.compile("Income:(\\s)*" + availablePatches.get(0).buttonScore + "(\\s)*" + availablePatches.get(1).buttonScore + "(\\s)*" + availablePatches.get(2).buttonScore);
        Matcher matcher = pattern.matcher(out.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);
    }

    private void validate_timeboard_patches(int expected, ByteArrayOutputStream out) {
        Pattern pattern = Pattern.compile("PATCH");
        Matcher matcher = pattern.matcher(out.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(expected, count);
    }

    private void validate_timeboard_buttons(int expected, ByteArrayOutputStream out) {
        Pattern pattern = Pattern.compile("BUTTN");
        Matcher matcher = pattern.matcher(out.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(expected, count);
    }

    private void validate_timeboard_players(int expected, ByteArrayOutputStream out) {
        Pattern pattern = Pattern.compile("Play");
        Matcher matcher = pattern.matcher(out.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(expected, count);
    }
}
