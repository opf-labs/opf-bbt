/**
 * 
 */
package org.openpreservation.jhove.qa;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author cfw
 *
 */
public class TestControllerState {

    /**
     * Test method for {@link org.openpreservation.jhove.qa.ControllerState#isValidState(int)}.
     */
    @Test
    public void testIsValidState() {
        int largestState = 0;
        for (ControllerState state : ControllerState.values()) {
            assertTrue(ControllerState.isValidState(state.getState()));
            largestState = (largestState > state.getState()) ? largestState : state.getState();
        }
        assertFalse(ControllerState.isValidState(largestState + 1));
    }

}
