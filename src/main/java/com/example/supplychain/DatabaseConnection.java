package com.example.supplychain;
import java.sql.*;

public class DatabaseConnection {
    //    important things to connect to database
    //        --db url(where your database is running, it maybe local or cloud)
    //        --username & Password
    private static final String db_url = "jdbc:mysql://localhost:3306/supply chain";
    private static final String username = "root";
    private static final String password = "Parousia@1";

    public Statement getStatement(){
        Statement statement = null;
        Connection conn;
        try{
            conn = DriverManager.getConnection(db_url, username, password);
            statement = conn.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
        return statement;
    }

    public ResultSet getQueryTable(String query){
        Statement statement = getStatement();
        try{
            return statement.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int insertOrderData(String query){ //int - no.of rows affected
        Statement statement = getStatement();
        try{
            return statement.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

//    public static void main(String[] args) {
//        DatabaseConnection dbconn = new DatabaseConnection();
//
//        String query = "SELECT * FROM CUSTOMER";
//        ResultSet rs = dbconn.getQueryTable(query);
//        try{
//            while(rs!=null && rs.next()){
//                System.out.println("Fetched result");
//                System.out.println("cid:"+rs.getInt("cid") + " " +
//                        "Name:"+rs.getString("first_name") + " " +
//                        "EmailID:"+rs.getString("email"));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
