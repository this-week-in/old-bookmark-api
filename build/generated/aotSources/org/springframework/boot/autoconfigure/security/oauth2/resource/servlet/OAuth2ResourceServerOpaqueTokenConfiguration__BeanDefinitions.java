package org.springframework.boot.autoconfigure.security.oauth2.resource.servlet;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OAuth2ResourceServerOpaqueTokenConfiguration}.
 */
public class OAuth2ResourceServerOpaqueTokenConfiguration__BeanDefinitions {
  /**
   * Bean definitions for {@link OAuth2ResourceServerOpaqueTokenConfiguration.OpaqueTokenIntrospectionClientConfiguration}.
   */
  public static class OpaqueTokenIntrospectionClientConfiguration {
    /**
     * Get the bean definition for 'opaqueTokenIntrospectionClientConfiguration'.
     */
    public static BeanDefinition getOpaqueTokenIntrospectionClientConfigurationBeanDefinition() {
      Class<?> beanType = OAuth2ResourceServerOpaqueTokenConfiguration.OpaqueTokenIntrospectionClientConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(OAuth2ResourceServerOpaqueTokenConfiguration.OpaqueTokenIntrospectionClientConfiguration::new);
      return beanDefinition;
    }
  }
}
