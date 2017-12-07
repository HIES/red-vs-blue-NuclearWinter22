import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class EmptyMap {
    public static void main(String[] args)throws Exception {
        visualize("AL.txt");
    }

    public static void visualize(String fileName) throws Exception{
        File f = new File("inputs/" + fileName);
        Scanner inputObject = new Scanner(f);
        inputObject.useDelimiter("   ");
        String axisLine1 = inputObject.nextLine();
        String axisLine2 = inputObject.nextLine();
        String[] axisString1 = axisLine1.split("   ");
        String[] axisString2 = axisLine2.split("   ");
        String[] axisStrings = new String[4];
        System.arraycopy(axisString1, 0, axisStrings, 0, 2);
        System.arraycopy(axisString2,0,axisStrings,2,2);
        double[] bounds = new double[axisStrings.length];
        for(int i = 0; i < axisStrings.length; i++){
            bounds[i] = Double.parseDouble(axisStrings[i]);
        }
        //double[] bounds = new double[4];
        //for (int i = 0; i < 4; i++) bounds[i] = Double.parseDouble(inputObject.next());
        int testInt1 = ((int)bounds[2])-((int)bounds[0]);
        int testInt2 = ((((int)bounds[3])-((int)bounds[1])));
        StdDraw.setXscale(bounds[0], bounds[2]);
        StdDraw.setYscale(bounds[1], bounds[3]);
        int regionsNum = Integer.parseInt(inputObject.nextLine());
            for (int i = 0; i < regionsNum; i++) {
                for (int y = 0; y < 3; y++) inputObject.nextLine();
                int pointsNum = Integer.parseInt(inputObject.nextLine());
                Queue<Double[]> points = new LinkedList<>();
                for (int j = 0; j < pointsNum; j++) {
                    Double[] thisPoint = new Double[2];
                    String[] pointString = inputObject.nextLine().split("   ");
                    for (int x = 0; x < pointString.length; x++) {
                        thisPoint[x] = Double.parseDouble(pointString[x]);
                    }
                    points.add(thisPoint);
                    System.out.println("point added" + j);
                }
                for (int y = 0; y < points.size(); y++) {
                    Double[] point1 = points.remove();
                    Double[] point2 = points.peek();
                    StdDraw.line(point1[0], point2[0], point1[1], point2[1]);
                    System.out.println("line drawn" + y);
                    points.add(point1);
                }

            }
        inputObject.close();
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }
}
