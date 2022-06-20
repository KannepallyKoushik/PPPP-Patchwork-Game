package com.patchwork.app.frontend;

public interface IWritable {

    void write(String text);

    default void writeln(String text) {
        this.write(text + "\n");
    }

    void clear();
}
