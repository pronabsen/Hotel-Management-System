import databse.DatabaseConnect;
import net.proteanit.sql.DbUtils;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import widgets.RoundedGradientButton;


public class CustomerListActivity extends JFrame implements ActionListener {
    JTable table;
    RoundedGradientButton back;
    CustomerListActivity(){
        setTitle("Hotel Management System - Guests Management");
        setVisible(true);
        setLayout(null);
        setBounds(300,200,1300,600);
        getContentPane().setBackground(Color.white);


        // **Title JLabel**
        JLabel title = new JLabel("GUESTS DETAILS", JLabel.CENTER);
        title.setFont(new Font("Raleway", Font.BOLD, 22));
        title.setForeground(Color.BLACK);
        title.setBounds(400, 10, 400, 30);  // Centered at the top
        add(title);

        // Table setup
        table = new JTable();
        table.setFont(new Font("Raleway", Font.PLAIN, 16));
        table.setRowHeight(30);

        // Custom Header Renderer (ALL CAPS)
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Raleway", Font.BOLD, 16));
        header.setForeground(Color.BLACK);

        // Convert headers to ALL CAPS
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString().toUpperCase(), JLabel.CENTER);
                label.setFont(new Font("Raleway", Font.BOLD, 16));
                return label;
            }
        });

        // Center align table headers
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setFont(new Font("Raleway", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 60, 1200, 400);
        add(scrollPane);

        // Fetch data from the database
        try {
            DatabaseConnect conn = new DatabaseConnect();
            ResultSet rs = conn.statement.executeQuery("SELECT customer.*, room.bedtype,room.price FROM customer LEFT JOIN room ON customer.room = room.roomno;");
            table.setModel(DbUtils.resultSetToTableModel(rs));

            // Apply center alignment to all columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Back Button
        back = new RoundedGradientButton("BACK", null);
        back.setFont(new Font("Raleway", Font.BOLD, 16));
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(500, 500, 120, 40);
        add(back);
        back.addActionListener(this);

        setVisible(true);

    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==back){
            setVisible(false);
            new DashboardActivity();
        }
    }

    public static void main(String args[]){
        new CustomerListActivity();
    }

}
