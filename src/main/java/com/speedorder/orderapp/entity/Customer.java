package com.speedorder.orderapp.entity;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The id of the user")
    private Long id;

    @ApiModelProperty(notes = "The name of the user")
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @ApiModelProperty(notes = "The tier of the user")
    @Column(name = "tier")
    private Integer tier;

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && name.equals(customer.name) && tier.equals(customer.tier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, tier);
    }


    @Override
    public String toString() {

        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tier=" + tier +
                '}';
    }
}
