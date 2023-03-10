package com.autocrypt.collector.common;

public enum Divisions {
    FIRSTDIVISION (new String[] {"安徽省", "福建省", "甘肃省", "广东省", "贵州省", "海南省", "河北省", "黑龙江省",
            "河南省","湖北省", "湖南省", "江苏省", "江西省", "吉林省", "辽宁省", "青海省", "陕西省", "山东省", "山西省",
            "四川省", "云南省", "浙江省", "广西壮族自治区", "内蒙古自治区", "宁夏回族自治区", "西藏自治区", "新疆维吾尔自治区",
            "北京市", "重庆市", "上海市", "天津市", "香港特别行政区", "澳门特别行政区"}),

    // Anhui Sheng
    安徽省 (new String[] {"合肥市", "芜湖市", "蚌埠市", "淮南市", "马鞍山市", "淮北市", "铜陵市", "安庆市", "黄山市", "滁州市",
            "阜阳市", "宿州市", "六安市", "亳州市", "池州市", "宣城市"}),

    // Fujian Sheng
    福建省  (new String[] {"厦门市", "福州市", "龙岩市", "南平市", "宁德市", "莆田市", "泉州市", "三明市", "漳州市"}),

    // Gansu Sheng
    甘肃省  (new String[] {"酒泉市", "嘉峪关市", "张掖市", "金昌市", "武威市", "白银市", "兰州市", "定西市", "陇南市", "天水市",
            "平凉市", "庆阳市", "临夏回族自治州", "甘南藏族自治州"}),

    // Guangdong Sheng
    广东省 (new String[] {"广州市", "深圳市", "清远市", "韶关市", "河源市", "梅州市", "潮州市", "肇庆市", "云浮市", "佛山市",
            "东莞市", "惠州市", "汕尾市", "揭阳市", "汕头市", "湛江市", "茂名市", "阳江市", "江门市", "中山市", "珠海市"}),

    // Guizhou Sheng
    贵州省  (new String[] {"贵阳市", "安顺市", "遵义市", "六盘水市", "毕节市", "铜仁市", "黔西南布依族苗族自治州",
            "黔南布依族苗族自治州", "黔东南苗族侗族自治州"}),

    // Hainan Sheng
    海南省  (new String[] {"海口市", "三亚市", "三沙市", "儋州市", "五指山市", "琼海市", "文昌市", "万宁市", "东方市", "定安县",
            "屯昌县", "澄迈县", "临高县", "白沙黎族自治县", "昌江黎族自治县", "乐东黎族自治县", "陵水黎族自治县", "保亭黎族苗族自治县",
            "琼中黎族苗族自治县"}),

    // Hebei Sheng
    河北省  (new String[] {"石家庄市", "唐山市", "秦皇岛市", "邯郸市", "邢台市", "保定市", "张家口市", "承德市", "沧州市",
            "廊坊市", "衡水市"}),

    // Heilongjaing Sheng
    黑龙江省  (new String[] {"哈尔滨市", "齐齐哈尔市", "牡丹江市", "佳木斯市", "大庆市", "鸡西市", "双鸭山市", "伊春市",
            "七台河市", "鹤岗市", "黑河市", "绥化市", "大兴安岭地区"}),

    // Henen Sheng
    河南省  (new String[] {"郑州市", "安阳市", "鹤壁市", "焦作市", "开封市", "漯河市", "洛阳市", "南阳市", "平顶山市", "濮阳市",
            "三门峡市", "商丘市", "新乡市", "信阳市", "许昌市", "周口市", "驻马店市", "济源市"}),

    // Hubei Sheng
    湖北省  (new String[] {"武汉市", "鄂州市", "黄冈市", "黄石市", "荆门市", "荆州市", "十堰市", "随州市", "襄阳市", "咸宁市",
            "孝感市", "宜昌市", "恩施土家族苗族自治州", "天门市", "潜江市", "仙桃市", "神农架林区"}),

