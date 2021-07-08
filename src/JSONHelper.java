import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class JSONHelper {

    public void enterAppointmentInJSONFile(AppointmentModel _Appointment, int _id) {
        JSONObject appointmentFile = getJSONFile("src/JSONFiles/AppointmentList.json");

        JSONObject appointmentData = new JSONObject();
        appointmentData.put("id", _Appointment.getId());
        appointmentData.put("Date", _Appointment.getDate());
        appointmentData.put("timePeriodStart", _Appointment.getTimePeriodStart());
        appointmentData.put("timePeriodEnd", _Appointment.getTimePeriodEnd());
        appointmentData.put("concurrentVaccinations", _Appointment.getConcurrentVaccinations());
        appointmentData.put("timeIntervalls", _Appointment.getTimeIntervalls());

        appointmentFile.put(_id, appointmentData);

        try (FileWriter file = new FileWriter("src/JSONFiles/AppointmentList.json")) {

            file.write(appointmentFile.toJSONString());
            System.out.println("Appointment created");
        } catch (IOException e) {
            // TODO: handle exception
        }

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

    /*
     * public String getValueFrom(String _jSONFile, String _key) { Object obj =
     * getJSONFile(_jSONFile); JSONObject jsonObject = (JSONObject) obj; String
     * value = (String) jsonObject.get(_key);
     * 
     * return value; }
     */

}
