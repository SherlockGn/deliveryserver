var contents = [
    [
        // 为一个用户注册 registerUser
        'registerUser?username=xxx&password=xxx&gender=xxx&name=xxx&phone=xxx&address=xxx <br>\
        其中： <br>\
        username是注册的用户名，字符串 <br>\
        password是注册密码，字符串 <br>\
        gender是用户性别，字符串，可以为True、true、TRUE、1（代表男性）、False、false、FALSE、0（代表女性） <br>\
        name是用户的真实姓名，字符串 <br>\
        phone是用户手机，字符串，多个手机号用;（英文的分号）相隔，不可以有非数字 <br>\
        address是用户的住址，字符串，多个地址用;（英文的分号）相隔  <br>\
        例如： <br>\
        registerUser?username=qyx&password=123456&gender=1&name=Qing%20Yuxiang&phone=15851808944;18795959520&<br>address=NJUPT,%20Nanjing,%20Jiangsu,%20China <br>\
        返回：  <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:xxx <br>\
        &nbsp;&nbsp;&nbsp;desc:xxx <br>\
        } <br>\
        如果注册成功，code（类型int）为用户的id；如果注册失败，code为-1，desc（类型字符串）为失败的描述 <br>\
        例如： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:-1 <br>\
        &nbsp;&nbsp;&nbsp;desc:"this username has exsisted" <br>\
        } <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:5 <br>\
        &nbsp;&nbsp;&nbsp;desc:"register success" <br>\
        }',
        // 为一个用户进行登录操作 loginUser
        '',
        // 更改一个用户的信息 setUser
        'setUser?id=xxx&username=xxx&[password=xxx]&[gender=xxx]&[name=xxx]&[phone=xxx]&[address=xxx] <br>\
        其中： <br>\
        id是用户的id，字符串，不可以有非数字 <br>\
        username是注册的用户名，字符串 <br>\
        【注意：id和username只能提供一个】 <br>\
        password是注册密码，字符串 <br>\
        gender是用户性别，字符串，可以为True、true、TRUE、1（代表男性）、False、false、FALSE、0（代表女性） <br>\
        name是用户的真实姓名，字符串 <br>\
        phone是用户手机，字符串，多个手机号用;（英文的分号）相隔，不可以有非数字 <br>\
        address是用户的住址，字符串，多个地址用;（英文的分号）相隔 <br>\
        【password、gender、name、phone、address可以有可以没有，但是不能全部都没有】 <br>\
        返回： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:xxx <br>\
        &nbsp;&nbsp;&nbsp;desc:xxx <br>\
        } <br>\
        如果修改成功，code（类型int）为用户的id；如果修改失败，code为-1，desc（类型字符串）为失败的描述 <br>\
        例如： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:-1 <br>\
        &nbsp;&nbsp;&nbsp;desc:"the user doesn\'t exist" <br>\
        } <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:5 <br>\
        &nbsp;&nbsp;&nbsp;desc:"reset success" <br>\
        }',
        // 获取一个用户的信息 getUser
        'getUser?id=xxx&username=xxx <br>\
        id是用户的id，字符串，不可以有非数字 <br>\
        username是注册的用户名，字符串 <br>\
        【注意：id和username只能提供一个】 <br>\
        返回： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;id=xxx <br>\
        &nbsp;&nbsp;&nbsp;username=xxx <br>\
        &nbsp;&nbsp;&nbsp;password=xxx <br>\
        &nbsp;&nbsp;&nbsp;name=xxx <br>\
        &nbsp;&nbsp;&nbsp;gender=xxx <br>\
        &nbsp;&nbsp;&nbsp;phone=xxx <br>\
        &nbsp;&nbsp;&nbsp;address=xxx <br>\
        } <br>\
        其中： <br>\
        id是用户的id，int类型 <br>\
        username是注册的用户名，字符串 <br>\
        password是注册密码，字符串 <br>\
        name是用户的真实姓名，字符串 <br>\
        gender是用户性别，boolean类型，true为男性，false为女性 <br>\
        phone是用户手机，字符串，多个手机号用;（英文的分号）相隔 <br>\
        address是用户的住址，字符串，多个地址用;（英文的分号）相隔 <br>\
        如果查询失败，则id为-1，name为失败描述，其余为空 <br>\
        例如： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;id=5 <br>\
        &nbsp;&nbsp;&nbsp;username="QYX" <br>\
        &nbsp;&nbsp;&nbsp;password="123456" <br>\
        &nbsp;&nbsp;&nbsp;name="Qing Yuxiang" <br>\
        &nbsp;&nbsp;&nbsp;gender=true <br>\
        &nbsp;&nbsp;&nbsp;phone="15851808944;18795959520" <br>\
        &nbsp;&nbsp;&nbsp;address="NJUPT, Nanjing, Jiangsu, China" <br>\
        } <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;id=-1 <br>\
        &nbsp;&nbsp;&nbsp;username=null <br>\
        &nbsp;&nbsp;&nbsp;password=null <br>\
        &nbsp;&nbsp;&nbsp;name="reduplicative param id & username" <br>\
        &nbsp;&nbsp;&nbsp;gender=null <br>\
        &nbsp;&nbsp;&nbsp;phone=null <br>\
        &nbsp;&nbsp;&nbsp;address=null <br>\
        }',
        // 为一个用户添加手机号或者住址信息 appendUser
        '为用户的phone或者address追加参数，例如原先用户只有一个手机号，追加一个之后就变成两个 <br>\
        appendUser?id=xxx&username=xxx&[phone=xxx]&[address=xxx] <br>\
        其中： <br>\
        id是用户的id，字符串，不可以有非数字 <br>\
        username是注册的用户名，字符串 <br>\
        【注意：id和username只能提供一个】 <br>\
        phone是用户的单个手机号，字符串，不可以有非数字 <br>\
        address是用户的单个住址，字符串，不可含有英文分号 <br>\
        【phone、address可以有可以没有，但是不能全部都没有】 <br>\
        返回： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:xxx <br>\
        &nbsp;&nbsp;&nbsp;desc:xxx <br>\
        } <br>\
        如果追加成功，code（类型int）为用户的id；如果追加失败，code为-1，desc（类型字符串）为失败的描述 <br>\
        例如： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:-1 <br>\
        &nbsp;&nbsp;&nbsp;desc:"phone and address are all null" <br>\
        } <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:5 <br>\
        &nbsp;&nbsp;&nbsp;desc:"append success" <br>\
        }'
    ]


]