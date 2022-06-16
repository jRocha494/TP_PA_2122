package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.StageOneProposalModeUI;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.StageOneStudentModeUI;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.StageOneTeacherModeUI;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.StageOneUI;

public class RootPane extends BorderPane {
    private Button btnExit, btnStart;
    private final Manager manager;

    public RootPane(Manager manager) {
        this.manager=manager;
        createView();
        registerHandlers();
        update();
    }

    private void createView() {
//        btnStart = new Button("Start");
//        btnStart.setMinWidth(75);
//        btnExit = new Button("Exit");
//        btnExit.setMinWidth(75);
//
//        HBox hbox = new HBox();
//        hbox.getChildren().addAll(btnStart, btnExit);
//        hbox.setAlignment(Pos.CENTER);
//        hbox.setPadding(new Insets(20));
//        hbox.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
//        this.setCenter(hbox);

        StackPane stackPane = new StackPane(
                new StageOneUI(manager),
                new StageOneStudentModeUI(manager),
                new StageOneTeacherModeUI(manager),
                new StageOneProposalModeUI(manager)
                new StageTwoUI(manager)
        );
        this.setCenter(stackPane);
    }

    private void registerHandlers() {}

    private void update(){}
}
