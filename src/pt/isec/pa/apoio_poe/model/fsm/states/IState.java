package pt.isec.pa.apoio_poe.model.fsm.states;

public interface IState {
    String importProposalsCSV(String filename);
    String exportProposalsCSV(String filename);
    String importStudentsCSV(String filename);
    String exportStudentsCSV(String filename);
    String importTeachersCSV(String filename);
    String exportTeachersCSV(String filename);
    String importApplicationsCSV(String filename);
    String exportApplicationsCSV(String filename);

    AppState getState();

    String getStage();

    boolean changeConfigurationMode(int option);
    boolean closeStage();
    boolean advanceStage();
    boolean returnStage();

    boolean automaticAttributionSelfProposals();
}
