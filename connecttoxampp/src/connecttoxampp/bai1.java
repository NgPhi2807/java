package connecttoxampp;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class bai1 extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tf1;
    private JTextField tf2;
    private JTable table;
    private JButton btnOK;
    private JButton btnReset;
    private JButton btnExit;
    private JComboBox<String> comboBox;

  
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    bai1 frame = new bai1();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public bai1() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 597, 457);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("Input information");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(10, 51, 139, 25);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel = new JLabel("TRUY XUẤT CƠ SỞ DỮ LIÊU");
        lblNewLabel.setBounds(152, 10, 252, 25);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_2 = new JLabel("SQL Query");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_2.setBounds(10, 86, 139, 25);
        contentPane.add(lblNewLabel_2);
        
        tf1 = new JTextField("jdbc:mysql://127.0.0.1:3306/DATA");
        tf1.setBounds(145, 51, 345, 30);
        contentPane.add(tf1);
        tf1.setColumns(10);
        
        tf2 = new JTextField();
        tf2.setBounds(145, 86, 345, 30);
        contentPane.add(tf2);
        tf2.setColumns(10);
        
        JLabel lblNewLabel_2_1 = new JLabel("Option");
        lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_2_1.setBounds(10, 121, 139, 25);
        contentPane.add(lblNewLabel_2_1);
        
        comboBox = new JComboBox<>();
        comboBox.addItem("Insert");
        comboBox.addItem("Update");
        comboBox.addItem("Delete"); 
        comboBox.addItem("Select");
        comboBox.setBounds(145, 128, 345, 30);
        contentPane.add(comboBox);
        
        btnOK = new JButton("OK");
        btnOK.setBounds(30, 374, 101, 36);
        contentPane.add(btnOK);
        btnOK.addActionListener(this); 
        
        btnReset = new JButton("Reset");
        btnReset.setBounds(242, 374, 101, 36);
        contentPane.add(btnReset);
        btnReset.addActionListener(this);
        
        btnExit = new JButton("Exit");
        btnExit.setBounds(454, 374, 101, 36);
        contentPane.add(btnExit);
        btnExit.addActionListener(this);
        
    
        table = new JTable(); 
        DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Name", "Address", "Total"}
        );
        table.setModel(tableModel);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(29, 175, 500, 180); 
        contentPane.add(scrollPane);
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnOK) {
            String sqlQuery = tf2.getText();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0); 
            
            
            String selectedOption = (String) comboBox.getSelectedItem();
            if(selectedOption.equals("Select")) {
                ConnectDB(tableModel, sqlQuery);
            } else {
              
                if(selectedOption.equals("Insert")) {
                    Insert(sqlQuery);
                } else if(selectedOption.equals("Update")) {
                   Update(sqlQuery);
                } else if(selectedOption.equals("Delete")) {
                   Delete(sqlQuery);
                }
            }
        } else if (e.getSource() == btnReset) {
            tf1.setText("jdbc:mysql://127.0.0.1:3306/DATA");
            tf2.setText("");
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0); 
        } else if (e.getSource() == btnExit) {
           
            System.exit(0);
        }
    }

    public void ConnectDB(DefaultTableModel tableModel, String sqlQuery) {
         try {              
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://127.0.0.1:3306/DATA";
            Connection con=DriverManager.getConnection(url,"root","");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            while (rs.next()) {
                Object[] row = new Object[4]; 
                row[0] = rs.getInt("Id");
                row[1] = rs.getString("Name");
                row[2] = rs.getString("Address");
                row[3] = rs.getDouble("Total");
                tableModel.addRow(row);
            }
            
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void Insert(String sqlQuery) {
        try {              
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://127.0.0.1:3306/DATA";
            Connection con=DriverManager.getConnection(url,"root","");
            Statement stmt = con.createStatement();

            int rowsAffected = stmt.executeUpdate(sqlQuery);
            
            if(rowsAffected > 0) {
                System.out.println("Insert successful.");
            } else {
                System.out.println("Insert failed.");
            }

            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void Update(String sqlQuery) {
        try {              
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://127.0.0.1:3306/DATA";
            Connection con=DriverManager.getConnection(url,"root","");
            Statement stmt = con.createStatement();

            int rowsAffected = stmt.executeUpdate(sqlQuery);
            
            if(rowsAffected > 0) {
                System.out.println("Update successful.");
            } else {
                System.out.println("Update failed.");
            }

            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void Delete(String sqlQuery) {
        try {              
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://127.0.0.1:3306/DATA";
            Connection con=DriverManager.getConnection(url,"root","");
            Statement stmt = con.createStatement();

            int rowsAffected = stmt.executeUpdate(sqlQuery);
            
            if(rowsAffected > 0) {
                System.out.println("Delete successful.");
            } else {
                System.out.println("Delete failed.");
            }

            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
