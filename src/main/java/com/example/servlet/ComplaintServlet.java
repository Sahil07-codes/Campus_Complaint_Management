package com.example.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/complaint")
public class ComplaintServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            !"student".equals(session.getAttribute("role"))) {
            response.sendRedirect("index.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        String category = request.getParameter("category");
        String subject = request.getParameter("subject");
        String description = request.getParameter("description");

        ArrayList<HashMap<String, String>> complaints =
            (ArrayList<HashMap<String, String>>) getServletContext()
                .getAttribute("complaints");

        if (complaints == null) {
            complaints = new ArrayList<>();
            getServletContext().setAttribute("complaints", complaints);
        }

        HashMap<String, String> complaint = new HashMap<>();

        complaint.put("id", String.valueOf(complaints.size() + 1));
        complaint.put("username", username);
        complaint.put("category", category);
        complaint.put("subject", subject);
        complaint.put("description", description);
        complaint.put("status", "Pending");

        synchronized (complaints) {
            complaint.put("id", String.valueOf(complaints.size() + 1));
            complaints.add(complaint);
        }

        response.sendRedirect("mycomplaints");
    }
}