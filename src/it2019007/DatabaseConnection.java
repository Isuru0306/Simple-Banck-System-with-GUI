/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it2019007;
import java.sql.*;
import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.JOptionPane;

/**
 *
 * @author isuru
 */
public class DatabaseConnection {
    
    final String JDBCdriver =  "com.mysql.jdbc.Driver";
    final String DBurl = "jdbc:mysql://localhost:3306/";
    final String DBname = "it2019007"; 
    final String USER = "root";
    final String PASS = "";
    
    private Connection con = null;
    private Statement stat = null;
    private PreparedStatement ps = null;
    private ResultSet result = null;
    
    private  boolean connectionTrue = false;
    
    
    private String userTableName = "users";
    private String customerTableName = "customer";
    private String traDtailsTableName = "transactiond";
    private String userLoginTableName = "userlogin";
    
    
    
    
    DatabaseConnection(){
         
    }
    
    private void createConnection(){
         try{
            Class.forName(JDBCdriver);
            this.con = DriverManager.getConnection(this.DBurl+ this.DBname, this.USER, this.PASS);
            if(con != null){ 
                connectionTrue = true; 
                System.out.println("Connection successfully");
            }
            

        }catch( ClassNotFoundException | SQLException e){
            System.out.println("Error Connection " + e);
            if(con == null && !connectionTrue){
                connectionTrue = true;
                createDatabase();
                createUserTable();
                createCustomerTable();
                creatTransactionReportsTable();
                creatLoginRecordsTable();
                insertDataForUserTable("isuru", "isuru@gmai.com", "0785884231", "isuru","IT2019007");
            }
        }
    }
    
    private void createDatabase(){
         try{ 
            Class.forName(JDBCdriver);
            this.con = DriverManager.getConnection(this.DBurl, this.USER, this.PASS);
            
            String mysql = "CREATE DATABASE "+this.DBname;
            this.stat = con.createStatement();
            stat.executeUpdate(mysql);
            System.out.println("database created  successfully");
            con.close();
            stat.close();
            
        }catch( ClassNotFoundException | SQLException e){
            System.out.println("Error Connection and can't create database" + e);
        }
    }


    private void createUserTable(){
        
        try{
            createConnection();
            String sql = "CREATE TABLE "+ userTableName +"(" +
                         "name VARCHAR(50) NOT NULL," +
                         "email VARCHAR(50) NOT NULL," +
                         "telPhone VARCHAR(15) NOT NULL," +
                         "userName VARCHAR(15) NOT NULL," +
                         "pass VARCHAR(16) NOT NULL," +
                         "PRIMARY KEY(userName)" + 
                         ");";
            
            stat = con.createStatement();
            stat.executeUpdate(sql);
            stat.close();
//            con.close();
            System.out.println("user table created");

        }catch(Exception e){
            System.out.println("can't create table there has no database"+e);
        }
    }
    
    private void  createCustomerTable(){
          try{
//            createConnection();
            String sql = "CREATE TABLE "+customerTableName+"(" +
                         "acNo VARCHAR(50) NOT NULL," +
                         "Name VARCHAR(50) NOT NULL," +
                         "age INT(10) NOT NULL," +
                         "gender VARCHAR(15) NOT NULL," +
                         "telephone VARCHAR(16) NOT NULL," +
                         "address VARCHAR(16) NOT NULL," +
                         "state VARCHAR(16) NOT NULL," +
                         "actype VARCHAR(16) NOT NULL," +
                         "amount decimal(18,2) NOT NULL," +
                         "PRIMARY KEY(acNo)" + 
                         ");";
            
            stat = con.createStatement();
            stat.executeUpdate(sql);
            stat.close();
//            con.close();
            System.out.println("customer table created");
            
        }catch(Exception e){
            System.out.println("can't create cutomer table there has no database"+e);
        }
    }
     
