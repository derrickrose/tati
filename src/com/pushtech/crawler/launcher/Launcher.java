package com.pushtech.crawler.launcher;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Launcher extends JFrame {

    public Launcher(String strTitle) {
        this.setTitle(strTitle);
        this.setSize(400, 250);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel title = new JLabel("Fenêtre d'administration");
        this.setLayout(new GridLayout(6, 2, 10, 10));

        // On ajoute le bouton au content pane de la JFrame
        final JTextField urlField = new JTextField(10);
        this.getContentPane().add(new JLabel("Url to crawl"));

        this.getContentPane().add(urlField);
        this.setUndecorated(true);

        this.getContentPane().add(new JLabel("Database link"));

        this.getContentPane().add(new JTextField(10));
        this.getContentPane().add(new JLabel("Database user"));

        this.getContentPane().add(new JTextField(10));
        this.getContentPane().add(new JLabel("Database pass"));

        this.getContentPane().add(new JTextField(10));
        JButton launch = new JButton("Launch");
        this.getContentPane().add(launch);
        this.getContentPane().add(new JButton("Cancel"));
        this.getContentPane().setBackground(Color.orange);
        this.getRootPane().setBorder(BorderFactory.createTitledBorder("Aspiration Alco distribution"));

        launch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String url=urlField.getText();
                new TreatCrawl(url);
            }
        });

        this.setVisible(true);

    }

//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (UnsupportedLookAndFeelException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        new Launcher("Alco Distribution");
//    }

}

