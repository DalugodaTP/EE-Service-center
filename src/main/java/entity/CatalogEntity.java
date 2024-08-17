package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "catalog")
public class CatalogEntity {
    @Id
    @GeneratedValue( generator = "catalog-id-generator")
    @GenericGenerator(
            name = "catalog-id-generator",
            strategy = "dao.util.idGenerators.CatalogIdGenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix",value = "CT")
    )
    @Column(name = "catalog_id",nullable = false,length = 7)
    private String catalogId;
    private String productName;
    private String category;
    private String imageLink;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="staff_id")
    private StaffEntity staff;

    public CatalogEntity(String catalogId, String productName, String category, String imageLink) {
        this.catalogId = catalogId;
        this.productName = productName;
        this.category = category;
        this.imageLink = imageLink;
    }
}
