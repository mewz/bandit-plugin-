package hudson.plugins.bandit;

import hudson.model.AbstractProject;
import hudson.plugins.analysis.core.AbstractProjectAction;


public class BanditProjectAction extends AbstractProjectAction<BanditResultAction> {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = 1002L;

    /**
     * Instantiates a new find bugs project action.
     *
     * @param project
     *            the project that owns this action
     */
    public BanditProjectAction(final AbstractProject<?, ?> project) {
        super(project, BanditResultAction.class, BanditPublisher.BANDIT_DESCRIPTOR);
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Bandit_ProjectAction_Name();
    }

    /** {@inheritDoc} */
    @Override
    public String getTrendName() {
        return Messages.Bandit_Trend_Name();
    }
}

