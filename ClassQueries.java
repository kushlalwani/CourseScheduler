import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author giris
 */
public class ClassQueries {
    private static Connection connection;
    private static PreparedStatement addClass;
    private static PreparedStatement getCourseCodes;
    private static PreparedStatement getCapacity;
    private static ResultSet resultSet;
    private static PreparedStatement dropCourse;
    
    
    public static void addClass(ClassEntry clss){
        connection = DBConnection.getConnection();
        try{
            addClass = connection.prepareStatement("insert into app.class (semester, courseCode, seats) values (?, ?, ?)");
            addClass.setString(1, clss.getSemester());
            addClass.setString(2, clss.getCourseCode());
            addClass.setInt(3, clss.getSeats());
            addClass.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester){
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try{
            getCourseCodes = connection.prepareStatement("select coursecode from app.class where semester = ?");
            getCourseCodes.setString(1, semester);
            resultSet = getCourseCodes.executeQuery();
            
            while(resultSet.next()){
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return courseCodes;
    }
    
    public static int getClassSeats(String semester, String courseCode){
        connection = DBConnection.getConnection();
        int count = 0;
        try{
            getCapacity = connection.prepareStatement("select seats from app.class where semester = ? and coursecode = ?");
            getCapacity.setString(1, semester);
            getCapacity.setString(2, courseCode);
            resultSet = getCapacity.executeQuery();
            
            while(resultSet.next())
                count = resultSet.getInt(1);
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return count;
    }
    
    public static boolean checkDuplicates(String code,String currSem){
        ArrayList<String> codes = getAllCourseCodes(currSem);
        for(String c: codes){
            if(c.equals(code)){
                return true;
            }
        }
        return false;
    }
    
    public static void dropClass(String semester,String courseCode){
        connection = DBConnection.getConnection();
        try{
            dropCourse = connection.prepareStatement("delete from app.class where semester = ? and coursecode = ?");
            dropCourse.setString(1,semester);
            dropCourse.setString(2,courseCode);
            dropCourse.executeUpdate();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
