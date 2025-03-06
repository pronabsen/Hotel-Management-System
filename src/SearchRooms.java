import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import databse.DatabaseConnect;
import net.proteanit.sql.DbUtils;

public class SearchRooms extends JFrame implements ActionListener {

    JTable table;
    Dashboard.RoundedGradientButton back, submit;
    JComboBox<String> bedtypecombo;
    JCheckBox available;

    SearchRooms() {
        setTitle("Hotel Management System - Search Rooms");
        setBounds(300, 150, 1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        getContentPane().setBackground(Color.white);

        // **Title JLabel**
        JLabel title = new JLabel("SEARCH FOR ROOM", JLabel.CENTER);
        title.setFont(new Font("Raleway", Font.BOLD, 22));
        title.setForeground(Color.BLACK);
        title.setBounds(350, 20, 400, 30); // Centered at the top
        add(title);

        // **Bed Type Selection**
        JLabel lbBed = new JLabel("BED TYPE:");
        lbBed.setBounds(50, 80, 100, 25);
        lbBed.setFont(new Font("Raleway", Font.BOLD, 16));
        add(lbBed);

        String[] bedValues = {"All","Single Bed", "Double Bed"};
        bedtypecombo = new JComboBox<>(bedValues);
        bedtypecombo.setBounds(160, 80, 150, 25);
        bedtypecombo.setFont(new Font("Raleway", Font.PLAIN, 14));
        bedtypecombo.setBackground(Color.white);
        add(bedtypecombo);

        // **Availability Checkbox**
        available = new JCheckBox("ONLY DISPLAY AVAILABLE");
        available.setBounds(400, 80, 250, 25);
        available.setFont(new Font("Raleway", Font.BOLD, 14));
        available.setBackground(Color.white);
        add(available);

        submit = new Dashboard.RoundedGradientButton("Filter", null);
        submit.setBounds(650, 80, 120, 35);
        submit.setFont(new Font("Raleway", Font.BOLD, 14));
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        add(submit);

        // **Table Setup**
        table = new JTable();
        table.setFont(new Font("Raleway", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.setDefaultEditor(Object.class, null); // Prevents editing

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 130, 1000, 400);
        add(scrollPane);

        // **Header Customization**
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Raleway", Font.BOLD, 16));
        header.setForeground(Color.BLACK);
        header.setBackground(Color.LIGHT_GRAY);

        // Convert headers to ALL CAPS
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setFont(new Font("Raleway", Font.BOLD, 16));

        table.getTableHeader().setDefaultRenderer(headerRenderer);



        back = new Dashboard.RoundedGradientButton("BACK", null);
        back.setBounds(500, 550, 120, 35);
        back.setFont(new Font("Raleway", Font.BOLD, 14));
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        // **Load Data Initially**
        loadTableData("SELECT * FROM room");
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            new Dashboard();
        } else if (ae.getSource() == submit) {
            String query;
            String selectedBedType = bedtypecombo.getSelectedItem().toString();

            if (available.isSelected()) {
                if (selectedBedType.equals("All")) {
                    query = "SELECT * FROM room WHERE availability='Available'";
                } else  {
                    query = "SELECT * FROM room WHERE availability='Available' AND bedtype='" + selectedBedType + "'";
                }
            } else if (selectedBedType.equals("All")) {
                query = "SELECT * FROM room";
            }else {
                query = "SELECT * FROM room WHERE bedtype='" + selectedBedType + "'";
            }

            loadTableData(query);
        }
    }

    private void loadTableData(String query) {
        try {
            DatabaseConnect conn = new DatabaseConnect();
            ResultSet rs = conn.statement.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));

            // Center align table content
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SearchRooms();
    }
}
