package hexlet.code.app.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

//import com.fasterxml.jackson.annotation.JsonIgnore;

//import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users")
//public class User implements UserDetails, BaseEntity {
public class User implements BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Post> posts = new ArrayList<>();

    // EMAIL
    @Column(unique = true)
    @Email
    @ToString.Include
    private String email;

    // @NotBlank
    @ToString.Include
    private String firstName;

    // @NotBlank
    @ToString.Include
    private String lastName;

    private String passwordDigest;

    @LastModifiedDate
    private LocalDate updatedAt;

    @CreatedDate
    private LocalDate createdAt;

//    @Override
    public String getPassword() {
        return passwordDigest;
    }

//    @Override
    public String getUsername() {
        return email;
    }

////    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
////    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return new ArrayList<GrantedAuthority>();
//    }
//
////    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
}
