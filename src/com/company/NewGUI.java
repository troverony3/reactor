package com.company;

import bd.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewGUI extends JFrame{
    private JButton countryButton = new JButton("По странам");
    private JButton companyButton = new JButton("По компаниям");
    private JButton regionButton = new JButton("По регионам");
    private JTable table1 = new JTable();
    private JTable table2 = new JTable();
    private JTable table3 = new JTable();
    private JScrollPane scrollPane = new JScrollPane();
    private JButton b = new JButton("import");
    private JFileChooser bb = new JFileChooser();
    DBBuilder builder = new DBBuilder("Lab3");//тут название файла с БД
    Read read = new Read();
    DBProvider provider = new DBProvider(builder);
    public NewGUI() {
        super("form");
        this.setBounds(100, 10, 900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(1, 3, 2, 4));
        container.add(countryButton);
        container.add(companyButton);
        container.add(regionButton);
        container.add(b);


        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Reactor> reactorList = new ArrayList<>();
                    int returnvalue = bb.showOpenDialog(NewGUI.this);
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
                            try {
                                provider.connect();
                                provider.getAll("countries", reactorList);//зачитываем таблички из бд таблички
                                provider.getAll("regions", reactorList);//зачитываем таблички из бд
                                provider.getAll("companies", reactorList);//зачитываем таблички из бд
                                provider.getAll("sites", reactorList);//зачитываем таблички из бд
                                provider.getAll("units", reactorList);//зачитываем таблички из бд
                                provider.close();

                                Fuel(builder.getRegions(), builder.getCountries(), builder.getCompanies(), builder.getSites(), builder.getUnits());
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                System.exit(1);
                            }

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


        // Parsers pars = new Parsers();//нужно сделать файл чузер как кнопку, зачитать из предыдущих лаб массив реакторов и его передать в тодо вместо нул
        //еще сделать 2 таблички)))






        countryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable1();

                JFrame frame = new JFrame();

                JScrollPane scrollPane = new JScrollPane(table1);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(scrollPane);
                frame.setSize(600, 500);
                frame.setVisible(true);

                read.ForCountries(table1, builder.getCountries());
            }
        });
        companyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable2();

                JFrame frame = new JFrame();
                JScrollPane scrollPane = new JScrollPane(table2);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(scrollPane);
                frame.setSize(600, 500);
                frame.setVisible(true);

                read.ForCompanies(table2, builder.getCompanies());
            }
        });

        regionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable3();

                JFrame frame = new JFrame();
                JScrollPane scrollPane = new JScrollPane(table3);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(scrollPane);
                frame.setSize(600, 500);
                frame.setVisible(true);

                read.ForRegions(table3, builder.getRegions());
            }
        });
    }
    public void Fuel(ArrayList<Regions> Regions, ArrayList< Countries > Countries, ArrayList<Companies> Companies,
                      ArrayList<Sites> Sites, ArrayList<Units> Units) {


        Regions.forEach(a -> {
            Countries.forEach(b -> {
                if (a.getId() == b.getRegion()) {
                    Companies.forEach(c -> {
                        if (b.getId() == c.getCountry()) {
                            Sites.forEach(d -> {
                                if (c.getId() == d.getCompany()) {
                                    d.setFuel((int) d.getFuel());
                                    Units.forEach(e -> {
                                        if (d.getId() == e.getSite()) {
                                            d.setFuel((e.getFuel_consumption()));
                                        }
                                    });
                                    c.setFuelConsuption(d.getFuel());
                                }
                            });
                            b.setFuelConsuption(c.getFuelConsuption());
                        }
                    });
                    a.setFuelConsuption(b.getFuelConsuption());
                }
            });
        });
    }

    private void createTable1 () {  //это мы создали табличку
        table1.setModel(new DefaultTableModel(null, new String[]{"Страна", "ежегодное потребление топлива"}) {
            @Override
            public boolean isCellEditable(int i, int k) {
                return false;
            }
        });
    }


    private void createTable3 () {  //это мы создали табличку
        table3.setModel(new DefaultTableModel(null, new String[]{"регион", "ежегодное потребление топлива"}) {
            @Override
            public boolean isCellEditable(int i, int k) {
                return false;
            }
        });
    }



    private void createTable2 () {  //это мы создали табличку
        table2.setModel(new DefaultTableModel(null, new String[]{"компания", "ежегодное потребление топлива"}) {
            @Override
            public boolean isCellEditable(int i, int k) {
                return false;
            }
        });
    }



}
///ничего не получается,
// регионы и компании не высвечиваются ,
// топливо не понятно откуда берется и как считается
// ничего не понимаю
//нужен ли нам вообе этот файлик с разной разметко и как его тут использовать.....
//грустно