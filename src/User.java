import java.util.Scanner;

public class User {

    public User() {

    }

    public void registerForAppointment() {

    }

    public void registerForWaitingList() {

    }

    private RegistrationModel enterRegistrationData() {
        Scanner scan = new Scanner(System.in);

        System.out.print("E-Mail: ");
        String enteredEMail = scan.nextLine();

        System.out.print("Vorname: ");
        String enteredFirstName = scan.nextLine();

        System.out.print("Nachname: ");
        String enteredLastName = scan.nextLine();

        System.out.print("Geburtsdatum: ");
        String enteredBirthDate = scan.nextLine();

        System.out.print("Telefonnummer: ");
        int enteredPhoneNumber = scan.nextInt();

        System.out.println("Adresse:");

        System.out.print("Straße: ");
        String enteredStreet = scan.nextLine();

        System.out.print("Hausnummer: ");
        int enteredHouseNumber = scan.nextInt();

        System.out.print("Postleitzahl: ");
        int enteredPostalCode = scan.nextInt();

        System.out.print("Stadt: ");
        String enteredCity = scan.nextLine();

        RegistrationModel Registration = new RegistrationModel(enteredEMail, enteredFirstName, enteredLastName, enteredBirthDate,
                enteredPhoneNumber, enteredStreet, enteredHouseNumber, enteredPostalCode, enteredCity);

        scan.close();

        return Registration;
    }
}
