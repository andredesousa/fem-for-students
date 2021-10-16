/*
 * Esta classe permite definir um elemento finito de qualquer tipo
 * As propriedades e os métodos da classe permitem definir o elemento finito
 * A classe fornece os resultados ao nível de cada elemento finito
 */

package app.calculations;

import static app.matrices.Determinant.determinant;
import static app.matrices.Inverse.inverse;
import static app.matrices.Multiply.multiply;
import static app.matrices.Sum.sum;
import static app.matrices.Transpose.transpose;

import app.finiteelement.MatrixD;
import app.finiteelement.MatrixJ;
import app.finiteelement.MatrixK;
import app.finiteelement.MatrixT;
import app.finiteelement.VectorF;
import app.gausslegendre.GaussLegendre;
import app.gausslobatto.GaussLobatto;
import app.shapefunctions.MatrixNv;
import app.variablesubstitution.DerivedMatrix;
import app.variablesubstitution.MatrixB;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author André de Sousa
 */
public class FiniteElement {

    //Variáveis que definem o tipo de elemento finito
    private final int ID;
    private final String shape;
    private final String type;
    private final String theory;
    private final String section;
    private final int degreeOfFreedom;
    private final double[][] shapeCoordinates;
    private double[][] nodesCoordinates;

    /**
     * Método construtor da classe FiniteElement
     *
     * @param ID
     * @param shape
     * @param type
     * @param theory
     * @param section
     * @param degreeOfFreedom
     * @param coordinates
     */
    public FiniteElement(int ID, String shape, String type, String theory, String section, int degreeOfFreedom, double[][] coordinates) {
        this.ID = ID;
        this.shape = shape;
        this.type = type;
        this.theory = theory;
        this.section = section;
        this.degreeOfFreedom = degreeOfFreedom;
        this.shapeCoordinates = coordinates;

        //Chamada do métod para definir a geometria do elemento finito
        finiteElementGeometry();
    }

    /**
     * Método para obter o número do elemento finito
     *
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * Método para obter a forma do elemento finito
     *
     * @return
     */
    public String getShape() {
        return shape;
    }

    /**
     * Método para obter o tipo de elemento finito
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Método para obter a teoria de cálculo do elemento finito
     *
     * @return
     */
    public String getTheory() {
        return theory;
    }

    /**
     * Método para obter o nome da secção atribuída ao elemento finito
     *
     * @return
     */
    public String getSection() {
        return section;
    }

    //Variáveis para armazenar o vetor de solicitação e o vetor de deslocamentos
    private double[][] loadVector, displacementVector;

    //Outros parâmetros e tabela de assemblagem do elemento finito
    private int nodes;
    private String material;
    private double inertia, torsion, area, thickness;
    private double elasticity, shear, poisson, thermal;
    private int[][] assemblyTable;

    //Parâmetros para receber as dimensões do elemento finito
    private double L, a, b, c;

    /**
     * Método para obter o número de nós do elemento finito
     *
     * @return
     */
    public int getNodes() {
        return nodes;
    }

    /**
     * Método para obter a matriz de assemblagem do elemento finito
     *
     * @return
     */
    public int[][] getAssemblyTable() {
        return assemblyTable;
    }

    /**
     * Método para definir a matriz de assemblagem do elemento finito
     *
     * @param assemblyTable
     */
    public void setAssemblyTable(int[][] assemblyTable) {
        this.assemblyTable = assemblyTable;
    }

    /**
     * Método para definir o número de nós do elemento finito
     *
     * @param nodes é o número de nós de um elemento finito
     */
    public void setNumberOfNodes(int nodes) {
        this.nodes = nodes;
        this.nodesCoordinates = nodesCoordinates(shapeCoordinates, nodes);
    }

    /**
     * Método para definir a secção do elemento finito
     *
     * @param inertia é a inercia da secção transversal
     * @param torsion é a inércia de torção da secção transversal
     * @param area é a área de um elementos finito
     * @param thickness é a espessura de um elemento finito
     */
    public void setSection(double inertia, double torsion, double area, double thickness) {
        this.inertia = inertia;
        this.torsion = torsion;
        this.area = area;
        this.thickness = thickness;
    }

    /**
     * Método para definir o meterial do elemento finito
     *
     * @param material é o nome do material do elemento finito
     * @param elasticity é o módulo de elasticidade do material
     * @param poisson é o coeficiente de Poisson do material
     * @param thermal é o coeficiente de dilatação térmica linear
     */
    public void setMaterial(String material, double elasticity, double poisson, double thermal) {
        this.material = material;
        this.elasticity = elasticity;
        this.shear = elasticity / (2 * (1 + poisson));
        this.poisson = poisson;
        this.thermal = thermal;
    }

