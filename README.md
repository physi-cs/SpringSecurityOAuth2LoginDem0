> https://github.com/spring-projects/spring-security/tree/master/samples/boot/oauth2webclient

# Register OAuth application
1. 联系 刘熠民 老师，在管理端配置应用，分配clientId 和 client_secret ,支持的授权方式和作用域等信息。Demo使用的是OAuth2测试用clientId.
2. ensure callback URL is set to {host}/login/oauth2/code/{client-id}
# Configure application.yml
```
spring:
  thymeleaf:
    cache: false
  security:
    oauth2:
      client:
        registration:
          maxkey:
            client-id: a4fe5396-0aff-40fd-99ac-48115f6f0297
            client-secret: XgsBMzAxMDIwMjAxMzUwNTg2OTM2IA
            authorization-grant-type: authorization_code
            # 第三方认证平台在收到OAuth2AuthorizationRequest后，会回调这个URI来对授权请求进行响应
            redirect-uri: "http://localhost:8080/login/oauth2/code/maxkey"
            client-authentication-method: post
            scope: all
        provider:
          maxkey:
            authorization-uri: ${oauth2.server.host}/maxkey/oauth/v20/authorize
            token-uri: ${oauth2.server.host}/maxkey/oauth/v20/token
            user-info-uri: ${oauth2.server.host}/maxkey/api/oauth/v20/me
            user-info-authentication-method: header
            user-name-attribute: realname
```
# Boot up the application
Launch the Spring Boot 2.0 sample and go to `http://localhost:8080/test`
