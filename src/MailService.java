import org.json.simple.*;
import java.util.Set;

public class MailService {

    public MailService() {
    }

    public void confirmAppointment(int _registrationID, int _appointmentID, int _chosenTimeIndex) {
        writeConfirmationOverRegistration(_registrationID, _appointmentID, _chosenTimeIndex);
        sendConfirmationEMail();
    }

    public void sendInformationToUserFromWaitingList(int _registration) {
        sendConfirmationEMail();
    }

    private void writeConfirmationOverRegistration(int _registrationID, int _appointmentID, int _chosenTimeIndex) {
        String date = getAppointmentDate(_appointmentID);
        String time = getAppointmentTime(_appointmentID, _chosenTimeIndex);
        String userName = getUserName(_registrationID);

        System.out.println(
                "------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Hallo " + userName + ",");
        System.out.println("");
        System.out.println("Ihre Registrierung für einen Impftermin wurde erfolgreich abgeschlossen.");
        System.out.println("Ihr gewählter Termin ist der " + date);
        System.out.println("um " + time + " Uhr.");
        System.out.println("");
        System.out.println("Mit freundlichen Grüßen Ihr Impfcenter Team :-)");
        System.out.println("");
        System.out.println(
                "------------------------------------------------------------------------------------------------");

    }

    private String getAppointmentDate(int _appointmentID) {
        AppointmentHelper Helper = new AppointmentHelper();
        JSONObject appointment = Helper.getAppointmentOverID(_appointmentID);
        return appointment.get("Date").toString();

    }

    private String getAppointmentTime(int _appointmentID, int _chosenTimeIndex) {

        AppointmentHelper Helper = new AppointmentHelper();
        JSONObject appointment = Helper.getAppointmentOverID(_appointmentID);
        JSONArray times = (JSONArray) appointment.get("Times");
        JSONObject bookedTime = (JSONObject) times.get(_chosenTimeIndex);
        Set<String> timeSet = bookedTime.keySet();
        String time = timeSet.iterator().next();

        return time;

    }

    private String getUserName(int _registrationID) {

        JSONHelper JSONHelper = new JSONHelper();
        JSONArray registrationList = JSONHelper.getJSONArray("src/JSONFiles/RegistrationList.json");
        String fullUserName = "";

        for (int i = 0; i < registrationList.size(); i++) {
            JSONObject user = (JSONObject) registrationList.get(i);
            if (Integer.parseInt(user.get("ID").toString()) == _registrationID) {
                fullUserName = user.get("FirstName").toString() + " " + user.get("LastName");
            }
        }

        return fullUserName;
    }

    private void sendConfirmationEMail() {
        // Schnittstelle zu externem E-Mail Programm, welche die Bestätigngsnachricht
        // versendet
    }

}
