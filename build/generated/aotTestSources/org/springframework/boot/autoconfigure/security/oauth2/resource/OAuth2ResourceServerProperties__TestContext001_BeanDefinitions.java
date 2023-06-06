package org.springframework.boot.autoconfigure.security.oauth2.resource;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OAuth2ResourceServerProperties}.
 */
public class OAuth2ResourceServerProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'oAuth2ResourceServerProperties'.
   */
  public static BeanDefinition getOAuthResourceServerPropertiesBeanDefinition() {
    Class<?> beanType = OAuth2ResourceServerProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(OAuth2ResourceServerProperties::new);
    return beanDefinition;
  }
}
