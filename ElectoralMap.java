import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ElectionMap {
    private static HashMap<String, ArrayList<SubRegion>> subRegions = new HashMap<>();
    private int regionsNum;
    private String[] regionKeys;

    public class SubRegion {
        private String name;
        private int[] votes = new int[3];
        private double[] xCoords;
        private double[] yCoords;
        private Color color;

        public SubRegion(String name) {
            this.name = name;
        }

        public void setColor() {
            if (votes[0] > votes[1] && votes[0] > votes[2]){
                color = Color.RED;
            } else if (votes[1] > votes[0] && votes[1] > votes[2]) {
                color = Color.BLUE;
            } else if (votes[2] > votes[0] && votes[2] > votes[1]) {
                color = Color.GREEN;
            }
        }
    }

    public static void main(String[] args) throws Exception{
        ElectionMap thisMap = new ElectionMap();
        thisMap.getLocations("USA.txt");
    }

    public void getLocations(String region, String year) throws Exception {
        File f = new File("inputs/" + region +".txt");
        Scanner inputObject = new Scanner(f);
        double[] bounds = new double[4];
        for (int i = 0; i < 4; i += 2) {
            bounds[i] = inputObject.nextDouble();
            bounds[i + 1] = inputObject.nextDouble();
            inputObject.nextLine();
        }
        StdDraw.setCanvasSize((((int) bounds[0] - (int) bounds[2]) * (512)) / ((int) bounds[1] - (int) bounds[3]), 512);
        StdDraw.setXscale(bounds[0], bounds[2]);
        StdDraw.setYscale(bounds[1], bounds[3]);
        regionsNum = Integer.parseInt(inputObject.nextLine());
        regionKeys = new String[regionsNum];
        for (int i = 0; i < regionsNum; i++) {
            inputObject.nextLine();
            String subName = inputObject.nextLine();
            regionKeys[i] = subName;
            if (subRegions.containsKey(subName)) {
                subRegions.get(subName).add(new SubRegion(subName));
            } else {
                ArrayList<SubRegion> currentSub = new ArrayList<>();
                currentSub.add(new SubRegion(subName));
                subRegions.put(subName, currentSub);
            }
            inputObject.nextLine();
            int pointsNum = Integer.parseInt(inputObject.nextLine());
            double[] pointsX = new double[pointsNum];
            double[] pointsY = new double[pointsNum];
            for (int z = 0; z < pointsNum; z++) {
                pointsX[z] = inputObject.nextDouble();
                pointsY[z] = inputObject.nextDouble();
                inputObject.nextLine();
            }
            subRegions.get(subName).get(subRegions.get(subName).size() - 1).xCoords = pointsX;
            subRegions.get(subName).get(subRegions.get(subName).size() - 1).yCoords = pointsY;

            StdDraw.polygon(subRegions.get(subName).get(subRegions.get(subName).size() - 1).xCoords, subRegions.get(subName).get(subRegions.get(subName).size() - 1).yCoords);
        }
        inputObject.close();
    }

    public void getColors(String fileName) throws  Exception{
        File f = new File("inputs/" + fileName);
        Scanner inputObject = new Scanner(f);
        inputObject.nextLine();
        inputObject.useDelimiter(",");
        for(int i = 0; i < regionsNum; i++) {
            String regionName = inputObject.next();
            int[] votes = {inputObject.nextInt(), inputObject.nextInt(), inputObject.nextInt()};
            inputObject.nextLine();
            for (int y = 0; y < subRegions.get(regionName).size(); y++) {
                subRegions.get(regionName).get(y).votes = votes;
                subRegions.get(regionName).get(y).setColor();
                visualize();
            }
        }
    }

    public void visualize() {
        for (int i = 0;)
        StdDraw.setPenColor();
        StdDraw.filledPolygon();
    }
}
