package connecttoxampp;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class bai2 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txturl;
    private JTable table;
    private Connection conn;
    private JRadioButton rdbtnId;
    private JRadioButton rdbtnName;
    private JRadioButton rdbtnAddress;
    private JRadioButton rdbtngender;
    private JRadioButton rdbtnDate;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    bai2 frame = new bai2();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public bai2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 823, 522);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Tìm Kiếm Thông Tin");
        lblNewLabel.setBounds(230, 11, 270, 37);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Input Information");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(38, 59, 144, 32);
        contentPane.add(lblNewLabel_1);

        txturl = new JTextField();
        txturl.setText("");
        txturl.setBounds(172, 61, 257, 32);
        contentPane.add(txturl);
        txturl.setColumns(10);

        JButton btnNewButton = new JButton("OK");
        btnNewButton.setBounds(442, 61, 107, 32);
        contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ketnoi();
            }
        });

        JButton btnNewButton_1 = new JButton("Reset");
        btnNewButton_1.setBounds(559, 61, 107, 32);
        contentPane.add(btnNewButton_1);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txturl.setText("");
                rdbtnId.setSelected(false);
                rdbtnName.setSelected(false);
                rdbtnAddress.setSelected(false);
                rdbtngender.setSelected(false);
                rdbtnDate.setSelected(false);
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
            }
        });

        JButton btnNewButton_1_1 = new JButton("Cancel");
        btnNewButton_1_1.setBounds(676, 61, 107, 32);
        contentPane.add(btnNewButton_1_1);
        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Đóng cửa sổ
                dispose();
            }
        });

        rdbtnId = new JRadioButton("Id");
        rdbtnId.setFont(new Font("Tahoma", Font.PLAIN, 15));
        rdbtnId.setBounds(274, 124, 47, 23);
        contentPane.add(rdbtnId);

        rdbtnName = new JRadioButton("Name");
        rdbtnName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        rdbtnName.setBounds(323, 124, 68, 23);
        contentPane.add(rdbtnName);

        rdbtnAddress = new JRadioButton("Address");
        rdbtnAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
        rdbtnAddress.setBounds(500, 124, 88, 23);
        contentPane.add(rdbtnAddress);

        rdbtngender = new JRadioButton("Gender");
        rdbtngender.setFont(new Font("Tahoma", Font.PLAIN, 15));
        rdbtngender.setBounds(601, 124, 88, 23);
        contentPane.add(rdbtngender);

        rdbtnDate = new JRadioButton("Date");
        rdbtnDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
        rdbtnDate.setBounds(412, 124, 88, 23);
        contentPane.add(rdbtnDate);

     
        
        
        
        table = new JTable(); 
        DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Mã Sinh Viên", "Họ Tên", "Ngày Sinh","Địa Chỉ ", "Giới Tính"}
        );
        table.setModel(tableModel);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(29, 175, 754, 241); 
        contentPane.add(scrollPane);
    
        JLabel lblNewLabel_2 = new JLabel("Search as");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_2.setBounds(149, 128, 75, 14);
        contentPane.add(lblNewLabel_2);
    }

    private void ketnoi() {
        String url = "jdbc:mysql://127.0.0.1:3306/DATA";
        String thongtin = txturl.getText().trim();
        try {
            conn = DriverManager.getConnection(url, "root", "");
            String query = buildQuery();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, thongtin);
            ResultSet rs = pstmt.executeQuery();
            hienthi(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String buildQuery() {
        StringBuilder query = new StringBuilder("SELECT * FROM table3 WHERE ");
        boolean isRadioSelected = false;

        String inputInfo = txturl.getText().trim();

        if (!inputInfo.isEmpty()) {
            if (rdbtnId.isSelected()) {
                query.append("MaSV = ?");
                isRadioSelected = true;
            }
            if (rdbtnName.isSelected()) {
                if (isRadioSelected) {
                    query.append(" AND ");
                }
                query.append("HoTen = ?");
                isRadioSelected = true;
            }
            if (rdbtnDate.isSelected()) {
                if (isRadioSelected) {
                    query.append(" AND ");
                }
                query.append("Ngaysinh = ?");
                isRadioSelected = true;
            }
            if (rdbtnAddress.isSelected()) {
                if (isRadioSelected) {
                    query.append(" AND ");
                }
                query.append("Diachi = ?");
                isRadioSelected = true;
            }
            if (rdbtngender.isSelected()) {
                if (isRadioSelected) {
                    query.append(" AND ");
                }
                query.append("Gioitinh = ?");
                isRadioSelected = true;
            }
           
        }

        return query.toString();
    }

    private void hienthi(ResultSet rs) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã SV");
        model.addColumn("Họ Tên");
        model.addColumn("Ngày Sinh");
        model.addColumn("Địa Chỉ");
        model.addColumn("Giới Tính");

        while (rs.next()) {
            Object[] row = new Object[5];
            row[0] = rs.getInt("MaSV");
            row[1] = rs.getString("HoTen");
            row[2] = rs.getString("Ngaysinh");
            row[3] = rs.getString("Diachi");
            row[4] = rs.getString("Gioitinh");
            model.addRow(row);
        }
        table.setModel(model);
    }
}
