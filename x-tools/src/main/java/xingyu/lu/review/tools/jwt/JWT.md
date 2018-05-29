# JWT



> **JWT**（Json Web Token）是JSON风格轻量级的授权和身份认证规范，可实现无状态、分布式的Web应用授权。

## Header 头部

```json
{
  "alg":"HS256",  // 加密算法
  "typ":"JWT"  // 类型
}
```

​	由上可知，该token使用HS256加密算法，将头部使用Base64编码可得到如下个格式的字符串：

```
eyJhbGciOiJIUzI1NiJ9
```



## Payload 有效载荷

​	有效载荷中存放了token的签发者（iss）、签发时间（iat）、过期时间（exp）等以及一些我们需要写进token中的信息。

claim分为3种：

claim分为3种：

- 预备的：
  - JWT预先实现的，比如（iss (issuer), exp (expiration time), sub (subject), aud (audience)）等；
- 公有的：
  - 定义包含namespace的uri，或者定义一些已经在IANA注册的claim；
- 私有的：
  - 定义多个应用约定好要共享的数据。

```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```



## Signature 签名

​	签名，服务器认准此签名进行授权

```
HMACSHA256
(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret
)
```

​	上面就是一个签名，HMACSHA256是我们在header中指定的加密算法

​	对base64UrlEncode(header)、base64UrlEncode(payload)和secret进行加密。用来验证请求发送者的身份和确保JWT中途没有被窜改。



## Token在服务与客户端的交互流程

- 客户端通过用户名和密码登录 

- 服务器验证用户名和密码，若通过，生成token返回给客户端。 

- 客户端收到token后以后每次请求的时候都带上这个token，相当于一个令牌，表示我有权限访问了 

- 服务器接收（通常在拦截器中实现）到该token，然后验证该token的合法性。若该token合法，则通过请求，若token不合法或者过期，返回请求失败。

  ​

## Token的优点

1. 相比于session，它无需保存在服务器，不占用服务器内存开销。 
2. 无状态、可拓展性强：比如有3台机器（A、B、C）组成服务器集群，若session存在机器A上，session只能保存在其中一台服务器，此时你便不能访问机器B、C，因为B、C上没有存放该Session，而使用token就能够验证用户请求合法性，并且我再加几台机器也没事，所以可拓展性好就是这个意思。 
3. 由（2）知，这样做可就支持了跨域访问。

## Q&A

- 服务如何判断这个Token是否合法？ 
  ​	由上面token的生成可知，token中的签名是由Header和有效载荷通过Base64编码生成再通过加密算法HS256和密钥最终生成签名，这个签名位于JWT的尾部，在服务器端同样对返回过来的JWT的前部分再进行一次签名生成，然后比较这次生成的签名与请求的JWT中的签名是否一致，若一致说明token合法。由于生成签名的密钥是服务器才知道的，所以别人难以伪造。
- Token中能放敏感信息吗？ 
  不能，因为有效载荷是经过Base64编码生成的，并不是加密。所以不能存放敏感信息。