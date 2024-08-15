package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "staff")
public class StaffEntity {
    @Id
    @GeneratedValue( generator = "staff-id-generator")
    @GenericGenerator(
            name = "staff-id-generator",
            strategy = "dao.util.idGenerators.StaffIdGenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix",value = "ST")
    )
    @Column(name = "staff_id",nullable = false,length = 5)
    private String staffId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String contactNo;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "staff")
    private List<CatalogEntity> catalogEntities = new ArrayList<>();

    public StaffEntity(String staffId, String firstName, String lastName, String contactNo, String email, String password, String role) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "StaffEntity{" +
                "staffId='" + staffId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
