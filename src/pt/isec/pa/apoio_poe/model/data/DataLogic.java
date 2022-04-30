package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.tiposProposta.Internship;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Project;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.SelfProposal;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataLogic {
    // TODO how to use this map: numberStudents.get('<branchName>').getNmrStudents()/.getNmrProposals()
    Map<String, Wrapper> numberStudentsAndProposals; // Number of students and proposals by branch
    Map<String, Proposal> proposalsList; // Map with the list of proposals (Key: Proposal ID, Value: Proposal Object)
    Map<Long, Student> studentsList; // Map with the list of students (Key: Student Number, Value: Student Object)
    Map<String, Teacher> teachersList; // Map with the list of proposals (Key: Teacher email, Value: Teacher Object)
    Map<Student, Application> applicationsList; // Map with the list of applications (Key: Student Object, Value: Application Object)
    List<Attribution> attributionList;  // List containing information about attributions (contains references to the correspondent student, proposal and advisor)

    public DataLogic() {
        this.numberStudentsAndProposals = new HashMap<>();
        this.proposalsList = new HashMap<>();
        this.studentsList = new HashMap<>();
        this.teachersList = new HashMap<>();
        this.applicationsList = new HashMap<>();
        this.attributionList = new ArrayList<>();
        setup();
    }
    private void setup(){
        this.numberStudentsAndProposals.put("DA", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
        this.numberStudentsAndProposals.put("RAS", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
        this.numberStudentsAndProposals.put("SI", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
    }

    public void addInternship(String id, String title, Student assignedStudent, List<String> destinedBranch, String hostingEntity){
        proposalsList.put(id, new Internship(id,title,assignedStudent,destinedBranch,hostingEntity));
        for(String b : destinedBranch)
            numberStudentsAndProposals.get(b).incrementNmrProposals();
    }
    public void addProject(String id, String title, Student assignedStudent, List<String> destinedBranch, Teacher proposingTeacher){
        proposalsList.put(id, new Project(id,title,assignedStudent,destinedBranch,proposingTeacher));
        for(String b : destinedBranch)
            numberStudentsAndProposals.get(b).incrementNmrProposals();
    }
    public void addSelfProposal(String id, String title, Student assignedStudent){
        proposalsList.put(id, new SelfProposal(id,title,assignedStudent));
        numberStudentsAndProposals.get(assignedStudent.getBranch()).incrementNmrProposals();
        studentsList.get(assignedStudent.getStudentNumber()).setHasProposed(true);
    }
    public void addStudent(long studentNumber, String name, String email, String course, String branch, double classification, boolean internshipAccess){
        studentsList.put(studentNumber, new Student(studentNumber, name, email, course, branch, classification, internshipAccess));
        numberStudentsAndProposals.get(branch).incrementNmrStudents();
    }
    public void addTeacher(String email, String name, boolean isAdvisor){
        teachersList.put(email, new Teacher(email, name, isAdvisor));
    }
    public void addApplication(Student studentNumber, List<Proposal> chosenProposals){
        applicationsList.put(studentNumber, new Application(chosenProposals, studentNumber));
        studentsList.get(studentNumber.getStudentNumber()).setHasApplication(true);
    }

    public Student getStudent(long id){
        return studentsList.get(id);
    }
    public Teacher getTeacher(String id) {
        return teachersList.get(id);
    }
    public Proposal getProposal(String id){
        return proposalsList.get(id);
    }

    public boolean proposalExists(String id){ return proposalsList.containsKey(id);}
    public boolean proposalWithStudentExists(long assignedStudent){
        if (studentExists(assignedStudent))
            return studentsList.get(assignedStudent).hasProposed();
        return false;
    }
    public boolean hasAssignedStudent(String id){
        if(proposalExists(id)){
            return proposalsList.get(id).hasAssignedStudent();
        }
        return false;
    }
    public boolean hasInternshipAccess(long studentNumber){
        return studentsList.get(studentNumber).hasInternshipAccess();
    }
    public boolean isInternship(String id){
        return (proposalsList.get(id) instanceof Internship);
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
    public boolean applicationHasProposal(Proposal p){
        for(Application a : applicationsList.values()){
            if(a.chosenProposals.contains(p))
                return true;
        }
        return false;
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
    public Collection<Application> getApplicationsValues() {
        return applicationsList.values();
    }

    public boolean areProposalsMoreThanStudents(){
        for (Wrapper value : numberStudentsAndProposals.values()){  // iterate through all values from the hashmap
            if(value.getNmrProposals() < value.getNmrStudents())    // if one branch has less proposals than students, return false
                return false;
        }
        return true;
    }

    public String viewStudents() {
        StringBuilder sb = new StringBuilder();
        for (Student s : studentsList.values()) {
            sb.append(s.studentToString());
        }
        return sb.toString();
    }
    public String viewTeachers() {
        StringBuilder sb = new StringBuilder();
        for (Teacher t : teachersList.values()) {
            sb.append(t.teacherToString());
        }
        return sb.toString();
    }
    public String viewProposals() {
        StringBuilder sb = new StringBuilder();
        for (Proposal p : proposalsList.values()) {
            sb.append(p.proposalToString());
        }
        return sb.toString();
    }
    public String viewStudentsSelfProposals() {
        StringBuilder sb = new StringBuilder();
        for (Student s : studentsList.values()) {
            if(s.hasProposed())
                sb.append(s.studentToString());
        }
        return sb.toString();
    }
    public String viewStudentsWithApplication() {
        StringBuilder sb = new StringBuilder();
        for (Student s : studentsList.values()) {
            if(s.hasApplication())
                sb.append(s.studentToString());
        }
        return sb.toString();
    }
    public String viewStudentsWithoutApplication() {
        StringBuilder sb = new StringBuilder();
        for (Student s : studentsList.values()) {
            if(!s.hasApplication())
                sb.append(s.studentToString());
        }
        return sb.toString();
    }

    Predicate<Proposal> bySelfProposals = proposal -> proposal instanceof SelfProposal;
    Predicate<Proposal> byTeacherProposals = proposal -> proposal instanceof Project;
    Predicate<Proposal> byProposalInApplication = proposal -> applicationHasProposal(proposal);
    Predicate<Proposal> byProposalNotInApplication = proposal -> applicationHasProposal(proposal) == false;
    public String filterProposals(Integer... filters){
        StringBuilder sb = new StringBuilder();

        List<Proposal> results = new ArrayList();
        results.addAll(proposalsList.values());
        for(int element : filters){
            switch (element){
                case 1 -> results = results.stream().filter(bySelfProposals).collect(Collectors.toList());
                case 2 -> results = results.stream().filter(byTeacherProposals).collect(Collectors.toList());
                case 3 -> results = results.stream().filter(byProposalInApplication).collect(Collectors.toList());
                case 4 -> results = results.stream().filter(byProposalNotInApplication).collect(Collectors.toList());
                default -> { return ""; }
            }
        }
        sb.append("\n[FILTERED PROPOSALS]");
        for(var proposal : results){
            sb.append(proposal.proposalToString());
        }
        return sb.toString();
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