    /**
     * Método para atribuir as cargas distibuídas nos elementos finitos
     *
     * @param loadType
     * @param loadCoordinates
     * @param xLoad
     * @param yLoad
     * @param zLoad
     */
    public void setLoads(String loadType, double[][] loadCoordinates, double xLoad, double yLoad, double zLoad) {
        double[][] vector = new double[nodes * degreeOfFreedom][1];

        if ("Distributed Load".equals(loadType)) {
            if ("2D".equals(type)) {
                double length = AnalyticGeometry.length(loadCoordinates);
                VectorF vectorF = new VectorF(type, xLoad, yLoad, 0);
                vector = vectorF.getLoadVector(length, thickness, nodes, theory);
                vector = loadVector(loadCoordinates, vector);
            }
            if ("Beams".equals(type)) {
                VectorF vectorF = new VectorF(type, 0, 0, yLoad);
                vector = vectorF.getLoadVector(L, 0, nodes, theory);
            }
            if ("Frames".equals(type)) {
                double alpha = (Math.PI / 2.0) - angle();
                double horizontal = xLoad * Math.sin(alpha) + yLoad * Math.cos(alpha);
                double vertical = -xLoad * Math.cos(alpha) + yLoad * Math.sin(alpha);
                double[][] matrixT = MatrixT.matrixT_Frames(angle());
                VectorF vectorF = new VectorF(type, horizontal, vertical, 0);
                vector = vectorF.getLoadVector(L, 0, nodes, theory);
                vector = multiply(transpose(matrixT), vector);
            }
            if ("Grids".equals(type)) {
                double[][] matrixT = MatrixT.matrixT_Grids(angle());
                VectorF vectorF = new VectorF(type, 0, 0, yLoad);
                vector = vectorF.getLoadVector(L, 0, nodes, theory);
                vector = multiply(transpose(matrixT), vector);
            }
        }

        if ("Axial Load".equals(loadType)) {
            if ("1D".equals(type)) {
                double[][] matrixT = MatrixT.matrixT_1D(angle());
                VectorF vectorF = new VectorF(type, xLoad, 0, 0);
                vector = vectorF.getLoadVector(L, 0, nodes, theory);
                vector = multiply(transpose(matrixT), vector);
            }
            if ("Frames".equals(type)) {
                double[][] matrixT = MatrixT.matrixT_Frames(angle());
                VectorF vectorF = new VectorF(type, xLoad, 0, 0);
                vector = vectorF.getLoadVector(L, 0, nodes, theory);
                vector = multiply(transpose(matrixT), vector);
            }
        }

        if ("Planar Load".equals(loadType)) {
            VectorF vectorF = new VectorF(type, 0, 0, zLoad);
            vector = vectorF.getLoadVector(0, a, b, 0, nodes, theory);
        }

        if (loadVector == null) {
            loadVector = new double[nodes * degreeOfFreedom][1];
        }

        loadVector = sum(loadVector, vector);
    }

    /**
     * Método para atribuir as cargas distibuídas nos elementos finitos
     *
     * @param Tzero é o valor da variação uniforme de temperatura
     * @param Ttop é o valor superior da variação de temperatura
     * @param Tbot é o valor inferior da variação de temperatura
     */
    public void setThermalLoads(double Tzero, double Ttop, double Tbot) {
        double[][] vector = new double[nodes * degreeOfFreedom][1];

        if ("1D".equals(type)) {
            double[][] matrixT = MatrixT.matrixT_1D(angle());
            VectorF vectorF = new VectorF(type, Tzero, 0, 0);
            vector = vectorF.getThermalLoadVector(thickness, elasticity * area, elasticity * inertia, thermal, nodes);
            vector = multiply(transpose(matrixT), vector);
        }
        if ("Frames".equals(type)) {
            double[][] matrixT = MatrixT.matrixT_Frames(angle());
            VectorF vectorF = new VectorF(type, Tzero, Ttop, Tbot);
            vector = vectorF.getThermalLoadVector(thickness, elasticity * area, elasticity * inertia, thermal, nodes);
            vector = multiply(transpose(matrixT), vector);
        }

        if (loadVector == null) {
            loadVector = new double[nodes * degreeOfFreedom][1];
        }

        loadVector = sum(loadVector, vector);
    }

    /**
     * Método para atribuir o peso próprio aos elementos finitos
     *
     * @param density
     */
    public void setSelfWeight(double density) {
        double[][] vector = new double[nodes * degreeOfFreedom][1];

        if ("2D".equals(type)) {
            if ("Triangle".equals(shape) || "Quadrilateral".equals(shape)) {
                if ("Plane Stress".equals(theory)) {
                    vector = vectorF_2D(thickness * density);
                } else {
                    vector = vectorF_2D(density);
                }
            } else {
                if ("Plane Stress".equals(theory)) {
                    VectorF vectorF = new VectorF(type, 0, thickness * density, 0);
                    vector = vectorF.getLoadVector(0, a, b, 0, nodes, theory);
                } else {
                    VectorF vectorF = new VectorF(type, 0, density, 0);
                    vector = vectorF.getLoadVector(0, a, b, 0, nodes, theory);
                }
            }
        }
        if ("Beams".equals(type)) {
            VectorF vectorF = new VectorF(type, 0, 0, area * density);
            vector = vectorF.getLoadVector(L, 0, nodes, theory);
        }
        if ("Frames".equals(type)) {
            double alpha = (Math.PI / 2.0) - angle();
            double horizontal = (area * density) * Math.cos(alpha);
            double vertical = (area * density) * Math.sin(alpha);
            double[][] matrixT = MatrixT.matrixT_Frames(angle());
            VectorF vectorF = new VectorF(type, horizontal, vertical, 0);
            vector = vectorF.getLoadVector(L, 0, nodes, theory);
            vector = multiply(transpose(matrixT), vector);
        }
        if ("Grids".equals(type)) {
            double[][] matrixT = MatrixT.matrixT_Grids(angle());
            VectorF vectorF = new VectorF(type, 0, 0, area * density);
            vector = vectorF.getLoadVector(L, 0, nodes, theory);
            vector = multiply(transpose(matrixT), vector);
        }
        if ("Slabs".equals(type)) {
            VectorF vectorF = new VectorF(type, 0, 0, thickness * density);
            vector = vectorF.getLoadVector(0, a, b, 0, nodes, theory);
        }

        if (loadVector == null) {
            loadVector = new double[nodes * degreeOfFreedom][1];
        }

        loadVector = sum(loadVector, vector);
    }

    //Tipo de quadratura a usar no cálculo do elemento finito
    private String quadrature;
    private int points;

    /**
     * Método para definir a quadratura e o número de pontos
     *
     * @param quadrature
     * @param points
     */
    public void setQuadrature(String quadrature, int points) {
        this.quadrature = quadrature;
        this.points = points;
    }

    /**
     * Método para obter as coordenadas da figura geométrica
     *
     * @return
     */
    public double[][] getShapeCoordinates() {
        return shapeCoordinates;
    }

