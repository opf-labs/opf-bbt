/**
 * 
 */
package org.openpreservation.jhove;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author  <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public class QAUtils {
    /**
     * @param resName
     *            the name of the resource to retrieve a file for
     * @return the java.io.File for the named resource
     * @throws URISyntaxException
     *             if the named resource can't be converted to a URI
     */
    public final static File getResourceAsFile(final String resName)
            throws URISyntaxException {
        return new File(ClassLoader.getSystemResource(resName).toURI());
    }

    /**
     * @param resName
     *            the name of the resource to retrieve a file for
     * @return the java.io.File for the named resource
     * @throws URISyntaxException
     *             if the named resource can't be converted to a URI
     */
    public final static File getResourceParentFolder(final String resName)
            throws URISyntaxException {
        File resource = getResourceAsFile(resName);
        return resource.getParentFile();
    }
}
