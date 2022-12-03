//import UI
import account_creation.Account;
import ui.LoginUI;
import data.persistency.UserDatabase;

//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;

public class StudyBuddyApp {
    static UserDatabase userDatabase;
    public static void main(String[] args){
//        //Serializes an empty userDatabase.
//        try {
//            FileOutputStream myFileOutStream
//                    = new FileOutputStream(
//                    "/Users/tankenji/IdeaProjects/course-project-zuck-s-children/userDatabase.txt");
//
//            ObjectOutputStream myObjectOutStream
//                    = new ObjectOutputStream(myFileOutStream);
//
//            myObjectOutStream.writeObject(new UserDatabase());
//
//            // closing FileOutputStream and
//            // ObjectOutputStream
//            myObjectOutStream.close();
//            myFileOutStream.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }

        HashMap<String, Account> userDatabase = null;
        // deserializing the userDatabase.txt file

        try {
            FileInputStream fin = new FileInputStream("/Users/tankenji/IdeaProjects/course-project-zuck-s-children/userDatabase.txt");
            //Creating stream to read the object
            ObjectInputStream in = new ObjectInputStream(fin);
            userDatabase = (HashMap<String, Account>)in.readObject();
            //closing the stream
            in.close();
            fin.close();
            System.out.println("successful deserialization");

            if (userDatabase == null) {
                userDatabase = new HashMap<String, Account>();
            }
            UserDatabase.setAccounts(userDatabase);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("unsuccessful deserialization");
            if (userDatabase == null) {
                userDatabase = new HashMap<String, Account>();
            }
            UserDatabase.setAccounts(userDatabase);
        }

//        System.out.println("Deserialized UserDatabase size: " + UserDatabase.getAccounts().size());

        //initial page: user authorization
        LoginUI frame = new LoginUI();
        frame.setTitle("Login Page");
        frame.setVisible(true);
        frame.setBounds(0, 0, 1440, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        LoginUI.setFrame(frame);


        HashMap<String, Account> finalUserDatabase = userDatabase;
        LoginUI.getFrames()[0].addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    try {
                        FileOutputStream fout = new FileOutputStream("/Users/tankenji/IdeaProjects/course-project-zuck-s-children/userDatabase.txt");
                        ObjectOutputStream out = new ObjectOutputStream(fout);
                        System.out.println("Serialized UserDatabase size: " + UserDatabase.getAccounts().size());
                        out.writeObject(finalUserDatabase);
                        out.flush();
                        out.close();
                        fout.close();
                        System.out.println("successful serialization");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    System.exit(0);
                }
            }
        });

    }
}