    private void creatTransactionReportsTable(){
        
        try{
//            createConnection();
            String sql = "CREATE TABLE "+traDtailsTableName+"(" +
                            "  no int AUTO_INCREMENT PRIMARY KEY," +
                            "  date VARCHAR(15) NOT NULL," +
                            "  time VARCHAR(15) NOT NULL," +
                            "  handdleBy VARCHAR(50) NOT NULL," +
                            "  method VARCHAR(50) NOT NULL," +
                            "  amount int NOT NULL," +
                            "  accountNo VARCHAR(50) NOT NULL," +
                            "  FOREIGN KEY(accountNo) REFERENCES "+customerTableName+"(acNo)" +
                            "  ON DELETE CASCADE" +
                            "  ON UPDATE CASCADE" +
                            "  );";
            
            stat = con.createStatement();
            stat.executeUpdate(sql);
            stat.close();
//            con.close();
            System.out.println("transaction records table created");
            
        }catch(Exception e){
            System.out.println("can't transaction records  create table there has no database"+e);
        }
    }
    
    private void creatLoginRecordsTable(){
        
        try{
//            createConnection();
            String sql = "CREATE TABLE "+userLoginTableName+"(" +
                            "  no int AUTO_INCREMENT PRIMARY KEY," +
                            "  userName VARCHAR(20) NOT NULL," +
                            "  date VARCHAR(20) NOT NULL," +
                            "  logTime VARCHAR(20) NOT NULL," +
                            "  outTime VARCHAR(20)," +
                            "  FOREIGN KEY(userName) REFERENCES "+userTableName+"(userName)" +
                            "  ON DELETE CASCADE" +
                            "  ON UPDATE CASCADE" +
                            "  );";
            
            stat = con.createStatement();
            stat.executeUpdate(sql);
            stat.close();
            con.close();
            System.out.println("user record table records table created");
            
        }catch(Exception e){
            System.out.println("can't user records  create table there has no database"+e);
        }
    }
    
    
    
