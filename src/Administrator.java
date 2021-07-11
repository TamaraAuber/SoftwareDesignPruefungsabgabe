import java.util.Scanner;

public class Administrator {

    private String username = "Batman";
    private String password = "NaNaNa!";

    public Administrator() {

    }

    // ToDo: Bug wenn im Benutzernamen Leerzeichen sind
    public void logIn() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Benutzername: ");
        String enteredUsername = scan.next();

        System.out.print("Passwort: ");
        String enteredPassword = scan.next();

        if (validateLogInData(enteredUsername, enteredPassword)) {
            System.out.println("Welcome to the Batcave Mr. Wayne");
            adminOptions();
        } else {
            System.out.println("Activated Selfdestruction. Try again");
            logIn();
        }

        scan.close();
    }

    private boolean validateLogInData(String _username, String _password) {
        if (username.equals(_username) && password.equals(_password)) {
            return true;
        }
        return false;
    }

    public void adminOptions() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Wählen sie ihren nächsten Schritt");

        System.out.println("1) Übersicht Termine");
        System.out.println("2) neuen Termin anlegen");
        System.out.println("3) Statistik ansehen");

        int selectedOption = scan.nextInt();

        switch(selectedOption) {
            case 1:
            Appointment AppointmentCase1 = new Appointment();
            AppointmentCase1.showAllAppoitments();;
            break;
            case 2:
            Appointment AppointmentCase2 = new Appointment();
            AppointmentCase2.createNewAppointment();
            break;
            case 3:
            System.out.println("show me you stats");
            break;
        }

        scan.close();
    }

    
}
