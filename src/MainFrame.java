import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class MainFrame extends JFrame implements ActionListener {
    public MainFrame() {
        try {
            connectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setSize(460, 460);
        this.setLayout(null);
        this.setTitle("登录界面 v1.0");
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(154, 180, 205));
        initFrame();
        this.setVisible(true);
    }

    Statement statm;
    String sql;
    Connection conn;
    private void connectDB() throws Exception {
        // 1. 获取数据库
        String driverClassName = "com.mysql.cj.jdbc.Driver"; // 查询数据库驱动的名字
        Class cla = Class.forName(driverClassName);// 获取类型

        // 2. 连接数据库
        String url = "jdbc:mysql://localhost:3306/name?serverTimezone=UTC";
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456789");
        conn = DriverManager.getConnection(url, info);

        if (conn != null) {
            statm = conn.createStatement();
        } else {
            System.out.println("数据库连接失败！");
        }
    }

    String name;
    String pw;


    JTextField jTextField1;
    JTextField jTextField2;

    //jTextField1.getText();
    JRadioButton jb1;
    JRadioButton jb2;

    private void initFrame() {

        JLabel jLabel = new JLabel("学生信息管理系统");
        jLabel.setFont(new Font("华文彩云", Font.BOLD, 30));
        jLabel.setBounds(100,20,300,80);
        this.getContentPane().add(jLabel);

        JLabel jLabel1 = new JLabel("登录:");
        jLabel1.setBounds(130, 100, 30, 20);
        jTextField1 = new JTextField();
        jTextField1.setBounds(160, 100, 150, 20);
        JLabel jLabel2 = new JLabel("密码:");
        jLabel2.setBounds(130, 130, 30, 20);
        jTextField2 = new JTextField();
        jTextField2.setBounds(160, 130, 150, 20);
        this.getContentPane().add(jTextField1);
        this.getContentPane().add(jTextField2);
        this.getContentPane().add(jLabel1);
        this.getContentPane().add(jLabel2);

        jb1 = new JRadioButton("用户", true);
        jb1.setBounds(160, 240, 60, 20);
        jb2 = new JRadioButton("管理员");
        jb2.setBounds(220, 240, 70, 20);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(jb1);
        buttonGroup.add(jb2);
        this.getContentPane().add(jb1);
        this.getContentPane().add(jb2);

        logButton = new JButton("登录");
        logButton.addActionListener(this);
        signButton = new JButton("注册");
        signButton.addActionListener(this);
        logButton.setBounds(135, 300, 70, 30);
        signButton.setBounds(245, 300, 70, 30);
        this.getContentPane().add(logButton);
        this.getContentPane().add(signButton);

        ImageIcon imageIcon = new ImageIcon("image\\a.png");
        JLabel jLabel3 = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(260, 260, Image.SCALE_DEFAULT)));
        jLabel3.setBounds(95,0,260,260);
        this.getContentPane().add(jLabel3);

        ImageIcon imageIcon2 = new ImageIcon("image\\c.png");
        JLabel jLabel5 = new JLabel(new ImageIcon(imageIcon2.getImage().getScaledInstance(160, 60, Image.SCALE_DEFAULT)));
        jLabel5.setBounds(142, 220,160,60);
        this.getContentPane().add(jLabel5);

        ImageIcon imageIcon1 = new ImageIcon("image\\b.png");
        JLabel jLabel4 = new JLabel(new ImageIcon(imageIcon1.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT)));
        jLabel4.setBounds(74,135,300,300);
        this.getContentPane().add(jLabel4);
    }

    JButton logButton;
    JButton signButton;

    boolean flag=false;
    @Override
    public void actionPerformed(ActionEvent ee) {
        name=jTextField1.getText();
        pw=jTextField2.getText();
        Object source = ee.getSource();
        String num=null;
        if (jb1.isSelected()){
            num="denglv_tmp";
        } else if (jb2.isSelected()) {
            num="denglv";
        }
        try {
            if (source == logButton) {
                String sql="SELECT * FROM "+num;
                ResultSet rs=statm.executeQuery(sql);
                while (rs.next()) {
                    String name_tmp=rs.getString("name");
                    String pw_tmp=rs.getString("ps");
                    if (name.equals(name_tmp)&&pw.equals(pw_tmp)){
                        flag=true;
                        System.out.println("登录成功");
                        conn.close();
                        break;
                    }
                }
                if (!flag){
                    JDialog jDialog = new JDialog(this);
                    JLabel jLabel = new JLabel("登录失败");
                    jLabel.setFont(new Font("华文彩云", Font.BOLD, 80));
                    jLabel.setBounds(10,0,100,60);
                    jDialog.getContentPane().add(jLabel);
                    jDialog.setSize(344,150);
                    jDialog.setAlwaysOnTop(true);//置顶
                    jDialog.setLocationRelativeTo(null);//居中
                    jDialog.setModal(true);//弹框不关闭就无法操作下面的界面
                    jDialog.setVisible(true);
                } else if (flag) {
                    if (jb1.isSelected()){
                        new UserFrame();
                    }else {
                        new ManagerFrame();
                    }
                    this.dispose();
                }
            } else if (source == signButton) {
                sql = "insert into "+num+" (name,ps) values("+"'"+name+"'," +"'"+pw + "')";
                statm.executeUpdate(sql);
                System.out.println("注册成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
