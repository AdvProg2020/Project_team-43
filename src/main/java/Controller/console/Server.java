package Controller.console;

import View.console.App;

import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        App.getInstance().open();
        new ServerImp().run();
    }


}
