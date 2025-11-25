import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestPatient {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        String fileName = scanner.nextLine();

    }
    public void run(){
        Scanner scanner = new Scanner(System.in);
        List<Patient> patientList = new List<Patient>();
        boolean valid = false;
        String fileName = "";
        int option = 0;

        //asks user for name of file containing patient data
        while(!valid){
            try{
                System.out.println("Enter name of path file to scan for patient data (include file extension type): ");
                fileName = scanner.nextLine();
                valid = true;
            }catch(InputMismatchException e){
                System.out.println("Error: input was not a string");
                System.out.println(e);
            }
        }

        //parses patient data from fileName
        loadPatientData(fileName, patientList);
        System.out.println("Patient data successfully parsed from " + fileName);

        //menu to manipulate newly parsed patient data
        while(option != 8){
            try{
                System.out.println("MENU:\n(1) Display List\n(2) Add a new patient\n(3) Show information for a patient" +
                        "\n(4) Delete a patient\n(5) Show average patient age\n(6) Show information for the youngest patient\n" +
                        "(7) Show notification list\n(8) Quit");
                option = scanner.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Error: input was not an integer");
                System.out.println(e);
                continue;
            }
            if(option < 1 || option > 8){
                System.out.println("Please enter an option from (1) to (8)");
            }else{
                switch(option){
                    case 1: //
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;

                }
            }

        }
    }

    //reads patient data from file, then inputs into linked list of patients
    //pre-conditions: input file is well-formed (each field for a patient on a new line, data for
    //each patient can be separated by a line (not required))
    //patient data on each line follows the order of the Patient constructor
    public static void loadPatientData(String file, List<Patient> patientList){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        boolean duplicate = false;
        try{
            Scanner scanner = new Scanner(new File(file));
            while(scanner.hasNextLine()){
                String name = scanner.nextLine().trim();
                if(name.isEmpty()){ //if there is a separator, go to next iteration
                    continue;
                }
                String id = scanner.nextLine().trim();
                String address = scanner.nextLine().trim();
                int height = Integer.parseInt(scanner.nextLine().trim());
                double weight = Double.parseDouble(scanner.nextLine().trim());
                Date dateOfBirth = simpleDateFormat.parse(scanner.nextLine().trim());
                Date initialVisit = simpleDateFormat.parse(scanner.nextLine().trim());
                Date lastVisit = simpleDateFormat.parse(scanner.nextLine().trim());

                //check if duplicate before adding to patientList
                patientList.reset();
                Patient currentPatient = patientList.getNext();
                while(currentPatient != null){
                    if(currentPatient.getPatientID().equals(id)){
                        System.out.println("DUPLICATE ID FOUND: " + id + ", patient won't be added to patientList");
                        duplicate = true;
                        break;
                    }
                    currentPatient = patientList.getNext();
                }
                if(duplicate){
                    continue;
                }

                //add patient if not duplicate
                Patient patient = new Patient(name, id, address, height, weight, dateOfBirth, initialVisit, lastVisit);
                patientList.add(patient);
            }
        }catch(FileNotFoundException e){
            System.out.println("Error: file not found");
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }

    }
}
