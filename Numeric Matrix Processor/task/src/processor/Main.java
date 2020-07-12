package processor;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = -1;

        while (choice != 0) {
            System.out.print( "1. Add matrices" + "\n" +
                    "2. Multiply matrix to a constant" + "\n" +
                    "3. Multiply matrices" + "\n" +
                    "4. Transpose matrix" + "\n" +
                    "5. Calculate a determinant" + "\n" +
                    "6. Inverse matrix" + "\n" +
                    "0. Exit" + "\n" +
                    "Your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                default:
                    System.out.println("Please choose between the choices.");
                case 0:
                    break;
                case 1:
                    addMatrixAndMatrix();
                    break;
                case 2:
                    multiplyConstAndMatrix();
                    break;
                case 3:
                    multiplyMatrixAndMatrix();
                    break;
                case 4:
                    transposeMatrix();
                    break;
                case 5:
                    calculateDeterminant();
                    break;
                case 6:
                    inverseMatrix();
                    break;
            }
        }
    }

    static void addMatrixAndMatrix() {
        System.out.print("Enter size of first matrix: ");
        int matrixOneRowCount = scanner.nextInt();
        int matrixOneColCount = scanner.nextInt();
        double[][] matrixOne = inputMatrix("Enter first matrix: ", matrixOneRowCount, matrixOneColCount);

        System.out.print("Enter size of second matrix: ");
        int matrixTwoRowCount = scanner.nextInt();
        int  matrixTwoColCount = scanner.nextInt();
        double[][] matrixTwo = inputMatrix("Enter second matrix: ", matrixTwoRowCount, matrixTwoColCount);

        if (matrixOneRowCount == matrixTwoRowCount && matrixOneColCount == matrixTwoColCount) {
            for (int i = 0; i < matrixOneRowCount; i++) {
                for (int j = 0; j < matrixOneColCount; j++) {
                    double entrySum = matrixOne[i][j] + matrixTwo[i][j];
                    System.out.print(entrySum + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("ERROR");
        }
    }

    static void multiplyConstAndMatrix() {
        System.out.print("Enter size of matrix: ");
        int rowCount = scanner.nextInt();
        int colCount = scanner.nextInt();
        double[][] matrix = inputMatrix("Enter matrix: ", rowCount, colCount);
        double scalar = scanner.nextDouble();

        for (double[] row : matrix) {
            for (double entry : row) {
                double multipliedEntry = scalar * entry;
                System.out.print(multipliedEntry + " ");
            }
            System.out.println();
        }
    }

    static void multiplyMatrixAndMatrix() {
        System.out.print("Enter size of first matrix: ");
        int matrixOneRowCount = scanner.nextInt();
        int matrixOneColCount = scanner.nextInt();
        double[][] matrixOne = inputMatrix("Enter first matrix: ", matrixOneRowCount, matrixOneColCount);

        System.out.print("Enter size of second matrix: ");
        int matrixTwoRowCount = scanner.nextInt();
        int matrixTwoColCount = scanner.nextInt();
        double[][] matrixTwo = inputMatrix("Enter second matrix: ", matrixTwoRowCount, matrixTwoColCount);

        System.out.println("The multiplication result is: ");
        for (int i = 0; i < matrixOneRowCount; i++) {
            for (int j = 0; j < matrixTwoColCount; j++) {
                double multipliedEntry = 0;
                for (int k = 0; k < matrixOne[i].length; k++) {
                    multipliedEntry += matrixOne[i][k] * matrixTwo[k][j];
                }

                System.out.print(multipliedEntry + " ");
            }
            System.out.println();
        }
    }

    static void transposeMatrix() {
        System.out.print( "1. Main diagonal" + "\n" +
                            "2. Side diagonal" + "\n" +
                            "3. Vertical line" + "\n" +
                            "4. Horizontal line" + "\n" +
                            "Your choice: ");
        int choice = scanner.nextInt();
        if (choice < 1 || choice > 4) {
            System.out.println("Choose between the choices.");
            return;
        }

        System.out.print("Enter matrix size: ");
        int rowCount = scanner.nextInt();
        int colCount = scanner.nextInt();
        double[][] matrix = inputMatrix("Enter matrix: ", rowCount, colCount);

        System.out.println("The result is: ");
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                double transposedEntry = 0;

                switch (choice) {
                    case 1:
                        transposedEntry = matrix[j][i];
                        break;
                    case 2:
                        transposedEntry = matrix[colCount - 1 - j][rowCount - 1 - i];
                        break;
                    case 3:
                        transposedEntry = matrix[i][colCount - 1 - j];
                        break;
                    case 4:
                        transposedEntry = matrix[rowCount - 1 - i][j];
                        break;
                }

                System.out.print(transposedEntry + " ");
            }
            System.out.println();
        }
    }

    static void calculateDeterminant() {
        System.out.print("Enter matrix size: ");
        int rowCount = scanner.nextInt();
        int colCount = scanner.nextInt();
        double[][] matrix = inputMatrix("Enter matrix: ", rowCount, colCount);
        double determinant = calculateDeterminantByRecursion(matrix);
        System.out.println("The result is: ");
        System.out.println(determinant);
    }

    /**
     *
     * @param matrix current matrix
     * @return if matrix length is 2, returns determinant according to (ac - bd) formula
     * overall, returns a determinant solved thru Laplace expansion
     *
     * row = 0 is used for every Laplace expansion
     *
     * loop i goes through every column of the original matrix to make a minor of every
     * element matrix[0][i]
     *
     * loop j and k is for creation of minor matrix, with dimensions of (n-1) x (n-1) or
     * matrix.length - 1 x matrix.length - 1
     *
     * -1 is powered by i + 2 because the indexes here start with 0, hence 1 is added for both column and row
     */
    static double calculateDeterminantByRecursion(double[][] matrix) {
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double determinant = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            double[][] minorMatrix = new double[matrix.length - 1][matrix.length - 1];

            for (int j = 0; j < matrix.length - 1; j++) {
                for (int k = 0; k < matrix.length - 1; k++) {
                    int origRow = j + 1;
                    int origCol = k >= i ? k + 1 : k;
                    minorMatrix[j][k] = matrix[origRow][origCol];
                }
            }

            determinant += matrix[0][i] * Math.pow(-1, i + 2) * calculateDeterminantByRecursion(minorMatrix);
        }

        return determinant;
    }

    static void inverseMatrix() {
        System.out.print("Enter matrix size: ");
        int rowCount = scanner.nextInt();
        int colCount = scanner.nextInt();
        double[][] matrix = inputMatrix("Enter matrix: ", rowCount, colCount);
        double determinant = calculateDeterminantByRecursion(matrix);
        double detReciprocal = 1 / determinant;

        double[][] adjointMatrix = new double[rowCount][colCount];
        double[][] transposedMatrix = new double[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                double[][] minorMatrix = new double[rowCount - 1][colCount - 1];

                for (int k = 0; k < rowCount - 1; k++) {
                    for (int l = 0; l < colCount - 1; l++) {
                        int origRow = k >= i ? k + 1 : k;
                        int origCol = l >= j ? l + 1 : l;

                        minorMatrix[k][l] = matrix[origRow][origCol];
                    }
                }

                adjointMatrix[i][j] = Math.pow(-1, i + j + 2) * calculateDeterminantByRecursion(minorMatrix);
                transposedMatrix[j][i] = adjointMatrix[i][j];
            }
        }

        double[][] inverseMatrix = new double[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                inverseMatrix[i][j] = detReciprocal * transposedMatrix[i][j];
            }
        }

        System.out.println("The result is: ");
        for (double[] inverseRow : inverseMatrix) {
            for (double inverseEntry : inverseRow) {
                System.out.print(inverseEntry + " ");
            }
            System.out.println();
        }
    }

    static double[][] inputMatrix(String instructions, int rowCount, int colCount) {
        System.out.println(instructions);
        double[][] scannerMatrix = new double[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                scannerMatrix[i][j] = scanner.nextDouble();
            }
        }

        return scannerMatrix.clone();
    }
}
