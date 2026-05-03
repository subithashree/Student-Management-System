import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

public class AttendanceForm extends JFrame {

    JTextField studentIdField;
    JComboBox<String> statusBox;

    public AttendanceForm() {

        setTitle("Mark Attendance");
        setSize(350, 250);
        setLayout(null);

        JLabel idLabel = new JLabel("Student ID:");
        idLabel.setBounds(20, 20, 100, 25);
        add(idLabel);

        studentIdField = new JTextField();
        studentIdField.setBounds(120, 20, 150, 25);
        add(studentIdField);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(20, 60, 100, 25);
        add(statusLabel);

        statusBox = new JComboBox<>(new String[]{"Present", "Absent"});
        statusBox.setBounds(120, 60, 150, 25);
        add(statusBox);

        JButton markBtn = new JButton("Mark Attendance");
        markBtn.setBounds(90, 120, 150, 30);
        add(markBtn);

        markBtn.addActionListener(e -> markAttendance());

        setVisible(true);
    }

    void markAttendance() {

        try {
            String input = studentIdField.getText();

            // ✅ FIX 1: validate number
            if (!input.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Enter valid numeric Student ID");
                return;
            }

            int studentId = Integer.parseInt(input);
            String status = statusBox.getSelectedItem().toString();
            LocalDate date = LocalDate.now();

            Connection con = DBConnection.getConnection();

            // ❗ FIX 2: check student exists (prevents FK error)
            PreparedStatement check = con.prepareStatement(
                "SELECT id FROM students WHERE id=?"
            );
            check.setInt(1, studentId);
            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Student ID not found!");
                return;
            }

            // ✅ insert attendance
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO attendance(student_id, status, date) VALUES (?, ?, ?)"
            );

            ps.setInt(1, studentId);
            ps.setString(2, status);
            ps.setDate(3, Date.valueOf(date));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Attendance Marked Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error marking attendance");
        }
    }
}