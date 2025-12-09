import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;

public class Game<AudioStream> extends JFrame implements ActionListener , MouseListener  {
    private Animator animator;
    private GLCanvas glcanvas;
    private GameListener listener = new GameGLListener();
    private AirTableGLListener AirTable = new AirTableGLListener();
    JPanel jPanel = new JPanel();
    JTextField UserName = new JTextField("");
    JLabel  UsernameTitle = new JLabel("                                Player Name : " );
    JLabel space = new JLabel("                                                        ");
    boolean onePlayer = false ;
    boolean TwoPlayer = false ;


    public static void main(String[] args) {
        new Game().animator.start();
        // ابدأ بالقائمة
    }

    public Game() {
        super("The Air Hockey Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        showStartWindow();
        glcanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getX() < 454 && e.getX() > 328 && e.getY() < 423 && e.getY() > 387) {
                    System.out.println("Switching to Game...");
                    new AirTable().setVisible(true);
                }
            }
        });
        animator = new FPSAnimator(10);
        animator.add(glcanvas);

        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(800, 500);
        setLocationRelativeTo(this);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }

   private void showStartWindow() {
       jPanel.setLayout(new GridLayout(1 , 3, 50, 50));
       jPanel.add(UsernameTitle);
       jPanel.add(UserName);
       jPanel.add(space);
       this.add(jPanel, BorderLayout.NORTH);
       glcanvas.addMouseListener(new GameGLListener());
       glcanvas.addMouseListener(this);
       glcanvas.addMouseListener(new AirTableGLListener());
   }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {

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


