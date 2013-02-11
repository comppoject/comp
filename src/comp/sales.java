package comp;


import beans.sell;
import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;


public class sales {
    
    private JLabel total, paid, rest, date, bill_num, search_lbl, pay_sys_lbl, pay_nw_lbl, pay_ltr_lbl, amount_lbl, name, tel, toPay_lbl, Paid_lbl;
    private JTextField totalT, paidT, restT, search, amount, nameT, telT, toPay, Paid;
    private JList list;
    private JFrame window;
    private JPanel main_panel;
    private JTable results;
    private JScrollPane results_sp, list_sp;
    public static DefaultTableModel model;
    private JRadioButton pay_nw, pay_ltr;
    private ButtonGroup choices;
    private JButton add, remove, save_bill, edit_bill;
    private beans.sell x;
    public Vector<String> rows;
    public static int choice = 1, item_num = 0;
    public static double total_price = 0;
    public String[] choices_arr = {"id", "title"};
    DateFormat df;
    Date curr_time;
    Timer time;
    
    JSeparator sep1 = new JSeparator(SwingConstants.VERTICAL), sep2 = new JSeparator(SwingConstants.HORIZONTAL), sep3 = new JSeparator(SwingConstants.VERTICAL);
    
    Font titles_font = new Font("serif", Font.BOLD, 20);
    Font tf = new Font("Tahoma", Font.BOLD, 12);
    Font lbls_font = new Font("Arial", Font.BOLD, 14);

