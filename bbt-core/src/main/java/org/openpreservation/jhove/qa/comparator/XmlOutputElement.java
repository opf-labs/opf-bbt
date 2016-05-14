/**
 * 
 */
package org.openpreservation.jhove.qa.comparator;


/**
 * @author  <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public class XmlOutputElement {
    private final String name;
    private final String xPath;
    
    public XmlOutputElement(final String name, final String xPath) {
        this.name = name;
        this.xPath = xPath;
    }
    
    public String getXPath() {
        return this.xPath;
    }
    
    public String getName() {
        return this.name;
    }
}
