package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.ui.gui.StageFive.StageFiveUI;
import pt.isec.pa.apoio_poe.ui.gui.StageFour.StageFourUI;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.StageOneProposalModeUI;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.StageOneStudentModeUI;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.StageOneTeacherModeUI;
import pt.isec.pa.apoio_poe.ui.gui.StageOne.StageOneUI;
import pt.isec.pa.apoio_poe.ui.gui.StageThree.ConflictStageUI;
import pt.isec.pa.apoio_poe.ui.gui.StageThree.StageThreeUI;
import pt.isec.pa.apoio_poe.ui.gui.StageTwo.StageTwoUI;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;

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
        StackPane stackPane = new StackPane(
                new StageOneUI(manager),
                new StageOneStudentModeUI(manager),
                new StageOneTeacherModeUI(manager),
                new StageOneProposalModeUI(manager),
                new StageTwoUI(manager),
                new StageThreeUI(manager),
                new ConflictStageUI(manager),
                new StageFourUI(manager),
                new StageFiveUI(manager)
        );
        this.setCenter(stackPane);

        CSSManager.applyCSS(stackPane, "styles.css");
    }

    private void registerHandlers() {}

    private void update(){}
}
