import org.json.simple.*;
import java.util.Set;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WaitingList {

    public WaitingList() {
    }

    public void workOffTheWaitingList() {
        AppointmentHelper AppointmentHelper = new AppointmentHelper();
        JSONHelper JSONHelper = new JSONHelper();

        if (AppointmentHelper.areThereFreeAppointments()) {
            System.out.println("Wooooooooorking on WaitingList");

            JSONArray appointmentList = JSONHelper.getJSONArray("src/JSONFiles/AppointmentList.json");
            JSONArray waitingList = JSONHelper.getJSONArray("src/JSONFiles/WaitingList.json");

            linkFreeAppointmentsToUser();

        }
    }

    public void linkFreeAppointmentsToUser() {
        JSONHelper Helper = new JSONHelper();
        JSONArray appointmentList = Helper.getJSONArray("src/JSONFiles/AppointmentList.json");
        JSONArray waitingList = Helper.getJSONArray("src/JSONFiles/WaitingList.json");

        for (int i = 0; i < appointmentList.size(); i++) {
            JSONObject appointment = (JSONObject) appointmentList.get(i);
            JSONArray timesOfAppointment = (JSONArray) appointment.get("Times");

            for (int x = 0; x < timesOfAppointment.size(); x++) {
                JSONObject jsonObj = (JSONObject) timesOfAppointment.get(x);
                Set<String> timeSet = jsonObj.keySet();
                String time = timeSet.iterator().next();

                JSONArray timeSlots = (JSONArray) jsonObj.get(time);
                for (int y = 0; y < timeSlots.size(); y++) {
                    if (Integer.parseInt(timeSlots.get(y).toString()) == 0 && waitingList.size() != 0) {
                        JSONObject firstUserOnList = (JSONObject) waitingList.get(0);
                        int idFirstUserOnList = Integer.parseInt(firstUserOnList.get("ID").toString());
                        timeSlots.set(y, idFirstUserOnList);
                        moveUserToRegistrationList(firstUserOnList);
                        waitingList.remove(0);
                    }
                }

            }
        }

        Helper.updateJSONFile(appointmentList, "src/JSONFiles/AppointmentList.json");
        Helper.updateJSONFile(waitingList, "src/JSONFiles/WaitingList.json");
    }

    private void moveUserToRegistrationList(JSONObject _user) {
        JSONHelper JSONHelper = new JSONHelper();

        try {
            int id = Integer.parseInt(_user.get("ID").toString());
            String eMail = _user.get("EMail").toString();
            String firstName = _user.get("FirstName").toString();
            String lastName = _user.get("LastName").toString();
            String birthDate = _user.get("BirthDate").toString();
            int phoneNumber = Integer.parseInt(_user.get("PhoneNumber").toString());
            String street = _user.get("Street").toString();
            int houseNumber = Integer.parseInt(_user.get("HouseNumber").toString());
            int postalCode = Integer.parseInt(_user.get("PostalCode").toString());
            String city = _user.get("City").toString();

            RegistrationModel Registration = new RegistrationModel(id, eMail, firstName, lastName,
            LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")), phoneNumber, street,
                    houseNumber, postalCode, city);
            JSONHelper.enterRegitrationInList(Registration, "src/JSONFiles/RegistrationList.json");
        } catch (Exception e) {
            System.out.println("Oh No");
            e.printStackTrace();
        }

    }

}
