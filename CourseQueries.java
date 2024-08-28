
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author giris
 */
public class CourseQueries {
    private static Connection connection;
    
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseList;
    private static ResultSet resultSet;


    public static void addCourse(CourseEntry course){
        connection = DBConnection.getConnection();
        try{
            addCourse = connection.prepareStatement("insert into app.course (courseCode, description) values (?, ?)");
            addCourse.setString(1, course.getCourseCode());
            addCourse.setString(2, course.getDescription());

            addCourse.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<String> getAllCourseCodes(){
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try{
            getCourseList = connection.prepareStatement("select coursecode from app.course");
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next()){
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return courseCodes;
    }
    
    public static boolean checkDuplicates(String code){
        ArrayList<String> codes = getAllCourseCodes();
        for(String c:codes){
            if(c.equals(code)){
                return true;
            }
        }
        return false;
    }
}