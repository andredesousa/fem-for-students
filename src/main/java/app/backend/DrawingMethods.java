/*
 * Esta classe fornece ou valida as coordenadas dos vértices das figuras geométricas
 * Existem métodos para fornecer as coordenadas dos vértices em função do número de clicks
 * Existem métodos para validar se as coordenadas do rato são compatíveis com cada tarefa
 */

package app.backend;

import static app.calculations.AnalyticGeometry.centroid;
import static app.calculations.AnalyticGeometry.midPoint;
import static java.lang.Math.round;

import app.calculations.AnalyticGeometry;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author André de Sousa
 */
public class DrawingMethods {

    /**
     * Este método fornece as coordenadas de uma linha para o evento do rato
     *
     * @param xPoint
     * @param yPoint
     * @param nClicks
     * @param coordinates
     * @return
     */
    public static int[] mP_LineCoordinates(int xPoint, int yPoint, int nClicks, int[] coordinates) {
        int[] tempLines = coordinates;

        if (nClicks == 1) {
            tempLines[0] = xPoint;
            tempLines[1] = yPoint;
            tempLines[2] = xPoint;
            tempLines[3] = yPoint;
        }
        if (nClicks == 2) {
            tempLines[2] = xPoint;
            tempLines[3] = yPoint;
        }

        return tempLines;
    }

    /**
     * Este método fornece as coordenadas de uma linha para o evento do rato
     *
     * @param xPoint
     * @param yPoint
     * @param nClicks
     * @param coordinates
     * @return
     */
    public static int[] mP_HorizontalLine(int xPoint, int yPoint, int nClicks, int[] coordinates) {
        int[] tempLines = coordinates;

        if (nClicks == 1) {
            tempLines[0] = xPoint;
            tempLines[1] = yPoint;
            tempLines[2] = xPoint;
            tempLines[3] = yPoint;
        }
        if (nClicks == 2) {
            tempLines[2] = xPoint;
        }

        return tempLines;
    }

    /**
     * Este método fornece as coordenadas de uma linha para o evento do rato
     *
     * @param xPoint
     * @param yPoint
     * @param nClicks
     * @param coordinates
     * @return
     */
    public static int[] mM_LineCoordinates(int xPoint, int yPoint, int nClicks, int[] coordinates) {
        int[] tempLines = coordinates;

        if (nClicks == 1) {
            tempLines[2] = xPoint;
            tempLines[3] = yPoint;
        }

        return tempLines;
    }

    /**
     * Este método fornece as coordenadas de uma linha para o evento do rato
     *
     * @param xPoint
     * @param yPoint
     * @param nClicks
     * @param coordinates
     * @return
     */
    public static int[] mM_HorizontalLine(int xPoint, int yPoint, int nClicks, int[] coordinates) {
        int[] tempLines = coordinates;

        if (nClicks == 1) {
            tempLines[2] = xPoint;
        }

        return tempLines;
    }

