/*
 * Esta classe cria e desenha cargas nos elementos finitos
 * A classe permite desenhar cargas concentradas e cargas distribuídas
 * As cargas são desenhadas a partir das coordenadas dos nós
 */

package app.backend;

import static app.calculations.FormatResults.decimalFormat;
import static java.lang.Math.PI;
import static java.lang.Math.round;

import app.calculations.AnalyticGeometry;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author André de Sousa
 */
public class DrawLoads implements Serializable {

    public String loadType;
    public String loadName;
    public String sign;
    public String horizontalSign;
    public String verticalSign;
    public double Tzero;
    public double Ttop;
    public double Tbot;
    public double value;
    public double horizontalValue;
    public double verticalValue;
    public boolean selected;

    private Point pointA;
    private Point pointB;
    private int[][] loadCoordinates;
    private int[][] polygonCoordinates;

    /**
     * Método construtor da classe DrawLoads
     *
     * @param x1 é a abscissa do ponto da carga nodal
     * @param y1 é a ordenada do ponto da carga nodal
     * @param load é o tipo de carregamento estrutural
     */
    public DrawLoads(int x1, int y1, String[] load) {
        this.pointA = new Point(x1, y1);
        this.pointB = new Point();
        this.loadType = load[0];
        this.loadName = load[1];

        if ("Concentrated Load".equals(this.loadType)) {
            this.horizontalSign = loadClassify("H", Double.valueOf(load[2]));
            this.verticalSign = loadClassify("V", Double.valueOf(load[3]));
            this.horizontalValue = Double.valueOf(load[2]);
            this.verticalValue = Double.valueOf(load[3]);
        }

        if ("Bending Moment".equals(this.loadType)) {
            this.sign = loadClassify("", Double.valueOf(load[2]));
            this.value = Double.valueOf(load[2]);
        }

        this.selected = false;
    }

    /**
     * Método construtor da classe DrawLoads
     *
     * @param x1 é a abscissa do primeiro ponto da carga distribuída
     * @param y1 é a ordenada do primeiro ponto da carga distribuída
     * @param x2 é a abscissa do último ponto da carga distribuída
     * @param y2 é a ordenada do último ponto da carga distribuída
     * @param load é o tipo de carregamento estrutural
     */
    public DrawLoads(int x1, int y1, int x2, int y2, String[] load) {
        this.pointA = new Point(x1, y1);
        this.pointB = new Point(x2, y2);
        this.loadType = load[0];
        this.loadName = load[1];

        if ("Distributed Load".equals(this.loadType)) {
            this.horizontalSign = loadClassify("H", Double.valueOf(load[2]));
            this.verticalSign = loadClassify("V", Double.valueOf(load[3]));
            this.horizontalValue = Double.valueOf(load[2]);
            this.verticalValue = Double.valueOf(load[3]);
        }
        if ("Axial Load".equals(this.loadType)) {
            this.sign = loadClassify("", Double.valueOf(load[2]));
            this.value = Double.valueOf(load[2]);
        }
        if ("Thermal Load".equals(this.loadType)) {
            this.Tzero = Double.valueOf(load[2]);
            this.Ttop = Double.valueOf(load[3]);
            this.Tbot = Double.valueOf(load[4]);
        }

        this.selected = false;
    }

    /**
     * Método construtor da classe DrawLoads
     *
     * @param polygon é o polígono onde vai ser desenhado o carregamneto
     * @param load é o tipo de carregamento estrutural
     */
    public DrawLoads(DrawPolygon polygon, String[] load) {
        this.loadType = load[0];
        this.loadName = load[1];
        this.sign = loadClassify("", Double.valueOf(load[2]));
        this.value = Double.valueOf(load[2]);

        this.loadCoordinates = loadCoordinates(polygon);
        this.polygonCoordinates = createPolygonCoordinates(polygon);
    }

    /**
     * Método para alterar o estado da carga estrutural
     *
     * @param select informa se a carga está selecionada
     */
    public void select(boolean select) {
        this.selected = select;
    }

