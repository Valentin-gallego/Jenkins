folder('Tools') {
    description('Folder for miscellaneous tools.')
}

job('Tools/clone-repository') {
    parameters {
    	stringParam('GIT_REPOSITORY_URL', null, 'Git URL of the repository to clone')
    }
    wrappers {
        preBuildCleanup()
    }
    steps {
        shell 'git clone ${GIT_REPOSITORY_URL}'
    }
}

job('Tools/SEED') {
    parameters {
    	stringParam('GITHUB_NAME', null, 'GitHub repository owner/repo_name')
        stringParam('DISPLAY_NAME', null, 'Display name for the job')
    }
    steps {
        dsl {
            text("job(\"\${DISPLAY_NAME}\") {\n    scm {\n        github(\"\${GITHUB_NAME}\", 'master')\n    }\n    wrappers {\n        preBuildCleanup()\n    }\n    triggers {\n        scm('* * * * *')\n    }\n    steps {\n        shell 'make fclean'\n        shell 'make'\n        shell 'make tests_run'\n        shell 'make clean'\n    }\n}")
        }
    }
}
