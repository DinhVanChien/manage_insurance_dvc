package com.insurance.application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROLE", //
        uniqueConstraints =
                {@UniqueConstraint(name = "role_uk", columnNames = {"role_id"})})

public class Role {
    @Id
    @GeneratedValue
    @Column(name = "role_id", nullable = false)
    private Integer roleId;
    @Column(name = "role_name", length = 30, nullable = false)
    private String roleName;
}
