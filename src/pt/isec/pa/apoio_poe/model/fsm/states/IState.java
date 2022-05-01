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
    String exportStageThreeCSV(String filename);

    AppState getState();

    String getStage();

    boolean changeConfigurationMode(int option);

    boolean closeStage();

    boolean advanceStage();

    boolean returnStage();

    String viewStudents();

    String viewTeachers();

    String viewProposals();

    String viewStudentsSelfProposals();

    String viewStudentsWithApplication();

    String viewStudentsWithoutApplication();

    String filterProposals(Integer[] filters);

    boolean automaticAssignmentSelfProposals();

    boolean manuallyAssign(int proposalChosen, int studentChosen, String[] availableProposals, String[] availableStudents);

    boolean removeAssignment(int assignmentToRemove);

    boolean removeAllAssignments();

    String viewStudentsAssigned();

    String viewStudentsUnassigned();
    boolean automaticAssignment();

    String[] getConflictedCases();
    String getConflictedProposal();
    boolean resolveConflictedCases(int option);
}
