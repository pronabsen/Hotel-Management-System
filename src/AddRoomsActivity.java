import databse.DatabaseConnect;
import validation.NumberValidation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import widgets.RoundedGradientButton;

public class AddRoomsActivity extends JFrame implements ActionListener {
    RoundedGradientButton addRoom;
    RoundedGradientButton cancel;
    JTextField tfprice, tfroom;
    JComboBox<String> bedCombo, cleanCombo, availableCombo;
    DatabaseConnect connect; // Use a single connection instance

    AddRoomsActivity() {
        setTitle("Hotel Management System - Add Room");
        setLayout(null);
        setVisible(true);
        getContentPane().setBackground(Color.white);
        setBounds(330, 200, 480, 470);

        connect = new DatabaseConnect(); // Initialize Database Connection

        Font font = new Font("Raleway", Font.PLAIN, 16);
        JLabel heading = new JLabel("ADD ROOMS");
        heading.setBounds(150, 20, 200, 20);
        add(heading);
        heading.setFont(new Font("Raleway", Font.BOLD, 18));

        JLabel roomno = new JLabel("Room Number");
        roomno.setBounds(60, 80, 120, 30);
        roomno.setFont(font);
        add(roomno);

        tfroom = new JTextField();
        tfroom.setBounds(200, 80, 180, 30);
        add(tfroom);

        // Fetch Last Room Number
        try {
            String query = "SELECT MAX(roomno) FROM room";
            try (PreparedStatement pstmt = connect.connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int lastRoomNumber = rs.getInt(1);
                    tfroom.setText(String.valueOf(lastRoomNumber + 1));
                } else {
                    tfroom.setText("1");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching room number.");
            System.out.println(e.getMessage());
        }

        JLabel roomAvailable = new JLabel("Available");
        roomAvailable.setBounds(60, 130, 120, 30);
        roomAvailable.setFont(font);
        add(roomAvailable);

        String availableValues[] = {"Available", "Occupied"};
        availableCombo = new JComboBox<>(availableValues);
        availableCombo.setBounds(200, 130, 180, 30);
        availableCombo.setBackground(Color.white);
        add(availableCombo);

        JLabel cleanStatus = new JLabel("Cleaning Status");
        cleanStatus.setBounds(60, 180, 120, 30);
        cleanStatus.setFont(font);
        add(cleanStatus);

        String cleanValues[] = {"Clean", "Dirty"};
        cleanCombo = new JComboBox<>(cleanValues);
        cleanCombo.setBounds(200, 180, 180, 30);
        cleanCombo.setBackground(Color.white);
        add(cleanCombo);

        JLabel price = new JLabel("Price");
        price.setFont(font);
        price.setBounds(60, 230, 130, 30);
        add(price);

        tfprice = new JTextField();
        tfprice.setBounds(200, 230, 180, 30);
        add(tfprice);

        JLabel bedType = new JLabel("Bed Type");
        bedType.setBounds(60, 280, 130, 30);
        bedType.setFont(font);
        add(bedType);

        String bedValues[] = {"Single Bed", "Double Bed"};
        bedCombo = new JComboBox<>(bedValues);
        bedCombo.setBounds(200, 280, 180, 30);
        bedCombo.setBackground(Color.white);
        add(bedCombo);

        addRoom = new RoundedGradientButton("Add Room",  null);
        addRoom.setBounds(60, 350, 130, 30);
        addRoom.setBackground(Color.black);
        addRoom.setForeground(Color.white);
        add(addRoom);
        addRoom.addActionListener(this);

        cancel = new RoundedGradientButton("Cancel", null);
        cancel.setBounds(220, 350, 130, 30);
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.white);
        add(cancel);
        cancel.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == cancel) {
            setVisible(false);
            new RoomListsActivity();
        }
        if (ae.getSource() == addRoom) {
            String roomno = tfroom.getText();
            String availability = (String) availableCombo.getSelectedItem();
            String status = (String) cleanCombo.getSelectedItem();
            String type = (String) bedCombo.getSelectedItem();
            String price = tfprice.getText();

            if (roomno.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are Mandatory");
            } else if (!NumberValidation.containsOnlyNumbers(roomno)) {
                JOptionPane.showMessageDialog(null, "Please enter Correct Room no");
            } else if (!NumberValidation.containsOnlyNumbers(price)) {
                JOptionPane.showMessageDialog(null, "Please enter valid Price\n ONLY Numbers allowed");
            } else {
                try {
                    // Check if Room Exists
                    String checkQuery = "SELECT * FROM room WHERE roomno = ?";
                    try (PreparedStatement checkStmt = connect.connection.prepareStatement(checkQuery)) {
                        checkStmt.setString(1, roomno);
                        ResultSet rs = checkStmt.executeQuery();
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(null, "Room number already exists!");
                            return;
                        }
                    }

                    // Insert New Room
                    String insertQuery = "INSERT INTO room VALUES(?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = connect.connection.prepareStatement(insertQuery)) {
                        pstmt.setString(1, roomno);
                        pstmt.setString(2, availability);
                        pstmt.setString(3, status);
                        pstmt.setString(4, price);
                        pstmt.setString(5, type);
                        pstmt.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "New Room Added Successfully");
                    setVisible(false);
                    new RoomListsActivity();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "Error adding room. Please try again.");
                }
            }
        }
    }

    public static void main(String args[]) {
        new AddRoomsActivity();
    }
}
