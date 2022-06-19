package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.data.*;
import pt.isec.pa.apoio_poe.model.fsm.AppContext;
import pt.isec.pa.apoio_poe.model.fsm.AppState;
import pt.isec.pa.apoio_poe.model.fsm.ListingType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.List;

public class Manager {
    public static final String STATE = "state";
    public static final String DATA = "data";
    public static final String LISTING = "listing";

    AppContext ac;
    DataLogic dl;
    PropertyChangeSupport pcs;

    ListingType listingType;

    public Manager(){
        dl = new DataLogic();
        ac = new AppContext(dl);
        pcs = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener){
        pcs.addPropertyChangeListener(property, listener);
    }

    public void setListingType(ListingType listingType) {
        this.listingType = listingType;
        pcs.firePropertyChange(LISTING, null, null);
    }

    public ListingType getListingType() {
        return listingType;
    }

    public AppState getState() {
        return ac.getState();
    }

    public String getStage(){return ac.getStage();}

    public boolean boolImportCSV(String filename) { return ac.boolImportCSV(filename); }

    public boolean boolExportCSV(String filename) { return ac.boolExportCSV(filename); }

    public String importCSV(String filename) { return ac.importCSV(filename); }

    public String exportCSV(String filename) { return ac.exportCSV(filename); }

    public void changeConfigurationMode(int option){
        ac.changeConfigurationMode(option);
        pcs.firePropertyChange(STATE, null, null);
    }
    public boolean closeStage() {
        pcs.firePropertyChange(STATE, null, null);
        return ac.closeStage();
    }

    public void advanceStage() {
        ac.advanceStage();
        this.listingType = ListingType.NONE;
        pcs.firePropertyChange(STATE, null, null);
        pcs.firePropertyChange(LISTING, null, null);
    }

    public List<Student> getStudents(){return ac.getStudents();}
    public List<Teacher> getTeachers(){return ac.getTeachers();}
    public List<Proposal> getProposals(){return ac.getProposals();}
    public List<Application> getApplications(){return ac.getApplications();}
    public List<Student> getStudentsSelfProposals() {return ac.getStudentsSelfProposals();}
    public List<Student> getStudentsWithApplication() {return ac.getStudentsWithApplication();}
    public List<Student> getStudentsWithoutApplication() {return ac.getStudentsWithoutApplication();}
    public List<Proposal> getProposalsWithFiltersStageTwo() {return ac.getProposalsWithFiltersStageTwo();}
    public List<Proposal> getProposalsWithFiltersStageThree() {return ac.getProposalsWithFiltersStageThree();}
    public String[] getStudentsAssigned() {return ac.getStudentsAssigned();}
    public String[] getListStudentsUnassigned() {return ac.getListStudentsUnassigned();}

    public boolean returnStage() {
        if (ac.returnStage()) {
            this.listingType = ListingType.NONE;
            pcs.firePropertyChange(STATE, null, null);
            pcs.firePropertyChange(LISTING, null, null);
            return true;
        }
        return false;
    }
    public String viewStudents() { return ac.viewStudents(); }
    public String viewTeachers() { return ac.viewTeachers(); }
    public String viewProposals() { return ac.viewProposals(); }
    public String viewStudentsSelfProposals() { return ac.viewStudentsSelfProposals(); }
    public String viewStudentsWithApplication() { return ac.viewStudentsWithApplication(); }
    public String viewStudentsWithoutApplication() { return ac.viewStudentsWithoutApplication(); }
    public String[] viewAssignments() { return ac.viewAssignments(); }

    public String filterProposals(Integer... filters) { return ac.filterProposals(filters); }

    public boolean automaticAssignmentSelfProposals() { return ac.automaticAssignmentSelfProposals(); }
    public List<Proposal> getAvailableProposalsList() { return ac.getAvailableProposalsList(); }
    public String[] getAvailableProposals() { return ac.getAvailableProposals(); }
    public String[] getAvailableStudents() { return ac.getAvailableStudents(); }
    public List<Student> getAvailableStudentsWithoutApplication() { return ac.getAvailableStudentsWithoutApplication(); }
    public boolean manuallyAssign(int proposalChosen, int studentChosen, String[] availableProposals, String[] availableStudents) { return ac.manuallyAssign(proposalChosen, studentChosen, availableProposals, availableStudents); }

