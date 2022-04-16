package com.company;

import com.company.Parsers;
import com.company.Reactor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private JButton b = new JButton("import");
    private JFileChooser bb = new JFileChooser();
    private JTree jTree = new JTree(new DefaultMutableTreeNode());

    public GUI() {

        super("form");
        this.setBounds(300, 300, 300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2, 2, 2, 2));

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Reactor> reactorList = new ArrayList<>();
                    int returnvalue = bb.showOpenDialog(GUI.this);
                    if (returnvalue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = bb.getSelectedFile();
                        try {
                            Parsers p = new Parsers();
                            if (selectedFile.getName().endsWith(".xml")) {
                                reactorList = p.Parsxml(selectedFile.getAbsolutePath());
                            } else if (selectedFile.getName().endsWith(".json")) {
                                reactorList = p.parsjson(selectedFile.getAbsolutePath());
                            } else if (selectedFile.getName().endsWith(".yaml")) {
                                reactorList = p.parsyaml(selectedFile.getAbsolutePath());
                            }
                            gettree(jTree, reactorList);


                            JOptionPane.showMessageDialog(null, "Done", "Import", JOptionPane.PLAIN_MESSAGE);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(null, "файл не существует", "Error", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                } catch (HeadlessException HeadlessException) {
                    JOptionPane.showMessageDialog(null, "Not found", "Error", JOptionPane.PLAIN_MESSAGE);
                }

            }
        });
        container.add(b);
        JScrollPane jScrollPane = new JScrollPane(jTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);// TODO: 07.04.2022
        container.add(jScrollPane);
    }

    public void gettree(JTree t, List<Reactor> reactors){//надо подумать!"!"!!
        DefaultTreeModel tr = (DefaultTreeModel)t.getModel();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultMutableTreeNode sourse = new DefaultMutableTreeNode(reactors.get(0).getSourse());//берем нулевой реактор
        root.add(sourse);
        for(Reactor reactor: reactors){     //это цикл foreach
            DefaultMutableTreeNode y = new DefaultMutableTreeNode(reactor.getClasss()); //создаем штуку, куда добавили все данные о реакторе
            sourse.add(y); //в корневой элемент добавляем элемент сл уровня это по сути название расширения
            List<String> str = new ArrayList<>();
            str.add("Class: "+reactor.getClasss());
            str.add("Burnup: "+ reactor.getBurnup());
            str.add("Kpd: "+ reactor.getKpd());
            str.add("Enrichment: "+ reactor.getEnrichment());
            str.add("Termal capacity: "+ reactor.getTermal_capacity());
            str.add("Electrical capacity: "+ reactor.getElectrical_capacity());
            str.add("Life time: "+ reactor.getLife_time());
            str.add("First load: "+ reactor.getFirst_load());
            for (String str1: str ){
                y.add(new DefaultMutableTreeNode(str1));//еще элементы на уровень глубже
            }
        }
        tr.setRoot(root);
        tr.reload();//показывает то, чем мы заполнили JTree, типа команда по обновлению


    }
}