package twi.bookmarks;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link GreetingsController}.
 */
public class GreetingsController__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'greetingsController'.
   */
  private static BeanInstanceSupplier<GreetingsController> getGreetingsControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<GreetingsController>forConstructor(GreetingsService.class)
            .withGenerator((registeredBean, args) -> new GreetingsController(args.get(0)));
  }

  /**
   * Get the bean definition for 'greetingsController'.
   */
  public static BeanDefinition getGreetingsControllerBeanDefinition() {
    Class<?> beanType = GreetingsController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getGreetingsControllerInstanceSupplier());
    return beanDefinition;
  }
}
