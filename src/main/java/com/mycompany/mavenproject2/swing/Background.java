/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.swing;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

/**
 *
 * @author wahin
 */
public class Background {
    
    public static void startThread(String carnum,String user,UserWindow userWindow)
    {
        SwingWorker sw;
        sw = new SwingWorker()
        {
            @Override
            protected String doInBackground() throws Exception
            {
                Thread.sleep(20000);
                Database db = new Database("jdbc:mysql://localhost:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=Asia/Kolkata");
                db.changeStatus(carnum,0);
                db.changeStatusStudent(user,0);
                userWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                userWindow.bill.setVisible(false);
                if(!userWindow.isShowing())
                {  
                    userWindow.dispose();
                    System.out.println("here");
                }
                System.out.println("done");
                return "done";
            }
        };
        sw.execute();
    }
}
