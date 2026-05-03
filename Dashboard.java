import javax.swing.*;
import java.awt.Font;

public class Dashboard extends JFrame {

    Dashboard() {
        setTitle("Dashboard");
        setSize(400, 300);
        setLayout(null);

         setResizable(false);

        JButton studentBtn = new JButton("Manage Students");
        studentBtn.setBounds(100, 50, 200, 30);
        studentBtn.setFont(new Font("Arial", Font.BOLD, 14));
        add(studentBtn);

        JButton attendanceBtn = new JButton("Attendance");
        attendanceBtn.setBounds(100, 100, 200, 30);
        attendanceBtn.setFont(new Font("Arial", Font.BOLD, 14));  
        add(attendanceBtn);

        JButton viewStudents = new JButton("View Students");
        viewStudents.setBounds(50, 150, 150, 30);
        viewStudents.setFont(new Font("Arial", Font.BOLD, 14));
        add(viewStudents);

        JButton viewAttendance = new JButton("View Attendance");
        viewAttendance.setBounds(210, 150, 150, 30);
        viewAttendance.setFont(new Font("Arial", Font.BOLD, 14));
        add(viewAttendance);

        JButton logBtn = new JButton("View Logs");
        logBtn.setBounds(50, 200, 150, 30);
        add(logBtn);

        logBtn.addActionListener(e -> new ViewAuditLog());  

        studentBtn.addActionListener(e -> new StudentForm());
        attendanceBtn.addActionListener(e -> new AttendanceForm());

        viewStudents.addActionListener(e -> new ViewStudents());
        viewAttendance.addActionListener(e -> new ViewAttendance());

        setLocationRelativeTo(null); 

        setVisible(true);
    }
}