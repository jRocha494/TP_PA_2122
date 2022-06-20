package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Proposal implements Serializable, Cloneable {
    static final long serialVersionUID = 100L;
    private String id;  // identification code
    private String title;
    private Student assignedStudent;   // identification (studentNumber) of the student assigned to said internship (null if no student was assigned yet)
    private boolean hasBeenAssigned;  // Flag on whether this proposal has been officially associated with an assignment or not

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

    public Student getAssignedStudent(){
        Student student = null;
        try {
            student = (Student) assignedStudent.clone();
        }
        catch (Exception e){
            //e.printStackTrace();
            return null;
        }
        return student;
    }

    public void setAssignedStudent(Student assignedStudent) {
        this.assignedStudent = assignedStudent;
    }

    public String proposalToString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n|> " + title + id +
                (assignedStudent==null ? "has no student assigned yet" : "assigned to " + assignedStudent.getStudentNumber() + " | " + assignedStudent.getName()));

        return sb.toString();
    }

    public String toStringExport(){
        return "";
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

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + id.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;

        if(!(o instanceof Proposal))
            return false;

        Proposal p = (Proposal) o;
        return id.equals(p.id);
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public void setStudentHasProposed(boolean b) {
        if (assignedStudent != null)
            assignedStudent.setHasProposed(b);
    }
}
