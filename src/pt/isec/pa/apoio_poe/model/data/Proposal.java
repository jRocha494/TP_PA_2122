package pt.isec.pa.apoio_poe.model.data;

public class Proposal {
    protected String id;  // identification code
    protected String title;
    protected Student assignedStudent;   // identification (studentNumber) of the student assigned to said internship (null if no student was assigned yet)

    protected Proposal(String id, String title, Student assignedStudent) {
        this.id = id;
        this.title = title;
        this.assignedStudent = assignedStudent;
    }

    protected Proposal(String id, String title) {
        this.id = id;
        this.title = title;
        this.assignedStudent = null;
    }

    public String getId() {
        return id;
    }

    public String getTitle() { return title; }

    public long getAssignedStudent() {
        return assignedStudent.getStudentNumber();
    }

    public String proposalToString(){
        StringBuilder sb = new StringBuilder();
        sb.append("|> " + title + id +
                (assignedStudent!=null ? "has no student assigned yet" : "already has " + assignedStudent.getStudentNumber() + " | " + assignedStudent.getName() + " assigned to this proposal"));

        return sb.toString();
    }

    public boolean hasAssignedStudent() { return assignedStudent!=null; }
}
