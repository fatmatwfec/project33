import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class StartWindow implements GLEventListener,  MouseListener {
    String textureNames[] = {"air hockey start window.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    protected String assetsFolderName = ".idea\\picture";
    GL gl;
    GLAutoDrawable glAutoDrawable;
    static Clip clip;
    File f = new File(".idea\\Sound\\Air hockey sound.wav");

    private static final int MAX_X = 400;
    private static final int MIN_X = -400;
    private static final int MAX_Y = 250;
    private static final int MIN_Y = -250;


    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(MIN_X, MAX_X, MIN_Y, MAX_Y, -1.0, 1.0);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        onSound();
        //number of textures,array to hold the indeces
        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(".idea\\picture" + "//" + textureNames[i], true);
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
        }
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);  //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        DrawBackground(gl);
    }
    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);    // Turn Blending On

//        gl.glColor3f(0, 0.5f, 0.5f);
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(MIN_X, MIN_Y, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(MAX_X, MIN_Y, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(MAX_X, MAX_Y, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(MIN_X, MAX_Y, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
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
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    boolean sound = true ;
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > 7 && e.getX() < 51 && e.getY() > 9 && e.getY() < 52 && sound == true) {
            onSound();
            sound = false;
        }
        if (e.getX() > 65 && e.getX() < 105 && e.getY() > 9 && e.getY() < 52 && sound == false) {
            clip.stop();
            clip.close();
            sound = true;
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
}



