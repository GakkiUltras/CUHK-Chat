## CUHK Chat

Final project for IEMS5722, still more to be done due to the limited time, hope one day we can restart this project with more functions.

### Git

1. The current branch has no upstream branch

正确的写法是 $ git branch --set-upstream origin [your_new_version] 。 其中--set-upstream的简写形式是-u 。 origin，即默认的远程仓库名，替换成远程仓库地址，一样有效。 你第一条命令不成功是因为自己新建分支和远程仓库名的“顺序”写反了。

Solve:

```
# 添加checkout的别名
git config --global alias.co checkout
git co -b new_branch_name
git branch --set-upstream origin
# 查看branch
git branch -a
# add, commit
# push #
# 1. connect the upstream respotory 连接新branch和远程origin master 的 branch
git branch --set-upstream-to origin new_branch_name
# 2. pusch
git push origin new_branch_name

#
```

2. 用mainActivity测试，但是不git上去关于mainActivity的改动

```
git add -u
git reset -- app/src/main/res/layout/activity_main.xml
git reset -- app/src/main/java/hk/edu/cuhk/ie/iems5722/group7/MainActivity.java
```



### Login

1. Firebase Database connection was forcefully killed by the server.

   See reference 5



### User Information Send to Server

API

Table to save user information:

```sql
CREATE TABLE users (
id INT NOT NULL AUTO_INCREMENT,
user_id VARCHAR(20) NOT NULL,
user_name VARCHAR(20) NOT NULL,
age VARCHAR(10) NOT NULL,
email VARCHAR(20) NOT NULL,
PRIMARY KEY (id)
) DEFAULT CHARSET = utf8
```

### Friend list table

```sql
# Friends Table
CREATE TABLE `Friends` (
`FriendsId` int(11) NOT NULL,
`UserOne` varchar(255) NOT NULL,
`UserTwo` varchar(512) NOT NULL,
`Status` int(11) NOT NULL,
)ENGINE=MyISAM DEFAULT CHARSET=latin1;
```



### API

1. **send_userInfo**

| **API**              | **POST** http://34.92.204.198/api/a3/send_userInfo           |
| -------------------- | ------------------------------------------------------------ |
| **Descriptions**     | Send user information to server.                             |
| **Input Parameters** | String uidString user_idString user_nameString ageString email |
| **Example**          | POST the following to the API:  uid=34GAIWTT5T&user_id=86577&user_name=Jaden&age=18&email=123@123.com |
| **Sample Output**    | {        "status": "OK"     }                                |

2. get_userInfo**

| **API**              | **GET** http://34.92.204.198/api/a3/get_userInfo             |
| -------------------- | ------------------------------------------------------------ |
| **Descriptions**     | Get user information from user.                              |
| **Input Parameters** | String uid                                                   |
| **Example**          | GET with the following to the API:  ?uid=34GAIWTT5T          |
| **Sample Output**    | { "data": [ { "user_id":"86577","user_name": "Jaden", "age": "18", "email": "123@123.com" }], "status": "OK" } |

3. 

### Thoughts & Plans

1. -MESSAGE-Send/receive picture **NO**
2. -USER MANAGEMENT-Profile **YES** , change username and header picture **NO**
3. -USER MANAGEMENT-Their own username shown on message box **YES**
4. -MESSAGE- Remove the refresh button, update new message automatically. **YES, in our plan.**
5. -FRIENDS MANAGEMENT- Database to save friends, friends list to add friends. **YES, in our plan.**
6. Video talk using Agora. **NO?**

### References

1. checkout to create branchhttps://stackoverflow.com/questions/14489109/how-to-alias-git-checkout-to-git-co
2. Use branches https://medium.com/@jonathanmines/the-ultimate-github-collaboration-guide-df816e98fb67
3. Use branches 2 https://blog.csdn.net/qq_32452623/article/details/54340749
4. Change name of branches https://www.jianshu.com/p/cc740394faf5
5. Firebase database should use the same region as registered. https://stackoverflow.com/questions/68806876/firebase-realtime-database-connection-killed-different-region
6. Git add changes except https://www.codeleading.com/article/65305208346/
7. git reset/revert https://blog.csdn.net/yxlshk/article/details/79944535
8. Build friend list https://medium.com/@pbchandan3/friends-list-database-structure-php-mysql-1280c4f1d41c