package ttd.editor.api

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono

class R2dbcReactiveUserDetailsService(private val dbc: DatabaseClient) : ReactiveUserDetailsService {

  class R2dbcUserDetails(private val username: String,
                         private val password: String) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
    override fun isEnabled(): Boolean = true
    override fun getUsername(): String = username
    override fun isCredentialsNonExpired() = true
    override fun getPassword() = password
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
  }

  override fun findByUsername(username: String): Mono<UserDetails> {
    return dbc
        .sql(" select * from editor_users where username = $1 ")
        .bind("$1", username)
        .fetch()
        .one()
        .map { row ->
          R2dbcUserDetails(row["username"] as String, row["password"] as String) as UserDetails
        }
        .single()
  }
}