import javax.swing.*;
import java.lang.String;
import java.sql.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.SQLException;
class Login extends JFrame implements ActionListener 
{
        private JTextField tusr;
        private JPasswordField tpwd;
        private JRadioButton adm;
        private JRadioButton emp;
        public static String userID,password,accType;
        JLabel lbtl=new JLabel("Login");
        JLabel idt1=new JLabel("User ID");
        JLabel idt2=new JLabel("Password");
        JLabel idt3=new JLabel("User Type");
        
        public void newlogin()
        {
            tusr=new JTextField();
            tpwd=new JPasswordField();
            setLayout(null);
            tusr.setBounds(500,190,180,25);
            tpwd.setBounds(500,240,180,25);
            idt1.setBounds(300,100,200,200);
            lbtl.setBounds(100,100,200,200);
            lbtl.setFont(new Font("Serif", Font.PLAIN, 54));
            lbtl.setForeground(Color.white);
            idt1.setFont(new Font("Serif", Font.PLAIN, 34));
            idt1.setForeground(Color.white);
            idt2.setBounds(300,150,200,200);
            idt2.setFont(new Font("Serif", Font.PLAIN, 34));
            idt2.setForeground(Color.white);
            idt3.setBounds(300,200,200,200);
            idt3.setFont(new Font("Serif", Font.PLAIN, 34));
            idt3.setForeground(Color.white);
            adm=new JRadioButton("Admin");
            adm.setActionCommand("Admin");
            emp=new JRadioButton("Employee");
            emp.setActionCommand("Employee");
            ButtonGroup group=new ButtonGroup();
            group.add(adm);
            group.add(emp);
            adm.setBounds(500,290,70,30);
            adm.setForeground(Color.black);
            emp.setBounds(570,290,100,30);
            emp.setForeground(Color.black);
            getContentPane().setBackground(Color.black);
            add(lbtl);
            add(tusr);
            add(tpwd);
            add(idt1);
            add(idt2);
            add(idt3);
            emp.addActionListener(this);
            adm.addActionListener(this);
            add(adm);
            add(emp);
            setSize(1024,600);
            setTitle("Login");
            setResizable(false);
            Toolkit t1=getToolkit();
            Dimension size=t1.getScreenSize();
            setLocation(size.width/2-getWidth()/2,size.height/2-getHeight()/2);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        
          }  
          
         public void actionPerformed(ActionEvent evt)
         {
            userID=tusr.getText();
            password=tpwd.getText();
            accType=evt.getActionCommand();
            Funcs f=new Funcs();
            f.validate(userID,password,accType);
  
        }
}


class Funcs
{
      private String pass,type1;
      private String proName,proPhone,proModel,proColor,proAdd,proStatus;
      private String getName,getID,getStatus,getPhone,getModel,getColor;
      public static String type;
      public static String usrid;
      public static int logStatus=0;
      
      public void validate(String usrid,String pass,String type1)
       {
              
              if((usrid.equals(""))||(pass.equals(""))||(type1.equals("")))
              {
                    JOptionPane.showMessageDialog(null,"Fill in Login Details Properly.");
              }
              else
              {
                type=type1.substring(0,1).toUpperCase()+type1.substring(1);
                JOptionPane.showMessageDialog(null,"Checking Details...");
                try
                {
                  Database db=new Database();
                  db.dbconn();
                  db.chkLogin(usrid,pass,type);
                }
                catch(SQLException e)
                {
                
                }
                
              }
       }
       