    public sales(String obtained_title) {
        try {
            x = new beans.sell();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(sales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(sales.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        curr_time = Calendar.getInstance().getTime();
        
        window = new JFrame(obtained_title);
        main_panel = (JPanel) new JFrame().getContentPane();
        //**********Table***************************************
        model = new DefaultTableModel();
        results = new JTable(model);
        results.setPreferredScrollableViewportSize(new Dimension(800, 250));
        results.setFillsViewportHeight(true);
        results.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        results_sp = new JScrollPane(results);
        model.addColumn("السعر الكلي");
        model.addColumn("الكمية");
        model.addColumn("السعر");
        model.addColumn("اسم المنتج");
        //results.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //********************RadioButtons****************************
        pay_nw= new JRadioButton("حالاً", true);
        pay_ltr = new JRadioButton("بالآجل", false);
        choices = new ButtonGroup();
        choices.add(pay_nw);
        choices.add(pay_ltr);
        //**************Buttons****************
        add = new JButton("أضــف");
        remove = new JButton("حذف");
        save_bill = new JButton("تسجيل الفاتورة");
        edit_bill = new JButton("تعديل فاتورة سابقة");
        
        add.setFont(tf);
        remove.setFont(tf);
        save_bill.setFont(tf);
        edit_bill.setFont(tf);
        //**************Labels*****************
        total = new JLabel("الإجمالي");
        paid = new JLabel("المدفوع");
        rest = new JLabel("الباقي");
        search_lbl = new JLabel("بحث عن");
        pay_sys_lbl = new JLabel("نظام الدفع :");
        pay_ltr_lbl = new JLabel("بالآجل");
        pay_nw_lbl = new JLabel("حالاً");
        bill_num = new JLabel("التاريخ : " + df.format(curr_time));
        amount_lbl = new JLabel("الكمية");
        name = new JLabel("اسم العميل");
        tel = new JLabel("رقم التليفون");
        toPay_lbl = new JLabel("عليه");
        Paid_lbl = new JLabel("دفع");
        
        
        total.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        paid.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        rest.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        search_lbl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        pay_sys_lbl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        bill_num.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        amount_lbl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        toPay_lbl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        Paid_lbl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        total.setFont(titles_font);
        paid.setFont(titles_font);
        rest.setFont(titles_font);
        pay_sys_lbl.setFont(tf);
        search_lbl.setFont(tf);
        pay_nw_lbl.setFont(tf);
        pay_ltr_lbl.setFont(tf);
        bill_num.setFont(tf);
        amount_lbl.setFont(tf);
        name.setFont(tf);
        tel.setFont(tf);
        toPay_lbl.setFont(tf);
        Paid_lbl.setFont(tf);
        //**************TextFields*****************
        search = new JTextField();
        totalT = new JTextField("0");
        paidT = new JTextField("0");
        restT = new JTextField("0");
        amount = new JTextField();
        nameT = new JTextField();
        telT = new JTextField();
        Paid = new JTextField();
        toPay = new JTextField();
        
        //search.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        totalT.setFont(titles_font);
        paidT.setFont(titles_font);
        restT.setFont(titles_font);
        
        totalT.setEditable(false);
        //paidT.setEditable(false);
        restT.setEditable(false);
        search.putClientProperty("JTextField.variant", "search");
        search.putClientProperty("JTextField.Search.PlaceholderText", "Search...");
        
        
        //*******************************List******************************
        list = new JList();
        list_sp = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //*****************Table Selection_Listener*************************

//
//        ListSelectionModel ls = results.getSelectionModel();
//
//        ls.addListSelectionListener(
//                new ListSelectionListener() {
//                    public void valueChanged(ListSelectionEvent e) {
//                        String nn = null;
//                        if (results.getRowCount() > 0 && nn == null) {
//                            nn = results.getValueAt(results.getSelectedRow(), results.getSelectedColumn()).toString();
//                            System.out.println(nn);
//                            nn = null;
//                        }
//                    }
//                });

        //**************Placing Objects*****************
        total.setBounds(630, 20, 100, 30);
        totalT.setBounds(630, 60, 150, 60);
        paid.setBounds(630, 130, 100, 30);
        paidT.setBounds(630, 170, 150, 60);
        rest.setBounds(630, 240, 100, 30);
        restT.setBounds(630, 280, 150, 60);
        search_lbl.setBounds(550, 20, 70, 30);
        search.setBounds(425, 20, 120, 30);
        list_sp.setBounds(425, 60, 200, 100);
        pay_sys_lbl.setBounds(130, 60, 70, 30);
        pay_nw.setBounds(100, 65, 20, 20);
        pay_ltr.setBounds(50, 65, 20, 20);
        pay_nw_lbl.setBounds(75, 60, 60, 30);
        pay_ltr_lbl.setBounds(10, 60, 60, 30);
        bill_num.setBounds(200, 20, 200, 30);
        add.setBounds(330, 130, 70, 30);
        remove.setBounds(250, 130, 70, 30);
        amount_lbl.setBounds(300, 70, 100, 30);
        amount.setBounds(250, 70, 100, 30);
        results_sp.setBounds(10, 275, 615, 370);
        save_bill.setBounds(490, 650, 135, 30);
        edit_bill.setBounds(340, 650, 145, 30);
        sep1.setBounds(415, 15, 1, 40);
        sep2.setBounds(10, 55, 407, 1);
        name.setBounds(110, 100, 100, 30);
        nameT.setBounds(10, 100, 100, 30);
        tel.setBounds(110, 140, 100, 30);
        telT.setBounds(10, 140, 100, 30);
        toPay_lbl.setBounds(110, 180, 100, 30);
        toPay.setBounds(10, 180, 100, 30);
        Paid_lbl.setBounds(110, 220, 100, 30);
        Paid.setBounds(10, 220, 100, 30);
        sep3.setBounds(220, 60, 1, 190);
        
        //*****************Creating time ***********************
        
        time = new Timer();
        time.schedule(new Time(), 0, 1000);
        
        //************Adding Components To Main Panel*************
        main_panel.setLayout(null);
        
        main_panel.add(total);
        main_panel.add(totalT);
        main_panel.add(paid);
        main_panel.add(paidT);
        main_panel.add(rest);
        main_panel.add(restT);
        main_panel.add(search_lbl);
        main_panel.add(search);
        main_panel.add(list_sp);
        main_panel.add(pay_sys_lbl);
        main_panel.add(pay_nw_lbl);
        main_panel.add(pay_ltr_lbl);
        main_panel.add(pay_nw);
        main_panel.add(pay_ltr);
        main_panel.add(bill_num);
        main_panel.add(add);
        main_panel.add(remove);
        main_panel.add(amount);
        main_panel.add(amount_lbl);
        main_panel.add(results_sp);
        main_panel.add(save_bill);
        main_panel.add(edit_bill);
        main_panel.add(sep1);
        main_panel.add(sep2);
        
        k_lstnr k_lstnr = new k_lstnr();
        results_sp.addKeyListener(k_lstnr);
        results.addKeyListener(k_lstnr);
        amount.addKeyListener(k_lstnr);
        remove.addKeyListener(k_lstnr);
        add.addKeyListener(k_lstnr);
        totalT.addKeyListener(k_lstnr);
        paidT.addKeyListener(k_lstnr);
        restT.addKeyListener(k_lstnr);
        search.addKeyListener(k_lstnr);
        save_bill.addKeyListener(k_lstnr);
        edit_bill.addKeyListener(k_lstnr);
        
        
        //********************************* search button Key listener **********************************************     
        search.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void insertUpdate(DocumentEvent e) {
                        if (search.getText().equals("")) {
                            list.setListData(new Object[] {});
                        } else {
                            try {
                                rows = beans.sell.get_col("name", "items", "where name like '%" + search.getText() + "%'");
                            } catch (SQLException ex) {
                                Logger.getLogger(sales.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            list.setListData(rows);
                        }
                    }
                    public void removeUpdate(DocumentEvent e) {
                        if (search.getText().equals("")) {
                            list.setListData(new Object[] {});
                        } else {
                            try {
                                rows = beans.sell.get_col("name", "items", "where id like '%" + search.getText() + "%'");
                            } catch (SQLException ex) {
                                Logger.getLogger(sales.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            list.setListData(rows);
                        }
                    }
                    public void changedUpdate(DocumentEvent e) {
                    }
                });

        //**************************** Buttons Action Listeners ***********************************************
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if((list.getSelectedValue() != null) && (isInt(amount.getText()) && !amount.getText().equals(""))) {
                    String selected = list.getSelectedValue().toString();
                    String amount_val = amount.getText();
                    String[] result = null;
                    try {
                        result = beans.sell.search("name, price", "items", "where name = '" + selected + "'")[0];
                    } catch (SQLException ex) {
                        Logger.getLogger(sales.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    double tot = Double.parseDouble(amount_val) * Integer.parseInt(result[1]);
                    total_price += tot;
                    model.addRow(new Object[] {});
                    model.setValueAt(tot, item_num, 0);
                    model.setValueAt(amount_val, item_num, 1);
                    model.setValueAt(result[1], item_num, 2);
                    model.setValueAt(result[0], item_num, 3);
                    totalT.setText("" + total_price);
                    item_num++;
                }else {
                    JOptionPane.showMessageDialog(null, "خطأ !   لم يتم ادخال عنصر أو الكمية");
                }
            }
        });
        remove.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(results.getSelectedRow() != -1) {
                    double tot_rem = Double.parseDouble((String)results.getValueAt(results.getSelectedRow(), 2));
                    model.removeRow(results.getSelectedRow());
                    item_num--;
                    total_price -= tot_rem;
                    totalT.setText("" + total_price);
                } else {
                    JOptionPane.showMessageDialog(null, "لم يتم اختيار سطر لحذفه");
                }
            }
        });
        
        save_bill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!paidT.getText().equals("") && isNum(paidT.getText())) {
                    double get_paid = Double.parseDouble(paidT.getText());
                    if(get_paid >= total_price) {
                        String[][] id_conn = null;
                        try {
                            id_conn = beans.sell.search("MAX(id)", "sell", "");
                        } catch (SQLException ex) {
                            Logger.getLogger(sales.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        int current_id = Integer.parseInt(id_conn[0][0]) + 1;
                        double remaining = get_paid - total_price;
                        restT.setText("" + remaining);
                        try {
                            x.insertSell("date, total", "sell", totalT.getText());
                        } catch (Exception ex) {
                            Logger.getLogger(sales.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            
                        int rows = results.getRowCount() - 1;
                        System.out.println(rows);
                        try {
                            while(rows >= 0) {
                                String[][] items = x.search("id", "items", "where name = '" + model.getValueAt(rows, 3) + "'");
                                String item_id = items[0][0];
                                String quant = (String) model.getValueAt(rows, 1);
                                String price = (String) model.getValueAt(rows, 2);
                                x.insert("item_id, sell_id, quantity, price", "sell_detail", "'" + item_id + "', '" + current_id + "', '" + quant + "', '" + price + "'");
                                rows--;
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(sales.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                        item_num = 0;
                        total_price = 0;
                    } else {
                        JOptionPane.showMessageDialog(null, "المبلغ المدفوع أقل من المطلوب");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "خطأ في إدخال المبلغ المدفوع");
                }
            }
        });
        
        pay_ltr.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    main_panel.add(name);
                    main_panel.add(nameT);
                    main_panel.add(tel);
                    main_panel.add(telT);
                    main_panel.add(toPay);
                    main_panel.add(toPay_lbl);
                    main_panel.add(Paid);
                    main_panel.add(Paid_lbl);
                    main_panel.add(sep3);
                    main_panel.validate();
                    main_panel.repaint();
                }
            }
        });
        pay_nw.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    main_panel.remove(name);
                    main_panel.remove(nameT);
                    main_panel.remove(tel);
                    main_panel.remove(telT);
                    main_panel.remove(toPay);
                    main_panel.remove(toPay_lbl);
                    main_panel.remove(Paid);
                    main_panel.remove(Paid_lbl);
                    main_panel.remove(sep3);
                    main_panel.validate();
                    main_panel.repaint();
                }
            }
        });
        
        //*************Window listener**********************
        
        window.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {}
            public void windowClosing(WindowEvent e) {
                time.cancel();
            }
            public void windowClosed(WindowEvent e) {
                time.cancel();
            }
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}
        });
        
        //*************Setting Up Main Frame*****************
        window.add(main_panel);
        window.setSize(800, 728);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
    }
    
    public void resetTable() {
        for (; results.getRowCount() > 0;) {
            model.removeRow(0);
        }
        results.revalidate();
    }
    
    public boolean isNum(String num) {
        for (int i = 0; i < num.length(); i++)
            if(!(Character.isDigit(num.charAt(i)))) {
                if(num.charAt(i) == '.')
                    continue;
                return false;
            }
        return true;
    }
    
    public boolean isInt(String num) {
        try { 
            Integer.parseInt(num); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        return true;
    }
    
    private class k_lstnr implements KeyListener {
        public void keyTyped(KeyEvent e) {}
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_F5) {
                list.setListData(new Object[] {});
                list.revalidate();
                resetTable();
                search.setText(null);
                amount.setText(null);
                totalT.setText("0");
                paidT.setText("0");
                restT.setText("0");
            }
        }
        public void keyReleased(KeyEvent e) {}
    }
    
    class Time extends TimerTask {
        public void run() {
            curr_time = Calendar.getInstance().getTime();
            bill_num.setText("التاريخ : " + df.format(curr_time));
        }
    }

    public static void main(String[] args) {
        try {
            //UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
            new sales("المبيعات");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
