package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.valueobject.AbstractValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Embeddable
public class Backend extends AbstractValueObject<Backend> implements Serializable {
    private static final long serialVersionUID = -6568627904833694408L;

    private String usd;
    private String pivotal;
    private String mbox;

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getPivotal() {
        return pivotal;
    }

    public void setPivotal(String pivotal) {
        this.pivotal = pivotal;
    }

    public String getMbox() {
        return mbox;
    }

    public void setMbox(String mbox) {
        this.mbox = mbox;
    }
}
