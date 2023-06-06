package twi.bookmarks;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link BookmarkApiApplication}.
 */
public class ResourceserverApplication__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'resourceserverApplication'.
   */
  public static BeanDefinition getResourceserverApplicationBeanDefinition() {
    Class<?> beanType = BookmarkApiApplication.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    ConfigurationClassUtils.initializeConfigurationClass(BookmarkApiApplication.class);
    beanDefinition.setInstanceSupplier(ResourceserverApplication$$SpringCGLIB$$0::new);
    return beanDefinition;
  }
}
