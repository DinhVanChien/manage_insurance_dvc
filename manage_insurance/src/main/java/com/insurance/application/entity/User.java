package com.insurance.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
@EntityListeners(AuditingEntityListener.class)
public class User extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10)
    private Integer id;
    // Many to One Có nhiều user ở 1 cty
    // thông qua khóa ngoại company_id
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "insurance_id", nullable = false)
    private Insurance insuranceId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company companyId;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;
    
    @Column(name = "user_sex_division", nullable = false, length = 2)
    private char userSexDivision;
    
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
}
