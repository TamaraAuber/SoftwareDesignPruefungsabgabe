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

public class AppointmentHelper {

    public AppointmentHelper() {
    }

    /*
     * public Date parseDate(String date) {
     * 
     * String sDate1 = "31/12/1998"; Date date1 = null; try { date1 = new
     * SimpleDateFormat("dd/MM/yyyy").parse(sDate1); System.out.println(sDate1 +
     * "\t" + date1); } catch (ParseException e) { e.printStackTrace(); } return
     * date1;
     * 
     * 
     * Calendar myCalendar = new GregorianCalendar(2014, 2, 11); Date myDate =
     * myCalendar.getTime(); System.out.println(my); return myDate; }
     */

    /*
     * LocalTime time1 = LocalTime.parse("10:45"); System.out.println(time1);
     * LocalTime time2 = LocalTime.parse("11:30"); System.out.println(time2); long
     * time3 = Duration.between(time1, time2).toMinutes();
     * System.out.println(time3);
     */

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

            System.out.println("    - " + time + " Uhr (" + getFreeTimes(jsonObj, time) + ")");
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
