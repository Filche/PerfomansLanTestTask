package task2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class task2 {
    public static void main(String[] args) {
        List<Integer> pointsPositions = createPointsPositions(
                getCircle(args[0]),
                getPoints(args[1]));
        pointsPositions.forEach(System.out::println);
    }

    /**
     * @param filePath - путь к файлу с данными об окружности
     * @return - массив, состоящий из данных об окружности на координатной плоскости:
     * x, y и r^2.
     */
    private static ArrayList<Double> getCircle(String filePath) {
        ArrayList<Double> circleData = new ArrayList<>();

        //Данные из файла записываются в массив
        try (BufferedReader circleFile = new BufferedReader(
                new FileReader(filePath))) {
            String[] XandY = circleFile.readLine().split(" ");
            String radiusPowTwo = circleFile.readLine();
            circleData.add(Double.parseDouble(XandY[0]));
            circleData.add(Double.parseDouble(XandY[1]));
            circleData.add(Math.pow(Double.parseDouble(radiusPowTwo), 2));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return circleData;
    }

    /**
     * @param filePath - путь к файлу с координатами точек для анализа
     * @return - массив с координатами точек для анализа
     */
    private static List<List<Double>> getPoints(String filePath) {
        List<List<Double>> points = new LinkedList<>();

        //Данные из файла записываются в массив
        try (BufferedReader pointsFile = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = pointsFile.readLine()) != null) {
                List<Double> point = new ArrayList<>();
                point.add(Double.parseDouble(line.split(" ")[0]));
                point.add(Double.parseDouble(line.split(" ")[1]));
                points.add(point);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return points;
    }

    /**
     * @param circle - массив из данных об окружности
     * @param points - массив из координат точек
     * @return - массив результатов анализа точек
     */
    private static List<Integer> createPointsPositions(
            ArrayList<Double> circle, List<List<Double>> points) {
        List<Integer> pointsPositions = new ArrayList<>();

        for (List<Double> point : points) {
            //Применяем уравнение окружности
            double circleEquation = Math.pow(point.get(0) - circle.get(0), 2) +
                    Math.pow(point.get(1) - circle.get(1), 2) -
                    circle.get(2);
            //Если результат уравнения окружности > 0, то точка лежит снаружи окружности
            if (circleEquation > 0) {
                pointsPositions.add(2);
            //Если результат уравнения окружности = 0, то точка лежит на окружности
            } else if (circleEquation == 0) {
                pointsPositions.add(0);
            //Если результат уравнения окружности < 0, то есть во всех других случаях,
                // то точка лежит внутри окружности
            } else {
                pointsPositions.add(1);
            }
        }

        return pointsPositions;
    }
}
