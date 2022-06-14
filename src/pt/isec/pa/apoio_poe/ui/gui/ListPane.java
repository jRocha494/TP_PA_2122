package pt.isec.pa.apoio_poe.ui.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.model.Manager;

public class ListPane extends ListView<Object> {
    private final Manager manager;
    Button teste;

    public ListPane(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        teste = new Button("teste");
        this.getItems().add(teste);
        this.setBackground(new Background(new BackgroundFill(Color.CRIMSON, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.LISTING, evt -> update());
        manager.addPropertyChangeListener(Manager.DATA, evt -> update());
    }

    private void update() {
        this.getItems().clear();
        this.setVisible(true);

        switch(manager.getListingType()){
            case STUDENTS -> this.getItems().addAll(manager.getStudents());
            case TEACHERS -> this.getItems().addAll(manager.getTeachers());
            case PROPOSALS -> this.getItems().addAll(manager.getProposals());
            //default -> this.setVisible(false);
        }
    }
}
