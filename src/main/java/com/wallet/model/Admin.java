package com.wallet.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.wallet.enums.Gender;
import com.wallet.enums.PlanType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin")
public class Admin {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="admgen")
	@SequenceGenerator(name="admgen",sequenceName="admseq",allocationSize=1)
	private int aid;
	@NonNull
	private String adminfirstname;
	@NonNull
	private String adminlastname;
	@NonNull
	private String adminemailid;
	private String admincontact;
	private String adminpassword;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Account> allcustomers=new ArrayList<>();
}
