/*
 * Classe para criar o painel inferior JPanelBottom
 * O painel inferior é reponsável por fornecer dicas e as coordenadas do rato
 * Estas funcionalidades são controladas pela classe UserInterface
 */

package app.frontend;

/**
 *
 * @author André de Sousa
 */
public class PanelBottom extends javax.swing.JPanel {

    /**
     * Creates new form JP_Bottom
     *
     * @param type é o tipo de modelo estrutural
     */
    public PanelBottom(String type) {
        initComponents();

        if ("Beams".equals(type)) {
            jLabelY.setText("Z = 0.00 m");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabelZoom = new javax.swing.JLabel();
        jLabelCoordinates = new javax.swing.JLabel();
        jLabelX = new javax.swing.JLabel();
        jLabelY = new javax.swing.JLabel();
        jLabelInformation = new javax.swing.JLabel();

        setBackground(new java.awt.Color(222, 222, 222));
        setPreferredSize(new java.awt.Dimension(675, 25));

        jLabelZoom.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelZoom.setText("Zoom: 100%");
        jLabelZoom.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabelCoordinates.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCoordinates.setText("Coordinates:");
        jLabelCoordinates.setPreferredSize(new java.awt.Dimension(80, 25));

        jLabelX.setText("X = 0,00 m");
        jLabelX.setPreferredSize(new java.awt.Dimension(60, 25));

        jLabelY.setText("Y = 0,00 m");
        jLabelY.setPreferredSize(new java.awt.Dimension(60, 25));

        jLabelInformation.setText("Information");
        jLabelInformation.setPreferredSize(new java.awt.Dimension(34, 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelInformation, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(
                            jLabelCoordinates,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(18, 18, 18)
                        .addComponent(
                            jLabelX,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(18, 18, 18)
                        .addComponent(
                            jLabelY,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(100, 100, 100)
                        .addComponent(
                            jLabelZoom,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addContainerGap()
                )
        );
        layout.setVerticalGroup(
            layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout
                        .createSequentialGroup()
                        .addGroup(
                            layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelZoom,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jLabelCoordinates,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jLabelX,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jLabelY,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jLabelInformation,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addGap(0, 0, Short.MAX_VALUE)
                )
        );
    } // </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelCoordinates;
    public javax.swing.JLabel jLabelInformation;
    public javax.swing.JLabel jLabelX;
    public javax.swing.JLabel jLabelY;
    public javax.swing.JLabel jLabelZoom;
    // End of variables declaration//GEN-END:variables
}
