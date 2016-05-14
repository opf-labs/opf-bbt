/**
 * 
 */
package org.openpreservation.jhove.qa;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.openpreservation.jhove.qa.cli.CLI;
import org.openpreservation.jhove.qa.comparator.Comparator;
import org.openpreservation.jhove.qa.comparator.XmlOutputElement;
import org.openpreservation.jhove.qa.comparator.XmlUnitComparator;

/**
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public final class Controller {
    private final CLI cli = new CLI();
    private ControllerState state = ControllerState.OK;
    private Comparator comparator;
    private LogWriter log = new LogWriter();
    private String[] inputArgs;

    public Controller() {
        this(new XmlOutputElement[0]);
    }

    public Controller(XmlOutputElement... ignoredElements) {
        this.ignoreElements(ignoredElements);
    }

    public String[] getInputArgs() {
        return this.inputArgs;
    }

    public void setInputArgs(final String... args) {
        this.inputArgs = args;
    }

    public ControllerState getState() {
        return this.state;
    }

    public void ignoreElements(XmlOutputElement... ignoredElements) {
        this.comparator = new XmlUnitComparator(ignoredElements);
    }

    public void run() {
        if (!this.isValidInput()) {
            this.printHelp();
            return;
        }
        final File bf = new File(this.cli.getBaselineFolderPath());
        final File cf = new File(this.cli.getCandidateFolderPath());
        this.traverseFolders(bf, cf);
        this.log.flush(this.cli.getKey());
    }

    private boolean isValidInput() {
        if (this.inputArgs == null) {
            assignState(ControllerState.SYSTEM_ERROR);
            return false;
        }

        try {
            this.cli.parse(this.inputArgs);
        } catch (ParseException e) {
            if (e.getMessage().equals("HELP")) {
                assignState(ControllerState.OK);
            } else {
                assignState(ControllerState.SYSTEM_ERROR);
            }
            return false;
        }

        this.ignoreElements(this.cli.getIgnoredElements());
        boolean valid = true;
        File sourceFolder = new File(this.cli.getBaselineFolderPath());
        File candidateFolder = new File(this.cli.getCandidateFolderPath());

        File[] sourceFiles = sourceFolder.listFiles(new JhoveFileFilter());
        File[] candidateFiles = candidateFolder
                .listFiles(new JhoveFileFilter());

        if (sourceFiles == null || sourceFiles.length == 0
                || candidateFiles == null || candidateFiles.length == 0) {
            valid = false;
            assignState(ControllerState.TEST_NOT_EXECUTABLE);
        }

        return valid;
    }

    private void assignState(ControllerState newState) {
        if (newState == ControllerState.SYSTEM_ERROR) {
            this.state = newState;
        } else if (newState == ControllerState.OK) {
            this.state = (this.state == ControllerState.OK) ? newState
                    : this.state;
        } else {
            this.state = (this.state == ControllerState.OK || this.state == newState) ? newState
                    : ControllerState.MULTIPLE_PROBLEMS;
        }
    }

    private void traverseFolders(final File baselineFolder,
            final File candidateFolder) {
        List<File> baselineFiles = new ArrayList<>(Arrays.asList(baselineFolder
                .listFiles()));
        List<File> candidateFiles = new ArrayList<>(
                Arrays.asList(candidateFolder.listFiles()));

        Iterator<File> iter = baselineFiles.iterator();

        this.log.submitLog("Comparing folders:" + baselineFolder.getPath());
        while (iter.hasNext()) {
            File bf = iter.next();
            File cf = new File(candidateFolder, bf.getName());

            if (bf.isDirectory() && cf.isDirectory()) {
                traverseFolders(bf, cf);
            } else if (candidateFiles.contains(cf)) {
                this.log.submitLog("Testing file:" + bf.getName());
                ControllerState comparisonState = this.comparator.compare(bf,
                        cf);
                this.assignState(comparisonState);
                this.log.submitLog(this.comparator.getMessage());
                candidateFiles.remove(cf);
            } else {
                if (!isSystemFile(cf.getName())) {
                    this.log.submitLog("Missing candidate file:" + cf.getName());
                    this.assignState(ControllerState.FILE_MISSING_CANDIDATE);
                }
            }
            iter.remove();
        }

        if (candidateFiles.size() > 0) {
            for (File f : candidateFiles) {
                if (!isSystemFile(f.getName()) && !f.isDirectory()) {
                    this.log.submitLog("Missing baseline file:" + f.getName());
                    this.assignState(ControllerState.FILE_MISSING_BASELINE);
                }
            }
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("jhvtest", this.cli.getOptions());
    }

    private class JhoveFileFilter implements FilenameFilter {

        public JhoveFileFilter() {
            super();
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".jhove.xml");
        }

    }

    private static boolean isSystemFile(String name) {
        boolean systemFile = false;
        if (name.startsWith(".DS_Store")) {
            systemFile = true;
        }
        return systemFile;
    }
}
