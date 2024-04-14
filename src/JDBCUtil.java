/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import Example01.DBUtils.Utils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class studentMainFrame extends JFrame{
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    private JFrame frame;
    private JTable table;
    private JScrollPane jsp;
    private DefaultTableModel dtm;
    private DefaultTableCellRenderer dtr;
    private JTextField textField_sno;
    private JTextField textField_sname;
    private JTextField textField_sex;
    private JTextField textField_birthday;
    private JTextField textField_phone;
    private JTextField textField_address;
    private Vector v;

    public static void main(String[] args) {
        new studentMainFrame().stuManager();
    }

    //创建学生表格
    public studentMainFrame(){
        frame = new JFrame();
        dtm = new DefaultTableModel();
        dtr = new DefaultTableCellRenderer();
        dtr.setHorizontalAlignment(JLabel.CENTER);
        table = new JTable(stuTableModel(dtm));
        table.setDefaultRenderer(Object.class,dtr);
        jsp = new JScrollPane();
        jsp.setViewportView(table);
        frame.getContentPane().add(jsp);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    //使用DefaultTableModel导入表中数据
    private DefaultTableModel stuTableModel(DefaultTableModel dtm){
        Statement st = null;
        ResultSet rs = null;
        String[] stu ={"学号","姓名","性别","生日","电话","地址"};
        dtm.setColumnIdentifiers(stu);
        Vector v = null;
        try {
            conn = Utils.getConnection();
            st = conn.createStatement();
            String sql = "select * from stu";
            rs = st.executeQuery(sql);
            while(rs.next()){
                v = new Vector();
                int sno = rs.getInt("学号");
                String name = rs.getString("姓名");
                String sex = rs.getString("性别");
                String birthday = rs.getString("生日");
                String phone = rs.getString("电话");
                String address = rs.getString("地址");
                v.add(sno);
                v.add(name);
                v.add(sex);
                v.add(birthday);
                v.add(phone);
                v.add(address);
                dtm.addRow(v);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dtm;
    }

    //学生信息操作界面
    public void stuManager(){
        JLabel lblNewLabel = new JLabel("\u5B66\u53F7");
        lblNewLabel.setBounds(24, 28, 58, 15);
        getContentPane().add(lblNewLabel);

        textField_sno = new JTextField();
        textField_sno.setBounds(109, 25, 103, 21);
        getContentPane().add(textField_sno);
        textField_sno.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("\u59D3\u540D");
        lblNewLabel_1.setBounds(24, 86, 58, 15);
        getContentPane().add(lblNewLabel_1);

        textField_sname = new JTextField();
        textField_sname.setBounds(109, 83, 103, 21);
        getContentPane().add(textField_sname);
        textField_sname.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("\u6027\u522B");
        lblNewLabel_2.setBounds(24, 151, 58, 15);
        getContentPane().add(lblNewLabel_2);

        textField_sex = new JTextField();
        textField_sex.setBounds(109, 148, 103, 21);
        getContentPane().add(textField_sex);
        textField_sex.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("\u751F\u65E5");
        lblNewLabel_3.setBounds(24, 212, 58, 15);
        getContentPane().add(lblNewLabel_3);

        textField_birthday = new JTextField();
        textField_birthday.setBounds(109, 209, 103, 21);
        getContentPane().add(textField_birthday);
        textField_birthday.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("\u7535\u8BDD\u53F7\u7801");
        lblNewLabel_4.setBounds(24, 280, 58, 15);
        getContentPane().add(lblNewLabel_4);

        textField_phone = new JTextField();
        textField_phone.setBounds(109, 277, 103, 21);
        getContentPane().add(textField_phone);
        textField_phone.setColumns(10);

        JLabel lblNewLabel_5 = new JLabel("\u4F4F\u5740");
        lblNewLabel_5.setBounds(24, 349, 58, 15);
        getContentPane().add(lblNewLabel_5);

        textField_address = new JTextField();
        textField_address.setBounds(109, 346, 103, 21);
        getContentPane().add(textField_address);
        textField_address.setColumns(10);

        JButton btnNewButton_add = new JButton("添加");
        btnNewButton_add.setBounds(306, 24, 97, 23);
        btnNewButton_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add(textField_sno,textField_sname,textField_sex,textField_birthday,textField_phone,textField_address);
            }
        });
        getContentPane().add(btnNewButton_add);

        JButton btnNewButton_delete = new JButton("删除");
        btnNewButton_delete.setBounds(306, 110, 97, 23);
        btnNewButton_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete(textField_sno);
            }
        });
        getContentPane().add(btnNewButton_delete);

        JButton btnNewButton_alter = new JButton("修改");
        btnNewButton_alter.setBounds(306, 178, 97, 23);
        btnNewButton_alter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alter(textField_sno,textField_sname,textField_sex,textField_birthday,textField_phone,textField_address);
            }
        });
        getContentPane().add(btnNewButton_alter);

        JButton btnNewButton_query = new JButton("查询");
        btnNewButton_query.setBounds(306, 240, 97, 23);
        btnNewButton_query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query(textField_sno,textField_sname,textField_sex,textField_birthday,textField_phone,textField_address);
            }
        });
        getContentPane().add(btnNewButton_query);

        JButton btnNewButton_reset = new JButton("重置");
        btnNewButton_reset.setBounds(306, 323, 97, 23);
        btnNewButton_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset(textField_sno,textField_sname,textField_sex,textField_birthday,textField_phone,textField_address);
            }
        });
        getContentPane().add(btnNewButton_reset);
        setTitle("\u5B66\u751F\u4FE1\u606F\u64CD\u4F5C");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 473, 467);
        this.setSize(540,500);
        this.setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        this.setVisible(true);
    }

    //添加学生数据
    private void add(JTextField textField_sno,JTextField textField_sname,JTextField textField_sex,JTextField textField_birthday,JTextField textField_phone,JTextField textField_address){
        String sno = textField_sno.getText();
        String sname =textField_sname.getText();
        String sex = textField_sex.getText();
        String birthday = textField_birthday.getText();
        String phone = textField_phone.getText();
        String address = textField_address.getText();
        v = new Vector();
        v.add(sno);
        v.add(sname);
        v.add(sex);
        v.add(birthday);
        v.add(phone);
        v.add(address);
        dtm.addRow(v);
        dtm.setColumnIdentifiers(v);
        try {
            conn = Utils.getConnection();
            conn.setAutoCommit(false);
            Statement st =null;
            String sql = "insert into stu(学号,姓名,性别,生日,电话,地址) value(?,?,?,?,?,?)";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1,sno);
            pstm.setString(2,sname);
            pstm.setString(3,sex);
            pstm.setString(4,birthday);
            pstm.setString(5,phone);
            pstm.setString(6,address);
            pstm.executeUpdate();
            conn.commit();
            JOptionPane.showMessageDialog(null,"添加成功");
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null,"添加失败");

            throwables.printStackTrace();
        }
    }

    //删除学生数据
    private void delete(JTextField textField_sno){
        String sno = textField_sno.getText();
        try {
            conn = Utils.getConnection();
            String sql = "delete from stu where 学号 =?";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1,sno);
            pstm.execute();
            JOptionPane.showMessageDialog(null,"删除成功");
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null,"删除失败");
            throwables.printStackTrace();
        } finally {
            Utils.close(pstm,conn);
        }
    }

    //修改学生数据
    private void alter(JTextField textField_sno,JTextField textField_sname,JTextField textField_sex,JTextField textField_birthday,JTextField textField_phone,JTextField textField_address) {
        String sno = textField_sno.getText();
        String sname =textField_sname.getText();
        String sex = textField_sex.getText();
        String birthday = textField_birthday.getText();
        String phone = textField_phone.getText();
        String address = textField_address.getText();
        try {
            conn = Utils.getConnection();
            conn.setAutoCommit(false);
            String sql = "update stu set 学号=?,姓名=?,性别=?,生日=?,电话=?,地址=? where 学号=?";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1,sno);
            pstm.setString(2,sname);
            pstm.setString(3,sex);
            pstm.setString(4,birthday);
            pstm.setString(5,phone);
            pstm.setString(6,address);
            pstm.setString(7,sno);
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null,"修改成功");
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null,"修改失败");
            throwables.printStackTrace();
        } finally {
            Utils.close(pstm,conn);
        }
    }

    //查询学生数据
    private void query(JTextField textField_sno,JTextField textField_sname,JTextField textField_sex,JTextField textField_birthday,JTextField textField_phone,JTextField textField_address){
        String sno = textField_sno.getText();
        try {
            conn = Utils.getConnection();
            conn.setAutoCommit(false);
            String sql = "select * from stu where 学号=?";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1,sno);
            rs = pstm.executeQuery();
            while(rs.next()){
                String ssno = rs.getString("学号");
                if(sno.equals(ssno)){
                    textField_sno.setText(rs.getString("学号"));
                    textField_sname.setText( rs.getString("姓名"));
                    textField_sex.setText(rs.getString("性别"));
                    textField_birthday.setText(rs.getString("生日"));
                    textField_phone.setText(rs.getString("电话"));
                    textField_address.setText(rs.getString("地址"));
                }
            }
            JOptionPane.showMessageDialog(null,"查询成功");
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null,"查询失败");
            throwables.printStackTrace();
            Utils.close(pstm,conn,rs);
        }
    }

    //重置
    private void reset(JTextField textField_sno,JTextField textField_sname,JTextField textField_sex,JTextField textField_birthday,JTextField textField_phone,JTextField textField_address){
        textField_sno.setText("");
        textField_sname.setText("");
        textField_sex.setText("");
        textField_birthday.setText("");
        textField_phone.setText("");
        textField_address.setText("");
    }
}
————————————————
        版权声明：本文为CSDN博主「Ming_He2」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        原文链接：https://blog.csdn.net/qq_45648856/article/details/109331041
