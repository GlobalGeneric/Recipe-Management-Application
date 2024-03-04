package com.recipe.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "recipe")
//@NamedQuery(name = "findAllRecipe",query = "from Recipe order by name")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "instructions")
    private String instructions;

    @OneToMany(/*fetch = FetchType.EAGER,*/ mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("name ASC")
    @Sort(type = SortType.NATURAL)
    private List<Ingredient> ingredients;

}
