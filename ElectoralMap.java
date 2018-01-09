import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ElectionMap {
    private static HashMap<String, HashMap<String, ArrayList<SubRegion>>> subRegions = new HashMap<>();

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
        //thisMap.visualize("USA-county", "2016");
        String region = "USA-county";
        String year = "1960";
        thisMap.getGeoData(region);
        thisMap.getVoteData(year);
        thisMap.newVisualize();
    }


    public void getGeoData(String region) throws Exception {
        File f = new File("inputs/" + region + ".txt");
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
        int regionsNum = Integer.parseInt(inputObject.nextLine());
        for (int i = 0; i < regionsNum; i++) {
            inputObject.nextLine();
            String subName = inputObject.nextLine();
            String supName = inputObject.nextLine();
            subName = subName.toLowerCase();
            supName = supName.toLowerCase();
            if (subRegions.containsKey(supName)) {
                if (subRegions.get(supName).containsKey(subName)) {
                    subRegions.get(supName).get(subName).add(new SubRegion(subName));
                } else {
                    ArrayList<SubRegion> subList = new ArrayList<>();
                    subList.add(new SubRegion(subName));
                    subRegions.get(supName).put(subName, subList);
                }
            } else {
                HashMap<String, ArrayList<SubRegion>> subMap = new HashMap<>();
                ArrayList<SubRegion> subList = new ArrayList<>();
                subList.add(new SubRegion(subName));
                subMap.put(subName, subList);
                subRegions.put(supName, subMap);
            }
            int pointsNum = Integer.parseInt(inputObject.nextLine());
            double[] pointsX = new double[pointsNum];
            double[] pointsY = new double[pointsNum];
            for (int z = 0; z < pointsNum; z++) {
                pointsX[z] = inputObject.nextDouble();
                pointsY[z] = inputObject.nextDouble();
                inputObject.nextLine();
            }
            subRegions.get(supName).get(subName).get(subRegions.get(supName).get(subName).size() - 1).xCoords = pointsX;
            subRegions.get(supName).get(subName).get(subRegions.get(supName).get(subName).size() - 1).yCoords = pointsY;
        }
        inputObject.close();
    }

    public void getVoteData(String year) throws Exception {
        for (String key : subRegions.keySet()) {
            File f2 = new File("inputs/" + key + year + ".txt");
            Scanner inputObject = new Scanner(f2);
            inputObject.useDelimiter(",");
            inputObject.nextLine();
            while (inputObject.hasNextLine()) {
                String regionName = inputObject.next();
                regionName = regionName.toLowerCase();
                int[] votes = {inputObject.nextInt(), inputObject.nextInt(), inputObject.nextInt()};
                inputObject.nextLine();
                if (subRegions.get(key).containsKey(regionName)) {
                } else if (subRegions.get(key).containsKey(regionName + " city")) {
                    regionName += " city";
                } else if (subRegions.get(key).containsKey(regionName + " parish")) {
                    regionName += " parish";
                }
                System.out.println(regionName);
                System.out.println(key);
                if (subRegions.get(key).containsKey(regionName)) {
                    for (int i = 0; i < subRegions.get(key).get(regionName).size(); i++) {
                        System.out.println(i);
                        subRegions.get(key).get(regionName).get(i).votes = votes;
                        subRegions.get(key).get(regionName).get(i).setColor();
                    }
                    if (subRegions.get(key).containsKey(regionName + " city")) {
                        for (int i = 0; i < subRegions.get(key).get(regionName + " city").size(); i++) {
                            subRegions.get(key).get(regionName + " city").get(i).votes = votes;
                            subRegions.get(key).get(regionName + " city").get(i).setColor();
                        }
                    }
                }
            }
            inputObject.close();
        }

    }

    public void newVisualize() {
        for (String supKey : subRegions.keySet()) {
            for (String subKey : subRegions.get(supKey).keySet()) {
                for (int i = 0; i < subRegions.get(supKey).get(subKey).size(); i++) {
                    if (subRegions.get(supKey).get(subKey).get(i).color != null) {
                        StdDraw.setPenColor(subRegions.get(supKey).get(subKey).get(i).color);
                        StdDraw.filledPolygon(subRegions.get(supKey).get(subKey).get(i).xCoords, subRegions.get(supKey).get(subKey).get(i).yCoords);
                        StdDraw.show();
                    } else {
                        System.out.println(supKey + " " + subKey);
                    }
                }
            }
        }

    }
