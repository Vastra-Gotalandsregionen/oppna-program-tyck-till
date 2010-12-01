package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class StaticCategory extends AbstractEntity<Long> {

    private Long id;

    private String category;

    private List<String> subCategories;

    public StaticCategory(Long id, String category, String... subCategories) {
        this.id = id;
        this.category = category;
        if (subCategories == null) {
            this.subCategories = Collections.emptyList();
        } else {
            this.subCategories = Collections.unmodifiableList(Arrays.asList(subCategories));
        }
    }

    public String getCategory() {
        return category;
    }

    public List<String> getSubCategories() {
        return subCategories;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
