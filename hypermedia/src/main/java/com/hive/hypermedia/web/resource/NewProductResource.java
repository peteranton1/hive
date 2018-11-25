package com.hive.hypermedia.web.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import com.hive.hypermedia.model.Product;
import com.hive.hypermedia.model.ProductView;
import com.hive.hypermedia.web.controller.ProductController;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class NewProductResource extends ResourceSupport {

    @JsonView(ProductView.Summary.class)
    private final Product product;

    @JsonCreator
    public NewProductResource(@NotNull final Product product) {
        this.product = product;

        this.add(linkTo(ProductController.class)
        .slash(product.getProductcode()).withSelfRel());
    }

    @JsonFilter("productFilter")
    public Product getProduct() { return product; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("product", product)
                .toString();
    }
}
