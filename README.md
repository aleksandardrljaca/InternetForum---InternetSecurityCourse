# Internet Security Course Project - Internet Forum

This project implements an internet forum where registered users can exchange opinions on various topics. Topics are organized into categories/rooms, including at least Science, Culture, Sports, and Music. The web application consists of three main sections, each accessible based on the user's role: Administrator, Moderator, and User.

    Administrator: Manages user accounts (approval, role assignment, permission settings).
    Moderator: Moderates user comments, including approving, editing, or blocking them.
    User: Participates in discussions within forum rooms, viewing and posting comments.

The system includes user registration with email notifications, two-factor authentication, and OAuth2 login support (e.g., Google, GitHub). Security features include JWT-based session management, a Web Application Firewall (WAF) to filter malicious traffic, and encrypted communication using digital certificates. The SIEM component logs security-sensitive activities for monitoring purposes.
