import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
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


    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(MIN_X, MAX_X, MIN_Y, MAX_Y, 1.0f, -1.0f);
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


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

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



