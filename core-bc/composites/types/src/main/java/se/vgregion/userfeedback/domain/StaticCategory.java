package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Domain objject that handles a static category and it's subcategories.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class StaticCategory extends AbstractEntity<Long> {
    private Long id;

    private String name;

    private Map<Long, String> subCategories;

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
