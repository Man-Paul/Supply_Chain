package com.example.supplychain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;

public class Login {

    DatabaseConnection dbconn = new DatabaseConnection();

    public static byte[] getSHA(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    static String getEncryptedPassword(String password){
        String encryptedPassword = "";
        BigInteger number = new BigInteger(1,getSHA(password));
        StringBuilder hexString = new StringBuilder(number.toString(16));
        encryptedPassword = hexString.toString();
        return encryptedPassword;
    }

    public String CustomerName,CustomerEmailId;
    public boolean customerLogin(String email, String password){
        try{
            String query = String.format("Select * FROM customer WHERE email = '%s' and password = '%s'", email, getEncryptedPassword(password));
            ResultSet rs = dbconn.getQueryTable(query);
            if(rs==null)
                return false;
            if(rs.next()){
               CustomerName = rs.getString("first_name");
               CustomerEmailId = rs.getString("email");
               if(rs.getString("last_name")!=null)
                   CustomerName += rs.getString("last_name");
               return true;
            }
            else
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

//    public static void main(String[] args) {
//        System.out.println(getEncryptedPassword("Parousia@1"));
//    }
}
