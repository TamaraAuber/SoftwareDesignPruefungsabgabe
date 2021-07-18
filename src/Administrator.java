import java.util.Scanner;
import org.json.simple.*;

public class Administrator {

    private String username = "Admin";
    private String password = "VaccApp!";

    public Administrator() {

    }

    public void logIn() {
        Scanner scan = new Scanner(System.in);

        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("");

        System.out.print("Benutzername: ");
        String enteredUsername = scan.nextLine();

        System.out.print("Passwort: ");
        String enteredPassword = scan.nextLine();

        if (validateLogInData(enteredUsername, enteredPassword)) {
            adminLogIn();
            adminOptions();
        } else {
            System.out.println("Benutzername oder Passwort nicht korrekt. Versuchen Sie es erneut!");
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
            System.out.println("Fehler bei der Anmeldung!");
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
            System.out.println("Fehler bei LogOut!");
            dataList.put("isAdminLoggedIn", 0);
            JSONFile.updateDataList(dataList);
        }
    }

    public void adminOptions() {
        Scanner scan = new Scanner(System.in);

        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Wählen sie ihren nächsten Schritt");
        System.out.println("");

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
                Stats Stats = new Stats();
                Stats.showAllStats();
                break;
        }

        scan.close();
    }

}
