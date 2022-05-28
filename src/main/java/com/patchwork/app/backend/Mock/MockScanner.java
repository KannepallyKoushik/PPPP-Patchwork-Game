package com.patchwork.app.backend.Mock;


//Mock scanner which can be made beforehand for the tests
public class MockScanner implements Scanners {
    private final String string;

    public MockScanner(String nextLine) {
        string = nextLine;
    }

    @Override
    public String nextLine() {
        return string;
    }
}