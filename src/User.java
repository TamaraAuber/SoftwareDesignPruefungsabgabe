import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class User {

    public User() {

    }

    public void userOptions() {
        Scanner scanInt = new Scanner(System.in);
        Scanner scanString = new Scanner(System.in);

        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("");

        System.out.println("1) Übersicht aller Termine");
        System.out.println("2) nach Datum suchen");

        int selectedOption = scanInt.nextInt();

        switch (selectedOption) {
            case 1:
                Appointment Appointment = new Appointment();
                Appointment.showAllAppoitments();
                break;
            case 2:
                System.out.println("");
                System.out.println("Geben sie das gesuchte Datum ein (z.B. 13.09.2021):");
                String enteredDate = scanString.nextLine();
                LocalDate date = LocalDate.parse(enteredDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                String searchedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                searchForDate(searchedDate);
                break;
        }

        scanInt.close();
        scanString.close();
    }

    public void registerForAppointment(int _chosenDateId, int _chosenTimeIndex) {
        AppointmentHelper AppointmentHelper = new AppointmentHelper();
        if (AppointmentHelper.checkIfTimeSlotsAreFree(_chosenDateId, _chosenTimeIndex)) {
            JSONHelper JSONFile = new JSONHelper();
            MailService MailService = new MailService();
            try {
                RegistrationModel registration = enterRegistrationData();
                int registrationID = registration.getId();

                JSONFile.enterRegitrationInList(registration, "src/JSONFiles/RegistrationList.json");

                linkAppointmentWithRegistration(JSONFile, _chosenDateId, _chosenTimeIndex, registrationID);

                MailService.confirmAppointment(registrationID, _chosenDateId, _chosenTimeIndex - 1);

            } catch (Exception e) {
                System.out.println("Irgendwas ist schiefgelaufen. Versuchen sie es erneut.");
                registerForAppointment(_chosenDateId, _chosenTimeIndex);
            }
        } else {
            System.out.println("");
            System.out.println("Für diese Uhrzeit sind keine freien Termine mehr verfügbar.");
            System.out.println("Wählen Sie einen anderen Termin.");
            userOptions();
        }
    }

    private void linkAppointmentWithRegistration(JSONHelper JSONFile, int _chosenDateID, int _chosenTimeIndex,
            int _registrationID) {

        JSONArray appointmentList = JSONFile.getJSONArray("src/JSONFiles/AppointmentList.json");
        for (int i = 0; i < appointmentList.size(); i++) {
            JSONObject appointment = (JSONObject) appointmentList.get(i);

            if (Integer.parseInt(appointment.get("ID").toString()) == _chosenDateID) {
                JSONArray appointmentTimes = (JSONArray) appointment.get("Times");

                JSONObject chosenTime = (JSONObject) appointmentTimes.get(_chosenTimeIndex - 1);

                Set<String> timeSet = chosenTime.keySet();
                String time = timeSet.iterator().next();

                JSONArray timeSlots = (JSONArray) chosenTime.get(time);

                for (int x = 0; x < timeSlots.size(); x++) {
                    if (Integer.parseInt(timeSlots.get(x).toString()) == 0) {
                        timeSlots.set(x, _registrationID);
                        break;
                    }
                }

            }
        }
        JSONFile.updateJSONFile(appointmentList,  "src/JSONFiles/AppointmentList.json");
    }

    public void registerForWaitingList() {

        try {
            JSONHelper JSONFile = new JSONHelper();
            JSONFile.enterRegitrationInList(enterRegistrationData(), "src/JSONFiles/WaitingList.json");
        } catch (Exception e) {
            System.out.println("Irgendwas ist schiefgelaufen. Versuchen sie es erneut.");
            registerForWaitingList();
        }

    }

    private RegistrationModel enterRegistrationData() {
        Scanner scanString = new Scanner(System.in);
        Scanner scanInt = new Scanner(System.in);

        System.out.println("");
        System.out.println("Bei der Registrierung bitte keine Sonderzeichen benutzen (ä, ö, ü, ß).");

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
            System.out.println("");
            System.out
                    .println("Diese E-Mail-Adresse ist bereits registriert. Geben Sie eine andere E-Mail-Adresse ein!");
            enteredEMail = getEMailAdress(_scan);
        }
        if (!validateEMail(enteredEMail)) {
            System.out.println("");
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

    public boolean validateEMail(String _eMail) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(_eMail);
        return matcher.matches();
    }

    private void searchForDate(String _searchedDate) {

        JSONHelper Helper = new JSONHelper();
        JSONArray appointmentArray = Helper.getJSONArray("src/JSONFiles/AppointmentList.json");

        if (findIndexOfDate(_searchedDate, appointmentArray) == -1) {
            System.out.println("");
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
        Scanner scan = new Scanner(System.in);
        Appointment Appointment = new Appointment();
        AppointmentHelper Helper = new AppointmentHelper();

        if (!Helper.areAllTimesTaken(_indexOfDate, _appointmentArray)) {
            System.out.println("");
            System.out.println("0) zurück");
            System.out.println("Wählen Sie eine Uhrzeit für die Sie sich registrieren möchten.");

            Appointment.getSelectedDay(_indexOfDate + 1, _appointmentArray);

            int selectedOption = scan.nextInt();

            if (selectedOption == 0) {
                userOptions();
            } else {
                JSONObject appointment = (JSONObject) _appointmentArray.get(_indexOfDate);
                registerForAppointment(Integer.parseInt(appointment.get("ID").toString()), selectedOption);
            }

        } else {
            System.out.println("");
            System.out.println("Für dieses Datum sind alle Termine bereits vergeben.");
            userOptions();
        }
        scan.close();
    }

}
