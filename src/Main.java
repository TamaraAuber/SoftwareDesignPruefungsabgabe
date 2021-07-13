import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Administrator Admin = new Administrator();
        Admin.adminLogOut();
        welcomeUser();
        userOrAdmin();
    }

    public static void welcomeUser() {
        System.out.println("Henlo bei der superduper besten VaccinationAppointmenApp :-)");
    }

    public static void userOrAdmin() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Termin buchen oder als Administrator fortfahren?");
        System.out.println("Wählen sie dazu eine der Nummern aus");

        Appointment Appointment = new Appointment();
        if (Appointment.areThereFreeAppointments()) {
            System.out.println("1) Termin buchen");
        } else {
            System.out.println("Keine freien Termine verfügbar");
            System.out.println("1) in die Warteliste eintragen");
        }

        System.out.println("2) als Administrator anmelden");

        int selectedOption = scan.nextInt();

        switch (selectedOption) {
            case 1:
                chooseCase1ForUser();
                break;
            case 2:
                System.out.println("OptionLogIn");
                Administrator Admin = new Administrator();
                Admin.logIn();
                break;
        }

        scan.close();
    }

    private static void chooseCase1ForUser() {
        Appointment Appointment = new Appointment();
        if (Appointment.areThereFreeAppointments()) {
            System.out.println("Option Termin buchen");
        } else {
            System.out.println("Option Warteliste");
        }
    }
}