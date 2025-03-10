import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import widgets.RoundedGradientButton;

public class DashboardActivity extends JFrame implements ActionListener {
    RoundedGradientButton newCustomer, rooms, customers, checkout, searchRoom, update, roomStatus, about, logout;

    DashboardActivity() {
        setTitle("Hotel Management System - Dashboard");
        setSize(1365, 864);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/hotel_bg.png"));
        Image i2 = i1.getImage().getScaledInstance(1365, 864, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 1365, 864);


        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1365, 864);
        layeredPane.add(background, Integer.valueOf(0));

        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(300, 60, 800, 80);
        titlePanel.setOpaque(false);

        JLabel text = new JLabel("THE HOTEL WUB WELCOMES YOU!");
        text.setFont(new Font("Tahoma", Font.BOLD, 40));
        text.setForeground(Color.orange);
        titlePanel.add(text);
        layeredPane.add(titlePanel, Integer.valueOf(1));

        JPanel titleDev = new JPanel();
        titleDev.setBounds(300, 790, 800, 80);
        titleDev.setOpaque(false);

        JLabel text2 = new JLabel("Software Develop by - Pronab Sen Gupta | Version 1.0.0");
        text2.setFont(new Font("Tahoma", Font.BOLD, 12));
        text2.setForeground(Color.white);
        titleDev.add(text2);
        layeredPane.add(titleDev, Integer.valueOf(1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(350, 250, 700, 500);
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(4, 3, 30, 30)); // 4 rows, 3 columns, spacing

        newCustomer = new RoundedGradientButton("Add New Guests", "/icons/users.png");
        rooms = new RoundedGradientButton("Rooms", "/icons/hotel.png");
        customers = new RoundedGradientButton("Guests Info", "/icons/user.png");
        checkout = new RoundedGradientButton("Checkout", "/icons/checkout.png");
        update = new RoundedGradientButton("Update Status", "/icons/profile.png");
        roomStatus = new RoundedGradientButton("Update Room Status", "/icons/room.png");
        searchRoom = new RoundedGradientButton("Search Room", "/icons/loupe.png");
        about = new RoundedGradientButton("About", "/icons/about.png");
        logout = new RoundedGradientButton("Logout", "/icons/logout.png");

        RoundedGradientButton[] buttons = {newCustomer, rooms, customers,checkout, update, roomStatus, searchRoom, logout, about};

        for (RoundedGradientButton btn : buttons) {
            btn.addActionListener(this);
            buttonPanel.add(btn);
        }

        layeredPane.add(buttonPanel, Integer.valueOf(1)); // Add buttons above background

        add(layeredPane);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == newCustomer) {
            setVisible(false);
            new AddGuestsActivity();
        } else if (ae.getSource() == rooms) {
            setVisible(false);
            new RoomListsActivity();
        } else if (ae.getSource() == customers) {
            setVisible(false);
            new CustomerListActivity();
        } else if (ae.getSource() == searchRoom) {
            setVisible(false);
            new SearchRoomsActivity();
        } else if (ae.getSource() == update) {
            setVisible(false);
            new UpdateStatusCheck();
        } else if (ae.getSource() == roomStatus) {
            setVisible(false);
            new UpdateRoomStatus();
        } else if (ae.getSource() == checkout) {
            setVisible(false);
            new GuestCheckoutActivity();
        } else if (ae.getSource() == about) {

            String aboutInfo = "<html><b>Hotel Management System</b><br>" +
                    "<b>Version:</b> 1.0.0<br><br>" +
                    "<b>Developer:</b><br>" +
                    "<b>Pronab Sen Gupta</b><br>" +
                    "<b>Batch: 67B | Roll: 4167</b><br>" +
                    "<b>Department of CSE</b><br>" +
                    "<b>World University of Bangladesh</b><br><br>" +
                    "<b>Contact:</b><br>" +
                    "<b>pronabsen18@gmail.com</b><br><br>" +
                    "<b>Build Year:</b> 2025</html>";

            JOptionPane.showMessageDialog(null, aboutInfo, "About", JOptionPane.INFORMATION_MESSAGE);
        } else if (ae.getSource() == logout) {
            setVisible(false);
            JOptionPane.showMessageDialog(null, "Logout Successfully");
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        new DashboardActivity();
    }

}
