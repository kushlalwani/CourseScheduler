import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author giris
 */
public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudent;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student){
        connection = DBConnection.getConnection();
        try{
            addStudent = connection.prepareStatement("insert into app.student (studentid,firstname,lastname) values (?,?,?)");
            addStudent.setString(1,student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
        ArrayList<StudentEntry> studentList = new ArrayList<>();
        connection = DBConnection.getConnection();
        try{
            getAllStudent = connection.prepareStatement("select studentid, firstname, lastname from app.student");
            resultSet = getAllStudent.executeQuery();
            
            while(resultSet.next()){
                String sid = resultSet.getString(1);
                String fn = resultSet.getString(2);
                String ln = resultSet.getString(3);
                studentList.add(new StudentEntry(sid,fn,ln));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return studentList;    
    }
    
    public static boolean checkDuplicates(String sid){
        ArrayList<StudentEntry> students = getAllStudents();
        for(StudentEntry s: students){
            if(s.getStudentID().equals(sid)){
                return true;
            }
        }
        return false;
    }
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        try{
            getStudent = connection.prepareStatement("select studentid,firstname,lastname from app.student where studentid = ?");
            getStudent.setString(1,studentID);
            resultSet = getStudent.executeQuery();
            
            resultSet.next();
            return new StudentEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }
    
    public static void dropStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        try{
            dropStudent = connection.prepareStatement("delete from app.student where studentid = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
}
