/*
 * Esta classe é responsável para cálculo da estrutura
 * A classe fornece métodos para atribuição e obtenção dos resultados da análise
 * A classe inicia o cálculo pela chamada do mátodo calculate()
 */

package app.calculations;

import static app.backend.DrawingMethods.copyElasticSupport;
import static app.backend.DrawingMethods.copyLoad;
import static app.backend.DrawingMethods.copySettlement;
import static app.backend.DrawingMethods.copySupport;
import static app.matrices.Multiply.multiply;
import static app.matrices.Subtract.subtract;
import static app.matrices.Sum.sum;
import static java.lang.Math.abs;
import static java.lang.Math.round;

import app.backend.DrawElasticSupports;
import app.backend.DrawLine;
import app.backend.DrawLoads;
import app.backend.DrawPolygon;
import app.backend.DrawSettlements;
import app.backend.DrawSupports;
import app.backend.Geometry.Material;
import app.backend.Geometry.Nodes;
import app.backend.Geometry.Section;
import app.backend.TypesOfLoads;
import java.util.ArrayList;

/**
 *
 * @author André de Sousa
 */
public class Processor {

    //Variável para construção dos elementos finitos
    public ArrayList<FiniteElement> arrayListFiniteElements;

    //Tipo de elemento finito e teoria de cálculo
    private String type;
    private String theory;

    /**
     * Construtor geral da classe processor
     */
    public Processor() {
        this.factor = 100.0;
        this.arrayListFiniteElements = new ArrayList();
    }

    /**
     * Construtor operacional da classe processor
     *
     * @param type
     * @param theory
     * @param factor
     */
    public Processor(String type, String theory, double factor) {
        this.factor = factor;
        this.type = type;
        this.theory = theory;
        this.arrayListFiniteElements = new ArrayList();
        this.DOF_Node = degreeOfFreedom(type, theory);
    }

    private ArrayList<DrawLine> arrayListLines = new ArrayList();
    private ArrayList<DrawPolygon> arrayListPolygons = new ArrayList();
    private final ArrayList<DrawSupports> arrayListSupports = new ArrayList();
    private final ArrayList<DrawElasticSupports> arrayListElasticSupports = new ArrayList();
    private final ArrayList<DrawSettlements> arrayListSettlements = new ArrayList();
    private final ArrayList<DrawLoads> arrayListLoads = new ArrayList();

    /**
     * Método para obter as figuras geométricas desenhadas
     *
     * @param lines
     * @param polygons
     */
    public void setDrawnFigures(ArrayList<DrawLine> lines, ArrayList<DrawPolygon> polygons) {
        this.arrayListLines = lines;
        this.arrayListPolygons = polygons;
    }

    /**
     * Método para atribuir os apoios estruturais
     *
     * @param arrayListSupports
     * @param arrayListElasticSupports
     * @param arrayListSettlements
     */
    public void setDrawnSupports(
        ArrayList<DrawSupports> arrayListSupports,
        ArrayList<DrawElasticSupports> arrayListElasticSupports,
        ArrayList<DrawSettlements> arrayListSettlements
    ) {
        this.arrayListSupports.addAll(copySupport(arrayListSupports));
        this.arrayListElasticSupports.addAll(copyElasticSupport(arrayListElasticSupports));
        this.arrayListSettlements.addAll(copySettlement(arrayListSettlements));
    }

    /**
     * Método para atribuir as cargas aplicadas nos elementos finitos
     *
     * @param arrayListLoads
     */
    public void setDrawnLoads(ArrayList<DrawLoads> arrayListLoads) {
        this.arrayListLoads.addAll(copyLoad(arrayListLoads));
    }

    //Número de nós dos elementos finitos
    private int bars;
    private int triangles;
    private int rectangles;
    private int quadrilaterals;

    //Propriedades da secção dos elementos finitos
    private double inertia;
    private double torsion;
    private double area;
    private double thickness;

    //Propriedades do material dos elementos finitos
    private String material;
    private double elasticity;
    private double poisson;
    private double thermal;

    /**
     * Método para atribuir as propriedades dos elementos finitos
     *
     * @param nodes
     * @param section
     * @param material
     */
    public void setProperties(Nodes nodes, Section section, Material material) {
        this.bars = nodes.barsNumberNodes;
        this.triangles = nodes.trianglesNumberNodes;
        this.rectangles = nodes.quadrilateralsNumberNodes;
        this.quadrilaterals = nodes.quadrilateralsNumberNodes;

        this.inertia = section.inertia;
        this.torsion = section.torsion;
        this.area = section.area;
        this.thickness = section.thickness;

        this.material = material.material;
        this.elasticity = material.elasticity;
        this.poisson = material.poisson;
        this.thermal = material.thermal;
    }

    //Lista de secções dos elementos finitos
    private ArrayList<Section> sections = new ArrayList();

