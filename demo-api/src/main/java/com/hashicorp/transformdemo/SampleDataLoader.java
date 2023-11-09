package com.hashicorp.transformdemo;

import static java.lang.String.format;

import com.hashicorp.transformdemo.controller.TransformController;
import com.hashicorp.transformdemo.repository.UserJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SampleDataLoader implements ApplicationListener<ApplicationReadyEvent> {

  private final Config config;
  private final TransformController controller;
  private final UserJpaRepository repository;



  public SampleDataLoader(Config config, TransformController controller,
    UserJpaRepository repository) {
    this.config = config;
    this.controller = controller;
    this.repository = repository;
  }

  @Override
  public void onApplicationEvent(
    ApplicationReadyEvent applicationReadyEvent) {
    if(!config.isSkipLoadingSampleData()){
      log.info("Skip loading sample data");
      return ;
    }
    int id = 1;
    if(!repository.existsUserByUsername(userName(id))) {
      log.info("Adding Sample Data for User: {}", userName(id));
      controller.addOneEncryptedUser(userName(id), password(id), email(id), creditcard(id));
    }
    ++id;
    if(!repository.existsUserByUsername(userName(id))) {
      log.info("Adding Sample Data for User: {}", userName(id));
      controller.addOneSimplestTransformedUser(userName(id), password(id), email(id),
        creditcard(id));
    }
    ++id;
    if(!repository.existsUserByUsername(userName(id))) {
      log.info("Adding Sample Data for User: {}", userName(id));
      controller.addOneSimpleTransformedUser(userName(id), password(id), email(id), creditcard(id));
    }
    ++id;
    if(!repository.existsUserByUsername(userName(id))) {
      log.info("Adding Sample Data for User: {}", userName(id));
      controller.addOneTransformedUser(userName(id), password(id), email(id), creditcard(id));
    }
    ++id;
    if(!repository.existsUserByUsername(userName(id))) {
      log.info("Adding Sample Data for User: {}", userName(id));
      controller.addOneDefaultTokenizedUser(userName(id), password(id), email(id), creditcard(id));
    }
    ++id;
    if(!repository.existsUserByUsername(userName(id))) {
      log.info("Adding Sample Data for User: {}", userName(id));

      controller.addOneConvergentTokenizedUser(userName(id), password(id), email(id),
        creditcard(id));
    }
  }
  private String userName(int id){
    return format("User %1$s", id);
  }
  private String password(int id){
    return format("User%1$sPassw0rd", id);
  }
  private String creditcard(int id){
    return format("1111-2222-3333-4444", id);
  }
  private String email(int id){
    return format("user%1$s@example.com", id);
  }
}
