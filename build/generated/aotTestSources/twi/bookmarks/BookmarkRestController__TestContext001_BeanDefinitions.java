package twi.bookmarks;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link BookmarkRestController}.
 */
public class BookmarkRestController__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'bookmarkRestController'.
   */
  private static BeanInstanceSupplier<BookmarkRestController> getBookmarkRestControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<BookmarkRestController>forConstructor(ObjectMapper.class, BookmarkService.class)
            .withGenerator((registeredBean, args) -> new BookmarkRestController(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'bookmarkRestController'.
   */
  public static BeanDefinition getBookmarkRestControllerBeanDefinition() {
    Class<?> beanType = BookmarkRestController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getBookmarkRestControllerInstanceSupplier());
    return beanDefinition;
  }
}
