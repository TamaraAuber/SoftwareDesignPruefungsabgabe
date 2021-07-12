public class RegistrationModel {

    // in der JSON Datei dnn noch JSONObjekt "Appointment hinzufügen" wo dann id und
    // Uhrzeit drinstehen

    private String eMail;
    private String firstName;
    private String lastName;
    private String birthDate;
    private int phoneNumber;
    private String street;
    private int houseNumber;
    private int postalCode;
    private String city;

    //oder Registrierung id geben und dadurch dem Termin hinzufügen
    private int appointmentId;
    private int appointmentTime;

    public RegistrationModel(String _eMail, String _firstName, String _lastName, String _birthDate, int _phoneNumber,
            String _street, int _houseNumber, int _postalCode, String _city) {
        this.eMail = _eMail;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.birthDate = _birthDate;
        this.phoneNumber = _phoneNumber;
        this.street = _street;
        this.houseNumber = _houseNumber;
        this.postalCode = _postalCode;
        this.city = _city;
    }

    public String toString() {
        return "E-MAil: " + eMail + " Vorname: " + firstName + " Nachname: " + lastName + " Geburtsdatum: " + birthDate
                + " TelefonNummer: " + phoneNumber + " Adresse: " + street + " " + houseNumber + ", " + postalCode
                + city;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String _newEmail) {
        eMail = _newEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String _NewFisrtName) {
        firstName = _NewFisrtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String _newLastName) {
        lastName = _newLastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String _newBirthdate) {
        birthDate = _newBirthdate;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int _newPhoneNumber) {
        phoneNumber = _newPhoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String _newStreet) {
        street = _newStreet;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int _newHouseNumber) {
        houseNumber = _newHouseNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int _newPostalCode) {
        postalCode = _newPostalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String _newCity) {
        city = _newCity;
    }
}
