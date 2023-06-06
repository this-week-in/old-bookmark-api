package org.springframework.boot.autoconfigure.security.oauth2.resource.servlet;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OAuth2ResourceServerAutoConfiguration}.
 */
public class OAuth2ResourceServerAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'oAuth2ResourceServerAutoConfiguration'.
   */
  public static BeanDefinition getOAuthResourceServerAutoConfigurationBeanDefinition() {
    Class<?> beanType = OAuth2ResourceServerAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(OAuth2ResourceServerAutoConfiguration::new);
    return beanDefinition;
  }
}
