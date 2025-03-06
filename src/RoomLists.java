

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import databse.DatabaseConnect;
import net.proteanit.sql.*;

public class RoomLists extends JFrame implements ActionListener {

    JTable table;
    Dashboard.RoundedGradientButton addRoom, back, addReservation;

    RoomLists(){
        setTitle("Hotel Management System - Room Management");
        setVisible(true);
        setLayout(null);
        setBounds(300,200,1100,600);
        getContentPane().setBackground(Color.white);
        setVisible(true);


        JLabel title = new JLabel("ROOM DETAILS", JLabel.CENTER);
        title.setFont(new Font("Raleway", Font.BOLD, 22));
        title.setForeground(Color.BLACK);
        title.setBounds(350, 10, 400, 30);
        add(title);

        // Add Button
        addRoom = new Dashboard.RoundedGradientButton("Add Room", null);
        addRoom.setFont(new Font("Raleway", Font.BOLD, 16));
        addRoom.setBackground(Color.BLACK);
        addRoom.setForeground(Color.WHITE);
        addRoom.setBounds(30, 10, 120, 30);
        add(addRoom);
        addRoom.addActionListener(this);


        // Add Button
        addReservation = new Dashboard.RoundedGradientButton("Add Reservation", null);
        addReservation.setFont(new Font("Raleway", Font.BOLD, 16));
        addReservation.setBackground(Color.BLACK);
        addReservation.setForeground(Color.WHITE);
        addReservation.setBounds(180, 10, 220, 30);
        add(addReservation);
        addReservation.addActionListener(this);

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
        scrollPane.setBounds(30, 60, 1000, 400);
        add(scrollPane);

        // Fetch data from the database
        try {
            DatabaseConnect conn = new DatabaseConnect();
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM room");
            table.setModel(DbUtils.resultSetToTableModel(rs));

            // Apply center alignment to all columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Back Button
        back = new Dashboard.RoundedGradientButton("BACK", null);
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
            new Dashboard();
        } else if (ae.getSource()==addRoom) {
            setVisible(false);
            new AddRooms();
        } else if (ae.getSource()==addReservation) {
            setVisible(false);
            new AddReservation();
        }
    }
    public static void main(String args[]){
        new RoomLists();
    }
}
