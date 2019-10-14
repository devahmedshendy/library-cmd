package task.shendy.views;

import java.util.Scanner;

public abstract class MenuView {
    public final Scanner consoleReader = new Scanner(System.in);

    public abstract void render();
}
