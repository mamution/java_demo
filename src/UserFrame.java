import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;

public class UserFrame extends JFrame implements ActionListener {
    Color color;
    public UserFrame(){
        try {
            connectDB_tmp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setSize(800, 800);
        this.setLayout(null);
        this.setTitle("用户界面 v1.0");
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        color = new Color(154, 180, 205);
        this.getContentPane().setBackground(color);
        initFrame();
        this.setVisible(true);
    }
    Statement st;
    String sql;
    Connection conn;
    int count=0;
    private void connectDB_tmp() throws Exception {
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
            st = conn.createStatement();
        } else {
            System.out.println("数据库连接失败！");
        }
    }
    JTable table;
    DefaultTableModel dtm;
    JButton broButton;
    JTextArea infoArea;
    JButton infoButton;
    JButton quitButton;
    private void initFrame() {
        dtm = new DefaultTableModel();
        table = new JTable(dtm);
        JScrollPane jsp=new JScrollPane(table);
        jsp.setBounds(200, 30, 580, 720);
        table.setEnabled(false);//不可直接编辑表格
        this.getContentPane().add(jsp);

        JLabel jLabel = new JLabel("学生信息浏览表");
        jLabel.setFont(new Font("华文行楷",Font.BOLD, 20));
        jLabel.setBounds(200, 5, 200, 25);
        this.getContentPane().add(jLabel);

        broButton  = new JButton("学生信息录入");
        broButton .setBounds(34, 60, 140, 35);
        broButton.addActionListener(this);
        this.getContentPane().add(broButton);

        JLabel jLabel4 = new JLabel("查询信息学号:");
        jLabel4.setBounds(25, 140, 90, 18);
        this.getContentPane().add(jLabel4);
        infoArea = new JTextArea();
        infoArea.setBackground(color);
        infoArea.setBounds(25, 170, 150, 20);
        this.getContentPane().add(infoArea);
        infoButton = new JButton("查询学生信息");
        infoButton.setBounds(25, 200, 150, 35);
        infoButton.addActionListener(this);
        this.getContentPane().add(infoButton);

        quitButton = new JButton("退出登录");
        quitButton.setBounds(25, 300, 150, 35);
        quitButton.addActionListener(this);
        this.getContentPane().add(quitButton);


        ImageIcon imageIcon1 = new ImageIcon("image\\e.jpg");
        JLabel jLabeltmp2 = new JLabel(new ImageIcon(imageIcon1.getImage().getScaledInstance(178, 50, Image.SCALE_DEFAULT)));
        jLabeltmp2.setBounds(15,52,178,50);
        this.getContentPane().add(jLabeltmp2);

        ImageIcon imageIcon = new ImageIcon("image\\d.jpg");
        JLabel jLabeltmp1 = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(202, 780, Image.SCALE_DEFAULT)));
        jLabeltmp1.setBounds(0,0,202,780);
        this.getContentPane().add(jLabeltmp1);
    }
    private DefaultTableModel stuTableModel(DefaultTableModel dtm){
        dtm.getDataVector().removeAllElements();
        String[] stu ={"学号","姓名","性别","专业","平均绩点"};
        dtm.setColumnIdentifiers(stu);
        Vector v = null;
        try {
            sql = "select * from Student";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                v = new Vector();
                String sno = rs.getString("ID");
                String name = rs.getString("Name");
                String sex = rs.getString("Sex");
                String major = rs.getString("Major");
                String grame = rs.getString("Grame");
                v.add(sno);
                v.add(name);
                v.add(sex);
                v.add(major);
                v.add(grame);
                dtm.addRow(v);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dtm.fireTableDataChanged();
        return dtm;
    }
    boolean flag = false;
    String stmp;

    public int sort(String ID) throws SQLException {
        count=0;
        sql="SELECT * FROM Student";
        boolean f=false;
        ResultSet resultSet = st.executeQuery(sql);
        while (resultSet.next()) {
            count++;
            if (ID.equals(resultSet.getString("ID"))){
                f=true;
                break;
            }
        }
        if (f)
            return count;
        else
            return -1;
    }
    @Override
    public void actionPerformed(ActionEvent ee) {
        Object source=ee.getSource();
        try {
            if (source==broButton&&flag==false){
                flag=true;
                dtm=stuTableModel(dtm);
            } else if (source==infoButton&&flag==true) {
                stmp=infoArea.getText();
                int tmp=sort(stmp);
                if (tmp!=-1){
                    table.setRowSelectionInterval(tmp-1,tmp-1);
                    table.setSelectionBackground(Color.RED);
                }else{
                    dtm=stuTableModel(dtm);
                }
            }else if (source==quitButton) {
                new MainFrame();
                this.dispose();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        infoArea.setText("");
    }
}
