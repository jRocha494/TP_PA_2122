package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.tiposProposta.Internship;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLogic {
    // TODO how to use this map: numberStudents.get('<branchName>').getNmrStudents()/.getNmrProposals()
    Map<String, Wrapper> numberStudentsAndProposals; // Number of students and proposals by branch
    List<Proposal> proposalsList;
    List<Student> studentsList;
    List<Teacher> teachersList;

    public DataLogic() {
        this.numberStudentsAndProposals = new HashMap<>();
        this.proposalsList = new ArrayList<Proposal>();
        this.studentsList = new ArrayList<Student>();
        this.teachersList = new ArrayList<Teacher>();
        setup();
    }

    private void setup(){
        this.numberStudentsAndProposals.put("DA", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
        this.numberStudentsAndProposals.put("RAS", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
        this.numberStudentsAndProposals.put("SI", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
    }

    public void addInternship(String id, String title, List<String> destinedBranch, String hostingEntity){
        proposalsList.add(new Internship(id,title,destinedBranch,hostingEntity));
    }

    public void addProject(String id, String title, List<String> destinedBranch, String proposingTeacher){
        proposalsList.add(new Project(id,title,destinedBranch,proposingTeacher));
    }

    public void addSelfProposal(String id, String title, long assignedStudent){
        proposalsList.add(new Proposal(id,title,assignedStudent));
    }

    public void addStudent(long studentNumber, String name, String email, String course, String branch, double classification, boolean internshipAccess){
        studentsList.add(new Student(studentNumber, name, email, course, branch, classification, internshipAccess));
    }

    public void addTeacher(String email, String name, boolean isAdvisor){
        teachersList.add(new Teacher(email, name, isAdvisor));
    }

    public boolean proposalExists(String id, long assignedStudent){
        for(Proposal p : proposalsList){
            if(p.getId().equalsIgnoreCase(id) || p.getAssignedStudent() == assignedStudent)
                return true;
        }
        return false;
    }

    public boolean studentExists(long studentNumber, String email){
        for(Student s : studentsList) {
            if(studentNumber == s.getStudentNumber() || email.equals(s.getEmail()))
                return true;
        }
        return false;
    }

    public boolean teacherExists(String email){
        for(Teacher t : teachersList){
            if(t.getEmail().equals(email))
                return true;
        }
        return false;
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
    }
}
