import Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.glu.GLU;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static java.lang.System.in;
import static java.lang.System.setOut;

import javax.media.opengl.GLEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.media.opengl.GLCanvas;



public class GameGLListener implements GLEventListener, KeyListener, MouseListener {
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
  
   String textureNames[] = {"air hockey start window.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    GL gl;
    GLAutoDrawable glAutoDrawable;
    static Clip clip;
    File f = new File("D:\\New folder\\project33\\.idea\\Sound\\Air hockey sound.wav");
  
  int X =0  , Y= 0 ;
    GLCanvas glCanvas = new GLCanvas();
      AirTableGLListener AirTable = new AirTableGLListener();



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
        // collision with players
        if( Math.abs(xplayer1-xBall)<45 ||  Math.abs(xplayer2-xBall)<45){
            dxBall=-dxBall;
        }
        if( Math.abs(yplayer1-yBall)<45 ||  Math.abs(yplayer2-yBall)<45 ){
            dyBall=-dyBall;
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
    public void init(GLAutoDrawable glAutoDrawable) {
     
        
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(MIN_X, MAX_X, MIN_Y, MAX_Y, 1.0f, -1.0f);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
      
       onSound();
        //number of textures,array to hold the indeces
        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//              mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }


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
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
      
       DrawBackground(gl);
//          DrawGraph(gl);
//          DrawSprite(gl);
      
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
        drawPlayer(gl, -350, 0, 1.0f, 0.0f, 0.0f);
        drawPlayer(gl, 350, 0, 0.0f, 0.0f, 1.0f);
        
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
        gl.glLineWidth(3);

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
        gl.glLineWidth(3);

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
      
       public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);    // Turn Blending On

//        gl.glColor3f(0, 0.5f, 0.5f);
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawSprite(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);    // Turn Blending On

//      gl.glColor3f(0, 0, 0);
        gl.glPushMatrix();
//      gl.glTranslated( 0, 0, 0);
        gl.glScaled(0.5, 0.5, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -0.5f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -0.5f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }





  
   public void mouseClicked(MouseEvent e) {
    }

    void onSound() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // أمر التكرار
            clip.start();
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > 7 && e.getX() < 51 && e.getY() > 9 && e.getY() < 52) {
            onSound();
        } else if (e.getX() > 65 && e.getX() < 105 && e.getY() > 9 && e.getY() < 52) {
            clip.stop();
            clip.close();
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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
    }

//    public static void main(String[] args) {
//        JFrame window = new JFrame("Football Field");
//        window.setSize(820, 540);
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        GLCanvas canvas = new GLCanvas();
//        GameGLListener listener = new GameGLListener();
//        canvas.addGLEventListener(listener);
//        window.add(canvas);
//        window.setVisible(true);
//
//
//    }

}