    /**
     * Método para atribuir a lista de secções dos elementos finitos
     *
     * @param sections
     */
    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }

    //Listas das cargas atribuídas ao modelo
    private ArrayList<TypesOfLoads.ConcentratedLoad> concentratedLoads = new ArrayList();
    private ArrayList<TypesOfLoads.BendingMoment> bendingMoments = new ArrayList();
    private ArrayList<TypesOfLoads.DistributedLoad> distributedLoads = new ArrayList();
    private ArrayList<TypesOfLoads.AxialLoad> axialLoads = new ArrayList();
    private ArrayList<TypesOfLoads.PlanarLoad> planarLoads = new ArrayList();

    /**
     * Método para atribuir as lista de cargas estruturais
     *
     * @param concentratedLoads
     * @param bendingMoments
     * @param distributedLoads
     * @param axialLoads
     * @param planarLoads
     */
    public void setLoads(
        ArrayList<TypesOfLoads.ConcentratedLoad> concentratedLoads,
        ArrayList<TypesOfLoads.BendingMoment> bendingMoments,
        ArrayList<TypesOfLoads.DistributedLoad> distributedLoads,
        ArrayList<TypesOfLoads.AxialLoad> axialLoads,
        ArrayList<TypesOfLoads.PlanarLoad> planarLoads
    ) {
        this.concentratedLoads = concentratedLoads;
        this.bendingMoments = bendingMoments;
        this.distributedLoads = distributedLoads;
        this.axialLoads = axialLoads;
        this.planarLoads = planarLoads;
    }

    //Densidade do material dos elementos finitos
    private double density;

    /**
     * Método para atribuir a densidade do material dos elementos finitos
     *
     * @param density
     */
    public void setDensity(double density) {
        this.density = density;
    }

    //Parâmetros associados à integração numérica
    private String quadrature;
    private int pointsBars;
    private int pointsTriangles;
    private int pointsRectangles;
    private int pointsQuadrilaterals;

    /**
     * Método para atribuir a quadratura de integração das funções de forma
     *
     * @param quadrature
     * @param bars
     * @param triangles
     * @param rectQuadrilaterals
     */
    public void setQuadrature(String quadrature, int bars, int triangles, int rectQuadrilaterals) {
        this.quadrature = quadrature;
        this.pointsBars = bars;
        this.pointsTriangles = triangles;
        this.pointsRectangles = rectQuadrilaterals;
        this.pointsQuadrilaterals = rectQuadrilaterals;
    }

    //Variável que contém o número de graus de liberdade da malha
    private int DOF_Mesh;

    //Variável que contém o número de graus de liberdade da malha
    private int DOF_Node;

    //Variáveis para armazenar o vetor de solicitação, a matriz de rigidez e o vetor de deslocamentos
    public double[][] loadVector, stiffnessMatrix, displacementVector;

    //Variáveis para armazenar o vetor de reações de apoio e o vetor de forças nodais
    public double[][] supportReactions, nodalForces;

    /**
     * Método para iniciar o cálculo do modelo
     */
    public void calculate() {
        //Criação dos elementos finitos e adição ao arrayList
        int ID = 1;

        for (DrawLine line : arrayListLines) {
            double[][] nodesCoordinates = convert(line.getCoordinates(), factor);
            arrayListFiniteElements.add(new FiniteElement(ID, line.shape, type, theory, line.section, DOF_Node, nodesCoordinates));
            ID++;
        }

        for (DrawPolygon polygon : arrayListPolygons) {
            double[][] nodesCoordinates = convert(polygon.getCoordinates(), factor);
            arrayListFiniteElements.add(new FiniteElement(ID, polygon.shape, type, theory, polygon.section, DOF_Node, nodesCoordinates));
            ID++;
        }

        //Atribuição do material, da secção e de outras propriedades aos elementos finitos
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            //Atribição do número de nós e pontos de integração ao elemento finito
            switch (finiteElement.getShape()) {
                case "Line":
                    finiteElement.setNumberOfNodes(bars);
                    finiteElement.setQuadrature(quadrature, pointsBars);
                    break;
                case "Triangle":
                    finiteElement.setNumberOfNodes(triangles);
                    finiteElement.setQuadrature(quadrature, pointsTriangles);
                    break;
                case "Rectangle":
                    finiteElement.setNumberOfNodes(rectangles);
                    finiteElement.setQuadrature(quadrature, pointsRectangles);
                    break;
                case "Quadrilateral":
                    finiteElement.setNumberOfNodes(quadrilaterals);
                    finiteElement.setQuadrature(quadrature, pointsQuadrilaterals);
                    break;
            }

            //Atribuição da secção aos elementos finitos
            finiteElement.setSection(inertia, torsion, area, thickness);
            String sectionName = finiteElement.getSection();
            for (Section section : sections) {
                if (section.name.equals(sectionName)) {
                    finiteElement.setSection(section.inertia, section.torsion, section.area, section.thickness);
                }
            }

            //Atribuição do material ao elemento finito
            finiteElement.setMaterial(material, elasticity * 1000000, poisson, thermal);
        }

        //Atribuição das cargas estruturais distribuídas aos elementos finitos
        setDistributedLoads();

        //Construção da tabela de assemblagem dos elementos finitos
        assemblyTable();

        //Dimensão da matriz de rididez global da malha de elementos finitos
        int dimension = DOF_Mesh;

        //Preenchimento do vetor de solicitação, da matriz de rigidez e do vetor de deslocamentos
        loadVector = new double[dimension][1];
        stiffnessMatrix = new double[dimension][dimension];
        displacementVector = new double[dimension][1];

        for (FiniteElement finiteElement : arrayListFiniteElements) {
            double[][] vectorF, matrixK, vectorA;

            vectorF = finiteElement.getLoadVector();
            matrixK = finiteElement.getStiffnessMatrix();
            //vectorA = finiteElement.getDisplacementVector();

            int[][] assemblyTable = finiteElement.getAssemblyTable();
            int length = assemblyTable.length;

            for (int i = 0; i < length; i++) {
                int column = assemblyTable[i][1] - 1;

                //Prenchimento da matriz de rigidez global
                for (int j = 0; j < length; j++) {
                    int line = assemblyTable[j][1] - 1;
                    stiffnessMatrix[line][column] = stiffnessMatrix[line][column] + matrixK[j][i];
                }

                int line = column;

                //Preenchimento do vetor de solicitação global
                loadVector[line][0] = loadVector[line][0] + vectorF[i][0];
                //Preenchimento do vetor de deslocamentos global
                //displacementVector[line][0] = displacementVector[line][0] + vectorA[i][0];
            }
        }

        //Atribuição das cargas estruturais concentradas ao vetor F
        setNodalLoads();

        //Identificação da posição dos apoios estruturais na malha
        supportConditions();

        //Atribuição da rigidez dos apoios elásticas à matriz de rigidez
        setElasticSupports();

        //Atribuição dos assentamentos de apoio aos respectivos nós da malha
        setSettlements();

        /*
         * Resolução do sistema de equações F = K . a
         *
         * | Fl | = | Kll Klf | = | al |
         * | Ff |   | Kfl Kff |   | af |
         *
         *
         * Os termos Kll referen-se aos termos sem qualquer relação com os nós fixos
         * Os termos Kff referem-se aos termos quem têm os nós fixos
         *
         * Os termos Klf são os termos livres que se relacionam com os termos fixos
         * Os termos Kfl são os termos fixos que se relacionam com s termos livres
         *
         *
         * { al } = [ Kll ]^-1 * ({ Fl } - [ Klf ] * { af })
         *
         * { Ff } = [ Kfl ] * { al } + [ Kff ] * { af }
         *
         */

        //Contagem do número de nós fixos e nós livres
        int fixedNodes = 0;
        int freeNodes = 0;

        if (!arrayListElasticSupports.isEmpty()) {
            for (double[] nodes : arrayListNodes) {
                if (nodes[4] == 1 || nodes[4] == -1) {
                    fixedNodes++;
                }
                if (nodes[4] == 2 || nodes[4] == -2) {
                    fixedNodes++;
                    freeNodes++;
                }
                if (nodes[4] == 0) {
                    freeNodes++;
                }
            }
        } else {
            for (double[] nodes : arrayListNodes) {
                if (nodes[4] == 1 || nodes[4] == -1) {
                    fixedNodes++;
                }
                if (nodes[4] == 0) {
                    freeNodes++;
                }
            }
        }

        //Sub-matrizes de assemblagem da matriz de rigidez
        int[] matrixFreeNodes = new int[freeNodes];
        int[] matrixFixedNodes = new int[fixedNodes];
        int line;

        line = 0;
        for (int i = 0; i < freeNodes; i++) {
            while (line < arrayListNodes.size()) {
                double[] nodes = arrayListNodes.get(line);
                line++;

                if (abs(nodes[4]) == 0 || abs(nodes[4]) == 2) {
                    matrixFreeNodes[i] = (int) nodes[2];
                    break;
                }
            }
        }

        line = 0;
        for (int i = 0; i < fixedNodes; i++) {
            while (line < arrayListNodes.size()) {
                double[] nodes = arrayListNodes.get(line);
                line++;

                if (abs(nodes[4]) == 1 || abs(nodes[4]) == 2) {
                    matrixFixedNodes[i] = (int) nodes[2];
                    break;
                }
            }
        }

        /*
         * Resolução do sistema de equações F = K . a
         *
         * { al } = [ Kll ]^-1 * ({ Fl } - [ Klf ] * { af })
         *
         * { Ff } = [ Kfl ] * { al } + [ Kff ] * { af }
         *
         */

        //Criação dos sub-vetores de solicitação e de deslocamentos
        double[][] Ff, al;

        if (!arrayListSettlements.isEmpty()) {
            //Resolução do sistema de equações com deslocamentos impostos
            if (freeNodes > 0) {
                al =
                    sum(
                        getVector_al(freeNodes),
                        multiply(
                            inverse(getMatrix_Kll(freeNodes, matrixFreeNodes)),
                            subtract(
                                getVector_Fl(freeNodes),
                                multiply(getMatrix_Klf(freeNodes, fixedNodes, matrixFreeNodes, matrixFixedNodes), getVector_af(fixedNodes))
                            )
                        )
                    );
                Ff =
                    subtract(
                        sum(
                            multiply(getMatrix_Kfl(freeNodes, fixedNodes, matrixFreeNodes, matrixFixedNodes), al),
                            multiply(getMatrix_Kff(fixedNodes, matrixFixedNodes), getVector_af(fixedNodes))
                        ),
                        getVector_Ff(fixedNodes)
                    );
            } else {
                al = null;
                Ff = subtract(multiply(getMatrix_Kff(fixedNodes, matrixFixedNodes), getVector_af(fixedNodes)), getVector_Ff(fixedNodes));
            }
        } else {
            //Resolução do sistema de equações sem deslocamentos impostos
            if (freeNodes > 0) {
                al = multiply(inverse(getMatrix_Kll(freeNodes, matrixFreeNodes)), getVector_Fl(freeNodes));
                Ff =
                    subtract(
                        multiply(getMatrix_Kfl(freeNodes, fixedNodes, matrixFreeNodes, matrixFixedNodes), al),
                        getVector_Ff(fixedNodes)
                    );
            } else {
                al = null;
                Ff = subtract(new double[fixedNodes][1], getVector_Ff(fixedNodes));
            }
        }

        //Construção do vetor de solicitação global
        for (int i = 0; i < fixedNodes; i++) {
            int k = matrixFixedNodes[i] - 1;
            loadVector[k][0] = loadVector[k][0] + Ff[i][0];
        }

        //Construção do vetor de deslocamentos global
        for (int i = 0; i < freeNodes; i++) {
            int k = matrixFreeNodes[i] - 1;
            displacementVector[k][0] = al[i][0];
        }

        //Atribuição dos deslocamentos nodais aos elementos finitos
        for (FiniteElement finiteElement : arrayListFiniteElements) {
            int[][] assemblyTable = finiteElement.getAssemblyTable();
            int length = assemblyTable.length;

            double[][] vector = new double[length][1];

            for (int i = 0; i < length; i++) {
                vector[i][0] = displacementVector[assemblyTable[i][1] - 1][0];
            }

            finiteElement.setDisplacements(vector);
        }

        supportReactions = new double[fixedNodes][1];

        //Multiplicação das reações pelo fator de orientação
        line = 0;
        for (int i = 0; i < fixedNodes; i++) {
            while (line < arrayListNodes.size()) {
                double[] nodes = arrayListNodes.get(line);
                line++;

                if (nodes[4] == 1 || nodes[4] == -1) {
                    supportReactions[i][0] = Ff[i][0] * nodes[4];
                    break;
                }
                if (nodes[4] == 2 || nodes[4] == -2) {
                    supportReactions[i][0] = -getVector_af(fixedNodes)[i][0] * nodes[6] * (nodes[4] / 2);
                    break;
                }
            }
        }
    }

    /**
     * Método que fornece uma matriz com a descrição das reações de apoio
     *
     * @return
     */
    public String[][] getSupportReactions() {
        String[][] reactions;

        if (supportReactions != null) {
            reactions = FormatResults.getSupportReactions(type, theory, arrayListNodes, supportReactions);
        } else {
            reactions = null;
        }

        return reactions;
    }

    /**
     * Método que fornece a lista dos apoios estruturais
     *
     * @return
     */
    public ArrayList<DrawSupports> getRigidSupports() {
        return arrayListSupports;
    }

    /**
     * Método que fornece a lista dos apoios elásticos
     *
     * @return
     */
    public ArrayList<DrawElasticSupports> getElasticSupports() {
        return arrayListElasticSupports;
    }

    /**
     * Método para obter as coordenadas dos nós e a sua numeração
     *
     * 0: A primeira coluna contém a coordenada x
     * 1: A segunda coluna contém a coordenada y
     * 2: A terceira coluna contém o numeração dos graus de liberdade
     * 3: A quarta coluna contém o número identificativo do grau de liberdade
     * 4: A quinta coluna contém a pescrição do graus de liberdade
     * 5: A sexta coluna contém a numeração dos nós da malha
     * 6: A sétima coluna contém a rigidez dos apoios elásticos
     *
     * @return
     */
    public ArrayList<double[]> getArrayListNodes() {
        return arrayListNodes;
    }

    //Fator de conversão das coordenadas de pixels para metros
    private final double factor;

    /**
     * Método para obter o factor de escala
     *
     * @return
     */
    public int getFactor() {
        return (int) round(factor);
    }

    /**
     * Método para converter as coordenadas de pixeis para metros
     *
     * @param coordinates
     * @param factor
     * @return
     */
    private static double[][] convert(int[][] coordinates, double factor) {
        double[][] nodesCoordinates;

        int lines = coordinates.length;
        int columns = coordinates[0].length;

        if (lines > 0 && columns == 2) {
            nodesCoordinates = new double[lines][columns];

            for (int i = 0; i < lines; i++) {
                nodesCoordinates[i][0] = coordinates[i][0] / factor;
                nodesCoordinates[i][1] = coordinates[i][1] / factor;
            }
        } else {
            nodesCoordinates = null;
        }

        return nodesCoordinates;
    }

    /**
     * Método para comparar as coordenadas da carga com as do elemento finito
     *
     * @param loadType
     * @param shape
     * @param loadCoordinates
     * @param shapeCoordinates
     * @return
     */
    private static boolean compareCoordinates(String loadType, String shape, double[][] loadCoordinates, double[][] shapeCoordinates) {
        boolean result = false;
        int counter;

        if ("Concentrated Load".equals(loadType) || "Bending Moment".equals(loadType)) {
            counter = 0;
            for (double[] shapeCoordinate : shapeCoordinates) {
                if (loadCoordinates[0][0] == shapeCoordinate[0]) {
                    if (loadCoordinates[0][1] == shapeCoordinate[1]) {
                        counter++;
                        break;
                    }
                }
            }

            if (counter == 1) {
                result = true;
            }
        }

        if ("Distributed Load".equals(loadType) || "Axial Load".equals(loadType) || "Thermal Load".equals(loadType)) {
            counter = 0;
            for (double[] loadCoordinate : loadCoordinates) {
                for (double[] shapeCoordinate : shapeCoordinates) {
                    if (loadCoordinate[0] == shapeCoordinate[0]) {
                        if (loadCoordinate[1] == shapeCoordinate[1]) {
                            counter++;
                            break;
                        }
                    }
                }
            }

            if (counter == 2) {
                result = true;
            }
        }

        if ("Planar Load".equals(loadType)) {
            if ("Triangle".equals(shape)) {
                counter = 0;
                for (double[] loadCoordinate : loadCoordinates) {
                    for (double[] shapeCoordinate : shapeCoordinates) {
                        if (loadCoordinate[0] == shapeCoordinate[0]) {
                            if (loadCoordinate[1] == shapeCoordinate[1]) {
                                counter++;
                                break;
                            }
                        }
                    }
                }

                if (counter == 3) {
                    result = true;
                }
            }
            if ("Rectangle".equals(shape) || "Quadrilateral".equals(shape)) {
                counter = 0;
                for (double[] loadCoordinate : loadCoordinates) {
                    for (double[] shapeCoordinate : shapeCoordinates) {
                        if (loadCoordinate[0] == shapeCoordinate[0]) {
                            if (loadCoordinate[1] == shapeCoordinate[1]) {
                                counter++;
                                break;
                            }
                        }
                    }
                }

                if (counter == 4) {
                    result = true;
                }
            }
        }

        return result;
    }

    /**
     * Método para definir o número de graus de liberdade por nó
     *
     * @param type
     * @param theory
     * @return
     */
    private static int degreeOfFreedom(String type, String theory) {
        int degreeOfFreedom;

        switch (type) {
            case "1D":
                degreeOfFreedom = 2;
                break;
            case "2D":
                degreeOfFreedom = 2;
                break;
            case "3D":
                degreeOfFreedom = 3;
                break;
            case "Beams":
                degreeOfFreedom = 2;
                break;
            case "Frames":
                degreeOfFreedom = 3;
                break;
            case "Grids":
                degreeOfFreedom = 3;
                break;
            case "Slabs":
                switch (theory) {
                    case "Reissner-Mindlin":
                        degreeOfFreedom = 3;
                        break;
                    case "Kirchhoff":
                        degreeOfFreedom = 4;
                        break;
                    default:
                        degreeOfFreedom = 0;
                        break;
                }
                break;
            default:
                degreeOfFreedom = 0;
                break;
        }

        return degreeOfFreedom;
    }

    /**
     * Variável para receber as coordenadas dos nós e a sua numeração
     *
     * 0: A primeira coluna recebe a coordenada x
     * 1: A segunda coluna recebe a coordenada y
     * 2: A terceira coluna recebe o numeração dos graus de liberdade
     * 3: A quarta coluna recebe o número identificativo do grau de liberdade
     * 4: A quinta coluna recebe a pescrição do graus de liberdade
     * 5: A sexta coluna recebe a numeração dos nós da malha
     * 6: A sétima coluna contém a rigidez dos apoios elásticos
     */
    private final ArrayList<double[]> arrayListNodes = new ArrayList();

    /**
     * Método para construir a tabela de assemblagem de cada elemento finito
     */
    private void assemblyTable() {
        //Variável para contar o número de nós da malha
        int numberOfNode = 1;

        //Variável para contar o número de graus de liberdade da malha
        boolean newNode;
        int counter = 1;

        //Variável relativa ao número de graus de liberdade por nó
        int degreeOfFreedom = DOF_Node;

        for (FiniteElement finiteElement : arrayListFiniteElements) {
            double[][] nodesCoordinates = finiteElement.getNodesCoordinates();
            int nodes = finiteElement.getNodes();
            int line = 0;

            int[][] assemblyTable = new int[nodes * degreeOfFreedom][2];

            //Construção da primeira coluna da tabela de assemblagem
            for (int i = 0; i < nodes * degreeOfFreedom; i++) {
                assemblyTable[i][0] = i + 1;
            }

            //Construção da segunda coluna da tabela de assemblagem

            int length = nodesCoordinates.length;
            for (int i = 0; i < length; i++) {
                double x = nodesCoordinates[i][0];
                double y = nodesCoordinates[i][1];

                newNode = false;
                for (int j = 1; j <= degreeOfFreedom; j++) {
                    //Verificação se o ArrayList coordinates contém as coordenadas do nó
                    double number = 0;
                    boolean contains = false;

                    for (double[] node : arrayListNodes) {
                        if (x == node[0] && y == node[1] && j == (int) node[3]) {
                            contains = true;
                            number = node[2];
                        }
                    }

                    //Se contém adiciona a numeração desse nó ao nó do elemento finito
                    //Se não, adiciona um novo nó a lista de nós da malha
                    if (contains) {
                        assemblyTable[line][1] = (int) number;
                    } else {
                        double[] node = { x, y, counter, j, 0, numberOfNode, 0 };
                        arrayListNodes.add(node);
                        assemblyTable[line][1] = counter;

                        newNode = true;
                        counter++;
                    }

                    line++;
                }

                //Incremento de um novo nó á malha de elementos finitos
                if (newNode) {
                    numberOfNode++;
                }
            }

            //Atribuição da tabela de assemblagem ao elemento finito
            finiteElement.setAssemblyTable(assemblyTable);
        }

        //Número de graus de liberdade da malha de elementos finitos
        DOF_Mesh = counter - 1;
    }

    /**
     * Método para determinar as condições de apoio de cada nó da malha
     *
     * 2: Nó restringido
     * 1: Nó fixo
     * 0: Nó livre
     */
    private void supportConditions() {
        elasticSupports();
        rigidSupports();
    }

    /**
     * Método para classificar os nós com apoios elásticos
     */
    private void elasticSupports() {
        for (DrawElasticSupports support : arrayListElasticSupports) {
            double[][] coordinates = convert(support.getCoordinates(), factor);
            double[] stiffness = support.stiffness;

            if ("1D".equals(type) || "2D".equals(type)) {
                if (stiffness[0] > 0.0) {
                    int j = 1;
                    for (double[] node : arrayListNodes) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = 2;
                            break;
                        }
                    }
                }
                if (stiffness[1] > 0.0) {
                    int j = 2;
                    for (double[] node : arrayListNodes) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = -2;
                            break;
                        }
                    }
                }
            }

            if ("Beams".equals(type)) {
                if (stiffness[1] > 0.0) {
                    int j = 1;
                    for (double[] node : arrayListNodes) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = -2;
                            break;
                        }
                    }
                }
                if (stiffness[2] > 0.0) {
                    int j = 2;
                    for (double[] node : arrayListNodes) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = 2;
                            break;
                        }
                    }
                }
            }

            if ("Frames".equals(type)) {
                if (stiffness[0] > 0.0) {
                    int j = 1;
                    for (double[] node : arrayListNodes) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = 2;
                            break;
                        }
                    }
                }
                if (stiffness[1] > 0.0) {
                    int j = 2;
                    for (double[] node : arrayListNodes) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = -2;
                            break;
                        }
                    }
                }
                if (stiffness[2] > 0.0) {
                    int j = 3;
                    for (double[] node : arrayListNodes) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = 2;
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Método para classificar os nós como fixos ou livres
     */
    private void rigidSupports() {
        int degreeOfFreedom = DOF_Node;

        for (DrawSupports support : arrayListSupports) {
            double[][] supportCoordinates = convert(support.getCoordinates(), factor);

            if ("1D".equals(type) || "2D".equals(type)) {
                if ("Horizontal Support".equals(support.support)) {
                    int j = 1;
                    for (double[] node : arrayListNodes) {
                        if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = 1;
                            break;
                        }
                    }
                }
                if ("Vertical Support".equals(support.support)) {
                    int j = 2;
                    for (double[] node : arrayListNodes) {
                        if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = -1;
                            break;
                        }
                    }
                }
                if ("Pinned Support".equals(support.support)) {
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                if (j == 1) {
                                    node[4] = 1;
                                }
                                if (j == 2) {
                                    node[4] = -1;
                                }
                                break;
                            }
                        }
                    }
                }
            }

            if ("3D".equals(type)) {
                //TODO add your handling code here
            }

            if ("Beams".equals(type)) {
                if ("Vertical Support".equals(support.support)) {
                    int j = 1;
                    for (double[] node : arrayListNodes) {
                        if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = -1;
                            break;
                        }
                    }
                }
                if ("Horizontal Slider".equals(support.support)) {
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                if (j == 1) {
                                    node[4] = -1;
                                }
                                if (j == 2) {
                                    node[4] = 1;
                                }
                                break;
                            }
                        }
                    }
                }
            }

            if ("Frames".equals(type)) {
                if ("Horizontal Support".equals(support.support)) {
                    int j = 1;
                    for (double[] node : arrayListNodes) {
                        if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = 1;
                            break;
                        }
                    }
                }
                if ("Vertical Support".equals(support.support)) {
                    int j = 2;
                    for (double[] node : arrayListNodes) {
                        if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = -1;
                            break;
                        }
                    }
                }
                if ("Pinned Support".equals(support.support)) {
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                if (j == 1) {
                                    node[4] = 1;
                                }
                                if (j == 2) {
                                    node[4] = -1;
                                }
                                break;
                            }
                        }
                    }
                }
                if ("Horizontal Slider".equals(support.support)) {
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                if (j == 2) {
                                    node[4] = -1;
                                }
                                if (j == 3) {
                                    node[4] = 1;
                                }
                                break;
                            }
                        }
                    }
                }
                if ("Vertical Slider".equals(support.support)) {
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                if (j == 1) {
                                    node[4] = 1;
                                }
                                if (j == 3) {
                                    node[4] = 1;
                                }
                                break;
                            }
                        }
                    }
                }
                if ("Fixed Support".equals(support.support)) {
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                if (j == 1) {
                                    node[4] = 1;
                                }
                                if (j == 2) {
                                    node[4] = -1;
                                }
                                if (j == 3) {
                                    node[4] = 1;
                                }
                                break;
                            }
                        }
                    }
                }
            }

            if ("Grids".equals(type)) {
                if ("Vertical Support".equals(support.support)) {
                    int j = 1;
                    for (double[] node : arrayListNodes) {
                        if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = -1;
                            break;
                        }
                    }
                }
                if ("Horizontal Slider".equals(support.support)) {
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                if (j == 1) {
                                    node[4] = -1;
                                }
                                if (j == 2) {
                                    node[4] = 1;
                                }
                                if (j == 3) {
                                    node[4] = 1;
                                }
                                break;
                            }
                        }
                    }
                }
            }

            if ("Slabs".equals(type)) {
                if ("Vertical Support".equals(support.support)) {
                    int j = 1;
                    for (double[] node : arrayListNodes) {
                        if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                            node[4] = -1;
                            break;
                        }
                    }
                }
                if ("Horizontal Slider".equals(support.support)) {
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (supportCoordinates[0][0] == node[0] && supportCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                if (j == 1) {
                                    node[4] = -1;
                                }
                                if (j == 2) {
                                    node[4] = 1;
                                }
                                if (j == 3) {
                                    node[4] = 1;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Método par atribuir a rigidez dos apoios elásticos à matriz de rigidez
     */
    private void setElasticSupports() {
        int degreeOfFreedom = DOF_Node;

        for (DrawElasticSupports elasticSupports : arrayListElasticSupports) {
            double[][] coordinates = convert(elasticSupports.getCoordinates(), factor);
            double[] stiffness = elasticSupports.stiffness;

            if ("1D".equals(type) || "2D".equals(type)) {
                int i = 0;
                while (i < arrayListNodes.size()) {
                    double[] node = arrayListNodes.get(i);

                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            int position = (int) node[2] - 1;

                            if (j == 1 && stiffness[0] > 0.0) {
                                stiffnessMatrix[position][position] = stiffnessMatrix[position][position] + stiffness[0];
                                node[6] = stiffness[0];
                            }
                            if (j == 2 && stiffness[1] > 0.0) {
                                stiffnessMatrix[position][position] = stiffnessMatrix[position][position] + stiffness[1];
                                node[6] = stiffness[1];
                            }
                        }
                    }

                    i++;
                }
            }

            if ("Beams".equals(type)) {
                int i = 0;
                while (i < arrayListNodes.size()) {
                    double[] node = arrayListNodes.get(i);

                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            int position = (int) node[2] - 1;

                            if (j == 1 && stiffness[1] > 0.0) {
                                stiffnessMatrix[position][position] = stiffnessMatrix[position][position] + stiffness[1];
                                node[6] = stiffness[1];
                            }
                            if (j == 2 && stiffness[2] > 0.0) {
                                stiffnessMatrix[position][position] = stiffnessMatrix[position][position] + stiffness[2];
                                node[6] = stiffness[2];
                            }
                        }
                    }

                    i++;
                }
            }

            if ("Frames".equals(type)) {
                int i = 0;
                while (i < arrayListNodes.size()) {
                    double[] node = arrayListNodes.get(i);

                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            int position = (int) node[2] - 1;

                            if (j == 1 && stiffness[0] > 0.0) {
                                stiffnessMatrix[position][position] = stiffnessMatrix[position][position] + stiffness[0];
                                node[6] = stiffness[0];
                            }
                            if (j == 2 && stiffness[1] > 0.0) {
                                stiffnessMatrix[position][position] = stiffnessMatrix[position][position] + stiffness[1];
                                node[6] = stiffness[1];
                            }
                            if (j == 3 && stiffness[2] > 0.0) {
                                stiffnessMatrix[position][position] = stiffnessMatrix[position][position] + stiffness[2];
                                node[6] = stiffness[2];
                            }
                        }
                    }

                    i++;
                }
            }
        }
    }

    /**
     * Método para adicionar os assentamentos de apoio à malha
     */
    private void setSettlements() {
        int degreeOfFreedom = DOF_Node;

        for (DrawSettlements settlements : arrayListSettlements) {
            double[][] coordinates = convert(settlements.getCoordinates(), factor);

            if ("1D".equals(type) || "2D".equals(type)) {
                boolean result = false;
                int position = -1;
                for (int j = 1; j <= degreeOfFreedom; j++) {
                    for (double[] node : arrayListNodes) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            result = true;
                            position = (int) node[2];
                            break;
                        }
                    }

                    if (result == true && j == 1) {
                        displacementVector[position - 1][0] = displacementVector[position - 1][0] + settlements.displacements[0];
                    }
                    if (result == true && j == 2) {
                        displacementVector[position - 1][0] = displacementVector[position - 1][0] + settlements.displacements[1];
                    }
                    result = false;
                }
            }

            if ("Beams".equals(type)) {
                boolean result = false;
                int position = -1;
                for (int j = 1; j <= degreeOfFreedom; j++) {
                    for (double[] node : arrayListNodes) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            result = true;
                            position = (int) node[2];
                            break;
                        }
                    }

                    if (result == true && j == 1) {
                        displacementVector[position - 1][0] = displacementVector[position - 1][0] + settlements.displacements[1];
                    }
                    if (result == true && j == 2) {
                        displacementVector[position - 1][0] = displacementVector[position - 1][0] + settlements.displacements[2];
                    }
                    result = false;
                }
            }

            if ("Frames".equals(type)) {
                boolean result = false;
                int position = -1;
                for (int j = 1; j <= degreeOfFreedom; j++) {
                    for (double[] node : arrayListNodes) {
                        if (coordinates[0][0] == node[0] && coordinates[0][1] == node[1] && j == (int) node[3]) {
                            result = true;
                            position = (int) node[2];
                            break;
                        }
                    }

                    if (result == true && j == 1) {
                        displacementVector[position - 1][0] = displacementVector[position - 1][0] + settlements.displacements[0];
                    }
                    if (result == true && j == 2) {
                        displacementVector[position - 1][0] = displacementVector[position - 1][0] + settlements.displacements[1];
                    }
                    if (result == true && j == 3) {
                        displacementVector[position - 1][0] = displacementVector[position - 1][0] + settlements.displacements[2];
                    }
                    result = false;
                }
            }
        }
    }

    /**
     * Atribuição da carga estrutural ao respetivo elemento finito
     */
    private void setDistributedLoads() {
        //Atribuição das cargas da lista arrayListLoads
        for (DrawLoads load : arrayListLoads) {
            double[][] loadCoordinates = convert(load.getCoordinates(), factor);
            boolean result;

            if ("Distributed Load".equals(load.loadType) || "Axial Load".equals(load.loadType) || "Thermal Load".equals(load.loadType)) {
                for (FiniteElement finiteElement : arrayListFiniteElements) {
                    result =
                        compareCoordinates(load.loadType, finiteElement.getShape(), loadCoordinates, finiteElement.getShapeCoordinates());

                    if (result == true) {
                        if ("Distributed Load".equals(load.loadType)) {
                            finiteElement.setLoads(load.loadType, loadCoordinates, load.horizontalValue, load.verticalValue, 0.0);
                        }
                        if ("Axial Load".equals(load.loadType)) {
                            finiteElement.setLoads(load.loadType, loadCoordinates, load.value, 0.0, 0.0);
                        }
                        if ("Thermal Load".equals(load.loadType)) {
                            finiteElement.setThermalLoads(load.Tzero, load.Ttop, load.Tbot);
                        }
                        break;
                    }
                }
            }

            if ("Planar Load".equals(load.loadType)) {
                for (FiniteElement finiteElement : arrayListFiniteElements) {
                    if (!"Line".equals(finiteElement.getShape())) {
                        result =
                            compareCoordinates(
                                load.loadType,
                                finiteElement.getShape(),
                                loadCoordinates,
                                finiteElement.getShapeCoordinates()
                            );

                        if (result == true) {
                            finiteElement.setLoads(load.loadType, loadCoordinates, 0.0, 0.0, load.value);
                            break;
                        }
                    }
                }
            }
        }

        //Atribuição do peso próprio aos elementos finitos
        if (density > 0) {
            for (FiniteElement finiteElement : arrayListFiniteElements) {
                if (!"1D".equals(type)) {
                    finiteElement.setSelfWeight(density);
                }
            }
        }
    }

    /**
     * Atribuição das cargas estruturais concentradas ao vetor F
     */
    private void setNodalLoads() {
        int degreeOfFreedom = DOF_Node;

        for (DrawLoads load : arrayListLoads) {
            double[][] loadCoordinates = convert(load.getCoordinates(), factor);

            if ("Concentrated Load".equals(load.loadType)) {
                int position;
                boolean result = false;

                if ("1D".equals(type)) {
                    position = -1;
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (loadCoordinates[0][0] == node[0] && loadCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                result = true;
                                position = (int) node[2];
                                break;
                            }
                        }

                        if (result == true && j == 1) {
                            loadVector[position - 1][0] = loadVector[position - 1][0] + load.horizontalValue;
                        }
                        if (result == true && j == 2) {
                            loadVector[position - 1][0] = loadVector[position - 1][0] + load.verticalValue;
                        }
                    }
                }

                if ("2D".equals(type)) {
                    position = -1;
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (loadCoordinates[0][0] == node[0] && loadCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                result = true;
                                position = (int) node[2];
                                break;
                            }
                        }

                        if ("Plane Stress".equals(theory)) {
                            if (result == true && j == 1) {
                                loadVector[position - 1][0] = loadVector[position - 1][0] + load.horizontalValue * thickness;
                            }
                            if (result == true && j == 2) {
                                loadVector[position - 1][0] = loadVector[position - 1][0] + load.verticalValue * thickness;
                            }
                        } else {
                            if (result == true && j == 1) {
                                loadVector[position - 1][0] = loadVector[position - 1][0] + load.horizontalValue;
                            }
                            if (result == true && j == 2) {
                                loadVector[position - 1][0] = loadVector[position - 1][0] + load.verticalValue;
                            }
                        }
                    }
                }

                if ("Beams".equals(type)) {
                    position = -1;
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (loadCoordinates[0][0] == node[0] && loadCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                result = true;
                                position = (int) node[2];
                                break;
                            }
                        }

                        if (result == true && j == 1) {
                            loadVector[position - 1][0] = loadVector[position - 1][0] + load.verticalValue;
                        }
                    }
                }

                if ("Frames".equals(type)) {
                    position = -1;
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (loadCoordinates[0][0] == node[0] && loadCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                result = true;
                                position = (int) node[2];
                                break;
                            }
                        }

                        if (result == true && j == 1) {
                            loadVector[position - 1][0] = loadVector[position - 1][0] + load.horizontalValue;
                        }
                        if (result == true && j == 2) {
                            loadVector[position - 1][0] = loadVector[position - 1][0] + load.verticalValue;
                        }
                    }
                }

                if ("Grids".equals(type)) {
                    position = -1;
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (loadCoordinates[0][0] == node[0] && loadCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                result = true;
                                position = (int) node[2];
                                break;
                            }
                        }

                        if (result == true && j == 1) {
                            loadVector[position - 1][0] = loadVector[position - 1][0] + load.verticalValue;
                        }
                    }
                }

                if ("Slabs".equals(type)) {
                    position = -1;
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (loadCoordinates[0][0] == node[0] && loadCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                result = true;
                                position = (int) node[2];
                                break;
                            }
                        }

                        if (result == true && j == 1) {
                            loadVector[position - 1][0] = loadVector[position - 1][0] + load.verticalValue;
                        }
                    }
                }
            }

            if ("Bending Moment".equals(load.loadType)) {
                boolean result = false;

                if ("Beams".equals(type)) {
                    int position = -1;
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (loadCoordinates[0][0] == node[0] && loadCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                result = true;
                                position = (int) node[2];
                                break;
                            }
                        }

                        if (result == true && j == 2) {
                            loadVector[position - 1][0] = loadVector[position - 1][0] + load.value;
                        }
                    }
                }

                if ("Frames".equals(type)) {
                    int position = -1;
                    for (int j = 1; j <= degreeOfFreedom; j++) {
                        for (double[] node : arrayListNodes) {
                            if (loadCoordinates[0][0] == node[0] && loadCoordinates[0][1] == node[1] && j == (int) node[3]) {
                                result = true;
                                position = (int) node[2];
                                break;
                            }
                        }

                        if (result == true && j == 3) {
                            loadVector[position - 1][0] = loadVector[position - 1][0] + load.value;
                        }
                    }
                }
            }
        }
    }

    /**
     * Construção do vetor de solicitação com nós livres
     *
     * @param freeNodes
     * @return
     */
    private double[][] getVector_Fl(int freeNodes) {
        double[][] Fl = new double[freeNodes][1];

        int line = 0;
        for (int i = 0; i < freeNodes; i++) {
            while (line < arrayListNodes.size()) {
                double[] nodes = arrayListNodes.get(line);
                line++;

                if (abs(nodes[4]) == 0 || abs(nodes[4]) == 2) {
                    int k = (int) nodes[2] - 1;
                    Fl[i][0] = loadVector[k][0];
                    break;
                }
            }
        }

        return Fl;
    }

    /**
     * Construção do vetor de solicitação com nós fixos
     *
     * @param fixedNodes
     * @return
     */
    private double[][] getVector_Ff(int fixedNodes) {
        double[][] Ff = new double[fixedNodes][1];

        int line = 0;
        for (int i = 0; i < fixedNodes; i++) {
            while (line < arrayListNodes.size()) {
                double[] nodes = arrayListNodes.get(line);
                line++;

                if (abs(nodes[4]) == 1 || abs(nodes[4]) == 2) {
                    int k = (int) nodes[2] - 1;
                    Ff[i][0] = loadVector[k][0];
                    break;
                }
            }
        }

        return Ff;
    }

    /**
     * Construção do vetor de deslocamentos com nós livres
     *
     * @param freeNodes
     * @return
     */
    private double[][] getVector_al(int freeNodes) {
        double[][] al = new double[freeNodes][1];

        int line = 0;
        for (int i = 0; i < freeNodes; i++) {
            while (line < arrayListNodes.size()) {
                double[] nodes = arrayListNodes.get(line);
                line++;

                if (abs(nodes[4]) == 0 || abs(nodes[4]) == 2) {
                    int k = (int) nodes[2] - 1;
                    al[i][0] = displacementVector[k][0];
                    break;
                }
            }
        }

        return al;
    }

    /**
     * Construção do vetor de deslocamentos com nós fixos
     *
     * @param fixedNodes
     * @return
     */
    private double[][] getVector_af(int fixedNodes) {
        double[][] af = new double[fixedNodes][1];

        int line = 0;
        for (int i = 0; i < fixedNodes; i++) {
            while (line < arrayListNodes.size()) {
                double[] nodes = arrayListNodes.get(line);
                line++;

                if (abs(nodes[4]) == 1 || abs(nodes[4]) == 2) {
                    int k = (int) nodes[2] - 1;
                    af[i][0] = displacementVector[k][0];
                    break;
                }
            }
        }

        return af;
    }

    /**
     * Método para construir a sub-matriz Kll
     *
     * @param freeNodes
     * @param matrixFreeNodes
     * @return
     */
    private double[][] getMatrix_Kll(int freeNodes, int[] matrixFreeNodes) {
        double[][] Kll = new double[freeNodes][freeNodes];

        for (int i = 0; i < freeNodes; i++) {
            for (int j = 0; j < freeNodes; j++) {
                Kll[i][j] = stiffnessMatrix[matrixFreeNodes[i] - 1][matrixFreeNodes[j] - 1];
            }
        }

        return Kll;
    }

    /**
     * Método para construir a sub-matriz Klf
     *
     * @param freeNodes
     * @param fixedNodes
     * @param matrixFreeNodes
     * @param matrixFixedNodes
     * @return
     */
    private double[][] getMatrix_Klf(int freeNodes, int fixedNodes, int[] matrixFreeNodes, int[] matrixFixedNodes) {
        double[][] Klf = new double[freeNodes][fixedNodes];

        for (int i = 0; i < freeNodes; i++) {
            for (int j = 0; j < fixedNodes; j++) {
                Klf[i][j] = stiffnessMatrix[matrixFreeNodes[i] - 1][matrixFixedNodes[j] - 1];
            }
        }

        return Klf;
    }

    /**
     * Método para construir a sub-matriz Kfl
     *
     * @param freeNodes
     * @param fixedNodes
     * @param matrixFreeNodes
     * @param matrixFixedNodes
     * @return
     */
    private double[][] getMatrix_Kfl(int freeNodes, int fixedNodes, int[] matrixFreeNodes, int[] matrixFixedNodes) {
        double[][] Kfl = new double[fixedNodes][freeNodes];

        for (int i = 0; i < fixedNodes; i++) {
            for (int j = 0; j < freeNodes; j++) {
                Kfl[i][j] = stiffnessMatrix[matrixFixedNodes[i] - 1][matrixFreeNodes[j] - 1];
            }
        }

        return Kfl;
    }

    /**
     * Método para construir a sub-matriz Kff
     *
     * @param fixedNodes
     * @param matrixFixedNodes
     * @return
     */
    private double[][] getMatrix_Kff(int fixedNodes, int[] matrixFixedNodes) {
        double[][] Kff = new double[fixedNodes][fixedNodes];

        for (int i = 0; i < fixedNodes; i++) {
            for (int j = 0; j < fixedNodes; j++) {
                Kff[i][j] = stiffnessMatrix[matrixFixedNodes[i] - 1][matrixFixedNodes[j] - 1];
            }
        }

        return Kff;
    }

    /**
     * Método para calcular a inversa de uma matriz
     *
     * @param matrixA
     * @return
     */
    private static double[][] inverse(double[][] matrixA) {
        double[][] identityMatrix;

        //Review the matrix dimensions

        int lines = matrixA.length;
        int columns = matrixA[0].length;

        //Validate and inverse the matrixA

        if (lines == columns) {
            identityMatrix = new double[lines][columns];

            for (int i = 0; i < lines; i++) {
                identityMatrix[i][i] = 1;
            }

            double multiple;
            boolean invertible = true;

            //PART 1: Cancel the terms below diagonal

            for (int i = 0; i < lines; i++) {
                //Operation for when the pivot is equal to 0
                if (matrixA[i][i] == 0 && invertible == true) {
                    for (int ii = i + 1; ii < lines; ii++) {
                        if (matrixA[ii][i] != 0) {
                            for (int jj = 0; jj < columns; jj++) {
                                matrixA[i][jj] = matrixA[i][jj] + matrixA[ii][jj];
                                identityMatrix[i][jj] = identityMatrix[i][jj] + identityMatrix[ii][jj];
                            }
                        }
                    }

                    if (matrixA[i][i] == 0) {
                        matrixA = null;
                        invertible = false;
                        break;
                    }
                }

                //Operation for when the pivot is different from 0
                if (matrixA[i][i] != 0) {
                    //Operation to turn the pivot on 1
                    if (matrixA[i][i] != 1) {
                        multiple = matrixA[i][i];
                        for (int jj = 0; jj < columns; jj++) {
                            if (matrixA[i][jj] != 0) {
                                matrixA[i][jj] = matrixA[i][jj] / multiple;
                            }
                            if (identityMatrix[i][jj] != 0) {
                                identityMatrix[i][jj] = identityMatrix[i][jj] / multiple;
                            }
                        }
                    }

                    //Operation for when the pivot is 1
                    for (int ii = i + 1; ii < lines; ii++) {
                        if (matrixA[ii][i] != 0) {
                            multiple = matrixA[ii][i];
                            for (int jj = 0; jj < columns; jj++) {
                                matrixA[ii][jj] = matrixA[ii][jj] - multiple * matrixA[i][jj];
                                identityMatrix[ii][jj] = identityMatrix[ii][jj] - multiple * identityMatrix[i][jj];
                            }
                        }
                    }
                }
            }

            //PART 2: Cancel the terms above diagonal

            if (invertible) {
                for (int i = (lines - 1); i >= 0; i--) {
                    if (matrixA[i][i] == 1) {
                        for (int ii = i - 1; ii >= 0; ii--) {
                            if (matrixA[ii][i] != 0) {
                                multiple = matrixA[ii][i];
                                for (int jj = columns - 1; jj >= 0; jj--) {
                                    matrixA[ii][jj] = matrixA[ii][jj] - multiple * matrixA[i][jj];
                                    identityMatrix[ii][jj] = identityMatrix[ii][jj] - multiple * identityMatrix[i][jj];
                                }
                            }
                        }
                    }
                }

                //InverseMatrix assign the value of the inverse matrix
                matrixA = identityMatrix;
            }
        } else {
            matrixA = null;
        }

        return matrixA;
    }
}
