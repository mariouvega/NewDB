/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newdb;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author David
 */


public class dbCon {
    
    Scanner scan = new Scanner(System.in);
    boolean flag = true;
    int userInput = 0;
    

    public void runMe(String host, String database, String user, String password)
            throws Exception {

        /* run driverTest method shown below */
        driverTest();

        /* make the connection to the database */
        Connection conMe = makeCon(host, database, user, password);

        /* now run a select query of the intended database */
        exeQuery(conMe, "SELECT * FROM Students");
        
        do {
            System.out.println("What would you like to do?");
            System.out.println("Enter 1 to show the class roster.");
            System.out.println("Enter 2 to select a student by ID number.");
            System.out.println("Enter 3 to add a student to the roster.");
            System.out.println("Enter 4 to update a students information.");
            System.out.println("Enter 5 to quit.");
            
            userInput = scan.nextInt();
            
            switch(userInput){
                case 1:
                    exeQuery(conMe, "SELECT * FROM Students");
                    break;
                case 2:
                    System.out.println("Enter ID number");
                    break;
                case 3:
                    System.out.println("Add student");
                    break;
                case 4:
                    System.out.println("Update student");
                    break;
                case 5:
                    flag = false;
                    break;
            }
            
        }while(flag);

        /* close the database */
        conMe.close();
    }

    protected void driverTest() throws Exception {

        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("MySQL Driver Found");
            System.out.println("Welcome");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found ... ");
            throw (e);
        }
    }

    protected Connection makeCon (String host, String database, String user, String password)
          throws Exception {

            String url = "";
            try {
                url = "jdbc:mysql://" + host + ":3306/" + database;
                  Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established to " + url + "...");
            return con;
            } catch (java.sql.SQLException e) {
            System.out.println("Connection couldn't be established to " + url);
            throw (e);
            }
    } 

    protected void exeQuery(Connection con, String sqlStatement)
          throws Exception {

                try {
                        Statement cs = con.createStatement();
                        ResultSet sqls = cs.executeQuery(sqlStatement);

                        while (sqls.next()) {
                                 
                                int id = sqls.getInt("idStudents");
                                String name = sqls.getString("studentName");
                                int age = sqls.getInt("age");
                                double gpa = sqls.getDouble("gpa");
                                System.out.println("Student Details");
                                System.out.println("ID:" + id + " Name:" + name + " Age:" + age + " GPA:" + gpa);
                        }

                        sqls.close();

                } catch (SQLException e) {
                        System.out.println ("Error executing sql statement");
                        throw (e);
                }
    }
    
        public static void main (String args[]) throws Exception {
        
            dbCon a = new dbCon();
            a.runMe("dbjava.c96ahlzpethg.us-east-1.rds.amazonaws.com", "dbjava", "dbjava", "mypassword");
           
            System.out.println ("usage: java dbCon host database user passwd");
        }
    }

