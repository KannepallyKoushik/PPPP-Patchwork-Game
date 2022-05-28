package com.patchwork.app.backend.Mock;


//Regular system.in scanner for normal functionality
import java.util.Scanner;

public class RealScanner implements Scanners {
    private Scanner s;

    public RealScanner(Scanner scanner) {
        s = scanner;
    }

    @Override
    public String nextLine() {
        return s.nextLine();
    }
}
