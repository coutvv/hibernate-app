package ru.coutvv.lifecycle.entity.validation;

import com.sun.istack.internal.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Created by coutvv on 25.01.2017.
 */
@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class ValidatedSimplePerson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    @NotNull
    @Size(min = 2, max = 60)
    String fname;

    @Column
    @NotNull
    @Size(min = 2, max = 60)
    String lname;

    @Column
    @Min(value = 13)
    Integer age;
}
