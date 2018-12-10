/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.model;

import com.fpt.entity.Phone;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quanglinh
 */
public class processDatabase {
    Connection cnn;
    private Connection getConnect(){
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            cnn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Phone", "sa", "sa"
            );
            return cnn;
        } catch (SQLException ex) {
            Logger.getLogger(processDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(processDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int addPhone(Phone p){
        PreparedStatement ps;
        String sql = "INSERT INTO SA.Phoness (ID, NAME, BRAND, PRICE, DESCRIPTION) VALUES(?,?,?,?,?)";
        try {

            ps = getConnect().prepareStatement(sql);
            ps.setLong(1, System.currentTimeMillis());
            ps.setString(2, p.getName());
            ps.setString(3, p.getBrand());
            ps.setInt(4, p.getPrice());
            ps.setString(5, p.getDesc());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(processDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public List<Phone> getAll() {
        PreparedStatement ps;
        List<Phone> list = new LinkedList();
        try {
            String sql = "SELECT * FROM SA.Phoness";
            ps = getConnect().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Phone p = new Phone();
                p.setId(rs.getLong("ID"));
                p.setName(rs.getString("NAME"));
                p.setBrand(rs.getString("BRAND"));
                p.setPrice(rs.getInt("PRICE"));
                p.setDesc(rs.getString("DESCRIPTION"));
                list.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return list;
    }
    
}
