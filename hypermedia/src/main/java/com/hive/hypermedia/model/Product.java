package com.hive.hypermedia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @JsonIgnore
    @Id
    private long id;

    @Column(nullable = false)
    @JsonView(ProductView.Summary.class)
    private String productcode;

    @Column(nullable = false)
    private String supplier;

    @Column(nullable = false)
    @JsonView(ProductView.Summary.class)
    private String title;

    @Column
    private String description;

    public Product() { super(); }

    public Product(final String supplier,
                   final String title,
                   final String productcode) {
        super();
        this.supplier = supplier;
        this.title = title;
        this.productcode = productcode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return new EqualsBuilder()
                .append(getId(), product.getId())
                .append(getProductcode(), product.getProductcode())
                .append(getSupplier(), product.getSupplier())
                .append(getTitle(), product.getTitle())
                .append(getDescription(), product.getDescription())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getProductcode())
                .append(getSupplier())
                .append(getTitle())
                .append(getDescription())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("productcode", productcode)
                .append("supplier", supplier)
                .append("title", title)
                .append("description", description)
                .toString();
    }
}
