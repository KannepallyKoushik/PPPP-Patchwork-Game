package com.patchwork.app.backend;

import com.patchwork.app.backend.controller.GameStates.Move;
import com.patchwork.app.backend.controller.Inputs.ScannerCommands;
import com.patchwork.app.backend.controller.Inputs.ScannerInput;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScannerInputTest {
    private ScannerInput input;

    @Before
    public void setUp(){
        this.input = new ScannerInput();
    }

    @Test
    public void setUpTest(){
        assertNotNull(input.getScannerCommands());
    }

    @Test
    public void getMoveFromInputTest(){
        Move nullMove = input.getMoveFromInput("Null");
        assertNull(nullMove);
        assertEquals(input.getMoveFromInput("HELP"), Move.HELP);
    }

    @Test
    public void getScannerCommandsTest(){
        ScannerCommands sc = input.getScannerCommands();
        assertTrue(sc.getScannerCommands().size() > 0);
    }
}
