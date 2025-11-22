import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;

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

        while(!valid){
            try{
                System.out.println("Enter name of path file to scan for patient data: ");
                fileName = scanner.nextLine();
                try(FileInputStream patientData = new FileInputStream(String.format("%s.txt", fileName))){
                    valid = true;
                }catch(IOException e){

                }
            }catch(InputMismatchException e){
                System.out.println("Error: input was not a string");
                System.out.println(e);
            }
        }

        System.out.println("Patient data successfully parsed from " + fileName + ".txt");

        while(option != 8){
            try{
                System.out.println("MENU:\n(1) Display List\n(2) Add a new patient\n(3) Show information for a patient" +
                        "\n(4) Delete a patient\n(5) Show average patient age\n(6) Show information for the youngest patient\n" +
                        "(7) Show notification list\n(8) Quit");
                option = scanner.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Error: input was not an integer");
                System.out.println(e);
            }
            if(option < 1 || option > 8){
                System.out.println("Please enter an option from (1) to (8)");
            }else{
                switch(option){
                    case 1:
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
}
