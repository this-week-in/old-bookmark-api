package org.springframework.boot.autoconfigure.security.servlet;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link SpringBootWebSecurityConfiguration}.
 */
public class SpringBootWebSecurityConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'springBootWebSecurityConfiguration'.
   */
  public static BeanDefinition getSpringBootWebSecurityConfigurationBeanDefinition() {
    Class<?> beanType = SpringBootWebSecurityConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(SpringBootWebSecurityConfiguration::new);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link SpringBootWebSecurityConfiguration.WebSecurityEnablerConfiguration}.
   */
  public static class WebSecurityEnablerConfiguration {
    /**
     * Get the bean definition for 'webSecurityEnablerConfiguration'.
     */
    public static BeanDefinition getWebSecurityEnablerConfigurationBeanDefinition() {
      Class<?> beanType = SpringBootWebSecurityConfiguration.WebSecurityEnablerConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(SpringBootWebSecurityConfiguration.WebSecurityEnablerConfiguration::new);
      return beanDefinition;
    }
  }
}
