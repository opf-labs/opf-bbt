/**
 * 
 */
package org.openpreservation.jhove.qa.comparator;

/**
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public final class JhoveHelpers {
    public static final XmlOutputElement EXECUTION_TIMESTAMP = new XmlOutputElement(
            "date", "/jhove[1]/date[1]/text()[1]");
    public static final XmlOutputElement RELEASE_DATE = new XmlOutputElement(
            "release-date", "/jhove[1]/@date");
    public static final XmlOutputElement RELEASE_VERSION = new XmlOutputElement(
            "release-version", "/jhove[1]/@release");
    public static final XmlOutputElement API_DATE = new XmlOutputElement(
            "api-date", "/jhove[1]/app[1]/api[1]/@date");
    public static final XmlOutputElement API_VERSION = new XmlOutputElement(
            "api-version", "/jhove[1]/app[1]/api[1]/text()[1]");
    public static final XmlOutputElement CONF_TEXT = new XmlOutputElement(
            "conf-text", "/jhove[1]/app[1]/configuration[1]/text()[1]");
    public static final XmlOutputElement JHOVE_HOME = new XmlOutputElement(
            "conf-text", "/jhove[1]/app[1]/jhoveHome[1]/text()[1]");
    public static final XmlOutputElement[] EXECUTION_SET = { EXECUTION_TIMESTAMP };
    public static final XmlOutputElement[] INSTALLATION_SET = { EXECUTION_TIMESTAMP,
        CONF_TEXT, JHOVE_HOME };
    public static final XmlOutputElement[] RELEASE_SET = { EXECUTION_TIMESTAMP,
        RELEASE_DATE, RELEASE_VERSION, API_DATE, API_VERSION, CONF_TEXT, JHOVE_HOME };
}
