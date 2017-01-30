package ru.coutvv.searchnqueries;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	Supplier supplier;
	
	@Column
	@NotNull
	String name;
	
	@Column
	@NotNull
	String description;
	
	@Column
	@NotNull
	Double price;
}