       public void validateaddp(String np1,String np2,String np3,String np4,String np5,String np6)
       {
              String p1,p2,p3,p4,p5,p6;
              if((np1.equals(""))||(np2.equals(""))||(np3.equals(""))||(np4.equals(""))||(np5.equals(""))||(np6.equals("")))
              {
                    JOptionPane.showMessageDialog(null,"Fill in Prospect Details Properly.");
                    
              }
              else
              {
                p1=np1.substring(0,1).toUpperCase()+np1.substring(1);
                p2=np2.substring(0,1).toUpperCase()+np2.substring(1);
                p3=np3.substring(0,1).toUpperCase()+np3.substring(1);
                p4=np4.substring(0,1).toUpperCase()+np4.substring(1);
                p5=np5.substring(0,1).toUpperCase()+np5.substring(1);
                p6=np6.substring(0,1).toUpperCase()+np6.substring(1);
                JOptionPane.showMessageDialog(null,"Submitting Details...");
                try
                {
                  Database db=new Database();
                  db.addProspect(p1,p2,p3,p4,p5,p6);
                  
                }
                catch(SQLException e)
                {
                
                }
                
              }
       }
}

class Database extends JFrame
{
      public static Connection con;
       public static int logStatus=0;
        public static String uid;
        public static JTextArea textArea;

      public void dbconn() throws SQLException
      {
         try
         {
              Class.forName("com.mysql.jdbc.Driver");
              con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","password");
              
         }
         catch(Exception e) 
         {
              System.err.println("Exception: "+e.getMessage());
         }
      }
       
       public void chkLogin(String usrid,String pass,String type)throws SQLException
       {
                    Statement st=con.createStatement();
                    ResultSet rs=st.executeQuery("SELECT * from login");
                    while(rs.next())
                    {
                        if(rs.getString("loginid").equals(usrid) && rs.getString("passwd").equals(pass) && rs.getString("role").equals(type))
                        {
                              logStatus=1;
                              break;
                        }
                        
                    }
                    if(logStatus==1)
                    {
                           JOptionPane.showMessageDialog(null,"Login Authentication Successfull, Welcome..");
                           uid=new String(usrid);
                           EmployeeAcc();
                    }
                    else
                    {
                           JOptionPane.showMessageDialog(null,"User not found!");
                    }
                    
       }
       
       public void addProspect(String proName,String proAdd,String proPhone,String proModel,String proColor,String proStatus)throws SQLException
       {
                  
                  String query="insert into prospect(empid,pname,padd,model,color,status,phone) values (?,?,?,?,?,?,?)";
                  PreparedStatement ps=con.prepareStatement(query);
                  ps.setString(1,uid);
                  ps.setString(2,proName);
                  ps.setString(3,proAdd);
                  ps.setString(4,proModel);
                  ps.setString(5,proColor);
                  ps.setString(6,proStatus);
                  ps.setString(7,proPhone);
                  ps.execute();
                  
       }
       
       public void viewAllPros()throws SQLException
       {

                Statement st=con.createStatement();
                ResultSet rs=st.executeQuery("SELECT * from prospect");
                String res="\t Searched Data for All Prospects \n";
                while(rs.next())
                {
                      
                      res+=" Name: "+rs.getString("pname")+" ID: "+rs.getString("id")+"\n Phone: "+rs.getString("phone")+"\n Address: "+rs.getString("padd")+"\n Model: "+rs.getString("model")+", Color: "+rs.getString("color")+"\n\n";
                      
                }
                textArea.append(res);
              
       }
       
