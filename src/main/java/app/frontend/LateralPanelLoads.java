/*
 * Classe para criar o painel lateral Loads
 * O painel lateral é composto por sub painéis abertos individualmente
 * Os sub painéis são o jPanelConLoads, jPanelMoments, jPanelDistrLoads,
 *jPanelAxialLoads, jPanelPlanarLoads, jPanelThermalLoads e o jPanelSelfWeight
 */

package app.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author André de Sousa
 */
public class LateralPanelLoads extends javax.swing.JPanel {

    private String type;
    private String jPanelActivated;
    private final Color selectedColor;
    private final Color notSelectedColor;
    private final Color enteredColor;

    /**
     * Creates new form JLateralPanel
     */
    public LateralPanelLoads() {
        initComponents();

        type = "";
        jPanelActivated = "";
        selectedColor = new Color(51, 153, 255);
        notSelectedColor = new Color(236, 236, 236);
        enteredColor = new Color(222, 222, 222);

        jLabelConLoadsTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelMomentsTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelDistrLoadsTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelAxialLoadsTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelPlanarLoadsTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelThermalLoadsTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelSelfWeightTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelConLoadsA.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelConLoadsB.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelMomentsA.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelDistrLoadsA.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelDistrLoadsB.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelAxialLoadsA.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelPlanarLoadsA0.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelPlanarLoadsA2.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelThermalLoadsTzero.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelThermalLoadsTtop.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelThermalLoadsTbot.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelSelfWeightA0.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelSelfWeightA2.setFont(new java.awt.Font("Tahoma", 0, 11));

        jB_ConLoadsDelete.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_ConLoadsEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_ConLoadsSave.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_MomentsDelete.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_MomentsEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_MomentsSave.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_DistrLoadsDelete.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_DistrLoadsEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_DistrLoadsSave.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_AxialLoadsDelete.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_AxialLoadsEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_AxialLoadsSave.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_PlanarLoadsDelete.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_PlanarLoadsEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_PlanarLoadsSave.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_ThermalLoadsDelete.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_ThermalLoadsEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_ThermalLoadsSave.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_SelfWeightDelete.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_SelfWeightEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_SelfWeightSave.setFont(new java.awt.Font("Tahoma", 0, 11));

        jTF_ConLoadsFx.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_ConLoadsFy.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_MomentsM.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_DistrLoadsQx.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_DistrLoadsQy.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_AxialLoadsN.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_PlanarLoadsQz.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_ThermalLoadsTzero.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_ThermalLoadsTtop.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_ThermalLoadsTbot.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_SelfWeight.setFont(new java.awt.Font("Tahoma", 0, 11));

        jCB_ConLoadsLoad.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_MomentsLoad.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_DistrLoadsLoad.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_AxialLoadsLoad.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_PlanarLoadsLoad.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_ThermalLoads.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_SelfWeight.setFont(new java.awt.Font("Tahoma", 0, 11));

        jButtonClose.setFont(new java.awt.Font("Tahoma", 0, 11));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLateralPanelConLoads = new javax.swing.JPanel();
        jLabelConLoadsTitle = new javax.swing.JLabel();
        jLabelConLoadsA = new javax.swing.JLabel();
        jLabelConLoadsB = new javax.swing.JLabel();
        jCB_ConLoadsLoad = new javax.swing.JComboBox();
        jB_ConLoadsSave = new javax.swing.JButton();
        jB_ConLoadsEdit = new javax.swing.JButton();
        jB_ConLoadsDelete = new javax.swing.JButton();
        jTF_ConLoadsFx = new javax.swing.JTextField();
        jTF_ConLoadsFy = new javax.swing.JTextField();
        jLateralPanelMoments = new javax.swing.JPanel();
        jLabelMomentsTitle = new javax.swing.JLabel();
        jLabelMomentsA = new javax.swing.JLabel();
        jCB_MomentsLoad = new javax.swing.JComboBox();
        jB_MomentsSave = new javax.swing.JButton();
        jB_MomentsEdit = new javax.swing.JButton();
        jB_MomentsDelete = new javax.swing.JButton();
        jTF_MomentsM = new javax.swing.JTextField();
        jLateralPanelDistrLoads = new javax.swing.JPanel();
        jLabelDistrLoadsTitle = new javax.swing.JLabel();
        jLabelDistrLoadsA = new javax.swing.JLabel();
        jLabelDistrLoadsB = new javax.swing.JLabel();
        jCB_DistrLoadsLoad = new javax.swing.JComboBox();
        jB_DistrLoadsSave = new javax.swing.JButton();
        jB_DistrLoadsEdit = new javax.swing.JButton();
        jB_DistrLoadsDelete = new javax.swing.JButton();
        jTF_DistrLoadsQx = new javax.swing.JTextField();
        jTF_DistrLoadsQy = new javax.swing.JTextField();
        jLateralPanelAxialLoads = new javax.swing.JPanel();
        jLabelAxialLoadsTitle = new javax.swing.JLabel();
        jLabelAxialLoadsA = new javax.swing.JLabel();
        jCB_AxialLoadsLoad = new javax.swing.JComboBox();
        jB_AxialLoadsSave = new javax.swing.JButton();
        jB_AxialLoadsEdit = new javax.swing.JButton();
        jB_AxialLoadsDelete = new javax.swing.JButton();
        jTF_AxialLoadsN = new javax.swing.JTextField();
        jLateralPanelPlanarLoads = new javax.swing.JPanel();
        jLabelPlanarLoadsTitle = new javax.swing.JLabel();
        jLabelPlanarLoadsA0 = new javax.swing.JLabel();
        jLabelPlanarLoadsA1 = new javax.swing.JLabel();
        jLabelPlanarLoadsA2 = new javax.swing.JLabel();
        jCB_PlanarLoadsLoad = new javax.swing.JComboBox();
        jB_PlanarLoadsSave = new javax.swing.JButton();
        jB_PlanarLoadsEdit = new javax.swing.JButton();
        jB_PlanarLoadsDelete = new javax.swing.JButton();
        jTF_PlanarLoadsQz = new javax.swing.JTextField();
        jLateralPanelThermalLoads = new javax.swing.JPanel();
        jLabelThermalLoadsTitle = new javax.swing.JLabel();
        jLabelThermalLoadsTtop = new javax.swing.JLabel();
        jLabelThermalLoadsTbot = new javax.swing.JLabel();
        jCB_ThermalLoads = new javax.swing.JComboBox();
        jB_ThermalLoadsSave = new javax.swing.JButton();
        jB_ThermalLoadsEdit = new javax.swing.JButton();
        jB_ThermalLoadsDelete = new javax.swing.JButton();
        jTF_ThermalLoadsTtop = new javax.swing.JTextField();
        jTF_ThermalLoadsTbot = new javax.swing.JTextField();
        jTF_ThermalLoadsTzero = new javax.swing.JTextField();
        jLabelThermalLoadsTzero = new javax.swing.JLabel();
        jLateralPanelSelfWeight = new javax.swing.JPanel();
        jB_SelfWeightEdit = new javax.swing.JButton();
        jB_SelfWeightSave = new javax.swing.JButton();
        jB_SelfWeightDelete = new javax.swing.JButton();
        jTF_SelfWeight = new javax.swing.JTextField();
        jCB_SelfWeight = new javax.swing.JComboBox();
        jLabelSelfWeightTitle = new javax.swing.JLabel();
        jLabelSelfWeightA0 = new javax.swing.JLabel();
        jLabelSelfWeightA1 = new javax.swing.JLabel();
        jLabelSelfWeightA2 = new javax.swing.JLabel();
        jPanelContainer = new javax.swing.JPanel();
        jPanelConLoads = new JPanelMouseEvents();
        jLabel1 = new javax.swing.JLabel();
        jPanelConLoads_Container = new javax.swing.JPanel();
        jPanelMoments = new JPanelMouseEvents();
        jLabel2 = new javax.swing.JLabel();
        jPanelMoments_Container = new javax.swing.JPanel();
        jPanelDistrLoads = new JPanelMouseEvents();
        jLabel3 = new javax.swing.JLabel();
        jPanelDistrLoads_Container = new javax.swing.JPanel();
        jPanelAxialLoads = new JPanelMouseEvents();
        jLabel4 = new javax.swing.JLabel();
        jPanelAxialLoads_Container = new javax.swing.JPanel();
        jPanelPlanarLoads = new JPanelMouseEvents();
        jLabel5 = new javax.swing.JLabel();
        jPanelPlanarLoads_Container = new javax.swing.JPanel();
        jPanelThermalLoads = new JPanelMouseEvents();
        jLabel7 = new javax.swing.JLabel();
        jPanelThermalLoads_Container = new javax.swing.JPanel();
        jPanelSelfWeight = new JPanelMouseEvents();
        jLabel6 = new javax.swing.JLabel();
        jPanelSelfWeight_Container = new javax.swing.JPanel();
        jPanelBottom = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();
        jPanelSeparator = new javax.swing.JPanel();

        jLateralPanelConLoads.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelConLoads.setMinimumSize(new java.awt.Dimension(199, 150));

        jLabelConLoadsTitle.setText("Loads");

        jLabelConLoadsA.setText("Fx (KN)");

        jLabelConLoadsB.setText("Fy (KN)");

        jCB_ConLoadsLoad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<New>" }));
        jCB_ConLoadsLoad.setFocusable(false);
        jCB_ConLoadsLoad.setPreferredSize(new java.awt.Dimension(70, 20));

        jB_ConLoadsSave.setText("Save");
        jB_ConLoadsSave.setEnabled(false);
        jB_ConLoadsSave.setFocusable(false);

        jB_ConLoadsEdit.setText("Edit");
        jB_ConLoadsEdit.setFocusable(false);

        jB_ConLoadsDelete.setText("Delete");
        jB_ConLoadsDelete.setFocusable(false);

        jTF_ConLoadsFx.setEnabled(false);
        jTF_ConLoadsFx.setPreferredSize(new java.awt.Dimension(70, 20));

        jTF_ConLoadsFy.setEnabled(false);
        jTF_ConLoadsFy.setPreferredSize(new java.awt.Dimension(70, 20));

        javax.swing.GroupLayout jLateralPanelConLoadsLayout = new javax.swing.GroupLayout(jLateralPanelConLoads);
        jLateralPanelConLoads.setLayout(jLateralPanelConLoadsLayout);
        jLateralPanelConLoadsLayout.setHorizontalGroup(
            jLateralPanelConLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelConLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelConLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralPanelConLoadsLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelConLoadsTitle)
                                        .addGap(81, 81, 81)
                                        .addComponent(
                                            jCB_ConLoadsLoad,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    jLateralPanelConLoadsLayout
                                        .createSequentialGroup()
                                        .addGroup(
                                            jLateralPanelConLoadsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(
                                                    jLabelConLoadsB,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    Short.MAX_VALUE
                                                )
                                                .addComponent(
                                                    jLabelConLoadsA,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    Short.MAX_VALUE
                                                )
                                        )
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addGroup(
                                            jLateralPanelConLoadsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(
                                                    jTF_ConLoadsFx,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                                .addComponent(
                                                    jTF_ConLoadsFy,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                        )
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelConLoadsLayout
                                        .createSequentialGroup()
                                        .addComponent(jB_ConLoadsDelete)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_ConLoadsSave)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_ConLoadsEdit)
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralPanelConLoadsLayout.setVerticalGroup(
            jLateralPanelConLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelConLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelConLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelConLoadsTitle,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_ConLoadsLoad,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelConLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelConLoadsA,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_ConLoadsFx,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelConLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelConLoadsB,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_ConLoadsFy,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addGap(18, 18, 18)
                        .addGroup(
                            jLateralPanelConLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_ConLoadsEdit)
                                .addComponent(jB_ConLoadsSave)
                                .addComponent(jB_ConLoadsDelete)
                        )
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        jLateralPanelMoments.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelMoments.setMinimumSize(new java.awt.Dimension(199, 120));
        jLateralPanelMoments.setPreferredSize(new java.awt.Dimension(199, 120));

        jLabelMomentsTitle.setText("Loads");

        jLabelMomentsA.setText("M (KNm)");

        jCB_MomentsLoad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<New>" }));
        jCB_MomentsLoad.setFocusable(false);
        jCB_MomentsLoad.setPreferredSize(new java.awt.Dimension(70, 20));

        jB_MomentsSave.setText("Save");
        jB_MomentsSave.setEnabled(false);
        jB_MomentsSave.setFocusable(false);

        jB_MomentsEdit.setText("Edit");
        jB_MomentsEdit.setFocusable(false);

        jB_MomentsDelete.setText("Delete");
        jB_MomentsDelete.setFocusable(false);

        jTF_MomentsM.setEnabled(false);
        jTF_MomentsM.setPreferredSize(new java.awt.Dimension(70, 20));

        javax.swing.GroupLayout jLateralPanelMomentsLayout = new javax.swing.GroupLayout(jLateralPanelMoments);
        jLateralPanelMoments.setLayout(jLateralPanelMomentsLayout);
        jLateralPanelMomentsLayout.setHorizontalGroup(
            jLateralPanelMomentsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelMomentsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelMomentsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelMomentsLayout
                                        .createSequentialGroup()
                                        .addGap(0, 4, Short.MAX_VALUE)
                                        .addComponent(jB_MomentsDelete)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_MomentsSave)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_MomentsEdit)
                                )
                                .addGroup(
                                    jLateralPanelMomentsLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelMomentsA)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jTF_MomentsM,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    jLateralPanelMomentsLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelMomentsTitle)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jCB_MomentsLoad,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralPanelMomentsLayout.setVerticalGroup(
            jLateralPanelMomentsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelMomentsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelMomentsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelMomentsTitle,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_MomentsLoad,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelMomentsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelMomentsA,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_MomentsM,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(
                            jLateralPanelMomentsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_MomentsEdit)
                                .addComponent(jB_MomentsSave)
                                .addComponent(jB_MomentsDelete)
                        )
                        .addContainerGap()
                )
        );

        jLateralPanelDistrLoads.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelDistrLoads.setMinimumSize(new java.awt.Dimension(199, 150));
        jLateralPanelDistrLoads.setPreferredSize(new java.awt.Dimension(199, 150));

        jLabelDistrLoadsTitle.setText("Loads");

        jLabelDistrLoadsA.setText("Qx (KN/m)");

        jLabelDistrLoadsB.setText("Qy (KN/m)");

        jCB_DistrLoadsLoad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<New>" }));
        jCB_DistrLoadsLoad.setFocusable(false);
        jCB_DistrLoadsLoad.setPreferredSize(new java.awt.Dimension(70, 20));

        jB_DistrLoadsSave.setText("Save");
        jB_DistrLoadsSave.setEnabled(false);
        jB_DistrLoadsSave.setFocusable(false);

        jB_DistrLoadsEdit.setText("Edit");
        jB_DistrLoadsEdit.setFocusable(false);

        jB_DistrLoadsDelete.setText("Delete");
        jB_DistrLoadsDelete.setFocusable(false);

        jTF_DistrLoadsQx.setEnabled(false);
        jTF_DistrLoadsQx.setPreferredSize(new java.awt.Dimension(70, 20));

        jTF_DistrLoadsQy.setEnabled(false);
        jTF_DistrLoadsQy.setPreferredSize(new java.awt.Dimension(70, 20));

        javax.swing.GroupLayout jLateralPanelDistrLoadsLayout = new javax.swing.GroupLayout(jLateralPanelDistrLoads);
        jLateralPanelDistrLoads.setLayout(jLateralPanelDistrLoadsLayout);
        jLateralPanelDistrLoadsLayout.setHorizontalGroup(
            jLateralPanelDistrLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelDistrLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelDistrLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralPanelDistrLoadsLayout
                                        .createSequentialGroup()
                                        .addGroup(
                                            jLateralPanelDistrLoadsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(
                                                    jLabelDistrLoadsB,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    Short.MAX_VALUE
                                                )
                                                .addComponent(
                                                    jLabelDistrLoadsA,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    Short.MAX_VALUE
                                                )
                                        )
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addGroup(
                                            jLateralPanelDistrLoadsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(
                                                    jTF_DistrLoadsQx,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                                .addComponent(
                                                    jTF_DistrLoadsQy,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                        )
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelDistrLoadsLayout
                                        .createSequentialGroup()
                                        .addGroup(
                                            jLateralPanelDistrLoadsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                    jLateralPanelDistrLoadsLayout
                                                        .createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(jB_DistrLoadsDelete)
                                                        .addGap(2, 2, 2)
                                                )
                                                .addGroup(
                                                    jLateralPanelDistrLoadsLayout
                                                        .createSequentialGroup()
                                                        .addComponent(jLabelDistrLoadsTitle)
                                                        .addPreferredGap(
                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            Short.MAX_VALUE
                                                        )
                                                )
                                        )
                                        .addGroup(
                                            jLateralPanelDistrLoadsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                    jLateralPanelDistrLoadsLayout
                                                        .createSequentialGroup()
                                                        .addComponent(jB_DistrLoadsSave)
                                                        .addGap(2, 2, 2)
                                                        .addComponent(jB_DistrLoadsEdit)
                                                )
                                                .addComponent(
                                                    jCB_DistrLoadsLoad,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                        )
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralPanelDistrLoadsLayout.setVerticalGroup(
            jLateralPanelDistrLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelDistrLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelDistrLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelDistrLoadsTitle,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_DistrLoadsLoad,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelDistrLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelDistrLoadsA,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_DistrLoadsQx,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelDistrLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelDistrLoadsB,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_DistrLoadsQy,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(
                            jLateralPanelDistrLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_DistrLoadsEdit)
                                .addComponent(jB_DistrLoadsSave)
                                .addComponent(jB_DistrLoadsDelete)
                        )
                        .addContainerGap()
                )
        );

        jLateralPanelAxialLoads.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelAxialLoads.setMinimumSize(new java.awt.Dimension(199, 120));
        jLateralPanelAxialLoads.setPreferredSize(new java.awt.Dimension(199, 120));

        jLabelAxialLoadsTitle.setText("Loads");

        jLabelAxialLoadsA.setText("N (KN/m)");

        jCB_AxialLoadsLoad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<New>" }));
        jCB_AxialLoadsLoad.setFocusable(false);
        jCB_AxialLoadsLoad.setPreferredSize(new java.awt.Dimension(70, 20));

        jB_AxialLoadsSave.setText("Save");
        jB_AxialLoadsSave.setEnabled(false);
        jB_AxialLoadsSave.setFocusable(false);

        jB_AxialLoadsEdit.setText("Edit");
        jB_AxialLoadsEdit.setFocusable(false);

        jB_AxialLoadsDelete.setText("Delete");
        jB_AxialLoadsDelete.setFocusable(false);

        jTF_AxialLoadsN.setEnabled(false);
        jTF_AxialLoadsN.setPreferredSize(new java.awt.Dimension(70, 20));

        javax.swing.GroupLayout jLateralPanelAxialLoadsLayout = new javax.swing.GroupLayout(jLateralPanelAxialLoads);
        jLateralPanelAxialLoads.setLayout(jLateralPanelAxialLoadsLayout);
        jLateralPanelAxialLoadsLayout.setHorizontalGroup(
            jLateralPanelAxialLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelAxialLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelAxialLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralPanelAxialLoadsLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelAxialLoadsTitle)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jCB_AxialLoadsLoad,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelAxialLoadsLayout
                                        .createSequentialGroup()
                                        .addGap(0, 4, Short.MAX_VALUE)
                                        .addComponent(jB_AxialLoadsDelete)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_AxialLoadsSave)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_AxialLoadsEdit)
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelAxialLoadsLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelAxialLoadsA)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jTF_AxialLoadsN,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralPanelAxialLoadsLayout.setVerticalGroup(
            jLateralPanelAxialLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelAxialLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelAxialLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelAxialLoadsTitle,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_AxialLoadsLoad,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelAxialLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelAxialLoadsA,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_AxialLoadsN,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(
                            jLateralPanelAxialLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_AxialLoadsEdit)
                                .addComponent(jB_AxialLoadsSave)
                                .addComponent(jB_AxialLoadsDelete)
                        )
                        .addContainerGap()
                )
        );

        jLateralPanelPlanarLoads.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelPlanarLoads.setMinimumSize(new java.awt.Dimension(199, 120));
        jLateralPanelPlanarLoads.setPreferredSize(new java.awt.Dimension(199, 120));

        jLabelPlanarLoadsTitle.setText("Loads");

        jLabelPlanarLoadsA0.setText("Qz (KN/m");

        jLabelPlanarLoadsA1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabelPlanarLoadsA1.setText("2");
        jLabelPlanarLoadsA1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabelPlanarLoadsA2.setText(")");

        jCB_PlanarLoadsLoad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<New>" }));
        jCB_PlanarLoadsLoad.setFocusable(false);
        jCB_PlanarLoadsLoad.setPreferredSize(new java.awt.Dimension(70, 20));

        jB_PlanarLoadsSave.setText("Save");
        jB_PlanarLoadsSave.setEnabled(false);
        jB_PlanarLoadsSave.setFocusable(false);

        jB_PlanarLoadsEdit.setText("Edit");
        jB_PlanarLoadsEdit.setFocusable(false);

        jB_PlanarLoadsDelete.setText("Delete");
        jB_PlanarLoadsDelete.setFocusable(false);

        jTF_PlanarLoadsQz.setEnabled(false);
        jTF_PlanarLoadsQz.setPreferredSize(new java.awt.Dimension(70, 20));

        javax.swing.GroupLayout jLateralPanelPlanarLoadsLayout = new javax.swing.GroupLayout(jLateralPanelPlanarLoads);
        jLateralPanelPlanarLoads.setLayout(jLateralPanelPlanarLoadsLayout);
        jLateralPanelPlanarLoadsLayout.setHorizontalGroup(
            jLateralPanelPlanarLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelPlanarLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelPlanarLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralPanelPlanarLoadsLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelPlanarLoadsA0)
                                        .addGap(0, 0, 0)
                                        .addComponent(jLabelPlanarLoadsA1)
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabelPlanarLoadsA2)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jTF_PlanarLoadsQz,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelPlanarLoadsLayout
                                        .createSequentialGroup()
                                        .addGap(0, 4, Short.MAX_VALUE)
                                        .addComponent(jB_PlanarLoadsDelete)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_PlanarLoadsSave)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_PlanarLoadsEdit)
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelPlanarLoadsLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelPlanarLoadsTitle)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jCB_PlanarLoadsLoad,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralPanelPlanarLoadsLayout.setVerticalGroup(
            jLateralPanelPlanarLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelPlanarLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelPlanarLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelPlanarLoadsTitle,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_PlanarLoadsLoad,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelPlanarLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralPanelPlanarLoadsLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(
                                            jLabelPlanarLoadsA0,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            25,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                            jLabelPlanarLoadsA2,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            25,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                            jTF_PlanarLoadsQz,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addComponent(
                                    jLabelPlanarLoadsA1,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(
                            jLateralPanelPlanarLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_PlanarLoadsEdit)
                                .addComponent(jB_PlanarLoadsSave)
                                .addComponent(jB_PlanarLoadsDelete)
                        )
                        .addContainerGap()
                )
        );

        jLateralPanelThermalLoads.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelThermalLoads.setMinimumSize(new java.awt.Dimension(199, 180));
        jLateralPanelThermalLoads.setPreferredSize(new java.awt.Dimension(199, 180));

        jLabelThermalLoadsTitle.setText("Loads");

        jLabelThermalLoadsTtop.setText("Ttop (ºC)");

        jLabelThermalLoadsTbot.setText("Tbot (ºC)");

        jCB_ThermalLoads.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<New>" }));
        jCB_ThermalLoads.setFocusable(false);
        jCB_ThermalLoads.setPreferredSize(new java.awt.Dimension(70, 20));

        jB_ThermalLoadsSave.setText("Save");
        jB_ThermalLoadsSave.setEnabled(false);
        jB_ThermalLoadsSave.setFocusable(false);

        jB_ThermalLoadsEdit.setText("Edit");
        jB_ThermalLoadsEdit.setFocusable(false);

        jB_ThermalLoadsDelete.setText("Delete");
        jB_ThermalLoadsDelete.setFocusable(false);

        jTF_ThermalLoadsTtop.setEnabled(false);
        jTF_ThermalLoadsTtop.setPreferredSize(new java.awt.Dimension(70, 20));

        jTF_ThermalLoadsTbot.setEnabled(false);
        jTF_ThermalLoadsTbot.setPreferredSize(new java.awt.Dimension(70, 20));

        jTF_ThermalLoadsTzero.setEnabled(false);
        jTF_ThermalLoadsTzero.setPreferredSize(new java.awt.Dimension(70, 20));

        jLabelThermalLoadsTzero.setText("ΔT (ºC)");

        javax.swing.GroupLayout jLateralPanelThermalLoadsLayout = new javax.swing.GroupLayout(jLateralPanelThermalLoads);
        jLateralPanelThermalLoads.setLayout(jLateralPanelThermalLoadsLayout);
        jLateralPanelThermalLoadsLayout.setHorizontalGroup(
            jLateralPanelThermalLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelThermalLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelThermalLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelThermalLoadsLayout
                                        .createSequentialGroup()
                                        .addGroup(
                                            jLateralPanelThermalLoadsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabelThermalLoadsTitle)
                                                .addComponent(jLabelThermalLoadsTzero)
                                        )
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addGroup(
                                            jLateralPanelThermalLoadsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(
                                                    jTF_ThermalLoadsTzero,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                                .addComponent(
                                                    jCB_ThermalLoads,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                        )
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelThermalLoadsLayout
                                        .createSequentialGroup()
                                        .addGap(0, 4, Short.MAX_VALUE)
                                        .addComponent(jB_ThermalLoadsDelete)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_ThermalLoadsSave)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_ThermalLoadsEdit)
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelThermalLoadsLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelThermalLoadsTtop)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jTF_ThermalLoadsTtop,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelThermalLoadsLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelThermalLoadsTbot)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jTF_ThermalLoadsTbot,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralPanelThermalLoadsLayout.setVerticalGroup(
            jLateralPanelThermalLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelThermalLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelThermalLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelThermalLoadsTitle,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_ThermalLoads,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelThermalLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelThermalLoadsTzero,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_ThermalLoadsTzero,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelThermalLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelThermalLoadsTtop,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_ThermalLoadsTtop,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelThermalLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelThermalLoadsTbot,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_ThermalLoadsTbot,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(
                            jLateralPanelThermalLoadsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_ThermalLoadsEdit)
                                .addComponent(jB_ThermalLoadsSave)
                                .addComponent(jB_ThermalLoadsDelete)
                        )
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        jLateralPanelSelfWeight.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelSelfWeight.setPreferredSize(new java.awt.Dimension(199, 120));

        jB_SelfWeightEdit.setText("Edit");
        jB_SelfWeightEdit.setFocusable(false);

        jB_SelfWeightSave.setText("Save");
        jB_SelfWeightSave.setEnabled(false);
        jB_SelfWeightSave.setFocusable(false);

        jB_SelfWeightDelete.setText("Delete");
        jB_SelfWeightDelete.setFocusable(false);

        jTF_SelfWeight.setEnabled(false);
        jTF_SelfWeight.setPreferredSize(new java.awt.Dimension(70, 20));

        jCB_SelfWeight.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<New>" }));
        jCB_SelfWeight.setFocusable(false);
        jCB_SelfWeight.setPreferredSize(new java.awt.Dimension(70, 20));

        jLabelSelfWeightTitle.setText("Densities");

        jLabelSelfWeightA0.setText("γ (KN/m");

        jLabelSelfWeightA1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabelSelfWeightA1.setText("3");
        jLabelSelfWeightA1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabelSelfWeightA2.setText(")");

        javax.swing.GroupLayout jLateralPanelSelfWeightLayout = new javax.swing.GroupLayout(jLateralPanelSelfWeight);
        jLateralPanelSelfWeight.setLayout(jLateralPanelSelfWeightLayout);
        jLateralPanelSelfWeightLayout.setHorizontalGroup(
            jLateralPanelSelfWeightLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelSelfWeightLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelSelfWeightLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralPanelSelfWeightLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelSelfWeightA0)
                                        .addGap(0, 0, 0)
                                        .addComponent(jLabelSelfWeightA1)
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabelSelfWeightA2)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jTF_SelfWeight,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelSelfWeightLayout
                                        .createSequentialGroup()
                                        .addGap(0, 4, Short.MAX_VALUE)
                                        .addComponent(jB_SelfWeightDelete)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_SelfWeightSave)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_SelfWeightEdit)
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelSelfWeightLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelSelfWeightTitle)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jCB_SelfWeight,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralPanelSelfWeightLayout.setVerticalGroup(
            jLateralPanelSelfWeightLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelSelfWeightLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelSelfWeightLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelSelfWeightTitle,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_SelfWeight,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelSelfWeightLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralPanelSelfWeightLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(
                                            jLabelSelfWeightA0,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            25,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                            jLabelSelfWeightA2,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            25,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                            jTF_SelfWeight,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addComponent(
                                    jLabelSelfWeightA1,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(
                            jLateralPanelSelfWeightLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_SelfWeightEdit)
                                .addComponent(jB_SelfWeightSave)
                                .addComponent(jB_SelfWeightDelete)
                        )
                        .addContainerGap()
                )
        );

        setBackground(new java.awt.Color(236, 236, 236));
        setPreferredSize(new java.awt.Dimension(200, 350));

        jPanelContainer.setBackground(new java.awt.Color(236, 236, 236));

        jPanelConLoads.setBackground(new java.awt.Color(236, 236, 236));
        jPanelConLoads.setName("jPanelConLoads"); // NOI18N
        jPanelConLoads.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelConLoadsMousePressed(evt);
                }
            }
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setText("Concentrated Loads");

        javax.swing.GroupLayout jPanelConLoadsLayout = new javax.swing.GroupLayout(jPanelConLoads);
        jPanelConLoads.setLayout(jPanelConLoadsLayout);
        jPanelConLoadsLayout.setHorizontalGroup(
            jPanelConLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelConLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelConLoadsLayout.setVerticalGroup(
            jPanelConLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelConLoads_Container.setBackground(new java.awt.Color(236, 236, 236));

        javax.swing.GroupLayout jPanelConLoads_ContainerLayout = new javax.swing.GroupLayout(jPanelConLoads_Container);
        jPanelConLoads_Container.setLayout(jPanelConLoads_ContainerLayout);
        jPanelConLoads_ContainerLayout.setHorizontalGroup(
            jPanelConLoads_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelConLoads_ContainerLayout.setVerticalGroup(
            jPanelConLoads_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelMoments.setBackground(new java.awt.Color(236, 236, 236));
        jPanelMoments.setName("jPanelMoments"); // NOI18N
        jPanelMoments.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelMomentsMousePressed(evt);
                }
            }
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setText("Bending Moments");

        javax.swing.GroupLayout jPanelMomentsLayout = new javax.swing.GroupLayout(jPanelMoments);
        jPanelMoments.setLayout(jPanelMomentsLayout);
        jPanelMomentsLayout.setHorizontalGroup(
            jPanelMomentsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelMomentsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelMomentsLayout.setVerticalGroup(
            jPanelMomentsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelMoments_Container.setBackground(new java.awt.Color(236, 236, 236));

        javax.swing.GroupLayout jPanelMoments_ContainerLayout = new javax.swing.GroupLayout(jPanelMoments_Container);
        jPanelMoments_Container.setLayout(jPanelMoments_ContainerLayout);
        jPanelMoments_ContainerLayout.setHorizontalGroup(
            jPanelMoments_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelMoments_ContainerLayout.setVerticalGroup(
            jPanelMoments_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelDistrLoads.setBackground(new java.awt.Color(236, 236, 236));
        jPanelDistrLoads.setName("jPanelDistrLoads"); // NOI18N
        jPanelDistrLoads.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelDistrLoadsMousePressed(evt);
                }
            }
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setText("Uniformly Distributed Loads");

        javax.swing.GroupLayout jPanelDistrLoadsLayout = new javax.swing.GroupLayout(jPanelDistrLoads);
        jPanelDistrLoads.setLayout(jPanelDistrLoadsLayout);
        jPanelDistrLoadsLayout.setHorizontalGroup(
            jPanelDistrLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelDistrLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelDistrLoadsLayout.setVerticalGroup(
            jPanelDistrLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelDistrLoads_Container.setBackground(new java.awt.Color(236, 236, 236));

        javax.swing.GroupLayout jPanelDistrLoads_ContainerLayout = new javax.swing.GroupLayout(jPanelDistrLoads_Container);
        jPanelDistrLoads_Container.setLayout(jPanelDistrLoads_ContainerLayout);
        jPanelDistrLoads_ContainerLayout.setHorizontalGroup(
            jPanelDistrLoads_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelDistrLoads_ContainerLayout.setVerticalGroup(
            jPanelDistrLoads_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelAxialLoads.setBackground(new java.awt.Color(236, 236, 236));
        jPanelAxialLoads.setName("jPanelAxialLoads"); // NOI18N
        jPanelAxialLoads.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelAxialLoadsMousePressed(evt);
                }
            }
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setText("Distributed Axial Loads");

        javax.swing.GroupLayout jPanelAxialLoadsLayout = new javax.swing.GroupLayout(jPanelAxialLoads);
        jPanelAxialLoads.setLayout(jPanelAxialLoadsLayout);
        jPanelAxialLoadsLayout.setHorizontalGroup(
            jPanelAxialLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelAxialLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelAxialLoadsLayout.setVerticalGroup(
            jPanelAxialLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelAxialLoads_Container.setBackground(new java.awt.Color(236, 236, 236));

        javax.swing.GroupLayout jPanelAxialLoads_ContainerLayout = new javax.swing.GroupLayout(jPanelAxialLoads_Container);
        jPanelAxialLoads_Container.setLayout(jPanelAxialLoads_ContainerLayout);
        jPanelAxialLoads_ContainerLayout.setHorizontalGroup(
            jPanelAxialLoads_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelAxialLoads_ContainerLayout.setVerticalGroup(
            jPanelAxialLoads_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelPlanarLoads.setBackground(new java.awt.Color(236, 236, 236));
        jPanelPlanarLoads.setName("jPanelPlanarLoads"); // NOI18N
        jPanelPlanarLoads.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelPlanarLoadsMousePressed(evt);
                }
            }
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel5.setText("Uniform Planar Loads");

        javax.swing.GroupLayout jPanelPlanarLoadsLayout = new javax.swing.GroupLayout(jPanelPlanarLoads);
        jPanelPlanarLoads.setLayout(jPanelPlanarLoadsLayout);
        jPanelPlanarLoadsLayout.setHorizontalGroup(
            jPanelPlanarLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelPlanarLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelPlanarLoadsLayout.setVerticalGroup(
            jPanelPlanarLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(
                    jLabel5,
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    25,
                    Short.MAX_VALUE
                )
        );

        jPanelPlanarLoads_Container.setBackground(new java.awt.Color(236, 236, 236));
        jPanelPlanarLoads_Container.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanelPlanarLoads_ContainerLayout = new javax.swing.GroupLayout(jPanelPlanarLoads_Container);
        jPanelPlanarLoads_Container.setLayout(jPanelPlanarLoads_ContainerLayout);
        jPanelPlanarLoads_ContainerLayout.setHorizontalGroup(
            jPanelPlanarLoads_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelPlanarLoads_ContainerLayout.setVerticalGroup(
            jPanelPlanarLoads_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelThermalLoads.setBackground(new java.awt.Color(236, 236, 236));
        jPanelThermalLoads.setName("jPanelThermalLoads"); // NOI18N
        jPanelThermalLoads.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelThermalLoadsMousePressed(evt);
                }
            }
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel7.setText("Thermal Loads");

        javax.swing.GroupLayout jPanelThermalLoadsLayout = new javax.swing.GroupLayout(jPanelThermalLoads);
        jPanelThermalLoads.setLayout(jPanelThermalLoadsLayout);
        jPanelThermalLoadsLayout.setHorizontalGroup(
            jPanelThermalLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelThermalLoadsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelThermalLoadsLayout.setVerticalGroup(
            jPanelThermalLoadsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelThermalLoads_Container.setBackground(new java.awt.Color(236, 236, 236));
        jPanelThermalLoads_Container.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanelThermalLoads_ContainerLayout = new javax.swing.GroupLayout(jPanelThermalLoads_Container);
        jPanelThermalLoads_Container.setLayout(jPanelThermalLoads_ContainerLayout);
        jPanelThermalLoads_ContainerLayout.setHorizontalGroup(
            jPanelThermalLoads_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelThermalLoads_ContainerLayout.setVerticalGroup(
            jPanelThermalLoads_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelSelfWeight.setBackground(new java.awt.Color(236, 236, 236));
        jPanelSelfWeight.setName("jPanelSelfWeight"); // NOI18N
        jPanelSelfWeight.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelSelfWeightMousePressed(evt);
                }
            }
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setText("Self-Weight");

        javax.swing.GroupLayout jPanelSelfWeightLayout = new javax.swing.GroupLayout(jPanelSelfWeight);
        jPanelSelfWeight.setLayout(jPanelSelfWeightLayout);
        jPanelSelfWeightLayout.setHorizontalGroup(
            jPanelSelfWeightLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelSelfWeightLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelSelfWeightLayout.setVerticalGroup(
            jPanelSelfWeightLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelSelfWeight_Container.setBackground(new java.awt.Color(236, 236, 236));
        jPanelSelfWeight_Container.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanelSelfWeight_ContainerLayout = new javax.swing.GroupLayout(jPanelSelfWeight_Container);
        jPanelSelfWeight_Container.setLayout(jPanelSelfWeight_ContainerLayout);
        jPanelSelfWeight_ContainerLayout.setHorizontalGroup(
            jPanelSelfWeight_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelSelfWeight_ContainerLayout.setVerticalGroup(
            jPanelSelfWeight_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelBottom.setBackground(new java.awt.Color(236, 236, 236));

        jButtonClose.setText("Close");
        jButtonClose.setFocusPainted(false);

        javax.swing.GroupLayout jPanelBottomLayout = new javax.swing.GroupLayout(jPanelBottom);
        jPanelBottom.setLayout(jPanelBottomLayout);
        jPanelBottomLayout.setHorizontalGroup(
            jPanelBottomLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jPanelBottomLayout
                        .createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClose)
                        .addContainerGap()
                )
        );
        jPanelBottomLayout.setVerticalGroup(
            jPanelBottomLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    javax.swing.GroupLayout.Alignment.TRAILING,
                    jPanelBottomLayout
                        .createSequentialGroup()
                        .addContainerGap(292, Short.MAX_VALUE)
                        .addComponent(jButtonClose)
                        .addContainerGap()
                )
        );

        javax.swing.GroupLayout jPanelContainerLayout = new javax.swing.GroupLayout(jPanelContainer);
        jPanelContainer.setLayout(jPanelContainerLayout);
        jPanelContainerLayout.setHorizontalGroup(
            jPanelContainerLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelConLoads, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(
                    jPanelConLoads_Container,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(jPanelMoments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(
                    jPanelMoments_Container,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(jPanelDistrLoads, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(
                    jPanelDistrLoads_Container,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(jPanelAxialLoads, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(
                    jPanelAxialLoads_Container,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(
                    jPanelPlanarLoads,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(jPanelPlanarLoads_Container, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addComponent(jPanelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelSelfWeight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelSelfWeight_Container, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addComponent(
                    jPanelThermalLoads,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(jPanelThermalLoads_Container, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
        );
        jPanelContainerLayout.setVerticalGroup(
            jPanelContainerLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelContainerLayout
                        .createSequentialGroup()
                        .addComponent(
                            jPanelConLoads,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelConLoads_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelMoments,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelMoments_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelDistrLoads,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelDistrLoads_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelAxialLoads,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelAxialLoads_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelPlanarLoads,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelPlanarLoads_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelThermalLoads,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelThermalLoads_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelSelfWeight,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelSelfWeight_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelBottom,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE
                        )
                )
        );

        jPanelSeparator.setBackground(new java.awt.Color(222, 222, 222));
        jPanelSeparator.setAlignmentX(0.0F);
        jPanelSeparator.setAlignmentY(0.0F);
        jPanelSeparator.setPreferredSize(new java.awt.Dimension(1, 0));

        javax.swing.GroupLayout jPanelSeparatorLayout = new javax.swing.GroupLayout(jPanelSeparator);
        jPanelSeparator.setLayout(jPanelSeparatorLayout);
        jPanelSeparatorLayout.setHorizontalGroup(
            jPanelSeparatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1, Short.MAX_VALUE)
        );
        jPanelSeparatorLayout.setVerticalGroup(
            jPanelSeparatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout
                        .createSequentialGroup()
                        .addComponent(
                            jPanelContainer,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelSeparator,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                )
        );
        layout.setVerticalGroup(
            layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
    } // </editor-fold>//GEN-END:initComponents

    /**
     * Métdodo para definir o conteúdo do painel em função do modelo estrutural
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
        setUnits();
        objectsOfLateralPanel();
    }

    /**
     * Método para abrir o painel lateral jPanelConLoads
     */
    public void jPanelConLoadsMousePressed() {
        jPanelConLoadsMousePressed(null);
    }

    /**
     * Método para abrir o painel lateral jPanelMoments
     */
    public void jPanelMomentsMousePressed() {
        jPanelMomentsMousePressed(null);
    }

    /**
     * Método para abrir o painel lateral jPanelDistrLoads
     */
    public void jPanelDistrLoadsMousePressed() {
        jPanelDistrLoadsMousePressed(null);
    }

    /**
     * Método para abrir o painel lateral jPanelAxialLoads
     */
    public void jPanelAxialLoadsMousePressed() {
        jPanelAxialLoadsMousePressed(null);
    }

    /**
     * Método para abrir o painel lateral jPanelPlanarLoads
     */
    public void jPanelPlanarLoadsMousePressed() {
        jPanelPlanarLoadsMousePressed(null);
    }

    /**
     * Método para abrir o painel lateral jPanelThermalLoads
     */
    public void jPanelThermalLoadsMousePressed() {
        jPanelThermalLoadsMousePressed(null);
    }

    /**
     * Método para abrir o painel lateral jPanelPlanarLoads
     */
    public void jPanelSelfWeightMousePressed() {
        jPanelSelfWeightMousePressed(null);
    }

    /**
     * Método para alterar os modelos das ComboBox
     *
     * @param modelConLoads
     * @param modelMomentsLoad
     * @param modelDistrLoads
     * @param modelAxialLoads
     * @param modelPlanarLoads
     * @param modelThermalLoads
     * @param modelSelfWeight
     */
    public void updateAllModels(
        String[] modelConLoads,
        String[] modelMomentsLoad,
        String[] modelDistrLoads,
        String[] modelAxialLoads,
        String[] modelPlanarLoads,
        String[] modelThermalLoads,
        String[] modelSelfWeight
    ) {
        setModeljCB_ConLoads(modelConLoads);
        setModeljCB_MomentsLoad(modelMomentsLoad);
        setModeljCB_DistrLoads(modelDistrLoads);
        setModeljCB_AxialLoads(modelAxialLoads);
        setModeljCB_PlanarLoads(modelPlanarLoads);
        setModeljCB_ThermalLoads(modelThermalLoads);
        setModeljCB_SelfWeight(modelSelfWeight);
    }

    /**
     * Método para alterar o modelo da ComboBox jCB_ConLoads
     *
     * @param model
     */
    public void setModeljCB_ConLoads(String[] model) {
        int lenght = model.length;
        String[] newModel = new String[lenght + 1];
        System.arraycopy(model, 0, newModel, 0, lenght);
        newModel[lenght] = "<New>";

        jCB_ConLoadsLoad.setModel(new javax.swing.DefaultComboBoxModel(newModel));
    }

    /**
     * Método para alterar o modelo da ComboBox jCB_MomentsLoad
     *
     * @param model
     */
    public void setModeljCB_MomentsLoad(String[] model) {
        int lenght = model.length;
        String[] newModel = new String[lenght + 1];
        System.arraycopy(model, 0, newModel, 0, lenght);
        newModel[lenght] = "<New>";

        jCB_MomentsLoad.setModel(new javax.swing.DefaultComboBoxModel(newModel));
    }

    /**
     * Método para alterar o modelo da ComboBox jCB_DistrLoads
     *
     * @param model
     */
    public void setModeljCB_DistrLoads(String[] model) {
        int lenght = model.length;
        String[] newModel = new String[lenght + 1];
        System.arraycopy(model, 0, newModel, 0, lenght);
        newModel[lenght] = "<New>";

        jCB_DistrLoadsLoad.setModel(new javax.swing.DefaultComboBoxModel(newModel));
    }

    /**
     * Método para alterar o modelo da ComboBox jCB_AxialLoads
     *
     * @param model
     */
    public void setModeljCB_AxialLoads(String[] model) {
        int lenght = model.length;
        String[] newModel = new String[lenght + 1];
        System.arraycopy(model, 0, newModel, 0, lenght);
        newModel[lenght] = "<New>";

        jCB_AxialLoadsLoad.setModel(new javax.swing.DefaultComboBoxModel(newModel));
    }

    /**
     * Método para alterar o modelo da ComboBox jCB_PlanarLoads
     *
     * @param model
     */
    public void setModeljCB_PlanarLoads(String[] model) {
        int lenght = model.length;
        String[] newModel = new String[lenght + 1];
        System.arraycopy(model, 0, newModel, 0, lenght);
        newModel[lenght] = "<New>";

        jCB_PlanarLoadsLoad.setModel(new javax.swing.DefaultComboBoxModel(newModel));
    }

    /**
     * Método para alterar o modelo da ComboBox jCB_ThermalLoads
     *
     * @param model
     */
    public void setModeljCB_ThermalLoads(String[] model) {
        int lenght = model.length;
        String[] newModel = new String[lenght + 1];
        System.arraycopy(model, 0, newModel, 0, lenght);
        newModel[lenght] = "<New>";

        jCB_ThermalLoads.setModel(new javax.swing.DefaultComboBoxModel(newModel));
    }

    /**
     * Método para alterar o modelo da ComboBox jCB_PlanarLoads
     *
     * @param model
     */
    public void setModeljCB_SelfWeight(String[] model) {
        int lenght = model.length;
        String[] newModel = new String[lenght + 1];
        System.arraycopy(model, 0, newModel, 0, lenght);
        newModel[lenght] = "<New>";

        jCB_SelfWeight.setModel(new javax.swing.DefaultComboBoxModel(newModel));
    }

    /**
     * Método para abrir o painel lateral jPanelConLoads
     *
     * @param evt
     */
    private void jPanelConLoadsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelConLoadsMousePressed
        Dimension dimension = jLateralPanelConLoads.getMinimumSize();
        jPanelConLoads.setBackground(selectedColor);
        jPanelActivated = "jPanelConLoads";
        jPanelActivated();

        jPanelConLoads_Container.removeAll();
        jPanelConLoads_Container.setSize(dimension);
        jPanelConLoads_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelConLoads_Container.setLayout(new BorderLayout());
        jPanelConLoads_Container.add(jLateralPanelConLoads);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelConLoadsMousePressed

    /**
     * Método para abrir o painel lateral jPanelMoments
     *
     * @param evt
     */
    private void jPanelMomentsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelMomentsMousePressed
        Dimension dimension = jLateralPanelMoments.getMinimumSize();
        jPanelMoments.setBackground(selectedColor);
        jPanelActivated = "jPanelMoments";
        jPanelActivated();

        jPanelMoments_Container.removeAll();
        jPanelMoments_Container.setSize(dimension);
        jPanelMoments_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelMoments_Container.setLayout(new BorderLayout());
        jPanelMoments_Container.add(jLateralPanelMoments);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelMomentsMousePressed

    /**
     * Método para abrir o painel lateral jPanelDistrLoads
     *
     * @param evt
     */
    private void jPanelDistrLoadsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelDistrLoadsMousePressed
        Dimension dimension = jLateralPanelDistrLoads.getMinimumSize();
        jPanelDistrLoads.setBackground(selectedColor);
        jPanelActivated = "jPanelDistrLoads";
        jPanelActivated();

        jPanelDistrLoads_Container.removeAll();
        jPanelDistrLoads_Container.setSize(dimension);
        jPanelDistrLoads_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelDistrLoads_Container.setLayout(new BorderLayout());
        jPanelDistrLoads_Container.add(jLateralPanelDistrLoads);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelDistrLoadsMousePressed

    /**
     * Método para abrir o painel lateral jPanelAxialLoads
     *
     * @param evt
     */
    private void jPanelAxialLoadsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelAxialLoadsMousePressed
        Dimension dimension = jLateralPanelAxialLoads.getMinimumSize();
        jPanelAxialLoads.setBackground(selectedColor);
        jPanelActivated = "jPanelAxialLoads";
        jPanelActivated();

        jPanelAxialLoads_Container.removeAll();
        jPanelAxialLoads_Container.setSize(dimension);
        jPanelAxialLoads_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelAxialLoads_Container.setLayout(new BorderLayout());
        jPanelAxialLoads_Container.add(jLateralPanelAxialLoads);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelAxialLoadsMousePressed

    /**
     * Método para abrir o painel lateral jPanelPlanarLoads
     *
     * @param evt
     */
    private void jPanelPlanarLoadsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelPlanarLoadsMousePressed
        Dimension dimension = jLateralPanelPlanarLoads.getMinimumSize();
        jPanelPlanarLoads.setBackground(selectedColor);
        jPanelActivated = "jPanelPlanarLoads";
        jPanelActivated();

        jPanelPlanarLoads_Container.removeAll();
        jPanelPlanarLoads_Container.setSize(dimension);
        jPanelPlanarLoads_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelPlanarLoads_Container.setLayout(new BorderLayout());
        jPanelPlanarLoads_Container.add(jLateralPanelPlanarLoads);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelPlanarLoadsMousePressed

    private void jPanelSelfWeightMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelSelfWeightMousePressed
        Dimension dimension = jLateralPanelSelfWeight.getMinimumSize();
        jPanelSelfWeight.setBackground(selectedColor);
        jPanelActivated = "jPanelSelfWeight";
        jPanelActivated();

        jPanelSelfWeight_Container.removeAll();
        jPanelSelfWeight_Container.setSize(dimension);
        jPanelSelfWeight_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelSelfWeight_Container.setLayout(new BorderLayout());
        jPanelSelfWeight_Container.add(jLateralPanelSelfWeight);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelSelfWeightMousePressed

    private void jPanelThermalLoadsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelThermalLoadsMousePressed
        Dimension dimension = jLateralPanelThermalLoads.getMinimumSize();
        jPanelThermalLoads.setBackground(selectedColor);
        jPanelActivated = "jPanelThermalLoads";
        jPanelActivated();

        jPanelThermalLoads_Container.removeAll();
        jPanelThermalLoads_Container.setSize(dimension);
        jPanelThermalLoads_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelThermalLoads_Container.setLayout(new BorderLayout());
        jPanelThermalLoads_Container.add(jLateralPanelThermalLoads);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelThermalLoadsMousePressed

    /**
     * Classe privada para adicionar eventos do rato aos painéis
     */
    private class JPanelMouseEvents extends javax.swing.JPanel {

        private JPanelMouseEvents() {
            addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        jPanelMouseEntered(evt);
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        jPanelMouseExited(evt);
                    }
                }
            );
        }

        private void jPanelMouseEntered(java.awt.event.MouseEvent evt) {
            String jPanelName;
            jPanelName = getName();

            if (jPanelActivated == null ? jPanelName != null : !jPanelActivated.equals(jPanelName)) {
                setBackground(enteredColor);
            }
        }

        private void jPanelMouseExited(java.awt.event.MouseEvent evt) {
            String jPanelName;
            jPanelName = getName();

            if (jPanelActivated == null ? jPanelName != null : !jPanelActivated.equals(jPanelName)) {
                setBackground(notSelectedColor);
            }
        }
    }

    /**
     * Método para alterar o estado dos painéis não selecionados
     */
    private void jPanelActivated() {
        if (!"jPanelConLoads".equals(jPanelActivated)) {
            jPanelConLoads.setBackground(notSelectedColor);
        }
        if (!"jPanelMoments".equals(jPanelActivated)) {
            jPanelMoments.setBackground(notSelectedColor);
        }
        if (!"jPanelDistrLoads".equals(jPanelActivated)) {
            jPanelDistrLoads.setBackground(notSelectedColor);
        }
        if (!"jPanelAxialLoads".equals(jPanelActivated)) {
            jPanelAxialLoads.setBackground(notSelectedColor);
        }
        if (!"jPanelPlanarLoads".equals(jPanelActivated)) {
            jPanelPlanarLoads.setBackground(notSelectedColor);
        }
        if (!"jPanelThermalLoads".equals(jPanelActivated)) {
            jPanelThermalLoads.setBackground(notSelectedColor);
        }
        if (!"jPanelSelfWeight".equals(jPanelActivated)) {
            jPanelSelfWeight.setBackground(notSelectedColor);
        }

        jPanelActivated(jPanelActivated);
        jPanelContainer.updateUI();
    }

    /**
     * Método para alterar a dimensão dos painéis selecionados
     *
     * @param jPanelName é o nome do painel selecionado
     */
    private void jPanelActivated(String jPanelName) {
        if (!"jPanelConLoads".equals(jPanelName)) {
            jPanelConLoads_Container.removeAll();
            jPanelConLoads_Container.setSize(199, 0);
            jPanelConLoads_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
        if (!"jPanelMoments".equals(jPanelName)) {
            jPanelMoments_Container.removeAll();
            jPanelMoments_Container.setSize(199, 0);
            jPanelMoments_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
        if (!"jPanelDistrLoads".equals(jPanelName)) {
            jPanelDistrLoads_Container.removeAll();
            jPanelDistrLoads_Container.setSize(199, 0);
            jPanelDistrLoads_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
        if (!"jPanelAxialLoads".equals(jPanelName)) {
            jPanelAxialLoads_Container.removeAll();
            jPanelAxialLoads_Container.setSize(199, 0);
            jPanelAxialLoads_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
        if (!"jPanelPlanarLoads".equals(jPanelName)) {
            jPanelPlanarLoads_Container.removeAll();
            jPanelPlanarLoads_Container.setSize(199, 0);
            jPanelPlanarLoads_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
        if (!"jPanelThermalLoads".equals(jPanelName)) {
            jPanelThermalLoads_Container.removeAll();
            jPanelThermalLoads_Container.setSize(199, 0);
            jPanelThermalLoads_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
        if (!"jPanelSelfWeight".equals(jPanelName)) {
            jPanelSelfWeight_Container.removeAll();
            jPanelSelfWeight_Container.setSize(199, 0);
            jPanelSelfWeight_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
    }

    /**
     * Método para definir as unidades das cargas estruturais
     */
    private void setUnits() {
        if (null != type) switch (type) {
            case "2D":
                jLabelConLoadsA.setText("Fx (KN/m)");
                jLabelConLoadsB.setText("Fy (KN/m)");
                jLabelDistrLoadsA.setText("Qx (KN/m/m)");
                jLabelDistrLoadsB.setText("Qy (KN/m/m)");
                break;
            case "Beams":
                jLabelConLoadsA.setText("Fx (KN)");
                jLabelConLoadsB.setText("Fz (KN)");
                jLabelDistrLoadsA.setText("Qx (KN/m)");
                jLabelDistrLoadsB.setText("Qz (KN/m)");
                break;
            case "Grids":
                jLabelConLoadsA.setText("Fx (KN)");
                jLabelConLoadsB.setText("Fz (KN)");
                jLabelDistrLoadsA.setText("Qx (KN/m)");
                jLabelDistrLoadsB.setText("Qz (KN/m)");
                break;
            case "Slabs":
                jLabelConLoadsA.setText("Fx (KN)");
                jLabelConLoadsB.setText("Fz (KN)");
                break;
            default:
                jLabelConLoadsA.setText("Fx (KN)");
                jLabelConLoadsB.setText("Fy (KN)");
                jLabelDistrLoadsA.setText("Qx (KN/m)");
                jLabelDistrLoadsB.setText("Qy (KN/m)");
                break;
        }
    }

    /**
     * Método para definir o estado dos objetos da classe
     */
    private void objectsOfLateralPanel() {
        jCB_ConLoadsLoad.setEnabled(false);
        jCB_MomentsLoad.setEnabled(false);
        jCB_DistrLoadsLoad.setEnabled(false);
        jCB_AxialLoadsLoad.setEnabled(false);
        jCB_PlanarLoadsLoad.setEnabled(false);
        jCB_ThermalLoads.setEnabled(false);
        jCB_SelfWeight.setEnabled(true);

        jB_ConLoadsDelete.setEnabled(false);
        jB_ConLoadsEdit.setEnabled(false);
        jB_ConLoadsSave.setEnabled(false);
        jB_MomentsDelete.setEnabled(false);
        jB_MomentsEdit.setEnabled(false);
        jB_MomentsSave.setEnabled(false);
        jB_DistrLoadsDelete.setEnabled(false);
        jB_DistrLoadsEdit.setEnabled(false);
        jB_DistrLoadsSave.setEnabled(false);
        jB_AxialLoadsDelete.setEnabled(false);
        jB_AxialLoadsEdit.setEnabled(false);
        jB_AxialLoadsSave.setEnabled(false);
        jB_PlanarLoadsDelete.setEnabled(false);
        jB_PlanarLoadsEdit.setEnabled(false);
        jB_PlanarLoadsSave.setEnabled(false);
        jB_ThermalLoadsDelete.setEnabled(false);
        jB_ThermalLoadsEdit.setEnabled(false);
        jB_ThermalLoadsSave.setEnabled(false);
        jB_SelfWeightDelete.setEnabled(true);
        jB_SelfWeightEdit.setEnabled(true);
        jB_SelfWeightSave.setEnabled(false);

        jTF_ConLoadsFx.setEnabled(false);
        jTF_ConLoadsFy.setEnabled(false);
        jTF_MomentsM.setEnabled(false);
        jTF_DistrLoadsQx.setEnabled(false);
        jTF_DistrLoadsQy.setEnabled(false);
        jTF_AxialLoadsN.setEnabled(false);
        jTF_PlanarLoadsQz.setEnabled(false);
        jTF_ThermalLoadsTzero.setEnabled(false);
        jTF_ThermalLoadsTtop.setEnabled(false);
        jTF_ThermalLoadsTbot.setEnabled(false);
        jTF_SelfWeight.setEnabled(false);

        jLabelConLoadsTitle.setEnabled(false);
        jLabelMomentsTitle.setEnabled(false);
        jLabelDistrLoadsTitle.setEnabled(false);
        jLabelAxialLoadsTitle.setEnabled(false);
        jLabelPlanarLoadsTitle.setEnabled(false);
        jLabelThermalLoadsTitle.setEnabled(false);
        jLabelSelfWeightTitle.setEnabled(true);
        jLabelConLoadsA.setEnabled(false);
        jLabelConLoadsB.setEnabled(false);
        jLabelMomentsA.setEnabled(false);
        jLabelDistrLoadsA.setEnabled(false);
        jLabelDistrLoadsB.setEnabled(false);
        jLabelAxialLoadsA.setEnabled(false);
        jLabelPlanarLoadsA0.setEnabled(false);
        jLabelPlanarLoadsA1.setEnabled(false);
        jLabelPlanarLoadsA2.setEnabled(false);
        jLabelThermalLoadsTzero.setEnabled(false);
        jLabelThermalLoadsTtop.setEnabled(false);
        jLabelThermalLoadsTbot.setEnabled(false);
        jLabelSelfWeightA0.setEnabled(true);
        jLabelSelfWeightA1.setEnabled(true);
        jLabelSelfWeightA2.setEnabled(true);

        switch (this.type) {
            case "1D":
                jCB_ConLoadsLoad.setEnabled(true);
                jCB_AxialLoadsLoad.setEnabled(true);
                jCB_ThermalLoads.setEnabled(true);
                jCB_SelfWeight.setEnabled(false);

                jB_ConLoadsDelete.setEnabled(true);
                jB_ConLoadsEdit.setEnabled(true);
                jB_AxialLoadsDelete.setEnabled(true);
                jB_AxialLoadsEdit.setEnabled(true);
                jB_ThermalLoadsDelete.setEnabled(true);
                jB_ThermalLoadsEdit.setEnabled(true);
                jB_SelfWeightDelete.setEnabled(false);
                jB_SelfWeightEdit.setEnabled(false);
                jB_SelfWeightSave.setEnabled(false);

                jLabelConLoadsTitle.setEnabled(true);
                jLabelAxialLoadsTitle.setEnabled(true);
                jLabelThermalLoadsTitle.setEnabled(true);
                jLabelSelfWeightTitle.setEnabled(false);
                jLabelConLoadsA.setEnabled(true);
                jLabelConLoadsB.setEnabled(true);
                jLabelAxialLoadsA.setEnabled(true);
                jLabelThermalLoadsTzero.setEnabled(true);
                jLabelSelfWeightA0.setEnabled(false);
                jLabelSelfWeightA1.setEnabled(false);
                jLabelSelfWeightA2.setEnabled(false);
                break;
            case "2D":
                jCB_ConLoadsLoad.setEnabled(true);
                jCB_DistrLoadsLoad.setEnabled(true);
                jCB_SelfWeight.setEnabled(false);

                jB_ConLoadsDelete.setEnabled(true);
                jB_ConLoadsEdit.setEnabled(true);
                jB_DistrLoadsDelete.setEnabled(true);
                jB_DistrLoadsEdit.setEnabled(true);
                jB_SelfWeightDelete.setEnabled(false);
                jB_SelfWeightEdit.setEnabled(false);
                jB_SelfWeightSave.setEnabled(false);

                jLabelConLoadsTitle.setEnabled(true);
                jLabelDistrLoadsTitle.setEnabled(true);
                jLabelSelfWeightTitle.setEnabled(false);
                jLabelConLoadsA.setEnabled(true);
                jLabelConLoadsB.setEnabled(true);
                jLabelDistrLoadsA.setEnabled(true);
                jLabelDistrLoadsB.setEnabled(true);
                jLabelSelfWeightA0.setEnabled(false);
                jLabelSelfWeightA1.setEnabled(false);
                jLabelSelfWeightA2.setEnabled(false);
                break;
            case "3D":
                jCB_ConLoadsLoad.setEnabled(true);
                jCB_DistrLoadsLoad.setEnabled(true);
                jCB_PlanarLoadsLoad.setEnabled(true);

                jB_ConLoadsDelete.setEnabled(true);
                jB_ConLoadsEdit.setEnabled(true);
                jB_DistrLoadsDelete.setEnabled(true);
                jB_DistrLoadsEdit.setEnabled(true);
                jB_PlanarLoadsDelete.setEnabled(true);
                jB_PlanarLoadsEdit.setEnabled(true);

                jLabelConLoadsTitle.setEnabled(true);
                jLabelDistrLoadsTitle.setEnabled(true);
                jLabelPlanarLoadsTitle.setEnabled(true);
                jLabelConLoadsA.setEnabled(true);
                jLabelConLoadsB.setEnabled(true);
                jLabelDistrLoadsA.setEnabled(true);
                jLabelDistrLoadsB.setEnabled(true);
                jLabelPlanarLoadsA0.setEnabled(true);
                jLabelPlanarLoadsA1.setEnabled(true);
                jLabelPlanarLoadsA2.setEnabled(true);
                break;
            case "Beams":
                jCB_ConLoadsLoad.setEnabled(true);
                jCB_MomentsLoad.setEnabled(true);
                jCB_DistrLoadsLoad.setEnabled(true);

                jB_ConLoadsDelete.setEnabled(true);
                jB_ConLoadsEdit.setEnabled(true);
                jB_MomentsDelete.setEnabled(true);
                jB_MomentsEdit.setEnabled(true);
                jB_DistrLoadsDelete.setEnabled(true);
                jB_DistrLoadsEdit.setEnabled(true);

                jLabelConLoadsTitle.setEnabled(true);
                jLabelMomentsTitle.setEnabled(true);
                jLabelDistrLoadsTitle.setEnabled(true);
                jLabelConLoadsB.setEnabled(true);
                jLabelMomentsA.setEnabled(true);
                jLabelDistrLoadsB.setEnabled(true);
                break;
            case "Frames":
                jCB_ConLoadsLoad.setEnabled(true);
                jCB_MomentsLoad.setEnabled(true);
                jCB_DistrLoadsLoad.setEnabled(true);
                jCB_ThermalLoads.setEnabled(true);

                jB_ConLoadsDelete.setEnabled(true);
                jB_ConLoadsEdit.setEnabled(true);
                jB_MomentsDelete.setEnabled(true);
                jB_MomentsEdit.setEnabled(true);
                jB_DistrLoadsDelete.setEnabled(true);
                jB_DistrLoadsEdit.setEnabled(true);
                jB_ThermalLoadsDelete.setEnabled(true);
                jB_ThermalLoadsEdit.setEnabled(true);

                jLabelConLoadsTitle.setEnabled(true);
                jLabelMomentsTitle.setEnabled(true);
                jLabelDistrLoadsTitle.setEnabled(true);
                jLabelThermalLoadsTitle.setEnabled(true);
                jLabelConLoadsA.setEnabled(true);
                jLabelConLoadsB.setEnabled(true);
                jLabelMomentsA.setEnabled(true);
                jLabelDistrLoadsA.setEnabled(true);
                jLabelDistrLoadsB.setEnabled(true);
                jLabelThermalLoadsTtop.setEnabled(true);
                jLabelThermalLoadsTbot.setEnabled(true);
                break;
            case "Grids":
                jCB_ConLoadsLoad.setEnabled(true);
                jCB_DistrLoadsLoad.setEnabled(true);

                jB_ConLoadsDelete.setEnabled(true);
                jB_ConLoadsEdit.setEnabled(true);
                jB_DistrLoadsDelete.setEnabled(true);
                jB_DistrLoadsEdit.setEnabled(true);

                jLabelConLoadsTitle.setEnabled(true);
                jLabelDistrLoadsTitle.setEnabled(true);
                jLabelConLoadsB.setEnabled(true);
                jLabelDistrLoadsB.setEnabled(true);
                break;
            case "Slabs":
                jCB_ConLoadsLoad.setEnabled(true);
                jCB_PlanarLoadsLoad.setEnabled(true);

                jB_ConLoadsDelete.setEnabled(true);
                jB_ConLoadsEdit.setEnabled(true);
                jB_PlanarLoadsDelete.setEnabled(true);
                jB_PlanarLoadsEdit.setEnabled(true);

                jLabelConLoadsTitle.setEnabled(true);
                jLabelPlanarLoadsTitle.setEnabled(true);
                jLabelConLoadsB.setEnabled(true);
                jLabelPlanarLoadsA0.setEnabled(true);
                jLabelPlanarLoadsA1.setEnabled(true);
                jLabelPlanarLoadsA2.setEnabled(true);
                break;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jB_AxialLoadsDelete;
    public javax.swing.JButton jB_AxialLoadsEdit;
    public javax.swing.JButton jB_AxialLoadsSave;
    public javax.swing.JButton jB_ConLoadsDelete;
    public javax.swing.JButton jB_ConLoadsEdit;
    public javax.swing.JButton jB_ConLoadsSave;
    public javax.swing.JButton jB_DistrLoadsDelete;
    public javax.swing.JButton jB_DistrLoadsEdit;
    public javax.swing.JButton jB_DistrLoadsSave;
    public javax.swing.JButton jB_MomentsDelete;
    public javax.swing.JButton jB_MomentsEdit;
    public javax.swing.JButton jB_MomentsSave;
    public javax.swing.JButton jB_PlanarLoadsDelete;
    public javax.swing.JButton jB_PlanarLoadsEdit;
    public javax.swing.JButton jB_PlanarLoadsSave;
    public javax.swing.JButton jB_SelfWeightDelete;
    public javax.swing.JButton jB_SelfWeightEdit;
    public javax.swing.JButton jB_SelfWeightSave;
    public javax.swing.JButton jB_ThermalLoadsDelete;
    public javax.swing.JButton jB_ThermalLoadsEdit;
    public javax.swing.JButton jB_ThermalLoadsSave;
    public javax.swing.JButton jButtonClose;
    public javax.swing.JComboBox jCB_AxialLoadsLoad;
    public javax.swing.JComboBox jCB_ConLoadsLoad;
    public javax.swing.JComboBox jCB_DistrLoadsLoad;
    public javax.swing.JComboBox jCB_MomentsLoad;
    public javax.swing.JComboBox jCB_PlanarLoadsLoad;
    public javax.swing.JComboBox jCB_SelfWeight;
    public javax.swing.JComboBox jCB_ThermalLoads;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelAxialLoadsA;
    private javax.swing.JLabel jLabelAxialLoadsTitle;
    private javax.swing.JLabel jLabelConLoadsA;
    private javax.swing.JLabel jLabelConLoadsB;
    private javax.swing.JLabel jLabelConLoadsTitle;
    private javax.swing.JLabel jLabelDistrLoadsA;
    private javax.swing.JLabel jLabelDistrLoadsB;
    private javax.swing.JLabel jLabelDistrLoadsTitle;
    private javax.swing.JLabel jLabelMomentsA;
    private javax.swing.JLabel jLabelMomentsTitle;
    private javax.swing.JLabel jLabelPlanarLoadsA0;
    private javax.swing.JLabel jLabelPlanarLoadsA1;
    private javax.swing.JLabel jLabelPlanarLoadsA2;
    private javax.swing.JLabel jLabelPlanarLoadsTitle;
    private javax.swing.JLabel jLabelSelfWeightA0;
    private javax.swing.JLabel jLabelSelfWeightA1;
    private javax.swing.JLabel jLabelSelfWeightA2;
    private javax.swing.JLabel jLabelSelfWeightTitle;
    private javax.swing.JLabel jLabelThermalLoadsTbot;
    private javax.swing.JLabel jLabelThermalLoadsTitle;
    private javax.swing.JLabel jLabelThermalLoadsTtop;
    private javax.swing.JLabel jLabelThermalLoadsTzero;
    private javax.swing.JPanel jLateralPanelAxialLoads;
    private javax.swing.JPanel jLateralPanelConLoads;
    private javax.swing.JPanel jLateralPanelDistrLoads;
    private javax.swing.JPanel jLateralPanelMoments;
    private javax.swing.JPanel jLateralPanelPlanarLoads;
    private javax.swing.JPanel jLateralPanelSelfWeight;
    private javax.swing.JPanel jLateralPanelThermalLoads;
    private javax.swing.JPanel jPanelAxialLoads;
    private javax.swing.JPanel jPanelAxialLoads_Container;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelConLoads;
    private javax.swing.JPanel jPanelConLoads_Container;
    private javax.swing.JPanel jPanelContainer;
    private javax.swing.JPanel jPanelDistrLoads;
    private javax.swing.JPanel jPanelDistrLoads_Container;
    private javax.swing.JPanel jPanelMoments;
    private javax.swing.JPanel jPanelMoments_Container;
    private javax.swing.JPanel jPanelPlanarLoads;
    private javax.swing.JPanel jPanelPlanarLoads_Container;
    private javax.swing.JPanel jPanelSelfWeight;
    private javax.swing.JPanel jPanelSelfWeight_Container;
    private javax.swing.JPanel jPanelSeparator;
    private javax.swing.JPanel jPanelThermalLoads;
    private javax.swing.JPanel jPanelThermalLoads_Container;
    public javax.swing.JTextField jTF_AxialLoadsN;
    public javax.swing.JTextField jTF_ConLoadsFx;
    public javax.swing.JTextField jTF_ConLoadsFy;
    public javax.swing.JTextField jTF_DistrLoadsQx;
    public javax.swing.JTextField jTF_DistrLoadsQy;
    public javax.swing.JTextField jTF_MomentsM;
    public javax.swing.JTextField jTF_PlanarLoadsQz;
    public javax.swing.JTextField jTF_SelfWeight;
    public javax.swing.JTextField jTF_ThermalLoadsTbot;
    public javax.swing.JTextField jTF_ThermalLoadsTtop;
    public javax.swing.JTextField jTF_ThermalLoadsTzero;
    // End of variables declaration//GEN-END:variables
}
