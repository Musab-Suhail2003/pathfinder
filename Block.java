import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Block extends JButton implements ActionListener {
    MyPanel mp;
    int col;
    int row;

        ArrayList<Block> neighbours = new ArrayList<>(4);
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;

    static int count = 0;
    public Block(int col, int row, MyPanel mp) {
        this.col = col;
        this.row = row;
        this.mp = mp;

        setBackground(Color.white);
        setForeground(Color.black);
        addActionListener(this);
        setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!start && !goal && !solid) {
            System.out.println(col + " " + row);
            setAsSolid();
            open = false;
            checked = false;
        } else if (!start && !goal && solid) {
            solid = false;
            setBackground(Color.white);
            setForeground(Color.black);
        }
    }

    public void setAsStart(){
        setBackground(Color.BLUE);
        setForeground(Color.white);
        setFont(new Font("ASD", Font.PLAIN, 8));
        setText("Start");
        start = true;
    }
    public void setAsGoal(){
        setBackground(Color.GREEN);
        setForeground(Color.white);
        setFont(new Font("ASD", Font.PLAIN, 8));
        setText("Goal");
        goal = true;
    }
    public void setAsSolid(){
        setBackground(Color.black);
        setForeground(Color.black);
        solid = true;
    }

    public void setChecked(){
        if(!start && !goal && !solid){
            setBackground(Color.yellow);
            setForeground(Color.black);
        }
        checked = true;
        repaint();
    }
}
