package pt.isec.pa.apoio_poe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.ui.gui.RootPane;

public class MainJFX extends Application {
    Manager manager;
    @Override
    public void init() throws Exception {
        super.init();
        this.manager = new Manager();
    }

    @Override
    public void start(Stage stage) throws Exception {
        RootPane root = new RootPane(manager);
        Scene scene = new Scene(root, 600, 400, Color.NAVAJOWHITE);
        stage.setScene(scene);
        stage.setTitle("PIM - Projects/Internships Manager");
        stage.setResizable(true);
        stage.show();
    }
}
