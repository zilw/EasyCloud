#### 1.1 用户登录

**请求地址**   
- ` ~/api/pub/login `  
  
**请求方式**  
- POST JSON  
- 示例  
```
  {
    "account":"test",
    "password":"mypassword"
  }
```

**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|account |是  |string |用户账户   |
|password |是  |string |登录密码  |


 **返回示例**  

``` json
  {
      "msg": "",
      "code": 200,
      "data": {
          "userId": 10026,
          "account": "test",
          "password": null,
          "createTime": "2018-01-06 02:20:07",
          "lastTime": "2018-01-06 02:20:07",
          "token": "3032506a6a774036a16d7cf0814f1a86"
      }
  }
```

 **返回参数说明**  

|参数名|类型|说明|
|:-----  |:-----|----- |  
|userId|long|用户id|
|account|string|用户账户|
|createTime|string|创建时间|
|lastTime|string|最后修改时间|
|token|string|令牌|


 **备注** 

- none

---  

#### 1.2 用户注册

**请求地址**   
- ` ~/api/pub/register `  
  
**请求方式**  
- POST JSON  
- 示例  
```
  {
    "account":"test",
    "password":"mypassword"
  }
```

**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|account |是  |string |用户账户   |
|password |是  |string |登录密码  |


 **返回示例**  

``` json
  {
    "code": 200,
    "data": "",
    "msg": null
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |

 **备注** 

- none

---  

#### 1.3 用户注销

**请求地址**   
- ` ~/api/usr/logout  `  需要token
  
**请求方式**  
- GET

**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |


 **返回示例**  

``` json
  {
    "code": 200,
    "data": "",
    "msg": null
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |

 **备注** 

- none