       public void viewByName()throws SQLException
       {
              int i=0;
              String getName=JOptionPane.showInputDialog(null,"Enter Prospect Name");
              if(getName.equals(""))
              JOptionPane.showMessageDialog(null,"Improper Details...");
              else
              {
                String query="select id,padd,model,color,phone from prospect where pname=?";
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1,getName); 
                ResultSet rs=ps.executeQuery();
                String bn="\t Searched Data for Prospect by Name \n";
                while(rs.next())
                {     i++;
                      bn+=" Name: "+getName+" ID: "+rs.getString("id")+"\n Phone: "+rs.getString("phone")+"\n Address: "+rs.getString("padd")+"\n Model: "+rs.getString("model")+", Color: "+rs.getString("color")+"\n\n";
                }
                if(i>0)
                textArea.append(bn);
                else
                JOptionPane.showMessageDialog(null,"No data Found..");
              }
       }
       
       public void viewById()throws SQLException
       {
              int i=0;
              String getID=JOptionPane.showInputDialog(null,"Enter Prospect ID");
              if(getID.equals(""))
              JOptionPane.showMessageDialog(null,"Improper Details...");
              else
              {
                String query="select pname,padd,model,color,phone from prospect where id=?";
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1,getID); 
                ResultSet rs=ps.executeQuery();
                String bi="\t Searched Data for Prospect by ID \n";
                while(rs.next())
                {
                      i++;
                      bi+=" Name: "+rs.getString("pname")+" ID: "+getID+"\n Phone: "+rs.getString("phone")+"\n Address: "+rs.getString("padd")+"\n Model: "+rs.getString("model")+", Color: "+rs.getString("color")+"\n\n";
                }
                if(i>0)
                textArea.append(bi);
                else
                JOptionPane.showMessageDialog(null,"No data Found..");
               }
        }
        
        public void viewByStatus()throws SQLException
       {
              int i=0;
              String getStatus=JOptionPane.showInputDialog(null,"Enter Status of Prospect");
              if(getStatus.equals(""))
              JOptionPane.showMessageDialog(null,"Improper Details...");
              else
              {
                String query="select id,pname,padd,model,color,phone from prospect where status=?";
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1,getStatus); 
                ResultSet rs=ps.executeQuery();
                String bs="\t Searched Data for Prospect by Status \n";
                while(rs.next())
                {     
                      i++;
                      bs+=" Name: "+rs.getString("pname")+" ID: "+rs.getString("id")+"\n Phone: "+rs.getString("phone")+"\n Address: "+rs.getString("padd")+"\n Model: "+rs.getString("model")+", Color: "+rs.getString("color")+"\n\n";
                }
                if(i>0)
                textArea.append(bs);
                else
                JOptionPane.showMessageDialog(null,"No data Found..");
               }
       }
       
       public void updatePhone()throws SQLException
       {
              boolean r=false;
              String getID=JOptionPane.showInputDialog(null,"Enter ID of Prospect to update Phone number");
              String getPhone=JOptionPane.showInputDialog(null,"Enter Phone number");
              if(getID.equals("")||getPhone.equals(""))
              JOptionPane.showMessageDialog(null,"Improper Details...");
              else
              {
                String query="update prospect set phone=? where id=?";
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1,getPhone);
                ps.setString(2,getID); 
                ps.execute();
                r=true;
                if(r==true)
                JOptionPane.showMessageDialog(null,"Update Successfull");
                else
                JOptionPane.showMessageDialog(null,"Updated UNsuccessfull");
              }
       }
       
       public void updateMC()throws SQLException
       {
              boolean r=false;
              String getID=JOptionPane.showInputDialog(null,"Enter ID of Prospect to update Model And Color");
              String getModel=JOptionPane.showInputDialog(null,"Enter Model");
              String getColor=JOptionPane.showInputDialog(null,"Enter Color");
              if(getID.equals("")||getModel.equals("")||getColor.equals(""))
              JOptionPane.showMessageDialog(null,"Improper Details...");
              else
              {
                String query="update prospect set model=?,color=? where id=?";
                PreparedStatement ps=con.prepareStatement(query);
                ps.setString(1,getModel);
                ps.setString(2,getColor); 
                ps.setString(3,getID); 
                ps.execute();
                r=true;
                if(r==true)
                JOptionPane.showMessageDialog(null,"Update Successfull");
                else
                JOptionPane.showMessageDialog(null,"Update UNsuccessfull");
              }
       }
       
       public void changePassword()throws SQLException
       {
              boolean r=false;
              String query="SELECT passwd from login where loginid=?";
              PreparedStatement ps=con.prepareStatement(query);
              ps.setString(1,uid); 
              ResultSet rs=ps.executeQuery();
              String oldPass,newPass,cnewPass;
              oldPass=JOptionPane.showInputDialog(null,"Enter old Password");
              newPass=JOptionPane.showInputDialog(null,"Enter new Password");
              cnewPass=JOptionPane.showInputDialog(null,"Confirm new Password");
              if((oldPass.equals("")||newPass.equals("")||cnewPass.equals(""))||!newPass.equals(cnewPass))
              JOptionPane.showMessageDialog(null,"Improper Details");
              else
              {
                String op=null;
                while(rs.next())
                {
                op=rs.getString("passwd");
                }
                if(oldPass.equals(op))
                {
                    String query1="update login set passwd=? where loginid=?";
                    PreparedStatement ps1=con.prepareStatement(query1);
                    ps1.setString(1,newPass);
                    ps1.setString(2,uid);
                    ps1.execute(); 
                    r=true;   
                    if(r==true)
                    JOptionPane.showMessageDialog(null,"Password changed Successfully");
                    else                
                    JOptionPane.showMessageDialog(null,"Password change Unsuccessfull");
                
                }
 
              }
       }
       
        public void EmployeeAcc() 
        {
            setTitle("Employee Account");
            textArea=new JTextArea();
            JMenuBar menubar = new JMenuBar();
            textArea.setBounds(500,190,180,25);
            textArea.setEditable(false);
            JScrollPane scroll = new JScrollPane(textArea);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            JMenu ap=new JMenu("Add Prospect");
            JMenuItem ap1=new JMenuItem("Add new Prospect");
            ap.add(ap1);
            JMenu view=new JMenu("View");
            JMenuItem All=new JMenuItem("All Prospect");
            JMenuItem byName=new JMenuItem("Prospect By Name");
            JMenuItem byId=new JMenuItem("Prospect By ID");
            JMenuItem bySta=new JMenuItem("Prospect By Status");
            view.add(All);
            view.add(byName);
            view.add(byId);
            view.add(bySta);
            JMenu updt=new JMenu("Update");
            JMenuItem phone=new JMenuItem("Update Prospect Phone No.");
            JMenuItem Mc=new JMenuItem("Update Prospect Model and Color");
            updt.add(phone);
            updt.add(Mc);
            JMenu cp=new JMenu("Change Password");
            JMenuItem cp1=new JMenuItem("Change my Passsword");
            cp.add(cp1);
            JMenu lout=new JMenu("Logout");
            JMenuItem lout1=new JMenuItem("Logout From this Account");
            lout.add(lout1);
            add(scroll);
            ap1.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent event) 
                {
                      AddP a=new AddP();
                }
                
            });
            
            All.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent event) 
                {
                      try
                      {
                      viewAllPros();
                      }
                      catch(SQLException e)
                      {
                      }
                }
                
            });
            
            byName.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent event) 
                {
                      try
                      {
                      viewByName();
                      }                                                     
                      catch(SQLException e)
                      {
                      }
                }
                
            });
            
            byId.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent event) 
                {
                      try
                      {
                      viewById();
                      }                                                     
                      catch(SQLException e)
                      {
                      }
                }
                
            });
            
             bySta.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent event) 
                {
                      try
                      {
                      viewByStatus();
                      }                                                     
                      catch(SQLException e)
                      {
                      }
                }
                
            });
            
            phone.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent event) 
                {
                      try
                      {
                      updatePhone();
                      }                                                     
                      catch(SQLException e)
                      {
                      }
                }
                
            });
            
            Mc.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent event) 
                {
                      try
                      {
                      updateMC();
                      }                                                     
                      catch(SQLException e)
                      {
                      }
                }
                
            });
            
            cp1.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent event) 
                {
                      try
                      {
                      changePassword();
                      }                                                     
                      catch(SQLException e)
                      {
                      }
                }
                
            });
           
            lout1.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent event) 
                {
                      setVisible(false);
                      System.exit(0);
                }
                
            });
            
            menubar.add(ap);
            menubar.add(view);
            menubar.add(updt);
            menubar.add(cp);
            menubar.add(lout);
            setJMenuBar(menubar);
            setSize(1024,600);
            Toolkit t1=getToolkit();
            Dimension size=t1.getScreenSize();
            setLocation(size.width/2-getWidth()/2,size.height/2-getHeight()/2);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setVisible(true);
        }
        
}
class AddP extends JFrame
{
        private JTextField pname;
        private JTextField padd;
        private JTextField pphone;
        private JTextField pmodel;
        private JTextField pcolor;
        private JTextField pstatus;
        public static String npname,npadd,npphone,npmodel,npcolor,npstatus;
        JLabel lbtl=new JLabel("Add new Prospect");
        JLabel idt1=new JLabel("Name");
        JLabel idt2=new JLabel("Address");
        JLabel idt3=new JLabel("Phone no.");
        JLabel idt4=new JLabel("Model");
        JLabel idt5=new JLabel("Color");
        JLabel idt6=new JLabel("Status");
        
