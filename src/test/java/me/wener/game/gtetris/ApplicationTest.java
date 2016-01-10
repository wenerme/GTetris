package me.wener.game.gtetris;

/**
 * @author wener
 * @since 16/1/10
 */
public class ApplicationTest {

    static {
        try {
            Class.forName("jodd.swingspy.SwingSpy").getMethod("install").invoke(null);
        } catch (Exception ex) {
            System.err.println("SwingSpy is not installed... " + ex.toString());
        }
    }

    public static void main(String[] args) {
        Application.main(args);
    }
}
