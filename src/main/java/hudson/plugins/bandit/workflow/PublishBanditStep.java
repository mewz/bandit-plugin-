package hudson.plugins.bandit.workflow;

import hudson.Extension;
import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.CheckForNull;

/*
 * Forked from the fine brakeman plugin for Jenkins: https://github.com/jenkinsci/brakeman-plugin
 */

public class PublishBanditStep extends AbstractStepImpl {

    private final String outputFile;

    @DataBoundConstructor
    public PublishBanditStep(@CheckForNull String outputFile) {
        this.outputFile = outputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    @Extension
    public static class DescriptorImpl extends AbstractStepDescriptorImpl {

        public DescriptorImpl() {
            super(PublishBanditStepExecution.class);
        }

        @Override
        public String getFunctionName() {
            return "publishBandit";
        }


        @Override
        public String getDisplayName() {
            return "Publish Bandit reports";
        }
    }
}
