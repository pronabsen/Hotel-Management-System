import databse.DatabaseConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;
public class Checkout extends JFrame implements ActionListener {

    Choice customerid;
    JLabel lbroomno,lbcheckintime,lbcheckOutTime;
    Dashboard.RoundedGradientButton checkOut,back;

    Checkout(){
        setTitle("Hotel Management System - Guests Checkout");
        setLayout(null);
        setVisible(true);
        getContentPane().setBackground(Color.white);
        setBounds(300,200,480,400);

        JLabel text=new JLabel("Checkout");
        text.setBounds(100,20,100,30);
        text.setForeground(Color.black);
        text.setFont(new Font("Raleway",Font.BOLD,22));
        add(text);

        JLabel lbid=new JLabel("Customer ID");
        lbid.setBounds(30,80,100,30);
        add(lbid);

        customerid=new Choice();
        customerid.setBounds(150,80,150,25);
        add(customerid);

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("images/tick.png"));
        Image i2=i1.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);

        JLabel tick=new JLabel(i3);
        tick.setBounds(310,80,20,20);
        add(tick);

        JLabel lbroom=new JLabel("Room No");
        lbroom.setBounds(30,130,100,30);
        add(lbroom);

        lbroomno=new JLabel();
        lbroomno.setBounds(150,130,100,30);
        add(lbroomno);

        JLabel lbchekin=new JLabel("Checkin Time");
        lbchekin.setBounds(30,180,100,30);
        add(lbchekin);

        lbcheckintime=new JLabel();
        lbcheckintime.setBounds(150,180,100,30);
        add(lbcheckintime);

        JLabel lbcheckout=new JLabel("Checkout Time");
        lbcheckout.setBounds(30,230,100,30);
        add(lbcheckout);

        Date date=new Date();
        lbcheckOutTime=new JLabel(""+date);
        lbcheckOutTime.setBounds(150,230,150,30);
        add(lbcheckOutTime);


        checkOut=new Dashboard.RoundedGradientButton("CHECK OUT", null);
        checkOut.setBounds(30,280,120,30);
        checkOut.setForeground(Color.white);
        checkOut.setBackground(Color.BLACK);
        add(checkOut);
        checkOut.addActionListener(this);

        back=new Dashboard.RoundedGradientButton("BACK", null);
        back.setBounds(200,280,120,30);
        back.setForeground(Color.white);
        back.setBackground(Color.BLACK);
        add(back);
        back.addActionListener(this);

        try {
            DatabaseConnect conn=new DatabaseConnect();
            ResultSet rs=conn.statement.executeQuery("select * from customer where status != 'Check Out'");
            while (rs.next()){
                customerid.add(rs.getString("number"));
                lbroomno.setText(rs.getString("room"));
                lbcheckintime.setText(rs.getString("time"));
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==checkOut ){


            try {

                if (customerid.getSelectedItem() == null){

                    JOptionPane.showMessageDialog(null, "Please Select Customer ");

                } else {
                    String query1="update customer set status ='Check Out' where number='"+customerid.getSelectedItem()+"'";
                    String query2="update room set availability='Available' where roomno='"+lbroomno.getText()+"'";
                    DatabaseConnect conn=new DatabaseConnect();
                    conn.statement.executeUpdate(query1);
                    conn.statement.executeUpdate(query2);

                    JOptionPane.showMessageDialog(null,"Checkout Successfully");
                }
            }
            catch (Exception e){
                System.out.println(e);
            }

        } else if(ae.getSource()==back){
            setVisible(false);
            new Dashboard();
        }
    }
    public static void main(String  args[]){
        new Checkout();
    }
}
