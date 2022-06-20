package com.patchwork.app.frontend;

public class StdoutWritable implements IWritable {

    @Override
    public void write(String text) {
        System.out.print(text);
    }

    @Override
    public void clear() {
        // Do not supported for stdout
    }
}
