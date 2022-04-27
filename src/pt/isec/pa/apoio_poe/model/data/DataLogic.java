package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.tiposProposta.Internship;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Project;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.SelfProposal;

import java.util.*;

public class DataLogic {
    // TODO how to use this map: numberStudents.get('<branchName>').getNmrStudents()/.getNmrProposals()
    Map<String, Wrapper> numberStudentsAndProposals; // Number of students and proposals by branch
    Map<String, Proposal> proposalsList; // Map with the list of proposals (Key: Proposal ID, Value: Proposal Object)
    Map<Long, Student> studentsList; // Map with the list of students (Key: Student Number, Value: Student Object)
    Map<String, Teacher> teachersList; // Map with the list of proposals (Key: Teacher email, Value: Teacher Object)

    public DataLogic() {
        this.numberStudentsAndProposals = new HashMap<>();
        this.proposalsList = new HashMap<>();
        this.studentsList = new HashMap<>();
        this.teachersList = new HashMap<>();
        setup();
    }

    private void setup(){
        this.numberStudentsAndProposals.put("DA", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
        this.numberStudentsAndProposals.put("RAS", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
        this.numberStudentsAndProposals.put("SI", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
    }

    public void addInternship(String id, String title, List<String> destinedBranch, String hostingEntity){
        proposalsList.put(id, new Internship(id,title,destinedBranch,hostingEntity));
    }

    public void addProject(String id, String title, List<String> destinedBranch, String proposingTeacher){
        proposalsList.put(id, new Project(id,title,destinedBranch,proposingTeacher));
    }

    public void addInternship(String id, String title, long assignedStudent, List<String> destinedBranch, String hostingEntity){
        proposalsList.put(id, new Internship(id,title,assignedStudent,destinedBranch,hostingEntity));
    }

    public void addProject(String id, String title, long assignedStudent, List<String> destinedBranch, String proposingTeacher){
        proposalsList.put(id, new Project(id,title,assignedStudent,destinedBranch,proposingTeacher));
    }

    public void addSelfProposal(String id, String title, long assignedStudent){
        proposalsList.put(id, new SelfProposal(id,title,assignedStudent));
    }

    public void addStudent(long studentNumber, String name, String email, String course, String branch, double classification, boolean internshipAccess){
        studentsList.put(studentNumber, new Student(studentNumber, name, email, course, branch, classification, internshipAccess));
        numberStudentsAndProposals.get(branch).incrementNmrStudents();
    }

    public void addTeacher(String email, String name, boolean isAdvisor){
        teachersList.put(email, new Teacher(email, name, isAdvisor));
    }

    public boolean proposalExists(String id){ return proposalsList.containsKey(id);}

    public boolean proposalWithStudentExists(long assignedStudent){
        for(Proposal p : proposalsList.values()) {
            if(assignedStudent == p.getAssignedStudent())
                return true;
        }
        return false;
    }

    public boolean proposalWithTeacherExists(long assignedStudent){
        for(Proposal p : proposalsList.values()) {
            if(assignedStudent == p.getAssignedStudent())
                return true;
        }
        return false;
    }

    public boolean studentExists(long studentNumber){
        return studentsList.containsKey(studentNumber);
    }

    public boolean studentExists(String email){
        for(Student s : studentsList.values()) {
            if(email.equals(s.getEmail()))
                return true;
        }
        return false;
    }

    public boolean teacherExists(String email){
        return teachersList.containsKey(email);
    }

    public Collection<Proposal> getProposalsValues() {
        return proposalsList.values();
    }

    public Collection<Student> getStudentsValues() {
        return studentsList.values();
    }

    public Collection<Teacher> getTeachersValues() {
        return teachersList.values();
    }

    public boolean areProposalsMoreThanStudents(){
        for (Wrapper value : numberStudentsAndProposals.values()){  // iterate through all values from the hashmap
            if(value.getNmrProposals() < value.getNmrStudents())    // if one branch has less proposals than students, return false
                return false;
        }
        return true;
    }

    private class Wrapper{    // to be used on 'numberStudents' hashmap
        int nmrStudents;    // number of students in that branch
        int nmrProposals;   // number of proposals destined for that branch

        public Wrapper(int nmrStudents, int nmrProposals) {
            this.nmrStudents = nmrStudents;
            this.nmrProposals = nmrProposals;
        }

        public int getNmrStudents() { return nmrStudents; }
        public int getNmrProposals() { return nmrProposals; }
        public void setNmrProposals(int nmrProposals) { this.nmrProposals = nmrProposals; }
        public void setNmrStudents(int nmrStudents) { this.nmrStudents = nmrStudents; }
        public void incrementNmrProposals() { nmrProposals++; }
        public void incrementNmrStudents() { nmrStudents++; }

    }
}
