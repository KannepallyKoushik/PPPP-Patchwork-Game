package com.patchwork.app.frontend;

import com.patchwork.app.backend.Game;
import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.PatchFactory;
import com.patchwork.app.backend.QuiltBoard;
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
}
