public class AppointmentModel {

    private int id;
    private String date;
    private int timePeriodStart;
    private int timePeriodEnd;
    private int concurrentVaccinations;
    private int freeVaccinations;
    private int timeIntervalls;

    public AppointmentModel(int _id, String _date, int _timePeriodStart, int _timePeriodEnd,
            int _concurrentVaccinations, int _freeVaccinations, int _timeIntervalls) {
        this.id = _id;
        this.date = _date;
        this.timePeriodStart = _timePeriodStart;
        this.timePeriodEnd = _timePeriodEnd;
        this.concurrentVaccinations = _concurrentVaccinations;
        this.freeVaccinations = _freeVaccinations;
        this.timeIntervalls = _timeIntervalls;
    }

    public String toString() {
        return "ID: " + id + " Datum: " + date + " Zeitraum von: " + timePeriodStart + " Zeitraum bis: " + timePeriodEnd
                + " Gleichzeitige Impfungen: " + concurrentVaccinations + " frei verfügbare Impfungen "
                + freeVaccinations + " Zeitabstände in Minuten: " + timeIntervalls;
    }

    public int getId() {
        return id;
    }

    public void setId(int _newId) {
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

    public int getFreeVaccinations() {
        return freeVaccinations;
    }

    public void setFreeVaccinations (int _newFreeVaccinations) {
        freeVaccinations = _newFreeVaccinations;
    }

    public int getTimeIntervalls() {
        return timeIntervalls;
    }

    public void setTimeIntervalls(int _newTimeIntervalls) {
        timeIntervalls = _newTimeIntervalls;
    }

}