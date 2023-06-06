package org.springframework.boot.autoconfigure.jdbc;

import java.lang.Class;
import javax.sql.DataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Bean definitions for {@link DataSourceTransactionManagerAutoConfiguration}.
 */
public class DataSourceTransactionManagerAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'dataSourceTransactionManagerAutoConfiguration'.
   */
  public static BeanDefinition getDataSourceTransactionManagerAutoConfigurationBeanDefinition() {
    Class<?> beanType = DataSourceTransactionManagerAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(DataSourceTransactionManagerAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link DataSourceTransactionManagerAutoConfiguration.JdbcTransactionManagerConfiguration}.
   */
  public static class JdbcTransactionManagerConfiguration {
    /**
     * Get the bean definition for 'jdbcTransactionManagerConfiguration'.
     */
    public static BeanDefinition getJdbcTransactionManagerConfigurationBeanDefinition() {
      Class<?> beanType = DataSourceTransactionManagerAutoConfiguration.JdbcTransactionManagerConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(DataSourceTransactionManagerAutoConfiguration.JdbcTransactionManagerConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'transactionManager'.
     */
    private static BeanInstanceSupplier<DataSourceTransactionManager> getTransactionManagerInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<DataSourceTransactionManager>forFactoryMethod(DataSourceTransactionManagerAutoConfiguration.JdbcTransactionManagerConfiguration.class, "transactionManager", Environment.class, DataSource.class, ObjectProvider.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(DataSourceTransactionManagerAutoConfiguration.JdbcTransactionManagerConfiguration.class).transactionManager(args.get(0), args.get(1), args.get(2)));
    }

    /**
     * Get the bean definition for 'transactionManager'.
     */
    public static BeanDefinition getTransactionManagerBeanDefinition() {
      Class<?> beanType = DataSourceTransactionManager.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getTransactionManagerInstanceSupplier());
      return beanDefinition;
    }
  }
}