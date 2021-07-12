import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.StringWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONHelper {

    public void enterAppointmentInJSONFile(AppointmentModel _Appointment) {
        JSONArray appointmentFile = getJSONArray("src/JSONFiles/AppointmentList.json");

        JSONObject appointment = new JSONObject();
        appointment.put("ID", _Appointment.getId());
        appointment.put("Date", _Appointment.getDate());
        appointment.put("TimePeriodStart", _Appointment.getTimePeriodStart().toString());
        appointment.put("TimePeriodEnd", _Appointment.getTimePeriodEnd().toString());
        appointment.put("ConcurrentVaccinations", _Appointment.getConcurrentVaccinations());
        appointment.put("TimeIntervalls", _Appointment.getTimeIntervalls());

        AppointmentHelper Helper = new AppointmentHelper();
        JSONArray times = Helper.createTimesForADay(_Appointment.getTimePeriodStart(),  _Appointment.getTimePeriodEnd(), _Appointment.getTimeIntervalls(),  _Appointment.getConcurrentVaccinations());
        appointment.put("Times", times);

        /* JSONObject appointment = new JSONObject();
        appointment.put("Appointment", appointmentData); */

        appointmentFile.add(appointment);


        try (FileWriter file = new FileWriter("src/JSONFiles/AppointmentList.json")) {
            file.write(appointmentFile.toJSONString());
            System.out.println("Appointment created");
        } catch (IOException e) {
            System.out.println("Error while writing json file");
        }

    }

    public JSONArray getJSONArray(String _fileName) {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) parser.parse(new FileReader(_fileName));
            return jsonArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONObject getJSONFile(String _fileName) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader(_fileName));
            return jsonObject;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void updateDataList(JSONObject _updatedDataList) {
        try (FileWriter file = new FileWriter("src/JSONFiles/DataList.json")) {
            file.write(_updatedDataList.toJSONString());
            System.out.println("created first id");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateKeyValue(String _jsonFile, String _key, int _value) {
        JSONObject fileToUpdate = getJSONFile("src/JSONFiles/DataList.json");
        fileToUpdate.put(_key, _value);
        try (FileWriter file = new FileWriter(_jsonFile)) {
            file.write(fileToUpdate.toJSONString());
            System.out.println("created first id");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
