package com.patchwork.app.frontend;

import com.patchwork.app.backend.*;
import com.patchwork.app.utils.ConsoleColor;
import com.patchwork.app.utils.GridModifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class TUITest {
    /* Mock System.out */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void test_drawQuiltBoard_shape() {
        TUI tui = new TUI(new Game());

        QuiltBoard quiltBoard = new QuiltBoard();
        tui.drawQuiltBoard(quiltBoard);

        /* Test Number of spaces */
        Pattern pattern = Pattern.compile(String.valueOf(TUI.SPACE_CHAR));
        Matcher matcher = pattern.matcher(outContent.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(9*9, count);

        /* Test Number of lines */
        pattern = Pattern.compile("\n");
        matcher = pattern.matcher(outContent.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(9, count);
    }

    @Test
    public void test_drawQuiltBoardWithPatch_shape() {
        TUI tui = new TUI(new Game());
        PatchFactory patchFactory = new PatchFactory();

        QuiltBoard quiltBoard = new QuiltBoard();
        tui.drawQuiltBoardWithPatch(quiltBoard, patchFactory.getPatches().get(0), 0, 0);

        /* Test Number of spaces */
        Pattern pattern = Pattern.compile(String.valueOf(TUI.SPACE_CHAR));
        Matcher matcher = pattern.matcher(outContent.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(9*9, count);

        /* Test Number of lines */
        pattern = Pattern.compile("\n");
        matcher = pattern.matcher(outContent.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(9, count);
    }

    @Test
    public void test_drawTimeBoard() {
        Game game = new Game();
        TUI tui = new TUI(game);

        /* Find characteristics of game */
        int numPatches = 0;
        int numButtons = 0;
        int numPlayers = 0;
        for(List<TimeBoard.SpaceElement> spaceElements : game.timeBoard.spaces) {
            for(TimeBoard.SpaceElement spaceElement : spaceElements) {
                if(spaceElement.type == TimeBoard.SpaceElementType.PATCH) numPatches++;
                if(spaceElement.type == TimeBoard.SpaceElementType.BUTTON) numPatches++;
                if(spaceElement.type == TimeBoard.SpaceElementType.PLAYER) numPlayers++;
            }
        }

        tui.drawTimeBoard();

        /* Test Number of patches drawn */
        Pattern pattern = Pattern.compile("PATCH");
        Matcher matcher = pattern.matcher(outContent.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(numPatches, count);

        /* Test Number of buttons drawn */
        pattern = Pattern.compile("BUTTN");
        matcher = pattern.matcher(outContent.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(numButtons, count);

        /* Test Number of players drawn */
        pattern = Pattern.compile("Play");
        matcher = pattern.matcher(outContent.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(numPlayers, count);
    }



    @Test
    public void test_drawPatches() {
        Game game = new Game();
        TUI tui = new TUI(game);
        List<Patch> availablePatches = game.patchList.getAvailablePatches();

        tui.drawPatches();

        /* Test Button Cost drawn */
        Pattern pattern = Pattern.compile("Button cost:(\\s)*" + availablePatches.get(0).buttonCost + "(\\s)*" + availablePatches.get(1).buttonCost + "(\\s)*" + availablePatches.get(2).buttonCost);
        Matcher matcher = pattern.matcher(outContent.toString());
        int count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);

        /* Test Time Cost drawn */
        pattern = Pattern.compile("Time cost:(\\s)*" + availablePatches.get(0).timeTokenCost + "(\\s)*" + availablePatches.get(1).timeTokenCost + "(\\s)*" + availablePatches.get(2).timeTokenCost);
        matcher = pattern.matcher(outContent.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);

        /* Test Income drawn */
        pattern = Pattern.compile("Income:(\\s)*" + availablePatches.get(0).buttonScore + "(\\s)*" + availablePatches.get(1).buttonScore + "(\\s)*" + availablePatches.get(2).buttonScore);
        matcher = pattern.matcher(outContent.toString());
        count = 0;
        while (matcher.find()) count++;
        assertEquals(1, count);
    }
}
