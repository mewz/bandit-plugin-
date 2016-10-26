A Hudson/[Jenkins](http://jenkins-ci.org) plugin to run [Bandit](https://github.com/openstack/bandit) against Python applications and track the results. This plugin was forked from the fine Brakeman for Jenkins plugin (https://github.com/jenkinsci/brakeman-plugin) after much trail and error.

## Requirements

This plugin runs Python static source code analyis tool, Bandit (https://github.com/openstack/bandit). You will need Bandit installed to use this plugin


### Add Shell Command

Bandit does not return a clean exit response, thus the need to echo the exit status. Replace /opt/local/bin/bandit with the actual location where bandit is installed on your system

##!/bin/sh

    #!/bin/sh
    rm $WORKSPACE/bandit.json
    /usr/local/bin/bandit -r -f json -o $WORKSPACE/bandit.json $WORKSPACE
	echo "build exit code was: $?"

### Publish Warnings

Click 'Publish Bandit warnings' to enable the plugin along with the bandit output file, which is contained in the $WORKSPACE folder (default is bandit.json)