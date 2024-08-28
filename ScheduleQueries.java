import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author giris
 */
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getWaitlist;
    private static PreparedStatement dropStudentSchedule;
    private static PreparedStatement dropSchedule;
    private static PreparedStatement updateSchedule;
    private static PreparedStatement getScheduled;
    private static ResultSet resultSet;
    
    
    public static void addScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try{
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, studentid, coursecode, status, timestamp) values (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getStudentID());
            addScheduleEntry.setString(3, entry.getCourseCode());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        connection = DBConnection.getConnection();
        try{
            getScheduleByStudent = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where studentid = ? and semester = ?");
            getScheduleByStudent.setString(1, studentID);
            getScheduleByStudent.setString(2, semester);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next()){
                String sem = resultSet.getString(1);
                String code = resultSet.getString(2);
                String id = resultSet.getString(3);
                String status = resultSet.getString(4);
                Timestamp time = resultSet.getTimestamp(5);
                schedule.add(new ScheduleEntry(sem, code, id, status, time));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();   
        }
        return schedule;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode){
        int count = 0;
        connection = DBConnection.getConnection();
        try{
            getScheduledStudentCount = connection.prepareStatement("select count(studentid) from app.schedule where semester = ? and courseCode = ?");
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            
            while(resultSet.next()){
                count = resultSet.getInt(1);
            }
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return count;
    }
    
    public static boolean checkDuplicates(String currSem,String code,String sid){
        ArrayList<ScheduleEntry> schedule = getScheduleByStudent(currSem,sid);
        for(ScheduleEntry s:schedule){
            if(s.getCourseCode().equals(code)){
                return true;
            }
        }
        return false;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> enrolled = new ArrayList<ScheduleEntry>();
        try{
            getScheduled = connection.prepareStatement("select studentid, timestamp from app.schedule where semester = ? and courseCode = ? and status = ?");
            getScheduled.setString(1, semester);
            getScheduled.setString(2, courseCode);
            getScheduled.setString(3, "Scheduled");
            resultSet = getScheduled.executeQuery();
            
            while(resultSet.next()){
                enrolled.add(new ScheduleEntry(semester, courseCode, resultSet.getString(1), "Scheduled", resultSet.getTimestamp(2)));
            }  
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return enrolled;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> waitlisted = new ArrayList<ScheduleEntry>();
        try{
            getWaitlist = connection.prepareStatement("select studentid, timestamp from app.schedule where semester = ? and courseCode = ? and status = ?");
            getWaitlist.setString(1, semester);
            getWaitlist.setString(2, courseCode);
            getWaitlist.setString(3, "Waitlisted");
            resultSet = getWaitlist.executeQuery();
            
            while(resultSet.next()){
                waitlisted.add(new ScheduleEntry(semester, courseCode, resultSet.getString(1), "Waitlisted", resultSet.getTimestamp(2)));
            }
            
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return waitlisted;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
        connection = DBConnection.getConnection();
        try{
            dropStudentSchedule = connection.prepareStatement("DELETE FROM APP.SCHEDULE WHERE SEMESTER = ? AND STUDENTID = ? AND COURSECODE = ?");
            dropStudentSchedule.setString(1, semester);
            dropStudentSchedule.setString(2, studentID);
            dropStudentSchedule.setString(3, courseCode);
            dropStudentSchedule.executeUpdate();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try{
            dropSchedule = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ?");
            dropSchedule.setString(1, semester);
            dropSchedule.setString(2, courseCode);
            dropSchedule.executeUpdate();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try{
            updateSchedule = connection.prepareStatement("update app.schedule set status = ? where studentid = ? and coursecode = ?");
            updateSchedule.setString(1,"Scheduled");
            updateSchedule.setString(2, entry.getStudentID());
            updateSchedule.setString(3, entry.getCourseCode());
            updateSchedule.executeUpdate();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
}
