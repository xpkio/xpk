package io.xpk.domain.obj;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Entity
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String body;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private XpkUser author;

  private OffsetDateTime createDate;

  @ManyToMany
  private Set<Spot> spots;
}
