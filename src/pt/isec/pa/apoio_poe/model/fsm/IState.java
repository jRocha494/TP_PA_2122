package pt.isec.pa.apoio_poe.model.fsm;

public interface IState {
    String importCSV(String filename);

    String exportCSV(String filename);

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

    boolean automaticAssignmentAdvisors();

    String viewStudentsAssignedWithAdvisor();
    String viewStudentsAssignedWithoutAdvisor();
    String viewStudentsUnassignedWithApplications();
    String viewProposalsUnassigned();
    String viewProposalsAssigned();
}