    public void insertDataForUserTable(String name, String email, String telPhone, String userName, String password){
        
        try{
            createConnection();
            if(con != null){
               
               String sql = "INSERT INTO "+ userTableName +"(`name`, `email`, `telPhone`, `userName`, `pass`) " +
                         "VALUES " + "(?, ?, ?, ?,?)";

                ps = con.prepareStatement(sql);

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, telPhone);
                ps.setString(4, userName);
                ps.setString(5, password);

                ps.executeUpdate();
                ps.close();
                con.close();
                System.out.println("Insert successfully");
            }
            
        }catch(Exception e){

            System.out.println("can't insert user table data "+e);
//            try{
//                
//                if(!userTableConnection){
//                     userTableConnection = true;
//                     createUserTable();  
//                     insertDataForUserTable(name, email, telPhone, userName, password);
//                }
//                
//            }catch(Exception ex){
//                System.out.println("Error insert "+ ex);
//            }
        }
    }
    
    
    public boolean inserDataForCustomer(String accountNo, String name, String age, 
    String gender,String telephone, String address, String sate, String acType, int amount ){
        boolean cusInsert = false;
        try{
            createConnection();
            if(con != null){ 
                 String sql = "INSERT INTO `"+ customerTableName +"`(`acNo`, `Name`, `age`, `gender`, `telephone`, `address`, `state`, `actype`, `amount`) " +
                         "VALUES " + 
                         "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
                ps = con.prepareStatement(sql);

                ps.setString(1, accountNo);
                ps.setString(2, name);
                ps.setString(3, age);
                ps.setString(4, gender);
                ps.setString(5, telephone);
                ps.setString(6, address);
                ps.setString(7, sate);
                ps.setString(8, acType);
                ps.setInt(9, amount);

                ps.executeUpdate();
                ps.close();
                con.close();
                cusInsert = true;
                System.out.println("Insert successfully");
            }
           
        }catch(Exception e){
              System.out.println("can't insert customer table data "+e);
        
//                if(customerTableConnection){
//                     System.out.println("2");
//                     customerTableConnection = false;
//                     createCustomerTable();  
//                     inserDataForCustomer(accountNo, name, age, gender, telephone,address, sate, acType, amount );
//                }
        }       
           
      return cusInsert;  
    }
    
   
    private void findCustomerDetails(String acNo){
      
        try{
            createConnection();
            String sql = "SELECT * FROM `"+customerTableName+"` WHERE `acNo`= '"+ acNo +"'";
            ps = this.con.prepareStatement(sql);
            result = ps.executeQuery();      
        }catch(Exception e){
            System.out.println("can't find there has no customer table "+e);
        }
     
    }
    
    public void depositAmount(String acountNo, double updateAmount){
        String sql = "UPDATE `"+ customerTableName +"` SET `amount`="+ updateAmount +" WHERE acNo = "+acountNo;
        
        try{
            createConnection();
            stat = con.createStatement();
            stat.executeUpdate(sql);
            System.out.println("Deposit successfully");
            con.close();
            stat.close();
        }catch(Exception e){
             System.out.println(e);
        }
    }
    
    public void witdrawAmount(String acountNo, double updateAmount){
        String sql = "UPDATE `"+ customerTableName +"` SET `amount`="+ updateAmount +" WHERE acNo = "+acountNo;
        
        try{
            createConnection();
            stat = con.createStatement();
            stat.executeUpdate(sql);
            System.out.println("withdraw successfully");
            con.close();
            stat.close();
        }catch(Exception e){
             System.out.println(e);
        }
    }
    
    
    private void findUserdetails(String userName){
        
        try{
            createConnection();
            String sql = "SELECT * FROM `"+ userTableName +"` WHERE userName = '"+userName+"'";
            ps = this.con.prepareStatement(sql);
            result = ps.executeQuery();      
        }catch(Exception e){
            System.out.println("can't find there has no user table "+e);
        }
    }
    
    public boolean getUserNameAndPasswordDetails(String userName, String password){
        boolean passwordMatch = false;
       
        try{
            findUserdetails(userName);
           if(result.next()){
               
             if(userName.equals(result.getString("userName")) && password.equals(result.getString("pass"))){
                   passwordMatch = true;
              }  
           }  
        }catch(Exception e){
            System.out.println(e);
        }
  
       return passwordMatch; 
 }
    

    public void insertDataForTransactionRecordsTable(String date, String time, String handdleBy, String method, int amount, String accountNo){
        
        try{
            createConnection();
            if(con != null){
                String sql = "INSERT INTO `"+ traDtailsTableName +"`(`date`, `time`, `handdleBy`, `method`, `amount`, `accountNo`) " +
                         "VALUES " + "(?, ?, ?, ?, ?, ?)";

                ps = con.prepareStatement(sql);

                ps.setString(1, date);
                ps.setString(2, time);
                ps.setString(3, handdleBy);
                ps.setString(4, method);
                ps.setInt(5, amount);
                ps.setString(6, accountNo);

                ps.executeUpdate();
                ps.close();
                con.close();
                System.out.println("Insert successfully");
            }
            
        }catch(Exception e){

            System.out.println("can't insert trnsaction records table data "+e);
//                if(e !=null && TRTableConnection){
//                     TRTableConnection = false;
//                     creatTransactionReportsTable();  
//                     insertDataForTransactionRecordsTable(date, time, handdleBy, method, amount, accountNo);
//                } 
        }
    }
    
    
    public ArrayList<TraData> getTaransactionRecords(String mysql){
        
        ArrayList<TraData> transactionData = new ArrayList<>();
        
        try{
            createConnection();
            stat = con.createStatement();
            result = stat.executeQuery(mysql);
            TraData traData;
            while(result.next()){
                traData = new TraData(result.getInt("no"),result.getString("date"),result.getString("time"),result.getString("handdleBy"),
                        result.getString("method"), result.getInt("amount"),result.getString("accountNo"));
                
                transactionData.add(traData);   
                
            }
  
        }catch(Exception e){
            
        }
        
        
     return transactionData;   
    }
    

     public ArrayList<TraData> getCustomerDetails(String mysql){
        
        ArrayList<TraData> customerDatas = new ArrayList<>();
        
        try{
            createConnection();
            stat = con.createStatement();
            result = stat.executeQuery(mysql);
            TraData customerData;
            while(result.next()){
                customerData = new TraData(
                        result.getString("acNo"),
                        result.getString("Name"),
                        result.getString("age"),
                        result.getString("gender"),
                        result.getString("telephone"),
                        result.getString("address"),
                        result.getString("state"),
                        result.getString("actype"),
                        result.getInt("amount"));
                
                customerDatas.add(customerData);   
                
            }
  
        }catch(Exception e){
            System.out.println(e);
        }
        
        
     return customerDatas;   
    }
    
    
    
     public ArrayList<TraData> getUserList(String mysql){
        
        ArrayList<TraData> userData = new ArrayList<>();
        
        try{
            createConnection();
            stat = con.createStatement();
            result = stat.executeQuery(mysql);
            TraData UserData;
            while(result.next()){
                UserData = new TraData(
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("telPhone"),
                        result.getString("userName"));
     
                userData.add(UserData);   
                
            }
  
        }catch(Exception e){
            
        }
        
        
     return userData;   
    }
    
   
    
     public void insertDataUserloginRecordsTable(String userName, String date, String LogTime, String outTime){
        
        try{
            createConnection();
            if(con != null){
                String sql = "INSERT INTO `"+ userLoginTableName +"`(`userName`, `date`, `logTime`, `outTime`) " +
                         "VALUES " + "(?, ?, ?, ?)";

                ps = con.prepareStatement(sql);

                ps.setString(1, userName);
                ps.setString(2, date);
                ps.setString(3, LogTime);
                ps.setString(4, outTime);
                
                ps.executeUpdate();
                ps.close();
                con.close();
                System.out.println("Insert successfully");
            }
            
        }catch(Exception e){

            System.out.println("can't insert trnsaction records table data "+e);
//                if(e !=null && userRecordTable){
//                     userRecordTable = false;
//                     creatLoginRecordsTable();  
//                     insertDataUserloginRecordsTable(userName, date, LogTime, outTime);
//                } 
        }
    }
    
    
  
     public ArrayList<TraData> getUserRecorsList(String mysql){
        
        ArrayList<TraData> userData = new ArrayList<>();
        
        try{
            createConnection();
            stat = con.createStatement();
            result = stat.executeQuery(mysql);
            TraData UserRecordData;
            while(result.next()){
                UserRecordData = new TraData(
                        result.getString("userName"),
                        result.getString("date"),
                        result.getString("logTime"),
                        result.getString("outTime"));
     
                userData.add(UserRecordData);   
                
            }
  
        }catch(Exception e){
            
        }
    
        return userData;
       }
    

     
     public boolean deleteCustomerDeytails(String acNo){
      
         
         boolean delete = false;
        try{
            createConnection();
            findCustomerDetails(acNo);
             if(this.result.next()){    
                 if(acNo.equals(result.getString("acNo"))){
                      String sql = "DELETE FROM `"+customerTableName+"` WHERE `acNo`= '"+ acNo +"'";
                      stat = con.createStatement();
                      stat.executeUpdate(sql);
                      delete = true;
                 } 
             }
           
               
        }catch(Exception e){
            System.out.println("can't find there has no customer table "+e);
        }
     
        return delete;
    }
     
     
     public boolean updateCustomerTable(String name, int age, String tele, String address, String AcNo){
         
         boolean updateSu = false;
         try{
            createConnection();
            String mysql = "UPDATE `customer` SET `Name`= '"+name+"' ,`age`= "+age+",`telephone`= '"+tele+"',`address`='"+address+"' WHERE `acNo`='"+AcNo+"'";
            stat = con.createStatement();
            stat.executeUpdate(mysql);
            stat.close();
            con.close();
            updateSu = true;
             
         }catch(Exception e){
             System.out.println(e);
         }
         
         return updateSu;
     }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
}