    /**
     * Método para obter as coordenadas do elemento finito
     *
     * @return
     */
    public double[][] getNodesCoordinates() {
        return nodesCoordinates;
    }

    /**
     * Método para obter o nome do material do elemento finito
     *
     * @return
     */
    public String getMaterialName() {
        return material;
    }

    /**
     * Método que retorna o vetor de forças nodais do elemento finito
     *
     * @return
     */
    public double[][] getLoadVector() {
        if (loadVector == null) {
            loadVector = new double[nodes * degreeOfFreedom][1];
        }

        return loadVector;
    }

    /**
     * Método que retorna o vetor de solicitação local calculado do elemento finito
     *
     * @return
     */
    public double[][] getVectorF() {
        double[][] dispVector;
        double[][] matrixK;
        double[][] matrixT;

        MatrixK stiffnessMatrix = new MatrixK(elasticity, shear, poisson, inertia, torsion, area, thickness, type);
        switch (type) {
            case "1D":
                matrixT = MatrixT.matrixT_1D(angle());
                dispVector = multiply(matrixT, displacementVector);
                matrixK = stiffnessMatrix.getStiffnessMatrix(L, 0.0, 0.0, 0.0, nodes, theory);
                break;
            case "Frames":
                matrixT = MatrixT.matrixT_Frames(angle());
                dispVector = multiply(matrixT, displacementVector);
                matrixK = stiffnessMatrix.getStiffnessMatrix(L, 0.0, 0.0, 0.0, nodes, theory);
                break;
            case "Grids":
                matrixT = MatrixT.matrixT_Grids(angle());
                dispVector = multiply(matrixT, displacementVector);
                matrixK = stiffnessMatrix.getStiffnessMatrix(L, 0.0, 0.0, 0.0, nodes, theory);
                break;
            default:
                dispVector = displacementVector;
                matrixK = getStiffnessMatrix();
                break;
        }

        return multiply(matrixK, dispVector);
    }

    /**
     * Método que retorna a matriz de rigidez local do elemento finito
     *
     * @return
     */
    public double[][] getLocalStiffnessMatrix() {
        double[][] matrixK;

        switch (type) {
            case "1D":
                MatrixK matrix_1D = new MatrixK(elasticity, shear, poisson, inertia, torsion, area, thickness, type);
                matrixK = matrix_1D.getStiffnessMatrix(L, 0.0, 0.0, 0.0, nodes, theory);
                break;
            case "Frames":
                MatrixK matrix_Frames = new MatrixK(elasticity, shear, poisson, inertia, torsion, area, thickness, type);
                matrixK = matrix_Frames.getStiffnessMatrix(L, 0.0, 0.0, 0.0, nodes, theory);
                break;
            case "Grids":
                MatrixK matrix_Grids = new MatrixK(elasticity, shear, poisson, inertia, torsion, area, thickness, type);
                matrixK = matrix_Grids.getStiffnessMatrix(L, 0.0, 0.0, 0.0, nodes, theory);
                break;
            default:
                matrixK = getStiffnessMatrix();
                break;
        }

        return matrixK;
    }

    /**
     * Método que retorna a matriz de rigidez global do elemento finito
     *
     * @return
     */
    public double[][] getStiffnessMatrix() {
        double[][] stiffnessMatrix;

        if ("Gauss-Legendre".equals(quadrature) || "Gauss-Lobatto".equals(quadrature)) {
            stiffnessMatrix = numericalIntegration();
        } else {
            MatrixK matrixK = new MatrixK(elasticity, shear, poisson, inertia, torsion, area, thickness, type);

            switch (type) {
                case "1D":
                    stiffnessMatrix = stiffnessMatrix_1D();
                    break;
                case "2D":
                    if ("Triangle".equals(shape) || "Quadrilateral".equals(shape)) {
                        if (nodes == 3) {
                            setQuadrature("Gauss-Legendre", 1);
                            stiffnessMatrix = numericalIntegration();
                        } else if (nodes == 6) {
                            setQuadrature("Gauss-Legendre", 3);
                            stiffnessMatrix = numericalIntegration();
                        } else if (nodes == 8) {
                            setQuadrature("Gauss-Legendre", 9);
                            stiffnessMatrix = numericalIntegration();
                        } else {
                            setQuadrature("Gauss-Legendre", nodes);
                            stiffnessMatrix = numericalIntegration();
                        }
                    } else {
                        stiffnessMatrix = matrixK.getStiffnessMatrix(L, a, b, c, nodes, theory);
                    }
                    break;
                case "Frames":
                    stiffnessMatrix = stiffnessMatrix_Frames();
                    break;
                case "Grids":
                    stiffnessMatrix = stiffnessMatrix_Grids();
                    break;
                default:
                    stiffnessMatrix = matrixK.getStiffnessMatrix(L, a, b, c, nodes, theory);
                    break;
            }
        }

        return stiffnessMatrix;
    }

    /**
     * Método que retorna o vetor de deslocamentos do elemento finito
     *
     * @return
     */
    public double[][] getDisplacementVector() {
        if (displacementVector == null) {
            displacementVector = new double[nodes * degreeOfFreedom][1];
        }
        return displacementVector;
    }

    /**
     * Método que retorna o vetor de deslocamentos locais do elemento finito
     *
     * @return
     */
    public double[][] getLocalDisplacements() {
        double matrixT[][];
        double[][] dispVector;

        switch (type) {
            case "1D":
                matrixT = MatrixT.matrixT_1D(angle());
                dispVector = multiply(matrixT, getDisplacementVector());
                break;
            case "Frames":
                matrixT = MatrixT.matrixT_Frames(angle());
                dispVector = multiply(matrixT, getDisplacementVector());
                break;
            case "Grids":
                matrixT = MatrixT.matrixT_Grids(angle());
                dispVector = multiply(matrixT, getDisplacementVector());
                break;
            default:
                dispVector = getDisplacementVector();
                break;
        }

        return dispVector;
    }

