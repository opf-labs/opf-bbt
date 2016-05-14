/**
 * 
 */
package org.openpreservation.jhove.qa;


/**
 * @author  <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public final class App {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.setInputArgs(args);
        controller.run();
        System.exit(controller.getState().getState());
    }

}
