package com.pushtech.crawler.ihm;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

public class FirstVersionIHM extends JFrame {

   private static Logger logger = Logger.getLogger(FirstVersionIHM.class);

   public FirstVersionIHM(String strTitle) {
      JTabbedPane jTabbedPane = new JTabbedPane();
      JPanel jpnl = new JPanel();
      JPanel config = new JPanel();
      this.setTitle(strTitle);
      this.setSize(700, 240);
      this.setLocationRelativeTo(null);
      this.setResizable(false);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JLabel title = new JLabel(" ");
      jpnl.setLayout(new BorderLayout());
      jpnl.add(title, BorderLayout.NORTH);
      jpnl.add(new JLabel("               "), BorderLayout.WEST);
      jpnl.add(new JLabel("               "), BorderLayout.EAST);
      jpnl.add(new DefaultPanel(), BorderLayout.CENTER);
      jTabbedPane.addTab("Crawl entry points", jpnl);
      jTabbedPane.addTab("Data base config", new JPanel());
      this.add(jTabbedPane);
      this.setVisible(true);
   }

   public static void main(String[] args) {
      System.setProperty("jsse.enableSNIExtension", "true");
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (ClassNotFoundException e) {
         // TODO Auto-generated catch block
         logger.error(e.getMessage());
      } catch (InstantiationException e) {
         // TODO Auto-generated catch block
         logger.error(e.getMessage());
      } catch (IllegalAccessException e) {
         // TODO Auto-generated catch block
         logger.error(e.getMessage());
      } catch (UnsupportedLookAndFeelException e) {
         // TODO Auto-generated catch block
         logger.error(e.getMessage());
      }

      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            FirstVersionIHM fvihm = new FirstVersionIHM("Tati crawler");
            fvihm.setOpacity((float) 0.0);
         }
      });

   }

}