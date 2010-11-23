package se.vgregion.userfeedback.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class Attachments {
    Map<String, byte[]> fileMap = new HashMap<String, byte[]>();

    public Map<String, byte[]> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, byte[]> fileMap) {
        this.fileMap = fileMap;
    }
}
