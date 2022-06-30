
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Maze extends JFrame {

    private int[][] values;
    private boolean[][] visited;
    private int startRow;
    private int startColumn;
    private ArrayList<JButton> buttonList;
    private int rows;
    private int columns;
    private boolean backtracking;
    private int algorithm;

    public Maze(int algorithm, int size, int startRow, int startColumn) {

        this.algorithm = algorithm;
        Random random = new Random();
        this.values = new int[size][];

        for (int i = 0; i < values.length; i++) {
            int[] row = new int[size];
            for (int j = 0; j < row.length; j++) {
                if (i > 1 || j > 1) {
                    row[j] = random.nextInt(8) % 7 == 0 ? Definitions.OBSTACLE : Definitions.EMPTY;
                } else {
                    row[j] = Definitions.EMPTY;
                }
            }
            values[i] = row;
        }

        values[0][0] = Definitions.EMPTY;
        values[size - 1][size - 1] = Definitions.EMPTY;
        this.visited = new boolean[this.values.length][this.values.length];
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.buttonList = new ArrayList<>();
        this.rows = values.length;
        this.columns = values.length;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    this.setLocationRelativeTo(null);
        GridLayout gridLayout = new GridLayout(rows, columns);
        this.setLayout(gridLayout);
        for (int i = 0; i < rows * columns; i++) {
            int value = values[i / rows][i % columns];
            JButton jButton = new JButton(String.valueOf(i));
            if (value == Definitions.OBSTACLE) {
                jButton.setBackground(Color.BLACK);
            } else {
                jButton.setBackground(Color.WHITE);
            }
            this.buttonList.add(jButton);
            this.add(jButton);
        }
        this.setVisible(true);
        this.setSize(Definitions.WINDOW_WIDTH, Definitions.WINDOW_HEIGHT);
        this.setResizable(false);
    }

    public void checkWayOut() {
        new Thread(() -> {
            boolean result = false;
            switch (this.algorithm) {
                case Definitions.ALGORITHM_DFS:                   
                    Stack<Point> stack = new Stack<Point>();
                    stack.push(new Point(startRow, startColumn));                   
                    result = runAlgorithm(stack);
                    break;
                    
                case Definitions.ALGORITHM_BFS:                   
                    Queue<Point> queue = new LinkedList<>();
                    queue.add(new Point(startRow, startColumn));
                    result = runAlgorithm(queue);                   
                    break;
            }

            JOptionPane.showMessageDialog(null,  result ? "FOUND SOLUTION" : "NO SOLUTION FOR THIS MAZE");

        }).start();
    }

    private boolean runAlgorithm(Collection<Point> collection) {

        while (!collection.isEmpty()) {

            Point point;
            if(collection instanceof Queue) {
                point = ((Queue<Point>) collection).remove();
            }else {
                point = ((Stack<Point>) collection).pop();
            }
            
            int x = point.x, y = point.y;

            if(!this.visited[x][y]) {
                setSquareAsVisited(x, y, true);
            }             

            collection.addAll(findNeighbors(x, y));

            if(x == this.values.length - 1 && y == this.values.length - 1)
                return true;
        }

        return false;
    }

    private List<Point> findNeighbors(int x, int y){

        List<Point> neighbors = new ArrayList<>();
      
        if(y < this.values.length - 1 && !this.visited[x][y + 1] && values[x][y + 1] == Definitions.EMPTY)
            neighbors.add(new Point(x, y + 1));       
        
        if(x < this.values.length - 1 && !this.visited[x + 1][y] && values[x + 1][y] == Definitions.EMPTY)
            neighbors.add(new Point(x + 1, y));
            
        if(y > 0 && !this.visited[x][y - 1] && values[x][y - 1] == Definitions.EMPTY) 
            neighbors.add(new Point(x, y - 1));
         
        if(x > 0 && !this.visited[x - 1][y] && values[x - 1][y] == Definitions.EMPTY)
            neighbors.add(new Point(x - 1, y));    
        
        return neighbors;
    }

    public void setSquareAsVisited(int x, int y, boolean visited) {
        try {
            if (visited) {
                if (this.backtracking) {
                    Thread.sleep(Definitions.PAUSE_BEFORE_NEXT_SQUARE * 5);
                    this.backtracking = false;
                }
                this.visited[x][y] = true;
                for (int i = 0; i < this.visited.length; i++) {
                    for (int j = 0; j < this.visited[i].length; j++) {
                        if (this.visited[i][j]) {
                            if (i == x && y == j) {
                                this.buttonList.get(i * this.rows + j).setBackground(Color.RED);
                            } else {
                                this.buttonList.get(i * this.rows + j).setBackground(Color.BLUE);
                            }
                        }
                    }
                }
            } else {
                this.visited[x][y] = false;
                this.buttonList.get(x * this.columns + y).setBackground(Color.WHITE);
                Thread.sleep(Definitions.PAUSE_BEFORE_BACKTRACK);
                this.backtracking = true;
            }
            if (!visited) {
                Thread.sleep(Definitions.PAUSE_BEFORE_NEXT_SQUARE / 4);
            } else {
                Thread.sleep(Definitions.PAUSE_BEFORE_NEXT_SQUARE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
