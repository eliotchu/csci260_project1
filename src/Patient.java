import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;


//store patient data
public class Patient{
    //instance variables
    private String name;
    private final String patientID; //final variable so id cannot be changed
    private String address;
    private int height; //measured in inches
    private double weight;
    private Date dateOfBirth;
    private Date dateOfInitialVisit;
    private Date dateOfLastVisit;

    //constructor to initialize a patient's details
    public Patient(String name, String patientID, String address, int height, double weight,
                      Date dateOfBirth, Date dateOfInitialVisit, Date dateOfLastVisit){
        this.name = name;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.dateOfBirth = dateOfBirth;
        this.dateOfInitialVisit = dateOfInitialVisit;
        this.dateOfLastVisit = dateOfLastVisit;
        this.patientID = patientID;
    }


    //equals method override to check if two Patients have the same ID. Overrides equals() used
    //find() method in List<T> class if T is Patient
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Patient)){
            return false;
        }
        Patient patient = (Patient) obj;
        return this.patientID.equals(patient.patientID);
    }

    @Override
    public String toString(){
        return name + ", " + patientID;
    }

    //getter methods
    public String getPatientID(){
        return patientID;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public int getHeight(){
        return height;
    }

    public double getWeight(){
        return weight;
    }

    public Date getDateOfBirth(){
        return dateOfBirth;
    }

    public Date getDateOfInitialVisit(){
        return dateOfInitialVisit;
    }

    public Date getDateOfLastVisit(){
        return dateOfLastVisit;
    }

    //setter methods
    public void setName(String name){
        this.name = name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public void setDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfInitialVisit(Date dateOfInitialVisit){
        this.dateOfInitialVisit = dateOfInitialVisit;
    }

    public void setDateOfLastVisit(Date dateOfLastVisit){
        this.dateOfLastVisit = dateOfLastVisit;
    }

    //calculates a patient's age in years
    public int getAge(){
        LocalDate today = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.ofInstant(this.dateOfBirth.toInstant(), ZoneId.systemDefault());
        return Period.between(dateOfBirth, today).getYears();
    }

    //calculates how long a patient has been visiting from the first visit to today in years
    public int getTimeAsPatient(){
        LocalDate initialVisit = LocalDate.ofInstant(this.dateOfInitialVisit.toInstant(), ZoneId.systemDefault());
        LocalDate today = LocalDate.now();
        return Period.between(initialVisit, today).getYears();
    }

    //calculates how long ago from a patient's last visit to today in years
    public int getTimeSinceLastVisit(){
        LocalDate today = LocalDate.now();
        LocalDate lastVisit = LocalDate.ofInstant(this.dateOfLastVisit.toInstant(), ZoneId.systemDefault());
        return Period.between(lastVisit, today).getYears();
    }

}




