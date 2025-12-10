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
            return "player1 is win";
        else if(score2==3)
            return "player2 is win";
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

    }

    @Override
    public void keyReleased(KeyEvent e) {

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

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glDisable(GL.GL_TEXTURE_2D);


        gl.glEnable(GL.GL_TEXTURE_2D);
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
        // win massage
        if (hasWinner()) {
            System.out.println(winner());

        }
        drawScore();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
    public void drawBall(GL gl,double xBall,double yBall,double size){



        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);


        gl.glBindTexture(GL.GL_TEXTURE_2D, ballTexture[0]);



        gl.glBegin(GL.GL_QUADS);

        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3d(xBall, yBall, 0.0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3d(xBall+size, yBall, 0.0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3d(xBall+size, yBall+size, 0.0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3d(xBall, yBall+size, 0.0);
        gl.glEnd();



        gl.glDisable(GL.GL_BLEND);

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