        public AddP()
        {
            
            pname=new JTextField();
            padd=new JTextField();
            pphone=new JTextField();
            pmodel=new JTextField();
            pcolor=new JTextField();
            pstatus=new JTextField();
            JButton submit= new JButton("Submit");
            setLayout(null);
            pname.setBounds(500,190,180,20);
            padd.setBounds(500,220,180,20);
            pphone.setBounds(500,250,180,20);
            pmodel.setBounds(500,280,180,20);
            pcolor.setBounds(500,310,180,20);
            pstatus.setBounds(500,340,180,20);
            lbtl.setBounds(70,100,300,200);
            lbtl.setFont(new Font("Serif", Font.PLAIN, 34));
            lbtl.setForeground(Color.white);
            idt1.setBounds(400,100,100,200);
            idt1.setFont(new Font("Serif", Font.PLAIN, 14));
            idt1.setForeground(Color.white);
            idt2.setBounds(400,130,200,200);
            idt2.setFont(new Font("Serif", Font.PLAIN, 14));
            idt2.setForeground(Color.white);
            idt3.setBounds(400,160,200,200);
            idt3.setFont(new Font("Serif", Font.PLAIN, 14));
            idt3.setForeground(Color.white);
            idt4.setBounds(400,190,200,200);
            idt4.setFont(new Font("Serif", Font.PLAIN, 14));
            idt4.setForeground(Color.white);
            idt5.setBounds(400,220,200,200);
            idt5.setFont(new Font("Serif", Font.PLAIN, 14));
            idt5.setForeground(Color.white);
            idt6.setBounds(400,250,200,200);
            idt6.setFont(new Font("Serif", Font.PLAIN, 14));
            idt6.setForeground(Color.white);
            submit.setBounds(500,380,180,20);
            getContentPane().setBackground(Color.black);
            add(lbtl);
            add(idt1);
            add(idt2);
            add(idt3);
            add(idt4);
            add(idt5);
            add(idt6);
            add(pname);
            add(padd);
            add(pphone);
            add(pmodel);
            add(pcolor);
            add(pstatus);
            add(submit);
            setSize(1000,600);
            setTitle("Add new Prospect");
            setResizable(false);
            Toolkit t1=getToolkit();
            Dimension size=t1.getScreenSize();
            setLocation(size.width/2-getWidth()/2,size.height/2-getHeight()/2);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            submit.addActionListener(new ActionListener() 
            {
              public void actionPerformed(ActionEvent event) 
              {
                   npname=pname.getText();
                   npadd=padd.getText();
                   npphone=pphone.getText();
                   npmodel=pmodel.getText();
                   npcolor=pcolor.getText();
                   npstatus=pstatus.getText();
                   Funcs f1=new Funcs();
                   f1.validateaddp(npname,npadd,npphone,npmodel,npcolor,npstatus);
                   setVisible(false);
              }
            });
        }
}
       
class Callmain
{
        public static void main(String...args)throws SQLException
        {
                  Login l=new Login();
                  l.newlogin();
         }
}
          
        
            
      