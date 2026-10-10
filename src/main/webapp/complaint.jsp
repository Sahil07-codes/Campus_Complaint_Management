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
    <title>Submit Complaint</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #162b4d, #267c83);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 25px;
        }

        .box {
            width: 480px;
            max-width: 100%;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
        }

        h1 {
            text-align: center;
            color: #162b4d;
        }

        p {
            text-align: center;
            color: #666;
        }

        label {
            display: block;
            margin-top: 16px;
            margin-bottom: 7px;
            font-weight: bold;
        }

        input, select, textarea {
            width: 100%;
            padding: 11px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-family: Arial, sans-serif;
            font-size: 14px;
        }

        textarea {
            height: 110px;
            resize: vertical;
        }

        button {
            width: 100%;
            margin-top: 22px;
            padding: 12px;
            background: #267c83;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background: #1b6268;
        }

        .back {
            display: block;
            text-align: center;
            margin-top: 18px;
            color: #162b4d;
            text-decoration: none;
        }
    </style>
</head>

<body>

    <div class="box">

        <h1>Submit Complaint</h1>

        <p>Tell us about the problem on campus.</p>

        <form action="complaint" method="post">

            <label>Complaint Category</label>

            <select name="category" required>
                <option value="">Select Category</option>
                <option value="Classroom">Classroom</option>
                <option value="Hostel">Hostel</option>
                <option value="Electricity">Electricity</option>
                <option value="Cleanliness">Cleanliness</option>
                <option value="Water Supply">Water Supply</option>
                <option value="Other">Other</option>
            </select>

            <label>Complaint Subject</label>

            <input type="text"
                   name="subject"
                   placeholder="Enter complaint subject"
                   maxlength="100"
                   required>

            <label>Description</label>

            <textarea name="description"
                      placeholder="Describe the problem"
                      maxlength="1000"
                      required></textarea>

            <button type="submit">Submit Complaint</button>

        </form>

        <a class="back" href="user.jsp">Back to Dashboard</a>

    </div>

</body>
</html>