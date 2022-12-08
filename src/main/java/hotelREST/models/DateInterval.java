package hotelREST.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateInterval {

    private Long id;
    private Date startDate;
    private Date endDate;

    public DateInterval() {
    }

    public DateInterval(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DateInterval(String startDate, String endDate) throws ParseException {
        this.startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
        this.endDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
    }

    public DateInterval(long id, String startDate, String endDate) throws ParseException {
        this.id = id;
        this.startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
        this.endDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    //check if dateIntervals overlaps
    public boolean isOverlapping(DateInterval dateInterval) {
        return startDate.before(dateInterval.startDate) && endDate.after(dateInterval.startDate) ||
                startDate.before(dateInterval.endDate) && endDate.after(dateInterval.endDate) ||
                startDate.before(dateInterval.startDate) && endDate.after(dateInterval.endDate) ||
                startDate.after(dateInterval.startDate) && endDate.before(dateInterval.endDate);
    }

    public int nbNuits()
    {
        return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    public String toString() {
        return "soap.DateInterval [startDate=" + startDate.toString() + ", endDate=" + endDate + "]";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
