import java.io.FileReader;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Appointment {

    public Appointment() {
    }

    public void createNewAppointment() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Datum (z.B. 12.03.2021): ");
        String enteredDate = scan.nextLine();

        System.out.print("Zeitraum von (z.B. 09:30): ");
        String enteredTimePeriodStart = scan.nextLine();

        System.out.print("Zeitraum bis (z.B. 10:45): ");
        String enteredTimePeriodEnd = scan.nextLine();

        System.out.print("Gleichzeitig stattfindende Impfungen: ");
        int enteredConcurrentVaccinations = scan.nextInt();

        System.out.print("Zeitabstände zwischen den Terminen (in Minuten): ");
        int enteredTimeIntervalls = scan.nextInt();

        ID_Generator Generator = ID_Generator.getInstance();
        int id = Generator.generateNewId();

        try {
            AppointmentModel NewAppointment = new AppointmentModel(id,
                    LocalDate.parse(enteredDate, DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    LocalTime.parse(enteredTimePeriodStart), LocalTime.parse(enteredTimePeriodEnd),
                    enteredConcurrentVaccinations, enteredTimeIntervalls);

            System.out.println(NewAppointment.toString());

            JSONHelper JSONFile = new JSONHelper();
            JSONFile.enterAppointmentInJSONFile(NewAppointment);

            goBackToOptions();
        } catch (Exception e) {
            System.out.println("Irgendwas ist schief gelaufen. Versuchen sie es erneut.");
            createNewAppointment();
        }

        scan.close();
    }

    private void goBackToOptions() {
        Administrator Admin = new Administrator();
        User User = new User();
        JSONHelper Helper = new JSONHelper();
        JSONObject dataList = Helper.getJSONFile("src/JSONFiles/DataList.json");
        long isAdminLoggedIn = (long) dataList.get("isAdminLoggedIn");

        if (isAdminLoggedIn == 1) {
            Admin.adminOptions();
        } else {
            User.userOptions();
        }
    }

    public void showAllAppoitments() {
        Scanner scan = new Scanner(System.in);

        AppointmentHelper Helper = new AppointmentHelper();

        System.out.println("Wählen Sie ein Datum!");

        System.out.println("0) zurück");

        JSONHelper JSONFile = new JSONHelper();
        JSONArray appointmentArray = JSONFile.getJSONArray("src/JSONFiles/AppointmentList.json");

        for (int i = 0; i < appointmentArray.size(); i++) {
            JSONObject appointment = (JSONObject) appointmentArray.get(i);
            String appointmentDate = appointment.get("Date").toString();

            String occupiedTimesInPercentage = String.format("%.2f",
                    Helper.howManyPercentAreOccupied(i, appointmentArray));

            System.out.println(
                    i + 1 + ") " + appointmentDate + " [" + occupiedTimesInPercentage + "% der Termine sind besetzt]");
        }

        int selectedOption = scan.nextInt();

        if (selectedOption == 0) {
            goBackToOptions();
        } else {
            if (!Helper.areAllTimesTaken(selectedOption - 1, appointmentArray)) {
                getSelectedDay(selectedOption, appointmentArray);
                System.out.println("0) zurück");
                goBackOrRegister(scan);
                /*
                 * if (scan.nextInt() == 0) { goBackToOptions(); }
                 */
            } else {
                System.out.println("Für dieses Datum gibt es keine freien Termine.");
                System.out.println("Wählen Sie ein anderes Datum.");
                showAllAppoitments();
            }

        }

        scan.close();
    }

    public void getSelectedDay(int _selectedDay, JSONArray _appointmentArray) {
        AppointmentHelper Helper = new AppointmentHelper();

        JSONObject appointment = (JSONObject) _appointmentArray.get(_selectedDay - 1);

        System.out.println("Gewähltes Datum: " + appointment.get("Date"));

        String occupiedTimesInPercentage = String.format("%.2f",
                Helper.howManyPercentAreOccupied(_selectedDay - 1, _appointmentArray));
        System.out.println("- " + occupiedTimesInPercentage + "% der Termine sind besetzt");

        Helper.showAppointmentTimes(appointment);

        noteIdOfSelectedDate(Integer.parseInt(appointment.get("ID").toString()));
    }

    // writes down the id of the selected day to use it later for user registration
    private void noteIdOfSelectedDate(int _idOfSelectedDate) {
        JSONHelper Helper = new JSONHelper();
        Helper.updateKeyValue("src/JSONFiles/DataList.json", "IdOfLastSelectedDate", _idOfSelectedDate);
    }

    private void goBackOrRegister(Scanner scan) {
        int selectedOption = scan.nextInt();
        if (selectedOption == 0) {
            goBackToOptions();
        } else {
            startRegistration(selectedOption);
        }
    }

    private void startRegistration(int _selectedOption) {
        JSONHelper Helper = new JSONHelper();
        JSONObject dataList = Helper.getJSONFile("src/JSONFiles/DataList.json");
        long isAdminLoggedIn = (long) dataList.get("isAdminLoggedIn");

        if (isAdminLoggedIn == 0) {
            User User = new User();
            long idOfSelectedDay = (long) dataList.get("IdOfLastSelectedDate");
            User.registerForAppointment((int) idOfSelectedDay, _selectedOption);
        }
    }

}