import databse.DatabaseConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import widgets.RoundedGradientButton;

public class UpdateRoomStatus extends JFrame implements ActionListener {
    Choice customer;
    JTextField tfroom, tfstatus, tfavailable;
    RoundedGradientButton check;
    RoundedGradientButton update, back;

    UpdateRoomStatus() {
        setTitle("Hotel Management System - Update Room Status");
        setBounds(300, 200, 480, 500);
        setLayout(null);
        setVisible(true);
        getContentPane().setBackground(Color.white);

        JLabel text = new JLabel("Update Room Status");
        text.setFont(new Font("Raleway", Font.BOLD, 25));
        text.setBounds(100, 20, 250, 30);
        text.setForeground(Color.BLACK);
        add(text);

        JLabel lbid = new JLabel("Customer ID");
        lbid.setBounds(30, 80, 100, 20);
        add(lbid);

        customer = new Choice();
        customer.setBounds(200, 80, 180, 25);
        add(customer);

        // Load customers with status not 'Check Out'
        try {
            DatabaseConnect conn = new DatabaseConnect();
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM customer WHERE status != 'Check Out'");
            while (rs.next()) {
                customer.add(rs.getString("number"));
            }
            conn.closeConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading customers.");
            e.printStackTrace();
        }

        JLabel lbroom = new JLabel("Room Number");
        lbroom.setBounds(30, 130, 100, 20);
        add(lbroom);

        tfroom = new JTextField();
        tfroom.setBounds(200, 130, 180, 20);
        tfroom.setEditable(false);
        add(tfroom);

        JLabel lbavailable = new JLabel("Availability");
        lbavailable.setBounds(30, 180, 100, 20);
        add(lbavailable);

        tfavailable = new JTextField();
        tfavailable.setBounds(200, 180, 180, 20);
        add(tfavailable);

        JLabel lbstatus = new JLabel("Cleaning Status");
        lbstatus.setBounds(30, 230, 100, 20);
        add(lbstatus);

        tfstatus = new JTextField();
        tfstatus.setBounds(200, 230, 180, 20);
        add(tfstatus);

        // Button for checking room details
        check = new RoundedGradientButton("CHECK", null);
        check.setBackground(Color.black);
        check.setForeground(Color.white);
        check.setBounds(30, 340, 100, 30);
        add(check);
        check.addActionListener(this);

        // Button for updating room details
        update = new RoundedGradientButton("UPDATE", null);
        update.setBackground(Color.black);
        update.setForeground(Color.white);
        update.setBounds(150, 340, 100, 30);
        add(update);
        update.addActionListener(this);

        // Button for going back to the dashboard
        back = new RoundedGradientButton("BACK", null);
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        back.setBounds(270, 340, 100, 30);
        add(back);
        back.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            updateRoomStatus();
        } else if (ae.getSource() == check) {
            fetchRoomDetails();
        } else if (ae.getSource() == back) {
            setVisible(false);
            new DashboardActivity();
        }
    }

    private void updateRoomStatus() {
        String number = customer.getSelectedItem();
        String roomNo = tfroom.getText();
        String availability = tfavailable.getText();
        String status = tfstatus.getText();

        // Ensure availability and status are not empty
        if (availability.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return;
        }

        try {
            DatabaseConnect conn = new DatabaseConnect();
            String updateQuery = "UPDATE room SET availability='" + availability + "', cleanliness='" + status + "' WHERE roomno='" + roomNo + "'";
            conn.statement.executeUpdate(updateQuery);
            conn.closeConnection();
            JOptionPane.showMessageDialog(null, "Room Status Updated Successfully");
            setVisible(false);
            new DashboardActivity();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating room status.");
            e.printStackTrace();
        }
    }

    private void fetchRoomDetails() {
        String id = customer.getSelectedItem();
        String query = "SELECT * FROM customer WHERE number='" + id + "'";

        try {
            DatabaseConnect conn = new DatabaseConnect();
            ResultSet rs = conn.statement.executeQuery(query);
            if (rs.next()) {
                tfroom.setText(rs.getString("room"));
            }

            String roomNo = tfroom.getText();
            String roomQuery = "SELECT * FROM room WHERE roomno='" + roomNo + "'";
            ResultSet rs2 = conn.statement.executeQuery(roomQuery);

            if (rs2.next()) {
                tfavailable.setText(rs2.getString("availability"));
                tfstatus.setText(rs2.getString("cleanliness"));
            } else {
                JOptionPane.showMessageDialog(null, "No Data Found");
            }
            conn.closeConnection();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching room details.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UpdateRoomStatus();
    }
}
