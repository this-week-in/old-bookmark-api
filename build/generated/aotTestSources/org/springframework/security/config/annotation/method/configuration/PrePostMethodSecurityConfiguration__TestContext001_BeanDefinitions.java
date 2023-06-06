package org.springframework.security.config.annotation.method.configuration;

import java.lang.Class;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;

/**
 * Bean definitions for {@link PrePostMethodSecurityConfiguration}.
 */
public class PrePostMethodSecurityConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'prePostMethodSecurityConfiguration'.
   */
  public static BeanDefinition getPrePostMethodSecurityConfigurationBeanDefinition() {
    Class<?> beanType = PrePostMethodSecurityConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(PrePostMethodSecurityConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'preFilterAuthorizationMethodInterceptor'.
   */
  private static BeanInstanceSupplier<Advisor> getPreFilterAuthorizationMethodInterceptorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<Advisor>forFactoryMethod(PrePostMethodSecurityConfiguration.class, "preFilterAuthorizationMethodInterceptor", ObjectProvider.class, ObjectProvider.class, ObjectProvider.class, ApplicationContext.class)
            .withGenerator((registeredBean, args) -> PrePostMethodSecurityConfiguration.preFilterAuthorizationMethodInterceptor(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'preFilterAuthorizationMethodInterceptor'.
   */
  public static BeanDefinition getPreFilterAuthorizationMethodInterceptorBeanDefinition() {
    Class<?> beanType = Advisor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(getPreFilterAuthorizationMethodInterceptorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'preAuthorizeAuthorizationMethodInterceptor'.
   */
  private static BeanInstanceSupplier<Advisor> getPreAuthorizeAuthorizationMethodInterceptorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<Advisor>forFactoryMethod(PrePostMethodSecurityConfiguration.class, "preAuthorizeAuthorizationMethodInterceptor", ObjectProvider.class, ObjectProvider.class, ObjectProvider.class, ObjectProvider.class, ObjectProvider.class, ApplicationContext.class)
            .withGenerator((registeredBean, args) -> PrePostMethodSecurityConfiguration.preAuthorizeAuthorizationMethodInterceptor(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5)));
  }

  /**
   * Get the bean definition for 'preAuthorizeAuthorizationMethodInterceptor'.
   */
  public static BeanDefinition getPreAuthorizeAuthorizationMethodInterceptorBeanDefinition() {
    Class<?> beanType = Advisor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(getPreAuthorizeAuthorizationMethodInterceptorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'postAuthorizeAuthorizationMethodInterceptor'.
   */
  private static BeanInstanceSupplier<Advisor> getPostAuthorizeAuthorizationMethodInterceptorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<Advisor>forFactoryMethod(PrePostMethodSecurityConfiguration.class, "postAuthorizeAuthorizationMethodInterceptor", ObjectProvider.class, ObjectProvider.class, ObjectProvider.class, ObjectProvider.class, ObjectProvider.class, ApplicationContext.class)
            .withGenerator((registeredBean, args) -> PrePostMethodSecurityConfiguration.postAuthorizeAuthorizationMethodInterceptor(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5)));
  }

  /**
   * Get the bean definition for 'postAuthorizeAuthorizationMethodInterceptor'.
   */
  public static BeanDefinition getPostAuthorizeAuthorizationMethodInterceptorBeanDefinition() {
    Class<?> beanType = Advisor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(getPostAuthorizeAuthorizationMethodInterceptorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'postFilterAuthorizationMethodInterceptor'.
   */
  private static BeanInstanceSupplier<Advisor> getPostFilterAuthorizationMethodInterceptorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<Advisor>forFactoryMethod(PrePostMethodSecurityConfiguration.class, "postFilterAuthorizationMethodInterceptor", ObjectProvider.class, ObjectProvider.class, ObjectProvider.class, ApplicationContext.class)
            .withGenerator((registeredBean, args) -> PrePostMethodSecurityConfiguration.postFilterAuthorizationMethodInterceptor(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'postFilterAuthorizationMethodInterceptor'.
   */
  public static BeanDefinition getPostFilterAuthorizationMethodInterceptorBeanDefinition() {
    Class<?> beanType = Advisor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(getPostFilterAuthorizationMethodInterceptorInstanceSupplier());
    return beanDefinition;
  }
}
