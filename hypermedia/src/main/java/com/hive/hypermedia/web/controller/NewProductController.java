package com.hive.hypermedia.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.hive.hypermedia.model.Product;
import com.hive.hypermedia.model.ProductView;
import com.hive.hypermedia.persistence.ProductRepository;
import com.hive.hypermedia.web.error.Checks;
import com.hive.hypermedia.web.resource.MessageConstants;
import com.hive.hypermedia.web.resource.NewProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/new/products")
public class NewProductController {

    @Autowired
    private ProductRepository repo;


    @RequestMapping("/{productcode}")
    public MappingJacksonValue findByProductId(@PathVariable final String productcode) {
        final Product product = Checks.checkEntityExists(
                repo.findByProductcode(productcode),
                MessageConstants.MESSAGE_PRODUCT_NOT_FOUND + productcode);

        final NewProductResource productResource = new NewProductResource(product);
        productResource.add(linkTo(methodOn(CartController.class)
                .addNewProductToCart(productResource))
                .withRel("add-to-cart"));

        final FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("productFilter",
                        SimpleBeanPropertyFilter.serializeAll());
        final MappingJacksonValue wrapper = new MappingJacksonValue(productResource);
        wrapper.setFilters(filterProvider);

        return wrapper;
    }

    @RequestMapping(value = "/{productcode}", params = "fields")
    public MappingJacksonValue findByProductcodeFiltered(
            @RequestParam String fields,
            @PathVariable final String productcode) {
        final Product product = Checks.checkEntityExists(
                repo.findByProductcode(productcode),
                MessageConstants.MESSAGE_PRODUCT_NOT_FOUND + productcode);

        final NewProductResource productResource = new NewProductResource(product);
        productResource.add(linkTo(methodOn(CartController.class)
                .addNewProductToCart(productResource))
                .withRel("add-to-cart"));

        final FilterProvider filterProvider =
                new SimpleFilterProvider()
                        .addFilter("productFilter",
                                SimpleBeanPropertyFilter.filterOutAllExcept(fields));
        final MappingJacksonValue wrapper = new MappingJacksonValue(productResource);
        wrapper.setFilters(filterProvider);

        return wrapper;
    }


    @JsonView(ProductView.Summary.class)
    @RequestMapping(method = RequestMethod.GET)
    public List<NewProductResource> findAll() {
        final List<Product> products = (List<Product>) repo.findAll();
        final List<NewProductResource> productResources = products.stream()
                .map(NewProductResource::new).collect(Collectors.toList());
        return productResources;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody NewProductResource newProduct) {
        repo.save(newProduct.getProduct());
    }

}
