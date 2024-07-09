import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class MyPanel extends JPanel{
    final int maxCol = 20;
    final int maxRow = 15;
    final int blockSize = 50;
    final int screenWidth = blockSize * maxCol + 50;
    final int screenHeight = blockSize * maxRow;
    int step = 0;
    Block[][] blocks = new Block[maxCol][maxRow];
    Block startBlock, goalBlock, currBlock;

    boolean goalReached = false;

    public MyPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        //Placing Blocks
        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow){
            blocks[col][row] = new Block(col, row, this);
            this.add(blocks[col][row]);
            col++;

            if(col == maxCol){
                col = 0;
                row ++;
            }
        }

        setStartBlock(0,0);
        setGoalBlock(15, 12);


        try {
            setSolidBlock();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    private void setStartBlock(int col, int row){
        blocks[col][row].setAsStart();
        startBlock = blocks[col][row];
        currBlock = startBlock;
    }

    private void setGoalBlock(int col, int row){
        blocks[col][row].setAsGoal();
        goalBlock = blocks[col][row];
    }
    private void setSolidBlock() throws IOException {
        FileReader file = new FileReader("src/SolidList");
        Scanner read = new Scanner(file);
        while (read.hasNext()){
            String line = read.nextLine();
            String[] s = line.split(" ");
            int row = Integer.parseInt(s[0]);
            int col = Integer.parseInt(s[1]);
            blocks[row][col].setAsSolid();
        }
        file.close();
    }

    public void clearPath(){
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                Block curr = blocks[i][j];
                if(!curr.goal && !curr.start && !curr.solid){
                    curr.setBackground(Color.white);
                    curr.setForeground(Color.black);
                    curr.checked = false;
                    curr.neighbours = new ArrayList<>();
                }
            }
        }
        System.out.println("Cleared Path!");
    }
    public void clearAll(){
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                Block curr = blocks[i][j];
                if(!curr.goal && !curr.start){
                    curr.setBackground(Color.white);
                    curr.setForeground(Color.black);
                    curr.checked = false;
                    curr.solid = false;
                    curr.neighbours = new ArrayList<>();
                }
            }
        }
        System.out.println("Cleared All!");
    }
    private  void reconstructPath(List<Block> path) {
        for (Block b : path) {
            b.setBackground(Color.green);
        }
    }

    public List<Block> shortestPath(){
        addEdges();

        Set<Block> visited = new HashSet<>();
        Queue<List<Block>> queue = new LinkedList<>();
        queue.offer(Arrays.asList(startBlock));
        visited.add(startBlock);

        while (!queue.isEmpty()) {
            List<Block> path = queue.poll();
            Block current = path.get(path.size() - 1);
            goalReached = current == goalBlock;

            if (goalReached) {
                System.out.println("Path Found!");
                reconstructPath(path);
                return path;
            }

            for (Block neighbor :
                    current.neighbours) {
                if (!visited.contains(neighbor) && !neighbor.solid) {
                    neighbor.setChecked();
                    visited.add(neighbor);
                    List<Block> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.offer(newPath);
                }
            }
        }

        // If no path is found
        System.out.println("No path found!");
        return Collections.emptyList();
    }

    private void addEdges(){
        for (int i = 0; i < maxCol; i++) {
            for (int j = 0; j < maxRow; j++) {
                 Block curr = blocks[i][j];

                //open North block
                if(j-1 >= 0){
                    curr.neighbours.add(blocks[i][j-1]);
                    blocks[i][j-1].neighbours.add(curr);
                }

                //open West block
                if(i-1 >= 0){
                    curr.neighbours.add(blocks[i-1][j]);
                    blocks[i-1][j].neighbours.add(curr);
                }

                //open South block
                if(j+1 < maxRow){
                    curr.neighbours.add(blocks[i][j+1]);
                    blocks[i][j+1].neighbours.add(curr);
                }

                //open East block
                if(i+1 < maxCol){
                    curr.neighbours.add(blocks[i+1][j]);
                    blocks[i+1][j].neighbours.add(curr);
                }
            }
        }
    }

    private int[] getIndex(Block curr) {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if(blocks[i][j] == curr)
                    return new int[]{i, j};
            }
        }
        return new int[]{-1,-1};
    }
}
