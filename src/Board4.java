import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.util.ArrayList;
import java.util.HashMap;

public class Board4 {
    public static final int N_ROWS = 21;
    public static final int N_COLS = 21;
//    public static final Color TwoWordBonusColor = new Color(0x66BB66);
//    public static final Color TwoLetterBonusColor = new Color(0xCCBB66);

    public Board4() {
        JFrame frame = new JFrame("Scrabble");
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        canvas = new ScrabbleCanvas();
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }


    public void setSquareLetter(int row, int col, String letter) {
        canvas.setSquareLetter(row, col, letter);

    }

    public String getSquareLetter(int row, int col) {
        return canvas.getSquareLetter(row, col);
    }

    public void setSquareColor(int row, int col, Color color) {
        canvas.setSquareColor(row, col, color);
    }

    public Color getSquareColor(int row, int col) {
        return canvas.getSquareColor(row, col);
    }

    public void setKeyColor(String label, Color color) {
        canvas.setKeyColor(label, color);
    }

    public Color getKeyColor(String label) {
        return canvas.getKeyColor(label);
    }

    public void setCurrentRow(int row) {
        canvas.setCurrentRow(row);
    }

    public int getCurrentRow() {
        return canvas.getCurrentRow();
    }

    public void showMessage(String msg) {
        canvas.showMessage(msg);
    }

    public void addEnterListener(ScrabbleEventListener listener) {
        canvas.addEnterListener(listener);
    }

