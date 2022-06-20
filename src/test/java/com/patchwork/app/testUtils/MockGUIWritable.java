package com.patchwork.app.testUtils;

import com.patchwork.app.frontend.IWritable;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class MockGUIWritable implements IWritable {

    private final PrintStream printStream;

    public MockGUIWritable(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void write(String text) {
        try {
            printStream.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
    }
}
