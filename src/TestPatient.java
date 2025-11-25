import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestPatient {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public static void main(String[] args){
        new TestPatient().run();

    }

    //runs the menu-user program
    public void run(){
        Scanner scanner = new Scanner(System.in);
        List<Patient> patientList = new List<Patient>();
        boolean valid = false;
        String fileName = "";
        int option = 0;

        while(!valid){
            System.out.println("Enter name of path file to scan for patient data (include file extension type): ");
            fileName = scanner.nextLine();
            File file = new File(fileName);
            try(Scanner fileScan = new Scanner(file)){
                valid = true;
            }catch(FileNotFoundException e){
                System.out.println("Error: File not found, try again");
            }
        }

        loadPatientData(fileName, patientList);
        System.out.println("Patient data successfully parsed from " + fileName);

        while(option != 8){
            try{
                System.out.println("MENU:\n(1) Display List\n(2) Add a new patient\n(3) Show information for a patient" +
                        "\n(4) Delete a patient\n(5) Show average patient age\n(6) Show information for the youngest patient\n" +
                        "(7) Show notification list\n(8) Quit");
                option = scanner.nextInt();
                scanner.nextLine();
            }catch(InputMismatchException e){
                System.out.println("Error: input was not an integer");
                System.out.println(e);
                scanner.nextLine();
                continue;
            }

            if(option < 1 || option > 8){
                System.out.println("Please enter an option from (1) to (8)");
            }else{
                switch(option){
                    case 1:
                        printNameId(patientList);
                        break;
                    case 2:
                        addPatient(scanner, patientList);
                        break;
                    case 3:
                        printPatientById(scanner, patientList);
                        break;
                    case 4:
                        deletePatientById(scanner, patientList);
                        break;
                    case 5:
                        showAverageAge(patientList);
                        break;
                    case 6:
                        printYoungestPatient(patientList);
                        break;
                    case 7:
                        printOverduePatients(patientList);
                        break;
                    case 8:
                        quit(scanner, patientList);
                        break;
                }
            }
        }
    }

    //reads patient data from file, then inputs into linked list of patients
    //pre-conditions: input file is well-formed (each field for a patient on a new line, data for
    //each patient can be separated by a line (not required))
    //patient data on each line follows the order of the Patient constructor
    public static void loadPatientData(String file, List<Patient> list){
        try(Scanner scanner = new Scanner(new File(file))){
            while(true){
                //read name skipping blank separators
                String name = null;
                while(scanner.hasNextLine()){
                    name = scanner.nextLine().trim();
                    if(!name.isEmpty()) break;
                }
                if(name == null || name.isEmpty()) break; // EOF

                // read remaining 7 lines (we assume well-formed file per assignment)
                if(!scanner.hasNextLine()) break;
                String id = scanner.nextLine().trim();

                if(!scanner.hasNextLine()) break;
                String address = scanner.nextLine().trim();

                if(!scanner.hasNextLine()) break;
                int height = Integer.parseInt(scanner.nextLine().trim());

                if(!scanner.hasNextLine()) break;
                double weight = Double.parseDouble(scanner.nextLine().trim());

                if(!scanner.hasNextLine()) break;
                Date dateOfBirth = simpleDateFormat.parse(scanner.nextLine().trim());

                if(!scanner.hasNextLine()) break;
                Date initialVisit = simpleDateFormat.parse(scanner.nextLine().trim());

                if(!scanner.hasNextLine()) break;
                Date lastVisit = simpleDateFormat.parse(scanner.nextLine().trim());

                //check duplicate ID using list iterator (reset() and getNext())
                if(existsById(list, id)){
                    System.out.println("DUPLICATE ID FOUND (skipping): " + id);
                    continue;
                }

                Patient p = new Patient(name, id, address, height, weight, dateOfBirth, initialVisit, lastVisit);
                list.add(p);
            }
        }catch(FileNotFoundException e){
            System.out.println("Error: file not found");
        }catch(ParseException e) {
            System.out.println("Invalid date format. Use MM/dd/yyyy.");
        }catch(NumberFormatException e){
            System.out.println("Error: invalid numeric input");
        }

    }

    //menu methods

    //option 1: display name and patient id #'s of all patients in order
    public static void printNameId(List<Patient> list){
        if(list.isEmpty()){
            System.out.println("List is empty");
            return;
        }
        list.reset();
        Patient current = list.getNext();
        while(current != null){
            System.out.println(current.getName() + ", " + current.getPatientID());
            current = list.getNext();
        }
    }

    //option 2: Add a new patient to the end of the list
    public static void addPatient(Scanner scanner, List<Patient> list){
        try {
            System.out.print("Enter patient id: ");
            String id = scanner.nextLine().trim();
            if(id.isEmpty()){
                System.out.println("ID cannot be blank.");
                return;
            }
            if(existsById(list, id)){
                System.out.println("ID already exists");
                return;
            }

            System.out.print("Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Address: ");
            String address = scanner.nextLine().trim();
            System.out.print("Height (inches): ");
            int height = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Weight (double): ");
            double weight = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Date of birth (MM/dd/yyyy): ");
            Date dateOfBirth = simpleDateFormat.parse(scanner.nextLine().trim());
            System.out.print("Date of initial visit (MM/dd/yyyy): ");
            Date initialVisit = simpleDateFormat.parse(scanner.nextLine().trim());
            System.out.print("Date of last visit (MM/dd/yyyy): ");
            Date lastVisit = simpleDateFormat.parse(scanner.nextLine().trim());
            Patient p = new Patient(name, id, address, height, weight, dateOfBirth, initialVisit, lastVisit);
            list.add(p);
            System.out.println("Patient added: " + name + ", " + id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use MM/dd/yyyy.");
        }

    }

    //option 3: print all info for specified patient id
    public static void printPatientById(Scanner scanner, List<Patient> list){
        System.out.print("Enter patient id: ");
        String id = scanner.nextLine().trim();
        Patient p = findById(list, id);
        if(p == null){
            System.out.println("Patient not found");
            return;
        }
        printPatientInfo(p);
    }

    //option 4: delete patient by id
    public static void deletePatientById(Scanner scanner, List<Patient> list){
        System.out.print("Enter patient id: ");
        String id = scanner.nextLine().trim();
        Patient p = findById(list, id);
        if(p == null){
            System.out.println("Patient not found, nothing deleted");
            return;
        }
        try{
            list.remove(p);
            System.out.println("Patient deleted: " + id);
        }catch(ListUnderFlowException e){
            System.out.println("List is empty, delete cannot be done");
        }
    }

    //option 5: show average age of all patients (rounded to 1 decimal place)
    public static void showAverageAge(List<Patient> list){
        if(list.isEmpty()){
            System.out.println("List is empty");
            return;
        }
        list.reset();
        Patient current = list.getNext();
        int count = 0;
        double sum = 0.0;
        while(current != null){
            sum += current.getAge();
            count++;
            current = list.getNext();
        }
        System.out.printf("Average age: %.1f years%n", sum/count);
    }

    //option 6: display information on the youngest patient
    public static void printYoungestPatient(List<Patient> list){
        if(list.isEmpty()){
            System.out.println("List is empty");
            return;
        }
        list.reset();
        Patient current = list.getNext();
        Patient youngest = current;
        while(current != null){
            if(current.getAge()<youngest.getAge()){
                youngest = current;
            }
            current = list.getNext();
        }
        System.out.println("Youngest patient:");
        printPatientInfo(youngest);
    }

    //option 7: display all patients who are overdue for a visit (>= 3 years)
    public static void printOverduePatients(List<Patient> list){
        if(list.isEmpty()){
            System.out.println("List is empty");
            return;
        }
        System.out.println("Overdue patient list:");
        list.reset();
        Patient current = list.getNext();
        boolean overdue = false;
        while(current != null){
            if(current.getTimeSinceLastVisit() >= 3){
                System.out.println(current.getName() + ", " + current.getPatientID());
                overdue = true;
            }
            current = list.getNext();
        }
        if(!overdue){
            System.out.println("No overdue patients found");
        }
    }

    //option 8: quit program and ask user if they want to save
    public static void quit(Scanner scanner, List<Patient> list){
        System.out.print("Save patient info list to a file before quitting? (y/n)");
        String answer = scanner.nextLine().trim();
        if(answer.equalsIgnoreCase("y")){
            System.out.println("Enter output file name: ");
            String fileName = scanner.nextLine().trim();
            if(fileName.isEmpty()){
                System.out.println("Invalid file name. Save cancelled");
                return;
            }
            boolean save = savePatientsToFile(fileName, list);
            if(save){
                System.out.println("Successfully save to:  " + fileName);
            }
        }
        System.out.println("Quitting");
    }

    private static boolean savePatientsToFile(String filename, List<Patient> list) {
        try(PrintWriter pw = new PrintWriter(new File(filename))){
            list.reset();
            Patient p = list.getNext();
            while(p != null){
                pw.println(p.getName());
                pw.println(p.getPatientID());
                pw.println(p.getAddress());
                pw.println(Integer.toString(p.getHeight()));
                pw.println(Double.toString(p.getWeight()));
                pw.println(simpleDateFormat.format(p.getDateOfBirth()));
                pw.println(simpleDateFormat.format(p.getDateOfInitialVisit()));
                pw.println(simpleDateFormat.format(p.getDateOfLastVisit()));
                pw.println(); //space separator
                p = list.getNext();
            }
            return true;
        }catch (FileNotFoundException e){
            System.out.println("Error writing to file: " + filename);
            return false;
        }
    }

    //helper methods

    //find a patient by id using list reset() and getNext() iterator, similar to find()
    private static Patient findById(List<Patient> list, String id){
        Patient dummyData = new Patient("", id, "", 0,
                0.0, new Date(), new Date(), new Date());
        Node<Patient> node = list.find(dummyData);
        return (node == null ? null : node.getData());
    }

    //check existence by id, similar to contains()
    private static boolean existsById(List<Patient> list, String id){
        return findById(list, id) != null;
    }

    //print all patient info
    private static void printPatientInfo(Patient p){
        System.out.println("--------------------------------------");
        System.out.println("Name: " + p.getName());
        System.out.println("Patient ID: " + p.getPatientID());
        System.out.println("Address: " + p.getAddress());
        // height in feet/inches
        int feet = p.getHeight() / 12;
        int inch = p.getHeight() % 12;
        System.out.println("Height: " + feet + " ft, " + inch + " in");
        System.out.println("Weight: " + p.getWeight());
        System.out.println("Age: " + p.getAge() + " years");
        int yearsAsPatient = p.getTimeAsPatient();
        System.out.println("Years as patient: " + (yearsAsPatient == 0 ? "less than one year" : yearsAsPatient));
        int yearsSinceLast = p.getTimeSinceLastVisit();
        System.out.println("Years since last visit: " + (yearsSinceLast == 0 ? "less than one year" : yearsSinceLast));
        if (yearsSinceLast >= 3) {
            System.out.println("Patient is overdue for a visit (>= 3 years since last visit)");
        }
        System.out.println("--------------------------------------");
    }

}