    /**
     * Método para desenhar a carga estrutural
     *
     * @param load contém a informação gráfica da carga
     */
    public void draw(Graphics2D load) {
        load.setPaint(new java.awt.Color(255, 0, 0));

        if ("Concentrated Load".equals(this.loadType)) {
            drawConcentratedLoad(load);
        }
        if ("Bending Moment".equals(this.loadType)) {
            drawBendingMoment(load);
        }
        if ("Distributed Load".equals(this.loadType)) {
            drawUnifDistLoad(load);
        }
        if ("Axial Load".equals(this.loadType)) {
            drawDistAxialLoad(load);
        }
        if ("Planar Load".equals(this.loadType)) {
            drawUnifPlanarLoad(load);
        }
        if ("Thermal Load".equals(this.loadType)) {
            drawThermalLoad(load);
        }
    }

    /**
     * Método para desenhar a carga estrutural
     *
     * @param type é o tipo de modelo estrutural
     * @param load contém a informação gráfica da carga
     */
    public void draw(String type, Graphics2D load) {
        load.setPaint(new java.awt.Color(255, 0, 0));

        if ("Grids".equals(type) || "Slabs".equals(type)) {
            if ("Planar Load".equals(this.loadType)) {
                draw(load);
            } else {
                drawLoads_TopView(load);
            }
        } else {
            draw(load);
        }
    }

    /**
     * Método para saber se a carga contém o ponto
     *
     * @param point contém as coordenadas do rato
     * @return
     */
    public boolean contains(Point point) {
        boolean contains;

        contains = false;
        if (point.x == pointA.x && point.y == pointA.y) {
            contains = true;
        }
        if (point.x == pointB.x && point.y == pointB.y) {
            contains = true;
        }

        return contains;
    }

    /**
     * Método para saber se a carga contém o ponto
     *
     * @param point contém as coordenadas do rato
     * @return
     */
    public boolean polygonContains(Point point) {
        int nPoints = polygonCoordinates.length;
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];

        for (int i = 0; i < nPoints; i++) {
            xPoints[i] = polygonCoordinates[i][0];
            yPoints[i] = polygonCoordinates[i][1];
        }

        Polygon polygon = new Polygon(xPoints, yPoints, nPoints);

