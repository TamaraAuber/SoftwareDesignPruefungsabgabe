import java.io.FileReader;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.Set;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Appointment {

    public Appointment() {

    }

    public void createNewAppointment() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Datum: ");
        String enteredDate = scan.nextLine();

        System.out.print("Zeitraum von: ");
        String enteredTimePeriodStart = scan.nextLine();

        System.out.print("Zeitraum bis: ");
        String enteredTimePeriodEnd = scan.nextLine();

        System.out.print("Gleichzeitig stattfindende Impfungen: ");
        int enteredConcurrentVaccinations = scan.nextInt();

        System.out.print("Zeitabstände zwischen den Terminen (in Minuten): ");
        int enteredTimeIntervalls = scan.nextInt();

        ID_Generator Generator = ID_Generator.getInstance();
        int id = Generator.generateNewId();

        AppointmentModel NewAppointment = new AppointmentModel(id, enteredDate, LocalTime.parse(enteredTimePeriodStart),
                LocalTime.parse(enteredTimePeriodEnd), enteredConcurrentVaccinations, enteredTimeIntervalls);

        System.out.println(NewAppointment.toString());

        JSONHelper JSONFile = new JSONHelper();
        JSONFile.enterAppointmentInJSONFile(NewAppointment);

        goBackToOptions();
        scan.close();
    }

    private void goBackToOptions() {
        Administrator Admin = new Administrator();
        Admin.adminOptions();
    }

    public void showAllAppoitments() {
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

        System.out.println("Wählen Sie ein Datum!");

        System.out.println("0) zurück");

        JSONHelper JSONFile = new JSONHelper();
        JSONArray appointmentArray = JSONFile.getJSONArray("src/JSONFiles/AppointmentList.json");

        for (int i = 0; i < appointmentArray.size(); i++) {
            JSONObject appointment = (JSONObject) appointmentArray.get(i);
            String appointmentDate = appointment.get("Date").toString();

            System.out.println(i + 1 + ") " + appointmentDate);
        }

        int selectedOption = scan.nextInt();

        if (selectedOption == 0) {
            goBackToOptions();
        } else {
            getSelectedDay(selectedOption, appointmentArray);
        }

        scan.close();
    }

    private void getSelectedDay(int _selectedDay, JSONArray _appointmentArray) {
        JSONObject appointment = (JSONObject) _appointmentArray.get(_selectedDay - 1);

        showAppointmentTimes(appointment);

    }

    private void showAppointmentTimes(JSONObject _appointment) {
        JSONArray times = (JSONArray) _appointment.get("Times");

        for (int i = 0; i < times.size(); i++) {
            JSONObject jsonObj = (JSONObject) times.get(i);

            Set<String> timeSet = jsonObj.keySet();

            String time = timeSet.iterator().next();

            System.out.println(time + " (" +  getFreeTimes(jsonObj, time) + ")");
        }
    }

    private int getFreeTimes(JSONObject _time, String _timeKey) {
        JSONArray idArray = (JSONArray) _time.get(_timeKey);
        int freeTimesCounter = 0;

        for (int i = 0; i < idArray.size(); i++) {
            if ((long) idArray.get(i) == 0) {
                freeTimesCounter++;
            }
        }

        return freeTimesCounter;
    }

}

/*
 * import java.text.SimpleDateFormat; import java.util.Date;
 */

/*
 * String sDate1 = "31/12/1998"; Date date1 = new
 * SimpleDateFormat("dd/MM/yyyy").parse(sDate1); System.out.println(sDate1 +
 * "\t" + date1);
 */