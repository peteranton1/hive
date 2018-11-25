package com.hive.hypermedia.web.controller;

import com.hive.hypermedia.model.Product;
import com.hive.hypermedia.persistence.ProductRepository;
import com.hive.hypermedia.web.error.Checks;
import com.hive.hypermedia.web.resource.MessageConstants;
import com.hive.hypermedia.web.resource.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @RequestMapping("/{productcode}")
    public ProductResource findByProductId(@PathVariable final String productcode) {
        final Product product = Checks
                .checkEntityExists(repo.findByProductcode(productcode),
                        MessageConstants.MESSAGE_PRODUCT_NOT_FOUND + productcode);

        final ProductResource productResource = new ProductResource(product);
        productResource.add(linkTo(methodOn(CartController.class)
        .addProductToCart(productResource))
        .withRel("add-to-cart"));

        return productResource;
    }
}
