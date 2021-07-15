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

    public boolean areThereFreeAppointments() {
        AppointmentHelper Helper = new AppointmentHelper();
        JSONHelper JSONFile = new JSONHelper();
        JSONArray appointmentArray = JSONFile.getJSONArray("src/JSONFiles/AppointmentList.json");
        int allAppointmentDays = appointmentArray.size();
        int occupiedAppointmentDays = 0;

        if (appointmentArray.size() == 0) {
            return false;
        }

        for (int i = 0; i < appointmentArray.size(); i++) {

            if (Helper.areAllTimesTaken(i, appointmentArray)) {
                occupiedAppointmentDays++;
            }

            if (allAppointmentDays == occupiedAppointmentDays) {
                return false;
            }
        }

        return true;
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
        Admin.adminOptions();
    }

    public void showAllAppoitmentsAdmin() {
        /*
         * try { JSONParser parser = new JSONParser(); Object obj = parser.parse(new
         * FileReader("src/JSONFiles/AppointmentList.json")); JSONArray jsonObject =
         * (JSONArray) obj; JSONObject test = (JSONObject) jsonObject.get(1);
         * System.out.println(test); String test2 = (String) test.get("Date"); String
         * test2 = test.get("Date").toString(); System.out.println(test2);
         * 
         * int test3 = Integer.parseInt(test.get("timeIntervalls").toString());
         * System.out.println(test3); } catch (Exception e) { e.printStackTrace(); }
         */

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
                if (scan.nextInt() == 0) {
                    goBackToOptions();
                }
            }
            System.out.println("Für dieses Datum gibt es keine freien Termine.");
            System.out.println("Wählen Sie ein anderes Datum.");
            showAllAppoitmentsAdmin();
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
    }

}