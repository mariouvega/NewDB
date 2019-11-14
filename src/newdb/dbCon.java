/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newdb;

import java.sql.*;

/**
 *
 * @author David
 */


public class dbCon {

    public void runMe(String host, String database, String user, String password)
            throws Exception {

        /* run driverTest method shown below */
        driverTest();

        /* make the connection to the database */
        Connection conMe = makeCon(host, database, user, password);

        /* now run a select query of the intended database */
        exeQuery(conMe, "SELECT * FROM Students");

        /* close the database */
        conMe.close();
    }

    protected void driverTest() throws Exception {

        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("MySQL Driver Found");
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
                                System.out.println(id + " " + name + " " + age + " " + gpa);
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

