/**
 * 
 */
package org.openpreservation.jhove.qa.comparator;

import java.util.ArrayList;
import java.util.List;

import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DifferenceEvaluator;

/**
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public class XmlXpathIgnoreEvaluator implements DifferenceEvaluator {

    private final XmlOutputElement[] ignoredElements;
    private final List<String> ignoredXPaths = new ArrayList<>();

    public XmlXpathIgnoreEvaluator(XmlOutputElement... ignoredElements) {
        this.ignoredElements = ignoredElements;
        for (XmlOutputElement element : this.ignoredElements) {
            this.ignoredXPaths.add(element.getXPath());
        }
    }

    @Override
    public ComparisonResult evaluate(Comparison comparison,
            ComparisonResult outcome) {
        if ((this.ignoredXPaths.isEmpty())
                || (comparison.getControlDetails().getTarget() == null)) {
            return outcome;
        }
        String xPath = comparison.getControlDetails().getXPath();

        if (this.ignoredXPaths.contains(xPath)) {
            return ComparisonResult.EQUAL;
        }
        return outcome;
    }

}
