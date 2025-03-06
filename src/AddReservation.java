import databse.DatabaseConnect;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AddReservation extends JFrame implements ActionListener {
    Choice customer, croom;
    JTextField tfname, tfcheckin, tfpaid, tfpending, tfstatus;
    Dashboard.RoundedGradientButton check, newUser, update, back;
    JTable table;

    AddReservation() {
        setTitle("Hotel Management System - Add Reservation");
        setBounds(300, 150, 1000, 540);
        setLayout(null);
        setVisible(true);
        getContentPane().setBackground(Color.white);

        JLabel text = new JLabel("ADD RESERVATION");
        text.setFont(new Font("Raleway", Font.BOLD, 25));
        text.setBounds(110, 20, 250, 30);
        text.setForeground(Color.black);
        add(text);

        JLabel lbid = new JLabel("CUSTOMER ID:");
        lbid.setBounds(30, 80, 150, 25);
        lbid.setFont(new Font("Raleway", Font.BOLD, 14));
        add(lbid);

        customer = new Choice();
        customer.setBounds(200, 80, 180, 25);
        add(customer);

        try {
            DatabaseConnect conn = new DatabaseConnect();
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM customer where status = 'New' or status = 'Check Out'");
            while (rs.next()) {
                customer.add(rs.getString("number"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading customer IDs.");
            e.printStackTrace();
        }

        JLabel lbroom = new JLabel("ROOM NUMBER:");
        lbroom.setBounds(30, 120, 150, 25);
        lbroom.setFont(new Font("Raleway", Font.BOLD, 14));
        add(lbroom);

        croom = new Choice();
        croom.setBounds(200, 120, 180, 25);
        add(croom);

        croom.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                try {
                    DatabaseConnect conn = new DatabaseConnect();

                    String query = "SELECT * FROM room WHERE roomno = ?";
                    PreparedStatement pst = conn.connection.prepareStatement(query);
                    pst.setString(1, croom.getSelectedItem().toString());
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        tfpaid.setText(rs.getString("price"));
                    }
                } catch (Exception es) {
                    JOptionPane.showMessageDialog(null, "Error loading customer IDs.");
                    es.printStackTrace();
                }

            }
        });

        JLabel lbname = new JLabel("NAME:");
        lbname.setBounds(30, 160, 150, 25);
        lbname.setFont(new Font("Raleway", Font.BOLD, 14));
        add(lbname);

        tfname = new JTextField();
        tfname.setBounds(200, 160, 180, 25);
        tfname.setEditable(false);
        add(tfname);

        JLabel lbcheck = new JLabel("CHECK-IN TIME:");
        lbcheck.setBounds(30, 200, 150, 25);
        lbcheck.setFont(new Font("Raleway", Font.BOLD, 14));
        add(lbcheck);

        tfcheckin = new JTextField();
        tfcheckin.setBounds(200, 200, 180, 25);
        tfcheckin.setEditable(false);
        add(tfcheckin);

        JLabel lbpaid = new JLabel("AMOUNT PAID:");
        lbpaid.setBounds(30, 240, 150, 25);
        lbpaid.setFont(new Font("Raleway", Font.BOLD, 14));
        add(lbpaid);

        tfpaid = new JTextField();
        tfpaid.setBounds(200, 240, 180, 25);
        add(tfpaid);

        JLabel lbpending = new JLabel("PENDING AMOUNT:");
        lbpending.setBounds(30, 280, 150, 25);
        lbpending.setFont(new Font("Raleway", Font.BOLD, 14));
        add(lbpending);

        tfpending = new JTextField();
        tfpending.setBounds(200, 280, 180, 25);
        tfpending.setEditable(false);
        add(tfpending);

        JLabel lbstatus = new JLabel("STATUS:");
        lbstatus.setBounds(30, 320, 150, 25);
        lbstatus.setFont(new Font("Raleway", Font.BOLD, 14));
        add(lbstatus);

        tfstatus = new JTextField();
        tfstatus.setBounds(200, 320, 180, 25);
        tfstatus.setEditable(false);
        add(tfstatus);

        JLabel titleRm = new JLabel("ROOM DETAILS", JLabel.CENTER);
        titleRm.setFont(new Font("Raleway", Font.BOLD, 18));
        titleRm.setForeground(Color.BLACK);
        titleRm.setBounds(420, 10, 550, 30);
        add(titleRm);

        // Table setup
        table = new JTable();
        table.setFont(new Font("Raleway", Font.PLAIN, 16));
        table.setRowHeight(25);

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
        scrollPane.setBounds(420, 50, 550, 380);
        add(scrollPane);

        // Fetch data from the database
        try {
            DatabaseConnect conn = new DatabaseConnect();
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM room WHERE availability='Available'");
            table.setModel(DbUtils.resultSetToTableModel(rs));

            // Apply center alignment to all columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        newUser = new Dashboard.RoundedGradientButton("Add New Customer", null);
        newUser.setBounds(120, 380, 200, 30);
        newUser.setBackground(Color.BLACK);
        newUser.setForeground(Color.WHITE);
        newUser.addActionListener(this);
        add(newUser);

        check = new Dashboard.RoundedGradientButton("CHECK", null);
        check.setBounds(30, 440, 100, 30);
        check.setBackground(Color.BLACK);
        check.setForeground(Color.WHITE);
        check.addActionListener(this);
        add(check);

        update = new Dashboard.RoundedGradientButton("Add", null);
        update.setBounds(150, 440, 100, 30);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        update.addActionListener(this);
        update.setVisible(false);
        add(update);

        back = new Dashboard.RoundedGradientButton("BACK", null);
        back.setBounds(270, 440, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == check) {
            fetchCustomerData();
        } else if (ae.getSource() == newUser) {
            new AddCustomer();
        }  else if (ae.getSource() == update) {
            updateCustomerData();
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Dashboard();
        }
    }

    private void fetchCustomerData() {
        String id = customer.getSelectedItem();
        String query = "SELECT * FROM customer WHERE number=?";

        try {
            DatabaseConnect conn = new DatabaseConnect();
            PreparedStatement pst = conn.connection.prepareStatement(query);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tfname.setText(rs.getString("name"));
                tfcheckin.setText("" + new Date());
                tfpaid.setText("0.0");
                tfstatus.setText("Check In");
            }

            try {
                ResultSet rs2 = conn.statement.executeQuery("SELECT roomno FROM room WHERE availability='Available'");
                while (rs2.next()) {
                    croom.add(rs2.getString("roomno"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            update.setVisible(true);
            conn.closeConnection();


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching data.");
            e.printStackTrace();
        }
    }

    private void updateCustomerData() {
        String number = customer.getSelectedItem();
        String roomNo = croom.getSelectedItem();
        String checkin = tfcheckin.getText();
        String deposit = tfpaid.getText();

        if (roomNo.isEmpty() || checkin.isEmpty() || deposit.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
            return;
        }

        try {
            DatabaseConnect conn = new DatabaseConnect();

            String updateQuery = "UPDATE customer SET room=?, time=?, deposite=?,status='Check In' WHERE number=?";
            PreparedStatement pst = conn.connection.prepareStatement(updateQuery);
            pst.setString(1, roomNo);
            pst.setString(2, checkin);
            pst.setString(3, deposit);
            pst.setString(4, number);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Data Updated Successfully");
                setVisible(false);
                new CustomerInfo();
            } else {
                JOptionPane.showMessageDialog(null, "Customer not found or data not updated.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating data.");
            e.printStackTrace();
        }
    }

/*
    private void fetchCustomerData() {
        String id = customer.getSelectedItem();
        String query = "SELECT * FROM customer WHERE number='" + id + "'";

        try {
            DatabaseConnect conn = new DatabaseConnect();
            ResultSet rs = conn.statement.executeQuery(query);

            if (rs.next()) {
                tfroom.setText(rs.getString("room"));
                tfname.setText(rs.getString("name"));
                tfcheckin.setText(rs.getString("time"));
                tfpaid.setText(rs.getString("deposite"));
                tfstatus.setText(rs.getString("status"));
            }

            // Calculate pending amount
            String roomNo = tfroom.getText();
            ResultSet rs2 = conn.statement.executeQuery("SELECT * FROM room WHERE roomno='" + roomNo + "'");

            if (rs2.next()) {
                int price = rs2.getInt("price");
                float amtPaid = Float.parseFloat(tfpaid.getText().trim());
                float pendingAmount = price - amtPaid;
                tfpending.setText(String.valueOf(pendingAmount));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching data.");
            e.printStackTrace();
        }
    }

    private void updateCustomerData() {
        String number = customer.getSelectedItem();
        String roomNo = tfroom.getText();
        String name = tfname.getText();
        String checkin = tfcheckin.getText();
        String deposit = tfpaid.getText();

        try {
            DatabaseConnect conn = new DatabaseConnect();
            String updateQuery = "UPDATE customer SET room='" + roomNo + "', name='" + name + "', time='" + checkin + "', deposite='" + deposit + "' WHERE number='" + number + "'";
            conn.statement.executeUpdate(updateQuery);

            JOptionPane.showMessageDialog(null, "Data Updated Successfully");
            setVisible(false);
            new Dashboard();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating data.");
            e.printStackTrace();
        }
    }*/

    public static void main(String args[]) {
        new UpdateCheck();
    }
}
