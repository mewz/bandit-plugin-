package hudson.plugins.bandit.workflow;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.plugins.bandit.BanditPublisher;
import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;
import org.jenkinsci.plugins.workflow.steps.StepExecution;

import javax.inject.Inject;

public class PublishBanditStepExecution extends AbstractSynchronousStepExecution<Void> {

    @StepContextParameter
    private transient FilePath ws;

    @StepContextParameter
    private transient Run build;

    @StepContextParameter
    private transient Launcher launcher;

    @Inject
    private transient PublishBanditStep step;


    @Override
    protected Void run() throws Exception {
        new BanditPublisher(step.getOutputFile()).publishReport(build, ws);
        return null;
    }
}
