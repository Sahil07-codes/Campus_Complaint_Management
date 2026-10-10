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

@WebServlet("/mycomplaints")
public class MyComplaintsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            !"student".equals(session.getAttribute("role"))) {
            response.sendRedirect("index.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");

        ArrayList<HashMap<String, String>> complaints =
            (ArrayList<HashMap<String, String>>) getServletContext()
                .getAttribute("complaints");

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><html><head>");
        html.append("<title>My Complaints</title>");
        html.append("<style>");
        html.append("body{font-family:Arial;background:#f4f7fb;margin:0;padding:30px;}");
        html.append(".box{max-width:900px;margin:auto;background:white;padding:25px;border-radius:10px;}");
        html.append("h1{color:#162b4d;}");
        html.append("table{width:100%;border-collapse:collapse;}");
        html.append("th,td{padding:12px;border-bottom:1px solid #ddd;text-align:left;}");
        html.append("th{background:#162b4d;color:white;}");
        html.append(".status{color:#b77900;font-weight:bold;}");
        html.append("a{display:inline-block;margin-top:20px;background:#267c83;color:white;padding:10px 15px;text-decoration:none;border-radius:5px;}");
        html.append("</style></head><body>");

        html.append("<div class='box'>");
        html.append("<h1>My Complaints</h1>");
        html.append("<p>Student: ").append(escape(username)).append("</p>");

        html.append("<table><tr>");
        html.append("<th>ID</th><th>Category</th>");
        html.append("<th>Subject</th><th>Status</th>");
        html.append("</tr>");

        boolean found = false;

        if (complaints != null) {
            synchronized (complaints) {
                for (HashMap<String, String> c : complaints) {
                    if (username.equals(c.get("username"))) {
                        found = true;
                        html.append("<tr><td>")
                            .append(escape(c.get("id")))
                            .append("</td><td>")
                            .append(escape(c.get("category")))
                            .append("</td><td>")
                            .append(escape(c.get("subject")))
                            .append("</td><td class='status'>")
                            .append(escape(c.get("status")))
                            .append("</td></tr>");
                    }
                }
            }
        }

        if (!found) {
            html.append("<tr><td colspan='4'>No complaints submitted yet.</td></tr>");
        }

        html.append("</table>");
        html.append("<a href='user.jsp'>Back to Dashboard</a>");
        html.append("</div></body></html>");

        response.getWriter().println(html.toString());
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