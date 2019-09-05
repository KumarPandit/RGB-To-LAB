/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Blake Lee and Kumar Prasad Pandit
 */
public class ColorConvert extends javax.swing.JFrame {
    public ColorConvert() {
        initComponents();
        this.setTitle("RGB / Lab Converter");
    }
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jFieldR = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jFieldB = new javax.swing.JTextField();
        jFieldG = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jFieldMax = new javax.swing.JTextField();
        jComboSpace = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jButtonConv = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jFieldLAB_L = new javax.swing.JTextField();
        jFieldLAB_A = new javax.swing.JTextField();
        jFieldLAB_B = new javax.swing.JTextField();
        jLabelError = new javax.swing.JLabel();
        jButtonConv2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Red");

        jLabel2.setText("Green");

        jLabel3.setText("Blue");

        jLabel4.setText("Max");

        jComboSpace.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Adobe RGB", "Apple RGB (1998)", "Best RGB", "Beta RGB", "Bruce RGB", "CIE RGB", "ColorMatch RGB", "Don RGB 4", "ECI RGB v2", "Ekta Space PS5", "NTSC RGB ", "PAL/SECAM RGB", "ProPhoto RGB", "SMPTE-C RGB", "sRGB", "Wide Gamut RGB" }));

        jLabel5.setText("RGB Working Space");

        jButtonConv.setText("RGB to Lab");
        jButtonConv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConvActionPerformed(evt);
            }
        });

        jLabel6.setText("LAB");

        jButtonConv2.setText("Lab to RGB");
        jButtonConv2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConv2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel6)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jFieldLAB_L, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(68, 68, 68)
                                                                .addComponent(jFieldLAB_A, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jLabel1)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jFieldR, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jLabel2)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jFieldG, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jLabel4)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jFieldMax, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jLabel5))))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jComboSpace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel3)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jFieldB, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jFieldLAB_B, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButtonConv)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonConv2)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jFieldR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jFieldG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jFieldB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jFieldMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboSpace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonConv)
                                        .addComponent(jButtonConv2))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(jFieldLAB_L, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jFieldLAB_A, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jFieldLAB_B, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addComponent(jLabelError, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
        );
        jComboSpace.setSelectedIndex(14);
        jFieldMax.setText("255");
        jLabelError.setText("");

        pack();
    }

    private void jButtonConvActionPerformed(java.awt.event.ActionEvent evt) {
        String[] strs = new String[4];
        strs[0] = jFieldR.getText();
        strs[1] = jFieldG.getText();
        strs[2] = jFieldB.getText();
        strs[3] = jFieldMax.getText();
        for (String st : strs){
            if (!isNumeric(st)){
                jLabelError.setText("Please make sure each field is a valid number: "+st);
                return;
            }
        }
        double[] inputs = new double[4];
        for (int x=0;x<4;x++){
            inputs[x] = Double.parseDouble(strs[x]);
        }

        for (int x=0;x<3;x++){
            if (inputs[x] > inputs[3] || inputs[x] < 0.0){
                jLabelError.setText("Input RGB channel values must be lower or equal to the maximum value: "+inputs[x]+" > "+inputs[3]);
                return;
            }
        }
        jLabelError.setText("");
        int spaceIndex = jComboSpace.getSelectedIndex();
        double[][] LAB = conv.rgb2lab(inputs[0], inputs[1], inputs[2],inputs[3],spaceIndex);
        for (double[] db1 : LAB){
                db1[0] = round(db1[0], 4);
        }
        jFieldLAB_L.setText("" + LAB[0][0]);
        jFieldLAB_A.setText("" + LAB[1][0]);
        jFieldLAB_B.setText("" + LAB[2][0]);
        jFieldLAB_L.setCaretPosition(0);
        jFieldLAB_A.setCaretPosition(0);
        jFieldLAB_B.setCaretPosition(0);

    }

    private void jButtonConv2ActionPerformed(java.awt.event.ActionEvent evt){
        String[] strs = new String[4];
        strs[0] = jFieldLAB_L.getText();
        strs[1] = jFieldLAB_A.getText();
        strs[2] = jFieldLAB_B.getText();
        strs[3] = jFieldMax.getText();
        for (String st : strs){
            if (!isNumeric(st)){
                jLabelError.setText("Please make sure each field is a valid number: "+st);
                return;
            }
        }
        double[] inputs = new double[4];
        for (int x=0;x<4;x++){
            inputs[x] = Double.parseDouble(strs[x]);
        }
        if (inputs[0] > 100.0 || inputs[0] < 0){
            jLabelError.setText("Lab Lightness channel must be between 0 and 100: "+inputs[0]);
            return;
        }
        if (inputs[1] > 128.0 || inputs[1] < -128.0){
            jLabelError.setText("Lab A channel must be between -128 and 128: "+inputs[1]);
            return;
        }
        if (inputs[2] > 128.0 || inputs[2] < -128.0){
            jLabelError.setText("Lab B channel must be between -128 and 128: "+inputs[2]);
            return;
        }
        jLabelError.setText("");
        int spaceIndex = jComboSpace.getSelectedIndex();
        double[][] RGB = conv.lab2rgb(inputs[0], inputs[1],inputs[2],inputs[3],spaceIndex);
        for (double[] db1 : RGB){
            db1[0] = round(db1[0], 4);
        }
        jFieldR.setText(""+RGB[0][0]);
        jFieldG.setText(""+RGB[1][0]);
        jFieldB.setText(""+RGB[2][0]);
        jFieldR.setCaretPosition(0);
        jFieldG.setCaretPosition(0);
        jFieldB.setCaretPosition(0);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static void main(String args[]) {
        new ColorConvert().setVisible(true);
        conv = new LABRGB();

        
       
    }

    private static boolean isNumeric(String str){
        try{
            double d = Double.parseDouble(str);
        }
        catch (NumberFormatException nfe){
            return false;
        }
        return true;
    }

    private javax.swing.JButton jButtonConv;
    private javax.swing.JButton jButtonConv2;
    private javax.swing.JComboBox jComboSpace;
    private javax.swing.JTextField jFieldB;
    private javax.swing.JTextField jFieldG;
    private javax.swing.JTextField jFieldLAB_A;
    private javax.swing.JTextField jFieldLAB_B;
    private javax.swing.JTextField jFieldLAB_L;
    private javax.swing.JTextField jFieldMax;
    private javax.swing.JTextField jFieldR;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelError;
    private static LABRGB conv;
}
