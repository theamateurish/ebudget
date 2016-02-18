package utility;

//~--- JDK imports ------------------------------------------------------------


public class JspCalendar {
    private java.util.Calendar calendar = null;

    public JspCalendar() {
        calendar.setTime(new java.util.Date());
    }

    public JspCalendar(java.util.Date petsa) {
        calendar = java.util.Calendar.getInstance();
        calendar.setTime(petsa);
    }

    public JspCalendar(short hour, short minute, short second) {
        calendar = java.util.Calendar.getInstance();
        calendar.set(1970, 0, 1, hour, minute, second);
    }

    public JspCalendar(short month, short day, int year) {
        calendar = java.util.Calendar.getInstance();
        calendar.set(year, month, day);
    }

    public JspCalendar(int year, short month, short day, short hour, short minute, short second) {
        calendar = java.util.Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
    }

    public int getYear() {
        return calendar.get(java.util.Calendar.YEAR);
    }
    
    public void setYear(int value) {
        calendar.set(java.util.Calendar.YEAR, value);
    }
    

    public String getMonth() {
        int      m      = getMonthInt();
        String[] months = new String[] {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
            "November", "December"
        };

        if (m > 12) {
            return "Unknown to Man";
        }

        return months[m - 1];
    }

    public String getDay() {
        int      x    = getDayOfWeek();
        String[] days = new String[] {
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        };

        if (x > 7) {
            return "Unknown to Man";
        }

        return days[x - 1];
    }

    public long getTimeDay() {
        return calendar.getTimeInMillis();//calendar.getTime();
    }
    
    public int getMonthInt() {
        return 1 + calendar.get(java.util.Calendar.MONTH);
    }

    /**
     * 
     * @param value numeric value for equevalent to month.
     */
    public void setMonth(int value) {
        calendar.set(java.util.Calendar.MONTH, value);
    }
    
    public String getDateFormated(String pattern) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(pattern);
        
        return formatter.format(calendar.getTime()); //getMonthInt() + "/" + getDayOfMonth() + "/" + getYear();
    }

    public java.util.Date getCurrentDate() {
        calendar.setTime(new java.util.Date());

        return calendar.getTime();
    }

    public java.util.Date getNextDate() {
        calendar.set(java.util.Calendar.DAY_OF_MONTH, getDayOfMonth() + 1);

        return calendar.getTime();
    }

    public java.util.Date getPrevDate() {
        calendar.set(java.util.Calendar.DAY_OF_MONTH, getDayOfMonth() - 1);

        return calendar.getTime();
    }

    public String getTime() {
        return getHour() + ":" + getMinute() + ":" + getSecond();
    }

    public int getDayOfMonth() {
        return calendar.get(java.util.Calendar.DAY_OF_MONTH);
    }

    public void setDayOfMonth(int value) {
        calendar.set(java.util.Calendar.DAY_OF_MONTH, value);
    }
    
    public int getDayOfYear() {
        return calendar.get(java.util.Calendar.DAY_OF_YEAR);
    }

    public int getWeekOfYear() {
        return calendar.get(java.util.Calendar.WEEK_OF_YEAR);
    }

    public int getWeekOfMonth() {
        return calendar.get(java.util.Calendar.WEEK_OF_MONTH);
    }

    public int getDayOfWeek() {
        return calendar.get(java.util.Calendar.DAY_OF_WEEK);
    }

    public int getHour() {
        return calendar.get(java.util.Calendar.HOUR_OF_DAY);
    }

    public void setTime(int hour, int minute, int second) {
        calendar.set(java.util.Calendar.HOUR_OF_DAY, hour);
        calendar.set(java.util.Calendar.MINUTE,minute);
        calendar.set(java.util.Calendar.SECOND, second);
    }
    
    public int getMinute() {
        return calendar.get(java.util.Calendar.MINUTE);
    }

    public int getSecond() {
        return calendar.get(java.util.Calendar.SECOND);
    }

    public int getEra() {
        return calendar.get(java.util.Calendar.ERA);
    }

    public String getUSTimeZone() {
        String[] zones = new String[] {
            "Hawaii", "Alaskan", "Pacific", "Mountain", "Central", "Eastern"
        };

        return zones[10 + getZoneOffset()];
    }

    public int getZoneOffset() {
        return calendar.get(java.util.Calendar.ZONE_OFFSET) / (60 * 60 * 1000);
    }

    public int getDSTOffset() {
        return calendar.get(java.util.Calendar.DST_OFFSET) / (60 * 60 * 1000);
    }

    public int getAMPM() {
        return calendar.get(java.util.Calendar.AM_PM);
    }
    
    /**
     * Represent to add number of day(s) in a given value. It can be also subtract 
     * in number of day(s) when you pass a negative value.
     * 
     * @param day Can be positive (+) or negative (-) value. Positive add new value, 
     * negative subtract current value.
     */
    public void plusDay(int day) {
        calendar.set(java.util.Calendar.DAY_OF_MONTH, day + calendar.get(java.util.Calendar.DAY_OF_MONTH));
    }
    
    public void plusMinute(int minute) {
        calendar.set(java.util.Calendar.MINUTE, minute);
    }
    
    /**
     * Represent to add number of month(s) in a given value. It can be also subtract 
     * in number of month(s) when you pass a negative value.
     * 
     * @param month Can be positive (+) or negative (-) value. Positive add new value, 
     * negative subtract current value.
     */
    public void plusMonth(int month) {
        calendar.set(java.util.Calendar.MONTH, month + calendar.get(java.util.Calendar.MONTH));
    }

    /**
     * Represent to add number of year(s) in a given value. It can be also subtract 
     * in number of year(s) when you pass a negative value.
     * 
     * @param year Can be positive (+) or negative (-) value. Positive add new value, 
     * negative subtract current value.
     */
    public void plusYear(int year) {
        calendar.set(java.util.Calendar.YEAR, year + calendar.get(java.util.Calendar.YEAR));        
    }
    public java.util.Date getNewDate() {
        return calendar.getTime();
    }
        
}

