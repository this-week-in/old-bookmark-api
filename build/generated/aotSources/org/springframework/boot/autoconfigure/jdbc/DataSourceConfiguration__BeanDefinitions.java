package org.springframework.boot.autoconfigure.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import java.lang.Class;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link DataSourceConfiguration}.
 */
public class DataSourceConfiguration__BeanDefinitions {
  /**
   * Bean definitions for {@link DataSourceConfiguration.Hikari}.
   */
  public static class Hikari {
    /**
     * Get the bean definition for 'hikari'.
     */
    public static BeanDefinition getHikariBeanDefinition() {
      Class<?> beanType = DataSourceConfiguration.Hikari.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(DataSourceConfiguration.Hikari::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'jdbcConnectionDetailsHikariBeanPostProcessor'.
     */
    private static BeanInstanceSupplier<HikariJdbcConnectionDetailsBeanPostProcessor> getJdbcConnectionDetailsHikariBeanPostProcessorInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<HikariJdbcConnectionDetailsBeanPostProcessor>forFactoryMethod(DataSourceConfiguration.Hikari.class, "jdbcConnectionDetailsHikariBeanPostProcessor", ObjectProvider.class)
              .withGenerator((registeredBean, args) -> DataSourceConfiguration.Hikari.jdbcConnectionDetailsHikariBeanPostProcessor(args.get(0)));
    }

    /**
     * Get the bean definition for 'jdbcConnectionDetailsHikariBeanPostProcessor'.
     */
    public static BeanDefinition getJdbcConnectionDetailsHikariBeanPostProcessorBeanDefinition() {
      Class<?> beanType = HikariJdbcConnectionDetailsBeanPostProcessor.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getJdbcConnectionDetailsHikariBeanPostProcessorInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'dataSource'.
     */
    private static BeanInstanceSupplier<HikariDataSource> getDataSourceInstanceSupplier() {
      return BeanInstanceSupplier.<HikariDataSource>forFactoryMethod(DataSourceConfiguration.Hikari.class, "dataSource", DataSourceProperties.class, JdbcConnectionDetails.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(DataSourceConfiguration.Hikari.class).dataSource(args.get(0), args.get(1)));
    }

    /**
     * Get the bean definition for 'dataSource'.
     */
    public static BeanDefinition getDataSourceBeanDefinition() {
      Class<?> beanType = HikariDataSource.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setDestroyMethodNames("close");
      beanDefinition.setInstanceSupplier(getDataSourceInstanceSupplier());
      return beanDefinition;
    }
  }
}
