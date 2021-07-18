import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Administrator Admin = new Administrator();
        Admin.adminLogOut();
        welcomeUser();
        userOrAdmin();
    }

    public static void welcomeUser() {
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Hallo und Willkommen bei der VaccinationAppointmentApp!");
        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------------");
    }

    public static void userOrAdmin() {
        Scanner scan = new Scanner(System.in);

        System.out.println("");
        System.out.println("Geben Sie eine Zahl ein, um Ihre nächste Option auszuwählen.");
        System.out.println("");

        AppointmentHelper AppointmentHelper = new AppointmentHelper();
        if (AppointmentHelper.areThereFreeAppointments()) {
            System.out.println("1) Termin buchen");
        } else {
            System.out.println("Momentan sind keine freien Termine verfügbar. Wollen Sie sich in die Warteliste eintragen?");
            System.out.println("");
            System.out.println("1) in die Warteliste eintragen");
        }

        System.out.println("2) als Administrator fortfahren");

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
        AppointmentHelper AppointmentHelper = new AppointmentHelper();
        User User = new User();
        if (AppointmentHelper.areThereFreeAppointments()) {
            User.userOptions();
        } else {
            User.registerForWaitingList();
        }
    }
}