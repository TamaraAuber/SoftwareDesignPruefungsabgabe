import java.time.LocalTime;
import java.time.LocalDate;

public class AppointmentModel {

    private int id;
    private LocalDate date;
    private LocalTime timePeriodStart;
    private LocalTime timePeriodEnd;
    private int concurrentVaccinations;
    private int timeIntervalls;

    public AppointmentModel(int _id, LocalDate _date, LocalTime _timePeriodStart, LocalTime _timePeriodEnd,
            int _concurrentVaccinations, int _timeIntervalls) {
        this.id = _id;
        this.date = _date;
        this.timePeriodStart = _timePeriodStart;
        this.timePeriodEnd = _timePeriodEnd;
        this.concurrentVaccinations = _concurrentVaccinations;
        this.timeIntervalls = _timeIntervalls;
    }

    public String toString() {
        return "ID: " + id + " Datum: " + date + " Zeitraum von: " + timePeriodStart + " Zeitraum bis: " + timePeriodEnd
                + " Gleichzeitige Impfungen: " + concurrentVaccinations + " Zeitabstände in Minuten: " + timeIntervalls;
    }

    public int getId() {
        return id;
    }

    public void setId(int _newId) {
        id = _newId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate _newDate) {
        date = _newDate;
    }

    public LocalTime getTimePeriodStart() {
        return timePeriodStart;
    }

    public void setTimePeriodStart(LocalTime _newTimePeriodStart) {
        timePeriodStart = _newTimePeriodStart;
    }

    public LocalTime getTimePeriodEnd() {
        return timePeriodEnd;
    }

    public void setTimePeriodEnd(LocalTime _newTimePeriodEnd) {
        timePeriodEnd = _newTimePeriodEnd;
    }

    public int getConcurrentVaccinations() {
        return concurrentVaccinations;
    }

    public void setConcurrentVaccinations(int _newConcurrentVaccinations) {
        concurrentVaccinations = _newConcurrentVaccinations;
    }

    public int getTimeIntervalls() {
        return timeIntervalls;
    }

    public void setTimeIntervalls(int _newTimeIntervalls) {
        timeIntervalls = _newTimeIntervalls;
    }

}