    // Hunan Sheng
    湖南省  (new String[] {"长沙市", "常德市", "郴州市", "衡阳市", "怀化市", "娄底市", "邵阳市", "湘潭市", "益阳市", "永州市",
            "岳阳市", "张家界市", "株洲市", "湘西土家族苗族自治州"}),

    // Jiangsu Sheng
    江苏省 (new String[] {"南京市", "无锡市", "徐州市", "常州市", "苏州市", "南通市", "连云港市", "淮安市", "盐城市", "扬州市",
            "镇江市", "泰州市", "宿迁市"}),

    // Jiangxi Sheng
    江西省  (new String[] {"南昌市", "景德镇市", "萍乡市", "九江市", "新余市", "鹰潭市", "赣州市", "吉安市", "宜春市", "抚州市",
            "上饶市"}),

    // Jilin Sheng
    吉林省 (new String[] {"长春市", "白城市", "白山市", "吉林市", "辽源市", "四平市", "松原市", "通化市", "延边朝鲜族自治州"}),

    // Liaoning Sheng
    辽宁省  (new String[] {"沈阳市", "大连市", "鞍山市", "本溪市", "朝阳市", "丹东市", "抚顺市", "阜新市", "葫芦岛市", "锦州市",
            "辽阳市", "盘锦市", "铁岭市", "营口市"}),

    // Qinghai SHeng
    青海省  (new String[] {"西宁市", "海东市", "海西蒙古族藏族自治州", "海北藏族自治州", "海南藏族自治州", "黄南藏族自治州",
            "玉树藏族自治州", "果洛藏族自治州"}),

    // Shanxi Sheng
    陕西省  (new String[] {"西安市", "铜川市", "宝鸡市", "咸阳市", "渭南市", "延安市", "汉中市", "榆林市", "安康市", "商洛市"}),

    // Shandong Sheng
    山东省  (new String[] {"济南市", "青岛市", "淄博市", "枣庄市", "东营市", "烟台市", "潍坊市", "济宁市", "泰安市", "威海市",
            "日照市", "临沂市", "德州市", "聊城市", "滨州市", "菏泽市"}),

    // Shanxi Sheng
    山西省  (new String[] {"太原市", "大同市", "阳泉市", "长治市", "晋城市", "朔州市", "晋中市", "运城市", "忻州市", "临汾市",
            "吕梁市"}),

    // Sichuan Sheng
    四川省  (new String[] {"成都市", "自贡市", "攀枝花市", "泸州市", "德阳市", "绵阳市", "广元市", "遂宁市", "内江市", "乐山市",
            "南充市", "眉山市", "宜宾市", "广安市", "达州市", "雅安市", "巴中市", "资阳市", "阿坝藏族羌族自治州", "甘孜藏族自治州", "凉山彝族自治州"}),

    // Yunnan Sheng
    云南省  (new String[] {"昆明市", "曲靖市", "玉溪市", "保山市", "昭通市", "丽江市", "普洱市", "临沧市", "楚雄彝族自治州",
            "红河哈尼族彝族自治州", "文山壮族苗族自治州", "西双版纳傣族自治州", "大理白族自治州", "德宏傣族景颇族自治州", "怒江傈僳族自治州", "迪庆藏族自治州"}),

    // Zhejiang Sheng
    浙江省  (new String[] {"杭州市", "宁波市", "湖州市", "嘉兴市", "金华市", "丽水市", "衢州市", "绍兴市", "台州市",
            "温州市", "舟山市"}),

    // Guangxi Zhuangzu Zizhiqu
    广西壮族自治区 (new String[] {"南宁市", "来宾市", "河池市", "柳州市", "桂林市", "贺州市", "梧州市", "贵港市", "玉林市",
            "防城港市", "钦州市", "北海市", "崇左市", "百色市"}),

