package com.recipe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private String quantity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "recipeId", nullable = false)
    @JsonIgnore
    private Recipe recipe;
}
