package com.optimagrowth.licensing_service.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@Table(name="licenses") 
public class License {
    @Id
    @Column(name = "license_id", nullable = false) 
    private String licenseId;

    @Column(name = "organization_id", nullable = false) 
    private String organizationId;

    @Column(name = "description", nullable = false) 
    private String description;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "license_type", nullable = false)
    private String licenseType;

    @Column(name="comment")
    private String comment;

    public License withComment(String comment){
        this.setComment(comment);
        return this;
    } 
}