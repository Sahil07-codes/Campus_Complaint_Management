<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Campus Complaint Management</title>

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
        }

        .box {
            width: 380px;
            background: white;
            padding: 35px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.25);
        }

        h1 {
            text-align: center;
            color: #162b4d;
            font-size: 25px;
        }

        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin: 15px 0 7px;
            font-weight: bold;
        }

        input, select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 15px;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #267c83;
        }

        button {
            width: 100%;
            padding: 12px;
            margin-top: 25px;
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

        .footer {
            text-align: center;
            color: #777;
            font-size: 12px;
            margin-top: 22px;
        }
    </style>
</head>

<body>

    <div class="box">

        <h1>Campus Complaints</h1>

        <p class="subtitle">Campus Complaint Management System</p>

        <form action="login" method="post">

            <label>Username</label>
            <input type="text"
                   name="username"
                   placeholder="Enter username"
                   required>

            <label>Password</label>
            <input type="password"
                   name="password"
                   placeholder="Enter password"
                   required>

            <label>Login As</label>
            <select name="role" required>
                <option value="">Select Role</option>
                <option value="student">Student</option>
                <option value="admin">Admin</option>
            </select>

            <button type="submit">Login</button>

        </form>

        <div class="footer">
            Campus Complaint Management System
        </div>

    </div>

</body>
</html>