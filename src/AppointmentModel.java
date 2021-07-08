public class AppointmentModel {

    private int id;
    private String date;
    private int timePeriodStart;
    private int timePeriodEnd;
    private int concurrentVaccinations;
    private int timeIntervalls;

    public AppointmentModel(int _id, String _date, int _timePeriodStart, int _timePeriodEnd,
            int _concurrentVaccinations, int _timeIntervalls) {
        this.id = _id;
        this.date = _date;
        this.timePeriodStart = _timePeriodStart;
        this.timePeriodEnd = _timePeriodEnd;
        this.concurrentVaccinations = _concurrentVaccinations;
        this.timeIntervalls = _timeIntervalls;
    }

    public String toString() {
        return "id: " + id + " Datum: " + date + " Zeitraum von: " + timePeriodStart + " Zeitraum bis: " + timePeriodEnd
                + " Gleichzeitige Impfungen: " + concurrentVaccinations + " Zeitabstände in Minuten: " + timeIntervalls;
    }

    public int getId() {
        return id;
    }

    public void setID(int _newId) {
        id = _newId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String _newDate) {
        date = _newDate;
    }

    public int getTimePeriodStart() {
        return timePeriodStart;
    }

    public void setTimePeriodStart(int _newTimePeriodStart) {
        timePeriodStart = _newTimePeriodStart;
    }

    public int getTimePeriodEnd() {
        return timePeriodEnd;
    }

    public void setTimePeriodEnd(int _newTimePeriodEnd) {
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