/**
 * 
 */
package org.openpreservation.jhove.qa.cli;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.openpreservation.jhove.qa.comparator.JhoveHelpers;
import org.openpreservation.jhove.qa.comparator.XmlOutputElement;

/**
 * @author  <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public final class CLI {

    private final CommandLineParser parser;
    private final Options options;
    private String key;
    private String baselinePath;
    private String candidatePath;
    private XmlOutputElement[] ignoredElements = JhoveHelpers.EXECUTION_SET;

    public CLI() {
        this.parser = new GnuParser();
        this.options = new Options();
        this.options.addOption("k", "key", true,
                "A key that helps identifies the test comparison.");
        this.options
                .addOption(
                        "b",
                        "baseline",
                        true,
                        "A folder that contains the output jhove XML files from the current stable version used to compare against.");
        this.options
                .addOption(
                        "c",
                        "candidate",
                        true,
                        "A folder that contains the output jhove XML files from the merge candidate version.");
        this.options.addOption("i", "ignore-release", false,
                "Set to ignore all release variable output, e.g. versions and release date.");
        this.options.addOption("h", "help", false, "Prints this message");
    }

    public void parse(String... args) throws ParseException {
        CommandLine cmd = this.parser.parse(this.options, args);
        if (cmd.hasOption('h')) {
            throw new ParseException("HELP");
        }

        if (cmd.hasOption('b')) {
            this.baselinePath = cmd.getOptionValue('b');
        } else {
            throw new ParseException(
                    "Please provide a source folder containing jhove XML files from the current stable version");
        }

        if (cmd.hasOption('c')) {
            this.candidatePath = cmd.getOptionValue('c');
        } else {
            throw new ParseException(
                    "Please provide a candidate folder containing fits.xml files from the merge-candidate version");
        }
        
        if (cmd.hasOption('i')) {
            this.ignoredElements = JhoveHelpers.RELEASE_SET;
        }

        if (cmd.hasOption('k')) {
            this.key = cmd.getOptionValue('k');
        } else {
            this.key = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());
        }
    }

    public Options getOptions() {
        return this.options;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getBaselineFolderPath() {
        return this.baselinePath;
    }
    
    public String getCandidateFolderPath() {
        return this.candidatePath;
    }
    
    public XmlOutputElement[] getIgnoredElements() {
        return this.ignoredElements;
    }
}
