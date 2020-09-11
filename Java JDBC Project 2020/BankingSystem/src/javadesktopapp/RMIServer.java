package javadesktopapp;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class RMIServer extends UnicastRemoteObject implements RMIInterface {

    static final String DRIVER = "com.mysql.jdbc.Driver"; //Driver
    //static final String DATABASE_URL1 = "jdbc:oracle:thin:@localhost:1521:orcl", "gebre", "gebre12";
    static final String DATABASE_URL = "jdbc:mysql://localhost/student"; //JDBC
    Connection conn = null; // Manages connion
    Statement statement = null; // Query statement
    PreparedStatement ps; //Prepared statement
    ResultSet rs; //Result set

    public RMIServer() throws RemoteException {

    }

    public void message(String m) throws RemoteException {
        System.out.println(m);
    }

    public String login(String UserName, String Password, String Previlage) throws RemoteException {
        String val = "0";
        String uname = "", pass = "", pre = "";

        try {
            Class.forName(DRIVER);
            // Establish connion to database
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            //Create ststement to query Database
            statement = conn.createStatement();
            String query = "SELECT * FROM tblUserAccount WHERE user_name ='" + UserName + "'";
            rs = statement.executeQuery(query);
            while (rs.next()) {
                uname = rs.getString("userName");
                pass = rs.getString("password");
                pre = rs.getString("previlege");
            }
            if ((UserName.intern().equals(uname.intern()) || (Password.intern().equals(pass.intern())) || (Previlage.intern().equals(pre.intern())))) {
                val = "1"; ////User Name Exist
            } else if ((!UserName.intern().equals(uname.intern()) || (!Password.intern().equals(pass.intern())) || (!Previlage.intern().equals(pre.intern())))) {
                val = "0"; //User Name Doesn't Exist
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return val;
    }

    public String registerStudent(String studID, String studName, String sex, String dept, double CGPA) throws RemoteException {
        String val = "";
        String SID = "";
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
            String query = "SELECT *FROM tblStudent WHERE stud_id ='" + studID + "'";
            ps = (PreparedStatement) conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                SID = rs.getString("stud_id");
            }
            if (studID.intern().equals(SID.intern())) {
                val = "0"; //Info Already Exist (Must Not Insert Duplicate Info)
            } else if (!studID.intern().equals(SID.intern())) {
                statement = conn.createStatement();
                String sql = "INSERT INTO tblStudent(stud_id, stud_name, sex, department, cgpa) VALUES ('" + studID + "' , '" + studName + "', '" + sex + "', '" + dept + "', '" + CGPA + "')";
                statement.executeUpdate(sql);
                val = "1"; // Successfully Registered
            }
        } catch (Exception ex) {
            return ex.toString();
        }
        return val;
    }

    public String updateStudent(String studID, String studName, String sex, String dept, double CGPA) throws RemoteException {
        String SID = "";
        String val = "";
        try {
            Class.forName(DRIVER);
            // Establish connion to database
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            String query = "SELECT *FROM tblStudent WHERE stud_id ='" + studID + "'";
            ps = (PreparedStatement) conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                SID = rs.getString("stud_id");
            }
            if (!studID.intern().equals(SID.intern())) {
                val = "0"; //Info Doesn't Exist
            } else if (studID.intern().equals(SID.intern())) {
                ps = (PreparedStatement) conn.prepareStatement("UPDATE  tblStudent SET stud_name ='" + studName + "' WHERE stud_id ='" + studID + "'");
                ps.executeUpdate();
                ps = (PreparedStatement) conn.prepareStatement("UPDATE  tblStudent SET sex ='" + sex + "' WHERE stud_id ='" + studID + "'");
                ps.executeUpdate();
                ps = (PreparedStatement) conn.prepareStatement("UPDATE  tblStudent SET department ='" + dept + "' WHERE stud_id ='" + studID + "'");
                ps.executeUpdate();
                ps = (PreparedStatement) conn.prepareStatement("UPDATE  tblStudent SET cgpa ='" + CGPA + "' WHERE stud_id ='" + studID + "'");
                ps.executeUpdate();
                val = "1"; //Successfully Updated
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return val;
    }

    public String deleteStudent(String studID) throws RemoteException {
        String SID = "";
        String val = "";
        // Connection to database  query database
        try {
            Class.forName(DRIVER);
            // Establish conn to database
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            // Create Statement for querying database
            String query = "SELECT *FROM tblStudent WHERE stud_id='" + studID + "'";
            ps = (PreparedStatement) conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                SID = rs.getString("stud_id");
            }
            if (!studID.intern().equals(SID.intern())) {
                val = "0"; //Stud Info Not Found
            } else if (studID.intern().equals(SID.intern())) {
                ps = (PreparedStatement) conn.prepareStatement("DELETE FROM  tblStudent WHERE stud_id = '" + studID + "'");
                ps.executeUpdate();
                val = "1"; //Student Successfully Deleted
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return val;
    }

    public ArrayList searchStudent(String studID) throws RemoteException {
        ArrayList array = new ArrayList();
        //String[] store = new String[5];
        // ResultSetMetaData rsmdata;

        try {
            Class.forName(DRIVER);
            // Establish conn to database
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
            String sql = "SELECT * FROM tblStudent WHERE stud_id='" + studID + "'";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                // Add 7 elements to the array list
                for (int i = 0; i < 5; i++) {
                    array.add(rs.getString(i + 1));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return array;
    }

    public ArrayList searchStudentByID(int i, String studID) throws RemoteException {
        ArrayList array = new ArrayList();
        //String[] store = new String[6];
        ResultSetMetaData rsmdata;

        try {
            Class.forName(DRIVER);
            // establish conn to database
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
            String sql = "SELECT stud_id, stud_name, sex, department, cgpa FROM tblStudent WHERE stud_id = '" + studID + "'";
            statement.execute(sql);
            rs = statement.getResultSet();
            rsmdata = rs.getMetaData();
            while (rs.next()) {
                array.add(rs.getObject(i + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public ArrayList viewAllStudents(int i) throws RemoteException {
        ArrayList array = new ArrayList();
        //String[] store = new String[6];
        ResultSetMetaData rsmdata;

        try {
            Class.forName(DRIVER);
            // establish conn to database
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
            String sql = "SELECT stud_id, stud_name, sex, department, cgpa FROM tblStudent ORDER BY cgpa DESC";
            statement.execute(sql);
            rs = statement.getResultSet();
            rsmdata = rs.getMetaData();
            while (rs.next()) {
                array.add(rs.getObject(i + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void main(String args[]) throws RemoteException {

        try {
            Registry r = LocateRegistry.createRegistry(3134);
            RMIServer s = new RMIServer();
            r.rebind("x", s);
            System.out.println("The Server is running!!!");
        } catch (RemoteException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public int message(int x, int y) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
