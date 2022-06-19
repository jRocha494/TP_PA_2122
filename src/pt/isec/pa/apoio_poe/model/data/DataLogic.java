package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.tiposProposta.Internship;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.Project;
import pt.isec.pa.apoio_poe.model.data.tiposProposta.SelfProposal;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataLogic implements Serializable {
    // TODO how to use this map: numberStudents.get('<branchName>').getNmrStudents()/.getNmrProposals()
    static final long serialVersionUID = 100L;
    private Map<String, Wrapper> numberStudentsAndProposals; // Number of students and proposals by branch
    private Map<String, Proposal> proposalsList; // Map with the list of proposals (Key: Proposal ID, Value: Proposal Object)
    private Map<Long, Student> studentsList; // Map with the list of students (Key: Student Number, Value: Student Object)
    private Map<String, Teacher> teachersList; // Map with the list of teachers (Key: Teacher email, Value: Teacher Object)
    private Map<Student, Application> applicationsList; // Map with the list of applications (Key: Student Object, Value: Application Object)
    private List<Assignment> assignmentList;  // List containing information about attributions (contains references to the correspondent student, proposal and advisor)
    private List<Student> conflictStudents;
    private Proposal conflictProposal;
    private boolean[] filtersStageTwo;
    private boolean[] filtersStageThree;

    //TODO Em verificações que se faça se é um branch válido, verificar se existe neste array
    private String[] branches = {"DA", "RAS", "SI"};

    public DataLogic() {
        this.numberStudentsAndProposals = new HashMap<>();
        this.proposalsList = new HashMap<>();
        this.studentsList = new HashMap<>();
        this.teachersList = new HashMap<>();
        this.applicationsList = new HashMap<>();
        this.assignmentList = new ArrayList<>();
        this.filtersStageTwo = new boolean[4];
        this.filtersStageThree = new boolean[4];
        setup();
    }
    private void setup(){
        this.numberStudentsAndProposals.put("DA", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
        this.numberStudentsAndProposals.put("RAS", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
        this.numberStudentsAndProposals.put("SI", new Wrapper(0, 0));   // each branch starts with 0 students and 0 proposals -> to be incremented as they're created
        Arrays.fill(filtersStageTwo, false);
        Arrays.fill(filtersStageThree, false);
    }
    public void updateInternship(String id, String title, Student assignedStudent, List<String> destinedBranch, String hostingEntity){
        Long studentID = proposalsList.get(id).getAssignedStudent() != null ? proposalsList.get(id).getAssignedStudent().getStudentNumber() : null;
        if (studentID != null)
            studentsList.get(studentID).setAssociatedWithProposal(false);

        proposalsList.put(id, new Internship(id,title,assignedStudent,destinedBranch,hostingEntity));
        if (assignedStudent!=null)
            assignedStudent.setAssociatedWithProposal(true);
    }
    public void updateProject(String id, String title, Student assignedStudent, List<String> destinedBranch, Teacher proposingTeacher){
        Long studentID = proposalsList.get(id).getAssignedStudent() != null ? proposalsList.get(id).getAssignedStudent().getStudentNumber() : null;
        if (studentID != null)
            studentsList.get(studentID).setAssociatedWithProposal(false);
        String teacherID = ((Project)proposalsList.get(id)).getProposingTeacher() != null ? ((Project) proposalsList.get(id)).getProposingTeacher().getEmail() : null;
        if (teacherID != null)
            teachersList.get(teacherID).setIsAdvisor(true);

        proposalsList.put(id, new Project(id,title,assignedStudent,destinedBranch,proposingTeacher));
        if (assignedStudent!=null)
            assignedStudent.setAssociatedWithProposal(true);
        if (proposingTeacher != null)
            proposingTeacher.setIsAdvisor(false);
    }
    public void updateSelfProposal(String id, String title, Student assignedStudent){
        Long studentID = proposalsList.get(id).getAssignedStudent() != null ? proposalsList.get(id).getAssignedStudent().getStudentNumber() : null;
        if (studentID != null)
            studentsList.get(studentID).setHasProposed(false);

        proposalsList.put(id, new SelfProposal(id,title,assignedStudent));
        if (assignedStudent != null)
            assignedStudent.setHasProposed(true);
//        studentsList.get(assignedStudent.getStudentNumber()).setHasProposed(true);
    }

    public void addInternship(String id, String title, Student assignedStudent, List<String> destinedBranch, String hostingEntity){
        proposalsList.put(id, new Internship(id,title,assignedStudent,destinedBranch,hostingEntity));
        if (assignedStudent!=null)
            assignedStudent.setAssociatedWithProposal(true);
        for(String b : destinedBranch)
            numberStudentsAndProposals.get(b).incrementNmrProposals();
    }
    public void addProject(String id, String title, Student assignedStudent, List<String> destinedBranch, Teacher proposingTeacher){
        proposalsList.put(id, new Project(id,title,assignedStudent,destinedBranch,proposingTeacher));
        if (assignedStudent!=null)
            assignedStudent.setAssociatedWithProposal(true);
        if (proposingTeacher != null)
            proposingTeacher.setIsAdvisor(false);
        for(String b : destinedBranch)
            numberStudentsAndProposals.get(b).incrementNmrProposals();
    }
    public void addSelfProposal(String id, String title, Student assignedStudent){
        proposalsList.put(id, new SelfProposal(id,title,assignedStudent));
        if (assignedStudent != null) {
            assignedStudent.setHasProposed(true);
            assignedStudent.setAssociatedWithProposal(true);
        }
        numberStudentsAndProposals.get(assignedStudent.getBranch()).incrementNmrProposals();
//        studentsList.get(assignedStudent.getStudentNumber()).setHasProposed(true);
    }
    public void addStudent(long studentNumber, String name, String email, String course, String branch, double classification, boolean internshipAccess){
        studentsList.put(studentNumber, new Student(studentNumber, name, email, course, branch, classification, internshipAccess));
        numberStudentsAndProposals.get(branch).incrementNmrStudents();
    }
    public void addTeacher(String email, String name, boolean isAdvisor){
        teachersList.put(email, new Teacher(email, name, isAdvisor));
    }
    public void addTeacher(String email, String name){
        teachersList.put(email, new Teacher(email, name));
    }
    public void addApplication(Student student, List<Proposal> chosenProposals){
        applicationsList.put(student, new Application(chosenProposals, student));
        studentsList.get(student.getStudentNumber()).setHasApplication(true);
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
        //if(studentExists(id)) {
            for (Proposal p : proposalsList.values()) {
                if(p.hasAssignedStudent())
                    if(p.getAssignedStudent().getStudentNumber() == id)
                        return p;
            }
        //}
        return null;
    }
    public Proposal getProposalByTeacher(String email){
        //if(teacherExists(email)) {
            for (Proposal p : proposalsList.values()) {
                if (p instanceof Project && ((Project) p).hasProposingTeacher())
                    if(((Project) p).getProposingTeacher().getEmail().equalsIgnoreCase(email))
                        return p;
            }
        //}
        return null;
    }

    public Teacher getAssignedTeacherByStudent(long id) {
        if (studentExists(id)) {
            for (Assignment a : assignmentList) {
                if (a.hasStudent())
                    if (a.getStudent().getStudentNumber() == id && a.hasAdvisor())
                        return a.getAdvisor();
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
        return a.getChosenProposals().contains(p);
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

    public Proposal getFirstFreeProposal(Student student){
        for(Proposal p : applicationsList.get(student).getChosenProposals()){
            if(!p.hasAssignedStudent())
                return p;
        }

        return null;
    }

    public List<Student> getStudentWithBestClassification(){
        List<Student> students = new ArrayList<>();
        double bestClassification = -1;

        for(Student s : getStudents()){
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

    public void setConflictStudents(List<Student> conflictStudents) {
        this.conflictStudents = conflictStudents;
    }

    public void setConflictProposal(Proposal conflictProposal) {
        this.conflictProposal = conflictProposal;
    }

    public List<Student> getConflictStudents() {
        return conflictStudents;
    }

    public Proposal getConflictProposal() {
        return conflictProposal;
    }

    public List<Student> getStudentsWithSameProposal(List<Student> students, Proposal p){
        List<Student> studentsWithProposal = new ArrayList<>();

        for(Student s : students){
            if(applicationHasProposal(applicationsList.get(s),p))
                studentsWithProposal.add(s);
        }

        return studentsWithProposal;
    }

    public int getIndexofProposalInApplication(Proposal p, Student s){
        if(proposalExists(p.getId()) && studentExists(s.getStudentNumber())){
            if(applicationHasProposal(applicationsList.get(s),p)){
                return applicationsList.get(s).getChosenProposals().indexOf(p) + 1;
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

    public List<Proposal> getAvailableProposalsList(){
        List<Proposal> result = new ArrayList<>();

        for(Proposal p : getProposals()){
            if(!p.hasBeenAssigned() && !p.hasAssignedStudent()) // if the proposal hasn't been assigned yet... adds its key to the array
                result.add(p);
        }
        return result;
    }

    public String[] getAvailableProposals(){
        List<String> result = new ArrayList<>();

        for(Map.Entry<String, Proposal> entry : proposalsList.entrySet()){
            if(!entry.getValue().hasBeenAssigned() || !entry.getValue().hasAssignedStudent()) // if the proposal hasn't been assigned yet... adds its key to the array
                result.add(entry.getKey());
        }
        return result.toArray(new String[result.size()]);
    }

    public String[] getAvailableStudents() {
        List<String> result = new ArrayList<>();

        for(Map.Entry<Long, Student> entry : studentsList.entrySet()){
            if(!entry.getValue().hasBeenAssigned()) // if the proposal hasn't been assigned yet... adds its key to the array
                result.add(entry.getKey().toString());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getAssignmentsWithoutAdvisor() {
        List<String> result = new ArrayList<>();

        for (Assignment a : assignmentList){
            if(a.getAdvisor() == null)
                result.add(a.toString());
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getAvailableAdvisors() {
        List<String> result = new ArrayList<>();

        for (Teacher t : teachersList.values()){
            if(isAvailable(t))
                result.add(t.teacherToString());
        }

        return result.toArray(new String[result.size()]);
    }

    public boolean isAvailable(Teacher teacher){
        for(Assignment a : assignmentList){
            if(a.getAdvisor()==teacher)
                return false;
        }
        return true;
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

    public List<Student> getStudents() {
        List<Student> retLst = new ArrayList<>();
        for(Student s : getStudentsValues()){
            try{
                retLst.add((Student) s.clone());

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return retLst;
    }

    public List<Teacher> getTeachers() {
        List<Teacher> retLst = new ArrayList<>();
        for(Teacher t : getTeachersValues()){
            try{
                retLst.add((Teacher) t.clone());

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return retLst;
    }

    public List<Proposal> getProposals() {
        List<Proposal> retLst = new ArrayList<>();
        for(Proposal p : getProposalsValues()){
            try{
                retLst.add((Proposal) p.clone());

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return retLst;
    }

    public List<Application> getApplications() {
        List<Application> retLst = new ArrayList<>();
        for(Application a : getApplicationsValues()){
            try{
                retLst.add((Application) a.clone());

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return retLst;
    }

    public List<Student> getStudentsSelfProposals(){
        List<Student> retLst = new ArrayList<>();

        for (Student s : getStudents()) {
            if(s.hasProposed()) {
                try {
                    retLst.add((Student) s.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return retLst;
    }

    public List<Student> getStudentsWithApplication(){
        List<Student> retLst = new ArrayList<>();

        for (Student s : getStudents()) {
            if(s.hasApplication()) {
                try {
                    retLst.add((Student) s.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return retLst;
    }

    public List<Student> getStudentsWithoutApplication(){
        List<Student> retLst = new ArrayList<>();

        for (Student s : getStudents()) {
            if(!s.hasApplication()) {
                try {
                    retLst.add((Student) s.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return retLst;
    }

    public List<Proposal> getProposalsWithFiltersStageTwo() {
        Predicate<Proposal> bySelfProposals = proposal -> proposal instanceof SelfProposal;
        Predicate<Proposal> byTeacherProposals = proposal -> proposal instanceof Project;
        Predicate<Proposal> byProposalInApplication = proposal -> applicationHasProposal(proposal);
        Predicate<Proposal> byProposalNotInApplication = proposal -> applicationHasProposal(proposal) == false;

        List<Proposal> results = new ArrayList();
        List<Proposal> retLst = new ArrayList();

        results.addAll(getProposalsValues());
        for(int i = 0; i < filtersStageTwo.length; i++){
            if(filtersStageTwo[i]) {
                switch (i) {
                    case 0 -> results = results.stream().filter(bySelfProposals).collect(Collectors.toList());
                    case 1 -> results = results.stream().filter(byTeacherProposals).collect(Collectors.toList());
                    case 2 -> results = results.stream().filter(byProposalInApplication).collect(Collectors.toList());
                    case 3 -> results = results.stream().filter(byProposalNotInApplication).collect(Collectors.toList());
                }
            }
        }

        for(Proposal p : results) {
            try {
                retLst.add((Proposal) p.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        return retLst;
    }

    public List<Proposal> getProposalsWithFiltersStageThree() {
        Predicate<Proposal> bySelfProposals = proposal -> proposal instanceof SelfProposal;
        Predicate<Proposal> byTeacherProposals = proposal -> proposal instanceof Project;
        Predicate<Proposal> byProposalUnassigned = proposal -> proposal.hasBeenAssigned() == false;
        Predicate<Proposal> byProposalAssigned = proposal -> proposal.hasBeenAssigned();

        List<Proposal> results = new ArrayList();
        List<Proposal> retLst = new ArrayList();

        results.addAll(getProposalsValues());
        for(int i = 0; i < filtersStageThree.length; i++){
            if (filtersStageThree[i]){
                switch (i) {
                    case 0 -> results = results.stream().filter(bySelfProposals).collect(Collectors.toList());
                    case 1 -> results = results.stream().filter(byTeacherProposals).collect(Collectors.toList());
                    case 2 -> results = results.stream().filter(byProposalUnassigned).collect(Collectors.toList());
                    case 3 -> results = results.stream().filter(byProposalAssigned).collect(Collectors.toList());
                }
            }
        }

        for(Proposal p : results) {
            try {
                retLst.add((Proposal) p.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return retLst;
    }

    public String[] getStudentsAssigned(){
        List <String> result = new ArrayList<>();
        StringBuilder sb;

        for (Student s : getStudents()) {
            sb = new StringBuilder();
            if(s.hasBeenAssigned()) {
                sb.append(s.studentToString());
                Proposal p = getProposalByStudent(s.getStudentNumber()); // gets the proposal assigned to the student
                if(p instanceof SelfProposal)
                    sb.append("-> Preference order: 1");
                else
                    sb.append("-> Preference order: ").append(getApplicationByStudent(s).getIndexProposal(p));

                result.add(sb.toString());
            }
        }
        return result.toArray(new String[result.size()]);
    }

    public String[] getListStudentsUnassigned(){
        List <String> result = new ArrayList<>();

        for (Student s : getStudents()) {
            if(!s.hasBeenAssigned()) {
                result.add(s.studentToString());
            }
        }
        return result.toArray(new String[result.size()]);
    }

//    public String[] getAvailableStudentsWithoutApplication() {
//        List<String> result = new ArrayList<>();
//
//        for(Map.Entry<Long, Student> entry : studentsList.entrySet()){
//            if(!entry.getValue().hasBeenAssigned() && !entry.getValue().hasApplication()) // if the proposal hasn't been assigned yet... adds its key to the array
//                result.add(entry.getKey().toString());
//        }
//
//        return result.toArray(new String[result.size()]);
//    }

    public List<Student> getAvailableStudentsWithoutApplication() {
        List<Student> result = new ArrayList<>();

        for (Student s : getStudents()){
            if (!s.hasBeenAssigned() && !s.hasApplication() && !s.hasProposed())
                result.add(s);
        }
        return result;
    }

    public boolean filenameIsValid(String filename) {
        String[] fn = filename.split("\\.");

        if(fn.length > 1)
            return false;

        return true;
    }

    public boolean emailIsValid(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(regex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean proposalIdIsValid(String id) {
        String regex = "[a-zA-Z]\\d\\d\\d";

        Pattern pat = Pattern.compile(regex);
        if (regex == null)
            return false;
        return pat.matcher(id).matches();
    }

    public void setFiltersStageTwo(boolean[] filters) { this.filtersStageTwo = filters; }

    public void setFiltersStageThree(boolean[] filters) { this.filtersStageThree = filters; }

    public boolean deleteStudent(long studentNumber) {
        Student s = studentsList.remove(studentNumber);
        if (s != null){
            Proposal p = getProposalByStudent(s.getStudentNumber());
            if (p!=null){
                if(p instanceof SelfProposal)
                    deleteProposal(p.getId());
                else
                    p.setAssignedStudent(null);
            }
            numberStudentsAndProposals.get(s.getBranch()).decrementNmrStudents();
            return true;
        }
        return false;
    }

    public void updateStudent(long studentNumber, String name, String email, String course, String branch, double classification, boolean internshipAccess) {
        studentsList.put(studentNumber, new Student(studentNumber, name, email, course, branch, classification, internshipAccess));
    }

    public String[] getBranches() {return branches;}

    public List<Student> getStudentsForInternships() {
        List<Student> students = new ArrayList<>();

        for (Student student : getStudents()){
            if(student.hasInternshipAccess() && !student.hasBeenAssigned() && !student.hasProposed() && !student.isAssociatedWithProposal())
                students.add(student);
        }
        return students;
    }

    public List<Student> getStudentsUnassigned() {
        List<Student> students = new ArrayList<>();

        for (Student student : getStudents()){
            if(!student.hasBeenAssigned() && !student.hasProposed() && !student.isAssociatedWithProposal())
                students.add(student);
        }
        return students;
    }

    public List<Student> getStudentsWithoutProposal() {
        List<Student> students = new ArrayList<>();

        for (Student student : getStudents()){
            if(!student.hasProposed() && !student.hasBeenAssigned() && !student.isAssociatedWithProposal())
                students.add(student);
        }
        return students;
    }

    public boolean deleteTeacher(String email) {

        Teacher t = teachersList.remove(email);
        if (t != null){
            Proposal p = getProposalByTeacher(t.getEmail());
            if (p!=null)
                if(p instanceof Project)
                    deleteProposal(p.getId());
            return true;
        }
        return false;
    }

    public void updateTeacher(String email, String name) {
        teachersList.put(email, new Teacher(email, name));
    }

    public boolean deleteProposal(String id) {
        if(getProposal(id) instanceof Project){
            getTeacherByProposal(id).setIsAdvisor(true);
        }

        Student s = getStudentByProposal(id);
        if (s != null)
            s.setHasProposed(false);

        Proposal p = proposalsList.remove(id);
        if (p == null)
            return false;

        if (p instanceof Project)
            for(String b : ((Project) p).getDestinedBranch())
                numberStudentsAndProposals.get(b).decrementNmrProposals();
        else if (p instanceof Internship)
            for(String b : ((Internship) p).getDestinedBranch())
                numberStudentsAndProposals.get(b).decrementNmrProposals();
        else if (p instanceof SelfProposal)
            numberStudentsAndProposals.get(p.getAssignedStudent().getBranch()).decrementNmrProposals();
        return true;
    }

    public Student getStudentByProposal(String id) {
        if (proposalExists(id)) {
            return getProposal(id).getAssignedStudent();
        }
        return null;
    }

    public Teacher getTeacherByProposal(String id) {
        if (proposalExists(id)) {
            Proposal p = getProposal(id);
            if (p instanceof Project)
                return ((Project) p).getProposingTeacher();
        }
        return null;
    }

    public boolean deleteApplication(long studentNumber) {
        Student s = getStudent(studentNumber);
        if(applicationsList.remove(s)!=null) {
            s.setHasApplication(false);
            return true;
        }
        return false;
    }

    public void updateApplication(Student student, List<Proposal> chosenProposals) {
        applicationsList.put(student, new Application(chosenProposals, student));
        studentsList.get(student.getStudentNumber()).setHasApplication(true);
    }
//
//    public void setStudentHasProposedByProposal(String id, boolean b) {
//        if (proposalExists(id)){
//            getProposal(id).setStudentHasProposed(b);
//        }
//    }
//
//    public void setTeacherIsAdvisorByProposal(String id, boolean b) {
//        if (proposalExists(id)){
//            Proposal p = getProposal(id);
//            if (p instanceof Project)
//                ((Project) p).setTeacherIsAdvisor(b);
//        }
//    }

    /*public boolean manuallyAssign(int proposalChosen, int studentChosen, String[] availableProposals, String[] availableStudents) {
        Proposal pToAssign = proposalsList.get(availableProposals[proposalChosen]);
        Student sToAssign = studentsList.get(availableStudents[studentChosen]);
        assignmentList.add(new Assignment(sToAssign, pToAssign));
        pToAssign.setHasBeenAssigned(true);
        sToAssign.setHasBeenAssigned(true);
    }*/

    private class Wrapper implements Serializable{    // to be used on 'numberStudents' hashmap
        static final long serialVersionUID = 100L;
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
        public void decrementNmrProposals() { nmrProposals--; }
        public void decrementNmrStudents() { nmrStudents--; }

    }
}
