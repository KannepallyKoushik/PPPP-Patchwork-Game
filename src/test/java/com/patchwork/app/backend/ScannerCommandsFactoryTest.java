package com.patchwork.app.backend;

import com.patchwork.app.backend.controller.Inputs.ScannerCommands;
import com.patchwork.app.backend.controller.Inputs.ScannerCommandsFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScannerCommandsFactoryTest {
    private ScannerCommandsFactory scFactory;
    private ScannerCommands sc;

    @Before
    public void setUp(){
        this.scFactory = new ScannerCommandsFactory();
        this.sc = scFactory.createScannerCommands();
    }

    @Test
    public void createCommandsTest(){
        //There are currently 10 commands; 4 moves, 2 rotates, 2 mirrors, help & confirm
        assertEquals(10, sc.getScannerCommands().size());
    }
}
