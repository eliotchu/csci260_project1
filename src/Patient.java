import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Patient{
    //instance variables
    private String name;
    private final String patientID; //final variable to store unique IDs
    private static int idCounter = 1; //static counter to assign patients contiguous and unique IDs
    private int width; //dynamic number of leading zeroes in ID
    private String address;
    private int height; //measured in inches
    private double weight;
    private Date dateOfBirth;
    private Date dateOfInitialVisit;
    private Date dateOfLastVisit;

    //constructor to initialize a patient's details
    protected Patient(String name, String address, int height, double weight, Date dateOfBirth, Date dateOfInitialVisit, Date dateOfLastVisit){
        this.name = name;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.dateOfBirth = dateOfBirth;
        this.dateOfInitialVisit = dateOfInitialVisit;
        this.dateOfLastVisit = dateOfLastVisit;
        width = 8 - String.valueOf(idCounter).length();
        patientID = String.format("PT%0" + width + "d", idCounter);
        idCounter++;
    }

    //getter methods
    protected String getPatientID(){
        return patientID;
    }

    protected String getName(){
        return name;
    }

    protected String getAddress(){
        return address;
    }

    protected int getHeight(){
        return height;
    }

    protected double getWeight(){
        return weight;
    }

    protected Date getDateOfBirth(){
        return dateOfBirth;
    }

    protected Date getDateOfInitialVisit(){
        return dateOfInitialVisit;
    }

    protected Date getDateOfLastVisit(){
        return dateOfLastVisit;
    }

    //setter methods
    protected void setName(String name){
        this.name = name;
    }

    protected void setAddress(String address){
        this.address = address;
    }

    protected void setHeight(int height){
        this.height = height;
    }

    protected void setWeight(double weight){
        this.weight = weight;
    }

    protected void setDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    protected void setDateOfInitialVisit(Date dateOfInitialVisit){
        this.dateOfInitialVisit = dateOfInitialVisit;
    }

    protected void setDateOfLastVisit(Date dateOfLastVisit){
        this.dateOfLastVisit = dateOfLastVisit;
    }

    //other methods
    protected int getAge(){
        LocalDate today = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.ofInstant(this.dateOfBirth.toInstant(), ZoneId.systemDefault());
        return Period.between(dateOfBirth, today).getYears();
    }
}