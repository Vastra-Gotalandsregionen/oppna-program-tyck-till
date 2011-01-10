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

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Domain object that handles a static category and it's subcategories.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class StaticCategory extends AbstractEntity<Long> {
    private Long id;

    private String name;

    private Map<Long, String> subCategories;

    /**
     * Constructor creating an immutable Static Category object.
     *
     * @param id  - category id
     * @param name - category name.
     * @param subCategories - an array of subcategories.
     */
    public StaticCategory(Long id, String name, String... subCategories) {
        this.id = id;
        this.name = name;
        if (subCategories == null) {
            this.subCategories = Collections.emptyMap();
        } else {
            Map<Long, String> temp = new TreeMap<Long, String>();
            long i = 0;
            for (String subCategory : subCategories) {
                temp.put(i++, subCategory);
            }
            this.subCategories = Collections.unmodifiableMap(temp);
        }
    }

    public String getName() {
        return name;
    }

    public Map<Long, String> getSubCategories() {
        return subCategories;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
