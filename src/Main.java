import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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
        System.out.println("1) Termin buchen");
        System.out.println("2) als Administrator anmelden");

        int selectedOption = scan.nextInt();

        switch (selectedOption) {
            case 1:
                System.out.println("OptionTerminBuchen");
                break;
            case 2:
                System.out.println("OptionLogIn");
                Administrator Admin = new Administrator();
                Admin.logIn();
                break;
        }

        scan.close();
    }
}