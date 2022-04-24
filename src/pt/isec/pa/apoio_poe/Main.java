package pt.isec.pa.apoio_poe;

import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.ui.text.TextUI;
import pt.isec.pa.apoio_poe.utils.PAInput;

public class Main {
    public static void main(String[] args) {
        Manager m = new Manager();
        TextUI ui = new TextUI(m);
        ui.start();
    }
}
