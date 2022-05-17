public class Board5 {
    private Board4 gw;

    public void run() {
      gw = new Board4();
      gw.addEnterListener((s) -> enterAction(s));
    }

    public void enterAction (String s){
        gw.showMessage("You have to implement this method");
    }

    public static void main(String[] args){
        new Board5().run();
    }
}
