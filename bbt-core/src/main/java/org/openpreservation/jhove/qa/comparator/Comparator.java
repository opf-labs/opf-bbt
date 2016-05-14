/**
 * 
 */
package org.openpreservation.jhove.qa.comparator;

import java.io.File;

import org.openpreservation.jhove.qa.ControllerState;

/**
 * @author  <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public interface Comparator {
    public ControllerState compare(File baseline, File candidate);
    public String getMessage();
}