    /**
     * Este método fornece as coordenadas de um polígono para o evento do rato
     *
     * @param point
     * @param nClicks
     * @param vector
     * @param object
     * @return
     */
    public static int[] mP_PolygonCoordinates(Point point, int nClicks, int[] vector, String object) {
        int[] tempPolygon = vector;
        int xPoint = point.x;
        int yPoint = point.y;

        if ("xTriangle".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[0] = xPoint;
                tempPolygon[1] = xPoint;
                tempPolygon[2] = xPoint;
            }
            if (nClicks == 2) {
                tempPolygon[1] = xPoint;
                tempPolygon[2] = xPoint;
            }
            if (nClicks == 3) {
                tempPolygon[2] = xPoint;
            }
        }
        if ("yTriangle".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[0] = yPoint;
                tempPolygon[1] = yPoint;
                tempPolygon[2] = yPoint;
            }
            if (nClicks == 2) {
                tempPolygon[1] = yPoint;
                tempPolygon[2] = yPoint;
            }
            if (nClicks == 3) {
                tempPolygon[2] = yPoint;
            }
        }
        if ("xRectangle".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[0] = xPoint;
                tempPolygon[1] = xPoint;
                tempPolygon[2] = xPoint;
                tempPolygon[3] = xPoint;
            } else if (nClicks == 2) {
                tempPolygon[1] = xPoint;
                tempPolygon[2] = xPoint;
                tempPolygon[3] = tempPolygon[0];
            } else if (nClicks == 3) {
                tempPolygon[2] = tempPolygon[1];
                tempPolygon[3] = tempPolygon[0];
            }
        }
        if ("yRectangle".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[0] = yPoint;
                tempPolygon[1] = yPoint;
                tempPolygon[2] = yPoint;
                tempPolygon[3] = yPoint;
            } else if (nClicks == 2) {
                tempPolygon[1] = tempPolygon[0];
                tempPolygon[2] = yPoint;
                tempPolygon[3] = yPoint;
            } else if (nClicks == 3) {
                tempPolygon[2] = yPoint;
                tempPolygon[3] = tempPolygon[2];
            }
        }
        if ("xQuadrilateral".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[0] = xPoint;
                tempPolygon[1] = xPoint;
                tempPolygon[2] = xPoint;
                tempPolygon[3] = xPoint;
            }
            if (nClicks == 2) {
                tempPolygon[1] = xPoint;
                tempPolygon[2] = xPoint;
                tempPolygon[3] = xPoint;
            }
            if (nClicks == 3) {
                tempPolygon[2] = xPoint;
                tempPolygon[3] = xPoint;
            }
            if (nClicks == 4) {
                tempPolygon[3] = xPoint;
            }
        }
        if ("yQuadrilateral".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[0] = yPoint;
                tempPolygon[1] = yPoint;
                tempPolygon[2] = yPoint;
                tempPolygon[3] = yPoint;
            }
            if (nClicks == 2) {
                tempPolygon[1] = yPoint;
                tempPolygon[2] = yPoint;
                tempPolygon[3] = yPoint;
            }
            if (nClicks == 3) {
                tempPolygon[2] = yPoint;
                tempPolygon[3] = yPoint;
            }
            if (nClicks == 4) {
                tempPolygon[3] = yPoint;
            }
        }

        return tempPolygon;
    }

    /**
     * Este método fornece as coordenadas de um polígono para o evento do rato
     *
     * @param point
     * @param nClicks
     * @param vector
     * @param object
     * @return
     */
    public static int[] mM_PolygonCoordinates(Point point, int nClicks, int[] vector, String object) {
        int[] tempPolygon = vector;
        int xPoint = point.x;
        int yPoint = point.y;

        if ("xTriangle".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[1] = xPoint;
                tempPolygon[2] = xPoint;
            }
            if (nClicks == 2) {
                tempPolygon[2] = xPoint;
            }
        }
        if ("yTriangle".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[1] = yPoint;
                tempPolygon[2] = yPoint;
            }
            if (nClicks == 2) {
                tempPolygon[2] = yPoint;
            }
        }
        if ("xRectangle".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[1] = xPoint;
                tempPolygon[2] = xPoint;
                tempPolygon[3] = xPoint;
            }
            if (nClicks == 2) {
                tempPolygon[2] = tempPolygon[1];
                tempPolygon[3] = tempPolygon[0];
            }
        }
        if ("yRectangle".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[1] = tempPolygon[0];
                tempPolygon[2] = tempPolygon[0];
                tempPolygon[3] = tempPolygon[0];
            }
            if (nClicks == 2) {
                tempPolygon[2] = yPoint;
                tempPolygon[3] = yPoint;
            }
        }
        if ("xQuadrilateral".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[1] = xPoint;
                tempPolygon[2] = xPoint;
                tempPolygon[3] = xPoint;
            }
            if (nClicks == 2) {
                tempPolygon[2] = xPoint;
                tempPolygon[3] = xPoint;
            }
            if (nClicks == 3) {
                tempPolygon[3] = xPoint;
            }
        }
        if ("yQuadrilateral".equals(object)) {
            if (nClicks == 1) {
                tempPolygon[1] = yPoint;
                tempPolygon[2] = yPoint;
                tempPolygon[3] = yPoint;
            }
            if (nClicks == 2) {
                tempPolygon[2] = yPoint;
                tempPolygon[3] = yPoint;
            }
            if (nClicks == 3) {
                tempPolygon[3] = yPoint;
            }
        }

        return tempPolygon;
    }

    /**
     * Este método fornece as coordenas e dimensões do retângulo de seleção
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static int[] mM_SelectCoordinates(int x1, int y1, int x2, int y2) {
        int[] select = new int[4];
        int width = x2 - x1;
        int height = y2 - y1;

        if (width >= 0 && height >= 0) {
            select[0] = x1;
            select[1] = y1;
            select[2] = width;
            select[3] = height;
        }
        if (width < 0 && height >= 0) {
            select[0] = x2;
            select[1] = y1;
            select[2] = -width;
            select[3] = height;
        }
        if (width >= 0 && height < 0) {
            select[0] = x1;
            select[1] = y2;
            select[2] = width;
            select[3] = -height;
        }
        if (width < 0 && height < 0) {
            select[0] = x2;
            select[1] = y2;
            select[2] = -width;
            select[3] = -height;
        }

        return select;
    }

    /**
     * Este método fornece as coordenadas para mover um objeto
     *
     * @param point
     * @param nClicks
     * @param coordinates
     * @return
     */
    public static int[] mP_MoveCoordinates(Point point, int nClicks, int[] coordinates) {
        int[] tempMove = coordinates;

        if (nClicks == 1) {
            tempMove[0] = point.x;
            tempMove[1] = point.y;
            tempMove[2] = point.x;
            tempMove[3] = point.y;
        }
        if (nClicks == 2) {
            tempMove[2] = point.x;
            tempMove[3] = point.y;
        }

        return tempMove;
    }

    /**
     * Este método fornece as coordenadas para mover um objeto
     *
     * @param point
     * @param nClicks
     * @param coordinates
     * @return
     */
    public static int[] mM_MoveCoordinates(Point point, int nClicks, int[] coordinates) {
        int[] tempMove = coordinates;

        if (nClicks == 1) {
            tempMove[0] = tempMove[2];
            tempMove[1] = tempMove[3];
            tempMove[2] = point.x;
            tempMove[3] = point.y;
        }

        return tempMove;
    }

    /**
     * Este método fornece a cópia de uma lista de nós
     *
     * @param nodes
     * @return
     */
    public static ArrayList<DrawEllipse> copyNode(ArrayList<DrawEllipse> nodes) {
        ArrayList<DrawEllipse> listOfNodes = new ArrayList();

        for (DrawEllipse copyNode : nodes) {
            int width = (int) round(copyNode.drawEllipse.getWidth());
            int height = (int) round(copyNode.drawEllipse.getHeight());
            listOfNodes.add(new DrawEllipse(copyNode.point.x, copyNode.point.y, width, height));
        }

        return listOfNodes;
    }

    /**
     * Este método fornece a cópia de uma lista de linhas
     *
     * @param lines
     * @return
     */
    public static ArrayList<DrawLine> copyLine(ArrayList<DrawLine> lines) {
        ArrayList<DrawLine> listOfLines = new ArrayList();

        for (DrawLine copyLine : lines) {
            int[][] coordinates = copyLine.getCoordinates();
            int x1, y1, x2, y2;
            String shape = copyLine.shape;
            String section = copyLine.section;

            x1 = coordinates[0][0] + 0;
            y1 = coordinates[0][1] + 0;
            x2 = coordinates[1][0] + 0;
            y2 = coordinates[1][1] + 0;

            listOfLines.add(new DrawLine(x1, y1, x2, y2, shape, section, copyLine.selected));
        }

        return listOfLines;
    }

    /**
     * Este método fornece a cópia de um polígono
     *
     * @param polygons
     * @return
     */
    public static ArrayList<DrawPolygon> copyPolygon(ArrayList<DrawPolygon> polygons) {
        ArrayList<DrawPolygon> listOfPolygons = new ArrayList();

        for (DrawPolygon copyPolygon : polygons) {
            int[][] coordinates = copyPolygon.getCoordinates();

            int nPoints = coordinates.length;
            String shape = copyPolygon.shape;
            String section = copyPolygon.section;

            int[] xPoints = new int[nPoints];
            int[] yPoints = new int[nPoints];
            for (int i = 0; i < nPoints; i++) {
                xPoints[i] = coordinates[i][0] + 0;
                yPoints[i] = coordinates[i][1] + 0;
            }

            listOfPolygons.add(new DrawPolygon(xPoints, yPoints, nPoints, shape, section, copyPolygon.selected));
        }

        return listOfPolygons;
    }

    /**
     * Este método fornece a cópia de uma lista de apoios estruturais
     *
     * @param supports
     * @return
     */
    public static ArrayList<DrawSupports> copySupport(ArrayList<DrawSupports> supports) {
        ArrayList<DrawSupports> listOfSupports = new ArrayList();

        for (DrawSupports copySupport : supports) {
            int[][] coordinates = copySupport.getCoordinates();

            listOfSupports.add(new DrawSupports(coordinates[0][0], coordinates[0][1], copySupport.support, copySupport.selected));
        }

        return listOfSupports;
    }

    /**
     * Este método fornece a cópia de uma lista de apoios elásticos
     *
     * @param supports
     * @return
     */
    public static ArrayList<DrawElasticSupports> copyElasticSupport(ArrayList<DrawElasticSupports> supports) {
        ArrayList<DrawElasticSupports> listOfSupports = new ArrayList();

        for (DrawElasticSupports copyElasticSupport : supports) {
            int[][] coordinates = copyElasticSupport.getCoordinates();

            listOfSupports.add(
                new DrawElasticSupports(
                    copyElasticSupport.name,
                    coordinates[0][0],
                    coordinates[0][1],
                    copyElasticSupport.stiffness,
                    copyElasticSupport.selected
                )
            );
        }

        return listOfSupports;
    }

    /**
     * Este método fornece a cópia de uma lista de assentamentos de apoio
     *
     * @param settlements
     * @return
     */
    public static ArrayList<DrawSettlements> copySettlement(ArrayList<DrawSettlements> settlements) {
        ArrayList<DrawSettlements> listOfSettlements = new ArrayList();

        for (DrawSettlements copySettlement : settlements) {
            int[][] coordinates = copySettlement.getCoordinates();

            listOfSettlements.add(
                new DrawSettlements(
                    copySettlement.name,
                    coordinates[0][0],
                    coordinates[0][1],
                    copySettlement.displacements,
                    copySettlement.selected
                )
            );
        }

        return listOfSettlements;
    }

    /**
     * Este método fornece a cópia de uma lista de cargas estruturais
     *
     * @param loads
     * @return
     */
    public static ArrayList<DrawLoads> copyLoad(ArrayList<DrawLoads> loads) {
        ArrayList<DrawLoads> listOfLoad = new ArrayList();

        for (DrawLoads copyLoad : loads) {
            DrawLoads pasteLoad;

            int[][] coordinates = copyLoad.getCoordinates();
            String[] loadDescription = copyLoad.getDescription();
            String loadType = copyLoad.loadType;
            switch (loadType) {
                case "Concentrated Load":
                    pasteLoad = new DrawLoads(coordinates[0][0], coordinates[0][1], loadDescription);
                    pasteLoad.select(copyLoad.selected);
                    listOfLoad.add(pasteLoad);
                    break;
                case "Bending Moment":
                    pasteLoad = new DrawLoads(coordinates[0][0], coordinates[0][1], loadDescription);
                    pasteLoad.select(copyLoad.selected);
                    listOfLoad.add(pasteLoad);
                    break;
                case "Distributed Load":
                    pasteLoad = new DrawLoads(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1], loadDescription);
                    pasteLoad.select(copyLoad.selected);
                    listOfLoad.add(pasteLoad);
                    break;
                case "Axial Load":
                    pasteLoad = new DrawLoads(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1], loadDescription);
                    pasteLoad.select(copyLoad.selected);
                    listOfLoad.add(pasteLoad);
                    break;
                case "Thermal Load":
                    pasteLoad = new DrawLoads(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1], loadDescription);
                    pasteLoad.select(copyLoad.selected);
                    listOfLoad.add(pasteLoad);
                    break;
                case "Planar Load":
                    int[] xPoints = new int[coordinates.length];
                    int[] yPoints = new int[coordinates.length];

                    for (int i = 0; i < coordinates.length; i++) {
                        xPoints[i] = coordinates[i][0];
                        yPoints[i] = coordinates[i][1];
                    }

                    DrawPolygon polygon = new DrawPolygon(xPoints, yPoints, coordinates.length);

                    pasteLoad = new DrawLoads(polygon, loadDescription);
                    pasteLoad.select(copyLoad.selected);
                    listOfLoad.add(pasteLoad);
                    break;
            }
        }

        return listOfLoad;
    }

    /**
     * Este método fornece a cópia de uma lista de cargas estruturais
     *
     * @param dimensionLines
     * @return
     */
    public static ArrayList<DrawDimensionLine> copyDimensionLine(ArrayList<DrawDimensionLine> dimensionLines) {
        ArrayList<DrawDimensionLine> listOfDimensionLines = new ArrayList();

        for (DrawDimensionLine copyDimensionLine : dimensionLines) {
            int[][] coordinates = copyDimensionLine.getCoordinates();

            listOfDimensionLines.add(
                new DrawDimensionLine(
                    coordinates[0][0],
                    coordinates[0][1],
                    coordinates[1][0],
                    coordinates[1][1],
                    copyDimensionLine.selected
                )
            );
        }

        return listOfDimensionLines;
    }

    /**
     * Este método fornece a linha que vai ser colada
     *
     * @param copyLine
     * @return
     */
    public static DrawLine pasteLine(DrawLine copyLine) {
        DrawLine pasteLine;

        int[][] coordinates = copyLine.getCoordinates();
        int horizontal, vertical, x1, y1, x2, y2;
        String shape = copyLine.shape;

        horizontal = 150;
        vertical = 50;
        x1 = coordinates[0][0] + horizontal;
        y1 = coordinates[0][1] + vertical;
        x2 = coordinates[1][0] + horizontal;
        y2 = coordinates[1][1] + vertical;

        pasteLine = new DrawLine(x1, y1, x2, y2, shape);
        pasteLine.select(true);

        return pasteLine;
    }

    /**
     * Este método fornece o polígono que vai ser colado
     *
     * @param copyPolygon
     * @return
     */
    public static DrawPolygon pastePolygon(DrawPolygon copyPolygon) {
        DrawPolygon pastePolygon;

        int[][] coordinates = copyPolygon.getCoordinates();

        int horizontal = 150;
        int vertical = 50;
        int nPoints = coordinates.length;
        String shape = copyPolygon.shape;

        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];
        for (int i = 0; i < nPoints; i++) {
            xPoints[i] = coordinates[i][0] + horizontal;
            yPoints[i] = coordinates[i][1] + vertical;
        }

        pastePolygon = new DrawPolygon(xPoints, yPoints, nPoints, shape);
        pastePolygon.select(true);

        return pastePolygon;
    }

    /**
     * Este método fornece o apoio estrutural que vai ser colado
     *
     * @param copySupport
     * @return
     */
    public static DrawSupports pasteSupport(DrawSupports copySupport) {
        DrawSupports pasteSupport;

        int[][] coordinates = copySupport.getCoordinates();
        int horizontal = 150;
        int vertical = 50;

        pasteSupport = new DrawSupports(coordinates[0][0], coordinates[0][1], copySupport.support);
        pasteSupport.moveSupport(horizontal, vertical);
        pasteSupport.select(true);

        return pasteSupport;
    }

    /**
     * Este método fornece o apoio elástico que vai ser colado
     *
     * @param copySupport
     * @return
     */
    public static DrawElasticSupports pasteElasticSupport(DrawElasticSupports copySupport) {
        DrawElasticSupports pasteSupport;

        int[][] coordinates = copySupport.getCoordinates();
        int horizontal = 150;
        int vertical = 50;

        pasteSupport = new DrawElasticSupports(copySupport.name, coordinates[0][0], coordinates[0][1], copySupport.stiffness);
        pasteSupport.moveSupport(horizontal, vertical);
        pasteSupport.select(true);

        return pasteSupport;
    }

    /**
     * Este método fornece o assentamento de apoio que vai ser colado
     *
     * @param copySettlement
     * @return
     */
    public static DrawSettlements pasteSettlement(DrawSettlements copySettlement) {
        DrawSettlements pasteSettlement;

        int[][] coordinates = copySettlement.getCoordinates();
        int horizontal = 150;
        int vertical = 50;

        pasteSettlement = new DrawSettlements(copySettlement.name, coordinates[0][0], coordinates[0][1], copySettlement.displacements);
        pasteSettlement.moveSettlement(horizontal, vertical);
        pasteSettlement.select(true);

        return pasteSettlement;
    }

    /**
     * Este método fornece a carga estrutural que vai ser colada
     *
     * @param copyLoad
     * @return
     */
    public static DrawLoads pasteLoad(DrawLoads copyLoad) {
        DrawLoads pasteLoad;

        int[][] coordinates = copyLoad.getCoordinates();
        int horizontal = 150;
        int vertical = 50;

        String[] loadDescription = copyLoad.getDescription();
        String loadType = copyLoad.loadType;
        switch (loadType) {
            case "Concentrated Load":
                pasteLoad = new DrawLoads(coordinates[0][0], coordinates[0][1], loadDescription);
                pasteLoad.moveLoad(horizontal, vertical);
                pasteLoad.select(true);
                break;
            case "Bending Moment":
                pasteLoad = new DrawLoads(coordinates[0][0], coordinates[0][1], loadDescription);
                pasteLoad.moveLoad(horizontal, vertical);
                pasteLoad.select(true);
                break;
            case "Distributed Load":
                pasteLoad = new DrawLoads(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1], loadDescription);
                pasteLoad.moveLoad(horizontal, vertical);
                pasteLoad.select(true);
                break;
            case "Axial Load":
                pasteLoad = new DrawLoads(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1], loadDescription);
                pasteLoad.moveLoad(horizontal, vertical);
                pasteLoad.select(true);
                break;
            case "Thermal Load":
                pasteLoad = new DrawLoads(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1], loadDescription);
                pasteLoad.moveLoad(horizontal, vertical);
                pasteLoad.select(true);
                break;
            case "Planar Load":
                int[] xPoints = new int[coordinates.length];
                int[] yPoints = new int[coordinates.length];

                for (int i = 0; i < coordinates.length; i++) {
                    xPoints[i] = coordinates[i][0];
                    yPoints[i] = coordinates[i][1];
                }

                DrawPolygon polygon = new DrawPolygon(xPoints, yPoints, coordinates.length);

                pasteLoad = new DrawLoads(polygon, loadDescription);
                pasteLoad.moveLoad(horizontal, vertical);
                pasteLoad.select(true);
                break;
            default:
                pasteLoad = null;
                break;
        }

        return pasteLoad;
    }

    /**
     * Este método fornece a linha de cotagem que vai ser colada
     *
     * @param copyDimensionLine
     * @return
     */
    public static DrawDimensionLine pasteDimensionLine(DrawDimensionLine copyDimensionLine) {
        DrawDimensionLine pasteDimensionLine;

        int[][] coordinates = copyDimensionLine.getCoordinates();
        int horizontal = 150;
        int vertical = 50;

        pasteDimensionLine = new DrawDimensionLine(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1]);
        pasteDimensionLine.moveLine(horizontal, vertical);
        pasteDimensionLine.select(true);

        return pasteDimensionLine;
    }

    /**
     * Este método controla o número de clicks do rato
     *
     * @param nClicks
     * @param object
     * @return
     */
    public static int clicks(int nClicks, String object) {
        if ("Line".equals(object)) {
            if (nClicks >= 2) {
                nClicks = 0;
            }
        }
        if ("Triangle".equals(object)) {
            if (nClicks >= 3) {
                nClicks = 0;
            }
        }
        if ("Rectangle".equals(object)) {
            if (nClicks >= 3) {
                nClicks = 0;
            }
        }
        if ("Quadrilateral".equals(object)) {
            if (nClicks >= 4) {
                nClicks = 0;
            }
        }
        if ("DimensionLine".equals(object)) {
            if (nClicks >= 2) {
                nClicks = 0;
            }
        }
        if ("Move".equals(object)) {
            if (nClicks >= 2) {
                nClicks = 0;
            }
        }
        if ("UnifDistLoad".equals(object)) {
            if (nClicks >= 2) {
                nClicks = 0;
            }
        }
        if ("DistAxialLoad".equals(object)) {
            if (nClicks >= 2) {
                nClicks = 0;
            }
        }
        if ("ThermalLoad".equals(object)) {
            if (nClicks >= 2) {
                nClicks = 0;
            }
        }

        return nClicks;
    }

    /**
     * Este método valida a existência de coordenadas coincidentes numa linha
     *
     * @param vector
     * @param nClicks
     * @return
     */
    public static boolean validateLine(int[] vector, int nClicks) {
        boolean result = true;

        if (nClicks == 2) {
            if (vector[0] == vector[2]) {
                if (vector[1] == vector[3]) {
                    result = false;
                }
            }
        }

        return result;
    }

    /**
     * Este método valida a existência de coordenadas coincidentes num polígono
     *
     * @param xVector
     * @param yVector
     * @param nClicks
     * @return
     */
    public static boolean validatePolygon(int[] xVector, int[] yVector, int nClicks) {
        boolean result = true;

        if (xVector.length == yVector.length) {
            int k = 0;
            while (k < nClicks - 1) {
                for (int i = k + 1; i < nClicks; i++) {
                    if (xVector[k] == xVector[i]) {
                        if (yVector[k] == yVector[i]) {
                            result = false;
                        }
                    }
                }
                k++;
            }
        }

        return result;
    }

    /**
     * Este método valida a forma de um quadrilátero
     *
     * @param xVector
     * @param yVector
     * @return
     */
    public static boolean validateQuadrilateral(int[] xVector, int[] yVector) {
        boolean result;

        //Instruções para verificar se o polígono é regular
        Line2D.Double lineA, lineB;
        lineA = new Line2D.Double(xVector[0], yVector[0], xVector[2], yVector[2]);
        lineB = new Line2D.Double(xVector[1], yVector[1], xVector[3], yVector[3]);

        Point2D.Double intersection = AnalyticGeometry.intersectionPoint(lineA, lineB);
        Point point = new Point((int) round(intersection.x), (int) round(intersection.y));

        Polygon polygon = new Polygon(xVector, yVector, 4);
        result = polygon.contains(point);

        //Instruções para verificar outros casos relativos à forma do polígono
        if (result == true) {
            double deltaX, deltaY, angleA, angleB, angleC, angleD;

            //Cálculo da inclinação da reta que une o vértice 1 ao 4
            deltaX = (xVector[3] - xVector[0]);
            deltaY = (yVector[3] - yVector[0]);
            angleA = Math.abs(Math.atan(deltaY / deltaX));

            //Cálculo da inclinação da reta que une o vértice 1 ao 2
            deltaX = (xVector[1] - xVector[0]);
            deltaY = (yVector[1] - yVector[0]);
            angleB = Math.abs(Math.atan(deltaY / deltaX));

            //Cálculo da inclinação da reta que une o vértice 2 ao 3
            deltaX = (xVector[2] - xVector[1]);
            deltaY = (yVector[2] - yVector[1]);
            angleC = Math.abs(Math.atan(deltaY / deltaX));

            //Cálculo da inclinação da reta que une o vértice 3 ao 4
            deltaX = (xVector[3] - xVector[2]);
            deltaY = (yVector[3] - yVector[2]);
            angleD = Math.abs(Math.atan(deltaY / deltaX));

            if (angleA == (Math.PI / 4) && angleD == (Math.PI / 4)) {
                result = false;
            }
            if (angleA == angleB || angleC == angleD) {
                result = false;
            }
        }

        return result;
    }

    /**
     * Este método valida as coordenadas para cargas lineares distribuídas
     *
     * @param loadCoordinates
     * @param shapeCoordinates
     * @return
     */
    public static boolean validateLoad(int[][] loadCoordinates, int[][] shapeCoordinates) {
        boolean valid = false;

        //Verificação se a carga pertence ao mesmo elemento finito
        int counter = 0;

        for (int[] shapeCoordinate : shapeCoordinates) {
            if (loadCoordinates[0][0] == shapeCoordinate[0]) {
                if (loadCoordinates[0][1] == shapeCoordinate[1]) {
                    counter++;
                    break;
                }
            }
        }

        for (int[] shapeCoordinate : shapeCoordinates) {
            if (loadCoordinates[1][0] == shapeCoordinate[0]) {
                if (loadCoordinates[1][1] == shapeCoordinate[1]) {
                    counter++;
                    break;
                }
            }
        }

        if (counter == 2) {
            valid = true;
        }

        //Verificação se a carga cumpre os requesitos de distribuição dos nós
        if (valid) {
            int length = shapeCoordinates.length;

            //Verificação somente para triângulos, retângulos e quadriláteros
            if (length > 2) {
                int index = -1;

                for (int i = 0; i < shapeCoordinates.length; i++) {
                    if (loadCoordinates[0][0] == shapeCoordinates[i][0]) {
                        if (loadCoordinates[0][1] == shapeCoordinates[i][1]) {
                            index = i;
                            break;
                        }
                    }
                }

                //Posição do último elemento da matriz de coordenadas da figura
                int position = shapeCoordinates.length - 1;

                //Instruções para quando o primeiro ponto está na primeira posição
                if (index == 0) {
                    counter = 0;

                    if (loadCoordinates[1][0] == shapeCoordinates[1][0] && loadCoordinates[1][1] == shapeCoordinates[1][1]) {
                        counter++;
                    }
                    if (loadCoordinates[1][0] == shapeCoordinates[position][0] && loadCoordinates[1][1] == shapeCoordinates[position][1]) {
                        counter++;
                    }

                    if (counter == 0) {
                        valid = false;
                    }
                }

                //Instruções para quando o primeiro ponto está na última posição
                if (index == position) {
                    counter = 0;

                    if (loadCoordinates[1][0] == shapeCoordinates[0][0] && loadCoordinates[1][1] == shapeCoordinates[0][1]) {
                        counter++;
                    }
                    if (
                        loadCoordinates[1][0] == shapeCoordinates[position - 1][0] &&
                        loadCoordinates[1][1] == shapeCoordinates[position - 1][1]
                    ) {
                        counter++;
                    }

                    if (counter == 0) {
                        valid = false;
                    }
                }

                //Instruções para quando o primeiro ponto está numa posição intermédia
                if (index > 0 && index < position) {
                    counter = 0;

                    if (
                        loadCoordinates[1][0] == shapeCoordinates[index - 1][0] && loadCoordinates[1][1] == shapeCoordinates[index - 1][1]
                    ) {
                        counter++;
                    }
                    if (
                        loadCoordinates[1][0] == shapeCoordinates[index + 1][0] && loadCoordinates[1][1] == shapeCoordinates[index + 1][1]
                    ) {
                        counter++;
                    }

                    if (counter == 0) {
                        valid = false;
                    }
                }
            }
        }

        return valid;
    }

    /**
     * Método para verificar a possibilidade de atualizar o valor do assentamento
     *
     * @param type
     * @param supportName
     * @param displacements
     * @return
     */
    public static boolean validateSettlements(String type, String supportName, double[] displacements) {
        boolean result = true;

        if ("1D".equals(type) || "2D".equals(type)) {
            if (displacements[0] != 0.0) {
                if (!"Horizontal Support".equals(supportName) && !"Pinned Support".equals(supportName)) {
                    result = false;
                }
            }

            if (displacements[1] != 0.0) {
                if (!"Vertical Support".equals(supportName) && !"Pinned Support".equals(supportName)) {
                    result = false;
                }
            }

            if (displacements[0] != 0.0 && displacements[1] != 0.0) {
                if (!"Pinned Support".equals(supportName)) {
                    result = false;
                }
            }
        }

        if ("Beams".equals(type)) {
            if (displacements[2] != 0.0) {
                if (!"Horizontal Slider".equals(supportName)) {
                    result = false;
                }
            }

            if (displacements[1] != 0.0 && displacements[2] != 0.0) {
                if (!"Horizontal Slider".equals(supportName)) {
                    result = false;
                }
            }
        }

        if ("Frames".equals(type)) {
            if (displacements[0] != 0.0) {
                if ("Vertical Support".equals(supportName) || "Horizontal Slider".equals(supportName)) {
                    result = false;
                }
            }

            if (displacements[1] != 0.0) {
                if ("Horizontal Support".equals(supportName) || "Vertical Slider".equals(supportName)) {
                    result = false;
                }
            }

            if (displacements[2] != 0.0) {
                if (
                    "Horizontal Support".equals(supportName) ||
                    "Vertical Support".equals(supportName) ||
                    "Pinned Support".equals(supportName)
                ) {
                    result = false;
                }
            }
        }

        return result;
    }

    /**
     * Método de refinamento que retorna uma lista de linhas
     *
     * @param line
     * @return
     */
    public static ArrayList<DrawLine> meshRefinementLines(DrawLine line) {
        ArrayList<DrawLine> listOfLines = new ArrayList();
        String shape = line.shape;
        String section = line.section;
        int[][] coordinates = line.getCoordinates();

        int x1, y1, x2, y2, x3, y3;

        x1 = coordinates[0][0];
        y1 = coordinates[0][1];
        x3 = coordinates[1][0];
        y3 = coordinates[1][1];

        Point2D.Double point = midPoint(x1, y1, x3, y3);
        x2 = (int) (round(point.x));
        y2 = (int) (round(point.y));

        if ("Line".equals(shape)) {
            listOfLines.add(new DrawLine(x1, y1, x2, y2, shape, section, line.selected));
            listOfLines.add(new DrawLine(x2, y2, x3, y3, shape, section, line.selected));
        }

        return listOfLines;
    }

    /**
     * Método de refinamento que retorna uma lista de polígonos
     *
     * @param triangle
     * @return
     */
    public static ArrayList<DrawPolygon> meshRefinementTriangles(DrawPolygon triangle) {
        ArrayList<DrawPolygon> listOfPolygons = new ArrayList();
        String shape = triangle.shape;
        String section = triangle.section;

        if ("Triangle".equals(shape)) {
            int[][] coordinates = triangle.getCoordinates();
            boolean selected = triangle.selected;

            int[] xPoints = new int[coordinates.length];
            int[] yPoints = new int[coordinates.length];

            for (int i = 0; i < coordinates.length; i++) {
                xPoints[i] = coordinates[i][0];
                yPoints[i] = coordinates[i][1];
            }

            int x1, x2, x3, x4, x5, x6;
            int y1, y2, y3, y4, y5, y6;

            x1 = xPoints[0];
            y1 = yPoints[0];
            x3 = xPoints[1];
            y3 = yPoints[1];
            x5 = xPoints[2];
            y5 = yPoints[2];

            Point2D.Double pointA = midPoint(x1, y1, x3, y3);
            Point2D.Double pointB = midPoint(x3, y3, x5, y5);
            Point2D.Double pointC = midPoint(x1, y1, x5, y5);

            x2 = (int) (round(pointA.x));
            y2 = (int) (round(pointA.y));
            x4 = (int) (round(pointB.x));
            y4 = (int) (round(pointB.y));
            x6 = (int) (round(pointC.x));
            y6 = (int) (round(pointC.y));

            int[] xPointsA = { x1, x2, x6 };
            int[] yPointsA = { y1, y2, y6 };
            int[] xPointsB = { x2, x3, x4 };
            int[] yPointsB = { y2, y3, y4 };
            int[] xPointsC = { x6, x4, x5 };
            int[] yPointsC = { y6, y4, y5 };
            int[] xPointsD = { x2, x4, x6 };
            int[] yPointsD = { y2, y4, y6 };

            listOfPolygons.add(new DrawPolygon(xPointsA, yPointsA, 3, shape, section, selected));
            listOfPolygons.add(new DrawPolygon(xPointsB, yPointsB, 3, shape, section, selected));
            listOfPolygons.add(new DrawPolygon(xPointsC, yPointsC, 3, shape, section, selected));
            listOfPolygons.add(new DrawPolygon(xPointsD, yPointsD, 3, shape, section, selected));
        }

        return listOfPolygons;
    }

    /**
     * Método de refinamento que retorna uma lista de polígonos
     *
     * @param quadrilateral
     * @return
     */
    public static ArrayList<DrawPolygon> meshRefinementQuadrilaterals(DrawPolygon quadrilateral) {
        ArrayList<DrawPolygon> listOfPolygons = new ArrayList();
        String shape = quadrilateral.shape;
        String section = quadrilateral.section;

        if ("Rectangle".equals(shape) || "Quadrilateral".equals(shape)) {
            int[][] coordinates = quadrilateral.getCoordinates();
            boolean selected = quadrilateral.selected;

            int[] xPoints = new int[coordinates.length];
            int[] yPoints = new int[coordinates.length];

            for (int i = 0; i < coordinates.length; i++) {
                xPoints[i] = coordinates[i][0];
                yPoints[i] = coordinates[i][1];
            }

            int x1, x2, x3, x4, x5, x6, x7, x8, x9;
            int y1, y2, y3, y4, y5, y6, y7, y8, y9;

            x1 = xPoints[0];
            y1 = yPoints[0];
            x3 = xPoints[1];
            y3 = yPoints[1];
            x5 = xPoints[2];
            y5 = yPoints[2];
            x7 = xPoints[3];
            y7 = yPoints[3];

            Point2D.Double pointA = midPoint(x1, y1, x3, y3);
            Point2D.Double pointB = midPoint(x3, y3, x5, y5);
            Point2D.Double pointC = midPoint(x5, y5, x7, y7);
            Point2D.Double pointD = midPoint(x1, y1, x7, y7);

            x2 = (int) (round(pointA.x));
            y2 = (int) (round(pointA.y));
            x4 = (int) (round(pointB.x));
            y4 = (int) (round(pointB.y));
            x6 = (int) (round(pointC.x));
            y6 = (int) (round(pointC.y));
            x8 = (int) (round(pointD.x));
            y8 = (int) (round(pointD.y));

            Point2D.Double centroid = centroid(xPoints, yPoints, shape);
            x9 = (int) (round(centroid.x));
            y9 = (int) (round(centroid.y));

            int[] xPointsA = { x1, x2, x9, x8 };
            int[] yPointsA = { y1, y2, y9, y8 };
            int[] xPointsB = { x2, x3, x4, x9 };
            int[] yPointsB = { y2, y3, y4, y9 };
            int[] xPointsC = { x9, x4, x5, x6 };
            int[] yPointsC = { y9, y4, y5, y6 };
            int[] xPointsD = { x8, x9, x6, x7 };
            int[] yPointsD = { y8, y9, y6, y7 };

            listOfPolygons.add(new DrawPolygon(xPointsA, yPointsA, 4, shape, section, selected));
            listOfPolygons.add(new DrawPolygon(xPointsB, yPointsB, 4, shape, section, selected));
            listOfPolygons.add(new DrawPolygon(xPointsC, yPointsC, 4, shape, section, selected));
            listOfPolygons.add(new DrawPolygon(xPointsD, yPointsD, 4, shape, section, selected));
        }

        return listOfPolygons;
    }

    /**
     * Método de refinamento que retorna uma lista de cargas estruturais
     *
     * @param load
     * @return
     */
    public static ArrayList<DrawLoads> meshRefinementLinearLoads(DrawLoads load) {
        ArrayList<DrawLoads> listOfLoads = new ArrayList();

        if ("Distributed Load".equals(load.loadType) || "Axial Load".equals(load.loadType) || "Thermal Load".equals(load.loadType)) {
            int[][] coordinates = load.getCoordinates();
            int x1, y1, x2, y2, x3, y3;

            x1 = coordinates[0][0];
            y1 = coordinates[0][1];
            x3 = coordinates[1][0];
            y3 = coordinates[1][1];

            Point2D.Double point = midPoint(x1, y1, x3, y3);
            x2 = (int) (round(point.x));
            y2 = (int) (round(point.y));

            listOfLoads.add(new DrawLoads(x1, y1, x2, y2, load.getDescription()));
            listOfLoads.add(new DrawLoads(x2, y2, x3, y3, load.getDescription()));
        }

        return listOfLoads;
    }

    /**
     * Método de refinamento que retorna uma lista de cargas estruturais
     *
     * @param load
     * @return
     */
    public static ArrayList<DrawLoads> meshRefTriangleLoads(DrawLoads load) {
        ArrayList<DrawLoads> listOfLoads = new ArrayList();

        int[][] coordinates = load.getCoordinates();
        if ("Planar Load".equals(load.loadType) && coordinates.length == 3) {
            int x1, x2, x3, x4, x5, x6;
            int y1, y2, y3, y4, y5, y6;

            x1 = coordinates[0][0];
            y1 = coordinates[0][1];
            x3 = coordinates[1][0];
            y3 = coordinates[1][1];
            x5 = coordinates[2][0];
            y5 = coordinates[2][1];

            Point2D.Double pointA = midPoint(x1, y1, x3, y3);
            Point2D.Double pointB = midPoint(x3, y3, x5, y5);
            Point2D.Double pointC = midPoint(x1, y1, x5, y5);

            x2 = (int) (round(pointA.x));
            y2 = (int) (round(pointA.y));
            x4 = (int) (round(pointB.x));
            y4 = (int) (round(pointB.y));
            x6 = (int) (round(pointC.x));
            y6 = (int) (round(pointC.y));

            int[] xPointsA = { x1, x2, x6 };
            int[] yPointsA = { y1, y2, y6 };
            int[] xPointsB = { x2, x3, x4 };
            int[] yPointsB = { y2, y3, y4 };
            int[] xPointsC = { x6, x4, x5 };
            int[] yPointsC = { y6, y4, y5 };
            int[] xPointsD = { x2, x4, x6 };
            int[] yPointsD = { y2, y4, y6 };

            DrawPolygon polygonA = new DrawPolygon(xPointsA, yPointsA, 3, "Triangle");
            DrawPolygon polygonB = new DrawPolygon(xPointsB, yPointsB, 3, "Triangle");
            DrawPolygon polygonC = new DrawPolygon(xPointsC, yPointsC, 3, "Triangle");
            DrawPolygon polygonD = new DrawPolygon(xPointsD, yPointsD, 3, "Triangle");

            listOfLoads.add(new DrawLoads(polygonA, load.getDescription()));
            listOfLoads.add(new DrawLoads(polygonB, load.getDescription()));
            listOfLoads.add(new DrawLoads(polygonC, load.getDescription()));
            listOfLoads.add(new DrawLoads(polygonD, load.getDescription()));
        }

        return listOfLoads;
    }

    /**
     * Método de refinamento que retorna uma lista de cargas estruturais
     *
     * @param load
     * @return
     */
    public static ArrayList<DrawLoads> meshRefQuadrilateralLoads(DrawLoads load) {
        ArrayList<DrawLoads> listOfLoads = new ArrayList();

        int[][] coordinates = load.getCoordinates();
        if ("Planar Load".equals(load.loadType) && coordinates.length == 4) {
            int x1, x2, x3, x4, x5, x6, x7, x8, x9;
            int y1, y2, y3, y4, y5, y6, y7, y8, y9;

            x1 = coordinates[0][0];
            y1 = coordinates[0][1];
            x3 = coordinates[1][0];
            y3 = coordinates[1][1];
            x5 = coordinates[2][0];
            y5 = coordinates[2][1];
            x7 = coordinates[3][0];
            y7 = coordinates[3][1];

            Point2D.Double pointA = midPoint(x1, y1, x3, y3);
            Point2D.Double pointB = midPoint(x3, y3, x5, y5);
            Point2D.Double pointC = midPoint(x5, y5, x7, y7);
            Point2D.Double pointD = midPoint(x1, y1, x7, y7);

            x2 = (int) (round(pointA.x));
            y2 = (int) (round(pointA.y));
            x4 = (int) (round(pointB.x));
            y4 = (int) (round(pointB.y));
            x6 = (int) (round(pointC.x));
            y6 = (int) (round(pointC.y));
            x8 = (int) (round(pointD.x));
            y8 = (int) (round(pointD.y));

            int[] xPoints = new int[coordinates.length];
            int[] yPoints = new int[coordinates.length];

            for (int i = 0; i < coordinates.length; i++) {
                xPoints[i] = coordinates[i][0];
                yPoints[i] = coordinates[i][1];
            }

            Point2D.Double centroid = centroid(xPoints, yPoints, "Quadrilateral");
            x9 = (int) (round(centroid.x));
            y9 = (int) (round(centroid.y));

            int[] xPointsA = { x1, x2, x9, x8 };
            int[] yPointsA = { y1, y2, y9, y8 };
            int[] xPointsB = { x2, x3, x4, x9 };
            int[] yPointsB = { y2, y3, y4, y9 };
            int[] xPointsC = { x9, x4, x5, x6 };
            int[] yPointsC = { y9, y4, y5, y6 };
            int[] xPointsD = { x8, x9, x6, x7 };
            int[] yPointsD = { y8, y9, y6, y7 };

            DrawPolygon polygonA = new DrawPolygon(xPointsA, yPointsA, 4, "Quadrilateral");
            DrawPolygon polygonB = new DrawPolygon(xPointsB, yPointsB, 4, "Quadrilateral");
            DrawPolygon polygonC = new DrawPolygon(xPointsC, yPointsC, 4, "Quadrilateral");
            DrawPolygon polygonD = new DrawPolygon(xPointsD, yPointsD, 4, "Quadrilateral");

            listOfLoads.add(new DrawLoads(polygonA, load.getDescription()));
            listOfLoads.add(new DrawLoads(polygonB, load.getDescription()));
            listOfLoads.add(new DrawLoads(polygonC, load.getDescription()));
            listOfLoads.add(new DrawLoads(polygonD, load.getDescription()));
        }

        return listOfLoads;
    }

    /**
     * Método que devolve uma lista de pontos pertencentes a um quadrilátero
     *
     * @param polygon
     * @return
     */
    public static ArrayList<Point.Float> createPointsForNodes(DrawPolygon polygon) {
        ArrayList<Point.Float> listOfPoints = new ArrayList();

        //Construção da lista de polígonos através do refinamento do polígono recebido
        ArrayList<DrawPolygon> listOfPolygons = new ArrayList();
        listOfPolygons.addAll(meshRefinementQuadrilaterals(polygon));

        //Construção da lista de pontos a partir das coordenadas dos polígonos
        for (DrawPolygon drawPolygon : listOfPolygons) {
            ArrayList<Point.Float> pointsOfPolygon = new ArrayList();

            String shape = drawPolygon.shape;
            int[][] coordinates = drawPolygon.getCoordinates();
            int[] xPoints = new int[coordinates.length];
            int[] yPoints = new int[coordinates.length];

            for (int i = 0; i < coordinates.length; i++) {
                xPoints[i] = coordinates[i][0];
                yPoints[i] = coordinates[i][1];
            }

            int x1, x2, x3, x4, x5, x6, x7, x8, x9;
            int y1, y2, y3, y4, y5, y6, y7, y8, y9;

            x1 = xPoints[0];
            y1 = yPoints[0];
            x3 = xPoints[1];
            y3 = yPoints[1];
            x5 = xPoints[2];
            y5 = yPoints[2];
            x7 = xPoints[3];
            y7 = yPoints[3];

            Point2D.Double pointA = midPoint(x1, y1, x3, y3);
            Point2D.Double pointB = midPoint(x3, y3, x5, y5);
            Point2D.Double pointC = midPoint(x5, y5, x7, y7);
            Point2D.Double pointD = midPoint(x1, y1, x7, y7);

            x2 = (int) (round(pointA.x));
            y2 = (int) (round(pointA.y));
            x4 = (int) (round(pointB.x));
            y4 = (int) (round(pointB.y));
            x6 = (int) (round(pointC.x));
            y6 = (int) (round(pointC.y));
            x8 = (int) (round(pointD.x));
            y8 = (int) (round(pointD.y));

            Point2D.Double centroid = centroid(xPoints, yPoints, shape);
            x9 = (int) (round(centroid.x));
            y9 = (int) (round(centroid.y));

            pointsOfPolygon.add(new Point.Float(x1, y1));
            pointsOfPolygon.add(new Point.Float(x2, y2));
            pointsOfPolygon.add(new Point.Float(x3, y3));
            pointsOfPolygon.add(new Point.Float(x4, y4));
            pointsOfPolygon.add(new Point.Float(x5, y5));
            pointsOfPolygon.add(new Point.Float(x6, y6));
            pointsOfPolygon.add(new Point.Float(x7, y7));
            pointsOfPolygon.add(new Point.Float(x8, y8));
            pointsOfPolygon.add(new Point.Float(x9, y9));

            if (listOfPoints.isEmpty()) {
                listOfPoints.addAll(pointsOfPolygon);
            } else {
                for (Point2D.Float pointOfPolygon : pointsOfPolygon) {
                    boolean addPoint = true;

                    for (Point2D.Float point : listOfPoints) {
                        if (point == pointOfPolygon) {
                            addPoint = false;
                        }
                    }

                    if (addPoint) {
                        listOfPoints.add(pointOfPolygon);
                    }
                }
            }
        }

        return listOfPoints;
    }
}
