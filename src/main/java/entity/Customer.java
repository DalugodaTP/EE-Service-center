package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "customer-id-generator")
    @GenericGenerator(
            name = "customer-id-generator",
            strategy = "dao.util.idgenerators.CustomerIdGenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix",value = "CTR")
    )
    @Column(name = "customer_id",nullable = false,length = 10)
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String contact_no;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Orders> ordersList;

    public Customer(String id, String fName,String lName, String email, String contact_no) {
        this.id = id;
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.contact_no = contact_no;
    }
}
