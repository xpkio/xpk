package io.xpk.domain.obj;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Spot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "spot")
  private List<Registration> registrations;

  @ManyToMany
  private Set<Post> posts;

}
