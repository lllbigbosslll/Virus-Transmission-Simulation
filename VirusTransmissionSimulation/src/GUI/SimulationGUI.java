package GUI;

import Simulation.State;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SimulationGUI extends JFrame {
    private JPanel rootPanel;
    private JLabel message;
    private JButton buttonStart;
    private JCheckBox hasTestingPolicyCheckBox1;
    private JCheckBox hasQuarantinePolicyCheckBox1;
    private JCheckBox hasTestingPolicyCheckBox2;
    private JCheckBox hasQuarantinePolicyCheckBox2;
    private JCheckBox hasMaskingPolicyCheckBox1;
    private JCheckBox hasMaskingPolicyCheckBox2;


    public SimulationGUI() {
        message.setFont(new Font("Sitka Banner", Font.BOLD, 16));
        buttonStart.setFont(new Font("Sitka Banner", Font.BOLD, 16));

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, "The Simulation takes time, \nPlease wait for the simulation to complete.", "Attention",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

                State.testSimulation(hasMaskingPolicyCheckBox1.isSelected(), hasTestingPolicyCheckBox1.isSelected(), hasQuarantinePolicyCheckBox1.isSelected(),
                        hasMaskingPolicyCheckBox2.isSelected(), hasTestingPolicyCheckBox2.isSelected(), hasQuarantinePolicyCheckBox2.isSelected());
                System.out.println("Complete simulation");

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SimulationGUI");
        frame.setContentPane(new SimulationGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.pack();
        frame.setVisible(true);
    }

}
