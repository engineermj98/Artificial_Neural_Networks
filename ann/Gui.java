
package ann;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui extends JFrame{
    private ANN myAgent;
    
    public JTextField txtx1,txtx2,txtx3;
    public double x1,x2,x3;
    Gui(ANN a){
        super(a.getLocalName());
        myAgent = a;
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4,4));
        p.add(new JLabel("x1"));
        txtx1 = new JTextField();
        p.add(txtx1);
        p.add(new JLabel("x2"));
        txtx2 = new JTextField();
        p.add(txtx2);
        p.add(new JLabel("x3"));
        txtx3 = new JTextField();
        p.add(txtx3);
        getContentPane().add(p,BorderLayout.CENTER);
        
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               try{
                   ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                   msg.addReceiver(new AID("ann1",AID.ISLOCALNAME));
                   msg.setContent(txtx1.getText());
                   myAgent.x1=Double.parseDouble(msg.getContent());
                   myAgent.send(msg);
                   msg.setContent(txtx2.getText());
                   myAgent.x2=Double.parseDouble(msg.getContent());
                   myAgent.send(msg);
                   msg.setContent(txtx3.getText());
                   myAgent.x3=Double.parseDouble(msg.getContent());
                   myAgent.send(msg);
                   txtx1.setText("");
                   txtx2.setText("");
                   txtx3.setText("");
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(Gui.this, "Invalid values. "+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
               }
            }
        });
        p = new JPanel();
        p.add(submit);
        getContentPane().add(p,BorderLayout.SOUTH);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                myAgent.doDelete();
            }
        });
        setResizable(false);
    }
    public void showGui(){
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth()/2;
        int centerY = (int)screenSize.getHeight()/2;
        setLocation(centerX-getWidth()/2,centerY-getHeight()/2);
        super.setVisible(true);
    }
}
