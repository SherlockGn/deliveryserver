This is a readme file.

Please use the command to clone git repository:
git clone git@github.com:SherlockGn/deliveryserver.git

you can also access interfaces on:
https://sherlockgn.github.io/deliveryserver/

The interfaces are defined as following:

用户相关接口
【注意】
接口采取get方式进行参数上传
对象的序列、反序列方法为json
所有的基本类型建议采用封装类进行接收，例如Integer、Boolean等而不是int、boolean

1. 注册用户
registerUser?username=xxx&password=xxx&gender=xxx&name=xxx&phone=xxx&address=xxx
其中：
username是注册的用户名，字符串
password是注册密码，字符串
gender是用户性别，字符串，可以为True、true、TRUE、1（代表男性）、False、false、FALSE、0（代表女性）
name是用户的真实姓名，字符串
phone是用户手机，字符串，多个手机号用;（英文的分号）相隔，不可以有非数字
address是用户的住址，字符串，多个地址用;（英文的分号）相隔
例如：
registerUser?username=qyx&password=123456&gender=1&name=Qing%20Yuxiang&phone=15851808944;18795959520&address=NJUPT,%20Nanjing,%20Jiangsu,%20China
返回：
{
    code:xxx
    desc:xxx
}
如果注册成功，code（类型int）为用户的id；如果注册失败，code为-1，desc（类型字符串）为失败的描述
例如：
{
    code:-1
    desc:"this username has exsisted"
}
{
    code:5
    desc:"register success"
}

2.修改用户
setUser?id=xxx&username=xxx&[password=xxx]&[gender=xxx]&[name=xxx]&[phone=xxx]&[address=xxx]
其中：
id是用户的id，字符串，不可以有非数字
username是注册的用户名，字符串
【注意：id和username只能提供一个】
password是注册密码，字符串
gender是用户性别，字符串，可以为True、true、TRUE、1（代表男性）、False、false、FALSE、0（代表女性）
name是用户的真实姓名，字符串
phone是用户手机，字符串，多个手机号用;（英文的分号）相隔，不可以有非数字
address是用户的住址，字符串，多个地址用;（英文的分号）相隔
【password、gender、name、phone、address可以有可以没有，但是不能全部都没有】
返回：
{
    code:xxx
    desc:xxx
}
如果修改成功，code（类型int）为用户的id；如果修改失败，code为-1，desc（类型字符串）为失败的描述
例如：
{
    code:-1
    desc:"the user doesn't exist"
}
{
    code:5
    desc:"reset success"
}

3.获取用户
getUser?id=xxx&username=xxx
id是用户的id，字符串，不可以有非数字
username是注册的用户名，字符串
【注意：id和username只能提供一个】
返回：
{
    id=xxx
    username=xxx
    password=xxx
    name=xxx
    gender=xxx
    phone=xxx
    address=xxx
}
其中：
id是用户的id，int类型
username是注册的用户名，字符串
password是注册密码，字符串
name是用户的真实姓名，字符串
gender是用户性别，boolean类型，true为男性，false为女性
phone是用户手机，字符串，多个手机号用;（英文的分号）相隔
address是用户的住址，字符串，多个地址用;（英文的分号）相隔
如果查询失败，则id为-1，name为失败描述，其余为空
例如：
{
    id=5
    username="QYX"
    password="123456"
    name="Qing Yuxiang"
    gender=true
    phone="15851808944;18795959520"
    address="NJUPT, Nanjing, Jiangsu, China"
}
{
    id=-1
    username=null
    password=null
    name="reduplicative param id & username"
    gender=null
    phone=null
    address=null
}

4.为用户的phone或者address追加参数，例如原先用户只有一个手机号，追加一个之后就变成两个
appendUser?id=xxx&username=xxx&[phone=xxx]&[address=xxx]
其中：
id是用户的id，字符串，不可以有非数字
username是注册的用户名，字符串
【注意：id和username只能提供一个】
phone是用户的单个手机号，字符串，不可以有非数字
address是用户的单个住址，字符串，不可含有英文分号
【phone、address可以有可以没有，但是不能全部都没有】
返回：
{
    code:xxx
    desc:xxx
}
如果追加成功，code（类型int）为用户的id；如果追加失败，code为-1，desc（类型字符串）为失败的描述
例如：
{
    code:-1
    desc:"phone and address are all null"
}
{
    code:5
    desc:"append success"
}

