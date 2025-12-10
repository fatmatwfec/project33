import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.media.opengl.GLCanvas;



public class GameGLListener implements GLEventListener, KeyListener {
    private static final int MAX_X = 400;
    private static final int MIN_X = -400;
    private static final int MAX_Y = 250;
    private static final int MIN_Y = -250;
    private int xPlayer1 = -350;
    private int yPlayer1 = 0;
    private int xPlayer2 = 350;
    private int yPlayer2 = 0;


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
//        if( Math.abs(xplayer1-xBall)<45 ||  Math.abs(xplayer2-xBall)<45){
//            dxBall=-dxBall;
//        }
//        if( Math.abs(yplayer1-yBall)<45 ||  Math.abs(yplayer2-yBall)<45 ){
//            dyBall=-dyBall;
//        }
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
        drawPlayer(gl, xPlayer1, yPlayer1, 1.0f, 0.0f, 0.0f);
        drawPlayer(gl, xPlayer2, yPlayer2, 0.0f, 0.0f, 1.0f);
        
        
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



