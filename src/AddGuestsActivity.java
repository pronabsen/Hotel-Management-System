import databse.DatabaseConnect;
import validation.NumberValidation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import widgets.RoundedGradientButton;

public class AddGuestsActivity extends JFrame implements ActionListener {
    JComboBox<String> comboid;
    JTextField tfNumber, tfname, tfcountry, tfdeposite, tfmobileno;
    JRadioButton male, female;
    JLabel checkinTime;
    Choice croom;
    RoundedGradientButton add;
    RoundedGradientButton back;

    public AddGuestsActivity() {
        setTitle("Hotel Management System - Add Customer");
        setSize(600, 700);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
        setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 600, 700);


        Font font = new Font("Raleway", Font.PLAIN, 20);

        JPanel formPanel = new JPanel();
        formPanel.setBounds(20, 50, 480, 600);
        formPanel.setOpaque(false);
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(null);
        layeredPane.add(formPanel, Integer.valueOf(0));

        JLabel text = new JLabel("NEW CUSTOMER FORM");
        text.setBounds(100, 10, 300, 40);
        text.setFont(new Font("Raleway", Font.BOLD, 22));
        text.setForeground(Color.black);
        formPanel.add(text);

        JLabel lbid = new JLabel("Document ID");
        lbid.setBounds(35, 80, 200, 20);
        lbid.setFont(font);
        lbid.setForeground(Color.black);
        formPanel.add(lbid);

        String[] idvalues = {"NID", "Passport", "Driving Licence"};
        comboid = new JComboBox<>(idvalues);
        comboid.setBounds(200, 80, 220, 25);
        comboid.setBackground(Color.black);
        comboid.setForeground(Color.white);
        formPanel.add(comboid);

        JLabel lbnumber = new JLabel("ID Number");
        lbnumber.setBounds(35, 120, 200, 20);
        lbnumber.setFont(font);
        lbnumber.setForeground(Color.black);
        formPanel.add(lbnumber);

        tfNumber = new JTextField();
        tfNumber.setBounds(200, 120, 220, 25);
        formPanel.add(tfNumber);

        JLabel lbname = new JLabel("Name");
        lbname.setBounds(35, 160, 200, 20);
        lbname.setFont(font);
        lbname.setForeground(Color.black);
        formPanel.add(lbname);

        tfname = new JTextField();
        tfname.setBounds(200, 160, 220, 25);
        formPanel.add(tfname);

        JLabel lbgender = new JLabel("Gender");
        lbgender.setBounds(35, 200, 200, 20);
        lbgender.setFont(font);
        lbgender.setForeground(Color.black);
        formPanel.add(lbgender);

        male = new JRadioButton("Male");
        male.setBounds(200, 200, 100, 25);
        male.setOpaque(false);
        male.setForeground(Color.black);
        formPanel.add(male);

        female = new JRadioButton("Female");
        female.setBounds(270, 200, 100, 25);
        female.setOpaque(false);
        female.setForeground(Color.black);
        formPanel.add(female);

        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);

        JLabel lbcountry = new JLabel("Country");
        lbcountry.setBounds(35, 240, 200, 20);
        lbcountry.setFont(font);
        lbcountry.setForeground(Color.black);
        formPanel.add(lbcountry);

        tfcountry = new JTextField();
        tfcountry.setBounds(200, 240, 220, 25);
        formPanel.add(tfcountry);

        JLabel lbroom = new JLabel("Room Number");
        lbroom.setBounds(35, 280, 150, 20);
        lbroom.setFont(font);
        lbroom.setForeground(Color.black);
        formPanel.add(lbroom);

        croom = new Choice();
        croom.setBounds(200, 280, 220, 25);
        formPanel.add(croom);

        try {
            DatabaseConnect conn = new DatabaseConnect();
            ResultSet rs = conn.statement.executeQuery("SELECT roomno FROM room WHERE availability='Available'");
            while (rs.next()) {
                croom.add(rs.getString("roomno"));
            }
            conn.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lbtime = new JLabel("Checkin Time");
        lbtime.setBounds(35, 320, 200, 20);
        lbtime.setFont(font);
        lbtime.setForeground(Color.black);
        formPanel.add(lbtime);

        checkinTime = new JLabel("" + new Date());
        checkinTime.setFont(new Font("Raleway", Font.PLAIN, 16));
        checkinTime.setBounds(200, 320, 300, 25);
        checkinTime.setForeground(Color.black);
        formPanel.add(checkinTime);

        JLabel lbdeposite = new JLabel("Deposit");
        lbdeposite.setBounds(35, 360, 200, 20);
        lbdeposite.setFont(font);
        lbdeposite.setForeground(Color.black);
        formPanel.add(lbdeposite);

        tfdeposite = new JTextField();
        tfdeposite.setBounds(200, 360, 220, 25);
        formPanel.add(tfdeposite);

        JLabel lbmobile = new JLabel("Mobile No");
        lbmobile.setBounds(35, 400, 200, 20);
        lbmobile.setFont(font);
        lbmobile.setForeground(Color.black);
        formPanel.add(lbmobile);

        tfmobileno = new JTextField();
        tfmobileno.setBounds(200, 400, 220, 25);
        formPanel.add(tfmobileno);

        add = new RoundedGradientButton("ADD", null);
        add.setBounds(120, 450, 100, 40);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        formPanel.add(add);
        add.addActionListener(this);

        back = new RoundedGradientButton("BACK", null);
        back.setBounds(300, 450, 100, 40);
        back.setBackground(Color.black);
        back.setForeground(Color.WHITE);
        formPanel.add(back);
        back.addActionListener(this);

        add(layeredPane);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            new DashboardActivity();
        } else if (ae.getSource() == add) {
            String id = (String) comboid.getSelectedItem();
            String name = tfname.getText();
            String number = tfNumber.getText();
            String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "";
            String country = tfcountry.getText();
            String deposite = tfdeposite.getText();
            String mobileno = tfmobileno.getText();
            String checkin = checkinTime.getText();
            String room = croom.getSelectedItem();

            // Validate inputs
            if (name.isEmpty() || number.isEmpty() || country.isEmpty() || deposite.isEmpty() || mobileno.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
                return;
            }

            if (!NumberValidation.isValidMobileNumber(mobileno)) {
                JOptionPane.showMessageDialog(null, "Invalid Mobile Number!");
                return;
            }

            try {
                DatabaseConnect conn = new DatabaseConnect();
                // Prepare SQL statements with placeholders
                String query = "INSERT INTO customer (id, number, name, gender, country, deposite, time, room, mobileno, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.connection.prepareStatement(query);
                pst.setString(1, id);
                pst.setString(2, number);
                pst.setString(3, name);
                pst.setString(4, gender);
                pst.setString(5, country);
                pst.setString(6, deposite);
                pst.setString(7, checkin);
                pst.setString(8, room);
                pst.setString(9, mobileno);
                pst.setString(10, "Check In");

                // Prepare update query for the room status
                String query2 = "UPDATE room SET availability = 'Occupied' WHERE roomno = ?";
                PreparedStatement pst2 = conn.connection.prepareStatement(query2);
                pst2.setString(1, room);

                // Execute queries
                pst.executeUpdate();
                pst2.executeUpdate();

                JOptionPane.showMessageDialog(null, "New Customer Added Successfully");
                setVisible(false);
                new DashboardActivity();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Database Error! Please try again.");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new AddGuestsActivity();
    }
}
