package com.lyne.image;

import javax.swing.*;

/**
 * Created by lyne on 2017/4/1.
 */
public class GroupLayoutExample {
    public static void main(String[] args) {
        // Create and set up a frame window
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define new buttons with different width on help of the ---
        JButton jb1 = new JButton("1");
        JButton jb2 = new JButton("2");
        JButton jb3 = new JButton("3");
        JButton jb4 = new JButton("4");

        // Define the panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setSize(300, 300);
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        panel.setLayout(layout);

        // Set for horizontal and vertical group
        layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(jb1).addComponent(jb2)
                .addGroup(layout.createSequentialGroup().addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jb3).addComponent(jb4))));
        layout.setVerticalGroup(
                layout.createSequentialGroup().addComponent(jb1).addComponent(jb2).addComponent(jb3).addComponent(jb4));
        // Set the window to be visible as the default to be false
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }
}