5.用户登录
loginUser?username=xxx&password=xxx
其中：
username是注册的用户名，字符串
password是用户密码，字符串
返回：
{
    code:xxx
    desc:xxx
}
如果登录成功，code（类型int）为用户的id；如果登录失败，code为-1，desc（类型字符串）为失败的描述
例如：
{
    code:-1
    desc:"wrong password"
}
{
    code:5
    desc:"login success"
}

7.快递员注册
registerCourier?username=xxx&password=xxx&name=xxx&phone=xxx
其中：
username是快递员的用户名，字符串
password是快递员的登录密码，字符串
name是快递员的真实姓名，字符串
phone是快递员的手机号码（单个），字符串
返回：
{
    code:xxx
    desc:xxx
}
如果注册成功，code（类型int）为用户的id；如果注册失败，code为-1，desc（类型字符串）为失败的描述
例如：
{
    code:-1
    desc:"this username has exsisted"
}
{
    code:5
    desc:"register success"
}

8.快递员登录
loginCourierusername=xxx&password=xxx
其中：
username是快递员注册的用户名，字符串
password是快递员的登录密码，字符串
返回：
{
    code:xxx
    desc:xxx
}
如果登录成功，code（类型int）为快递员的id；如果登录失败，code为-1，desc（类型字符串）为失败的描述
例如：
{
    code:-1
    desc:"the courier doesn't exist"
}
{
    code:5
    desc:"login success"
}

9.修改快递员
setCourierid=xxx&username=xxx&[password=xxx]&[name=xxx]&[phone=xxx]
其中：
id是快递员的id，字符串，不可以有非数字
username是快递员注册的用户名，字符串
【注意：id和username只能提供一个】
password是注册密码，字符串
name是用户的真实姓名，字符串
phone是用户手机，字符串，多个手机号用;（英文的分号）相隔，不可以有非数字
【password、name、phone可以有可以没有，但是不能全部都没有】
返回：
{
    code:xxx
    desc:xxx
}
如果修改成功，code（类型int）为快递员的id；如果修改失败，code为-1，desc（类型字符串）为失败的描述
例如：
{
    code:-1
    desc:"phone input is not legal"
}
{
    code:5
    desc:"reset success"
}

10.获取快递员
getCourierid=xxx&username=xxx
id是快递员的id，字符串，不可以有非数字
username是快递员注册的用户名，字符串
【注意：id和username只能提供一个】
返回：
{
    id=xxx
    username=xxx
    password=xxx
    name=xxx
    phone=xxx
}
其中：
id是用户的id，int类型
username是注册的用户名，字符串
password是注册密码，字符串
name是用户的真实姓名，字符串
phone是用户手机，字符串
如果查询失败，则id为-1，name为失败描述，其余为空
例如：
{
    id=5
    username="c1"
    password="1234"
    name="courier1"
    phone="15851808944"
}
{
    id=-1
    username=null
    password=null
    name="id seems not a number: 12p"
    phone=null
}

11.添加好友
现将用户1与用户2添加为好友关系
addFriend?id1=xxx&username1=xxx&id2=xxx&username2=xxx
其中：
id1是用户1的id，字符串
username1是用户1的用户名，字符串
【注意：id1和username1只能提供一个】
id2是用户2的id，字符串
username2是用户2的用户名，字符串
【注意：id2和username2只能提供一个】
返回：
{
    code:xxx
    desc:xxx
}
如果添加好友成功，code（类型int）为1；如果修改失败，code为-1，desc（类型字符串）为失败的描述
例如：
{
    code:-1
    desc:"the user1 & user2 have already been friends"
}
{
    code:1
    desc:"add friend success"
}

