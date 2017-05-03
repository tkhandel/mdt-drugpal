/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.drug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import javax.json.JsonException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tarun
 */
@WebServlet(name = "DrugInteraction", urlPatterns = {"/DrugInteraction"})
public class DrugInteraction extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
          BufferedReader reader = request.getReader();
          while ((line = reader.readLine()) != null)
            jb.append(line);
        } catch (Exception e) {
            /*report an error*/ 
        }
        JsonReader jsonReader = Json.createReader(new StringReader(jb.toString()));
        JsonObject object = jsonReader.readObject();
        JsonObject params = object.getJsonObject("result").getJsonObject("parameters");
        String drug1 = params.getString("DrugNew");
        System.out.println("Drug1: " + drug1);
        String drug2 = params.getString("DrugNew1");
        System.out.println("Drug2: " + drug2);
        jsonReader.close(); 
        
        JsonObjectBuilder builder = Json.createObjectBuilder();
        //String respText = "You are good to take " + drug1 + " and " + drug2 + " together!";
        String respText = new InteractionInfo().getDrugInteraction(drug1, drug2);
        System.out.println("Response: " + respText);
        if(respText == null) {
            respText = "You are good to take " + drug1 + " and " + drug2 + " together!";
        }
        builder.add("speech", respText);
        builder.add("displayText", respText);
        builder.add("source", "DrugBank");
        builder.add("contextOut", Json.createArrayBuilder().build());
        builder.add("data", Json.createObjectBuilder().build());
        builder.add("followupEvent", Json.createObjectBuilder().build());
        response.setHeader("Content-type", "application/json");
        response.getWriter().write(builder.build().toString());
        response.getWriter().flush();
    }
    
}
