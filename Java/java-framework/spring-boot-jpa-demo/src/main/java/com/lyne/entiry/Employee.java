package com.lyne.entiry;

import com.lyne.common.LocalDateTimeConcerter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author nn_liu
 * @Created 2017-11-15-14:01
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private int age;
    @Enumerated(EnumType.ORDINAL)
    private EmployeeStatus status;
    //@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Convert(converter = LocalDateTimeConcerter.class)
    private LocalDateTime ts;

}
