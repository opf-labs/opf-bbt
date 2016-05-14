/**
 * 
 */
package org.openpreservation.jhove.qa.cli;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

/**
 * @author cfw
 *
 */
public class TestCLI {
    private final static String BASELINE_FLAG = "-b";
    private final static String BASELINE_OPT = "--baseline";
    private final static String BASELINE = "/tmp/baseline";
    private final static String CANDIDATE_FLAG = "-c";
    private final static String CANDIDATE_OPT = "--candidate";
    private final static String CANDIDATE = "/tmp/candidate";
    private final static String KEY_FLAG = "-k";
    private final static String KEY_OPT = "--key";
    private final static String KEY= "test-key";
    private final static String[] TEST_FLAGS = {KEY_FLAG, KEY, CANDIDATE_FLAG, CANDIDATE, BASELINE_FLAG, BASELINE};
    private final static String[] TEST_OPTIONS = {KEY_OPT, KEY, CANDIDATE_OPT, CANDIDATE, BASELINE_OPT, BASELINE};

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#parse(java.lang.String[])}.
     * @throws ParseException 
     */
    @Test
    public void testParseHelpFlag() throws ParseException {
        String[] helpArgs = {"-h"};
        CLI cli = new CLI();
        try {
            cli.parse(helpArgs);
        } catch (ParseException excep) {
            if (!excep.getMessage().equals("HELP")) {
                throw excep;
            }
            return;
        }
        fail("No help message exception thrown.");
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#parse(java.lang.String[])}.
     * @throws ParseException 
     */
    @Test
    public void testParseHelpOption() throws ParseException {
        String[] helpArgs = {"--help"};
        CLI cli = new CLI();
        try {
            cli.parse(helpArgs);
        } catch (ParseException excep) {
            if (!excep.getMessage().equals("HELP")) {
                throw excep;
            }
            return;
        }
        fail("No help message exception thrown.");
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#parse(java.lang.String[])}.
     * @throws ParseException 
     */
    @Test
    public void testParseMissingKey() throws ParseException {
        String[] args = {CANDIDATE_FLAG, CANDIDATE, BASELINE_FLAG, BASELINE};
        CLI cli = new CLI();
        cli.parse(args);
        String key = cli.getKey();
        String pattern = "\\d{4}\\d{2}\\d{2}_\\d{2}\\d{2}\\d{2}";
        assertTrue("Key:" + key + ", doesn't match regex pattern:" + pattern, key.matches(pattern));
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#parse(java.lang.String[])}.
     * @throws ParseException 
     */
    @Test(expected=ParseException.class)
    public void testParseMissingBaseLine() throws ParseException {
        String[] args = {CANDIDATE_FLAG, CANDIDATE, KEY_FLAG, KEY};
        CLI cli = new CLI();
        cli.parse(args);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#parse(java.lang.String[])}.
     * @throws ParseException 
     */
    @Test(expected=ParseException.class)
    public void testParseMissingCandidate() throws ParseException {
        String[] args = {BASELINE_FLAG, BASELINE, KEY_FLAG, KEY};
        CLI cli = new CLI();
        cli.parse(args);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#getOptions()}.
     */
    @Test
    public void testOptionCount() {
        CLI cli = new CLI();
        Options cliOptions = cli.getOptions();
        assertTrue(cliOptions.getOptions().size() == 5);
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#getOptions()}.
     */
    @Test
    public void testGetShortOptions() {
        String[] flags = {"h", "b", "c", "k"};
        CLI cli = new CLI();
        Options cliOptions = cli.getOptions();
        for (String flag : flags) {
            assertTrue("Missing flag:" + flag, cliOptions.hasOption(flag));
        }
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#getOptions()}.
     */
    @Test
    public void testGetLongOptions() {
        String[] options = {"help", "baseline", "candidate", "key"};
        CLI cli = new CLI();
        Options cliOptions = cli.getOptions();
        for (String option : options) {
            assertTrue("Missing flag:" + option, cliOptions.hasOption(option));
        }
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#getKey()}.
     * @throws ParseException 
     */
    @Test
    public void testGetKeyFlag() throws ParseException {
        CLI cli = new CLI();
        cli.parse(TEST_FLAGS);
        assertTrue(cli.getKey().equals(KEY));
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#getBaselineFolderPath()}.
     * @throws ParseException 
     */
    @Test
    public void testGetBaselineFolderPathFlag() throws ParseException {
        CLI cli = new CLI();
        cli.parse(TEST_FLAGS);
        assertTrue(cli.getBaselineFolderPath().equals(BASELINE));
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#getCandidateFolderPath()}.
     * @throws ParseException 
     */
    @Test
    public void testGetCandidateFolderPathFlag() throws ParseException {
        CLI cli = new CLI();
        cli.parse(TEST_FLAGS);
        assertTrue(cli.getCandidateFolderPath().equals(CANDIDATE));
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#getKey()}.
     * @throws ParseException 
     */
    @Test
    public void testGetKeyOpt() throws ParseException {
        CLI cli = new CLI();
        cli.parse(TEST_OPTIONS);
        assertTrue(cli.getKey().equals(KEY));
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#getBaselineFolderPath()}.
     * @throws ParseException 
     */
    @Test
    public void testGetBaselineFolderPathOpt() throws ParseException {
        CLI cli = new CLI();
        cli.parse(TEST_OPTIONS);
        assertTrue(cli.getBaselineFolderPath().equals(BASELINE));
    }

    /**
     * Test method for {@link org.openpreservation.jhove.qa.cli.CLI#getCandidateFolderPath()}.
     * @throws ParseException 
     */
    @Test
    public void testGetCandidateFolderPathOpt() throws ParseException {
        CLI cli = new CLI();
        cli.parse(TEST_OPTIONS);
        assertTrue(cli.getCandidateFolderPath().equals(CANDIDATE));
    }
}
