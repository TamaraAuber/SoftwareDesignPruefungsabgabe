import java.util.Scanner;
import org.json.simple.*;

public class Administrator {

    private String username = "Batman";
    private String password = "NaNaNa!";

    public Administrator() {

    }

    // ToDo: Bug wenn im Benutzernamen Leerzeichen sind
    public void logIn() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Benutzername: ");
        String enteredUsername = scan.nextLine();

        System.out.print("Passwort: ");
        String enteredPassword = scan.nextLine();

        if (validateLogInData(enteredUsername, enteredPassword)) {
            System.out.println("Welcome to the Batcave Mr. Wayne");
            adminLogIn();
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

    private void adminLogIn() {
        JSONHelper JSONFile = new JSONHelper();
        JSONObject dataList = JSONFile.getJSONFile("src/JSONFiles/DataList.json");
        try {
            // if Administrator is logged in, value is 1 => true, else value is 0 => false
            JSONFile.updateKeyValue("src/JSONFiles/DataList.json", "isAdminLoggedIn", 1);

        } catch (Exception e) {
            System.out.println("Faaaaaaaaaaaaaaaaaaail");
            dataList.put("isAdminLoggedIn", 0);
            JSONFile.updateDataList(dataList);
        }
    }

    public void adminLogOut() {
        JSONHelper JSONFile = new JSONHelper();
        JSONObject dataList = JSONFile.getJSONFile("src/JSONFiles/DataList.json");
        try {
            // if Administrator is logged in, value is 1 => true, else value is 0 => false
            JSONFile.updateKeyValue("src/JSONFiles/DataList.json", "isAdminLoggedIn", 0);

        } catch (Exception e) {
            System.out.println("Faaaaaaaaaaaaaaaaaaail");
            dataList.put("isAdminLoggedIn", 0);
            JSONFile.updateDataList(dataList);
        }
    }

    public void adminOptions() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Wählen sie ihren nächsten Schritt");

        System.out.println("1) Übersicht Termine");
        System.out.println("2) neuen Termin anlegen");
        System.out.println("3) Statistik ansehen");

        int selectedOption = scan.nextInt();

        switch (selectedOption) {
            case 1:
                Appointment AppointmentCase1 = new Appointment();
                AppointmentCase1.showAllAppoitments();
                ;
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
