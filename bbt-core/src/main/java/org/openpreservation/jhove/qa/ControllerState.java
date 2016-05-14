/**
 * 
 */
package org.openpreservation.jhove.qa;

/**
 * @author  <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public enum ControllerState {
    OK(0),
    SYSTEM_ERROR(1),
    FILE_MISSING_BASELINE(2),
    FILE_MISSING_CANDIDATE(3),
    FILE_INVALID_BASELINE(4),
    FILE_INVALID_CANDIDATE(5),
    CONFLICT(7),
    MULTIPLE_PROBLEMS(42),
    TEST_NOT_EXECUTABLE(125);
    
    private final int state;
    
    private ControllerState(final int state) {
        this.state = state;
    }
    
    public int getState() {
        return this.state;
    }
    
    public static boolean isValidState(int state) {
        boolean exists = false;
        for (ControllerState cs : ControllerState.values()) {
            if (cs.state == state) {
                exists = true;
                break;
            }
        }
        return exists;
    }
}
