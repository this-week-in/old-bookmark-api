package org.springframework.boot.autoconfigure.jdbc;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link DataSourceAutoConfiguration}.
 */
public class DataSourceAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'dataSourceAutoConfiguration'.
   */
  public static BeanDefinition getDataSourceAutoConfigurationBeanDefinition() {
    Class<?> beanType = DataSourceAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(DataSourceAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link DataSourceAutoConfiguration.PooledDataSourceConfiguration}.
   */
  public static class PooledDataSourceConfiguration {
    /**
     * Get the bean definition for 'pooledDataSourceConfiguration'.
     */
    public static BeanDefinition getPooledDataSourceConfigurationBeanDefinition() {
      Class<?> beanType = DataSourceAutoConfiguration.PooledDataSourceConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(DataSourceAutoConfiguration.PooledDataSourceConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'jdbcConnectionDetails'.
     */
    private static BeanInstanceSupplier<PropertiesJdbcConnectionDetails> getJdbcConnectionDetailsInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<PropertiesJdbcConnectionDetails>forFactoryMethod(DataSourceAutoConfiguration.PooledDataSourceConfiguration.class, "jdbcConnectionDetails", DataSourceProperties.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(DataSourceAutoConfiguration.PooledDataSourceConfiguration.class).jdbcConnectionDetails(args.get(0)));
    }

    /**
     * Get the bean definition for 'jdbcConnectionDetails'.
     */
    public static BeanDefinition getJdbcConnectionDetailsBeanDefinition() {
      Class<?> beanType = PropertiesJdbcConnectionDetails.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getJdbcConnectionDetailsInstanceSupplier());
      return beanDefinition;
    }
  }
}
