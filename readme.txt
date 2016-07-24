This is a readme file.

Please use the command to clone git repository:
git clone git@github.com:SherlockGn/deliveryserver.git

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
setUser?id=xxx&username=xxx&password=xxx&[gender=xxx]&[name=xxx]&[phone=xxx]&[address=xxx]
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
