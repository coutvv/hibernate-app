package ru.coutvv.searchnqueries;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Software extends Product implements Serializable {
	@Column
	@NotNull
	String version;
}
