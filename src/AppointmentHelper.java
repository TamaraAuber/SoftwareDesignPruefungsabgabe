import org.json.simple.*;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.time.Duration;
import java.util.Set;
import java.util.Scanner;

public class AppointmentHelper {

    public AppointmentHelper() {
    }

    // scans the entire appointment list for free appointments
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

    public JSONArray createTimesForADay(LocalTime _timePeriodStart, LocalTime _timePeriodEnd, int _timeIntervalls,
            int _concurrentVaccinations) {

        LocalTime timeStart = _timePeriodStart;
        LocalTime currentTime = timeStart;
        LocalTime timeEnd = _timePeriodEnd;

        long timeDifference = Duration.between(timeStart, timeEnd).toMinutes();
        long howMuchTimes = timeDifference / _timeIntervalls;

        JSONObject firstTime = new JSONObject();
        firstTime.put(timeStart.toString(), fillIdArray(_concurrentVaccinations));

        JSONArray timeArray = new JSONArray();
        timeArray.add(firstTime);

        for (int i = 0; i < howMuchTimes; i++) {
            currentTime = currentTime.plusMinutes(_timeIntervalls);

            JSONObject time = new JSONObject();
            time.put(currentTime, fillIdArray(_concurrentVaccinations));
            timeArray.add(time);
        }

        return timeArray;

    }

    private JSONArray fillIdArray(int _concurrentVaccinations) {
        JSONArray idArray = new JSONArray();

        for (int i = 0; i < _concurrentVaccinations; i++) {
            idArray.add(0);
        }

        return idArray;
    }

    public boolean areAllTimesTaken(int _selectedDay, JSONArray _appointmentArray) {
        if (howManyPercentAreOccupied(_selectedDay, _appointmentArray) == 100.00) {
            return true;
        }
        return false;
    }

    public double howManyPercentAreOccupied(int _selectedDay, JSONArray _appointmentArray) {
        JSONObject appointment = (JSONObject) _appointmentArray.get(_selectedDay);
        JSONArray times = (JSONArray) appointment.get("Times");
        int allTimes = times.size() * Integer.parseInt(appointment.get("ConcurrentVaccinations").toString());
        int availableTimes = 0;

        for (int i = 0; i < times.size(); i++) {
            JSONObject jsonObj = (JSONObject) times.get(i);
            Set<String> timeSet = jsonObj.keySet();
            String time = timeSet.iterator().next();
            availableTimes = availableTimes + getFreeTimes(jsonObj, time);
        }

        return occupiedTimesInPercent((double) allTimes, (double) availableTimes);

    }

    private double occupiedTimesInPercent(double _allTimes, double _availableTimes) {
        double occupiedTimes = _allTimes - _availableTimes;

        double occupiedTimesInPercent = occupiedTimes / _allTimes * 100;

        return occupiedTimesInPercent;
    }

    public void showAppointmentTimes(JSONObject _appointment) {
        JSONArray times = (JSONArray) _appointment.get("Times");

        System.out.println("- Freie Uhrzeiten:");

        for (int i = 0; i < times.size(); i++) {
            JSONObject jsonObj = (JSONObject) times.get(i);

            Set<String> timeSet = jsonObj.keySet();

            String time = timeSet.iterator().next();

            /*
             * System.out.println("    - " + time + " Uhr (" + getFreeTimes(jsonObj, time) +
             * ")");
             */

            chooseNotation(time, jsonObj, i + 1);
        }
    }

    // decides if it should take the notation for admin or user
    private void chooseNotation(String _time, JSONObject _jsonObj, int _counter) {
        JSONHelper Helper = new JSONHelper();
        JSONObject dataList = Helper.getJSONFile("src/JSONFiles/DataList.json");
        long isAdminLoggedIn = (long) dataList.get("isAdminLoggedIn");

        if (isAdminLoggedIn == 1) {
            System.out.println("    - " + _time + " Uhr [" + getFreeTimes(_jsonObj, _time) + "]");
        } else {
            System.out.println("     " + _counter + ") " + _time + " Uhr [" + getFreeTimes(_jsonObj, _time) + "]");
        }
    }

    public int getFreeTimes(JSONObject _time, String _timeKey) {
        JSONArray idArray = (JSONArray) _time.get(_timeKey);
        int freeTimesCounter = 0;

        for (int i = 0; i < idArray.size(); i++) {
            if ((long) idArray.get(i) == 0) {
                freeTimesCounter++;
            }
        }

        return freeTimesCounter;
    }

    public boolean checkIfTimeSlotsAreFree(int _chosenDateId, int _chosenTimeSlot) {
        int chosenTimeSlot = _chosenTimeSlot- 1;
        JSONHelper Helper = new JSONHelper();
        JSONArray appointmentList = Helper.getJSONArray("src/JSONFiles/AppointmentList.json");
        int availableTimes = 0;

        for (int i = 0; i < appointmentList.size(); i++) {
            JSONObject appointment = (JSONObject) appointmentList.get(i);
            if (Integer.parseInt(appointment.get("ID").toString()) == _chosenDateId) {
                JSONArray times = (JSONArray) appointment.get("Times");
                JSONObject jsonObj = (JSONObject) times.get(chosenTimeSlot);
                Set<String> timeSet = jsonObj.keySet();
                String time = timeSet.iterator().next();
                availableTimes = getFreeTimes(jsonObj, time);
            }
        }

        if (availableTimes == 0) {
            return false;
        } else {
            return true;
        }
    }

    public JSONObject getAppointmentOverID(int _appointmentID) {
        JSONHelper JSONHelper = new JSONHelper();
        JSONArray appointmentList = JSONHelper.getJSONArray("src/JSONFiles/AppointmentList.json");
        JSONObject searchedAppointment = null;

        for (int i = 0; i < appointmentList.size(); i++) {
            JSONObject appointment = (JSONObject) appointmentList.get(i);
            if (Integer.parseInt(appointment.get("ID").toString()) == _appointmentID) {
                searchedAppointment = appointment;
            }
        }
        return searchedAppointment;
    }

}
