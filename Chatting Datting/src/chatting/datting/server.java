package chatting.datting;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class server implements ActionListener {

    static JFrame f = new JFrame();
    JTextField text;
    JPanel jp1;
    static Box vertical = Box.createVerticalBox();

    static DataOutputStream dos;

        //for frame
    server(){

        //we have to specify that , we dont use swing layout.
        f.setLayout(null);

        //whenever we work upword the fram then use jpanel
        JPanel jp = new JPanel();
        jp.setBackground(new Color(7,94,84));
        jp.setBounds(0,0,450,70);
        jp.setLayout(null);
        f.add(jp);

        //for back button image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        jp.add(back);

        //back button clicked event
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                System.exit(0);
            }
        });

        //for profile image
        ImageIcon p1 = new ImageIcon(ClassLoader.getSystemResource("icons/micky.jpeg"));
        Image p2 = p1.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon p3 = new ImageIcon(p2);
        JLabel profile = new JLabel(p3);
        profile.setBounds(40,10,50,50);
        jp.add(profile);

        //for video call image
        ImageIcon v1 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image v2 = v1.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon v3 = new ImageIcon(v2);
        JLabel videocall = new JLabel(v3);
        videocall.setBounds(300,20,30,30);
        jp.add(videocall);

        //for call image
        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image c2 = c1.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon c3 = new ImageIcon(c2);
        JLabel call = new JLabel(c3);
        call.setBounds(350,20,35,30);
        jp.add(call);

        //for 3 dots image
        ImageIcon d1 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image d2 = d1.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon d3 = new ImageIcon(d2);
        JLabel dots = new JLabel(d3);
        dots.setBounds(410,25,10,25);
        jp.add(dots);

        //for name
        JLabel name = new JLabel("Mouse");
        name.setBounds(110,15,100,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SARIF",Font.BOLD,18));
        jp.add(name);

        //for stutas
        JLabel stutas = new JLabel("Active Now");
        stutas.setBounds(110,35,100,20);
        stutas.setForeground(Color.WHITE);
        stutas.setFont(new Font("SAN_SARIF",Font.BOLD,14));
        jp.add(stutas);

        //for text panel
        jp1 = new JPanel();
        jp1.setBounds(5,75,440,570);
        f.add(jp1);

        //text type
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SARIF",Font.PLAIN,16));
        f.add(text);

        //for send button
        JButton send = new JButton("SEND");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SARIF",Font.PLAIN,16));
        f.add(send);



        //for size of application
        f.setSize(450,700);
        f.setLocation(200,10);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        try {
            String out = text.getText();

            JPanel jp2 = formatLabel(out);

            jp1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(jp2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            jp1.add(vertical, BorderLayout.PAGE_START);

            dos.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){

        JPanel jp3 = new JPanel();
        jp3.setLayout(new BoxLayout(jp3,BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html> <p style=\"width: 150px\">" + out + "</p> </html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));

        jp3.add(output);

        //for time
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        jp3.add(time);


        return jp3;
    }

    public static void main(String[] args) {

        //anonymous object
        new server();

        //for create server using socket programming
        try{
            ServerSocket ss = new ServerSocket(6001);
            while (true){
                Socket s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());

                while (true){

                    String msg = dis.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }

            }

        }catch (Exception e){

            e.printStackTrace();

        }
    }
}
