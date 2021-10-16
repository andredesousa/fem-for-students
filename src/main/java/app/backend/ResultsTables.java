/*
 * Esta classe fornce método para construir o conteúdo das tabelas
 * Os métodos requerem o modelo da tabela e a lista de resultados
 * Os métodos fornecem o modelo da tabela já preenchido
 */

package app.backend;

import static app.calculations.FormatResults.decimalFormat;

import app.calculations.FiniteElement;
import app.calculations.FormatResults;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author André de Sousa
 */
public class ResultsTables {

    /**
     * Método para imprimir esforços em elementos finitos unidimensionais
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalForces_1D(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int id = finiteElement.getID();
            int nodes = finiteElement.getNodes();

            ArrayList<double[][]> nodalForces = finiteElement.getNodalForces();

            int i = 0;
            double[][] nodalForce = new double[nodalForces.size()][1];
            for (double[][] nodal : nodalForces) {
                nodalForce[i][0] = nodal[0][0];
                i++;
            }

            String[][] forces = FormatResults.getNodalForces("1D", id, nodes, nodalForce);

            for (String[] force : forces) {
                String n = " " + String.valueOf(line);
                String element = " " + force[0];
                String node = " " + force[1];
                String nForce = " " + force[2];
                String unit = " " + force[3];
                String description = " " + force[4];

                table.addRow(new String[] { n, element, node, nForce, unit, description });
                line++;
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }

    /**
     * Método para imprimir esforços em elementos finitos bidimensionais
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalForces_2D(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int id = finiteElement.getID();
            int nodes = finiteElement.getNodes();
            String theory = finiteElement.getTheory();

            ArrayList<double[][]> nodalForces = finiteElement.getNodalForces();

            int i = 0;
            double[][] nodalForce = new double[nodalForces.size() * 3][1];
            for (double[][] nodal : nodalForces) {
                nodalForce[i][0] = nodal[0][0];
                nodalForce[i + 1][0] = nodal[1][0];
                nodalForce[i + 2][0] = nodal[2][0];
                i = i + 3;
            }

            String[][] forces = FormatResults.getNodalForces("2D", id, nodes, theory, nodalForce);

            for (String[] force : forces) {
                String n = " " + String.valueOf(line);
                String element = " " + force[0];
                String node = " " + force[1];
                String nForce = " " + force[2];
                String unit = " " + force[3];
                String description = " " + force[4];

                table.addRow(new String[] { n, element, node, nForce, unit, description });
                line++;
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }

    /**
     * Método para imprimir esforços em elementos finitos tridimensionais
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalForces_3D(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }

    /**
     * Método para imprimir esforços em elementos finitos de viga
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalForces_Beams(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int id = finiteElement.getID();
            int nodes = finiteElement.getNodes();

            ArrayList<double[][]> nodalForces = finiteElement.getNodalForces();

            int i = 0;
            double[][] nodalForce = new double[nodalForces.size() * 2][1];
            for (double[][] nodal : nodalForces) {
                nodalForce[i][0] = nodal[0][0];
                nodalForce[i + 1][0] = nodal[1][0];
                i = i + 2;
            }

            String[][] forces = FormatResults.getNodalForces("Beams", id, nodes, nodalForce);

            for (String[] force : forces) {
                String n = " " + String.valueOf(line);
                String element = " " + force[0];
                String node = " " + force[1];
                String nForce = " " + force[2];
                String unit = " " + force[3];
                String description = " " + force[4];

                table.addRow(new String[] { n, element, node, nForce, unit, description });
                line++;
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }

    /**
     * Método para imprimir esforços em elementos finitos de barra
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalForces_Frames(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int id = finiteElement.getID();
            int nodes = finiteElement.getNodes();

            ArrayList<double[][]> nodalForces = finiteElement.getNodalForces();

            int i = 0;
            double[][] nodalForce = new double[nodalForces.size() * 2][1];
            for (double[][] nodal : nodalForces) {
                nodalForce[i][0] = nodal[0][0];
                nodalForce[i + 1][0] = nodal[1][0];
                i = i + 2;
            }

            String[][] forces = FormatResults.getNodalForces("Frames", id, nodes, nodalForce);

            for (String[] force : forces) {
                String n = " " + String.valueOf(line);
                String element = " " + force[0];
                String node = " " + force[1];
                String nForce = " " + force[2];
                String unit = " " + force[3];
                String description = " " + force[4];

                table.addRow(new String[] { n, element, node, nForce, unit, description });
                line++;
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }

    /**
     * Método para imprimir esforços em elementos finitos de grelha
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalForces_Grids(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int id = finiteElement.getID();
            int nodes = finiteElement.getNodes();

            ArrayList<double[][]> nodalForces = finiteElement.getNodalForces();

            int i = 0;
            double[][] nodalForce = new double[nodalForces.size() * 2][1];
            for (double[][] nodal : nodalForces) {
                nodalForce[i][0] = nodal[0][0];
                nodalForce[i + 1][0] = nodal[1][0];
                i = i + 2;
            }

            String[][] forces = FormatResults.getNodalForces("Grids", id, nodes, nodalForce);

            for (String[] force : forces) {
                String n = " " + String.valueOf(line);
                String element = " " + force[0];
                String node = " " + force[1];
                String nForce = " " + force[2];
                String unit = " " + force[3];
                String description = " " + force[4];

                table.addRow(new String[] { n, element, node, nForce, unit, description });
                line++;
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }

    /**
     * Método para imprimir esforços em elementos finitos de laje
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalForces_Slabs(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int id = finiteElement.getID();
            int nodes = finiteElement.getNodes();

            ArrayList<double[][]> nodalForces = finiteElement.getNodalForces();

            int i = 0;
            double[][] nodalForce = new double[nodalForces.size() * 5][1];
            for (double[][] nodal : nodalForces) {
                nodalForce[i][0] = nodal[0][0];
                nodalForce[i + 1][0] = nodal[1][0];
                nodalForce[i + 2][0] = nodal[2][0];
                nodalForce[i + 3][0] = nodal[3][0];
                nodalForce[i + 4][0] = nodal[4][0];
                i = i + 5;
            }

            String[][] forces = FormatResults.getNodalForces("Slabs", id, nodes, nodalForce);

            for (String[] force : forces) {
                String n = " " + String.valueOf(line);
                String element = " " + force[0];
                String node = " " + force[1];
                String nForce = " " + force[2];
                String unit = " " + force[3];
                String description = " " + force[4];

                table.addRow(new String[] { n, element, node, nForce, unit, description });
                line++;
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }

    /**
     * Método para imprimir tensões em elementos finitos unidimensionais
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalStresses_1D(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int id = finiteElement.getID();
            ArrayList<double[][]> nodalStresses = finiteElement.getNodalStresses();

            int nodes = 1;
            for (double[][] stresses : nodalStresses) {
                String n = " " + String.valueOf(line);
                String element = " " + String.valueOf(id);
                String node = " " + String.valueOf(nodes);
                String σ = " " + decimalFormat(stresses[0][0]);

                table.addRow(new String[] { n, element, node, σ });
                line++;
                nodes++;
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }

    /**
     * Método para imprimir tensões em elementos finitos bidimensionais
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalStresses_2D(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int id = finiteElement.getID();
            ArrayList<double[][]> nodalStresses = finiteElement.getNodalStresses();

            int nodes = 1;
            for (double[][] stresses : nodalStresses) {
                String n = " " + String.valueOf(line);
                String element = " " + String.valueOf(id);
                String node = " " + String.valueOf(nodes);
                String σx = " " + decimalFormat(stresses[0][0]);
                String σy = " " + decimalFormat(stresses[1][0]);
                String τxy = " " + decimalFormat(stresses[2][0]);

                table.addRow(new String[] { n, element, node, σx, σy, τxy });
                line++;
                nodes++;
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }

    /**
     * Método para imprimir tensões em elementos finitos tridimensionais
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalStresses_3D(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        return null;
    }

    /**
     * Método para imprimir tensões em elementos finitos de viga
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalStresses_Beams(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            ArrayList<double[][]> nodalStresses = finiteElement.getNodalStresses();
            String theory = finiteElement.getTheory();
            int id = finiteElement.getID();

            int nodes = 1;
            for (double[][] stresses : nodalStresses) {
                String n, element, node, σtop, σbottom, τ;

                n = " " + String.valueOf(line);
                element = " " + String.valueOf(id);
                node = " " + String.valueOf(nodes);
                switch (theory) {
                    case "Euler-Bernoulli":
                        σtop = " " + decimalFormat(stresses[0][0]);
                        σbottom = " " + decimalFormat(stresses[0][1]);
                        table.addRow(new String[] { n, element, node, σtop, σbottom, " 0" });
                        line++;
                        nodes++;
                        break;
                    case "Timoshenko":
                        σtop = " " + decimalFormat(stresses[0][0]);
                        σbottom = " " + decimalFormat(stresses[0][1]);
                        τ = " " + decimalFormat(stresses[0][2]);
                        table.addRow(new String[] { n, element, node, σtop, σbottom, τ });
                        line++;
                        nodes++;
                        break;
                }
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }

    /**
     * Método para imprimir tensões em elementos finitos de barra
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalStresses_Frames(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            ArrayList<double[][]> nodalStresses = finiteElement.getNodalStresses();
            int id = finiteElement.getID();

            int nodes = 1;
            for (double[][] stresses : nodalStresses) {
                String n, element, node, σ, σtop, σbottom, τ;

                n = " " + String.valueOf(line);
                element = " " + String.valueOf(id);
                node = " " + String.valueOf(nodes);
                σ = " " + decimalFormat(stresses[0][0]);
                σtop = " " + decimalFormat(stresses[0][1]);
                σbottom = " " + decimalFormat(stresses[0][2]);
                table.addRow(new String[] { n, element, node, σ, σtop, σbottom });
                line++;
                nodes++;
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }
        return table;
    }

    /**
     * Método para imprimir tensões em elementos finitos de grelha
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalStresses_Grids(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            ArrayList<double[][]> nodalStresses = finiteElement.getNodalStresses();
            int id = finiteElement.getID();

            int nodes = 1;
            for (double[][] stresses : nodalStresses) {
                String n, element, node, σ, σtop, σbottom, τ;

                n = " " + String.valueOf(line);
                element = " " + String.valueOf(id);
                node = " " + String.valueOf(nodes);
                σ = " " + decimalFormat(stresses[0][0]);
                σtop = " " + decimalFormat(stresses[0][1]);
                σbottom = " " + decimalFormat(stresses[0][2]);
                table.addRow(new String[] { n, element, node, σ, σtop, σbottom });
                line++;
                nodes++;
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }
        return table;
    }

    /**
     * Método para imprimir tensões em elementos finitos de laje
     *
     * @param table
     * @param arrayListFiniteElements
     * @return
     */
    public static DefaultTableModel nodalStresses_Slabs(DefaultTableModel table, ArrayList<FiniteElement> arrayListFiniteElements) {
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            table.removeRow(0);
        }

