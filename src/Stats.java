import org.json.simple.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class Stats {

    public Stats() {
    }

    public void showAllStats() {
        getTotalAppointments();
        howManyOccupiedAndFreeTimes();

        System.out.println("0) zurück");
        Scanner scan = new Scanner(System.in);
        if (scan.nextInt() == 0) {
             Appointment Appointment = new Appointment();
        Appointment.goBackToOptions(); 
        }
        scan.close();
    }

    private void getTotalAppointments() {
        JSONHelper Helper = new JSONHelper();
        JSONArray appointmentList = Helper.getJSONArray("src/JSONFiles/AppointmentList.json");
        int numberOfTotalAppointments = 0;

        for (int i = 0; i < appointmentList.size(); i++) {
            JSONObject appointment = (JSONObject) appointmentList.get(i);
            JSONArray times = (JSONArray) appointment.get("Times");
            int allTimesOfThisDay = times.size()
                    * Integer.parseInt(appointment.get("ConcurrentVaccinations").toString());
            numberOfTotalAppointments = numberOfTotalAppointments + allTimesOfThisDay;
        }

        System.out.println("Bisher wurden insgesamt " + numberOfTotalAppointments + " Termine angelegt");
    }

    private void howManyOccupiedAndFreeTimes() {
        JSONHelper JSONHelper = new JSONHelper();
        JSONArray appointmentList = JSONHelper.getJSONArray("src/JSONFiles/AppointmentList.json");
        AppointmentHelper AppointmentHelper = new AppointmentHelper();

        int allTimes = 0;
        int totalFreeTimes = 0;
        int totalFreeTimesPast = 0;
        int totalFreeTimesFuture = 0;
        int totalOccupiedTimes = 0;

        for (int i = 0; i < appointmentList.size(); i++) {
            JSONObject appointment = (JSONObject) appointmentList.get(i);
            JSONArray times = (JSONArray) appointment.get("Times");
            int allTimesForThisDay = times.size()
                    * Integer.parseInt(appointment.get("ConcurrentVaccinations").toString());
            int availableTimes = 0;

            for (int x = 0; x < times.size(); x++) {
                JSONObject jsonObj = (JSONObject) times.get(x);
                Set<String> timeSet = jsonObj.keySet();
                String time = timeSet.iterator().next();
                availableTimes = availableTimes + AppointmentHelper.getFreeTimes(jsonObj, time);
            }

            allTimes = allTimes + allTimesForThisDay;
            totalFreeTimes = totalFreeTimes + availableTimes;

            // looks up how many free dates are in the past and how many are in the future
            if (isDateInThePast(appointment)) {
                totalFreeTimesPast = totalFreeTimesPast + availableTimes;
            } else {
                totalFreeTimesFuture = totalFreeTimesFuture + availableTimes;
            }
        }

        totalOccupiedTimes = allTimes - totalFreeTimes;

        outputStats(allTimes, totalFreeTimes, totalFreeTimesPast, totalFreeTimesFuture, totalOccupiedTimes);
    }

    private boolean isDateInThePast(JSONObject _appointment) {
        LocalDate today = LocalDate.now();
        String appointmentDateString = _appointment.get("Date").toString();
        LocalDate appointmentDate = LocalDate.parse(appointmentDateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        return today.isBefore(appointmentDate);
    }

    private void outputStats(int _allTimes, int _totalFreeTimes, int _totalFreeTimesPast, int _totalFreeTimesFuture,
            int _totalOccupiedTimes) {

        double totalFreeTimesInPercent = (double) _totalFreeTimes / (double) _allTimes * 100;
        double totalOccupiedTimesInPercent = (double) _totalOccupiedTimes / (double) _allTimes * 100;
        double totalFreeTimesPastINPercent = (double) _totalFreeTimesPast / (double) _allTimes * 100;
        double totalFreeTimesFutureInPercent = (double) _totalFreeTimesFuture / (double) _allTimes * 100;

        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("Statistik:");
        System.out.println("");

        System.out.println("     - Von allen Terminen sind aktuell " + String.format("%.2f", totalOccupiedTimesInPercent)
                + "% (" + _totalOccupiedTimes + " Termine) belegt");
        System.out.println(
                "       und " + String.format("%.2f", totalFreeTimesInPercent) + "% (" + _totalFreeTimes + " Termine) frei");
        System.out.println("");
               
        System.out.println("     - Davon liegen aktuell " + String.format("%.2f", totalFreeTimesFutureInPercent) + "% (" + _totalFreeTimesFuture + " Termine) in der Zukunft");
        System.out.println("     - Davon lagen " + String.format("%.2f", totalFreeTimesPastINPercent) + "% (" + _totalFreeTimesPast + " Termine) in der Vergangenheit und sind somit ungenutzt.");

    }

}
