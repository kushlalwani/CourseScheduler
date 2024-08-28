import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author giris
 */
public class ExtraQueries {
    private static Connection connection;
    private static PreparedStatement resetSemester;
    private static PreparedStatement resetSchedule;
    private static PreparedStatement resetStudent;
    private static PreparedStatement resetCourse;
    private static PreparedStatement resetClass;
    
     public static void resetSemester(){
        connection = DBConnection.getConnection();
        try{
            resetSemester = connection.prepareStatement("delete from app.semester");
            resetSemester.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();   
        }
    }
    public static void resetSchedule(){
        connection = DBConnection.getConnection();
        try{
            resetSchedule = connection.prepareStatement("delete from app.schedule");
            resetSchedule.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();   
        }
    }
         public static void resetStudent(){
        connection = DBConnection.getConnection();
        try{
            resetStudent = connection.prepareStatement("delete from app.student");
            resetStudent.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();   
        }
    }
    public static void resetCourse(){
        connection = DBConnection.getConnection();
        try{
            resetCourse = connection.prepareStatement("delete from app.course");
            resetCourse.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();   
        }
    }
    public static void resetClass(){
        connection = DBConnection.getConnection();
        try{
            resetClass = connection.prepareStatement("delete from app.class");
            resetClass.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();   
        }
    }
    
    public static void resetAll(){
        resetSemester();
        resetSchedule();
        resetStudent();
        resetCourse();
        resetClass();
    }
     
}