    /**
     * Método para atribuir os deslocamentos globais dos nós do elemento finito
     *
     * @param displacementVector
     */
    public void setDisplacements(double[][] displacementVector) {
        this.displacementVector = displacementVector;
    }

    /**
     * Método que retorna a lista de esforços nodais do elemento finito
     *
     * @return
     */
    public ArrayList<double[][]> getNodalForces() {
        ArrayList<double[][]> nodalForces = new ArrayList();

        NodalResults nodalResults = new NodalResults(type, theory, shape, nodes);
        nodalResults.setMaterial(elasticity, shear, poisson);
        nodalResults.setSection(inertia, torsion, area, thickness);

        double[][] matrixT;
        double[][] dispVector;
        switch (this.type) {
            case "1D":
                matrixT = MatrixT.matrixT_1D(angle());
                dispVector = multiply(matrixT, displacementVector);
                nodalForces = nodalResults.nodalForces_1D(L, dispVector);
                break;
            case "2D":
                if ("Triangle".equals(shape) || "Quadrilateral".equals(shape)) {
                    nodalForces = nodalResults.nodalForces_2D(2, 2, displacementVector, nodesCoordinates);
                } else {
                    nodalForces = nodalResults.nodalForces_2D(a, b, displacementVector, nodesCoordinates);
                }
                break;
            case "Beams":
                nodalForces = nodalResults.nodalForces_Beams(L, displacementVector);
                break;
            case "Frames":
                matrixT = MatrixT.matrixT_Frames(angle());
                dispVector = multiply(matrixT, displacementVector);
                nodalForces = nodalResults.nodalForces_Frames(L, dispVector);
                break;
            case "Grids":
                matrixT = MatrixT.matrixT_Grids(angle());
                dispVector = multiply(matrixT, displacementVector);
                nodalForces = nodalResults.nodalForces_Grids(L, dispVector);
                break;
            case "Slabs":
                nodalForces = nodalResults.nodalForces_Slabs(a, b, displacementVector);
                break;
        }

        return nodalForces;
    }

    /**
     * Método qua retorna a lista de tensões nodais do elemento finito
     *
     * @return
     */
    public ArrayList<double[][]> getNodalStresses() {
        ArrayList<double[][]> nodalStresses = new ArrayList();

        NodalResults nodalResults = new NodalResults(type, theory, shape, nodes);
        nodalResults.setMaterial(elasticity, shear, poisson);
        nodalResults.setSection(inertia, torsion, area, thickness);

        double[][] matrixT;
        double[][] dispVector;
        switch (this.type) {
            case "1D":
                matrixT = MatrixT.matrixT_1D(angle());
                dispVector = multiply(matrixT, displacementVector);
                nodalStresses = nodalResults.nodalStresses_1D(L, dispVector);
                break;
            case "2D":
                if ("Triangle".equals(shape) || "Quadrilateral".equals(shape)) {
                    nodalStresses = nodalResults.nodalStresses_2D(2, 2, displacementVector, nodesCoordinates);
                } else {
                    nodalStresses = nodalResults.nodalStresses_2D(a, b, displacementVector, nodesCoordinates);
                }
                break;
            case "3D":
                nodalStresses = nodalResults.nodalStresses_3D(a, b, c, displacementVector);
                break;
            case "Beams":
                nodalStresses = nodalResults.nodalStresses_Beams(L, displacementVector);
                break;
            case "Frames":
                matrixT = MatrixT.matrixT_Frames(angle());
                dispVector = multiply(matrixT, displacementVector);
                nodalStresses = nodalResults.nodalStresses_Frames(L, dispVector);
                break;
            case "Grids":
                matrixT = MatrixT.matrixT_Grids(angle());
                dispVector = multiply(matrixT, displacementVector);
                nodalStresses = nodalResults.nodalStresses_Grids(L, dispVector);
                break;
            case "Slabs":
                nodalStresses = nodalResults.nodalStresses_Slabs(a, b, displacementVector);
                break;
        }

        return nodalStresses;
    }

    /**
     * Método para obter as tensões e direcções principais
     *
     * @return
     */
    public double[][] getStressesAndDirections() {
        double[][] stressesAndDirections;

        if ("2D".equals(type)) {
            stressesAndDirections = principalStressesDirections();
        } else {
            stressesAndDirections = null;
        }

        return stressesAndDirections;
    }

    /**
     * Método para obter as coordenadas dos pontos de cálculo das tensões
     *
     * @return
     */
    public double[][] getStressesCoordinates() {
        double[][] coordinates;

        if ("2D".equals(type) || "Slabs".equals(type)) {
            if (nodes == 3 || nodes == 6) {
                coordinates = new double[3][2];
            } else {
                coordinates = new double[4][2];
            }

            if ("Triangle".equals(shape) || "Quadrilateral".equals(shape)) {
                double[] dimensions = { 2, 2, 2, 2 };
                double[][] weightsCoordinates = getGaussLegendreCoordinates(0, 2, 2, 0);
                Point2D.Double centroid = AnalyticGeometry.centroid(shapeCoordinates, shape);

                for (int i = 0; i < weightsCoordinates.length; i++) {
                    double[][] pointsCoordinates;
                    double[] gaussPoints = { weightsCoordinates[i][1], weightsCoordinates[i][3], 0 };

                    MatrixNv matrixNv = new MatrixNv(type, theory, nodes, dimensions, gaussPoints);
                    pointsCoordinates = matrixNv.getShapeFunctions();
                    pointsCoordinates = multiply(transpose(nodesCoordinates), pointsCoordinates);

                    coordinates[i][0] = pointsCoordinates[0][0] - centroid.x;
                    coordinates[i][1] = pointsCoordinates[1][0] - centroid.y;
                }
            } else {
                double[][] weightsCoordinates = getGaussLegendreCoordinates(L, a, b, c);

                for (int j = 0; j < weightsCoordinates.length; j++) {
                    coordinates[j][0] = weightsCoordinates[j][1];
                    coordinates[j][1] = weightsCoordinates[j][3];
                }
            }
        } else {
            coordinates = null;
        }

        return coordinates;
    }

