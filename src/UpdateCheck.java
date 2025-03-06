import databse.DatabaseConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateCheck extends JFrame implements ActionListener {
    Choice customer;
    JTextField tfroom, tfname, tfcheckin, tfpaid, tfpending, tfstatus;
    Dashboard.RoundedGradientButton check, update, back;

    UpdateCheck() {
        setTitle("Hotel Management System - Update Customer Status");
        setBounds(300, 150, 480, 500);
        setLayout(null);
        setVisible(true);
        getContentPane().setBackground(Color.white);

        JLabel text = new JLabel("UPDATE STATUS");
        text.setFont(new Font("Raleway", Font.BOLD, 25));
        text.setBounds(90, 20, 250, 30);
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
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                customer.add(rs.getString("number"));
            }
            conn.closeConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading customer IDs.");
            e.printStackTrace();
        }

        JLabel lbroom = new JLabel("ROOM NUMBER:");
        lbroom.setBounds(30, 120, 150, 25);
        lbroom.setFont(new Font("Raleway", Font.BOLD, 14));
        add(lbroom);

        tfroom = new JTextField();
        tfroom.setBounds(200, 120, 180, 25);
        tfroom.setEditable(false);
        add(tfroom);

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

        check = new Dashboard.RoundedGradientButton("CHECK", null);
        check.setBounds(30, 380, 100, 30);
        check.setBackground(Color.BLACK);
        check.setForeground(Color.WHITE);
        check.addActionListener(this);
        add(check);

        update = new Dashboard.RoundedGradientButton("UPDATE", null);
        update.setBounds(150, 380, 100, 30);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        update.addActionListener(this);
        add(update);

        back = new Dashboard.RoundedGradientButton("BACK", null);
        back.setBounds(270, 380, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == check) {
            fetchCustomerData();
        } else if (ae.getSource() == update) {
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
                tfroom.setText(rs.getString("room"));
                tfname.setText(rs.getString("name"));
                tfcheckin.setText(rs.getString("time"));
                tfpaid.setText(rs.getString("deposite"));
                tfstatus.setText(rs.getString("status"));
            }

            // Calculate pending amount
            String roomNo = tfroom.getText();
            String roomQuery = "SELECT price FROM room WHERE roomno=?";
            PreparedStatement pst2 = conn.connection.prepareStatement(roomQuery);
            pst2.setString(1, roomNo);
            ResultSet rs2 = pst2.executeQuery();

            if (rs2.next()) {
                int price = rs2.getInt("price");
                float amtPaid = Float.parseFloat(tfpaid.getText().trim());
                float pendingAmount = price - amtPaid;
                tfpending.setText(String.valueOf(pendingAmount));
            }

            conn.closeConnection();

        } catch (SQLException e) {
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

        if (roomNo.isEmpty() || name.isEmpty() || checkin.isEmpty() || deposit.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
            return;
        }

        try {
            DatabaseConnect conn = new DatabaseConnect();

            String updateQuery = "UPDATE customer SET room=?, name=?, time=?, deposite=? WHERE number=?";
            PreparedStatement pst = conn.connection.prepareStatement(updateQuery);
            pst.setString(1, roomNo);
            pst.setString(2, name);
            pst.setString(3, checkin);
            pst.setString(4, deposit);
            pst.setString(5, number);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Data Updated Successfully");
                setVisible(false);
                new Dashboard();
            } else {
                JOptionPane.showMessageDialog(null, "Customer not found or data not updated.");
            }
            conn.closeConnection();

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
