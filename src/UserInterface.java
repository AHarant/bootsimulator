import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UserInterface extends JFrame {

    private StreamTime streamTime;

    JPanel koordinatenSystem;

    UserInterface(Vector target,double waterVelocity){

        streamTime = new StreamTime(0);

        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);


        JButton play = new JButton("Play");
        menubar.add(play);
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserInterface.this.streamTime.setStreamTime(UserInterface.this.streamTime.getStreamTime()+1);
                UserInterface.this.koordinatenSystem.repaint();
                UserInterface.this.repaint();
            }
        });

        JButton reset = new JButton("Reset");
        menubar.add(reset);
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserInterface.this.streamTime.setStreamTime(0);
                UserInterface.this.koordinatenSystem.repaint();
                UserInterface.this.repaint();
            }
        });

        koordinatenSystem = new CoordinateSystem(target, waterVelocity,streamTime);

        add(koordinatenSystem);

        setTitle("Darstellung");
        setSize(650,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

}
