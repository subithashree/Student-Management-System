import javax.swing.*;
import java.sql.*;

public class StudentForm extends JFrame {

    JTextField idField, nameField, deptField, emailField;

    public StudentForm() {

        setTitle("Student Form");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        // ID
        add(new JLabel("ID")).setBounds(20, 20, 80, 25);
        idField = new JTextField();
        idField.setBounds(120, 20, 200, 25);
        add(idField);

        // NAME
        add(new JLabel("Name")).setBounds(20, 60, 80, 25);
        nameField = new JTextField();
        nameField.setBounds(120, 60, 200, 25);
        add(nameField);

        // DEPT
        add(new JLabel("Dept")).setBounds(20, 100, 80, 25);
        deptField = new JTextField();
        deptField.setBounds(120, 100, 200, 25);
        add(deptField);

        // EMAIL
        add(new JLabel("Email")).setBounds(20, 140, 80, 25);
        emailField = new JTextField();
        emailField.setBounds(120, 140, 200, 25);
        add(emailField);

        // BUTTONS

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(20, 200, 80, 30);
        add(saveBtn);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(110, 200, 80, 30);
        add(searchBtn);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(200, 200, 80, 30);
        add(updateBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(290, 200, 80, 30);
        add(deleteBtn);

        // ACTIONS
        saveBtn.addActionListener(e -> saveStudent());
        searchBtn.addActionListener(e -> searchStudent());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());

        setVisible(true);
    }

    // ✅ ADD STUDENT
    void saveStudent() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO students(name, department, email) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nameField.getText());
            ps.setString(2, deptField.getText());
            ps.setString(3, emailField.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student Added");

            AuditLogger.log("Student Added: " + nameField.getText());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔍 SEARCH STUDENT
    void searchStudent() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM students WHERE id=? OR name LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, idField.getText());
            ps.setString(2, "%" + nameField.getText() + "%");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idField.setText(String.valueOf(rs.getInt("id")));
                nameField.setText(rs.getString("name"));
                deptField.setText(rs.getString("department"));
                emailField.setText(rs.getString("email"));
            } else {
                JOptionPane.showMessageDialog(this, "Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✏️ UPDATE STUDENT
    void updateStudent() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE students SET name=?, department=?, email=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nameField.getText());
            ps.setString(2, deptField.getText());
            ps.setString(3, emailField.getText());
            ps.setInt(4, Integer.parseInt(idField.getText()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student Updated");

            AuditLogger.log("Student Updated ID: " + idField.getText());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🗑 DELETE STUDENT
    void deleteStudent() {
    try {
        Connection con = DBConnection.getConnection();

        int id = Integer.parseInt(idField.getText());

        // STEP 1: DELETE CHILD TABLE FIRST (attendance)
        String sql1 = "DELETE FROM attendance WHERE student_id=?";
        PreparedStatement ps1 = con.prepareStatement(sql1);
        ps1.setInt(1, id);
        ps1.executeUpdate();

        // STEP 2: DELETE PARENT TABLE (students)
        String sql2 = "DELETE FROM students WHERE id=?";
        PreparedStatement ps2 = con.prepareStatement(sql2);
        ps2.setInt(1, id);
        ps2.executeUpdate();

        JOptionPane.showMessageDialog(this, "Student Deleted Successfully");

        AuditLogger.log("Student Deleted ID: " + idField.getText());

        // OPTIONAL: clear fields after delete
        idField.setText("");
        nameField.setText("");
        deptField.setText("");
        emailField.setText("");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
} 