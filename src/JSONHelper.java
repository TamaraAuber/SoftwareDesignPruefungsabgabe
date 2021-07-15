import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.time.format.DateTimeFormatter;
import java.io.StringWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONHelper {

    public JSONArray getJSONArray(String _fileName) {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) parser.parse(new FileReader(_fileName));
            return jsonArray;

        } catch (Exception e) {
            /* e.printStackTrace(); */
        }
        return jsonArray;
    }

    public void enterAppointmentInJSONFile(AppointmentModel _Appointment) {
        JSONArray appointmentFile = getJSONArray("src/JSONFiles/AppointmentList.json");

        JSONObject appointment = new JSONObject();
        appointment.put("ID", _Appointment.getId());
        appointment.put("Date", _Appointment.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        /* appointment.put("Date", _Appointment.getDate().toString()); */
        appointment.put("TimePeriodStart", _Appointment.getTimePeriodStart().toString());
        appointment.put("TimePeriodEnd", _Appointment.getTimePeriodEnd().toString());
        appointment.put("ConcurrentVaccinations", _Appointment.getConcurrentVaccinations());
        appointment.put("TimeIntervalls", _Appointment.getTimeIntervalls());

        AppointmentHelper Helper = new AppointmentHelper();
        JSONArray times = Helper.createTimesForADay(_Appointment.getTimePeriodStart(), _Appointment.getTimePeriodEnd(),
                _Appointment.getTimeIntervalls(), _Appointment.getConcurrentVaccinations());
        appointment.put("Times", times);

        /*
         * JSONObject appointment = new JSONObject(); appointment.put("Appointment",
         * appointmentData);
         */

        appointmentFile.add(appointment);

        try (FileWriter file = new FileWriter("src/JSONFiles/AppointmentList.json")) {
            file.write(appointmentFile.toJSONString());
            System.out.println("Appointment created");
        } catch (IOException e) {
            System.out.println("Error while writing json file");
        }
    }

    public void enterRegitrationInWaitingList(RegistrationModel _Registration) {
        JSONArray waitingList = getJSONArray("src/JSONFiles/WaitingList.json");

        JSONObject registration = new JSONObject();
        registration.put("ID", _Registration.getId());
        registration.put("EMail", _Registration.getEMail());
        registration.put("FirstName", _Registration.getFirstName());
        registration.put("LastName", _Registration.getLastName());
        registration.put("BirthDate", _Registration.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        registration.put("PhoneNumber", _Registration.getPhoneNumber());
        registration.put("Street", _Registration.getStreet());
        registration.put("HouseNumber", _Registration.getHouseNumber());
        registration.put("PostalCode", _Registration.getPostalCode());
        registration.put("City", _Registration.getCity());

        waitingList.add(registration);

        try (FileWriter file = new FileWriter("src/JSONFiles/WaitingList.json")) {
            file.write(waitingList.toJSONString());
            System.out.println("Registration entered");
        } catch (IOException e) {
            System.out.println("Error while writing json file");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
