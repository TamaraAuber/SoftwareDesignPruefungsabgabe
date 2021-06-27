import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class JSONHelper {

    public void enterAppointmentInJSONFile(AppointmentModel _Appointment) {
        /* JSONObject object = new JSONObject(); */
        JSONArray AppointmentList = getAppointmentJSONFile();

        JSONObject appointmentData = new JSONObject();
        appointmentData.put("Date", _Appointment.getDate());
        appointmentData.put("timePeriodStart", _Appointment.getTimePeriodStart());
        appointmentData.put("timePeriodEnd", _Appointment.getTimePeriodEnd());
        appointmentData.put("concurrentVaccinations", _Appointment.getConcurrentVaccinations());
        appointmentData.put("timeIntervalls", _Appointment.getTimeIntervalls());

        JSONObject appointment = new JSONObject();
        appointment.put("Appointment", appointmentData);

        AppointmentList.add(appointment);

        try (FileWriter file = new FileWriter("src/AppointmentList.json")) {
        

            file.write(AppointmentList.toJSONString());
            System.out.println("Appointment created");
            System.out.println("AppointmentList" + AppointmentList);
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    private JSONArray getAppointmentJSONFile() {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) parser.parse(new FileReader("src/AppointmentList.json"));
            return jsonArray;

        } catch (Exception e) {
            // TODO: handle exception
        }
        return jsonArray;
    }
}
