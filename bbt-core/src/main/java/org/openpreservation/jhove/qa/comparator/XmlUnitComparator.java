/**
 * 
 */
package org.openpreservation.jhove.qa.comparator;

import java.io.File;

import org.openpreservation.jhove.qa.ControllerState;
import org.xmlunit.XMLUnitException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

/**
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public final class XmlUnitComparator implements Comparator {
    private final XmlOutputElement[] ignoredElements;
    private XmlXpathIgnoreEvaluator diffEvaluator;
    private String message = "OK";

    public XmlUnitComparator() {
        this(new XmlOutputElement[0]);
    }

    public XmlUnitComparator(XmlOutputElement... ignoredElements) {
        this.ignoredElements = ignoredElements;
        this.diffEvaluator = new XmlXpathIgnoreEvaluator(this.ignoredElements);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.openpreservation.jhove.qa.comparator.Comparator#compare(java.lang
     * .String, java.lang.String, java.lang.String)
     */
    @Override
    public ControllerState compare(File baseline, File candidate) {
        boolean hasDifferences = true;
        try {
        Diff diff = DiffBuilder.compare(Input.fromFile(baseline)).withTest(Input.fromFile(candidate)).withDifferenceEvaluator(this.diffEvaluator).build();
            this.message = diff.toString();
            hasDifferences = diff.hasDifferences();
        } catch (XMLUnitException excep) {
            this.message = excep.getMessage();
        }
        return hasDifferences ? ControllerState.CONFLICT : ControllerState.OK;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.openpreservation.jhove.qa.comparator.Comparator#getMessage()
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}
