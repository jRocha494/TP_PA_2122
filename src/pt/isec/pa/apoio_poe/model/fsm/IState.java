package pt.isec.pa.apoio_poe.model.fsm;

public interface IState {
    boolean boolImportCSV(String filename);

    boolean boolExportCSV(String filename);

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

    boolean manuallyAssign(String student, String proposal);

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

    boolean add(Object ... parameters);
    boolean delete(Object selectedItem);
    boolean update(Object ... parameters);
}
