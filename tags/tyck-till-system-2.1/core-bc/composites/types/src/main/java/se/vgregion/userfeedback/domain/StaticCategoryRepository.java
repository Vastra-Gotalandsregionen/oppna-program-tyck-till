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

import se.vgregion.dao.domain.patterns.repository.Repository;

/**
 * Interface for the repository that that handles static categories.
 * It also declares which static categories exist.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface StaticCategoryRepository extends Repository<StaticCategory, Long> {
    /**
     * The content StaticCategory id.
     */
    static Long STATIC_CONTENT_CATEGORY = -1L;
    /**
     * The function StaticCategory id.
     */
    static Long STATIC_FUNCTION_CATEGORY = -2L;
    /**
     * The other StaticCategory id.
     */
    static Long STATIC_OTHER_CATEGORY = -3L;

}
