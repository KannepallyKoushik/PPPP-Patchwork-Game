package com.patchwork.app.frontend;

import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.PatchFactory;
import com.patchwork.app.backend.QuiltBoard;
import com.patchwork.app.backend.TimeBoard;
import com.patchwork.app.testUtils.AbstractGameTest;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class TUITest extends AbstractGameTest {

    @Test
    public void test_drawQuiltBoard_shape() {
        QuiltBoard quiltBoard = new QuiltBoard();
        tui.drawQuiltBoard(quiltBoard);

        /* Test Number of spaces */
        Pattern pattern = Pattern.compile(String.valueOf(TUI.SPACE_CHAR));
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(9 * 9, count);

        /* Test Number of lines */
        pattern = Pattern.compile("\n");
        matcher = pattern.matcher(tuiOutput.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(9, count);
    }

    @Test
    public void test_drawQuiltBoardWithPatch_shape() {
        PatchFactory patchFactory = new PatchFactory();

        QuiltBoard quiltBoard = new QuiltBoard();
        tui.drawQuiltBoardWithPatch(quiltBoard, patchFactory.getPatches().get(0), 0, 0);

        /* Test Number of spaces */
        Pattern pattern = Pattern.compile(String.valueOf(TUI.SPACE_CHAR));
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(9 * 9, count);

        /* Test Number of lines */
        pattern = Pattern.compile("\n");
        matcher = pattern.matcher(tuiOutput.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(9, count);
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
                if (spaceElement.type == TimeBoard.SpaceElementType.BUTTON) numPatches++;
                if (spaceElement.type == TimeBoard.SpaceElementType.PLAYER) numPlayers++;
            }
        }

        tui.drawTimeBoard();

        /* Test Number of patches drawn */
        Pattern pattern = Pattern.compile("PATCH");
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(numPatches, count);

        /* Test Number of buttons drawn */
        pattern = Pattern.compile("BUTTN");
        matcher = pattern.matcher(tuiOutput.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(numButtons, count);

        /* Test Number of players drawn */
        pattern = Pattern.compile("Play");
        matcher = pattern.matcher(tuiOutput.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(numPlayers, count);
    }


    @Test
    public void test_drawPatches() {
        List<Patch> availablePatches = game.patchList.getAvailablePatches();

        tui.drawPatches();

        /* Test Button Cost drawn */
        Pattern pattern = Pattern.compile("Button cost:(\\s)*" + availablePatches.get(0).buttonCost + "(\\s)*" + availablePatches.get(1).buttonCost + "(\\s)*" + availablePatches.get(2).buttonCost);
        Matcher matcher = pattern.matcher(tuiOutput.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);

        /* Test Time Cost drawn */
        pattern = Pattern.compile("Time cost:(\\s)*" + availablePatches.get(0).timeTokenCost + "(\\s)*" + availablePatches.get(1).timeTokenCost + "(\\s)*" + availablePatches.get(2).timeTokenCost);
        matcher = pattern.matcher(tuiOutput.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);

        /* Test Income drawn */
        pattern = Pattern.compile("Income:(\\s)*" + availablePatches.get(0).buttonScore + "(\\s)*" + availablePatches.get(1).buttonScore + "(\\s)*" + availablePatches.get(2).buttonScore);
        matcher = pattern.matcher(tuiOutput.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);
    }
}
