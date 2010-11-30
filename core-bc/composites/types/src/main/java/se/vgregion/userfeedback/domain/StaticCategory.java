package se.vgregion.userfeedback.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class StaticCategory {
    private String category;

    private List<String> subCategories;

//    public StaticCategory(String category) {
//        this(category, null);
//    }

    public StaticCategory(String category, String... subCategories) {
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
}
