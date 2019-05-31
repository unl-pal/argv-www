package ch.florianluescher.salary.timetracker;

public class TimeTrackingInformation {
    private final int billableHours;
    private final int unbillableHours;

    public TimeTrackingInformation(int billableHours, int unbillableHours) {
        this.billableHours = billableHours;
        this.unbillableHours = unbillableHours;
    }

    public int getBillableHours() {
        return billableHours;
    }

    public int getUnbillableHours() {
        return unbillableHours;
    }

    public int getTotalHours() {
        return billableHours + unbillableHours;
    }
}
