package org.springframework.security.config.annotation.authentication.configuration;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

/**
 * Bean definitions for {@link AuthenticationConfiguration}.
 */
public class AuthenticationConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'authenticationConfiguration'.
   */
  public static BeanDefinition getAuthenticationConfigurationBeanDefinition() {
    Class<?> beanType = AuthenticationConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    InstanceSupplier<AuthenticationConfiguration> instanceSupplier = InstanceSupplier.using(AuthenticationConfiguration::new);
    instanceSupplier = instanceSupplier.andThen(AuthenticationConfiguration__TestContext001_Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'authenticationManagerBuilder'.
   */
  private static BeanInstanceSupplier<AuthenticationManagerBuilder> getAuthenticationManagerBuilderInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AuthenticationManagerBuilder>forFactoryMethod(AuthenticationConfiguration.class, "authenticationManagerBuilder", ObjectPostProcessor.class, ApplicationContext.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(AuthenticationConfiguration.class).authenticationManagerBuilder(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'authenticationManagerBuilder'.
   */
  public static BeanDefinition getAuthenticationManagerBuilderBeanDefinition() {
    Class<?> beanType = AuthenticationManagerBuilder.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getAuthenticationManagerBuilderInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'enableGlobalAuthenticationAutowiredConfigurer'.
   */
  private static BeanInstanceSupplier<GlobalAuthenticationConfigurerAdapter> getEnableGlobalAuthenticationAutowiredConfigurerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<GlobalAuthenticationConfigurerAdapter>forFactoryMethod(AuthenticationConfiguration.class, "enableGlobalAuthenticationAutowiredConfigurer", ApplicationContext.class)
            .withGenerator((registeredBean, args) -> AuthenticationConfiguration.enableGlobalAuthenticationAutowiredConfigurer(args.get(0)));
  }

  /**
   * Get the bean definition for 'enableGlobalAuthenticationAutowiredConfigurer'.
   */
  public static BeanDefinition getEnableGlobalAuthenticationAutowiredConfigurerBeanDefinition() {
    Class<?> beanType = GlobalAuthenticationConfigurerAdapter.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getEnableGlobalAuthenticationAutowiredConfigurerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'initializeUserDetailsBeanManagerConfigurer'.
   */
  private static BeanInstanceSupplier<InitializeUserDetailsBeanManagerConfigurer> getInitializeUserDetailsBeanManagerConfigurerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<InitializeUserDetailsBeanManagerConfigurer>forFactoryMethod(AuthenticationConfiguration.class, "initializeUserDetailsBeanManagerConfigurer", ApplicationContext.class)
            .withGenerator((registeredBean, args) -> AuthenticationConfiguration.initializeUserDetailsBeanManagerConfigurer(args.get(0)));
  }

  /**
   * Get the bean definition for 'initializeUserDetailsBeanManagerConfigurer'.
   */
  public static BeanDefinition getInitializeUserDetailsBeanManagerConfigurerBeanDefinition() {
    Class<?> beanType = InitializeUserDetailsBeanManagerConfigurer.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getInitializeUserDetailsBeanManagerConfigurerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'initializeAuthenticationProviderBeanManagerConfigurer'.
   */
  private static BeanInstanceSupplier<InitializeAuthenticationProviderBeanManagerConfigurer> getInitializeAuthenticationProviderBeanManagerConfigurerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<InitializeAuthenticationProviderBeanManagerConfigurer>forFactoryMethod(AuthenticationConfiguration.class, "initializeAuthenticationProviderBeanManagerConfigurer", ApplicationContext.class)
            .withGenerator((registeredBean, args) -> AuthenticationConfiguration.initializeAuthenticationProviderBeanManagerConfigurer(args.get(0)));
  }

  /**
   * Get the bean definition for 'initializeAuthenticationProviderBeanManagerConfigurer'.
   */
  public static BeanDefinition getInitializeAuthenticationProviderBeanManagerConfigurerBeanDefinition(
      ) {
    Class<?> beanType = InitializeAuthenticationProviderBeanManagerConfigurer.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getInitializeAuthenticationProviderBeanManagerConfigurerInstanceSupplier());
    return beanDefinition;
  }
}
