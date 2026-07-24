# GIT Hands-on Exercises 1-5

## Hands-on 1: Git Configuration & Basic Operations

### Step 1: Setup Git Configuration
```bash
# Check Git version
git --version

# Configure user name and email (user level)
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"

# Verify configuration
git config --global --list
git config --list
```

### Step 2: Integrate Notepad++ as Default Editor
```bash
# Add Notepad++ to PATH (if needed)
# Verify if notepad++ is accessible
notepad++ --help

# Configure Git to use Notepad++ as editor
git config --global core.editor "'C:/Program Files/Notepad++/notepad++.exe' -multiInst -notabbar -nosession -noPlugin"

# Verify global configuration
git config --global --list
```

### Step 3: Add a File to Repository
```bash
# Initialize Git repository
git init

# Check repository status
git status

# Create a file
echo "Welcome to Git Demo" > welcome.txt

# Check status again
git status

# Stage the file (add to staging area)
git add welcome.txt

# Or add all files
git add .

# Commit with message (opens Notepad++ for multi-line comments)
git commit

# Or commit with inline message
git commit -m "Initial commit: Added welcome.txt"

# Verify working directory is clean
git status

# Connect to remote repository (GitLab)
git remote add origin https://gitlab.com/username/GitDemo.git

# Pull from remote first
git pull origin master --allow-unrelated-histories

# Push to remote
git push origin master
```

---

## Hands-on 2: .gitignore Implementation

### Ignoring Unwanted Files
```bash
# Create .log file
echo "This is a log file" > app.log

# Create log folder
mkdir log
echo "Debug info" > log/debug.log
echo "Error info" > log/error.log

# Check status (should show untracked files)
git status

# Update .gitignore file using Notepad++
notepad++ .gitignore
```

**Content of .gitignore:**
```
# Ignore all .log files
*.log

# Ignore the log folder
/log/

# Ignore IDE files
.idea/
.vscode/

# Ignore OS files
Thumbs.db
.DS_Store

# Ignore compiled files
*.class
*.jar
*.war
target/
```

```bash
# Verify that .log files and log folder are ignored
git status

# Add and commit .gitignore
git add .gitignore
git commit -m "Add .gitignore to ignore log files and folders"

# Push to remote
git push origin master
```

---

## Hands-on 3: Branching and Merging

### Creating and Working with Branches
```bash
# Create a new branch
git branch GitNewBranch

# List all branches (* denotes current branch)
git branch -a

# Switch to the new branch
git checkout GitNewBranch

# Or create and switch in one command
git checkout -b GitNewBranch

# Add files with content
echo "Changes in the new branch" > new-feature.txt
git add new-feature.txt
git commit -m "Add new feature file in GitNewBranch"

# Check status
git status
```

### Merging Branches
```bash
# Switch back to master
git checkout master

# List differences between trunk and branch
git diff master..GitNewBranch

# Visual differences using P4Merge (if configured)
git difftool master..GitNewBranch

# Merge source branch to trunk
git merge GitNewBranch

# View commit history with graph
git log --oneline --graph --decorate

# Delete the branch after merging
git branch -d GitNewBranch

# Check status
git status
```

---

## Hands-on 4: Conflict Resolution

### Creating and Resolving Merge Conflicts
```bash
# Verify master is in clean state
git status

# Create and switch to a new branch
git checkout -b GitWork

# Add hello.xml with content
echo "<hello>Content from branch</hello>" > hello.xml
git add hello.xml
git commit -m "Add hello.xml in GitWork branch"

# Switch to master
git checkout master

# Add hello.xml with DIFFERENT content (causes conflict)
echo "<hello>Content from master</hello>" > hello.xml
git add hello.xml
git commit -m "Add hello.xml in master"

# View commit history
git log --oneline --graph --decorate --all

# Check differences
git diff master..GitWork

# Visual diff using P4Merge
git difftool master..GitWork

# Merge branch to master (conflict expected!)
git merge GitWork
```

You will see a conflict message:
```
Auto-merging hello.xml
CONFLICT (content): Merge conflict in hello.xml
Resolved 'hello.xml' using previous merge.
Automatic merge failed; fix conflicts and then commit the result.
```

```bash
# View conflicted file (git markup shows conflict markers)
cat hello.xml
```

**hello.xml with conflict markers:**
```xml
<<<<<<< HEAD
<hello>Content from master</hello>
=======
<hello>Content from branch</hello>
>>>>>>> GitWork
```

### Use a 3-way merge tool to resolve:
```bash
# Visual merge with P4Merge
git mergetool

# After resolving the conflict manually (edit hello.xml to keep desired content)
git add hello.xml
git commit -m "Resolve merge conflict in hello.xml"

# Check status
git status

# Add backup files created by merge tool to .gitignore
echo "*.orig" >> .gitignore
git add .gitignore
git commit -m "Add .orig files to gitignore"

# List all branches
git branch -a

# Delete the merged branch
git branch -d GitWork

# View clean commit history
git log --oneline --graph --decorate
```

---

## Hands-on 5: Cleanup and Push to Remote

### Cleaning Up and Pushing to Remote
```bash
# Verify master is in clean state
git status

# List all available branches
git branch -a

# Pull remote repository to master
git pull origin master

# Push any pending changes to remote
git push origin master

# Verify the changes are reflected in remote repository
# (Check GitLab/GitHub web interface)
```
