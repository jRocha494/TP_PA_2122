package pt.isec.pa.apoio_poe.model.data;

public class Proposal {
    protected String id;  // identification code
    protected String title;
    protected Student assignedStudent;   // identification (studentNumber) of the student assigned to said internship (null if no student was assigned yet)
    private boolean hasBeenAssigned;

    protected Proposal(String id, String title, Student assignedStudent) {
        this.id = id;
        this.title = title;
        this.assignedStudent = assignedStudent;
        this.hasBeenAssigned = false;
    }

    protected Proposal(String id, String title) {
        this.id = id;
        this.title = title;
        this.assignedStudent = null;
        this.hasBeenAssigned = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() { return title; }

    public Student getAssignedStudent() {
        return assignedStudent;
    }

    public String proposalToString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n|> " + title + id +
                (assignedStudent==null ? "has no student assigned yet" : "assigned to " + assignedStudent.getStudentNumber() + " | " + assignedStudent.getName()));

        return sb.toString();
    }

    public boolean hasAssignedStudent() { return assignedStudent!=null; }

    public void setHasBeenAssigned(boolean hasBeenAssigned) {
        this.hasBeenAssigned = hasBeenAssigned;
    }

    public void setHasBeenAssigned(Student student, boolean hasBeenAssigned) {
        this.assignedStudent = student;
        this.hasBeenAssigned = hasBeenAssigned;
    }

    public boolean hasBeenAssigned() {
        return hasBeenAssigned;
    }
}
