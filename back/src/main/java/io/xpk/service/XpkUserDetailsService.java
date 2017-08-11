package io.xpk.service;

import io.xpk.domain.obj.XpkUser;
import io.xpk.domain.repo.XpkUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class XpkUserDetailsService implements UserDetailsService {

  private final XpkUserRepository userRepository;

  public XpkUserDetailsService(XpkUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    XpkUser user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return user;
  }
}
