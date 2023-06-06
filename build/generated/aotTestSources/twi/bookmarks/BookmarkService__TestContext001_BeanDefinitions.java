package twi.bookmarks;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Bean definitions for {@link BookmarkService}.
 */
public class BookmarkService__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'bookmarkService'.
   */
  private static BeanInstanceSupplier<BookmarkService> getBookmarkServiceInstanceSupplier() {
    return BeanInstanceSupplier.<BookmarkService>forConstructor(JdbcTemplate.class)
            .withGenerator((registeredBean, args) -> new BookmarkService(args.get(0)));
  }

  /**
   * Get the bean definition for 'bookmarkService'.
   */
  public static BeanDefinition getBookmarkServiceBeanDefinition() {
    Class<?> beanType = BookmarkService.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getBookmarkServiceInstanceSupplier());
    return beanDefinition;
  }
}