        int line = 1;
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            ArrayList<double[][]> nodalStresses = finiteElement.getNodalStresses();
            String theory = finiteElement.getTheory();
            int id = finiteElement.getID();

            int nodes = 1;
            for (double[][] stresses : nodalStresses) {
                String n, element, node, σxtop, σytop, τxytop, σxbottom, σybottom, τxybottom, τxz, τyz;

                n = " " + String.valueOf(line);
                element = " " + String.valueOf(id);
                node = " " + String.valueOf(nodes);
                σxtop = " " + decimalFormat(stresses[0][0]);
                σytop = " " + decimalFormat(stresses[1][0]);
                τxytop = " " + decimalFormat(stresses[2][0]);
                σxbottom = " " + decimalFormat(stresses[3][0]);
                σybottom = " " + decimalFormat(stresses[4][0]);
                τxybottom = " " + decimalFormat(stresses[5][0]);
                switch (theory) {
                    case "Kirchhoff":
                        table.addRow(new String[] { n, element, node, σxtop, σytop, τxytop, σxbottom, σybottom, τxybottom, " 0", " 0" });
                        line++;
                        nodes++;
                        break;
                    case "Reissner-Mindlin":
                        τxz = " " + decimalFormat(stresses[6][0]);
                        τyz = " " + decimalFormat(stresses[7][0]);
                        table.addRow(new String[] { n, element, node, σxtop, σytop, τxytop, σxbottom, σybottom, τxybottom, τxz, τyz });
                        line++;
                        nodes++;
                        break;
                }
            }
        }

        if (line < 40) {
            for (int i = line; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }

    /**
     * Método para construir a tabela com o conteúdo de um vetor
     *
     * @param table
     * @param vector
     * @return
     */
    public static DefaultTableModel printVector(DefaultTableModel table, double[][] vector) {
        int i = 1;
        for (double[] vector1 : vector) {
            String line = " " + String.valueOf(i);
            String value = " " + decimalFormat(vector1[0]);

            table.addRow(new String[] { line, value, "" });
            i++;
        }

        //Adição de linhas se o número total for inferior a 40
        if (i < 40) {
            for (int j = i; j <= 40; j++) {
                table.addRow(new String[] { " " + String.valueOf(i), "", "" });
                i++;
            }
        }

        return table;
    }

    /**
     * Método para construir a tabela com o conteúdo de uma matriz
     *
     * @param stiffnessMatrix
     * @return
     */
    public static DefaultTableModel printMatrix(double[][] stiffnessMatrix) {
        DefaultTableModel table = new DefaultTableModel();

        //Construção das linhas e colunas da tabela
        int length = stiffnessMatrix.length;

        table.addColumn("");
        for (int i = 1; i <= length; i++) {
            table.addColumn(String.valueOf(i));
        }

        for (int i = 0; i < length; i++) {
            String[] lineResults = new String[length + 1];

            //Construção do texto para uma linha da tabela
            lineResults[0] = " " + String.valueOf(i + 1);
            for (int j = 0; j < length; j++) {
                lineResults[j + 1] = " " + decimalFormat(stiffnessMatrix[i][j]);
            }

            table.addRow(lineResults);
        }

        //Adição de colunas se o número total for inferior a 20
        if (length < 20) {
            for (int i = length + 1; i <= 20; i++) {
                table.addColumn(String.valueOf(i));
            }
        }

        //Adição de linhas se o número total for inferior a 40
        if (length < 40) {
            for (int i = length + 1; i <= 40; i++) {
                table.addRow(new String[] { " " + String.valueOf(i) });
            }
        }

        return table;
    }
}
