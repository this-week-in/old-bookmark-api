package ttd.editor.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.security.config.Customizer
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource


@Configuration
class SecurityConfiguration {

  @Bean
  fun corsConfigurationSource(): CorsConfigurationSource =
      CorsConfigurationSource {
        CorsConfiguration()
            .let {
              it.allowCredentials = true
              it.allowedHeaders = listOf("*")
              it.allowedOrigins = listOf("*")
              it.allowedMethods = listOf(HttpMethod.POST, HttpMethod.OPTIONS,
                  HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.GET).map { it.toString() }
              it
            }
      }

  @Bean
  fun authentication(dbc: DatabaseClient): ReactiveUserDetailsService = R2dbcReactiveUserDetailsService(dbc)

  @Bean
  fun authorization(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain =
      httpSecurity
          .authorizeExchange { ae ->
            ae
                .pathMatchers("/bookmarks/export").permitAll()
                .anyExchange().authenticated()
          }//
          .oauth2ResourceServer { x -> x.jwt() }//
          .csrf { x -> x.disable() }
          .cors(Customizer.withDefaults())
          .build()

}