    public boolean removeAssignment(int assignmentToRemove) { return ac.removeAssignment(assignmentToRemove);}
    public boolean removeAllAssignments() { return ac.removeAllAssignments(); }

    public String viewStudentsAssigned() { return ac.viewStudentsAssigned(); }
    public String viewStudentsUnassigned() { return ac.viewStudentsUnassigned(); }
    public boolean automaticAssignment() { return ac.automaticAssignment();}
    public String[] getConflictedCases(){ return ac.getConflictedCases(); }
    public String getConflictedProposal() { return ac.getConflictedProposal(); }
    public boolean resolveConflictedCases(int option) { return ac.resolveConflictedCases(option); }

    public boolean automaticAssignmentAdvisors() { return ac.automaticAssignmentAdvisors(); }

    public String viewStudentsAssignedWithAdvisor() { return ac.viewStudentsAssignedWithAdvisor(); }
    public String viewStudentsAssignedWithoutAdvisor() { return ac.viewStudentsAssignedWithoutAdvisor(); }
    public String viewStudentsUnassignedWithApplications() { return ac.viewStudentsUnassignedWithApplications(); }
    public String viewProposalsUnassigned() { return ac.viewProposalsUnassigned(); }
    public String viewProposalsAssigned() { return ac.viewProposalsAssigned(); }

    public void save(String filename){
        try(ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(filename)))
        {
            oos.writeObject(ac);
        } catch (Exception e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public void save(File hFile){
        try(ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(hFile)))
        {
            oos.writeObject(ac);
        } catch (Exception e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadAppContext(String filename){
        AppContext appContext = load(filename);
        if(appContext!=null) {
            ac = appContext;
            ac.changeState(AppState.values()[ac.getCurrentState()].createState(ac, ac.getDl()));
        }
    }

    private AppContext load(String filename) {
        try(ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(filename)))
        {
            return (AppContext) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
        return null;
    }

    public void load(File hFile){
        AppContext appContext = _load(hFile);
        if(appContext!=null) {
            this.ac = appContext;
            this.ac.changeState(AppState.values()[this.ac.getCurrentState()].createState(ac, ac.getDl()));
            System.out.println(getState());
        }
        pcs.firePropertyChange(DATA, null, null);
        pcs.firePropertyChange(STATE, null, null);
        pcs.firePropertyChange(LISTING, null, null);
    }

    private AppContext _load(File hFile) {
        try(ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(hFile)))
        {
            return (AppContext) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
        return null;
    }

    public boolean add(Object ... parameters) {
        if (ac.add(parameters)){
            pcs.firePropertyChange(DATA, null, null);
            return true;
        }
        return false;
    }

    public void setFiltersStageTwo(boolean[] filters){
        ac.setFiltersStageTwo(filters);
    }

    public void setFiltersStageThree(boolean[] filters){
        ac.setFiltersStageThree(filters);
    }

    public boolean delete(Object selectedItem) {
        if (ac.delete(selectedItem)) {
            pcs.firePropertyChange(DATA, null, null);
            return true;
        }
        return false;
    }

    public boolean update(Object ... parameters) {
        if (ac.update(parameters)){
            pcs.firePropertyChange(DATA, null, null);
            return true;
        }
        return false;
    }

    public String[] getBranches() {return ac.getBranches();}

    public List<Student> getStudentsForInternships() {return ac.getStudentsForInternships();}
    public List<Student> getStudentsUnassigned() {return ac.getStudentsUnassigned();}
    public List<Student> getStudentsWithoutProposal(){return ac.getStudentsWithoutProposal();}

    public boolean isStageClosed(String stage){ return ac.isStageClosed(stage); }
}
