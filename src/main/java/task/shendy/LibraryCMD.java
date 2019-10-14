package task.shendy;

import task.shendy.console.ConsoleBrowser;

public class LibraryCMD {
    private static ConsoleBrowser consoleBrowser = new ConsoleBrowser();

    public static void main(String[] args) {
//        enableShutdownHook();
        consoleBrowser.start();
    }
}
