import java.util.Scanner;

public class Appointment {

    public Appointment() {

    }

    public void createNewAppointment(){
        Scanner scan = new Scanner(System.in);

        System.out.print("Datum: ");
        String enteredDate = scan.next();

        System.out.print("Zeitraum von: ");
        int enteredTimePeriodStart = scan.nextInt();

        System.out.print("Zeitraum bis: ");
        int enteredTimePeriodEnd = scan.nextInt();

        System.out.print("Gleichzeitig stattfindende Impfungen: ");
        int enteredConcurrentVaccinations = scan.nextInt();

        System.out.print("Zeitabstände zwischen den Terminen (in Minuten): ");
        int enteredTimeIntervalls = scan.nextInt();

        AppointmentModel NewAppointment = new AppointmentModel(enteredDate, enteredTimePeriodStart,
                enteredTimePeriodEnd, enteredConcurrentVaccinations, enteredTimeIntervalls);

        System.out.println(NewAppointment.toString());

        scan.close();

        JSONHelper JSONFile = new JSONHelper();
        JSONFile.enterAppointmentInJSONFile(NewAppointment);
      
    }

}

/*
 * import java.text.SimpleDateFormat; import java.util.Date;
 */

/*
 * String sDate1 = "31/12/1998"; Date date1 = new
 * SimpleDateFormat("dd/MM/yyyy").parse(sDate1); System.out.println(sDate1 +
 * "\t" + date1);
 */