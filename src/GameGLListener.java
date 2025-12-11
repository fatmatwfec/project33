import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;
import javax.swing.JOptionPane;



   
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import com.sun.opengl.util.j2d.TextRenderer;

public class GameGLListener implements GLEventListener, KeyListener {
    private static final int MAX_X = 400;
    private static final int MIN_X = -400;
    private static final int MAX_Y = 250;
    private static final int MIN_Y = -250;
    public BitSet keyBits = new BitSet(256);
    int xplayer1 = -350;
    int yplayer1 = 0;
    int xplayer2 = 350;
    int yplayer2 = 0;
    boolean winnerShown = false;

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_A)) {
            if (xplayer1 > MIN_X + 40) {
                xplayer1 -= 10;
            }
        }
        if (isKeyPressed(KeyEvent.VK_D)) {
            if (xplayer1 < -40) {
                xplayer1 += 10;
            }
        }
        if (isKeyPressed(KeyEvent.VK_S)) {
            if (yplayer1 > MIN_Y + 40) {
                yplayer1 -= 10;
            }
        }
        if (isKeyPressed(KeyEvent.VK_W)) {
            if (yplayer1 < MAX_Y - 40) {
                yplayer1 += 10;
            }
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (yplayer2 < MAX_Y - 40) {
                yplayer2 += 10;
            }
        }
        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (xplayer2 > 40) {
                xplayer2 -= 10;
            }
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (xplayer2 < MAX_X - 40) {
                xplayer2 += 10;
            }
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (yplayer2 > MIN_Y + 40) {
                yplayer2 -= 10;
            }
        }


    }
    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