    private ScrabbleCanvas canvas;

}

    class ScrabbleCanvas extends JComponent implements KeyListener, MouseListener {

        public ScrabbleCanvas() {
            setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
            initScrabbleGrid();
            initScrabbleKeys();
            message = "";
            row = 0;
            col = 0;
            listeners = new ArrayList<ScrabbleEventListener>();
            setFocusable(true);
            requestFocusInWindow();
            addKeyListener(this);
            addMouseListener(this);
        }

        public void setSquareLetter(int row, int col, String letter){
            grid[row][col].setLetter(letter);
            repaint();
        }

        public String getSquareLetter(int row, int col){
            return grid[row][col].getLetter();
        }

        public void setSquareColor(int row, int col, Color color){
            grid[row][col].setColor(color);
            repaint();
        }

        public Color getSquareColor(int row, int col){
            return grid[row][col].getColor();
        }

        public void setKeyColor(String label, Color color){
            keys.get(label).setColor(color);
            repaint();
        }

        public Color getKeyColor(String label){
            return keys.get(label).getColor();
        }

        public void setCurrentRow(int row){
            this.row = row;
            this.col = 0;
            for (int col = 0; col<N_COLS; col++){
                setSquareLetter(row, col, "");
                setSquareColor(row, col, UNKNOWN_COLOR);
            }
            repaint();
        }

        public int getCurrentRow(){
            return row;
        }

        public void showMessage(String msg){
            message = msg;
            repaint();
        }

        public void addEnterListener(ScrabbleEventListener listener){
           this.listeners.add(listener);
        }


        @Override
        public void paintComponent(Graphics g){
            for(int row = 0; row <N_ROWS; row++) {
                for (int col = 0; col <N_COLS; col++){
                    grid[row][col].paint(g);
                }
            }

            for(String label : keys.keySet()){
                keys.get(label).paint(g);
            }
            g.setColor(Color.BLACK);
            g.setFont(Font.decode(MESSAGE_FONT));
            FontMetrics fm = g.getFontMetrics();
            int tx = (CANVAS_WIDTH - fm.stringWidth(message)) / 2;
            g.drawString (message, tx, MESSAGE_Y);
        }

        @Override
        public void keyPressed(KeyEvent e){

        }

        @Override
        public void keyReleased(KeyEvent e){

        }

        @Override
        public void keyTyped(KeyEvent e){
            keyAction(e.getKeyChar());

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int b = 0;
            int a = 0;
            int c = e.getX();
            int d =e.getY();
            System.out.println(c + " " + d);
            String key = findKey(e.getX(), e.getY());
            if (key!= null){
                char ch = key.charAt(0);
                if (key.equals("DELETE")){
                    ch = DELETE;
                }
                else if (key.equals("ENTER")){
                    ch = ENTER;
                }
                keyAction(ch);
            }
            else if ((521 <= c) && (c <= 1379)){
                if((32 <= d) && (d <= 890)){
                    b = (int)((c-521)/41);
                    a = (int)((d-32)/41);
                    row = a;
                    col = b;

                }
            }


        }

        @Override
        public void mouseEntered(MouseEvent e){

        }

        @Override
        public void mouseExited(MouseEvent e){

        }

        @Override
        public void mousePressed(MouseEvent e){

        }

        @Override
        public void mouseReleased(MouseEvent e){

        }

        private void initScrabbleGrid(){
            grid = new ScrabbleSquare[N_ROWS][N_COLS];
            int x0 = (CANVAS_WIDTH - BOARD_WIDTH) / 2;
            for (int row=0; row < N_ROWS; row++){
                int y = TOP_MARGIN + row * SQUARE_DELTA;
                for (int col = 0; col < N_COLS; col++){
                    int x = x0 +col * SQUARE_DELTA;
                    grid[row][col] = new ScrabbleSquare(x+700, y);;
                }
            }
        }

        private void initScrabbleKeys() {
            keys = new HashMap<String, ScrabbleKey>();
            int nk = KEY_LABELS[0].length;
            int y0 = CANVAS_HEIGHT - BOTTOM_MARGIN - 3 * KEY_HEIGHT - 2 * KEY_YSEP;
            for (int row = 0; row < KEY_LABELS.length; row++) {
                int y = y0 + row * (KEY_HEIGHT + KEY_YSEP);
                int x = (CANVAS_WIDTH - nk * KEY_WIDTH - (nk - 1) * KEY_XSEP) / 2;
                if (row == 1) {
                    x += (KEY_WIDTH + KEY_XSEP) / 2;
                }
                for (int col = 0; col < KEY_LABELS[row].length; col++) {
                    String label = KEY_LABELS[row][col];
                    int w = KEY_WIDTH;
                    if (label.length() > 1) {
                        w += (KEY_WIDTH + KEY_XSEP) / 2;
                    }
                    ScrabbleKey key = new ScrabbleKey(x, y-100, w, label);
                    keys.put(label, key);
                    x += w + KEY_XSEP;

                }
            }
        }

            private void keyAction(char letter){
                letter = Character.toUpperCase(letter);
                if (letter == DELETE) {
                    showMessage("");
                    if (row < N_ROWS && col > 0 && isHorizontal) {
                        col--;
                        grid[row][col].setLetter(" ");
                        repaint();
                    }
                    if (row < N_ROWS && col > 0 && !isHorizontal) {
                        row--;
                        grid[row][col].setLetter(" ");
                        repaint();
                    }

                } else if (letter == ENTER) {
                    showMessage("");
                    for (ScrabbleEventListener listener : listeners) {
                        String s = "";
                        for (int col = 0; col < N_COLS; col++) {
                            s += grid[this.row][col].getLetter();
                        }
                        listener.eventAction(s);
                    }
                }
                else if (letter == TURN) {
                    isHorizontal = !isHorizontal;
                    showMessage("");
                    if (row < N_ROWS && col < N_COLS){
                        grid[row][col].setLetter("");
                    }
                }
                else if (Character.isLetter(letter)) {
                    showMessage("");
                    if (row < N_ROWS && col < N_COLS) {
                        grid[row][col].setLetter("" + letter);
                        col++;
                        repaint();
                    }
                }

            }

            private String findKey (int x, int y) {
                for (String label : keys.keySet()){
                    if (keys.get(label).contains(x, y)){
                        return label;
                    }
                }
                return null;
            }

            class ScrabbleSquare{

            public ScrabbleSquare(int x, int y){
                this.x = x;
                this.y = y;
                this.letter = "";
                this.color = UNKNOWN_COLOR;
            }

            public void paint(Graphics g) {
                Color fg = Color.WHITE;
                int corner = 2 * KEY_CORNER;
                if (color.equals(UNKNOWN_COLOR)){
                    fg = Color.BLACK;
                }
                g.setColor(color);
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
                g.setFont(Font.decode(SQUARE_FONT));
                FontMetrics fm = g.getFontMetrics();
                int tx = x + (SQUARE_SIZE - fm.stringWidth(letter)) / 2;
                int ty = y + SQUARE_SIZE / 2 + SQUARE_LABEL_DY;
                g.setColor(fg);
                g.drawString(letter, tx, ty-3);

            }

            public void setLetter(String letter){
                this.letter = letter;
            }

            public String getLetter(){
                return letter;
            }

            public void setColor(Color color){
                this.color = color;
            }

            public Color getColor(){
                return color;
            }

            private Color color;
            private String letter;
            private int x;
            private int y;
        };

        class ScrabbleKey {

            public ScrabbleKey(int x, int y, int width, String label) {
                this.x = x;
                this.y= y;
                this.width = width;
                this.label = label;
                this.color = KEY_COLOR;
            }

            public void paint(Graphics g){
                Color fg = Color.WHITE;
                int corner = 2 * KEY_CORNER;
                if (color.equals(KEY_COLOR)) {
                    fg = Color.BLACK;
                }
                g.setColor(color);
                g.fillRoundRect(x, y, width, KEY_HEIGHT, corner, corner);
                String key = label;
                String font = KEY_FONT;
                if (key.equals("ENTER")){
                    font = ENTER_FONT;
                }
                else if (key == "DELETE"){
                    key = "\u232B";
                }

                g.setFont(Font.decode(font));
                FontMetrics fm = g.getFontMetrics();
                int tx = x + (width - fm.stringWidth(key)) / 2;
                int ty = y + KEY_HEIGHT / 2 + KEY_LABEL_DY;
                g.setColor(fg);
                g.drawString(key, tx, ty);

            }

            public void setLetter(String label){
                this.label = label;
            }

            public String getLetter(){
                return label;
            }

            public void setColor(Color color){
                this.color = color;
            }

            public Color getColor(){
                return color;
            }

            public boolean contains(int x, int y){
                return x > this.x && x<this.x + width && y>this.y && y< this.y +KEY_HEIGHT;
            }

            private Color color;
            private String label;
            private int width;
            private int x;
            private int y;
            private int a;
            private int b;
        }

        private boolean isHorizontal = true;

        public static final int N_ROWS = Board4.N_ROWS;
        public static final int N_COLS = Board4.N_COLS;

//        public static final Color CORRECT_COLOR = Board4.CORRECT_COLOR;
//        public static final Color PRESENT_COLOR = Board4.PRESENT_COLOR;
//        public static final Color MISSING_COLOR = Board4.MISSING_COLOR;
        public static final Color UNKNOWN_COLOR = Color.WHITE;
        public static final Color KEY_COLOR = new Color(0xDDDDDD);

        public static final int CANVAS_WIDTH = 500;
        public static final int CANVAS_HEIGHT = 900;

        public static final int SQUARE_SIZE = 38;
        public static final int SQUARE_SEP = 3;
        public static final int SQUARE_LABEL_DY = 18;
        public static final int TOP_MARGIN = 30;
        public static final int BOTTOM_MARGIN =30;
        public static final int MESSAGE_SEP = 24;

        public static final int KEY_WIDTH = 40;
        public static final int KEY_HEIGHT = 60;
        public static final int KEY_CORNER = 9;
        public static final int KEY_LABEL_DY = 7;
        public static final int KEY_XSEP = 5;
        public static final int KEY_YSEP = 7;

        public static final int DELETE = (char) 8;
        public static final int ENTER = (char) 10;
        public static final int TURN = (char) 50;

        public static final String SQUARE_FONT = "Helvetica Neue-bold-38";
        public static final String MESSAGE_FONT = "Helvetica Neue-bold-20";
        public static final String KEY_FONT = "Helvetica Neue-18";
        public static final String ENTER_FONT = "Helvetica Neue-14";

        public static final String[][] KEY_LABELS = {
                {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
                {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
                {"ENTER", "Z", "X", "C", "V", "B", "N", "M", "DELETE"},
                {"2"}
        };

        public static final int SQUARE_DELTA = SQUARE_SIZE + SQUARE_SEP;
        public static final int BOARD_WIDTH = N_COLS * SQUARE_SIZE + (N_COLS -1) * SQUARE_SEP;
        public static final int BOARD_HEIGHT = N_ROWS * SQUARE_SIZE + (N_ROWS -1) * SQUARE_SEP;
        public static final int MESSAGE_X = CANVAS_WIDTH / 2;
        public static final int MESSAGE_Y = TOP_MARGIN + BOARD_HEIGHT +MESSAGE_SEP;

        private ArrayList<ScrabbleEventListener> listeners;
        private HashMap<String, ScrabbleKey> keys;
        private String message;
        private ScrabbleSquare[][] grid;
        private int row;
        private int col;

        private static Character currentKey = 'a';
        }





