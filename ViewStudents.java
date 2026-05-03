import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewStudents extends JFrame {

    JTable table;
    DefaultTableModel model;

    ViewStudents() {

        setTitle("View Students");
        setSize(500, 350);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Department");
        model.addColumn("Email");

        table = new JTable(model);

        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        add(refreshBtn, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadData());

        loadData();

        setVisible(true);
    }

    void loadData() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM students";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("email")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}