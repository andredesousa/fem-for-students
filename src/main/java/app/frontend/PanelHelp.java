/*
 * Classe para criar o painel que contém a ajuda do programa
 * Este painel é adicionado à janela Help criada na classe UserInterface
 * O texto apresentado está construído em HTML
 */

package app.frontend;

/**
 *
 * @author André de Sousa
 */
public class PanelHelp extends javax.swing.JPanel {

    /**
     * Creates new form JPanelHelp
     */
    public PanelHelp() {
        initComponents();

        jLabelA.setText(
            "<html><p align='justify'>After initialization, the program displays a panel that allows the user to choose the type of structural model that want for your project. This panel appears at the center of the program window lets you choose the type of structural model or open a project created earlier.</p><p></p><p align='justify'>Of different formulations and types of finite elements available are focused on one-dimensional, two-dimensional, three-dimensional finite elements, beams and slabs. Beam elements are formulated by Euler-Bernoulli theory and Timoshenko theory. The finite elements of slab presented are formulated by Kirchhoff theory and Reissner-Mindlin theory.</p><p></p><p align='justify'>Taking into account the combination of different formulations of finite elements, in this version, the following structural models are available:</p><ul><li>Plane Trusses</li><li>Beams</li><li>Plane Frames</li><li>Plane Stresses/Strains</li><li>Grids</li><li>Slabs</li></ul><p align='justify'>Selecting the type of model to develop the project appears the area of modeling of the program and available tools for the design of finite element and all other elements necessary for structural analysis. In order to make a simple program, such tools are arranged in a set of tabs sequentially arranged by the steps of modeling, structural analysis and display of results.</p></html>"
        );
        jLabelB.setText(
            "<html><p align='justify'>The tools and features of the program are organized into the following six tabs:</p><ul><li>Draw</li><li>View</li><li>Geometry</li><li>Loads</li><li>Analysis</li><li>Results</li></ul><p align='justify'>The Draw tab contains the tools needed to design the different finite elements available in the program. Has a few productivity features, including selection of the finite elements, move, cut, copy and paste.</p><p></p><p align='justify'>The View tab contains visualization tools, in particular, offers the possibility to move the panel, zoom and display a mesh points and other features that facilitate the design of finite elements.</p><p></p><p align='justify'>The Geometry tab contains the functionality to setting the number of nodes of the finite elements, the remaining properties of sections of finite elements, material properties, refinement of finite element meshes and placing of structural supports.</p><p></p><p align='justify'>The Loads tab contains tools for assignment of loads to the structural model built. Depending on the structural model can add loads as concentrated loads, bending moments, linear distributed loads, axial loads distributed, surface loads, thermal loads and/or consider the self-weight of the finite elements.</p><p></p><p align='justify'>For the Analysis tab, this provides, for example, for the finite element beam and slab selection of theory formulation. Enables even choose the analysis will be analytical or numerical and the button to perform the calculation of the structure.</p><p></p><p align='justify'>The Results tab, depending on the selected model, it is possible to see, for example, the global system equations, all results the level of each finite element, the deformed shape of the structure, diagrams for bars, maps of stresses and/or stresses and principal directions.</p></html>"
        );
        jLabelC.setText(
            "<html><ul><li>(L) Draw a line</li><li>(M) Move the selected objects</li><li>(P) Insert a point</li><li>(Q) Draw a quadrilateral</li><li>(R) Draw a rectangle</li><li>(S) Select drawn objects</li><li>(T) Draw a triangle</li><li>(F1) Help</li><li>(CTRL + C) Copy the selected objects</li><li>(CTRL + N) New project</li><li>(CTRL + O) Open project</li><li>(CTRL + S) Save project</li><li>(CTRL + V) Paste the selected objects</li><li>(CTRL + W) Close project</li><li>(CTRL + X) Delete the selected objects</li><li>(CTRL + Y) Redo</li><li>(CTRL + Z) Undo</li><li>(DELETE) Delete the selected objects</li><li>(ESCAPE) Cancel, unselect or exit from the results pane</li></ul></html>"
        );
        jLabelD.setText(
            "<html><p align='justify'>In the Results tab are available the options to choose the type of results obtained by finite element analysis.</p><p></p><p align='justify'>In the first group of buttons, options for the visualization of the global system of equations are available. In the second group the options for viewing the results in tables at the level of finite elements are available. Thus, besides the global system of equations, you can see the balance equation for each finite element, the support reactions, nodal forces and nodal stresses. In the latter group are concentrated features for graphical representation of results.</p><p></p><p align='justify'>For all models it is possible to visualize the deformed structure. In addition, for models of bars you can view diagrams of forces and for two-dimensional models the isovalue lines, maps of stresses and stresses and principal directions. Regarding the display of isovalue lines and maps of stresses, the user can choose that stress want to see represented.</p></html>"
        );
        jLabelE.setText(
            "<html><p align='justify'>Other functionalities of the program are concentrated in the File, Edit and Help menus. So that users can store projects created and subsequently retrieve them, the program allows recording projects in the permanent memory of computer.</p><p></p><p align='justify'>The program also presents, for example, information relating to the positioning of the mouse and tips on the operation of the selected tools. These tips are always presented to the user selects particular tool and are intended to inform you about the operating mode of this. These elements are arranged in the lower pane of the user interface.</p><p></p><p align='justify'>The developed program allows modeling simple problems with various finite element formulations. Models of discrete systems available allow the study of articulated structures, frame structures, beams and grids. For solid bodies, the user can choose between a plane stress or strain and the study of slabs by the two theories available.</p></html>"
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabelE = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabelA = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelB = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelD = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelC = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(500, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabelE.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelE.setText("<html></html>");
        jLabelE.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Display the Results");

        jLabelA.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelA.setText("<html></html>");
        jLabelA.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelA.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Initial Panel and Tabs");

        jLabelB.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelB.setText("<html></html>");
        jLabelB.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Description of the Tabs");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Other Functionalities");

        jLabelD.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelD.setText("<html></html>");
        jLabelD.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Keyboard Shortcuts");

        jLabelC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelC.setText("<html></html>");
        jLabelC.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel1Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            jPanel1Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    jLabelA,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE
                                )
                                .addComponent(
                                    jLabelB,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE
                                )
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(
                                    jLabelD,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE
                                )
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(
                                    jLabelE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE
                                )
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(
                                    jLabelC,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE
                                )
                        )
                )
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    jPanel1Layout
                        .createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(25, 25, 25)
                )
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
        layout.setVerticalGroup(
            layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                        .addContainerGap()
                )
        );
    } // </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelA;
    private javax.swing.JLabel jLabelB;
    private javax.swing.JLabel jLabelC;
    private javax.swing.JLabel jLabelD;
    private javax.swing.JLabel jLabelE;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
