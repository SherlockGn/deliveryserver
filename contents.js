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
        'loginUser?username=xxx&password=xxx <br>\
        其中： <br>\
        username是注册的用户名，字符串 <br>\
        password是用户密码，字符串 <br>\
        返回： <br>\
        { <br>\
            code:xxx <br>\
            desc:xxx <br>\
        } <br>\
        如果登录成功，code（类型int）为用户的id；如果登录失败，code为-1，desc（类型字符串）为失败的描述 <br>\
        例如： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:-1 <br>\
        &nbsp;&nbsp;&nbsp;desc:"wrong password" <br>\
        } <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:5 <br>\
        &nbsp;&nbsp;&nbsp;desc:"login success" <br>\
        }',
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
    ],
    [
        // 为一个快递员注册 registerCourier
        'registerCourier?username=xxx&password=xxx&name=xxx&phone=xxx <br>\
        其中： <br>\
        username是快递员的用户名，字符串 <br>\
        password是快递员的登录密码，字符串 <br>\
        name是快递员的真实姓名，字符串 <br>\
        phone是快递员的手机号码（单个），字符串 <br>\
        返回： <br>\
        { <br>\
            code:xxx <br>\
            desc:xxx <br>\
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
        // 为一个快递员进行登录操作 loginCourier
        'loginCourierusername=xxx&password=xxx <br>\
        其中： <br>\
        username是快递员注册的用户名，字符串 <br>\
        password是快递员的登录密码，字符串 <br>\
        返回： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:xxx <br>\
        &nbsp;&nbsp;&nbsp;desc:xxx <br>\
        } <br>\
        如果登录成功，code（类型int）为快递员的id；如果登录失败，code为-1，desc（类型字符串）为失败的描述 <br>\
        例如： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:-1 <br>\
        &nbsp;&nbsp;&nbsp;desc:"the courier doesn\'t exist" <br>\
        } <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:5 <br>\
        &nbsp;&nbsp;&nbsp;desc:"login success" <br>\
        }',
        // 更改一个快递员的信息 setCourier
        'setCourierid=xxx&username=xxx&[password=xxx]&[name=xxx]&[phone=xxx] <br>\
        其中： <br>\
        id是快递员的id，字符串，不可以有非数字 <br>\
        username是快递员注册的用户名，字符串 <br>\
        【注意：id和username只能提供一个】 <br>\
        password是注册密码，字符串 <br>\
        name是用户的真实姓名，字符串 <br>\
        phone是用户手机，字符串，多个手机号用;（英文的分号）相隔，不可以有非数字 <br>\
        【password、name、phone可以有可以没有，但是不能全部都没有】 <br>\
        返回： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:xxx <br>\
        &nbsp;&nbsp;&nbsp;desc:xxx <br>\
        } <br>\
        如果修改成功，code（类型int）为快递员的id；如果修改失败，code为-1，desc（类型字符串）为失败的描述 <br>\
        例如： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:-1 <br>\
        &nbsp;&nbsp;&nbsp;desc:"phone input is not legal" <br>\
        } <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:5 <br>\
        &nbsp;&nbsp;&nbsp;desc:"reset success" <br>\
        }',
        // 获取一个快递员的信息 getCourier
        'getCourierid=xxx&username=xxx <br>\
        id是快递员的id，字符串，不可以有非数字 <br>\
        username是快递员注册的用户名，字符串 <br>\
        【注意：id和username只能提供一个】 <br>\
        返回： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;id=xxx <br>\
        &nbsp;&nbsp;&nbsp;username=xxx <br>\
        &nbsp;&nbsp;&nbsp;password=xxx <br>\
        &nbsp;&nbsp;&nbsp;name=xxx <br>\
        &nbsp;&nbsp;&nbsp;phone=xxx <br>\
        } <br>\
        其中： <br>\
        id是快递员的id，int类型 <br>\
        username是快递员注册的用户名，字符串 <br>\
        password是快递员的注册密码，字符串 <br>\
        name是快递员的真实姓名，字符串 <br>\
        phone是快递员的手机，字符串 <br>\
        如果查询失败，则id为-1，name为失败描述，其余为空 <br>\
        例如： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;id=5 <br>\
        &nbsp;&nbsp;&nbsp;username="c1" <br>\
        &nbsp;&nbsp;&nbsp;password="1234" <br>\
        &nbsp;&nbsp;&nbsp;name="courier1" <br>\
        &nbsp;&nbsp;&nbsp;phone="15851808944" <br>\
        } <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;id=-1 <br>\
        &nbsp;&nbsp;&nbsp;username=null <br>\
        &nbsp;&nbsp;&nbsp;password=null <br>\
        &nbsp;&nbsp;&nbsp;name="id seems not a number: 12p" <br>\
        &nbsp;&nbsp;&nbsp;phone=null <br>\
        }'
    ],
    [
        // 给两个用户添加好友关系 addFriend
        '现将用户1与用户2添加为好友关系 <br>\
        addFriend?id1=xxx&username1=xxx&id2=xxx&username2=xxx <br>\
        其中： <br>\
        id1是用户1的id，字符串 <br>\
        username1是用户1的用户名，字符串 <br>\
        【注意：id1和username1只能提供一个】 <br>\
        id2是用户2的id，字符串 <br>\
        username2是用户2的用户名，字符串 <br>\
        【注意：id2和username2只能提供一个】 <br>\
        返回： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:xxx <br>\
        &nbsp;&nbsp;&nbsp;desc:xxx <br>\
        } <br>\
        如果添加好友成功，code（类型int）为1；如果修改失败，code为-1，desc（类型字符串）为失败的描述 <br>\
        例如： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:-1 <br>\
        &nbsp;&nbsp;&nbsp;desc:"the user1 & user2 have already been friends" <br>\
        } <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:1 <br>\
        &nbsp;&nbsp;&nbsp;desc:"add friend success" <br>\
        }',
        // 获取一个用户的所有好友 getFriend
        'getFriend?id=xxx&username=xxx <br>\
        id是用户的id，字符串，不可以有非数字 <br>\
        username是注册的用户名，字符串 <br>\
        【注意：id和username只能提供一个】 <br>\
        返回，User数组： <br>\
        [ <br>\
        &nbsp;{ <br>\
        &nbsp;&nbsp;&nbsp;id=xxx <br>\
        &nbsp;&nbsp;&nbsp;username=xxx <br>\
        &nbsp;&nbsp;&nbsp;password=xxx <br>\
        &nbsp;&nbsp;&nbsp;name=xxx <br>\
        &nbsp;&nbsp;&nbsp;gender=xxx <br>\
        &nbsp;&nbsp;&nbsp;phone=xxx <br>\
        &nbsp;&nbsp;&nbsp;address=xxx <br>\
        &nbsp;}, <br>\
        &nbsp;{...} <br>\
        ] <br>\
        描述略 <br>\
        如果查找失败，则数组长度为1，索引为0的用户id为-1，name为失败描述，其余为null <br>\
        例如： <br>\
        [ <br>\
        &nbsp;{ <br>\
        &nbsp;&nbsp;&nbsp;id=5 <br>\
        &nbsp;&nbsp;&nbsp;username="QYX" <br>\
        &nbsp;&nbsp;&nbsp;password="123456" <br>\
        &nbsp;&nbsp;&nbsp;name="Qing Yuxiang" <br>\
        &nbsp;&nbsp;&nbsp;gender=true <br>\
        &nbsp;&nbsp;&nbsp;phone="15851808944;18795959520" <br>\
        &nbsp;&nbsp;&nbsp;address="NJUPT, Nanjing, Jiangsu, China" <br>\
        &nbsp;}, <br>\
        &nbsp;{ <br>\
        &nbsp;&nbsp;&nbsp;id=6 <br>\
        &nbsp;&nbsp;&nbsp;username="GTH" <br>\
        &nbsp;&nbsp;&nbsp;password="12345" <br>\
        &nbsp;&nbsp;&nbsp;name="Gong Tianhe" <br>\
        &nbsp;&nbsp;&nbsp;gender=true <br>\
        &nbsp;&nbsp;&nbsp;phone="15851808944" <br>\
        &nbsp;&nbsp;&nbsp;address="NJUPT, Nanjing, Jiangsu, China" <br>\
        &nbsp;} <br>\
        ] <br>\
        或者： <br>\
        [ <br>\
        &nbsp;{ <br>\
        &nbsp;&nbsp;&nbsp;id=-1 <br>\
        &nbsp;&nbsp;&nbsp;username=null <br>\
        &nbsp;&nbsp;&nbsp;password=null <br>\
        &nbsp;&nbsp;&nbsp;name="the user doesn\'t exist" <br>\
        &nbsp;&nbsp;&nbsp;gender=null <br>\
        &nbsp;&nbsp;&nbsp;phone=null <br>\
        &nbsp;&nbsp;&nbsp;address=null <br>\
        &nbsp;} <br>\
        ]'
    ],
    [
        // 创建一个新的订单 createIndent
        'createIndent?fromuserid=xxx&touserid=xxx&fromphone=xxx&tophone=xxx&fromaddress=xxx&toaddress=xxx&[price=xxx] <br>\
        其中： <br>\
        fromuserid是寄件用户的id，字符串，必须数字 <br>\
        touserid是收件用户的id，字符串，必须数字 <br>\
        fromphone是寄件用户的手机号，字符串，必须数字 <br>\
        tophone是收件用户的手机号，字符串，必须数字 <br>\
        fromaddress是寄件用户的地址，字符串 <br>\
        toaddress是收件用户的地址，字符串 <br>\
        price是快递费，字符串，必须是数字（最多一个小数点），可以没有这一项 <br>\
        返回： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:xxx <br>\
        &nbsp;&nbsp;&nbsp;desc:xxx <br>\
        } <br>\
        如果创建订单成功，code（类型int）为1；如果创建失败，code为-1，desc（类型字符串）为失败的描述 <br>\
        例如： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:-1 <br>\
        &nbsp;&nbsp;&nbsp;desc:"price seems not a float number" <br>\
        } <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:23 <br>\
        &nbsp;&nbsp;&nbsp;desc:"create indent success" <br>\
        }',
        // 获取一个订单的信息 getIndent
        'getIndent?id=xxx <br>\
        其中id是订单的id，字符串，必须为数字 <br>\
        返回： <br>\
        一个订单的json格式字符串，略 <br>\
        如果失败，则id为-1，address为失败描述',
        // 获取用户发过的订单 getSendedIndent
        'getSendedIndent?id=xxx&username=xxx <br>\
        id是用户的id，字符串，不可以有非数字 <br>\
        username是注册的用户名，字符串 <br>\
        【注意：id和username只能提供一个】 <br>\
        返回： <br>\
        订单的数组，略 <br>\
        如果失败，则数组长度为1，索引为0的订单id为-1，address为失败描述',
        // '获取用户接受的订单 getReceivedIndent
        'getReceivedIndent?id=xxx&username=xxx <br>\
        id是用户的id，字符串，不可以有非数字 <br>\
        username是注册的用户名，字符串 <br>\
        【注意：id和username只能提供一个】 <br>\
        返回： <br>\
        订单的数组，略 <br>\
        如果失败，则数组长度为1，索引为0的订单id为-1，address为失败描述',
        // 获取快递员还未处理的订单 remainingIndent
        'remainingIndent?id=xxx&username=xxx <br>\
        id是快递员的id，字符串，不可以有非数字 <br>\
        username是快递员注册的用户名，字符串 <br>\
        【注意：id和username只能提供一个】 <br>\
        返回： <br>\
        订单的数组，略 <br>\
        如果失败，则数组长度为1，索引为0的订单id为-1，address为失败描述'
    ],
    [
        // 快递员处理订单时扫描二维码 courierScan',
        '快递员准备发送短信给用户时，会事先扫描订单二维码，需要调用该接口以更新数据库 <br>\
        该接口将处理该订单的快递员设置为该快递员，并将订单状态置1 <br>\
        courierScan?courierid=xxx&courierusername=xxx&indentid=xxx <br>\
        其中： <br>\
        courierid是快递员的id，字符串，不可以有非数字 <br>\
        courierusername是快递员注册的用户名，字符串 <br>\
        【注意：courierid和courierusername只能提供一个】 <br>\
        indentid是订单的id，字符串，不可以有非数字 <br>\
        返回： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:xxx <br>\
        &nbsp;&nbsp;&nbsp;desc:xxx <br>\
        }',
        // 用户领取快递时扫描二维码 userScan'
        '当用户准备扫描二维码以签收快递时，调用该接口 <br>\
        该接口将订单的状态置为2 <br>\
        userScan?userid=xxx&userusername=xxx&indentid=xxx <br>\
        其中： <br>\
        userid是用户的id，字符串，不可以有非数字 <br>\
        userusername是注册的用户名，字符串 <br>\
        【注意：userid和userusername只能提供一个】 <br>\
        indentid是订单的id，字符串，不可以有非数字 <br>\
        返回： <br>\
        { <br>\
        &nbsp;&nbsp;&nbsp;code:xxx <br>\
        &nbsp;&nbsp;&nbsp;desc:xxx <br>\
        }'
    ]
]