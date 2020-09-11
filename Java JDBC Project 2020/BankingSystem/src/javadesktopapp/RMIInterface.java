package javadesktopapp;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author getSmartA
 */
public interface RMIInterface extends Remote {

    int message(int x, int y) throws RemoteException;
    
    //Register Student
    String registerStudent(String studID, String studName,String sex, String dept, double CGPA) throws RemoteException;
    //Update Student
    String updateStudent(String studID, String studName,String sex, String dept, double CGPA) throws RemoteException;
    //Delete Student
    String deleteStudent(String studID) throws RemoteException;
    //Search Student
    ArrayList searchStudent(String ID) throws RemoteException;
    //Login to the System
    String login(String UserName, String Password, String Previlage) throws RemoteException;
        //Search Student
    ArrayList viewAllStudents(int i) throws RemoteException;
    ArrayList searchStudentByID(int i, String studID) throws RemoteException;

}