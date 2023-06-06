package org.springframework.boot.autoconfigure.security.oauth2.resource.servlet;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link Oauth2ResourceServerConfiguration}.
 */
public class Oauth2ResourceServerConfiguration__BeanDefinitions {
  /**
   * Bean definitions for {@link Oauth2ResourceServerConfiguration.JwtConfiguration}.
   */
  public static class JwtConfiguration {
    /**
     * Get the bean definition for 'jwtConfiguration'.
     */
    public static BeanDefinition getJwtConfigurationBeanDefinition() {
      Class<?> beanType = Oauth2ResourceServerConfiguration.JwtConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(Oauth2ResourceServerConfiguration.JwtConfiguration::new);
      return beanDefinition;
    }
  }

  /**
   * Bean definitions for {@link Oauth2ResourceServerConfiguration.OpaqueTokenConfiguration}.
   */
  public static class OpaqueTokenConfiguration {
    /**
     * Get the bean definition for 'opaqueTokenConfiguration'.
     */
    public static BeanDefinition getOpaqueTokenConfigurationBeanDefinition() {
      Class<?> beanType = Oauth2ResourceServerConfiguration.OpaqueTokenConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(Oauth2ResourceServerConfiguration.OpaqueTokenConfiguration::new);
      return beanDefinition;
    }
  }
}
