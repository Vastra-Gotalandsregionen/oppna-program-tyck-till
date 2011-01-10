/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 */

package se.vgregion.userfeedback.domain;

import org.apache.commons.lang.StringUtils;
import se.vgregion.dao.domain.patterns.valueobject.AbstractValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Domain object representing where to send the case.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Embeddable
public class Backend extends AbstractValueObject<Backend> implements Serializable {
    private static final long serialVersionUID = -6568627904833694408L;

    private String usd;
    private String pivotal;
    private String mbox;

    private boolean activeBackend;
    private boolean activeUsd;
    private boolean activePivotal;
    private boolean activeMbox;

    /**
     * Default constructor creating an inactive Backend.
     */
    public Backend() {
        this(false);
    }

    /**
     * Convenience constructor creating an active or inactive Backend.
     *
     * @param active - boolean state of Backend.
     */
    public Backend(boolean active) {
        activeBackend = active;
    }

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

    public boolean isActiveBackend() {
        return activeBackend;
    }

    public void setActiveBackend(boolean activeBackend) {
        this.activeBackend = activeBackend;
    }

    public boolean isActiveUsd() {
        return activeUsd;
    }

    public void setActiveUsd(boolean activeUsd) {
        this.activeUsd = activeUsd;
    }

    public boolean isActivePivotal() {
        return activePivotal;
    }

    public void setActivePivotal(boolean activePivotal) {
        this.activePivotal = activePivotal;
    }

    public boolean isActiveMbox() {
        return activeMbox;
    }

    public void setActiveMbox(boolean activeMbox) {
        this.activeMbox = activeMbox;
    }

    @Transient
    public boolean isBlank() {
        return (StringUtils.isBlank(usd) && StringUtils.isBlank(pivotal) && StringUtils.isBlank(mbox));
    }
}
