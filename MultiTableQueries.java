import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author giris
 */
public class MultiTableQueries {
    private static Connection connection;
    private static PreparedStatement allClassDescription;
    private static PreparedStatement scheduledByClass;
    private static PreparedStatement waitlistedByClass;
    private static ResultSet resultSet;
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> classDesc = new ArrayList<ClassDescription>();
        try
        {
            allClassDescription = connection.prepareStatement("select app.class.courseCode,description,seats from app.class,app.course where semester = ? and app.class.courseCode = app.course.courseCode order by app.class.courseCode");
            allClassDescription.setString(1, semester);
            resultSet = allClassDescription.executeQuery();
            
            while(resultSet.next())
            {
                String code = resultSet.getString(1);
                String desc = resultSet.getString(2);
                int seat = resultSet.getInt(3);
                classDesc.add(new ClassDescription(code,desc,seat));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return classDesc;
    }
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> enrolledStudents = new ArrayList<>();
        try{
            scheduledByClass = connection.prepareStatement("select app.schedule.studentid,firstname,lastname from app.student,app.schedule where semester = ? and app.schedule.studentid = app.student.studentid and coursecode = ? and status = ?");
            scheduledByClass.setString(1,semester);
            scheduledByClass.setString(2,courseCode);
            scheduledByClass.setString(3, "Scheduled");
            resultSet = scheduledByClass.executeQuery();
            
            while(resultSet.next()){
                String id = resultSet.getString(1);
                String fn = resultSet.getString(2);
                String ln = resultSet.getString(3);
                enrolledStudents.add(new StudentEntry(id,fn,ln));
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return enrolledStudents;
    }
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> enrolledStudents = new ArrayList<>();
        try{
            waitlistedByClass = connection.prepareStatement("select app.schedule.studentid,firstname,lastname from app.student,app.schedule where semester = ? and app.schedule.studentid = app.student.studentid and coursecode = ? and status = ?");
            waitlistedByClass.setString(1,semester);
            waitlistedByClass.setString(2,courseCode);
            waitlistedByClass.setString(3, "Waitlisted");
            resultSet = waitlistedByClass.executeQuery();
            
            while(resultSet.next()){
                String id = resultSet.getString(1);
                String fn = resultSet.getString(2);
                String ln = resultSet.getString(3);
                enrolledStudents.add(new StudentEntry(id,fn,ln));
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return enrolledStudents;
    }
}
