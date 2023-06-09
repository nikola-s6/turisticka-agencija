/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.form;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JButton;

/**
 *
 * @author nikol
 */
public class FrmLogin extends javax.swing.JFrame {

    /**
     * Creates new form FrmLogin
     */
    public FrmLogin() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblSifra = new javax.swing.JLabel();
        txtSifra = new javax.swing.JPasswordField();
        btnPrijava = new javax.swing.JButton();
        greskaEmail = new javax.swing.JLabel();
        greskaSifra = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Prijava korisnika na sistem");

        lblEmail.setText("Email:");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        lblSifra.setText("Sifra:");

        btnPrijava.setText("Prijava na sistem");

        greskaEmail.setForeground(new java.awt.Color(255, 0, 0));

        greskaSifra.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmail)
                            .addComponent(lblSifra))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(greskaEmail)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtEmail)
                                .addComponent(txtSifra, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
                            .addComponent(greskaSifra)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(btnPrijava)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(greskaEmail)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSifra)
                    .addComponent(txtSifra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(greskaSifra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(btnPrijava)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrijava;
    private javax.swing.JLabel greskaEmail;
    private javax.swing.JLabel greskaSifra;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblSifra;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtSifra;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JButton getBtnPrijava() {
        return btnPrijava;
    }

    public void setBtnPrijava(javax.swing.JButton btnPrijava) {
        this.btnPrijava = btnPrijava;
    }

    public javax.swing.JLabel getGreskaEmail() {
        return greskaEmail;
    }

    public void setGreskaEmail(javax.swing.JLabel greskaEmail) {
        this.greskaEmail = greskaEmail;
    }

    public javax.swing.JLabel getGreskaSifra() {
        return greskaSifra;
    }

    public void setGreskaSifra(javax.swing.JLabel greskaSifra) {
        this.greskaSifra = greskaSifra;
    }

    public javax.swing.JLabel getLblEmail() {
        return lblEmail;
    }

    public void setLblEmail(javax.swing.JLabel lblEmail) {
        this.lblEmail = lblEmail;
    }

    public javax.swing.JLabel getLblSifra() {
        return lblSifra;
    }

    public void setLblSifra(javax.swing.JLabel lblSifra) {
        this.lblSifra = lblSifra;
    }

    public javax.swing.JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(javax.swing.JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public javax.swing.JPasswordField getTxtSifra() {
        return txtSifra;
    }

    public void setTxtSifra(javax.swing.JPasswordField txtSifra) {
        this.txtSifra = txtSifra;
    }
}
