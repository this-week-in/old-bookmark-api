package org.springframework.boot.autoconfigure.security.oauth2.resource.servlet;

import java.lang.Class;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.SupplierJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Bean definitions for {@link OAuth2ResourceServerJwtConfiguration}.
 */
public class OAuth2ResourceServerJwtConfiguration__TestContext001_BeanDefinitions {
  /**
   * Bean definitions for {@link OAuth2ResourceServerJwtConfiguration.OAuth2SecurityFilterChainConfiguration}.
   */
  public static class OAuth2SecurityFilterChainConfiguration {
    /**
     * Get the bean definition for 'oAuth2SecurityFilterChainConfiguration'.
     */
    public static BeanDefinition getOAuthSecurityFilterChainConfigurationBeanDefinition() {
      Class<?> beanType = OAuth2ResourceServerJwtConfiguration.OAuth2SecurityFilterChainConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(OAuth2ResourceServerJwtConfiguration.OAuth2SecurityFilterChainConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'jwtSecurityFilterChain'.
     */
    private static BeanInstanceSupplier<SecurityFilterChain> getJwtSecurityFilterChainInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<SecurityFilterChain>forFactoryMethod(OAuth2ResourceServerJwtConfiguration.OAuth2SecurityFilterChainConfiguration.class, "jwtSecurityFilterChain", HttpSecurity.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(OAuth2ResourceServerJwtConfiguration.OAuth2SecurityFilterChainConfiguration.class).jwtSecurityFilterChain(args.get(0)));
    }

    /**
     * Get the bean definition for 'jwtSecurityFilterChain'.
     */
    public static BeanDefinition getJwtSecurityFilterChainBeanDefinition() {
      Class<?> beanType = SecurityFilterChain.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getJwtSecurityFilterChainInstanceSupplier());
      return beanDefinition;
    }
  }

  /**
   * Bean definitions for {@link OAuth2ResourceServerJwtConfiguration.JwtDecoderConfiguration}.
   */
  public static class JwtDecoderConfiguration {
    /**
     * Get the bean instance supplier for 'org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerJwtConfiguration$JwtDecoderConfiguration'.
     */
    private static BeanInstanceSupplier<OAuth2ResourceServerJwtConfiguration.JwtDecoderConfiguration> getJwtDecoderConfigurationInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<OAuth2ResourceServerJwtConfiguration.JwtDecoderConfiguration>forConstructor(OAuth2ResourceServerProperties.class)
              .withGenerator((registeredBean, args) -> new OAuth2ResourceServerJwtConfiguration.JwtDecoderConfiguration(args.get(0)));
    }

    /**
     * Get the bean definition for 'jwtDecoderConfiguration'.
     */
    public static BeanDefinition getJwtDecoderConfigurationBeanDefinition() {
      Class<?> beanType = OAuth2ResourceServerJwtConfiguration.JwtDecoderConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getJwtDecoderConfigurationInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'jwtDecoderByIssuerUri'.
     */
    private static BeanInstanceSupplier<SupplierJwtDecoder> getJwtDecoderByIssuerUriInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<SupplierJwtDecoder>forFactoryMethod(OAuth2ResourceServerJwtConfiguration.JwtDecoderConfiguration.class, "jwtDecoderByIssuerUri", ObjectProvider.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(OAuth2ResourceServerJwtConfiguration.JwtDecoderConfiguration.class).jwtDecoderByIssuerUri(args.get(0)));
    }

    /**
     * Get the bean definition for 'jwtDecoderByIssuerUri'.
     */
    public static BeanDefinition getJwtDecoderByIssuerUriBeanDefinition() {
      Class<?> beanType = SupplierJwtDecoder.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getJwtDecoderByIssuerUriInstanceSupplier());
      return beanDefinition;
    }
  }
}
