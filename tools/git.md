# 多git账户冲突解决
1. 在~/.ssh/目录下创建config文件,配置不同账号的rsa文件
```
# msxf 10.16.30.50
Host git.msxf.local
HostName git.msxf.local
User suli
IdentityFile ~/.ssh/id_rsa

# github
Host github.com
HostName github.com
User suli1
IdentityFile ~/.ssh/github_rsa
```

2. 使用ssh-add添加rsa配置
```
$ssh-add ~/.ssh/github_rsa
```

# git pull
```
$git pull <远程主机名> <远程分支名>:<本地分支名>


```
# git push
```
git push origin local_branch:remote_branch
```

# git fetch
```
$git fetch <远程主机名> <分支名>
```


# fork出来的project与原项目同步

# 修改最近一次提交的commit message
```
$git commit --amend
```
然后在出来的编辑界面,直接编辑注释信息

```
$git push -f origin local_branch:remote_branch
```
强制提交到远程服务器,覆盖


[Git操作简易小手册](https://github.com/suzeyu1992/GitOperateDemo)

# 使用vimdiff来解决冲突
[Use vimdiff as git mergetool](http://www.rosipov.com/blog/use-vimdiff-as-git-mergetool/)
# git config

```
git config merge.tool vimdiff
git config merge.conflictstyle diff3
git config mergetool.prompt false
```

## 让git mergetool不再生成烦人的备份文件（*.orig）
```
git config --global mergetool.keepBackup false
```

# [git rebase](http://gitbook.liuhui998.com/4_2.html)
更新merge request
```
git commit -m 'updated'
git rebase -i HEAD~2
```


# git config alias

```
git config --global alias.lg "log --color --graph --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit"

```



# 中文显示问题解决方式

[git中文显示问题解决](http://xstarcd.github.io/wiki/shell/git_chinese.html)

