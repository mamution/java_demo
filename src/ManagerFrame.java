import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;

public class ManagerFrame extends JFrame implements ActionListener {
    Color color;
    public ManagerFrame(){
        try {
            connectDB_tmp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setSize(800, 800);
        this.setLayout(null);
        this.setTitle("管理员界面 v1.0");
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
    JTextArea delArea;
    JButton delButton;
    JTextArea changeArea;
    JButton changeButton;
    JTextArea infoArea;
    JButton infoButton;
    JTextArea inputArea;
    JButton inputButton;
    JButton quitButton;
    private void initFrame() {
        dtm = new DefaultTableModel();
        table = new JTable(dtm);
        JScrollPane jsp=new JScrollPane(table);
        jsp.setBounds(200, 30, 590, 740);
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

        JLabel jLabel2 = new JLabel("1.删除信息学号:");
        jLabel2.setBounds(25, 140, 90, 18);
        this.getContentPane().add(jLabel2);
        delArea = new JTextArea();
        delArea.setBackground(color);
        delArea.setBounds(25, 170, 150, 20);
        this.getContentPane().add(delArea);
        delButton = new JButton("删除学生信息");
        delButton.setBounds(25, 200, 150, 35);
        delButton.addActionListener(this);
        this.getContentPane().add(delButton);

        JLabel jLabel3 = new JLabel("2.修改信息学号:");
        jLabel3.setBounds(25, 260, 90, 18);
        this.getContentPane().add(jLabel3);
        changeArea = new JTextArea();
        changeArea.setBackground(color);
        changeArea.setBounds(25, 290, 150, 20);
        this.getContentPane().add(changeArea);
        changeButton = new JButton("修改学生信息");
        changeButton.setBounds(25, 320, 150, 35);
        changeButton.addActionListener(this);
        this.getContentPane().add(changeButton);

        JLabel jLabel4 = new JLabel("3.查询信息学号:");
        jLabel4.setBounds(25, 380, 90, 18);
        this.getContentPane().add(jLabel4);
        infoArea = new JTextArea();
        infoArea.setBackground(color);
        infoArea.setBounds(25, 410, 150, 20);
        this.getContentPane().add(infoArea);
        infoButton = new JButton("查询学生信息");
        infoButton.setBounds(25, 440, 150, 35);
        infoButton.addActionListener(this);
        this.getContentPane().add(infoButton);

        JLabel jLabel5 = new JLabel("4.添加信息学号:");
        jLabel5.setBounds(25, 500, 90, 18);
        this.getContentPane().add(jLabel5);
        inputArea = new JTextArea();
        inputArea.setBackground(color);
        inputArea.setBounds(25, 530, 150, 20);
        this.getContentPane().add(inputArea);
        inputButton = new JButton("添加学生信息");
        inputButton.setBounds(25, 560, 150, 35);
        inputButton.addActionListener(this);
        this.getContentPane().add(inputButton);

        quitButton = new JButton("退出登录");
        quitButton.setBounds(25, 650, 150, 35);
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
    JButton jButton;
    boolean flag = false;
    JTextField jTextField_1;
    JTextField jTextField_2;
    JTextField jTextField_3;
    JTextField jTextField_4;
    JTextField jTextField_5;
    String stmp;
    JDialog jDialog;

    JButton jButton_tmp;
    public void initJFrameTmp(int flag){
        jDialog = new JDialog(this);

        //创建一个管理图片的容器
        jDialog.setSize(600,200);
        jDialog.setLayout(null);
        jDialog.setAlwaysOnTop(true);//置顶
        jDialog.setLocationRelativeTo(null);//居中
        jDialog.setModal(true);//弹框不关闭就无法操作下面的界面
        JLabel jLabel_1 = new JLabel("学号");
        jLabel_1.setBounds(32,30,75,35);
        jDialog.getContentPane().add(jLabel_1);
        JLabel jLabel_2 = new JLabel("姓名");
        jLabel_2.setBounds(139,30,75,35);
        jDialog.getContentPane().add(jLabel_2);
        JLabel jLabel_3 = new JLabel("性别");
        jLabel_3.setBounds(246,30,75,35);
        jDialog.getContentPane().add(jLabel_3);
        JLabel jLabel_4 = new JLabel("专业");
        jLabel_4.setBounds(353,30,75,35);
        jDialog.getContentPane().add(jLabel_4);
        JLabel jLabel_5 = new JLabel("绩点");
        jLabel_5.setBounds(460,30,75,35);
        jDialog.getContentPane().add(jLabel_5);
        jTextField_1 = new JTextField(stmp);
        jTextField_1.setEditable(false);
        jTextField_2 = new JTextField();
        jTextField_3 = new JTextField();
        jTextField_4 = new JTextField();
        jTextField_5 = new JTextField();
        jTextField_1.setBounds(32,60,100,20);
        jTextField_2.setBounds(139,60,100,20);
        jTextField_3.setBounds(246,60,100,20);
        jTextField_4.setBounds(353,60,100,20);
        jTextField_5.setBounds(460,60,100,20);
        jDialog.getContentPane().add(jTextField_1);
        jDialog.getContentPane().add(jTextField_2);
        jDialog.getContentPane().add(jTextField_3);
        jDialog.getContentPane().add(jTextField_4);
        jDialog.getContentPane().add(jTextField_5);
        CardLayout cardLayout = new CardLayout();
        JPanel jPanel=new JPanel(cardLayout);
        jButton = new JButton("确定修改");
        jButton.setBounds(240,120,120,35);
        jPanel.add(jButton,"修改");
        jButton.addActionListener(this);
        jButton_tmp = new JButton("确定添加");
        jButton_tmp.setBounds(240,120,120,35);
        jPanel.add(jButton_tmp,"添加");
        jButton_tmp.addActionListener(this);
        jPanel.setBounds(240,120,120,35);
        if (flag==1)
            cardLayout.show(jPanel,"修改");
        else if (flag==2)
            cardLayout.show(jPanel,"添加");
        jDialog.getContentPane().add(jPanel);
        jDialog.setVisible(true);
    }
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

    public void reFinish(){
        inputArea.setText("");
        infoArea.setText("");
        delArea.setText("");
        changeArea.setText("");
    }
    @Override
    public void actionPerformed(ActionEvent ee) {
        Object source=ee.getSource();
        try {
            if (source==broButton&&flag==false){
                flag=true;
                dtm=stuTableModel(dtm);
            } else if (source==delButton&&flag==true) {
                stmp=delArea.getText();
                if (sort(stmp)!=-1){
                    sql = "delete from Student where ID ='" + stmp + "'";
                    st.executeUpdate(sql);
                    dtm=stuTableModel(dtm);
                }
            }else if (source==changeButton&&flag==true) {
                stmp=changeArea.getText();
                if (sort(stmp)!=-1){
                    initJFrameTmp(1);
                }
            }else if (source==infoButton&&flag==true) {
                stmp=infoArea.getText();
                int tmp=sort(stmp);
                if (tmp!=-1){
                    table.setRowSelectionInterval(tmp-1,tmp-1);
                    table.setSelectionBackground(Color.RED);
                }else{
                    dtm=stuTableModel(dtm);
                }
            }else if (source==inputButton&&flag==true) {
                stmp=inputArea.getText();
                if (sort(stmp)==-1){
                    initJFrameTmp(2);
                }
            }else if (source==quitButton) {
                new MainFrame();
                this.dispose();
            }else if (source==jButton&&flag==true){
                sql = "update Student set Name='"+jTextField_2.getText()+"',Sex='"+
                        jTextField_3.getText()+"',Major='"+jTextField_4.getText()+"',Grame='"+jTextField_5.getText()+
                        "' where ID='"+stmp+"'";
                st.executeUpdate(sql);
                jDialog.dispose();
                dtm=stuTableModel(dtm);
            }else if (source==jButton_tmp&&flag==true){
                sql = "insert into Student values('"+jTextField_1.getText()+"','"+jTextField_2.getText() +
                        "','"+jTextField_3.getText()+"','"+jTextField_4.getText()+"','"+jTextField_5.getText()+"')";
                st.executeUpdate(sql);
                jDialog.dispose();
                dtm=stuTableModel(dtm);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        reFinish();
    }
}
