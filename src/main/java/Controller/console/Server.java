package Controller.console;

import View.console.App;

import java.io.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        App.getInstance().open();
        new save().start();
        new ServerImp().run();
    }

}

class save extends Thread {
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("close")) {
            input = scanner.nextLine();
        }
        App.getInstance().close();
        System.exit(0);
    }
}
