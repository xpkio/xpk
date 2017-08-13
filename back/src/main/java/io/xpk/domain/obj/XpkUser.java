package io.xpk.domain.obj;

import static io.xpk.util.MD5.toMD5;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
public class XpkUser implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String firstName;
  private String lastName;
  private String email;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String salt;

  @OneToMany(mappedBy = "xpkUser")
  private List<Registration> registrations;

  @OneToMany(mappedBy = "author")
  private List<Post> posts;

  public String getImgUrl() {
    if (StringUtils.isBlank(email)) {
      return null;
    }
    return "https://www.gravatar.com/avatar/" + toMD5(email);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