12.获取好友
getFriend?id=xxx&username=xxx
id是用户的id，字符串，不可以有非数字
username是注册的用户名，字符串
【注意：id和username只能提供一个】
返回，User数组：
[
  {
    id=xxx
    username=xxx
    password=xxx
    name=xxx
    gender=xxx
    phone=xxx
    address=xxx
  },
  {...}
]
描述略
如果查找失败，则数组长度为1，索引为0的用户id为-1，name为失败描述，其余为null
例如：
[
  {
    id=5
    username="QYX"
    password="123456"
    name="Qing Yuxiang"
    gender=true
    phone="15851808944;18795959520"
    address="NJUPT, Nanjing, Jiangsu, China"
  },
  {
    id=6
    username="GTH"
    password="12345"
    name="Gong Tianhe"
    gender=true
    phone="15851808944"
    address="NJUPT, Nanjing, Jiangsu, China"
  }
]
或者：
[
  {
    id=-1
    username=null
    password=null
    name="the user doesn't exist"
    gender=null
    phone=null
    address=null
  }
]

11.创建订单
createIndent?fromuserid=xxx&touserid=xxx&fromphone=xxx&tophone=xxx&fromaddress=xxx&toaddress=xxx&[price=xxx]
其中：
fromuserid是寄件用户的id，字符串，必须数字
touserid是收件用户的id，字符串，必须数字
fromphone是寄件用户的手机号，字符串，必须数字
tophone是收件用户的手机号，字符串，必须数字
fromaddress是寄件用户的地址，字符串
toaddress是收件用户的地址，字符串
price是快递费，字符串，必须是数字（最多一个小数点），可以没有这一项
返回：
{
    code:xxx
    desc:xxx
}
如果创建订单成功，code（类型int）为1；如果创建失败，code为-1，desc（类型字符串）为失败的描述
例如：
{
    code:-1
    desc:"price seems not a float number"
}
{
    code:23
    desc:"create indent success"
}

12.获取订单
getIndent?id=xxx
其中id是订单的id，字符串，必须为数字
返回：
一个订单的json格式字符串，略
如果失败，则id为-1，address为失败描述

13.获取用户发过的订单
getSendedIndent?id=xxx&username=xxx
id是用户的id，字符串，不可以有非数字
username是注册的用户名，字符串
【注意：id和username只能提供一个】
返回：
订单的数组，略
如果失败，则数组长度为1，索引为0的订单id为-1，address为失败描述

14.获取用户接受的订单
getReceivedIndent?id=xxx&username=xxx
id是用户的id，字符串，不可以有非数字
username是注册的用户名，字符串
【注意：id和username只能提供一个】
返回：
订单的数组，略
如果失败，则数组长度为1，索引为0的订单id为-1，address为失败描述


15.获取快递员还未处理的订单
remainingIndent?id=xxx&username=xxx
id是快递员的id，字符串，不可以有非数字
username是快递员注册的用户名，字符串
【注意：id和username只能提供一个】
返回：
订单的数组，略
如果失败，则数组长度为1，索引为0的订单id为-1，address为失败描述


16.快递员扫描二维码
快递员准备发送短信给用户时，会事先扫描订单二维码，需要调用该接口以更新数据库
该接口将处理该订单的快递员设置为该快递员，并将订单状态置1
courierScan?courierid=xxx&courierusername=xxx&indentid=xxx
其中：
courierid是快递员的id，字符串，不可以有非数字
courierusername是快递员注册的用户名，字符串
【注意：courierid和courierusername只能提供一个】
indentid是订单的id，字符串，不可以有非数字
返回：
{
    code:xxx
    desc:xxx
}


17.用户领取快递时扫描二维码
当用户准备扫描二维码以签收快递时，调用该接口
该接口将订单的状态置为2
userScan?userid=xxx&userusername=xxx&indentid=xxx
其中：
userid是用户的id，字符串，不可以有非数字
userusername是注册的用户名，字符串
【注意：userid和userusername只能提供一个】
indentid是订单的id，字符串，不可以有非数字
返回：
{
    code:xxx
    desc:xxx
}









