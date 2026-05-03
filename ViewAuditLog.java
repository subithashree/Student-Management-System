import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ViewAuditLog extends JFrame {

    ViewAuditLog() {

        setTitle("Audit Log");
        setSize(500, 300);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Action");
        model.addColumn("Time");

        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement()
                .executeQuery("SELECT * FROM audit_log");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("action"),
                    rs.getString("log_time")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new JScrollPane(table));

        setLocationRelativeTo(null);
        setVisible(true);
    }
}