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

package se.vgregion.userfeedback.persistence.inmemory;

import se.vgregion.dao.domain.patterns.repository.inmemory.AbstractInMemoryRepository;
import se.vgregion.userfeedback.domain.StaticCategory;
import se.vgregion.userfeedback.domain.StaticCategoryRepository;

/**
 * Static inmemory implementation of StaticCategory.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class InMemoryStaticCategoryRepository extends AbstractInMemoryRepository<StaticCategory, Long> implements StaticCategoryRepository {

    /**
     * All available StaticCategory's are created at repository instantiation.
     */
    public InMemoryStaticCategoryRepository() {
        init();
    }

    private void init() {
        this.persist(new StaticCategory(StaticCategoryRepository.STATIC_CONTENT_CATEGORY,
                "Webbplatsens innehåll"));
//                "Saknar innehåll", "Fel innehåll", "Hittar inte information"));
        this.persist(new StaticCategory(StaticCategoryRepository.STATIC_FUNCTION_CATEGORY,
                "Webbplatsens funktion"));
//                "Sidan finns inte", "Felmeddelande", "Sidan laddas inte", "Förstår inte funktionen"));
        this.persist(new StaticCategory(StaticCategoryRepository.STATIC_OTHER_CATEGORY,
                "Övrigt"));
    }
}
