import java.util.Objects;
import java.util.Random;

public class ColourGridApplication {
    static final Random random = new Random();
    static int globalRowIndex = 0;
    static int globalColumnIndex = 0;
    static String[][] checkedNode;
    static String[][] result;
    static int count;

    static boolean isValid(int x, int y,
                           String key,
                           String[][] input) {
        if (x < globalRowIndex && y < globalColumnIndex &&
                x >= 0 && y >= 0) {
            return Objects.equals(checkedNode[x][y], "0") &&
                    input[x][y].equals(key);
        } else
            return false;
    }

    static void searchGrid(String x, String y, int i,
                           int j, String[][] input) {
        if (x.equals(y))
            return;

        checkedNode[i][j] = input[i][j];
        count++;

        int[] xMove = {0, 0, 1, -1};
        int[] yMove = {1, -1, 0, 0};

        for (int u = 0; u < 4; u++)
            if ((isValid(i + yMove[u],
                    j + xMove[u], x, input)))
                searchGrid(x, y, i + yMove[u],
                        j + xMove[u], input);
    }

    static void resetCheckedNodes() {
        for (int i = 0; i < globalRowIndex; i++)
            for (int j = 0; j < globalColumnIndex; j++)
                checkedNode[i][j] = "0";
    }

    static void resetResult(String key,
                            String[][] input) {
        for (int i = 0; i < globalRowIndex; i++) {
            for (int j = 0; j < globalColumnIndex; j++) {
                if (Objects.equals(checkedNode[i][j], key) &&
                        Objects.equals(input[i][j], key))
                    result[i][j] = checkedNode[i][j];
                else
                    result[i][j] = "0";
            }
        }
    }

    static void displayResult(int res) {
        System.out.println("\n The largest connecting block of " +
                "nodes with the same color:" +
                res + "\n");

        for (int i = 0; i < globalRowIndex; i++) {
            for (int j = 0; j < globalColumnIndex; j++) {
                if (!Objects.equals(result[i][j], "0"))
                    System.out.print(result[i][j] + " ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }

    static void computeConnectingBlockOfNodes(String[][] input) {
        int currentMax = Integer.MIN_VALUE;

        for (int i = 0; i < globalRowIndex; i++) {
            for (int j = 0; j < globalColumnIndex; j++) {
                resetCheckedNodes();
                count = 0;


                if (j + 1 < globalColumnIndex)
                    searchGrid(input[i][j], input[i][j + 1],
                            i, j, input);


                if (count >= currentMax) {
                    currentMax = count;
                    resetResult(input[i][j], input);
                }
                resetCheckedNodes();
                count = 0;


                if (i + 1 < globalRowIndex)
                    searchGrid(input[i][j],
                            input[i + 1][j], i, j, input);


                if (count >= currentMax) {
                    currentMax = count;
                    resetResult(input[i][j], input);
                }
            }
        }
        displayResult(currentMax);
    }

    static String[][] generateColourGrid(int row, int column, String[] colours) {
        globalRowIndex = row;
        globalColumnIndex = column;
        checkedNode = new String[globalRowIndex][globalColumnIndex];
        result = new String[globalRowIndex][globalColumnIndex];
        final String[][] colourGrid = new String[row][column];

        for (int i = 0; i < row; i++) {

            for (int j = 0; j < column; j++) {

                final int colourIndex = random.nextInt(colours.length);
                colourGrid[i][j] = colours[colourIndex];

            }

        }

        return colourGrid;
    }

    public static void main(String[] args) {

        final String[][] colourGrid = generateColourGrid(12, 10,
                new String[]{"RED", "GREEN", "BLUE"});

        System.out.println("Generated Colour grid: \n");
        for (int i = 0; i < colourGrid.length; i++) {
            final String[] row = colourGrid[i];
            for (int j = 0; j < row.length; j++) {
                System.out.print(colourGrid[i][j] + " ");
            }
            System.out.println();
        }
        computeConnectingBlockOfNodes(colourGrid);
    }

}
