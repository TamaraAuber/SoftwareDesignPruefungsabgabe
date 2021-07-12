import org.json.simple.*;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.time.Duration;

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
}