    /**
     * Este método calcula as dimensões de um elemento finito regular
     */
    private void finiteElementGeometry() {
        double[] dimensions;

        switch (shape) {
            case "Line":
                L = AnalyticGeometry.length(shapeCoordinates);
                break;
            case "Triangle":
                dimensions = AnalyticGeometry.triangleDimensions(shapeCoordinates);
                a = dimensions[0];
                b = dimensions[1];
                break;
            case "Rectangle":
                dimensions = AnalyticGeometry.rectangleDimensions(shapeCoordinates);
                a = dimensions[0];
                b = dimensions[1];
                break;
        }
    }

    /**
     * Método para calcular as coordenadas dos nós do elemento finito
     *
     * @param shapeCoordinates contém as coordenadas da figura geométrica
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    private double[][] nodesCoordinates(double[][] shapeCoordinates, int nodes) {
        double[][] coordinates;

        switch (shape) {
            case "Line":
                coordinates = NodesCoordinates.line(shapeCoordinates, nodes);
                break;
            case "Triangle":
                coordinates = NodesCoordinates.triangle(shapeCoordinates, nodes);
                break;
            case "Rectangle":
                coordinates = NodesCoordinates.rectangle(shapeCoordinates, nodes);
                break;
            case "Quadrilateral":
                coordinates = NodesCoordinates.quadrilateral(shapeCoordinates, nodes);
                break;
            default:
                coordinates = null;
                break;
        }

        return coordinates;
    }

    /**
     * Método para construir o vetor de solicitação do elemento finito
     *
     * @param loadVector
     * @return
     */
    private double[][] loadVector(double[][] loadCoordinates, double[][] loadVector) {
        //Identificação dos vértices com as mesmas coordenadas da carga
        int[] nodesPosition = new int[2];

        int k = 0;
        for (double[] loadCoordinate : loadCoordinates) {
            for (int j = 0; j < nodesCoordinates.length; j++) {
                if (loadCoordinate[0] == nodesCoordinates[j][0]) {
                    if (loadCoordinate[1] == nodesCoordinates[j][1]) {
                        nodesPosition[k] = j;
                        k++;
                        break;
                    }
                }
            }
        }

        //Ordenar posições dos nós por ordem crescente
        Arrays.sort(nodesPosition, 0, nodesPosition.length);

        //Identificação dos nós situados entre os vértices da figura
        double[][] vectorF = new double[nodes * degreeOfFreedom][1];

        if ("2D".equals(type)) {
            vectorF[nodesPosition[0] * degreeOfFreedom][0] = 1;
            vectorF[nodesPosition[0] * degreeOfFreedom + 1][0] = 1;
            vectorF[nodesPosition[1] * degreeOfFreedom][0] = 1;
            vectorF[nodesPosition[1] * degreeOfFreedom + 1][0] = 1;

            if (nodes == 6) {
                if (nodesPosition[0] != 0 || nodesPosition[1] != 4) {
                    int position = (nodesPosition[0] + nodesPosition[1]) / 2;

                    vectorF[position * degreeOfFreedom][0] = 1;
                    vectorF[position * degreeOfFreedom + 1][0] = 1;
                } else {
                    vectorF[5 * degreeOfFreedom][0] = 1;
                    vectorF[5 * degreeOfFreedom + 1][0] = 1;
                }
            }

            if (nodes == 8) {
                if (nodesPosition[0] != 0 || nodesPosition[1] != 6) {
                    int position = (nodesPosition[0] + nodesPosition[1]) / 2;

                    vectorF[position * degreeOfFreedom][0] = 1;
                    vectorF[position * degreeOfFreedom + 1][0] = 1;
                } else {
                    vectorF[7 * degreeOfFreedom][0] = 1;
                    vectorF[7 * degreeOfFreedom + 1][0] = 1;
                }
            }

            if (nodes == 9) {
                if (nodesPosition[0] != 0 || nodesPosition[1] != 6) {
                    int position = (nodesPosition[0] + nodesPosition[1]) / 2;

                    vectorF[position * degreeOfFreedom][0] = 1;
                    vectorF[position * degreeOfFreedom + 1][0] = 1;
                } else {
                    vectorF[7 * degreeOfFreedom][0] = 1;
                    vectorF[7 * degreeOfFreedom + 1][0] = 1;
                }
            }
        }

        //Anular termos do vector de solicitação desnecessários
        if (vectorF != null) {
            for (int i = 0; i < loadVector.length; i++) {
                loadVector[i][0] = loadVector[i][0] * vectorF[i][0];
            }
        }

        return loadVector;
    }

    /**
     * Método que devolve o ângulo de inclinação da reta
     *
     * @return
     */
    private double angle() {
        double x1, y1, x2, y2;
        x1 = shapeCoordinates[0][0];
        y1 = shapeCoordinates[0][1];
        x2 = shapeCoordinates[1][0];
        y2 = shapeCoordinates[1][1];

        double inclination = (y2 - y1) / (x2 - x1);
        double angle = Math.atan(inclination);

        return angle;
    }

    /**
     * Método para calcular a matriz de rigidez do elemento finito
     *
     * @return
     */
    private double[][] numericalIntegration() {
        double[][] matrixK;

        switch (this.type) {
            case "1D":
                matrixK = stiffnessMatrix_1D();
                break;
            case "2D":
                matrixK = stiffnessMatrix_2D();
                break;
            case "3D":
                matrixK = stiffnessMatrix_3D();
                break;
            case "Beams":
                matrixK = stiffnessMatrix_Beams();
                break;
            case "Frames":
                matrixK = stiffnessMatrix_Frames();
                break;
            case "Slabs":
                matrixK = stiffnessMatrix_Slabs();
                break;
            default:
                matrixK = null;
                break;
        }

        return matrixK;
    }

