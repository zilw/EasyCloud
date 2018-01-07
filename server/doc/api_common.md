 ### 通用返回示例

- 请求成功
``` 
  
  {
    "code": 200,  	// 成功的返回码必定为200
    "data": "",  	// 返回的数据，不同接口不同
    "msg": null
  }
```

- 请求失败
``` 

  {
    "code": 400,  		// 失败的返回码，400客户端错误，500服务端错误，其他请看对应接口
    "data": null,  
    "msg": "参数错误" 	// 失败的提示语/原因  
  }
```

### 路径

- ~/api/usr/xxx 代表该接口需要登录才能访问，必须带上token
- ~/api/pub/xxx 公共接口，不登录也可以访问


### 其他
- 登录成功后返回token以及用户信息    
- 可通过请求头部"Authorization"带上token
- 或带上name=token的cookie （注：该cookie是HTTPOnly）


