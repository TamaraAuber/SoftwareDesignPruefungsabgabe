import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class User {

    public User() {

    }

    public void userOptions() {
        Scanner scanInt = new Scanner(System.in);
        Scanner scanString = new Scanner(System.in);

        System.out.println("1) Übersicht aller Termine");
        System.out.println("2) nach Datum suchen");

        int selectedOption = scanInt.nextInt();

        switch (selectedOption) {
            case 1:
                System.out.println("alle Termine");
                break;
            case 2:
                System.out.println("Geben sie das gesuchte Datum ein:");
                String enteredDate = scanString.nextLine();
                LocalDate date =  LocalDate.parse(enteredDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                String searchedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                searchForDate(searchedDate);
                break;
        }

        scanInt.close();
        scanString.close();
    }

    public void registerForAppointment() {

    }

    public void registerForWaitingList() {

        try {
            JSONHelper JSONFile = new JSONHelper();
            JSONFile.enterRegitrationInWaitingList(enterRegistrationData());
        } catch (Exception e) {
            System.out.println("Irgendwas ist schiefgelaufen. Versuchen sie es erneut.");
            registerForWaitingList();
        }

    }

    private RegistrationModel enterRegistrationData() {
        Scanner scanString = new Scanner(System.in);
        Scanner scanInt = new Scanner(System.in);

        System.out.println("Bei der Registrierung bitte keine Sonderzeichen benutzen.");

        String enteredEMail = getEMailAdress(scanString);

        System.out.print("Vorname: ");
        String enteredFirstName = scanString.nextLine();

        System.out.print("Nachname: ");
        String enteredLastName = scanString.nextLine();

        System.out.print("Geburtsdatum (z.B. 01.01.1970): ");
        String enteredBirthDate = scanString.nextLine();

        System.out.print("Telefonnummer: ");
        int enteredPhoneNumber = scanInt.nextInt();

        System.out.println("Adresse:");

        System.out.print("Straße: ");
        String enteredStreet = scanString.nextLine();

        System.out.print("Hausnummer: ");
        int enteredHouseNumber = scanInt.nextInt();

        System.out.print("Postleitzahl: ");
        int enteredPostalCode = scanInt.nextInt();

        System.out.print("Stadt: ");
        String enteredCity = scanString.nextLine();

        ID_Generator Generator = ID_Generator.getInstance();
        int id = Generator.generateNewId();

        RegistrationModel Registration = new RegistrationModel(id, enteredEMail, enteredFirstName, enteredLastName,
                LocalDate.parse(enteredBirthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy")), enteredPhoneNumber,
                enteredStreet, enteredHouseNumber, enteredPostalCode, enteredCity);

        scanString.close();
        scanInt.close();

        return Registration;
    }

    private String getEMailAdress(Scanner _scan) {
        System.out.print("E-Mail: ");
        String enteredEMail = _scan.nextLine();

        JSONHelper Helper = new JSONHelper();
        JSONArray registrationList = Helper.getJSONArray("src/JSONFiles/RegistrationList.json");
        JSONArray waitingList = Helper.getJSONArray("src/JSONFiles/WaitingList.json");
        if (findEmailInList(enteredEMail, registrationList) || findEmailInList(enteredEMail, waitingList)) {
            System.out.println("Diese E-Mail-Adresse ist bereits registriert. Geben Sie eine andere E-Mail-Adresse ein!");
            enteredEMail = getEMailAdress(_scan);
        }
        if (!validateEMail(enteredEMail)) {
            System.out.println("Dies ist keine gültige E-Mail-Adresse. Geben Sie eine andere E-Mail-Adresse ein!");
            enteredEMail = getEMailAdress(_scan);
        }
        
        return enteredEMail;
    }

    private boolean findEmailInList(String _eMail, JSONArray _list) {
        for (int i = 0; i < _list.size(); i++) {
            JSONObject registration = (JSONObject) _list.get(i);
            String registratedEMail = registration.get("EMail").toString();
            if (registratedEMail.equals(_eMail)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateEMail(String _eMail) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(_eMail);
        return matcher.matches();
    }

    private void searchForDate(String _searchedDate) {

        JSONHelper Helper = new JSONHelper();
        JSONArray appointmentArray = Helper.getJSONArray("src/JSONFiles/AppointmentList.json");

        if (findIndexOfDate(_searchedDate, appointmentArray) == -1) {
            System.out.println("Für dieses Datum gibt es leider keine Termine.");
            userOptions();
        } else {
            hasAppointmentFreeTimes(findIndexOfDate(_searchedDate, appointmentArray), appointmentArray);
        }
    }

    private int findIndexOfDate(String _searchedDate, JSONArray _appointmentArray) {

        for (int i = 0; i < _appointmentArray.size(); i++) {
            JSONObject appointment = (JSONObject) _appointmentArray.get(i);
            String date = appointment.get("Date").toString();

            if (date.equals(_searchedDate)) {
                return i;
            }
        }
        return -1;
    }

    private void hasAppointmentFreeTimes(int _indexOfDate, JSONArray _appointmentArray) {
        Appointment Appointment = new Appointment();
        AppointmentHelper Helper = new AppointmentHelper();

        if (!Helper.areAllTimesTaken(_indexOfDate, _appointmentArray)) {

            Appointment.getSelectedDay(_indexOfDate + 1, _appointmentArray);
        } else {
            System.out.println("Für dieses Datum sind alle Termine bereits vergeben.");
            userOptions();
        }
    }
}
