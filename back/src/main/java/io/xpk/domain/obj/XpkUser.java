package io.xpk.domain.obj;

import static io.xpk.util.MD5.toMD5;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@JsonIgnoreProperties("password")
public class XpkUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String password;

  public String getImgUrl() {
    if (StringUtils.isBlank(email)) {
      return null;
    }
    return "https://www.gravatar.com/avatar/" + toMD5(email);
  }
}
