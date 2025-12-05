import javax.media.opengl.GLEventListener;
import java.awt.event.KeyListener;

public class GameGLListener implements GLEventListener, KeyListener {
    private double xBall=0;
    private double yBall=0;
    private double dxBall=0.02;
    private double dyBall=0.01;
    private double xmax=350;
    private double xmin=-350;
    private double ymax=350;
    private double ymin=-350;
    private int score1=0;
    private int score2=0;
    public void updateball(){
        xBall += dxBall;
        yBall += dyBall;
        //تغيير الاتجاه لو الكرة خبطت فى الحواف
        if(xBall >= xmax ||xBall <= xmin){
            dxBall = -dxBall;
        }
        if(yBall >= ymax ||yBall <= ymin){
            dyBall = -dyBall;
        }
    }

    public void player1MakeGoal(){
        score1++;
    }
    public void player2MakeGoal(){
        score2++;
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
}
