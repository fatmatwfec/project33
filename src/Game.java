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

public class Game extends JFrame implements ActionListener , MouseListener  {
    private Animator animator;
    private GLCanvas glcanvas;
    private StartWindow Start = new StartWindow();
    private GameGLListener AirTable = new GameGLListener();
    JPanel jPanel = new JPanel();
    JTextField UserName = new JTextField("");
    JLabel  UsernameTitle = new JLabel("                                Player Name : " );
    JLabel space = new JLabel("                                                        ");
    JButton Exit = new JButton();



    public static void main(String[] args) {
        new Game();
        // ابدأ بالقائمة
    }

    public Game() {
        super("The Air Hockey Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(Start);
        glcanvas.addKeyListener(AirTable);
        glcanvas.setFocusable(true);
        glcanvas.requestFocusInWindow();

        showStartWindow();

        glcanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getX() < 454  && e.getX() > 331 && e.getY() < 427 && e.getY() > 387) {
                    glcanvas.removeGLEventListener(Start);
                    glcanvas.addGLEventListener(AirTable);
//                  glcanvas.removeMouseListener(this);
                    glcanvas.requestFocusInWindow();
                }
            }
        });
        animator = new FPSAnimator(10);
        animator.add(glcanvas);
        animator.start();

        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(800, 500);
        setLocationRelativeTo(this);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }

   private void showStartWindow() {
       jPanel.setLayout(new GridLayout(1, 3, 50, 50));
       jPanel.add(UsernameTitle);
       jPanel.add(UserName);
       jPanel.add(space);
       this.add(jPanel, BorderLayout.NORTH);
       glcanvas.addMouseListener(new StartWindow());
       glcanvas.addMouseListener(this);
   }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX()+" "+e.getY());
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


