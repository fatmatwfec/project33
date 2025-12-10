import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class AirTable extends JFrame {
    GLCanvas glcanvas = new GLCanvas();
    private Animator animator;

    public AirTable() {
        super("The Air Hockey Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(new GameGLListener());
        animator = new FPSAnimator(10);
        animator.add(glcanvas);

        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(800, 500);
        setLocationRelativeTo(this);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}
