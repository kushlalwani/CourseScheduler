
import java.util.Calendar;
import java.sql.Timestamp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author giris
 */
public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private Timestamp timestamp;
    //overloaded constructor
    

    public ScheduleEntry(String semester, String courseCode, String studentID, String status, Timestamp timestamp) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.timestamp = timestamp;
    }
    
    public ScheduleEntry(String semester, String courseCode, String studentID, String status) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    }
    
    public String getSemester() {
        return semester;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public String getStudentID() {
        return studentID;
    }
    
    public String getStatus() {
        return status;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