public class JDBCUtil {

    // 数据库的连接和使用
    public static void connectDB() throws Exception{
        // 1. 获取数据库
        String driverClassName="com.mysql.cj.jdbc.Driver"; // 查询数据库驱动的名字
        Class cla=Class.forName(driverClassName);// 获取类型

        // 2. 连接数据库
        String url="jdbc:mysql://localhost:3306/name?serverTimezone=UTC";
        Properties info=new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456789");
        Connection conn=DriverManager.getConnection(url, info);

        // 3. 显示连接成功与否
        if (conn!=null) {
            System.out.println("数据库连接成功！");
            // 4. 执行查询
            Statement statm=conn.createStatement();
            String sql="SELECT * FROM denglv";
            ResultSet rs=statm.executeQuery(sql);
            while (rs.next()) {
                String id=rs.getString("name");
                String name=rs.getString("ps");
                System.out.println("name="+id+",ps="+name);
            }
            // 增加一条记录
            sql="insert into denglv (name,ps) values(1001,"+"'1001'"+")";
            int n=statm.executeUpdate(sql);
            if (n>0) {
                System.out.println("数据库成功更新");
            }
            // 删除一条记录
            sql="delete from denglv where name ='"+1001+"'";
            n=statm.executeUpdate(sql);
            if (n>0) {
                System.out.println("删除了一条数据记录");
            }
            conn.close();
        }else {
            System.out.println("数据库连接失败！");
        }
    }
    }
    if (source == logButton) {

        } else if (source == signButton) {
            // 4. 执行查询
            statm = conn.createStatement();
            sql = "insert into denglv (name,ps) values(1001," + "'1001'" + ")";
            statm.executeUpdate(sql);

            // 删除一条记录
            sql = "delete from denglv where name ='" + 1001 + "'";
            statm.executeUpdate(sql);

            conn.close();
        }




*/
