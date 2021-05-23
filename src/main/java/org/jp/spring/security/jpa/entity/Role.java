package org.jp.spring.security.jpa.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "ROLE", schema = "auth", catalog = "auth")
@Entity
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = -6996807370695677046L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(name = "role", unique = true)
	private String role;
	@ManyToMany(mappedBy = "roles")
	private List<Users> user;

	@Override
	public String getAuthority() {
		return this.role;
	}

}
