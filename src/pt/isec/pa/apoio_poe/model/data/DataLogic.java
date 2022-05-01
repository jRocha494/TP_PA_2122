package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.tiposProposta.Internship;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Project;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.SelfProposal;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataLogic {
    // TODO how to use this map: numberStudents.get('<branchName>').getNmrStudents()/.getNmrProposals()
    private Map<String, Wrapper> numberStudentsAndProposals; // Number of students and proposals by branch
    private Map<String, Proposal> proposalsList; // Map with the list of proposals (Key: Proposal ID, Value: Proposal Object)
    private Map<Long, Student> studentsList; // Map with the list of students (Key: Student Number, Value: Student Object)
    private Map<String, Teacher> teachersList; // Map with the list of proposals (Key: Teacher email, Value: Teacher Object)
    private Map<Student, Application> applicationsList; // Map with the list of applications (Key: Student Object, Value: Application Object)
    private List<Assignment> assignmentList;  // List containing information about attributions (contains references to the correspondent student, proposal and advisor)

    public DataLogic() {
        this.numberStudentsAndProposals = new HashMap<>();
        this.proposalsList = new HashMap<>();
        this.studentsList = new HashMap<>();
        this.teachersList = new HashMap<>();
        this.applicationsList = new HashMap<>();
        this.assignmentList = new ArrayList<>();
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
    public void addAssignment(Assignment assignment) { assignmentList.add(assignment); }
    public void removeAssignment(int assignmentToRemove) { assignmentList.remove(assignmentToRemove); }
    public void removeAssignment(Assignment a) { assignmentList.remove(a); }

    public Student getStudent(long id){
        return studentsList.get(id);
    }
    public Teacher getTeacher(String id) {
        return teachersList.get(id);
    }
    public Proposal getProposal(String id){
        return proposalsList.get(id);
    }
    public Assignment getAssignment(int index) {return assignmentList.get(index); }
    public Application getApplicationByStudent(Student id) { return applicationsList.get(id); }

    public Proposal getProposalByStudent(long id){
        if(studentExists(id)) {
            for (Proposal p : proposalsList.values()) {
                if(p.hasAssignedStudent())
                    if(p.getAssignedStudent().getStudentNumber() == id)
                        return p;
            }
        }
        return null;
    }
    public boolean proposalExists(String id){ return proposalsList.containsKey(id);}
    public boolean proposalWithStudentExists(long assignedStudent){
        if (studentExists(assignedStudent))
            return studentsList.get(assignedStudent).hasProposed() || studentsList.get(assignedStudent).hasBeenAssigned();
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
            if(a.chosenProposalsContains(p))
                return true;
        }
        return false;
    }

    public boolean applicationHasProposal(Application a, Proposal p){
        return a.chosenProposals.contains(p);
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
    public List<Assignment> getAssignmentList() { return assignmentList; }

    public List<Student> getStudentsWithProposalInApplication(Proposal p) {
        List<Student> studentsWithProposal = new ArrayList<>();

        for (Application a : getApplicationsValues()) {
            if (a.chosenProposals.contains(p)) {
                studentsWithProposal.add(a.studentNumber);
            }
        }

        return studentsWithProposal;
    }

    public Proposal getFirstFreeProposal(Student student){
        for(Proposal p : applicationsList.get(student).getChoosenProposals()){
            if(!p.hasAssignedStudent())
                return p;
        }

        return null;
    }

    public List<Student> getStudentWithBestClassification(){
        List<Student> students = new ArrayList<>();
        double bestClassification = -1;

        for(Student s : getStudentsValues()){
            if(!s.hasBeenAssigned() && !s.hasProposed() && s.hasApplication()) {
                if (s.getClassification() > bestClassification) {
                    students.clear();
                    students.add(s);
                    bestClassification = s.getClassification();
                } else if (s.getClassification() == bestClassification)
                    students.add(s);
            }
        }

        return students;
    }

    public List<Student> getStudentsWithSameProposal(List<Student> students, Proposal p){
        List<Student> studentsWithProposal = new ArrayList<>();

        for(Student s : students){
            if(applicationHasProposal(applicationsList.get(s),p))
                studentsWithProposal.add(s);
        }

        return studentsWithProposal;
    }

    public Proposal getProposalByStudent(long id){
        if(studentExists(id)) {
            for (Proposal p : proposalsList.values()) {
                if(p.hasAssignedStudent())
                    if(p.getAssignedStudent().getStudentNumber() == id)
                        return p;
            }
        }
        return null;
    }

    public int getIndexofProposalInApplication(Proposal p, Student s){
        if(proposalExists(p.getId()) && studentExists(s.getStudentNumber())){
            if(applicationHasProposal(applicationsList.get(s),p)){
                return applicationsList.get(s).getChoosenProposals().indexOf(p) + 1;
            }
        }

        return -1;
    }

    public boolean areProposalsMoreThanStudents(){
        for (Wrapper value : numberStudentsAndProposals.values()){  // iterate through all values from the hashmap
            if(value.getNmrProposals() < value.getNmrStudents())    // if one branch has less proposals than students, return false
                return false;
        }
        return true;
    }

    public String[] getAvailableProposals(){
        List<String> result = new ArrayList<>();

        for(Map.Entry<String, Proposal> entry : proposalsList.entrySet()){
            if(!entry.getValue().hasBeenAssigned()) // if the proposal hasn't been assigned yet... adds its key to the array
                result.add(entry.getKey());
        }
        return result.toArray(new String[result.size()]);
    }

    public String[] getAvailableStudents() {
        List<String> result = new ArrayList<>();

        for(Map.Entry<Long, Student> entry : studentsList.entrySet()){
            if(!entry.getValue().hasBeenAssigned()); // if the proposal hasn't been assigned yet... adds its key to the array
                result.add(entry.getKey().toString());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] viewAssignments() {
        List<String> result = new ArrayList<>();

        for(Assignment a : assignmentList){
            result.add(a.toString());
        }

        return result.toArray(new String[result.size()]);
    }

    public boolean hasEveryStudentWithApplicationsBeenAssigned() {
        for(Student s : applicationsList.keySet()){
            if(!s.hasBeenAssigned())
                return false;
        }
        return true;
    }

    /*public boolean manuallyAssign(int proposalChosen, int studentChosen, String[] availableProposals, String[] availableStudents) {
        Proposal pToAssign = proposalsList.get(availableProposals[proposalChosen]);
        Student sToAssign = studentsList.get(availableStudents[studentChosen]);
        assignmentList.add(new Assignment(sToAssign, pToAssign));
        pToAssign.setHasBeenAssigned(true);
        sToAssign.setHasBeenAssigned(true);
    }*/

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
