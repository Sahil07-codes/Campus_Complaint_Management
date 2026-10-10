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

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect("index.jsp");
            return;
        }

        ArrayList<HashMap<String, String>> complaints =
            (ArrayList<HashMap<String, String>>) getServletContext()
                .getAttribute("complaints");

        response.setContentType("text/html");

        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><html><head>");
        html.append("<title>Admin Dashboard</title>");

        html.append("<style>");
        html.append("body{font-family:Arial;background:#f4f7fb;margin:0;}");
        html.append("nav{background:#162b4d;color:white;padding:20px 8%;}");
        html.append(".container{padding:35px 8%;}");
        html.append(".card{background:white;padding:25px;border-radius:10px;margin-top:20px;overflow-x:auto;}");
        html.append("table{width:100%;border-collapse:collapse;}");
        html.append("th,td{padding:12px;border-bottom:1px solid #ddd;text-align:left;}");
        html.append("th{background:#162b4d;color:white;}");
        html.append("select,button{padding:8px;border-radius:5px;}");
        html.append("button{background:#267c83;color:white;border:0;cursor:pointer;}");
        html.append("a{color:white;text-decoration:none;}");
        html.append("</style></head><body>");

        html.append("<nav><b>Campus Complaints - Admin</b></nav>");
        html.append("<div class='container'>");
        html.append("<h1>Admin Dashboard</h1>");

        int count = complaints == null ? 0 : complaints.size();

        html.append("<div class='card'><h2>Total Complaints: ");
        html.append(count);
        html.append("</h2></div>");

        html.append("<div class='card'><h2>Manage Complaints</h2>");
        html.append("<table><tr>");
        html.append("<th>ID</th><th>Student</th><th>Category</th>");
        html.append("<th>Subject</th><th>Description</th>");
        html.append("<th>Status</th><th>Update</th></tr>");

        if (complaints == null || complaints.isEmpty()) {

            html.append("<tr><td colspan='7'>No complaints available.</td></tr>");

        } else {

            synchronized (complaints) {
                for (HashMap<String, String> c : complaints) {

                    html.append("<tr>");

                    html.append("<td>").append(escape(c.get("id"))).append("</td>");
                    html.append("<td>").append(escape(c.get("username"))).append("</td>");
                    html.append("<td>").append(escape(c.get("category"))).append("</td>");
                    html.append("<td>").append(escape(c.get("subject"))).append("</td>");
                    html.append("<td>").append(escape(c.get("description"))).append("</td>");

                    html.append("<td>").append(escape(c.get("status"))).append("</td>");

                    html.append("<td><form action='admin' method='post'>");

                    html.append("<input type='hidden' name='id' value='")
                        .append(escape(c.get("id"))).append("'>");

                    html.append("<select name='status'>");

                    String[] statuses = {"Pending", "In Progress", "Resolved"};

                    for (String status : statuses) {
                        html.append("<option value='").append(status).append("'");

                        if (status.equals(c.get("status"))) {
                            html.append(" selected");
                        }

                        html.append(">").append(status).append("</option>");
                    }

                    html.append("</select> <button type='submit'>Update</button>");
                    html.append("</form></td></tr>");
                }
            }
        }

        html.append("</table></div>");
        html.append("<p><a href='logout' style='background:#ff7a00;padding:10px 15px;border-radius:5px;'>Logout</a></p>");
        html.append("</div></body></html>");

        response.getWriter().println(html.toString());
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect("index.jsp");
            return;
        }

        String id = request.getParameter("id");
        String status = request.getParameter("status");

        if (id != null && status != null &&
            (status.equals("Pending") ||
             status.equals("In Progress") ||
             status.equals("Resolved"))) {

            ArrayList<HashMap<String, String>> complaints =
                (ArrayList<HashMap<String, String>>) getServletContext()
                    .getAttribute("complaints");

            if (complaints != null) {
                synchronized (complaints) {
                    for (HashMap<String, String> c : complaints) {
                        if (id.equals(c.get("id"))) {
                            c.put("status", status);
                            break;
                        }
                    }
                }
            }
        }

        response.sendRedirect("admin");
    }

    private String escape(String value) {
        if (value == null) return "";

        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}