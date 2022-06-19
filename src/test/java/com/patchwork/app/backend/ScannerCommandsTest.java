package com.patchwork.app.backend;

import com.patchwork.app.backend.controller.GameStates.Move;
import com.patchwork.app.backend.controller.Inputs.ScannerCommands;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScannerCommandsTest {
    private ScannerCommands sc;

    @Before
    public void setUp(){
        this.sc = new ScannerCommands();
    }

    @Test
    public void addTest(){
        assertEquals(0, sc.getScannerCommands().size());
        sc.add("Test", Move.HELP);
        assertEquals(1, sc.getScannerCommands().size());

    }

    @Test
    public void getInputsTest(){
        assertEquals(0,sc.getInputs().size());
        sc.add("Test", Move.HELP);
        assertTrue(sc.getInputs().contains("Test"));
        assertFalse(sc.getInputs().contains("Not Test"));
    }

    @Test
    public void getScannerCommandsTest(){
        assertEquals(0,sc.getScannerCommands().size());
        sc.add("Test", Move.HELP);
        assertEquals(sc.getScannerCommands().get("Test"), Move.HELP);
        assertNull(sc.getScannerCommands().get("Not Test"));
    }
}
