package io.xpk.domain.obj;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Registration {

  @Id
  private Long id;

  @ManyToOne
  @JoinColumn(name="spot_id")
  private Spot spot;

  @ManyToOne
  @JoinColumn(name="xpk_user_id")
  private XpkUser xpkUser;

  private boolean poster;
  private boolean viewer;
}
