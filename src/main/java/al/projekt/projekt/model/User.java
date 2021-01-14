package al.projekt.projekt.model;

import al.projekt.projekt.annotations.UniqueEmail;
import al.projekt.projekt.annotations.UniqueUsername;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"), name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, name = "username", unique = true)
    @UniqueUsername
    @NotEmpty
    private String username;

    @Column(nullable = false, name = "password")
    @NotEmpty
    private String password;

    @Column(unique = true, nullable = false, name = "email")
    @NotEmpty
    @NotNull
    @Email
    @UniqueEmail(message = "Email is already registered")
    private String email;

    @Column(nullable = false, name = "name")
    @NotEmpty
    private String name;

    @Column(nullable = false, name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "to_date")
    private Date to_date;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinTable (
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "roleId"))
    private Set<Roles> roles;
}