    /**
     * Método para calcular a matriz de rigidez unidimensional
     *
     * @return
     */
    private double[][] stiffnessMatrix_1D() {
        MatrixK matrixK_1D = new MatrixK(elasticity, shear, poisson, inertia, torsion, area, thickness, type);
        double[][] matrixT = MatrixT.matrixT_1D(angle());
        double[][] matrixK = matrixK_1D.getStiffnessMatrix(L, a, b, c, nodes, theory);

        return multiply(multiply(transpose(matrixT), matrixK), matrixT);
    }

    /**
     * Método para calcular a matriz de rigidez bidimensional
     *
     * @return
     */
    private double[][] stiffnessMatrix_2D() {
        //Cálculo da matriz de elasticidade
        double[][] matrixD;
        if ("Plane Stress".equals(theory)) {
            matrixD = MatrixD.matrixD_PlaneStress(elasticity, poisson);
        } else {
            matrixD = MatrixD.matrixD_PlaneStrain(elasticity, poisson);
        }

        //Variável com as dimensões do elemento isoparamétrico
        double[] dimensions = { 2, 2, 2, 2 };

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getWeightsCoordinates(2, 2, 2, 2);

        //Variável para armazenar a matriz de rigidez
        double[][] globalStiffnessMatrix = new double[nodes * degreeOfFreedom][nodes * degreeOfFreedom];

        //Ciclo para todos os pontos de integração da matriz de rigidez
        int n = 0;
        while (n < points) {
            double[] pointsCoordinates = weightsCoordinates[n];

            //Matriz jacobiana no ponto de integração (i, j)
            MatrixJ jacobianMatrix = new MatrixJ(type, nodesCoordinates, pointsCoordinates);
            double[][] matrixJ = jacobianMatrix.getJacobianMatrix(nodes, theory);

            //Determinante da matriz Jacobiana
            double determinantJ = determinant(matrixJ);

            //Inversa da matriz Jacobiana
            double[][] inverseJ = inverse(matrixJ);

            //Cálculo da matriz das derivadas das funções de forma
            double[][] derivedMatrix = DerivedMatrix.derivedMatrix(type, theory, pointsCoordinates, dimensions, nodes);

            //Cálculo dos termos da matriz B
            double[][] derivedMatrixB = multiply(derivedMatrix, inverseJ);

            //Cálculo da matriz B do elemento finito
            double[][] matrixB = MatrixB.matrixB(type, theory, derivedMatrixB, nodes);

            //Cálculo da matriz de rigidez K'
            double[][] matrixK = multiply(multiply(transpose(matrixB), matrixD), matrixB);
            matrixK = multiply(determinantJ, matrixK);

            //Multiplicação da matrix k' pelos pesos
            double weightX = pointsCoordinates[0];
            double weightY = pointsCoordinates[2];
            matrixK = multiply((weightX * weightY), matrixK);

            //Cálculo da matriz de rigidez do elemento finito
            globalStiffnessMatrix = sum(globalStiffnessMatrix, matrixK);

            n++;
        }

        if ("Plane Stress".equals(theory)) {
            globalStiffnessMatrix = multiply(thickness, globalStiffnessMatrix);
        }

        return globalStiffnessMatrix;
    }

    /**
     * Método para calcular a matriz de rigidez tridimensional
     *
     * @return
     */
    private double[][] stiffnessMatrix_3D() {
        MatrixK matrixK = new MatrixK(elasticity, shear, poisson, inertia, torsion, area, thickness, type);
        return matrixK.getStiffnessMatrix(L, a, b, c, nodes, theory);
    }

    /**
     * Método para calcular a matriz de rigidez de uma viga
     *
     * @return
     */
    private double[][] stiffnessMatrix_Beams() {
        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getWeightsCoordinates(L, 0, 0, 0);

        //Variável para armazenar a matriz de rigidez
        double[][] globalStiffnessMatrix = new double[nodes * degreeOfFreedom][nodes * degreeOfFreedom];

        //Ciclo para todos os pontos de integração da matriz de rigidez
        int n = 0;
        while (n < points) {
            double[] pointsCoordinates = weightsCoordinates[n];

            //Cálculo da matriz B do elemento finito
            double pointX = pointsCoordinates[1];

            //Cálculo da matriz de rigidez K'
            double[][] matrixK;

            app.finiteelement.MatrixB derivedMatrix = new app.finiteelement.MatrixB(type, L, 0, 0, 0);

            switch (theory) {
                case "Euler-Bernoulli":
                    double[][] matrixB = derivedMatrix.getMatrixB(pointX, 0, 0, nodes, theory);

                    matrixK = multiply(transpose(matrixB), matrixB);
                    matrixK = multiply(elasticity * inertia, matrixK);
                    break;
                case "Timoshenko":
                    double[][] matrixBb = derivedMatrix.getMatrixBb(pointX, nodes, theory);
                    double[][] matrixBs = derivedMatrix.getMatrixBs(pointX, nodes, theory);
                    double[][] matrixKb = multiply(transpose(matrixBb), matrixBb);
                    double[][] matrixKs = multiply(transpose(matrixBs), matrixBs);

                    matrixKb = multiply(elasticity * inertia, matrixKb);
                    matrixKs = multiply(shear * (area * (5.0 / 6.0)), matrixKs);
                    matrixK = sum(matrixKb, matrixKs);
                    break;
                default:
                    matrixK = null;
                    break;
            }

            //Multiplicação da matrix k' pelos pesos
            double weightX = pointsCoordinates[0];
            matrixK = multiply(weightX, matrixK);

            //Cálculo da matriz de rigidez do elemento finito
            globalStiffnessMatrix = sum(globalStiffnessMatrix, matrixK);

            n++;
        }

        return globalStiffnessMatrix;
    }

