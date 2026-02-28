package com.journeymanager.journeybackend.entity;

import jakarta.persistence.*;

@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String subdomain;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSubdomain() { return subdomain; }
    public void setId(Long id) { this.id = id;}
    public void setName(String name) { this.name = name; }
    public void setSubdomain(String subdomain) { this.subdomain = subdomain; }
}