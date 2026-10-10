<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");

    if (username == null || !"student".equals(role)) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f7fb;
        }

        nav {
            background: #162b4d;
            color: white;
            padding: 20px 8%;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        nav a {
            color: white;
            text-decoration: none;
        }

        .container {
            padding: 40px 8%;
        }

        h1 {
            color: #162b4d;
        }

        .cards {
            display: flex;
            gap: 25px;
            flex-wrap: wrap;
            margin-top: 30px;
        }

        .card {
            background: white;
            width: 300px;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }

        .card h2 {
            color: #267c83;
        }

        .card p {
            color: #666;
            line-height: 1.6;
        }

        .btn {
            display: block;
            background: #267c83;
            color: white;
            text-align: center;
            padding: 12px;
            border-radius: 6px;
            text-decoration: none;
            margin-top: 20px;
        }

        .btn:hover {
            background: #1b6268;
        }
    </style>
</head>

<body>

    <nav>
        <b>Campus Complaints</b>
        <a href="logout">Logout</a>
    </nav>

    <div class="container">

        <h1>Welcome, <%= username %></h1>

        <p>Submit campus issues and track their progress.</p>

        <div class="cards">

            <div class="card">
                <h2>Submit Complaint</h2>
                <p>Report problems related to classrooms, hostel,
                   cleanliness, electricity or other campus facilities.</p>

                <a class="btn" href="complaint.jsp">
                    Submit Complaint
                </a>
            </div>

            <div class="card">
                <h2>My Complaints</h2>
                <p>Check the current status of your submitted complaints.</p>

                <a class="btn" href="mycomplaints">
                    View My Complaints
                </a>
            </div>

        </div>

    </div>

</body>
</html>