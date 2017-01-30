package ru.coutvv.searchnqueries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@NamedQueries({
	@NamedQuery(name = "supplier.findAll", query = "from Supplier s"),
})
@Entity
@Data
public class Supplier implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(unique = true) 
	@NotNull
	String name;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
			mappedBy = "supplier", targetEntity = Product.class)
	List<Product> products = new ArrayList<>();	
}
