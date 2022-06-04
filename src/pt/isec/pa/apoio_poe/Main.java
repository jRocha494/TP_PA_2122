package pt.isec.pa.apoio_poe;

import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.ui.text.TextUI;

public class Main {
    public static void main(String[] args) {
        Manager m = new Manager();
        TextUI ui = new TextUI(m);
        ui.start();
        //Application.launch(MainJFX.class, args);
    }
}
