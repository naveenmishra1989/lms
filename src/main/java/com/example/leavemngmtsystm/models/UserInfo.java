package com.example.leavemngmtsystm.models;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "USERINFO")
public class UserInfo {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "E-mail cannot be empty!")
    @Email(message = "Please provide a valid email!")
    private String email;

    @NotBlank(message = "Password cannot be empty!")
    @Length(min = 5, message = "Choose atleast five characters for password!")
    private String password;

    @NotBlank(message = "Please provide a role!")
    private String role;

    @NotBlank(message = "Please provide first name!")
    private String firstName;

    @NotBlank(message = "Please provide last name!")
    private String lastName;

    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return id != null && Objects.equals(id, userInfo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