//public BitSet keyBits = new BitSet(256);
   
    private double xBall=0;
    private double yBall=0;
    private double dxBall= 8;
    private double dyBall= 6;
    private final double ballSize=30;



    private int score1=0;
    private int score2=0;
    String textureName = "ball4.png";
    TextureReader.Texture texture1;
    int[] ballTexture = new int[1];

    private TextRenderer text = new TextRenderer(new Font("SansSerif", Font.BOLD, 10));

    public void updateBall(){
        xBall += dxBall;
        yBall += dyBall;
        //تغيير الاتجاه لو الكرة خبطت فى الحواف

        if(xBall+ballSize >= MAX_X || xBall<= MIN_X){
            dxBall = -dxBall;
        }
        if(yBall+ballSize>= MAX_Y || yBall <= MIN_Y){
            dyBall = -dyBall;
        }
//        // collision with players
        if (Math.sqrt(Math.pow(xplayer1-xBall,2)+Math.pow(yplayer1-yBall,2))<30){
            dxBall=-dxBall;
        }
        if (Math.sqrt(Math.pow(xplayer2-xBall,2)+Math.pow(yplayer2-yBall,2))<30){
            dxBall=-dxBall;
        }

    }

    public void player1MakeGoal(){
        score1++;
        System.out.println("player1 Make Goal");
    }
    public void player2MakeGoal(){
        score2++;
        System.out.println("player2 Make Goal");
    }
    public boolean hasWinner(){
        if(score1==3)
            return true;
        else if (score2==3)
            return true;
        else
            return false;
    }
    public String winner(){
        if(score1==3)
            return "player1 is the winner :)";
        else if(score2==3)
            return "player2 is the winner :)";
        else
            return null;
    }
    public void reset(){
        xBall=0;
        yBall=0;

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(MIN_X, MAX_X, MIN_Y, MAX_Y, -1.0, 1.0);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);


        gl.glGenTextures(1, ballTexture, 0);

        try {
            texture1 = TextureReader.readTexture("assets//" + textureName , true);
            gl.glBindTexture(GL.GL_TEXTURE_2D, ballTexture[0]);


            new GLU().gluBuild2DMipmaps(
                    GL.GL_TEXTURE_2D,
                    GL.GL_RGBA,
                    texture1.getWidth(), texture1.getHeight(),
                    GL.GL_RGBA,
                    GL.GL_UNSIGNED_BYTE,
                    texture1.getPixels()
            );
        } catch( IOException e ) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

        GL gl=glAutoDrawable.getGL();
        handleKeyPress();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glDisable(GL.GL_TEXTURE_2D);


        
        drawRect(gl,-400,0,100,MAX_Y,0.0f, 0.5f, 0.0f);
        drawRect(gl,-300,0,100,MAX_Y,0.5f, 1.0f, 0.5f);
        drawRect(gl,-200,0,100,MAX_Y,0.0f, 0.5f, 0.0f);
        drawRect(gl,-100,0,100,MAX_Y,0.5f,1.0f,0.5f);
        drawRect(gl,0,0,100,MAX_Y,0.0f, 0.5f, 0.0f);
        drawRect(gl,100,0,100,MAX_Y,0.5f,1.0f,0.5f);
        drawRect(gl,200,0,100,MAX_Y,0.0f, 0.5f, 0.0f);
        drawRect(gl,300,0,100,MAX_Y,0.5f,1.0f,0.5f);
        drawRect(gl,-400,MIN_Y,100,250,0.0f, 0.5f, 0.0f);
        drawRect(gl,-300,MIN_Y,100,250,0.5f, 1.0f, 0.5f);
        drawRect(gl,-200,MIN_Y,100,250,0.0f, 0.5f, 0.0f);
        drawRect(gl,-100,MIN_Y,100,250,0.5f,1.0f,0.5f);
        drawRect(gl,0,MIN_Y,100,250,0.0f, 0.5f, 0.0f);
        drawRect(gl,100,MIN_Y,100,250,0.5f,1.0f,0.5f);
        drawRect(gl,200,MIN_Y,100,250,0.0f, 0.5f, 0.0f);
        drawRect(gl,300,MIN_Y,100,250,0.5f,1.0f,0.5f);


        drawFieldBorder(gl);
        drawCenterCircle(gl, 0, 0, 50);
        drawGoals(gl);
        drawPlayer(gl, xplayer1, yplayer1, 1.0f, 0.0f, 0.0f);
        drawPlayer(gl, xplayer2, yplayer2, 0.0f, 0.0f, 1.0f);
        
        
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        drawBall(gl,xBall,yBall,30);
        //update ball position
        updateBall();


        // make goal
        if(xBall <=-350 && yBall +ballSize >=-100 && yBall +ballSize <= 100){
            player2MakeGoal();
            reset();
        }
        if(xBall+ballSize>=350 && yBall +ballSize >=-100 && yBall +ballSize <= 100){
            player1MakeGoal();
            reset();
        }

        if (hasWinner()&& !winnerShown) {
            String winnerMessage = winner();
            System.out.println(winnerMessage);
            JOptionPane.showMessageDialog(
                    null,
                    winnerMessage,
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE
            );

            System.exit(0);


            winnerShown = true;
        }
        drawScore();
    }

    public void drawRect( GL gl,int x,int y,int width,int height,float r,float g,float b) {
        gl.glColor3f(r,g,b);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2i(x, y);
        gl.glVertex2i(x+width, y);
        gl.glVertex2i(x+width, y + height);
        gl.glVertex2i(x, y + height);
        gl.glEnd();
    }

    public void drawFieldBorder(GL gl) {
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glLineWidth(3);

        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2i(MIN_X, MIN_Y);
        gl.glVertex2i(MIN_X, MAX_Y);
        gl.glVertex2i(MAX_X, MAX_Y);
        gl.glVertex2i(MAX_X, MIN_Y);
        gl.glEnd();
    }

    public void drawCenterCircle(GL gl, int centerX, int centerY, int radius) {
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glPointSize(3.0f);
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int angle = 0; angle < 360; angle++) {
            double rad = Math.toRadians(angle);
            int x = (int)(centerX + radius * Math.cos(rad));
            int y = (int)(centerY + radius * Math.sin(rad));
            gl.glVertex2i(x, y);
        }
        gl.glEnd();
    }


    public void drawGoals(GL gl) {
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glPointSize(3.0f);
        // Left Goal
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2i(-400, -100);
        gl.glVertex2i(-400, 100);
        gl.glVertex2i(-350, 100);
        gl.glVertex2i(-350, -100);
        gl.glEnd();

        // Right Goal
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2i(350, -100);
        gl.glVertex2i(350, 100);
        gl.glVertex2i(400, 100);
        gl.glVertex2i(400, -100);
        gl.glEnd();
    }

    public void drawPlayer(GL gl, int centerX, int centerY, float r, float g, float b) {
        int innerRadius = 15;
        int outerRadius = 22;

        // outer ring
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glLineWidth(3);
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int angle = 0; angle < 360; angle++) {
            double rad = Math.toRadians(angle);
            int x = (int)(centerX + outerRadius * Math.cos(rad));
            int y = (int)(centerY + outerRadius * Math.sin(rad));
            gl.glVertex2i(x, y);
        }
        gl.glEnd();

        //player
        gl.glColor3f(r, g, b);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex2i(centerX, centerY);
        for (int angle = 0; angle <= 360; angle++) {
            double rad = Math.toRadians(angle);
            int x = (int)(centerX + innerRadius * Math.cos(rad));
            int y = (int)(centerY + innerRadius * Math.sin(rad));
            gl.glVertex2i(x, y);
        }
        gl.glEnd();
    }






    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
    public void drawBall(GL gl,double xBall,double yBall,double size){



//        gl.glEnable(GL.GL_BLEND);
//        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
//
//
//        gl.glBindTexture(GL.GL_TEXTURE_2D, ballTexture[0]);
//
//
//
//        gl.glBegin(GL.GL_QUADS);
//
//        gl.glTexCoord2f(0.0f, 0.0f);
//        gl.glVertex3d(xBall, yBall, -1.0);
//        gl.glTexCoord2f(1.0f, 0.0f);
//        gl.glVertex3d(xBall+size, yBall, -1.0);
//        gl.glTexCoord2f(1.0f, 1.0f);
//        gl.glVertex3d(xBall+size, yBall+size, -1.0);
//        gl.glTexCoord2f(0.0f, 1.0f);
//        gl.glVertex3d(xBall, yBall+size, -1.0);
//        gl.glEnd();
//
//
//
//        gl.glDisable(GL.GL_BLEND);

        gl.glColor3f(0.0f,0.0f,0.0f);

        gl.glBegin(GL.GL_POLYGON);
        for ( double theta=0 ; theta< 2 * Math.PI; theta += Math.PI / 180) {

            double x = xBall+ ballSize/2 * Math.cos(theta);
           double y =yBall+ ballSize/2 * Math.sin(theta);
            gl.glVertex2d(x , y );
        }
        gl.glEnd();

    }
    public void drawScore(){
        text.beginRendering(MAX_X, MAX_Y);
        text.setColor(Color.red);
        text.draw("player1 : "+String.valueOf(score1), 120, 235);
        text.setColor(Color.blue);
        text.draw("player2 : "+String.valueOf(score2), 210, 235);
        text.endRendering();
    }
}



