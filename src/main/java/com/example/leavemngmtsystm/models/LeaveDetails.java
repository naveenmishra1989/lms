package com.example.leavemngmtsystm.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LeaveDetails {


    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String employeeName;

    @NotNull(message = "Please provide start date!")
    private Date fromDate;

    @NotNull(message = "Please provide end date!")
    private Date toDate;

    @NotBlank(message = "Please select type of leave!")
    private String leaveType;

    @NotBlank(message = "Please provide a reason for the leave!")
    private String reason;

    private int duration;

    private boolean acceptRejectFlag;
    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LeaveDetails that = (LeaveDetails) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