        return (polygon.contains(point.x, point.y));
    }

    /**
     * Método para obter uma matriz com as coordenadas dos pontos
     *
     * @return
     */
    public int[][] getCoordinates() {
        int[][] coordinates;

        switch (this.loadType) {
            case "Planar Load":
                coordinates = polygonCoordinates;
                break;
            case "Concentrated Load":
                coordinates = new int[1][2];
                coordinates[0][0] = (int) pointA.x;
                coordinates[0][1] = (int) pointA.y;
                break;
            case "Bending Moment":
                coordinates = new int[1][2];
                coordinates[0][0] = (int) pointA.x;
                coordinates[0][1] = (int) pointA.y;
                break;
            default:
                coordinates = new int[2][2];
                coordinates[0][0] = (int) pointA.x;
                coordinates[0][1] = (int) pointA.y;
                coordinates[1][0] = (int) pointB.x;
                coordinates[1][1] = (int) pointB.y;
                break;
        }

        return coordinates;
    }

    /**
     * Método para obter um vetor com a descrição da carga estrutural
     *
     * @return
     */
    public String[] getDescription() {
        String[] description;

        switch (this.loadType) {
            case "Concentrated Load":
                String[] concentratedLoad = { loadType, loadName, String.valueOf(horizontalValue), String.valueOf(verticalValue) };
                description = concentratedLoad;
                break;
            case "Bending Moment":
                String[] bendingMoment = { loadType, loadName, String.valueOf(value), "" };
                description = bendingMoment;
                break;
            case "Distributed Load":
                String[] distributedLoad = { loadType, loadName, String.valueOf(horizontalValue), String.valueOf(verticalValue) };
                description = distributedLoad;
                break;
            case "Axial Load":
                String[] axialLoad = { loadType, loadName, String.valueOf(value), "" };
                description = axialLoad;
                break;
            case "Planar Load":
                String[] planarLoad = { loadType, loadName, String.valueOf(value), "" };
                description = planarLoad;
                break;
            case "Thermal Load":
                String[] thermalLoad = { loadType, loadName, String.valueOf(Tzero), String.valueOf(Ttop), String.valueOf(Tbot) };
                description = thermalLoad;
                break;
            default:
                description = null;
                break;
        }

        return description;
    }

    /**
     * Método para atualizar os atributos da carga estrutural
     *
     * @param load
     * @return
     */
    public boolean updateLoad(String[] load) {
        boolean update = false;

        if ("Concentrated Load".equals(load[0]) && load[1].equals(this.loadName)) {
            this.horizontalSign = loadClassify("H", Double.valueOf(load[2]));
            this.verticalSign = loadClassify("V", Double.valueOf(load[3]));
            this.horizontalValue = Double.valueOf(load[2]);
            this.verticalValue = Double.valueOf(load[3]);
            update = true;
        }
        if ("Bending Moment".equals(load[0]) && load[1].equals(this.loadName)) {
            this.sign = loadClassify("", Double.valueOf(load[2]));
            this.value = Double.valueOf(load[2]);
            update = true;
        }
        if ("Distributed Load".equals(load[0]) && load[1].equals(this.loadName)) {
            this.horizontalSign = loadClassify("H", Double.valueOf(load[2]));
            this.verticalSign = loadClassify("V", Double.valueOf(load[3]));
            this.horizontalValue = Double.valueOf(load[2]);
            this.verticalValue = Double.valueOf(load[3]);
            update = true;
        }
        if ("Axial Load".equals(load[0]) && load[1].equals(this.loadName)) {
            this.sign = loadClassify("", Double.valueOf(load[2]));
            this.value = Double.valueOf(load[2]);
            update = true;
        }
        if ("Planar Load".equals(load[0]) && load[1].equals(this.loadName)) {
            this.sign = loadClassify("", Double.valueOf(load[2]));
            this.value = Double.valueOf(load[2]);
            update = true;
        }
        if ("Thermal Load".equals(load[0]) && load[1].equals(this.loadName)) {
            this.Tzero = Double.valueOf(load[2]);
            this.Ttop = Double.valueOf(load[3]);
            this.Tbot = Double.valueOf(load[4]);
            update = true;
        }

        return update;
    }

    /**
     * Método para obter o ponto mais proximo do ponto fornecido
     *
     * @param xPoint é a abscissa da coordenada do rato
     * @param yPoint é a ordenada da coordenada do rato
     * @param maxDistance é a distância máxima admissível
     * @return
     */
    public Point selectPoint(int xPoint, int yPoint, double maxDistance) {
        int[][] coordinates = getCoordinates();
        Point point2D = null;

        for (int[] coordinate : coordinates) {
            int x = coordinate[0];
            int y = coordinate[1];

            double distance = AnalyticGeometry.length(xPoint, yPoint, x, y);

            if (distance < maxDistance) {
                maxDistance = distance;
                point2D = new Point(x, y);
            }
        }

        return point2D;
    }

    /**
     * Método para mover a carga estrutural na relação fornecida
     *
     * @param deltaX é a distância a mover na horizontal
     * @param deltaY é a distância a mover na vertical
     */
    public void moveLoad(int deltaX, int deltaY) {
        if ("Concentrated Load".equals(this.loadType)) {
            pointA.translate(deltaX, deltaY);
        }

        if ("Bending Moment".equals(this.loadType)) {
            pointA.translate(deltaX, deltaY);
        }

        if ("Distributed Load".equals(this.loadType)) {
            pointA.translate(deltaX, deltaY);
            pointB.translate(deltaX, deltaY);
        }

        if ("Axial Load".equals(this.loadType)) {
            pointA.translate(deltaX, deltaY);
            pointB.translate(deltaX, deltaY);
        }

        if ("Planar Load".equals(this.loadType)) {
            int nPoints = polygonCoordinates.length;
            int[] xPoints = new int[nPoints];
            int[] yPoints = new int[nPoints];

            for (int i = 0; i < nPoints; i++) {
                xPoints[i] = polygonCoordinates[i][0];
                yPoints[i] = polygonCoordinates[i][1];
            }

            DrawPolygon polygon = new DrawPolygon(xPoints, yPoints, nPoints);
            polygon.movePolygon(deltaX, deltaY);
            loadCoordinates = loadCoordinates(polygon);
            polygonCoordinates = createPolygonCoordinates(polygon);
        }

        if ("Thermal Load".equals(this.loadType)) {
            pointA.translate(deltaX, deltaY);
            pointB.translate(deltaX, deltaY);
        }
    }

    /**
     * Método que devolve uma matriz com as coordenadas de desenho da carga estrutural
     *
     * @param polygon
     * @return
     */
    private static int[][] loadCoordinates(DrawPolygon polygon) {
        int[][] coordinates = polygon.getCoordinates();

        int xMin = coordinates[0][0];
        int xMax = coordinates[0][0];
        int yMin = coordinates[0][1];
        int yMax = coordinates[0][1];

        for (int i = 1; i < coordinates.length; i++) {
            if (xMin > coordinates[i][0]) {
                xMin = coordinates[i][0];
            }
            if (xMax < coordinates[i][0]) {
                xMax = coordinates[i][0];
            }
            if (yMin > coordinates[i][1]) {
                yMin = coordinates[i][1];
            }
            if (yMax < coordinates[i][1]) {
                yMax = coordinates[i][1];
            }
        }

        //Instruções para arredondar para o múltiplo de 25 mais próximo
        xMin = (int) (Math.round(xMin / 25.0) * 25);
        xMax = (int) (Math.round(xMax / 25.0) * 25);
        yMin = (int) (Math.round(yMin / 25.0) * 25);
        yMax = (int) (Math.round(yMax / 25.0) * 25);

        ArrayList<Point> arrayListCoordinates = new ArrayList();

        while (xMin <= xMax) {
            int y = yMin;
            while (y <= yMax) {
                Point point = new Point(xMin, y);
                if (polygon.contains(point)) {
                    arrayListCoordinates.add(new Point(xMin, y));
                }
                y = y + 25;
            }
            xMin = xMin + 25;
        }

        int length = arrayListCoordinates.size();
        int[][] loadCoordinates = new int[length][2];

        int i = 0;
        for (Point arrayCoordinates : arrayListCoordinates) {
            loadCoordinates[i][0] = arrayCoordinates.x;
            loadCoordinates[i][1] = arrayCoordinates.y;
            i++;
        }

        return loadCoordinates;
    }

    /**
     * Método que devolve uma matriz com as coordenadas da carga estrutural
     *
     * @param polygon
     * @return
     */
    private static int[][] createPolygonCoordinates(DrawPolygon polygon) {
        int[][] polygonCoordinates = polygon.getCoordinates();

        return polygonCoordinates;
    }

    /**
     * Método para obter a classificação da carga estrutural
     *
     * @param direction
     * @param load
     * @return
     */
    private static String loadClassify(String direction, double load) {
        String description;

        switch (direction) {
            case "H":
                //Bloco de instruções para cargas com componente horizontal
                if (load > 0) {
                    description = "Horizontal Positive";
                } else if (load < 0) {
                    description = "Horizontal Negative";
                } else {
                    description = "";
                }
                break;
            case "V":
                //Bloco de instruções para cargas com componente vertical
                if (load > 0) {
                    description = "Vertical Positive";
                } else if (load < 0) {
                    description = "Vertical Negative";
                } else {
                    description = "";
                }
                break;
            default:
                //Bloco de instruções para as restantes cargas estruturais
                if (load > 0) {
                    description = "Positive";
                } else if (load < 0) {
                    description = "Negative";
                } else {
                    description = "";
                }
                break;
        }

        return description;
    }

    /**
     * Método para desenhar cargas concentradas
     *
     * @param load contém a informação gráfica da carga
     */
    private void drawConcentratedLoad(Graphics2D load) {
        int x = pointA.x;
        int y = pointA.y;

        //Carga concentrada vertical positiva
        if ("Vertical Positive".equals(this.verticalSign)) {
            load.draw(new Line2D.Double(x, y, x, y - 25));
            load.draw(new Line2D.Double(x, y, x - 5, y - 5));
            load.draw(new Line2D.Double(x, y, x + 5, y - 5));
        }

        //Carga concentrada vertical negativa
        if ("Vertical Negative".equals(this.verticalSign)) {
            load.draw(new Line2D.Double(x, y, x, y - 25));
            load.draw(new Line2D.Double(x, y - 25, x - 5, y - 20));
            load.draw(new Line2D.Double(x, y - 25, x + 5, y - 20));
        }

        //Carga concentrada horizontal positiva
        if ("Horizontal Positive".equals(this.horizontalSign)) {
            load.draw(new Line2D.Double(x, y, x + 25, y));
            load.draw(new Line2D.Double(x + 25, y, x + 20, y - 5));
            load.draw(new Line2D.Double(x + 25, y, x + 20, y + 5));
        }

        //Carga concentrada horizontal negativa
        if ("Horizontal Negative".equals(this.horizontalSign)) {
            load.draw(new Line2D.Double(x, y, x + 25, y));
            load.draw(new Line2D.Double(x, y, x + 5, y - 5));
            load.draw(new Line2D.Double(x, y, x + 5, y + 5));
        }
    }

    /**
     * Método para desenhar momentos fletores
     *
     * @param load contém a informação gráfica da carga
     */
    private void drawBendingMoment(Graphics2D load) {
        int x = pointA.x;
        int y = pointA.y;

        //Momento fletor positivo
        if ("Positive".equals(this.sign)) {
            load.draw(new Line2D.Double(x + 12, y, x + 5, y - 4));
            load.draw(new Line2D.Double(x + 12, y, x + 15, y - 6));
            load.draw(new Arc2D.Double(x - 12, y - 12, 24, 24, 0, 240, Arc2D.OPEN));
        }

        //Momento fletor negativo
        if ("Negative".equals(this.sign)) {
            load.draw(new Line2D.Double(x + 12, y, x + 5, y + 4));
            load.draw(new Line2D.Double(x + 12, y, x + 15, y + 6));
            load.draw(new Arc2D.Double(x - 12, y - 12, 24, 24, 0, -240, Arc2D.OPEN));
        }
    }

    /**
     * Método para desenhar cargas uniformemente distribuídas
     *
     * @param load contém a informação gráfica da carga
     */
    private void drawUnifDistLoad(Graphics2D load) {
        double x1 = pointA.x;
        double y1 = pointA.y;
        double x2 = pointB.x;
        double y2 = pointB.y;

        //Cálculo da inclinação da reta e da reta perpendicular
        double inclination = (y2 - y1) / (x2 - x1);
        double angle = Math.atan(-1 / inclination);

        //Cálculo do comprimento da reta que contém a carga
        double length = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

        //Cálculo do número de linhas de carga e dos espaçamentos
        double nLoads, spacingX, spacingY, Xi, Yi;

        nLoads = Math.round(length / 25.0);
        spacingX = Math.abs((x2 - x1)) / nLoads;
        spacingY = Math.abs((y2 - y1)) / nLoads;

        //Instruções para desenhar a carga uniformemente distribuída

        //Desenho da parte inicial e final da carga distribuída
        if ("Vertical Positive".equals(this.verticalSign)) {
            load.draw(new Line2D.Double(x1, y1, x1, y1 - 25));
            load.draw(new Line2D.Double(x1, y1, x1 - 5, y1 - 5));
            load.draw(new Line2D.Double(x1, y1, x1 + 5, y1 - 5));

            load.draw(new Line2D.Double(x2, y2, x2, y2 - 25));
            load.draw(new Line2D.Double(x2, y2, x2 - 5, y2 - 5));
            load.draw(new Line2D.Double(x2, y2, x2 + 5, y2 - 5));
        }
        if ("Vertical Negative".equals(this.verticalSign)) {
            load.draw(new Line2D.Double(x1, y1, x1, y1 - 25));
            load.draw(new Line2D.Double(x1, y1 - 25, x1 - 5, y1 - 20));
            load.draw(new Line2D.Double(x1, y1 - 25, x1 + 5, y1 - 20));

            load.draw(new Line2D.Double(x2, y2, x2, y2 - 25));
            load.draw(new Line2D.Double(x2, y2 - 25, x2 - 5, y2 - 20));
            load.draw(new Line2D.Double(x2, y2 - 25, x2 + 5, y2 - 20));
        }
        if ("Horizontal Positive".equals(this.horizontalSign)) {
            load.draw(new Line2D.Double(x1, y1, x1 + 25, y1));
            load.draw(new Line2D.Double(x1 + 25, y1, x1 + 20, y1 - 5));
            load.draw(new Line2D.Double(x1 + 25, y1, x1 + 20, y1 + 5));

            load.draw(new Line2D.Double(x2, y2, x2 + 25, y2));
            load.draw(new Line2D.Double(x2 + 25, y2, x2 + 20, y2 - 5));
            load.draw(new Line2D.Double(x2 + 25, y2, x2 + 20, y2 + 5));
        }
        if ("Horizontal Negative".equals(this.horizontalSign)) {
            load.draw(new Line2D.Double(x1, y1, x1 + 25, y1));
            load.draw(new Line2D.Double(x1, y1, x1 + 5, y1 - 5));
            load.draw(new Line2D.Double(x1, y1, x1 + 5, y1 + 5));

            load.draw(new Line2D.Double(x2, y2, x2 + 25, y2));
            load.draw(new Line2D.Double(x2, y2, x2 + 5, y2 - 5));
            load.draw(new Line2D.Double(x2, y2, x2 + 5, y2 + 5));
        }

        //Desenho da parte interior da carga distribuída
        Xi = x1;
        Yi = y1;
        if (x2 < x1) {
            Xi = x2;
        }
        if (y2 < y1) {
            Yi = y2;
        }

        if (angle > 0) {
            Yi = y1;
            if (y1 < y2) {
                Yi = y2;
            }
        }

        Xi = Xi + spacingX;
        if (angle > 0) {
            Yi = Yi - spacingY;
        } else {
            Yi = Yi + spacingY;
        }
        for (int i = 1; i < nLoads; i++) {
            //Carga vertical uniformemente distribuída positiva
            if ("Vertical Positive".equals(this.verticalSign)) {
                load.draw(new Line2D.Double(Xi, Yi, Xi, Yi - 25));
                load.draw(new Line2D.Double(Xi, Yi, Xi - 5, Yi - 5));
                load.draw(new Line2D.Double(Xi, Yi, Xi + 5, Yi - 5));
            }

            //Carga vertical uniformemente distribuída negativa
            if ("Vertical Negative".equals(this.verticalSign)) {
                load.draw(new Line2D.Double(Xi, Yi, Xi, Yi - 25));
                load.draw(new Line2D.Double(Xi, Yi - 25, Xi - 5, Yi - 20));
                load.draw(new Line2D.Double(Xi, Yi - 25, Xi + 5, Yi - 20));
            }

            //Carga horizontal uniformemente distribuída positiva
            if ("Horizontal Positive".equals(this.horizontalSign)) {
                load.draw(new Line2D.Double(Xi, Yi, Xi + 25, Yi));
                load.draw(new Line2D.Double(Xi + 25, Yi, Xi + 20, Yi - 5));
                load.draw(new Line2D.Double(Xi + 25, Yi, Xi + 20, Yi + 5));
            }

            //Carga horizontal uniformemente distribuída negativa
            if ("Horizontal Negative".equals(this.horizontalSign)) {
                load.draw(new Line2D.Double(Xi, Yi, Xi + 25, Yi));
                load.draw(new Line2D.Double(Xi, Yi, Xi + 5, Yi - 5));
                load.draw(new Line2D.Double(Xi, Yi, Xi + 5, Yi + 5));
            }

            Xi = Xi + spacingX;
            if (angle > 0) {
                Yi = Yi - spacingY;
            } else {
                Yi = Yi + spacingY;
            }
        }
    }

    /**
     * Método para desenhar cargas axiais distribuídas
     *
     * @param load contém a informação gráfica da carga
     */
    private void drawDistAxialLoad(Graphics2D load) {
        int[][] coordinates = { { pointA.x, pointA.y }, { pointB.x, pointB.y } };
        coordinates = VerticesCoordinates.line(coordinates);

        double x1 = coordinates[0][0];
        double y1 = coordinates[0][1];
        double x2 = coordinates[1][0];
        double y2 = coordinates[1][1];

        //Cálculo da inclinação da linha e do seu comprimento
        double angle = AnalyticGeometry.lineInclination(x1, y1, x2, y2);
        double length = AnalyticGeometry.length(x1, y1, x2, y2);

        //Cálculo do número de linhas de carga e dos espaçamentos
        double nLoads, spacingX, spacingY, Xi, Yi;

        nLoads = Math.round(length / 25.0);
        spacingX = Math.abs((x2 - x1)) / nLoads;
        spacingY = Math.abs((y2 - y1)) / nLoads;

        //Carga axial positiva uniformemente distribuída
        if ("Positive".equals(this.sign)) {
            load.draw(new Line2D.Double(x1, y1, x2, y2));

            double[][] matrix = { { 0 }, { 0 }, { 7 }, { 0 } };
            double[][] pointsA = AnalyticGeometry.coordinateTransformations(matrix, angle - (3 * PI / 4));
            double[][] pointsB = AnalyticGeometry.coordinateTransformations(matrix, angle + (3 * PI / 4));

            Xi = x1 + spacingX;
            if (angle > 0) {
                Yi = y1 + spacingY;
            } else {
                Yi = y1 - spacingY;
            }
            for (int i = 1; i <= nLoads; i++) {
                load.draw(new Line2D.Double(Xi, Yi, round(Xi + pointsA[2][0]), round(Yi + pointsA[3][0])));
                load.draw(new Line2D.Double(Xi, Yi, round(Xi + pointsB[2][0]), round(Yi + pointsB[3][0])));

                Xi = Xi + spacingX;
                if (angle > 0) {
                    Yi = Yi + spacingY;
                } else {
                    Yi = Yi - spacingY;
                }
            }
        }

        //Carga axial negativa uniformemente distribuída
        if ("Negative".equals(this.sign)) {
            load.draw(new Line2D.Double(x2, y2, x1, y1));

            double[][] matrix = { { 0 }, { 0 }, { 7 }, { 0 } };
            double[][] pointsA = AnalyticGeometry.coordinateTransformations(matrix, angle - (PI / 4));
            double[][] pointsB = AnalyticGeometry.coordinateTransformations(matrix, angle + (PI / 4));

            Xi = x2 - spacingX;
            if (angle > 0) {
                Yi = y2 - spacingY;
            } else {
                Yi = y2 + spacingY;
            }
            for (int i = 1; i <= nLoads; i++) {
                load.draw(new Line2D.Double(Xi, Yi, round(Xi + pointsA[2][0]), round(Yi + pointsA[3][0])));
                load.draw(new Line2D.Double(Xi, Yi, round(Xi + pointsB[2][0]), round(Yi + pointsB[3][0])));

                Xi = Xi - spacingX;
                if (angle > 0) {
                    Yi = Yi - spacingY;
                } else {
                    Yi = Yi + spacingY;
                }
            }
        }
    }

    /**
     * Método para desenhar cargas uniformes de superfície
     *
     * @param load contém a informação gráfica da carga
     */
    private void drawUnifPlanarLoad(Graphics2D load) {
        //Carga plana uniformemente distribuída positiva
        if ("Positive".equals(this.sign)) {
            for (int[] loadCoordinate : loadCoordinates) {
                int x = loadCoordinate[0];
                int y = loadCoordinate[1];
                load.draw(new Line2D.Double(x - 3, y, x + 3, y));
                load.draw(new Line2D.Double(x, y - 3, x, y + 3));
            }
        }

        //Carga plana uniformemente distribuída positiva negativa
        if ("Negative".equals(this.sign)) {
            for (int[] loadCoordinate : loadCoordinates) {
                int x = loadCoordinate[0];
                int y = loadCoordinate[1];
                load.draw(new Line2D.Double(x - 3, y, x + 3, y));
            }
        }
    }

    /**
     * Método para desenhar variações de temperatura nos elementos
     *
     * @param load contém a informação gráfica da carga
     */
    private void drawThermalLoad(Graphics2D load) {
        int[][] coordinates = { { pointA.x, pointA.y }, { pointB.x, pointB.y } };
        coordinates = VerticesCoordinates.line(coordinates);

        double x1 = coordinates[0][0];
        double y1 = coordinates[0][1];
        double x2 = coordinates[1][0];
        double y2 = coordinates[1][1];

        //Cálculo da inclinação da linha, do comprimento e do número de cargas
        double angle = AnalyticGeometry.lineInclination(x1, y1, x2, y2);
        double length = AnalyticGeometry.length(x1, y1, x2, y2);
        double spacing = length / (length / 50.0);

        String thermalLoad_Ttop;
        String thermalLoad_Tbot;
        if (Tzero != 0.0) {
            thermalLoad_Ttop = decimalFormat(Tzero) + "ºC";
            thermalLoad_Tbot = decimalFormat(Tzero) + "ºC";
        } else {
            thermalLoad_Ttop = decimalFormat(Ttop) + "ºC";
            thermalLoad_Tbot = decimalFormat(Tbot) + "ºC";
        }

        //Instruções para desenhar a representação da variação de temperatura
        load.rotate(angle, x1, y1);

        for (int i = 0; i < (length / 50.0); i++) {
            load.drawString(thermalLoad_Ttop, round(x1 + i * spacing), round(y1 - 5));
            load.drawString(thermalLoad_Tbot, round(x1 + i * spacing), round(y1 + 15));
        }

        load.rotate(-angle, x1, y1);
    }

    /**
     * Método para desenhar cargas vistas de cima
     *
     * @param load contém a informação gráfica da carga
     */
    public void drawLoads_TopView(Graphics2D load) {
        int x = pointA.x;
        int y = pointA.y;

        if ("Concentrated Load".equals(this.loadType)) {
            if ("Vertical Positive".equals(this.verticalSign)) {
                load.draw(new Line2D.Double(x - 3, y, x + 3, y));
                load.draw(new Line2D.Double(x, y - 3, x, y + 3));
            }

            if ("Vertical Negative".equals(this.verticalSign)) {
                load.draw(new Line2D.Double(x - 3, y, x + 3, y));
            }
        }
        if ("Distributed Load".equals(this.loadType)) {
            double x1 = pointA.x;
            double y1 = pointA.y;
            double x2 = pointB.x;
            double y2 = pointB.y;

            //Cálculo da inclinação da reta e da reta perpendicular
            double inclination = (y2 - y1) / (x2 - x1);
            double angle = Math.atan(-1 / inclination);

            //Cálculo do comprimento da reta que contém a carga
            double length = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

            //Cálculo do número de linhas de carga e dos espaçamentos
            double nLoads, spacingX, spacingY, Xi, Yi;

            nLoads = Math.round(length / 25.0);
            spacingX = Math.abs((x2 - x1)) / nLoads;
            spacingY = Math.abs((y2 - y1)) / nLoads;

            //Instruções para desenhar a carga uniformemente distribuída
            Xi = x1;
            Yi = y1;
            if (x2 < x1) {
                Xi = x2;
            }
            if (y2 < y1) {
                Yi = y2;
            }

            if (angle > 0) {
                Yi = y1;
                if (y1 < y2) {
                    Yi = y2;
                }
            }

            for (int i = 0; i <= nLoads; i++) {
                //Carga vertical uniformemente distribuída positiva
                if ("Vertical Positive".equals(this.verticalSign)) {
                    load.draw(new Line2D.Double(Xi - 3, Yi, Xi + 3, Yi));
                    load.draw(new Line2D.Double(Xi, Yi - 3, Xi, Yi + 3));
                }

                //Carga vertical uniformemente distribuída negativa
                if ("Vertical Negative".equals(this.verticalSign)) {
                    load.draw(new Line2D.Double(Xi - 3, Yi, Xi + 3, Yi));
                }

                Xi = Xi + spacingX;
                if (angle > 0) {
                    Yi = Yi - spacingY;
                } else {
                    Yi = Yi + spacingY;
                }
            }
        }
    }
}