    /**
     * Método para calcular a matriz de rigidez de uma barra
     *
     * @return
     */
    private double[][] stiffnessMatrix_Frames() {
        MatrixK matrixK_Frames = new MatrixK(elasticity, shear, poisson, inertia, torsion, area, thickness, type);
        double[][] matrixT = MatrixT.matrixT_Frames(angle());
        double[][] matrixK = matrixK_Frames.getStiffnessMatrix(L, a, b, c, nodes, theory);

        return multiply(multiply(transpose(matrixT), matrixK), matrixT);
    }

    /**
     * Método para calcular a matriz de rigidez de uma grelha
     *
     * @return
     */
    private double[][] stiffnessMatrix_Grids() {
        MatrixK matrixK_Grids = new MatrixK(elasticity, shear, poisson, inertia, torsion, area, thickness, type);
        double[][] matrixT = MatrixT.matrixT_Grids(angle());
        double[][] matrixK = matrixK_Grids.getStiffnessMatrix(L, a, b, c, nodes, theory);

        return multiply(multiply(transpose(matrixT), matrixK), matrixT);
    }

    /**
     * Método para calcular a matriz de rigidez de uma laje
     *
     * @return
     */
    private double[][] stiffnessMatrix_Slabs() {
        MatrixK matrixK = new MatrixK(elasticity, shear, poisson, inertia, torsion, area, thickness, type);
        return matrixK.getStiffnessMatrix(L, a, b, c, nodes, theory);
    }

    /**
     * Método para fornecer a matriz de pesos e coordenadas de integração
     *
     * @param L é o comprimento do elemento finito
     * @param a é o comprimento do elemento finito
     * @param b é a largura do elemento finito
     * @param c é a altura do elemento finito
     * @return
     */
    private double[][] getWeightsCoordinates(double L, double a, double b, double c) {
        double[][] weightsCoordinates;

        //Variável com as dimensões do elemento isoparamétrico
        double[] dimensions = { L, a, b, c };

        switch (quadrature) {
            case "Gauss-Legendre":
                GaussLegendre gaussLegendre = new GaussLegendre(type, theory, shape, points, dimensions);
                weightsCoordinates = gaussLegendre.getWeightsCoordinates();
                break;
            case "Gauss-Lobatto":
                GaussLobatto gaussLobatto = new GaussLobatto(type, theory, shape, points, dimensions);
                weightsCoordinates = gaussLobatto.getWeightsCoordinates();
                break;
            default:
                weightsCoordinates = null;
                break;
        }

        return weightsCoordinates;
    }

    /**
     * Método para calcular o vetor de forças nodais equivalentes bidimensional
     *
     * @return
     */
    private double[][] vectorF_2D(double selfWeight) {
        double[][] vectorF_SelfWeight;

        double areaOfShape = AnalyticGeometry.area(shapeCoordinates, shape);
        Point2D.Double point = AnalyticGeometry.centroid(shapeCoordinates, shape);
        MatrixNv Nv = new MatrixNv(type, nodes);

        if (nodes > 1) {
            if (nodes == 3) {
                double[][] matrixNv = Nv.getShapeFunctions(point, nodesCoordinates);
                double[][] vectorFsw = { { 0 }, { areaOfShape * selfWeight } };

                double[][] matrixN = {
                    { matrixNv[0][0], 0 },
                    { 0, matrixNv[0][0] },
                    { matrixNv[1][0], 0 },
                    { 0, matrixNv[1][0] },
                    { matrixNv[2][0], 0 },
                    { 0, matrixNv[2][0] },
                };

                vectorF_SelfWeight = multiply(matrixN, vectorFsw);
            } else if (nodes == 4) {
                double[][] matrixNv = Nv.getShapeFunctions(point, nodesCoordinates);
                double[][] vectorFsw = { { 0 }, { areaOfShape * selfWeight } };

                double[][] matrixN = {
                    { matrixNv[0][0], 0 },
                    { 0, matrixNv[0][0] },
                    { matrixNv[1][0], 0 },
                    { 0, matrixNv[1][0] },
                    { matrixNv[2][0], 0 },
                    { 0, matrixNv[2][0] },
                    { matrixNv[3][0], 0 },
                    { 0, matrixNv[3][0] },
                };

                vectorF_SelfWeight = multiply(matrixN, vectorFsw);
            } else if (nodes == 6) {
                double[][] matrixNv = Nv.getShapeFunctions(point, nodesCoordinates);
                double[][] vectorFsw = { { 0 }, { areaOfShape * selfWeight } };

                double[][] matrixN = {
                    { matrixNv[0][0], 0 },
                    { 0, matrixNv[0][0] },
                    { matrixNv[1][0], 0 },
                    { 0, matrixNv[1][0] },
                    { matrixNv[2][0], 0 },
                    { 0, matrixNv[2][0] },
                    { matrixNv[3][0], 0 },
                    { 0, matrixNv[3][0] },
                    { matrixNv[4][0], 0 },
                    { 0, matrixNv[4][0] },
                    { matrixNv[5][0], 0 },
                    { 0, matrixNv[5][0] },
                };

                vectorF_SelfWeight = multiply(matrixN, vectorFsw);
            } else if (nodes == 8) {
                double[][] matrixNv = Nv.getShapeFunctions(point, nodesCoordinates);
                double[][] vectorFsw = { { 0 }, { areaOfShape * selfWeight } };

                double[][] matrixN = {
                    { matrixNv[0][0], 0 },
                    { 0, matrixNv[0][0] },
                    { matrixNv[1][0], 0 },
                    { 0, matrixNv[1][0] },
                    { matrixNv[2][0], 0 },
                    { 0, matrixNv[2][0] },
                    { matrixNv[3][0], 0 },
                    { 0, matrixNv[3][0] },
                    { matrixNv[4][0], 0 },
                    { 0, matrixNv[4][0] },
                    { matrixNv[5][0], 0 },
                    { 0, matrixNv[5][0] },
                    { matrixNv[6][0], 0 },
                    { 0, matrixNv[6][0] },
                    { matrixNv[7][0], 0 },
                    { 0, matrixNv[7][0] },
                };

                vectorF_SelfWeight = multiply(matrixN, vectorFsw);
            } else if (nodes == 9) {
                double[][] matrixNv = Nv.getShapeFunctions(point, nodesCoordinates);
                double[][] vectorFsw = { { 0 }, { areaOfShape * selfWeight } };

                double[][] matrixN = {
                    { matrixNv[0][0], 0 },
                    { 0, matrixNv[0][0] },
                    { matrixNv[1][0], 0 },
                    { 0, matrixNv[1][0] },
                    { matrixNv[2][0], 0 },
                    { 0, matrixNv[2][0] },
                    { matrixNv[3][0], 0 },
                    { 0, matrixNv[3][0] },
                    { matrixNv[4][0], 0 },
                    { 0, matrixNv[4][0] },
                    { matrixNv[5][0], 0 },
                    { 0, matrixNv[5][0] },
                    { matrixNv[6][0], 0 },
                    { 0, matrixNv[6][0] },
                    { matrixNv[7][0], 0 },
                    { 0, matrixNv[7][0] },
                    { matrixNv[8][0], 0 },
                    { 0, matrixNv[8][0] },
                };

                vectorF_SelfWeight = multiply(matrixN, vectorFsw);
            } else {
                vectorF_SelfWeight = null;
            }
        } else {
            vectorF_SelfWeight = null;
        }

        return vectorF_SelfWeight;
    }

