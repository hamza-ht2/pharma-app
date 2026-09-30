package com.marshall.pharma_app.dto.request.category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @NotBlank(message = "french name is required")
    private String nameFr;

    @NotBlank(message = "Arabic name is required")
    private String nameAr;

    private UUID parentCategoryId;

    @Min(value = 0, message = "display order must be equal or greater than 0")
    private Integer displayOrder;

    private Boolean active;
}