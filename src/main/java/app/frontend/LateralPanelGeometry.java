/*
 * Classe para criar o painel lateral Geometry
 * O painel lateral é composto por sub painéis abertos individualmente
 * Os sub painéis são o jPanelNodes, jPanelSections, jPanelMaterials, jPanelMesh,
 * jPanelElasticSupports e o jPanelSettlements
 */

package app.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author André de Sousa
 */
public class LateralPanelGeometry extends javax.swing.JPanel {

    private String type;
    private String jPanelActivated;
    private final Color selectedColor;
    private final Color notSelectedColor;
    private final Color enteredColor;

    /**
     * Creates new form JLateralPanel
     */
    public LateralPanelGeometry() {
        initComponents();

        type = "";
        jPanelActivated = "";
        selectedColor = new Color(51, 153, 255);
        notSelectedColor = new Color(236, 236, 236);
        enteredColor = new Color(222, 222, 222);

        jLabelNodesTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelNodesA.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelNodesB.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelNodesC.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelInertiaA.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelInertiaC.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelTorsionA.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelTorsionC.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelAreaA.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelAreaC.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelThickness.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelElasticity.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelMaterial.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelPoisson.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelThermal.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelMeshTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelMeshA.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelMeshB.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelMeshC.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelSupportsTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelSupportsA.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelSupportsB.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelSupportsC.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelSettlementsTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelSettlementsA.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelSettlementsB.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelSettlementsC.setFont(new java.awt.Font("Tahoma", 0, 11));

        jB_SectionsEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_SectionsSave.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_SectionsDelete.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_MaterialsEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_MaterialsSave.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_MaterialsDelete.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_SupportsEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_SupportsSave.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_SupportsDelete.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_SettlementsEdit.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_SettlementsSave.setFont(new java.awt.Font("Tahoma", 0, 11));
        jB_SettlementsDelete.setFont(new java.awt.Font("Tahoma", 0, 11));

        jCB_NodesLines.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_NodesTriangles.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_NodesQuadrilaterals.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_Materials.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_MeshLines.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_MeshTriangles.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_MeshRectQuadril.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_Sections.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_Supports.setFont(new java.awt.Font("Tahoma", 0, 11));
        jCB_Settlements.setFont(new java.awt.Font("Tahoma", 0, 11));

        jTF_SectionsInercia.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_SectionsTorsion.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_SectionsArea.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_SectionsThickness.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_MaterialsElasticity.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_MaterialsPoisson.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_MaterialsThermal.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_SupportsKx.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_SupportsKy.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_SupportsKz.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_SettlementsDx.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_SettlementsDy.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTF_SettlementsRz.setFont(new java.awt.Font("Tahoma", 0, 11));

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
        jLateralPanelNodes = new javax.swing.JPanel();
        jLabelNodesB = new javax.swing.JLabel();
        jLabelNodesC = new javax.swing.JLabel();
        jCB_NodesTriangles = new javax.swing.JComboBox();
        jCB_NodesQuadrilaterals = new javax.swing.JComboBox();
        jCB_NodesLines = new javax.swing.JComboBox();
        jLabelNodesTitle = new javax.swing.JLabel();
        jLabelNodesA = new javax.swing.JLabel();
        jLateralJPanelSections = new javax.swing.JPanel();
        jLabelSectionsTitle = new javax.swing.JLabel();
        jCB_Sections = new javax.swing.JComboBox();
        jLabelAreaA = new javax.swing.JLabel();
        jLabelThickness = new javax.swing.JLabel();
        jLabelAreaB = new javax.swing.JLabel();
        jLabelAreaC = new javax.swing.JLabel();
        jLabelInertiaA = new javax.swing.JLabel();
        jLabelInertiaB = new javax.swing.JLabel();
        jLabelInertiaC = new javax.swing.JLabel();
        jB_SectionsSave = new javax.swing.JButton();
        jB_SectionsEdit = new javax.swing.JButton();
        jTF_SectionsInercia = new javax.swing.JTextField();
        jTF_SectionsTorsion = new javax.swing.JTextField();
        jTF_SectionsArea = new javax.swing.JTextField();
        jTF_SectionsThickness = new javax.swing.JTextField();
        jLabelTorsionA = new javax.swing.JLabel();
        jLabelTorsionC = new javax.swing.JLabel();
        jLabelTorsionB = new javax.swing.JLabel();
        jB_SectionsDelete = new javax.swing.JButton();
        jLateralPanelMaterials = new javax.swing.JPanel();
        jLabelMaterial = new javax.swing.JLabel();
        jLabelElasticity = new javax.swing.JLabel();
        jLabelPoisson = new javax.swing.JLabel();
        jLabelThermal = new javax.swing.JLabel();
        jCB_Materials = new javax.swing.JComboBox();
        jB_MaterialsSave = new javax.swing.JButton();
        jB_MaterialsEdit = new javax.swing.JButton();
        jTF_MaterialsPoisson = new javax.swing.JTextField();
        jTF_MaterialsElasticity = new javax.swing.JTextField();
        jTF_MaterialsThermal = new javax.swing.JTextField();
        jB_MaterialsDelete = new javax.swing.JButton();
        jLateralPanelMesh = new javax.swing.JPanel();
        jLabelMeshTitle = new javax.swing.JLabel();
        jLabelMeshA = new javax.swing.JLabel();
        jLabelMeshB = new javax.swing.JLabel();
        jLabelMeshC = new javax.swing.JLabel();
        jCB_MeshTriangles = new javax.swing.JComboBox();
        jCB_MeshRectQuadril = new javax.swing.JComboBox();
        jCB_MeshLines = new javax.swing.JComboBox();
        jLateralPanelSupports = new javax.swing.JPanel();
        jLabelSupportsTitle = new javax.swing.JLabel();
        jLabelSupportsA = new javax.swing.JLabel();
        jLabelSupportsB = new javax.swing.JLabel();
        jLabelSupportsC = new javax.swing.JLabel();
        jCB_Supports = new javax.swing.JComboBox();
        jB_SupportsSave = new javax.swing.JButton();
        jB_SupportsEdit = new javax.swing.JButton();
        jB_SupportsDelete = new javax.swing.JButton();
        jTF_SupportsKx = new javax.swing.JTextField();
        jTF_SupportsKy = new javax.swing.JTextField();
        jTF_SupportsKz = new javax.swing.JTextField();
        jLateralPanelSettlements = new javax.swing.JPanel();
        jLabelSettlementsTitle = new javax.swing.JLabel();
        jLabelSettlementsA = new javax.swing.JLabel();
        jLabelSettlementsB = new javax.swing.JLabel();
        jLabelSettlementsC = new javax.swing.JLabel();
        jCB_Settlements = new javax.swing.JComboBox();
        jB_SettlementsSave = new javax.swing.JButton();
        jB_SettlementsEdit = new javax.swing.JButton();
        jB_SettlementsDelete = new javax.swing.JButton();
        jTF_SettlementsDx = new javax.swing.JTextField();
        jTF_SettlementsDy = new javax.swing.JTextField();
        jTF_SettlementsRz = new javax.swing.JTextField();
        jPanelContainer = new javax.swing.JPanel();
        jPanelNodes = new JPanelMouseEvents();
        jLabel1 = new javax.swing.JLabel();
        jPanelNodes_Container = new javax.swing.JPanel();
        jPanelSections = new JPanelMouseEvents();
        jLabel4 = new javax.swing.JLabel();
        jPanelSections_Container = new javax.swing.JPanel();
        jPanelMaterials = new JPanelMouseEvents();
        jLabel5 = new javax.swing.JLabel();
        jPanelMaterials_Container = new javax.swing.JPanel();
        jPanelMesh = new JPanelMouseEvents();
        jLabel6 = new javax.swing.JLabel();
        jPanelMesh_Container = new javax.swing.JPanel();
        jPanelElasticSupports = new JPanelMouseEvents();
        jLabel2 = new javax.swing.JLabel();
        jPanelElasticSupports_Container = new javax.swing.JPanel();
        jPanelSettlements = new JPanelMouseEvents();
        jLabel3 = new javax.swing.JLabel();
        jPanelSettlements_Container = new javax.swing.JPanel();
        jPanelBottom = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();
        jPanelSeparator = new javax.swing.JPanel();

        jLateralPanelNodes.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelNodes.setMinimumSize(new java.awt.Dimension(199, 150));
        jLateralPanelNodes.setPreferredSize(new java.awt.Dimension(199, 150));

        jLabelNodesB.setText("Triangles");

        jLabelNodesC.setText("Quadrilaterals");

        jCB_NodesTriangles.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3 Nodes", "6 Nodes" }));
        jCB_NodesTriangles.setFocusable(false);
        jCB_NodesTriangles.setPreferredSize(new java.awt.Dimension(70, 20));

        jCB_NodesQuadrilaterals.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "4 Nodes", "8 Nodes", "9 Nodes" }));
        jCB_NodesQuadrilaterals.setFocusable(false);
        jCB_NodesQuadrilaterals.setPreferredSize(new java.awt.Dimension(70, 20));

        jCB_NodesLines.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2 Nodes", "3 Nodes", "4 Nodes" }));
        jCB_NodesLines.setFocusable(false);
        jCB_NodesLines.setPreferredSize(new java.awt.Dimension(70, 20));

        jLabelNodesTitle.setText("Select the number of nodes:");

        jLabelNodesA.setText("Lines");

        javax.swing.GroupLayout jLateralPanelNodesLayout = new javax.swing.GroupLayout(jLateralPanelNodes);
        jLateralPanelNodes.setLayout(jLateralPanelNodesLayout);
        jLateralPanelNodesLayout.setHorizontalGroup(
            jLateralPanelNodesLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelNodesLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelNodesLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelNodesTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                .addGroup(
                                    jLateralPanelNodesLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelNodesC)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jCB_NodesQuadrilaterals,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    jLateralPanelNodesLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelNodesA)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jCB_NodesLines,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    jLateralPanelNodesLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelNodesB)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jCB_NodesTriangles,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralPanelNodesLayout.setVerticalGroup(
            jLateralPanelNodesLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelNodesLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelNodesTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelNodesLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelNodesA,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_NodesLines,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelNodesLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelNodesB,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_NodesTriangles,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelNodesLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelNodesC,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_NodesQuadrilaterals,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        jLateralJPanelSections.setBackground(new java.awt.Color(236, 236, 236));
        jLateralJPanelSections.setMinimumSize(new java.awt.Dimension(199, 215));
        jLateralJPanelSections.setPreferredSize(new java.awt.Dimension(199, 215));

        jLabelSectionsTitle.setText("Section");

        jCB_Sections.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<New>" }));
        jCB_Sections.setFocusable(false);
        jCB_Sections.setPreferredSize(new java.awt.Dimension(70, 20));

        jLabelAreaA.setText("Area (m");

        jLabelThickness.setText("Thickness (m)");

        jLabelAreaB.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabelAreaB.setText("2");
        jLabelAreaB.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabelAreaC.setText(")");

        jLabelInertiaA.setText("Inertia (m");

        jLabelInertiaB.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabelInertiaB.setText("4");
        jLabelInertiaB.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabelInertiaC.setText(")");

        jB_SectionsSave.setText("Save");
        jB_SectionsSave.setEnabled(false);
        jB_SectionsSave.setFocusable(false);

        jB_SectionsEdit.setText("Edit");
        jB_SectionsEdit.setFocusable(false);

        jTF_SectionsInercia.setEnabled(false);

        jTF_SectionsTorsion.setEnabled(false);

        jTF_SectionsArea.setEnabled(false);

        jTF_SectionsThickness.setEnabled(false);

        jLabelTorsionA.setText("Torsion (m");

        jLabelTorsionC.setText(")");

        jLabelTorsionB.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabelTorsionB.setText("4");
        jLabelTorsionB.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jB_SectionsDelete.setText("Delete");
        jB_SectionsDelete.setFocusable(false);

        javax.swing.GroupLayout jLateralJPanelSectionsLayout = new javax.swing.GroupLayout(jLateralJPanelSections);
        jLateralJPanelSections.setLayout(jLateralJPanelSectionsLayout);
        jLateralJPanelSectionsLayout.setHorizontalGroup(
            jLateralJPanelSectionsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralJPanelSectionsLayout
                        .createSequentialGroup()
                        .addGroup(
                            jLateralJPanelSectionsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralJPanelSectionsLayout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                            jLateralJPanelSectionsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    jLateralJPanelSectionsLayout
                                                        .createSequentialGroup()
                                                        .addComponent(
                                                            jLabelThickness,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            Short.MAX_VALUE
                                                        )
                                                        .addPreferredGap(
                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            Short.MAX_VALUE
                                                        )
                                                        .addComponent(
                                                            jTF_SectionsThickness,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            70,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                )
                                                .addGroup(
                                                    jLateralJPanelSectionsLayout
                                                        .createSequentialGroup()
                                                        .addComponent(jLabelSectionsTitle)
                                                        .addPreferredGap(
                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            Short.MAX_VALUE
                                                        )
                                                        .addComponent(
                                                            jCB_Sections,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                )
                                                .addGroup(
                                                    jLateralJPanelSectionsLayout
                                                        .createSequentialGroup()
                                                        .addComponent(jLabelAreaA)
                                                        .addGap(0, 0, 0)
                                                        .addComponent(jLabelAreaB)
                                                        .addGap(1, 1, 1)
                                                        .addComponent(jLabelAreaC)
                                                        .addPreferredGap(
                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            Short.MAX_VALUE
                                                        )
                                                        .addComponent(
                                                            jTF_SectionsArea,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            70,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                )
                                                .addGroup(
                                                    jLateralJPanelSectionsLayout
                                                        .createSequentialGroup()
                                                        .addComponent(jLabelInertiaA)
                                                        .addGap(0, 0, 0)
                                                        .addComponent(jLabelInertiaB)
                                                        .addGap(1, 1, 1)
                                                        .addComponent(jLabelInertiaC)
                                                        .addPreferredGap(
                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            Short.MAX_VALUE
                                                        )
                                                        .addComponent(
                                                            jTF_SectionsInercia,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            70,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                )
                                                .addGroup(
                                                    jLateralJPanelSectionsLayout
                                                        .createSequentialGroup()
                                                        .addComponent(jLabelTorsionA)
                                                        .addGap(0, 0, 0)
                                                        .addComponent(jLabelTorsionB)
                                                        .addGap(1, 1, 1)
                                                        .addComponent(jLabelTorsionC)
                                                        .addPreferredGap(
                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            Short.MAX_VALUE
                                                        )
                                                        .addComponent(
                                                            jTF_SectionsTorsion,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            70,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                )
                                        )
                                )
                                .addGroup(
                                    jLateralJPanelSectionsLayout
                                        .createSequentialGroup()
                                        .addGap(0, 14, Short.MAX_VALUE)
                                        .addComponent(jB_SectionsDelete)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_SectionsSave)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_SectionsEdit)
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralJPanelSectionsLayout.setVerticalGroup(
            jLateralJPanelSectionsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralJPanelSectionsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralJPanelSectionsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelSectionsTitle,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_Sections,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralJPanelSectionsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralJPanelSectionsLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(
                                            jLabelInertiaC,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            25,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                            jTF_SectionsInercia,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            20,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addComponent(
                                    jLabelInertiaB,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jLabelInertiaA,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralJPanelSectionsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralJPanelSectionsLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(
                                            jLabelTorsionA,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            25,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                            jLabelTorsionC,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            25,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                            jTF_SectionsTorsion,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            20,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addComponent(
                                    jLabelTorsionB,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralJPanelSectionsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    jLabelAreaB,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jLabelAreaA,
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralJPanelSectionsLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(
                                            jLabelAreaC,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            25,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                            jTF_SectionsArea,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            20,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralJPanelSectionsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelThickness,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_SectionsThickness,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    20,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(
                            jLateralJPanelSectionsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_SectionsEdit)
                                .addComponent(jB_SectionsSave)
                                .addComponent(jB_SectionsDelete)
                        )
                        .addContainerGap(21, Short.MAX_VALUE)
                )
        );

        jLateralPanelMaterials.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelMaterials.setMinimumSize(new java.awt.Dimension(199, 180));
        jLateralPanelMaterials.setPreferredSize(new java.awt.Dimension(199, 180));

        jLabelMaterial.setText("Select Material:");

        jLabelElasticity.setText("M of Elasticity (GPa)");

        jLabelPoisson.setText("Poisson’s Ratio");

        jLabelThermal.setText("Thermal Exp. (/ºC)");

        jCB_Materials.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<New>" }));
        jCB_Materials.setFocusable(false);
        jCB_Materials.setPreferredSize(new java.awt.Dimension(70, 20));

        jB_MaterialsSave.setText("Save");
        jB_MaterialsSave.setEnabled(false);
        jB_MaterialsSave.setFocusable(false);

        jB_MaterialsEdit.setText("Edit");
        jB_MaterialsEdit.setFocusable(false);

        jTF_MaterialsPoisson.setEnabled(false);

        jTF_MaterialsElasticity.setEnabled(false);

        jTF_MaterialsThermal.setEnabled(false);

        jB_MaterialsDelete.setText("Delete");
        jB_MaterialsDelete.setFocusable(false);

        javax.swing.GroupLayout jLateralPanelMaterialsLayout = new javax.swing.GroupLayout(jLateralPanelMaterials);
        jLateralPanelMaterials.setLayout(jLateralPanelMaterialsLayout);
        jLateralPanelMaterialsLayout.setHorizontalGroup(
            jLateralPanelMaterialsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelMaterialsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelMaterialsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralPanelMaterialsLayout
                                        .createSequentialGroup()
                                        .addGroup(
                                            jLateralPanelMaterialsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabelMaterial)
                                                .addComponent(jLabelElasticity)
                                                .addComponent(jLabelPoisson)
                                                .addComponent(jLabelThermal)
                                        )
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addGroup(
                                            jLateralPanelMaterialsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(
                                                    jTF_MaterialsElasticity,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    70,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                                .addComponent(
                                                    jTF_MaterialsPoisson,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    70,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                                .addComponent(
                                                    jTF_MaterialsThermal,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    70,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                                .addComponent(
                                                    jCB_Materials,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    70,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                        )
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelMaterialsLayout
                                        .createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jB_MaterialsDelete)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_MaterialsSave)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_MaterialsEdit)
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralPanelMaterialsLayout.setVerticalGroup(
            jLateralPanelMaterialsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelMaterialsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelMaterialsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelMaterial,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_Materials,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    20,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelMaterialsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelElasticity,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_MaterialsElasticity,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    20,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelMaterialsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelPoisson,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_MaterialsPoisson,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    20,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelMaterialsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelThermal,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_MaterialsThermal,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    20,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(
                            jLateralPanelMaterialsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_MaterialsEdit)
                                .addComponent(jB_MaterialsSave)
                                .addComponent(jB_MaterialsDelete)
                        )
                        .addContainerGap(17, Short.MAX_VALUE)
                )
        );

        jLateralPanelMesh.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelMesh.setMinimumSize(new java.awt.Dimension(199, 150));
        jLateralPanelMesh.setPreferredSize(new java.awt.Dimension(199, 150));

        jLabelMeshTitle.setText("Select the level of refinement:");

        jLabelMeshA.setText("Lines");

        jLabelMeshB.setText("Triangles");

        jLabelMeshC.setText("Quadrilaterals");

        jCB_MeshTriangles.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "x1", "x4", "x16" }));
        jCB_MeshTriangles.setFocusable(false);
        jCB_MeshTriangles.setPreferredSize(new java.awt.Dimension(70, 20));

        jCB_MeshRectQuadril.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "x1", "x4", "x16" }));
        jCB_MeshRectQuadril.setFocusable(false);
        jCB_MeshRectQuadril.setPreferredSize(new java.awt.Dimension(70, 20));

        jCB_MeshLines.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "x1", "x2", "x4" }));
        jCB_MeshLines.setFocusable(false);
        jCB_MeshLines.setPreferredSize(new java.awt.Dimension(70, 20));

        javax.swing.GroupLayout jLateralPanelMeshLayout = new javax.swing.GroupLayout(jLateralPanelMesh);
        jLateralPanelMesh.setLayout(jLateralPanelMeshLayout);
        jLateralPanelMeshLayout.setHorizontalGroup(
            jLateralPanelMeshLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelMeshLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelMeshLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelMeshTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                .addGroup(
                                    jLateralPanelMeshLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelMeshC)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jCB_MeshRectQuadril,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    jLateralPanelMeshLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelMeshA)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jCB_MeshLines,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    jLateralPanelMeshLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelMeshB)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jCB_MeshTriangles,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralPanelMeshLayout.setVerticalGroup(
            jLateralPanelMeshLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelMeshLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelMeshTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelMeshLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelMeshA,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_MeshLines,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelMeshLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelMeshB,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_MeshTriangles,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelMeshLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelMeshC,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_MeshRectQuadril,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        jLateralPanelSupports.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelSupports.setMinimumSize(new java.awt.Dimension(199, 190));
        jLateralPanelSupports.setPreferredSize(new java.awt.Dimension(199, 190));

        jLabelSupportsTitle.setText("Supports");

        jLabelSupportsA.setText("Kx (KN/m)");

        jLabelSupportsB.setText("Ky (KN/m)");

        jLabelSupportsC.setText("Kz (KNm/rad)");

        jCB_Supports.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<New>" }));
        jCB_Supports.setFocusable(false);
        jCB_Supports.setPreferredSize(new java.awt.Dimension(70, 20));

        jB_SupportsSave.setText("Save");
        jB_SupportsSave.setEnabled(false);
        jB_SupportsSave.setFocusable(false);

        jB_SupportsEdit.setText("Edit");
        jB_SupportsEdit.setFocusable(false);

        jB_SupportsDelete.setText("Delete");
        jB_SupportsDelete.setFocusable(false);

        jTF_SupportsKx.setEnabled(false);
        jTF_SupportsKx.setPreferredSize(new java.awt.Dimension(70, 20));

        jTF_SupportsKy.setEnabled(false);
        jTF_SupportsKy.setPreferredSize(new java.awt.Dimension(70, 20));

        jTF_SupportsKz.setEnabled(false);
        jTF_SupportsKz.setPreferredSize(new java.awt.Dimension(70, 20));

        javax.swing.GroupLayout jLateralPanelSupportsLayout = new javax.swing.GroupLayout(jLateralPanelSupports);
        jLateralPanelSupports.setLayout(jLateralPanelSupportsLayout);
        jLateralPanelSupportsLayout.setHorizontalGroup(
            jLateralPanelSupportsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelSupportsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelSupportsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralPanelSupportsLayout
                                        .createSequentialGroup()
                                        .addGroup(
                                            jLateralPanelSupportsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(
                                                    jLabelSupportsA,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    Short.MAX_VALUE
                                                )
                                                .addComponent(
                                                    jLabelSupportsB,
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
                                            jLateralPanelSupportsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(
                                                    jTF_SupportsKx,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                                .addComponent(
                                                    jTF_SupportsKy,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                        )
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelSupportsLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelSupportsC)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jTF_SupportsKz,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelSupportsLayout
                                        .createSequentialGroup()
                                        .addGroup(
                                            jLateralPanelSupportsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                    jLateralPanelSupportsLayout
                                                        .createSequentialGroup()
                                                        .addGap(0, 4, Short.MAX_VALUE)
                                                        .addComponent(jB_SupportsDelete)
                                                        .addGap(2, 2, 2)
                                                )
                                                .addGroup(
                                                    jLateralPanelSupportsLayout
                                                        .createSequentialGroup()
                                                        .addComponent(jLabelSupportsTitle)
                                                        .addPreferredGap(
                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            Short.MAX_VALUE
                                                        )
                                                )
                                        )
                                        .addGroup(
                                            jLateralPanelSupportsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                    jLateralPanelSupportsLayout
                                                        .createSequentialGroup()
                                                        .addComponent(jB_SupportsSave)
                                                        .addGap(2, 2, 2)
                                                        .addComponent(jB_SupportsEdit)
                                                )
                                                .addComponent(
                                                    jCB_Supports,
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
        jLateralPanelSupportsLayout.setVerticalGroup(
            jLateralPanelSupportsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelSupportsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelSupportsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelSupportsTitle,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_Supports,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelSupportsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelSupportsA,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_SupportsKx,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelSupportsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelSupportsB,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_SupportsKy,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelSupportsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelSupportsC,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_SupportsKz,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addGap(18, 18, 18)
                        .addGroup(
                            jLateralPanelSupportsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_SupportsEdit)
                                .addComponent(jB_SupportsSave)
                                .addComponent(jB_SupportsDelete)
                        )
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        jLateralPanelSettlements.setBackground(new java.awt.Color(236, 236, 236));
        jLateralPanelSettlements.setMinimumSize(new java.awt.Dimension(199, 190));

        jLabelSettlementsTitle.setText("Settlements");

        jLabelSettlementsA.setText("Dx (m)");

        jLabelSettlementsB.setText("Dy (m)");

        jLabelSettlementsC.setText("Rz (rad)");

        jCB_Settlements.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<New>" }));
        jCB_Settlements.setFocusable(false);
        jCB_Settlements.setPreferredSize(new java.awt.Dimension(70, 20));

        jB_SettlementsSave.setText("Save");
        jB_SettlementsSave.setEnabled(false);
        jB_SettlementsSave.setFocusable(false);

        jB_SettlementsEdit.setText("Edit");
        jB_SettlementsEdit.setFocusable(false);

        jB_SettlementsDelete.setText("Delete");
        jB_SettlementsDelete.setFocusable(false);

        jTF_SettlementsDx.setEnabled(false);
        jTF_SettlementsDx.setPreferredSize(new java.awt.Dimension(70, 20));

        jTF_SettlementsDy.setEnabled(false);
        jTF_SettlementsDy.setPreferredSize(new java.awt.Dimension(70, 20));

        jTF_SettlementsRz.setEnabled(false);
        jTF_SettlementsRz.setPreferredSize(new java.awt.Dimension(70, 20));

        javax.swing.GroupLayout jLateralPanelSettlementsLayout = new javax.swing.GroupLayout(jLateralPanelSettlements);
        jLateralPanelSettlements.setLayout(jLateralPanelSettlementsLayout);
        jLateralPanelSettlementsLayout.setHorizontalGroup(
            jLateralPanelSettlementsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelSettlementsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelSettlementsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    jLateralPanelSettlementsLayout
                                        .createSequentialGroup()
                                        .addComponent(jLabelSettlementsTitle)
                                        .addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addComponent(
                                            jCB_Settlements,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                                .addGroup(
                                    jLateralPanelSettlementsLayout
                                        .createSequentialGroup()
                                        .addGroup(
                                            jLateralPanelSettlementsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(
                                                    jLabelSettlementsB,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    Short.MAX_VALUE
                                                )
                                                .addComponent(
                                                    jLabelSettlementsA,
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
                                            jLateralPanelSettlementsLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(
                                                    jTF_SettlementsDx,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                                .addComponent(
                                                    jTF_SettlementsDy,
                                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                                )
                                        )
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelSettlementsLayout
                                        .createSequentialGroup()
                                        .addGap(0, 4, Short.MAX_VALUE)
                                        .addComponent(jB_SettlementsDelete)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_SettlementsSave)
                                        .addGap(2, 2, 2)
                                        .addComponent(jB_SettlementsEdit)
                                )
                                .addGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING,
                                    jLateralPanelSettlementsLayout
                                        .createSequentialGroup()
                                        .addComponent(
                                            jLabelSettlementsC,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE
                                        )
                                        .addGap(39, 39, 39)
                                        .addComponent(
                                            jTF_SettlementsRz,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                )
                        )
                        .addContainerGap()
                )
        );
        jLateralPanelSettlementsLayout.setVerticalGroup(
            jLateralPanelSettlementsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jLateralPanelSettlementsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jLateralPanelSettlementsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelSettlementsTitle,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jCB_Settlements,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelSettlementsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelSettlementsA,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_SettlementsDx,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelSettlementsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelSettlementsB,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_SettlementsDy,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            jLateralPanelSettlementsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabelSettlementsC,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jTF_SettlementsRz,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addGap(18, 18, 18)
                        .addGroup(
                            jLateralPanelSettlementsLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_SettlementsEdit)
                                .addComponent(jB_SettlementsSave)
                                .addComponent(jB_SettlementsDelete)
                        )
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        setBackground(new java.awt.Color(236, 236, 236));

        jPanelContainer.setBackground(new java.awt.Color(236, 236, 236));

        jPanelNodes.setBackground(new java.awt.Color(236, 236, 236));
        jPanelNodes.setName("jPanelNodes"); // NOI18N
        jPanelNodes.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelNodesMousePressed(evt);
                }
            }
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setText("Number of Nodes");

        javax.swing.GroupLayout jPanelNodesLayout = new javax.swing.GroupLayout(jPanelNodes);
        jPanelNodes.setLayout(jPanelNodesLayout);
        jPanelNodesLayout.setHorizontalGroup(
            jPanelNodesLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelNodesLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelNodesLayout.setVerticalGroup(
            jPanelNodesLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelNodes_Container.setBackground(new java.awt.Color(236, 236, 236));

        javax.swing.GroupLayout jPanelNodes_ContainerLayout = new javax.swing.GroupLayout(jPanelNodes_Container);
        jPanelNodes_Container.setLayout(jPanelNodes_ContainerLayout);
        jPanelNodes_ContainerLayout.setHorizontalGroup(
            jPanelNodes_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelNodes_ContainerLayout.setVerticalGroup(
            jPanelNodes_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelSections.setBackground(new java.awt.Color(236, 236, 236));
        jPanelSections.setName("jPanelSections"); // NOI18N
        jPanelSections.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelSectionsMousePressed(evt);
                }
            }
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setText("Sections");

        javax.swing.GroupLayout jPanelSectionsLayout = new javax.swing.GroupLayout(jPanelSections);
        jPanelSections.setLayout(jPanelSectionsLayout);
        jPanelSectionsLayout.setHorizontalGroup(
            jPanelSectionsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelSectionsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelSectionsLayout.setVerticalGroup(
            jPanelSectionsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelSections_Container.setBackground(new java.awt.Color(236, 236, 236));

        javax.swing.GroupLayout jPanelSections_ContainerLayout = new javax.swing.GroupLayout(jPanelSections_Container);
        jPanelSections_Container.setLayout(jPanelSections_ContainerLayout);
        jPanelSections_ContainerLayout.setHorizontalGroup(
            jPanelSections_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelSections_ContainerLayout.setVerticalGroup(
            jPanelSections_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelMaterials.setBackground(new java.awt.Color(236, 236, 236));
        jPanelMaterials.setName("jPanelMaterials"); // NOI18N
        jPanelMaterials.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelMaterialsMousePressed(evt);
                }
            }
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel5.setText("Materials");

        javax.swing.GroupLayout jPanelMaterialsLayout = new javax.swing.GroupLayout(jPanelMaterials);
        jPanelMaterials.setLayout(jPanelMaterialsLayout);
        jPanelMaterialsLayout.setHorizontalGroup(
            jPanelMaterialsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelMaterialsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelMaterialsLayout.setVerticalGroup(
            jPanelMaterialsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelMaterials_Container.setBackground(new java.awt.Color(236, 236, 236));

        javax.swing.GroupLayout jPanelMaterials_ContainerLayout = new javax.swing.GroupLayout(jPanelMaterials_Container);
        jPanelMaterials_Container.setLayout(jPanelMaterials_ContainerLayout);
        jPanelMaterials_ContainerLayout.setHorizontalGroup(
            jPanelMaterials_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelMaterials_ContainerLayout.setVerticalGroup(
            jPanelMaterials_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelMesh.setBackground(new java.awt.Color(236, 236, 236));
        jPanelMesh.setName("jPanelMesh"); // NOI18N
        jPanelMesh.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelMeshMousePressed(evt);
                }
            }
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setText("Mesh Refinement");

        javax.swing.GroupLayout jPanelMeshLayout = new javax.swing.GroupLayout(jPanelMesh);
        jPanelMesh.setLayout(jPanelMeshLayout);
        jPanelMeshLayout.setHorizontalGroup(
            jPanelMeshLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelMeshLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelMeshLayout.setVerticalGroup(
            jPanelMeshLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelMesh_Container.setBackground(new java.awt.Color(236, 236, 236));
        jPanelMesh_Container.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanelMesh_ContainerLayout = new javax.swing.GroupLayout(jPanelMesh_Container);
        jPanelMesh_Container.setLayout(jPanelMesh_ContainerLayout);
        jPanelMesh_ContainerLayout.setHorizontalGroup(
            jPanelMesh_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelMesh_ContainerLayout.setVerticalGroup(
            jPanelMesh_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelElasticSupports.setBackground(new java.awt.Color(236, 236, 236));
        jPanelElasticSupports.setName("jPanelSupports"); // NOI18N
        jPanelElasticSupports.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelElasticSupportsMousePressed(evt);
                }
            }
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setText("Elastic Supports");

        javax.swing.GroupLayout jPanelElasticSupportsLayout = new javax.swing.GroupLayout(jPanelElasticSupports);
        jPanelElasticSupports.setLayout(jPanelElasticSupportsLayout);
        jPanelElasticSupportsLayout.setHorizontalGroup(
            jPanelElasticSupportsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelElasticSupportsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelElasticSupportsLayout.setVerticalGroup(
            jPanelElasticSupportsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelElasticSupports_Container.setBackground(new java.awt.Color(236, 236, 236));
        jPanelElasticSupports_Container.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanelElasticSupports_ContainerLayout = new javax.swing.GroupLayout(jPanelElasticSupports_Container);
        jPanelElasticSupports_Container.setLayout(jPanelElasticSupports_ContainerLayout);
        jPanelElasticSupports_ContainerLayout.setHorizontalGroup(
            jPanelElasticSupports_ContainerLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelElasticSupports_ContainerLayout.setVerticalGroup(
            jPanelElasticSupports_ContainerLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelSettlements.setBackground(new java.awt.Color(236, 236, 236));
        jPanelSettlements.setName("jPanelSettlements"); // NOI18N
        jPanelSettlements.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    jPanelSettlementsMousePressed(evt);
                }
            }
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setText("Settlements");

        javax.swing.GroupLayout jPanelSettlementsLayout = new javax.swing.GroupLayout(jPanelSettlements);
        jPanelSettlements.setLayout(jPanelSettlementsLayout);
        jPanelSettlementsLayout.setHorizontalGroup(
            jPanelSettlementsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelSettlementsLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        jPanelSettlementsLayout.setVerticalGroup(
            jPanelSettlementsLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanelSettlements_Container.setBackground(new java.awt.Color(236, 236, 236));
        jPanelSettlements_Container.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanelSettlements_ContainerLayout = new javax.swing.GroupLayout(jPanelSettlements_Container);
        jPanelSettlements_Container.setLayout(jPanelSettlements_ContainerLayout);
        jPanelSettlements_ContainerLayout.setHorizontalGroup(
            jPanelSettlements_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelSettlements_ContainerLayout.setVerticalGroup(
            jPanelSettlements_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE)
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
                        .addContainerGap(131, Short.MAX_VALUE)
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
                        .addContainerGap(167, Short.MAX_VALUE)
                        .addComponent(jButtonClose)
                        .addContainerGap()
                )
        );

        javax.swing.GroupLayout jPanelContainerLayout = new javax.swing.GroupLayout(jPanelContainer);
        jPanelContainer.setLayout(jPanelContainerLayout);
        jPanelContainerLayout.setHorizontalGroup(
            jPanelContainerLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelNodes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(
                    jPanelNodes_Container,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(jPanelSections, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(
                    jPanelSections_Container,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(jPanelMaterials, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(
                    jPanelMaterials_Container,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(jPanelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelMesh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelMesh_Container, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addComponent(
                    jPanelElasticSupports,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(jPanelElasticSupports_Container, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addComponent(
                    jPanelSettlements,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(jPanelSettlements_Container, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
        );
        jPanelContainerLayout.setVerticalGroup(
            jPanelContainerLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanelContainerLayout
                        .createSequentialGroup()
                        .addComponent(
                            jPanelNodes,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelNodes_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelSections,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelSections_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelMaterials,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelMaterials_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelMesh,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelMesh_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelElasticSupports,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelElasticSupports_Container,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelSettlements,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                        .addGap(0, 0, 0)
                        .addComponent(
                            jPanelSettlements_Container,
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
        setTextOfLabels();
        objectsOfLateralPanel();
    }

    /**
     * Método para abrir o painel lateral jPanelNodes
     */
    public void jPanelNodesMousePressed() {
        jPanelNodesMousePressed(null);
    }

    /**
     * Método para abrir o painel lateral jPanelSections
     */
    public void jPanelSectionsMousePressed() {
        jPanelSectionsMousePressed(null);
    }

    /**
     * Método para abrir o painel lateral jPanelMaterials
     */
    public void jPanelMaterialsMousePressed() {
        jPanelMaterialsMousePressed(null);
    }

    /**
     * Método para abrir o painel lateral jPanelMesh
     */
    public void jPanelMeshMousePressed() {
        jPanelMeshMousePressed(null);
    }

    /**
     * Método para abrir o painel lateral jPanelElasticSupports
     */
    public void jPanelElasticSupportsMousePressed() {
        jPanelElasticSupportsMousePressed(null);
    }

    /**
     * Método para abrir o painel lateral jPanelSettlements
     */
    public void jPanelSettlementsMousePressed() {
        jPanelSettlementsMousePressed(null);
    }

    /**
     * Método para alterar o modelo da ComboBox jCB_Sections
     *
     * @param model
     */
    public void setModeljCB_Sections(String[] model) {
        int lenght = model.length;
        String[] newModel = new String[lenght + 1];
        System.arraycopy(model, 0, newModel, 0, lenght);
        newModel[lenght] = "<New>";

        jCB_Sections.setModel(new javax.swing.DefaultComboBoxModel(newModel));
    }

    /**
     * Método para alterar o modelo da ComboBox jCB_Materials
     *
     * @param model
     */
    public void setModeljCB_Materials(String[] model) {
        int lenght = model.length;
        String[] newModel = new String[lenght + 1];
        System.arraycopy(model, 0, newModel, 0, lenght);
        newModel[lenght] = "<New>";

        jCB_Materials.setModel(new javax.swing.DefaultComboBoxModel(newModel));
    }

    /**
     * Método para alterar o modelo da ComboBox jCB_Supports
     *
     * @param model
     */
    public void setModeljCB_Supports(String[] model) {
        int lenght = model.length;
        String[] newModel = new String[lenght + 1];
        System.arraycopy(model, 0, newModel, 0, lenght);
        newModel[lenght] = "<New>";

        jCB_Supports.setModel(new javax.swing.DefaultComboBoxModel(newModel));
    }

    /**
     * Método para alterar o modelo da ComboBox jCB_Settlements
     *
     * @param model
     */
    public void setModeljCB_Settlements(String[] model) {
        int lenght = model.length;
        String[] newModel = new String[lenght + 1];
        System.arraycopy(model, 0, newModel, 0, lenght);
        newModel[lenght] = "<New>";

        jCB_Settlements.setModel(new javax.swing.DefaultComboBoxModel(newModel));
    }

    /**
     * Método para abrir o painel lateral jPanelBars
     *
     * @param evt
     */
    private void jPanelNodesMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelNodesMousePressed
        Dimension dimension = jLateralPanelNodes.getMinimumSize();
        jPanelNodes.setBackground(selectedColor);
        jPanelActivated = "jPanelNodes";
        jPanelActivated();

        jPanelNodes_Container.removeAll();
        jPanelNodes_Container.setSize(dimension);
        jPanelNodes_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelNodes_Container.setLayout(new BorderLayout());
        jPanelNodes_Container.add(jLateralPanelNodes);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelNodesMousePressed

    /**
     * Método para abrir o painel lateral jPanelSections
     *
     * @param evt
     */
    private void jPanelSectionsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelSectionsMousePressed
        Dimension dimension = jLateralJPanelSections.getMinimumSize();
        jPanelSections.setBackground(selectedColor);
        jPanelActivated = "jPanelSections";
        jPanelActivated();

        jPanelSections_Container.removeAll();
        jPanelSections_Container.setSize(dimension);
        jPanelSections_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelSections_Container.setLayout(new BorderLayout());
        jPanelSections_Container.add(jLateralJPanelSections);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelSectionsMousePressed

    /**
     * Método para abrir o painel lateral jPanelMaterials
     *
     * @param evt
     */
    private void jPanelMaterialsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelMaterialsMousePressed
        Dimension dimension = jLateralPanelMaterials.getMinimumSize();
        jPanelMaterials.setBackground(selectedColor);
        jPanelActivated = "jPanelMaterials";
        jPanelActivated();

        jPanelMaterials_Container.removeAll();
        jPanelMaterials_Container.setSize(dimension);
        jPanelMaterials_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelMaterials_Container.setLayout(new BorderLayout());
        jPanelMaterials_Container.add(jLateralPanelMaterials);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelMaterialsMousePressed

    /**
     * Método para abrir o painel lateral PanelMesh
     *
     * @param evt
     */
    private void jPanelMeshMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelMeshMousePressed
        Dimension dimension = jLateralPanelMesh.getMinimumSize();
        jPanelMesh.setBackground(selectedColor);
        jPanelActivated = "jPanelMesh";
        jPanelActivated();

        jPanelMesh_Container.removeAll();
        jPanelMesh_Container.setSize(dimension);
        jPanelMesh_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelMesh_Container.setLayout(new BorderLayout());
        jPanelMesh_Container.add(jLateralPanelMesh);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelMeshMousePressed

    private void jPanelElasticSupportsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelElasticSupportsMousePressed
        Dimension dimension = jLateralPanelSupports.getMinimumSize();
        jPanelElasticSupports.setBackground(selectedColor);
        jPanelActivated = "jPanelSupports";
        jPanelActivated();

        jPanelElasticSupports_Container.removeAll();
        jPanelElasticSupports_Container.setSize(dimension);
        jPanelElasticSupports_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelElasticSupports_Container.setLayout(new BorderLayout());
        jPanelElasticSupports_Container.add(jLateralPanelSupports);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelElasticSupportsMousePressed

    private void jPanelSettlementsMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jPanelSettlementsMousePressed
        Dimension dimension = jLateralPanelSettlements.getMinimumSize();
        jPanelSettlements.setBackground(selectedColor);
        jPanelActivated = "jPanelSettlements";
        jPanelActivated();

        jPanelSettlements_Container.removeAll();
        jPanelSettlements_Container.setSize(dimension);
        jPanelSettlements_Container.setPreferredSize(new java.awt.Dimension(dimension));
        jPanelSettlements_Container.setLayout(new BorderLayout());
        jPanelSettlements_Container.add(jLateralPanelSettlements);

        jPanelContainer.updateUI();
    } //GEN-LAST:event_jPanelSettlementsMousePressed

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
        if (!"jPanelNodes".equals(jPanelActivated)) {
            jPanelNodes.setBackground(notSelectedColor);
            jPanelActivated(jPanelActivated);
        }
        if (!"jPanelSections".equals(jPanelActivated)) {
            jPanelSections.setBackground(notSelectedColor);
            jPanelActivated(jPanelActivated);
        }
        if (!"jPanelMaterials".equals(jPanelActivated)) {
            jPanelMaterials.setBackground(notSelectedColor);
            jPanelActivated(jPanelActivated);
        }
        if (!"jPanelMesh".equals(jPanelActivated)) {
            jPanelMesh.setBackground(notSelectedColor);
            jPanelActivated(jPanelActivated);
        }
        if (!"jPanelSupports".equals(jPanelActivated)) {
            jPanelElasticSupports.setBackground(notSelectedColor);
            jPanelActivated(jPanelActivated);
        }
        if (!"jPanelSettlements".equals(jPanelActivated)) {
            jPanelSettlements.setBackground(notSelectedColor);
            jPanelActivated(jPanelActivated);
        }

        jPanelContainer.updateUI();
    }

    /**
     * Método para alterar a dimensão dos painéis selecionados
     *
     * @param jPanelName é o nome do painel selecionado
     */
    private void jPanelActivated(String jPanelName) {
        if (!"jPanelNodes".equals(jPanelName)) {
            jPanelNodes_Container.removeAll();
            jPanelNodes_Container.setSize(199, 0);
            jPanelNodes_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
        if (!"jPanelSections".equals(jPanelName)) {
            jPanelSections_Container.removeAll();
            jPanelSections_Container.setSize(199, 0);
            jPanelSections_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
        if (!"jPanelMaterials".equals(jPanelName)) {
            jPanelMaterials_Container.removeAll();
            jPanelMaterials_Container.setSize(199, 0);
            jPanelMaterials_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
        if (!"jPanelMesh".equals(jPanelName)) {
            jPanelMesh_Container.removeAll();
            jPanelMesh_Container.setSize(199, 0);
            jPanelMesh_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
        if (!"jPanelSupports".equals(jPanelName)) {
            jPanelElasticSupports_Container.removeAll();
            jPanelElasticSupports_Container.setSize(199, 0);
            jPanelElasticSupports_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
        if (!"jPanelSettlements".equals(jPanelName)) {
            jPanelSettlements_Container.removeAll();
            jPanelSettlements_Container.setSize(199, 0);
            jPanelSettlements_Container.setPreferredSize(new java.awt.Dimension(199, 0));
        }
    }

    /**
     * Método para definir o texto a apresentar nos rótulos
     */
    private void setTextOfLabels() {
        if (null != type) switch (type) {
            case "2D":
                jLabelThickness.setText("Thickness (m)");
                break;
            case "Beams":
                jLabelThickness.setText("Height (m)");
                jLabelSupportsB.setText("Kz (KN/m)");
                jLabelSupportsC.setText("Ky (KNm/rad)");
                jLabelSettlementsB.setText("Dz (m)");
                jLabelSettlementsC.setText("Ry (rad)");
                break;
            case "Slabs":
                jLabelThickness.setText("Thickness (m)");
                break;
            default:
                jLabelThickness.setText("Height (m)");
                jLabelSupportsB.setText("Ky (KN/m)");
                jLabelSupportsC.setText("Kz (KNm/rad)");
                jLabelSettlementsB.setText("Dy (m)");
                jLabelSettlementsC.setText("Rz (rad)");
                break;
        }
    }

    /**
     * Método para definir o estado dos objetos da classe
     */
    private void objectsOfLateralPanel() {
        jLabelNodesTitle.setEnabled(false);
        jLabelNodesA.setEnabled(false);
        jLabelNodesB.setEnabled(false);
        jLabelNodesC.setEnabled(false);
        jLabelMeshTitle.setEnabled(false);
        jLabelMeshA.setEnabled(false);
        jLabelMeshB.setEnabled(false);
        jLabelMeshC.setEnabled(false);
        jLabelInertiaA.setEnabled(false);
        jLabelInertiaB.setEnabled(false);
        jLabelInertiaC.setEnabled(false);
        jLabelTorsionA.setEnabled(false);
        jLabelTorsionB.setEnabled(false);
        jLabelTorsionC.setEnabled(false);
        jLabelAreaA.setEnabled(false);
        jLabelAreaB.setEnabled(false);
        jLabelAreaC.setEnabled(false);
        jLabelThickness.setEnabled(false);
        jLabelSupportsTitle.setEnabled(false);
        jLabelSupportsA.setEnabled(false);
        jLabelSupportsB.setEnabled(false);
        jLabelSupportsC.setEnabled(false);
        jLabelSettlementsTitle.setEnabled(false);
        jLabelSettlementsA.setEnabled(false);
        jLabelSettlementsB.setEnabled(false);
        jLabelSettlementsC.setEnabled(false);

        jCB_NodesLines.setEnabled(false);
        jCB_NodesTriangles.setEnabled(false);
        jCB_NodesQuadrilaterals.setEnabled(false);
        jCB_MeshLines.setEnabled(false);
        jCB_MeshTriangles.setEnabled(false);
        jCB_MeshRectQuadril.setEnabled(false);
        jCB_Sections.setEnabled(true);
        jCB_Materials.setEnabled(true);
        jCB_Supports.setEnabled(true);
        jCB_Settlements.setEnabled(true);

        jCB_NodesLines.setSelectedIndex(0);
        jCB_NodesTriangles.setSelectedIndex(0);
        jCB_NodesQuadrilaterals.setSelectedIndex(0);
        jCB_MeshLines.setSelectedIndex(0);
        jCB_MeshTriangles.setSelectedIndex(0);
        jCB_MeshRectQuadril.setSelectedIndex(0);

        jB_SectionsEdit.setEnabled(true);
        jB_SectionsSave.setEnabled(false);
        jB_SectionsDelete.setEnabled(true);
        jB_MaterialsEdit.setEnabled(true);
        jB_MaterialsSave.setEnabled(false);
        jB_MaterialsDelete.setEnabled(true);
        jTF_SectionsInercia.setEnabled(false);
        jTF_SectionsTorsion.setEnabled(false);
        jTF_SectionsArea.setEnabled(false);
        jTF_SectionsThickness.setEnabled(false);
        jTF_MaterialsElasticity.setEnabled(false);
        jTF_MaterialsPoisson.setEnabled(false);
        jTF_MaterialsThermal.setEnabled(false);
        jB_SupportsEdit.setEnabled(true);
        jB_SupportsSave.setEnabled(false);
        jB_SupportsDelete.setEnabled(true);
        jB_SettlementsEdit.setEnabled(true);
        jB_SettlementsSave.setEnabled(false);
        jB_SettlementsDelete.setEnabled(true);

        jTF_SupportsKx.setEnabled(false);
        jTF_SupportsKy.setEnabled(false);
        jTF_SupportsKz.setEnabled(false);
        jTF_SettlementsDx.setEnabled(false);
        jTF_SettlementsDy.setEnabled(false);
        jTF_SettlementsRz.setEnabled(false);

        switch (this.type) {
            case "1D":
                jLabelAreaA.setEnabled(true);
                jLabelAreaB.setEnabled(true);
                jLabelAreaC.setEnabled(true);
                jLabelSupportsTitle.setEnabled(true);
                jLabelSupportsA.setEnabled(true);
                jLabelSupportsB.setEnabled(true);
                jLabelSettlementsTitle.setEnabled(true);
                jLabelSettlementsA.setEnabled(true);
                jLabelSettlementsB.setEnabled(true);
                break;
            case "2D":
                jLabelNodesTitle.setEnabled(true);
                jLabelNodesB.setEnabled(true);
                jLabelNodesC.setEnabled(true);
                jLabelMeshTitle.setEnabled(true);
                jLabelMeshB.setEnabled(true);
                jLabelMeshC.setEnabled(true);
                jLabelThickness.setEnabled(true);
                jLabelSupportsTitle.setEnabled(true);
                jLabelSupportsA.setEnabled(true);
                jLabelSupportsB.setEnabled(true);
                jLabelSettlementsTitle.setEnabled(true);
                jLabelSettlementsA.setEnabled(true);
                jLabelSettlementsB.setEnabled(true);

                jCB_NodesTriangles.setEnabled(true);
                jCB_NodesQuadrilaterals.setEnabled(true);
                jCB_MeshTriangles.setEnabled(true);
                jCB_MeshRectQuadril.setEnabled(true);
                break;
            case "3D":
                //TODO add your handling code here
                break;
            case "Beams":
                jLabelNodesTitle.setEnabled(true);
                jLabelNodesA.setEnabled(true);
                jLabelMeshTitle.setEnabled(true);
                jLabelMeshA.setEnabled(true);
                jLabelInertiaA.setEnabled(true);
                jLabelInertiaB.setEnabled(true);
                jLabelInertiaC.setEnabled(true);
                jLabelAreaA.setEnabled(true);
                jLabelAreaB.setEnabled(true);
                jLabelAreaC.setEnabled(true);
                jLabelThickness.setEnabled(true);
                jLabelSupportsTitle.setEnabled(true);
                jLabelSupportsB.setEnabled(true);
                jLabelSupportsC.setEnabled(true);
                jLabelSettlementsTitle.setEnabled(true);
                jLabelSettlementsB.setEnabled(true);
                jLabelSettlementsC.setEnabled(true);

                jCB_NodesLines.setEnabled(true);
                jCB_MeshLines.setEnabled(true);
                break;
            case "Frames":
                jLabelMeshTitle.setEnabled(true);
                jLabelMeshA.setEnabled(true);
                jLabelInertiaA.setEnabled(true);
                jLabelInertiaB.setEnabled(true);
                jLabelInertiaC.setEnabled(true);
                jLabelAreaA.setEnabled(true);
                jLabelAreaB.setEnabled(true);
                jLabelAreaC.setEnabled(true);
                jLabelThickness.setEnabled(true);
                jLabelSupportsTitle.setEnabled(true);
                jLabelSupportsA.setEnabled(true);
                jLabelSupportsB.setEnabled(true);
                jLabelSupportsC.setEnabled(true);
                jLabelSettlementsTitle.setEnabled(true);
                jLabelSettlementsA.setEnabled(true);
                jLabelSettlementsB.setEnabled(true);
                jLabelSettlementsC.setEnabled(true);

                jCB_MeshLines.setEnabled(true);
                break;
            case "Grids":
                jLabelMeshTitle.setEnabled(true);
                jLabelMeshA.setEnabled(true);
                jLabelInertiaA.setEnabled(true);
                jLabelInertiaB.setEnabled(true);
                jLabelInertiaC.setEnabled(true);
                jLabelTorsionA.setEnabled(true);
                jLabelTorsionB.setEnabled(true);
                jLabelTorsionC.setEnabled(true);
                jLabelAreaA.setEnabled(true);
                jLabelAreaB.setEnabled(true);
                jLabelAreaC.setEnabled(true);
                jLabelThickness.setEnabled(true);
                jLabelSupportsTitle.setEnabled(false);
                jLabelSupportsA.setEnabled(false);
                jLabelSupportsB.setEnabled(false);
                jLabelSupportsC.setEnabled(false);
                jLabelSettlementsTitle.setEnabled(false);
                jLabelSettlementsA.setEnabled(false);
                jLabelSettlementsB.setEnabled(false);
                jLabelSettlementsC.setEnabled(false);

                jCB_MeshLines.setEnabled(true);
                jCB_Supports.setEnabled(false);
                jCB_Settlements.setEnabled(false);

                jB_SupportsEdit.setEnabled(false);
                jB_SupportsSave.setEnabled(false);
                jB_SupportsDelete.setEnabled(false);
                jB_SettlementsEdit.setEnabled(false);
                jB_SettlementsSave.setEnabled(false);
                jB_SettlementsDelete.setEnabled(false);
                break;
            case "Slabs":
                jLabelNodesTitle.setEnabled(true);
                jLabelNodesC.setEnabled(true);
                jLabelMeshTitle.setEnabled(true);
                jLabelMeshC.setEnabled(true);
                jLabelThickness.setEnabled(true);
                jLabelSupportsTitle.setEnabled(false);
                jLabelSupportsA.setEnabled(false);
                jLabelSupportsB.setEnabled(false);
                jLabelSupportsC.setEnabled(false);
                jLabelSettlementsTitle.setEnabled(false);
                jLabelSettlementsA.setEnabled(false);
                jLabelSettlementsB.setEnabled(false);
                jLabelSettlementsC.setEnabled(false);

                jCB_NodesQuadrilaterals.setEnabled(true);
                jCB_MeshRectQuadril.setEnabled(true);
                jCB_Supports.setEnabled(false);
                jCB_Settlements.setEnabled(false);

                jB_SupportsEdit.setEnabled(false);
                jB_SupportsSave.setEnabled(false);
                jB_SupportsDelete.setEnabled(false);
                jB_SettlementsEdit.setEnabled(false);
                jB_SettlementsSave.setEnabled(false);
                jB_SettlementsDelete.setEnabled(false);
                break;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jB_MaterialsDelete;
    public javax.swing.JButton jB_MaterialsEdit;
    public javax.swing.JButton jB_MaterialsSave;
    public javax.swing.JButton jB_SectionsDelete;
    public javax.swing.JButton jB_SectionsEdit;
    public javax.swing.JButton jB_SectionsSave;
    public javax.swing.JButton jB_SettlementsDelete;
    public javax.swing.JButton jB_SettlementsEdit;
    public javax.swing.JButton jB_SettlementsSave;
    public javax.swing.JButton jB_SupportsDelete;
    public javax.swing.JButton jB_SupportsEdit;
    public javax.swing.JButton jB_SupportsSave;
    public javax.swing.JButton jButtonClose;
    public javax.swing.JComboBox jCB_Materials;
    public javax.swing.JComboBox jCB_MeshLines;
    public javax.swing.JComboBox jCB_MeshRectQuadril;
    public javax.swing.JComboBox jCB_MeshTriangles;
    public javax.swing.JComboBox jCB_NodesLines;
    public javax.swing.JComboBox jCB_NodesQuadrilaterals;
    public javax.swing.JComboBox jCB_NodesTriangles;
    public javax.swing.JComboBox jCB_Sections;
    public javax.swing.JComboBox jCB_Settlements;
    public javax.swing.JComboBox jCB_Supports;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelAreaA;
    private javax.swing.JLabel jLabelAreaB;
    private javax.swing.JLabel jLabelAreaC;
    private javax.swing.JLabel jLabelElasticity;
    private javax.swing.JLabel jLabelInertiaA;
    private javax.swing.JLabel jLabelInertiaB;
    private javax.swing.JLabel jLabelInertiaC;
    private javax.swing.JLabel jLabelMaterial;
    private javax.swing.JLabel jLabelMeshA;
    private javax.swing.JLabel jLabelMeshB;
    private javax.swing.JLabel jLabelMeshC;
    private javax.swing.JLabel jLabelMeshTitle;
    private javax.swing.JLabel jLabelNodesA;
    private javax.swing.JLabel jLabelNodesB;
    private javax.swing.JLabel jLabelNodesC;
    private javax.swing.JLabel jLabelNodesTitle;
    private javax.swing.JLabel jLabelPoisson;
    private javax.swing.JLabel jLabelSectionsTitle;
    private javax.swing.JLabel jLabelSettlementsA;
    private javax.swing.JLabel jLabelSettlementsB;
    private javax.swing.JLabel jLabelSettlementsC;
    private javax.swing.JLabel jLabelSettlementsTitle;
    private javax.swing.JLabel jLabelSupportsA;
    private javax.swing.JLabel jLabelSupportsB;
    private javax.swing.JLabel jLabelSupportsC;
    private javax.swing.JLabel jLabelSupportsTitle;
    private javax.swing.JLabel jLabelThermal;
    private javax.swing.JLabel jLabelThickness;
    private javax.swing.JLabel jLabelTorsionA;
    private javax.swing.JLabel jLabelTorsionB;
    private javax.swing.JLabel jLabelTorsionC;
    private javax.swing.JPanel jLateralJPanelSections;
    private javax.swing.JPanel jLateralPanelMaterials;
    private javax.swing.JPanel jLateralPanelMesh;
    private javax.swing.JPanel jLateralPanelNodes;
    private javax.swing.JPanel jLateralPanelSettlements;
    private javax.swing.JPanel jLateralPanelSupports;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelContainer;
    private javax.swing.JPanel jPanelElasticSupports;
    private javax.swing.JPanel jPanelElasticSupports_Container;
    private javax.swing.JPanel jPanelMaterials;
    private javax.swing.JPanel jPanelMaterials_Container;
    private javax.swing.JPanel jPanelMesh;
    private javax.swing.JPanel jPanelMesh_Container;
    private javax.swing.JPanel jPanelNodes;
    private javax.swing.JPanel jPanelNodes_Container;
    private javax.swing.JPanel jPanelSections;
    private javax.swing.JPanel jPanelSections_Container;
    private javax.swing.JPanel jPanelSeparator;
    private javax.swing.JPanel jPanelSettlements;
    private javax.swing.JPanel jPanelSettlements_Container;
    public javax.swing.JTextField jTF_MaterialsElasticity;
    public javax.swing.JTextField jTF_MaterialsPoisson;
    public javax.swing.JTextField jTF_MaterialsThermal;
    public javax.swing.JTextField jTF_SectionsArea;
    public javax.swing.JTextField jTF_SectionsInercia;
    public javax.swing.JTextField jTF_SectionsThickness;
    public javax.swing.JTextField jTF_SectionsTorsion;
    public javax.swing.JTextField jTF_SettlementsDx;
    public javax.swing.JTextField jTF_SettlementsDy;
    public javax.swing.JTextField jTF_SettlementsRz;
    public javax.swing.JTextField jTF_SupportsKx;
    public javax.swing.JTextField jTF_SupportsKy;
    public javax.swing.JTextField jTF_SupportsKz;
    // End of variables declaration//GEN-END:variables
}
