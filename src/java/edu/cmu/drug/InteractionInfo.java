/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.drug;

import edu.cmu.db.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarun
 */
public class InteractionInfo {
    
    public String getDrugInteraction(String drug1, String drug2) {
        
        try {
            String drugId_1 = getDrugID(drug1);
            System.out.println("Drug1 ID: " + drugId_1);
            String drugId_2 = getDrugID(drug2);
            System.out.println("Drug2 ID: " + drugId_2);
            String sql = "SELECT * FROM drug_interaction WHERE drug_id_1 = ? AND drug_id_2 = ?";
            Connection conn = DbConnection.connectDB();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, drugId_1);
            stmt.setString(2, drugId_2);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Interaction query executed.");
            rs.next();
            String desc = rs.getString("DESCRIPTION");
            DbConnection.closeConnection(conn);
            return desc;
        } catch (SQLException ex) {
            Logger.getLogger(InteractionInfo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    private String getDrugID(String drugName) {
        try {
            Connection conn = DbConnection.connectDB();
            String sql = "SELECT * FROM drug WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, drugName);
            ResultSet rs = stmt.executeQuery();
            String drugId = null;
            rs.next();
            drugId = rs.getString("DRUG_ID");
            DbConnection.closeConnection(conn);
            return drugId;
        } catch (SQLException ex) {
            Logger.getLogger(InteractionInfo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
