package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;
import pt.isec.pa.apoio_poe.model.Manager;

import java.io.File;

public class MenuBarUI extends MenuBar {
    private final Manager manager;
    private MenuItem mnSave, mnLoad, mnExit;

    public MenuBarUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        Menu app = new Menu("Application");
        mnSave = new MenuItem("_Save");
        mnLoad = new MenuItem("_Load");
        mnExit = new MenuItem("_Exit");
        app.getItems().addAll(mnSave, mnLoad, new SeparatorMenuItem(), mnExit);

        this.getMenus().add(app);
    }

    private void registerHandlers() {
        mnSave.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("(*.dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );
            File hFile = fileChooser.showSaveDialog(this.getScene().getWindow());
            if (hFile != null){
                manager.save(hFile);
            }
        });

        mnLoad.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("File open...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("(*.dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );
            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow());
            if (hFile != null){
                manager.load(hFile);
            }
        });

        mnExit.setOnAction(actionEvent -> Platform.exit());
    }

    private void update() {
    }
}
