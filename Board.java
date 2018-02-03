import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Board  extends JPanel implements Runnable, MouseListener
{
    boolean ingame = false;
    private Dimension d;
    int BOARD_WIDTH=600;
    int BOARD_HEIGHT=600;
    int x = 0;
    BufferedImage img;
    String message = "Click Board to Start";
    private Thread animator;
    int[][] cells = new int[6][7];
    int xCell;
    int yCell;
    boolean p1turn = true;
    boolean redPlayer = false;
    boolean yellowPlayer = false;
    
    public Board()
    {
        addKeyListener(new TAdapter());
        addMouseListener(this);
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);

                 
        try {
            img = ImageIO.read(this.getClass().getResource("connect4logo.png"));
        } catch (IOException e) {
            System.out.println("Image could not be read");
        // System.exit(1);
        }
         
        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }

        setDoubleBuffered(true);
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        //g.fillOval(x,y,r,r);

        g.setColor(Color.blue);
        g.fillRect(0,0,600,600);
       
        if(!ingame && redPlayer == true){
        g.drawImage(img,100,100,null);
        Font small = new Font("Helvetica", Font.BOLD, 40);
        FontMetrics metr = this.getFontMetrics(small);
        g.setColor(Color.red);
        g.setFont(small);
        message = "RED WINS!";
        g.drawString(message, d.width/2 - 100, d.height/2);
        Font small2 = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metr2 = this.getFontMetrics(small2);
        g.setColor(Color.white);    
        g.setFont(small2);
        message = "Press SPACEBAR to Play";
        g.drawString(message, d.width/2 - 170, d.height/2 + 100);
        }
        
        if(!ingame && yellowPlayer == true){
        g.drawImage(img,100,100,null);
        Font small = new Font("Helvetica", Font.BOLD, 40);
        FontMetrics metr = this.getFontMetrics(small);
        g.setColor(Color.yellow);
        g.setFont(small);
        message = "YELLOW WINS!";
        g.drawString(message, d.width/2 - 140, d.height/2);
        Font small2 = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metr2 = this.getFontMetrics(small2);
        g.setColor(Color.white);    
        g.setFont(small2);
        message = "Press SPACEBAR to Play";
        g.drawString(message, d.width/2 - 170, d.height/2 + 100);
        }
       
        if(!ingame && !yellowPlayer && !redPlayer){
        g.drawImage(img,100,100,null);
        Font small = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metr = this.getFontMetrics(small);
        g.setColor(Color.white);    
        g.setFont(small);
        message = "Press SPACEBAR to Play";
        g.drawString(message, d.width/2 - 170, d.height/2);
        }
        
        if (ingame) {
        int x = 40;
        int y = 20;
        int radius = 50;
        int space = 75;
        g.setColor(Color.gray);
        for(int r=0; r<cells.length; r++){
            g.setColor(Color.gray);
            for(int c = 0; c<cells[0].length; c++){
                if(cells[r][c] == 0){
                    g.setColor(Color.gray);
                }
                if(cells[r][c] == 1){
                    g.setColor(Color.red);
                }
                if(cells[r][c] == 2){
                    g.setColor(Color.yellow);
                }
                
                g.fillOval(x,y,radius,radius); 
                x += space;

            }
            y += space;
            x=40;
        }   
        
        for(int r=0; r < cells.length; r++){
            for(int c=0; c < 4; c++){
                if(cells[r][c] == 1 && cells[r][c+1] == 1 && cells[r][c+2] == 1 && cells[r][c+3] == 1){
                    ingame = false;
                    redPlayer = true;
                    message = "Red Player Wins!";
                }
                if(cells[r][c] == 2 && cells[r][c+1] == 2 && cells[r][c+2] == 2 && cells[r][c+3] == 2){
                    ingame = false;
                    yellowPlayer = true;
                    message = "Yellow Player Wins!";
                }
            }
        }

        for(int r=0; r < 3; r++){
            for(int c=0; c < cells[0].length; c++){
                if(cells[r][c] == 1 && cells[r+1][c] == 1 && cells[r+2][c] == 1 && cells[r+3][c] == 1){
                    ingame = false;
                    redPlayer = true;
                    message = "Red Player Wins!";
                }
                if(cells[r][c] == 2 && cells[r+1][c] == 2 && cells[r+2][c] == 2 && cells[r+3][c] == 2){
                    ingame = false;
                    yellowPlayer = true;
                    message = "Yellow Player Wins!";
                }
            }
        }
        
        for(int r=0; r < 3; r++){
            for(int c=0; c < 4; c++){
                if(cells[r][c] == 1 && cells[r+1][c+1] == 1 && cells[r+2][c+2] == 1 && cells[r+3][c+3] == 1){
                    ingame = false;
                    redPlayer = true;
                    message = "Red Player Wins!";
                }
                if(cells[r][c] == 2 && cells[r+1][c+1] == 2 && cells[r+2][c+2] == 2 && cells[r+3][c+3] == 2){
                    ingame = false;
                    yellowPlayer = true;
                    message = "Yellow Player Wins!";
                }
            }
        }
        
        for(int r=5; r > 2; r--){
            for(int c=0; c < 4; c++){
                if(cells[r][c] == 1 && cells[r-1][c+1] == 1 && cells[r-2][c+2] == 1 && cells[r-3][c+3] == 1){
                    ingame = false;
                    redPlayer = true;
                    message = "Red Player Wins!";
                }
                if(cells[r][c] == 2 && cells[r-1][c+1] == 2 && cells[r-2][c+2] == 2 && cells[r-3][c+3] == 2){
                    ingame = false;
                    yellowPlayer = true;
                    message = "Yellow Player Wins!";
                }
            }
        }
        
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

        }

        public void keyPressed(KeyEvent e) {
            //System.out.println( e.getKeyCode());
            // message = "Key Pressed: " + e.getKeyCode();
            int key = e.getKeyCode();
            if(key==32){
                for(int r = 0; r < cells.length;r++){
                    for(int c = 0; c < cells[0].length; c++){
                        cells[r][c] = 0;
                    }
                }
                yellowPlayer = false;
                redPlayer = false;
                message = "Press SPACEBAR to Play";
                ingame = true;
            }

        }
    }

    public void mousePressed(MouseEvent e) {
        
        int x = e.getX();        
        int y = e.getY();
        
        int xCell = (x/80) + 1;
        int yCell = (y/75) + 1;
        
        if(p1turn == true){
            p1turn=false;
            //cells[yCell-1][xCell-1] = 1;
            int highI = 0;
            for(int i = 0; i < cells.length; i++){   
                if(cells[i][xCell-1] == 0){
                    highI = i;
                }
            }
            
            if(cells[highI][xCell-1] == 0)
                cells[highI][xCell-1] = 1;
        }else{
            p1turn=true;
            
            int highI = 0;
            for(int i = 0; i < cells.length; i++){   
                if(cells[i][xCell-1] == 0){
                    highI = i;
                }
            }
            
            if(cells[highI][xCell-1] == 0)
                cells[highI][xCell-1] = 2;
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        int animationDelay = 50;
        long time = 
            System.currentTimeMillis();
        while (true) {//infinite loop
            // spriteManager.update();
            repaint();
            try {
                time += animationDelay;
                Thread.sleep(Math.max(0,time - 
                        System.currentTimeMillis()));
            }catch (InterruptedException e) {
                System.out.println(e);
            }//end catch
        }//end while loop


    }//end of run
}//end of class
