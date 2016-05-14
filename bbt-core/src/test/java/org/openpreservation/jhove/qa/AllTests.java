package org.openpreservation.jhove.qa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openpreservation.jhove.qa.cli.TestCLI;

/**
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ TestController.class, TestControllerState.class, TestCLI.class })
public class AllTests {
    /**
     * Empty
     */
}
