import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class Board2 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 512, 512);
        JPanel pn = new JPanel(){
            @Override
            public void paint(Graphics g) {
                boolean white = true;
                for(int i= 0; i<8; i++){
                    for (int j = 0; j<8; j++){
                        if(white) {
                            g.setColor(Color.white);
                        }
                        else{
                            g.setColor(Color.black);
                        }
                        g.fillRect(i *64, j*64, 64, 64);
                        white = !white;
                    }
                    white = !white;

                }
            }
        };
        frame.add(pn);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}
