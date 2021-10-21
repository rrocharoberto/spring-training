package com.roberto.ecom.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.roberto.ecom.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDTO {

    private Integer id;

    @NotNull(message = "Name can not be null.")
    @NotEmpty(message = "Name can not be empty.")
    @Size(min = 5, max = 80, message = "The size must be between 5 and 80.")
    private String name;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

}
