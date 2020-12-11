### 查看远程分支
git branch -a 查看远程所有分支  
git fetch 查看远程新增分支  
git branch -vv 查看所有本地分支对应关联的远程分支 （git remote —-verbose ）
解除远程关联分支：git remote remove origin  



### 版本回退
git reset + (commit id): 状态回滚，代码保留   
git reset --hard + (commit id)：状态回滚，commit id之前的代码提交被删除 
git reset --hard HEAD^：^表示上个commit版本， ^^表示上两个版本，～100表示上100个版本
git reset HEAD . ：如果已经git add .    

### 交叉提交
git cherry-pick + (commit  id)：(先拉b分支代码，然后把你commit(提交)到a分支的代码复制到b分支（然后a分支可删）   

### 撤销修改  
1. git restore . : 删除所有本地修改的代码（将工作区的内容回退到和暂存区一致）  
2. 如果已经git add . 表示已经提交到暂存区，使用 git reset Head <file>取消暂存区；再进行操作1
3. 如果已经git commit，表示已提交到本地仓库，先使用 git reset + (commit id)      
    -> git log: 查看commit 记录
4. 如果已经git push，已无法撤销。。重新修改后再push一下。。

### git stash
git stash：将当前工作区存储起来
git stash drop: 删除指定文件   
git stash clear: 删除所有文件    
git stash aplly stash@{1}：恢复到指定stash


git remote set-url origin “(远程git repository的http 地址)” ：本地项目上传到git repository    gi   
./gradlew assembleDebug —stacktrace: 查看潜在编译失败问题    
