package pt.isec.pa.apoio_poe.model.fsm.states;

public interface IState {
    AppState getState();

    boolean changeConfigurationMode(int option);
    boolean closeStage();
    boolean advanceStage();
}
