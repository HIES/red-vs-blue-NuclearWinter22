import java.io.File;
import java.util.Scanner;

public class EmptyMap {
    public static void main(String[] args)throws Exception {
        visualize("AK.txt");
    }

    public static void visualize(String fileName) throws Exception{
        File f = new File("inputs/" + fileName);
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
                for (int y = 0; y < 3; y++) inputObject.nextLine();
                int pointsNum = Integer.parseInt(inputObject.nextLine());
                double[] pointsX = new double[pointsNum];
                double[] pointsY = new double[pointsNum];
                for (int z = 0; z < pointsNum; z++) {
                    pointsX[z] = inputObject.nextDouble();
                    pointsY[z] = inputObject.nextDouble();
                    inputObject.nextLine();
                }
                StdDraw.polygon(pointsX, pointsY);
            }
        inputObject.close();
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
        System.out.println("Done!");
    }
}