    /**
     * Método para calcular as tensões e direcções principais
     *
     * @return
     */
    private double[][] principalStressesDirections() {
        double[][] stressesDirections;
        if (nodes == 6) {
            stressesDirections = new double[3][3];
        } else {
            stressesDirections = new double[nodes][3];
        }

        NodalResults nodalResults = new NodalResults(type, theory, shape, nodes);
        nodalResults.setMaterial(elasticity, shear, poisson);
        nodalResults.setSection(inertia, torsion, area, thickness);

        ArrayList<double[][]> nodalStresses;
        if ("Triangle".equals(shape) || "Quadrilateral".equals(shape)) {
            nodalStresses = nodalResults.nodalStresses_2D(2, 2, displacementVector, nodesCoordinates);
        } else {
            nodalStresses = nodalResults.nodalStresses_2D(a, b, displacementVector, nodesCoordinates);
        }

        //Cálculo das tensões e direcções principais
        int i = 0;
        for (double[][] stresses : nodalStresses) {
            double σx, σy, τxy, σ1, σ2, β;

            σx = stresses[0][0];
            σy = stresses[1][0];
            τxy = stresses[2][0];

            σ1 = ((σx + σy) / 2) + Math.sqrt(Math.pow(((σx - σy) / 2), 2) + Math.pow(τxy, 2));
            σ2 = ((σx + σy) / 2) - Math.sqrt(Math.pow(((σx - σy) / 2), 2) + Math.pow(τxy, 2));
            β = (Math.atan(2 * τxy / (σx - σy))) / 2;

            stressesDirections[i][0] = σ1;
            stressesDirections[i][1] = σ2;
            stressesDirections[i][2] = β;

            if (σx < σy) {
                if (β < 0) {
                    stressesDirections[i][2] = β + Math.PI / 2;
                } else {
                    stressesDirections[i][2] = β - Math.PI / 2;
                }
            }

            i++;
        }

        return stressesDirections;
    }

    /**
     * Método para obter as coordenadas dos pontos para cálculo dos resultados
     *
     * @param L é o comprimento do elemento finito
     * @param a é o comprimento do elemento finito
     * @param b é a largura do elemento finito
     * @param c é a altura do elemento finito
     * @return
     */
    private double[][] getGaussLegendreCoordinates(double L, double a, double b, double c) {
        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates;
        double[] dimensions = { L, a, b, c };

        GaussLegendre gaussLegendre = new GaussLegendre(type, theory, shape, nodes, dimensions);
        if (nodes == 6) {
            gaussLegendre = new GaussLegendre(type, theory, shape, 3, dimensions);
        }
        if (nodes == 8) {
            gaussLegendre = new GaussLegendre(type, theory, shape, 9, dimensions);
        }
        weightsCoordinates = gaussLegendre.getWeightsCoordinates();

        switch (shape) {
            case "Line":
                if (nodes == 3) {
                    double[][] coordinates = new double[2][2];

                    coordinates[0][0] = weightsCoordinates[0][0];
                    coordinates[0][1] = weightsCoordinates[0][1];
                    coordinates[1][0] = weightsCoordinates[2][0];
                    coordinates[1][1] = weightsCoordinates[2][1];

                    weightsCoordinates = coordinates;
                }
                if (nodes == 4) {
                    double[][] coordinates = new double[2][2];

                    coordinates[0][0] = weightsCoordinates[0][0];
                    coordinates[0][1] = weightsCoordinates[0][1];
                    coordinates[1][0] = weightsCoordinates[3][0];
                    coordinates[1][1] = weightsCoordinates[3][1];
                    weightsCoordinates = coordinates;
                }

                return weightsCoordinates;
            case "Triangle":
                return weightsCoordinates;
            case "Rectangle":
            case "Quadrilateral":
                if (nodes == 8 || nodes == 9) {
                    double[][] coordinates = new double[4][4];

                    int i = 0;
                    int j = 0;
                    for (double[] wCoordinates : weightsCoordinates) {
                        if ((j % 2) == 0 && j < 8) {
                            coordinates[i][0] = wCoordinates[0];
                            coordinates[i][1] = wCoordinates[1];
                            coordinates[i][2] = wCoordinates[2];
                            coordinates[i][3] = wCoordinates[3];
                            i++;
                        }
                        j++;
                    }

                    weightsCoordinates = coordinates;
                }

                return weightsCoordinates;
            default:
                return weightsCoordinates;
        }
    }
}