    // Nei Menggu zizhiqu
    内蒙古自治区 (new String[] {"呼和浩特市", "包头市", "乌兰察布市", "巴彦淖尔市", "鄂尔多斯市", "赤峰市", "通辽市",
            "呼伦贝尔市", "乌海市", "阿拉善盟", "锡林郭勒盟", "兴安盟"}),

    // Ningxia Huizu Zizhiqu
    宁夏回族自治区 (new String[] {"银川市", "石嘴山市", "吴忠市", "固原市", "中卫市"}),

    // Xizang Zizhiqu
    西藏自治区 (new String[] {"拉萨市", "日喀则市", "昌都市", "林芝市", "山南市", "那曲市", "阿里地区"}),

    // Xinjiang Weiwuer Zizhiqu
    新疆维吾尔自治区 (new String[] {"乌鲁木齐市", "克拉玛依市", "吐鲁番市", "哈密市", "阿勒泰地区", "塔城地区", "喀什地区",
            "阿克苏地区", "和田地区", "博尔塔拉蒙古自治州", "昌吉回族自治州", "伊犁哈萨克自治州", "克孜勒苏柯尔克孜自治州", "巴音郭楞蒙古自治州", "石河子市", "五家渠市", "图木舒克市", "阿拉尔市", "北屯市", "铁门关市", "双河市", "可克达拉市", "昆玉市", "胡杨河市"}),

    // Beijing
    北京市 (new String[] {"東城區", "西城區", "朝陽區", "海淀區", "豐台區", "石景山區", "門頭溝區", "房山區", "通州區",
            "順義區", "昌平區", "大興區", "平穀區", "懷柔區", "密雲區", "延慶區(연경구"}),

    // Chongqing
    重庆市 (new String[] {"渝中区", "大渡口区", "江北区", "沙坪坝区", "九龙坡区", "南岸区", "北碚区", "渝北区", "巴南区",
            "长寿区", "合川区", "江津区", "永川区", "綦江区", "大足区", "涪陵区", "南川区", "黔江区", "万州区", "璧山区", "铜梁区", "潼南区", "荣昌区", "开州区", "梁平区", "武隆区", "垫江县", "丰都县", "城口县", "奉节县", "巫山县", "巫溪县", "云阳县", "忠县", "彭水苗族土家族自治县", "土家族自治县", "秀山土家族苗族自治县", "酉阳土家族苗族自治县"}),

    // Shanghai
    上海市 (new String[] {"黄浦区", "徐汇区", "长宁区", "静安区", "普陀区", "虹口区", "杨浦区", "浦东新区", "宝山区", "闵行区",
            "嘉定区", "金山区", "松江区", "青浦区", "奉贤区", "崇明区"}),

    // Tianjin
    天津市 (new String[] {"和平区", "河西区", "河北区", "南开区", "河东区", "红桥区", "滨海新区", "津南区", "东丽区", "西青区",
            "北辰区", "宝坻区", "武清区", "静海区", "宁河区", "蓟州区"}),

    // Xianggang Tebie Xingzhengqu
    香港特别行政区 (new String[] {"香港特别行政区"}),

    // Aomen Tebie Xingzhengqu
    澳门特别行政区 (new String[] {"澳门特别行政区"});

    private final String[] value;

    Divisions(String[] value) {
        this.value = value;
    }

    public String[] getList() {
        return value;
    }

    public static String[] splitLocation(String location) throws Exception {
        String receivedRegion = "";
        String receivedCity = "";
        for (String region: Divisions.FIRSTDIVISION.getList()) {
            if (location.contains(region)) {
                receivedRegion = location.substring(0, region.length());
                if (receivedRegion.equals(region)) {
                    receivedCity = location.substring(region.length());
                }
            }
        }
        if (receivedRegion.equals("") || receivedCity.equals("")) {
            throw new Exception("Exception Occurred With Resolve Location");
        }
        return new String[] { receivedRegion, receivedCity };
    }
}
