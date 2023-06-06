package twi.bookmarks;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link GreetingsService}.
 */
public class GreetingsService__BeanDefinitions {
  /**
   * Get the bean definition for 'greetingsService'.
   */
  public static BeanDefinition getGreetingsServiceBeanDefinition() {
    Class<?> beanType = GreetingsService.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(GreetingsService::new);
    return beanDefinition;
  }
}
