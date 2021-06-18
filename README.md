@[TOC](目录)
# 1.使用说明
本程序使用eclipse Java编写。使用该程序可利用eclipse打开源代码文件夹，由于使用了有道词典API（使用时需要自己申请，修改API调用源码中的两个变量），所以在运行时需要联网，然后运行Main.java即可输出结果。可对常量明文plaintext和密钥key进行修改，重新输出。
# 2.运行截图
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20210616172117678.png)
# 3.总体设计
明文可以是一个单词或者是单词间以空格为分割的句子，加密时逐个对加密的句子（单词）的字符进行加密。解密时可以用加密的函数，只不过传入的key是负数。通过暴力破解解密时，先判断该密钥所生成的明文的句子是否可以正确翻译出。这里利用有道词典API，通过观察返回的json数据的translation键值进行判断。若返回的是原字符串，则翻译失败，不符合；若符合则添加到动态数组。
之后对上面获得的符合条件的句子遍历，进行逐个查词，通过观察有道词典API返回数据的isWord和web键值判断该字符串是否是单词，如果不是则剔除该单词所在的句子。如此便得到最后的结果。
# 4.类及函数
- YouDaoDictAPI.java，有道词典文本翻译API。JSONObject getResult(String q)设置调用API需要向接口发送的字段来访问服务，传入翻译的英文返回获取的JSON格式数据。
- Encryption.java，凯撒加密算法。String caesarEncryption(String plaintext, int key)传入明文（密文）、密钥，输出密文（明文）。
- Decryption.java，凯撒解密算法。ArrayList<String> caesarDecryption(String ciphertext)，传入密文，返回明文。
- Main.java，凯撒加密/解密算法主函数。
# 5.源码
[https://gitee.com/zhz000/caesar-algorithm](https://gitee.com/zhz000/caesar-algorithm)
[https://github.com/zhz000/caesar-algorithm](https://github.com/zhz000/caesar-algorithm)
