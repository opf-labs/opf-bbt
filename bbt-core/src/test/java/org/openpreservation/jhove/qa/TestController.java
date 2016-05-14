/**
 * 
 */
package org.openpreservation.jhove.qa;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openpreservation.jhove.QAUtils;
import org.openpreservation.jhove.qa.comparator.JhoveHelpers;

/**
 * 
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public class TestController {
    private final static String BASELINE_FLAG = "-b";
    private final static String BASELINE = "/tmp/baseline";
    private final static String CANDIDATE_FLAG = "-c";
    private final static String CANDIDATE = "/tmp/candidate";
    private final static String KEY_FLAG = "-k";
    private final static String KEY = "test-key";
    private final static String[] TEST_FLAGS = { KEY_FLAG, KEY, CANDIDATE_FLAG,
            CANDIDATE, BASELINE_FLAG, BASELINE };
    private static File NO_FILTER_MATCH_FOLDER;
    private static File FILTER_MATCH_FOLDER;
    private static File MISSING_TEST_FOLDER;
    private static File BASELINES_1_12_FOLDER;
    private static File ALT_BASELINES_1_12_FOLDER;
    private static File BASELINES_1_11_FOLDER;

    @BeforeClass
    public static void setup() throws URISyntaxException {
        NO_FILTER_MATCH_FOLDER = QAUtils
                .getResourceParentFolder("org/openpreservation/jhove/qa/controller/no-filter-match-test/jhove-not.xml");
        FILTER_MATCH_FOLDER = QAUtils
                .getResourceParentFolder("org/openpreservation/jhove/qa/controller/filter-match-test/match.jhove.xml");
        MISSING_TEST_FOLDER = QAUtils
                .getResourceParentFolder("org/openpreservation/jhove/qa/controller/missing-file-test/match.jhove.xml");
        BASELINES_1_12_FOLDER = QAUtils
                .getResourceParentFolder("org/openpreservation/jhove/qa/baselines/1.12/ascii/ascii.jhove.xml");
        ALT_BASELINES_1_12_FOLDER = QAUtils
                .getResourceParentFolder("org/openpreservation/jhove/qa/baselines/alt-1.12/ascii/ascii.jhove.xml");
        BASELINES_1_11_FOLDER = QAUtils
                .getResourceParentFolder("org/openpreservation/jhove/qa/baselines/1.11/ascii/ascii.jhove.xml");
    }

    /**
     * Test method for
     * {@link org.openpreservation.jhove.qa.Controller#setInputArgs(java.lang.String[])}
     * .
     */
    @Test
    public void testSetInputArgs() {
        Controller controller = new Controller();
        controller.setInputArgs(TEST_FLAGS);
        assertTrue(TEST_FLAGS == controller.getInputArgs());
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunNoArgs() {
        Controller controller = new Controller();
        controller.run();
        assertTrue(controller.getState() == ControllerState.SYSTEM_ERROR);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunHelpFlag() {
        Controller controller = new Controller();
        controller.setInputArgs("-h");
        controller.run();
        assertTrue(controller.getState() == ControllerState.OK);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunHelpOpt() {
        Controller controller = new Controller();
        controller.setInputArgs("--help");
        controller.run();
        assertTrue(controller.getState() == ControllerState.OK);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunNoBaselineArg() {
        final String[] args = { KEY_FLAG, KEY, CANDIDATE_FLAG, CANDIDATE };
        Controller controller = new Controller();
        controller.setInputArgs(args);
        controller.run();
        assertTrue(controller.getState() == ControllerState.SYSTEM_ERROR);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunNoCandidateArg() {
        final String[] args = { KEY_FLAG, KEY, BASELINE_FLAG, BASELINE };
        Controller controller = new Controller();
        controller.setInputArgs(args);
        controller.run();
        assertTrue(controller.getState() == ControllerState.SYSTEM_ERROR);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunNoCandidateMatch() {
        final String[] args = { KEY_FLAG, KEY, CANDIDATE_FLAG,
                FILTER_MATCH_FOLDER.getAbsolutePath(), BASELINE_FLAG,
                NO_FILTER_MATCH_FOLDER.getAbsolutePath() };
        Controller controller = new Controller();
        controller.setInputArgs(args);
        controller.run();
        assertTrue(controller.getState() == ControllerState.TEST_NOT_EXECUTABLE);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunNoBaselineMatch() {
        final String[] args = { KEY_FLAG, KEY, CANDIDATE_FLAG,
                NO_FILTER_MATCH_FOLDER.getAbsolutePath(), BASELINE_FLAG,
                FILTER_MATCH_FOLDER.getAbsolutePath() };
        Controller controller = new Controller();
        controller.setInputArgs(args);
        controller.run();
        assertTrue(controller.getState() == ControllerState.TEST_NOT_EXECUTABLE);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunMatch() {
        final String[] args = { KEY_FLAG, KEY, CANDIDATE_FLAG,
                FILTER_MATCH_FOLDER.getAbsolutePath(), BASELINE_FLAG,
                FILTER_MATCH_FOLDER.getAbsolutePath() };
        Controller controller = new Controller();
        controller.setInputArgs(args);
        controller.run();
        assertTrue(controller.getState() == ControllerState.OK);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testMissingCandidate() {
        final String[] args = { KEY_FLAG, KEY, CANDIDATE_FLAG,
                FILTER_MATCH_FOLDER.getAbsolutePath(), BASELINE_FLAG,
                MISSING_TEST_FOLDER.getAbsolutePath() };
        Controller controller = new Controller();
        controller.setInputArgs(args);
        controller.run();
        assertTrue(controller.getState() == ControllerState.FILE_MISSING_CANDIDATE);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testMissingBaseline() {
        final String[] args = { KEY_FLAG, KEY, CANDIDATE_FLAG,
                MISSING_TEST_FOLDER.getAbsolutePath(), BASELINE_FLAG,
                FILTER_MATCH_FOLDER.getAbsolutePath() };
        Controller controller = new Controller();
        controller.setInputArgs(args);
        controller.run();
        assertTrue(controller.getState() == ControllerState.FILE_MISSING_BASELINE);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunJhoveIgnoreNothingMatch() {
        final String[] args = { KEY_FLAG, KEY, CANDIDATE_FLAG,
                ALT_BASELINES_1_12_FOLDER.getAbsolutePath(), BASELINE_FLAG,
                BASELINES_1_12_FOLDER.getAbsolutePath() };
        Controller controller = new Controller();
        controller.setInputArgs(args);
        controller.run();
        assertTrue(controller.getState() == ControllerState.OK);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunJhoveIgnoreDateMatch() {
        final String[] args = { KEY_FLAG, KEY, CANDIDATE_FLAG,
                ALT_BASELINES_1_12_FOLDER.getAbsolutePath(), BASELINE_FLAG,
                BASELINES_1_12_FOLDER.getAbsolutePath() };
        Controller controller = new Controller(JhoveHelpers.EXECUTION_TIMESTAMP);
        controller.setInputArgs(args);
        controller.run();
        assertTrue(controller.getState() == ControllerState.OK);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunJhoveReleaseConflict() {
        final String[] args = { KEY_FLAG, KEY, CANDIDATE_FLAG,
                ALT_BASELINES_1_12_FOLDER.getAbsolutePath(), BASELINE_FLAG,
                BASELINES_1_11_FOLDER.getAbsolutePath() };
        Controller controller = new Controller(JhoveHelpers.EXECUTION_TIMESTAMP);
        controller.setInputArgs(args);
        controller.run();
        assertTrue(controller.getState() == ControllerState.CONFLICT);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.Controller#run()}.
     */
    @Test
    public void testRunJhoveIgnoreReleaseDetails() {
        final String[] args = { KEY_FLAG, KEY, CANDIDATE_FLAG,
                ALT_BASELINES_1_12_FOLDER.getAbsolutePath(), BASELINE_FLAG,
                BASELINES_1_11_FOLDER.getAbsolutePath(), "-i" };
        Controller controller = new Controller();
        controller.setInputArgs(args);
        controller.run();
        assertTrue(controller.getState() == ControllerState.OK);
    }
}
