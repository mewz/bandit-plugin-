package hudson.plugins.bandit;

import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.plugins.analysis.core.AbstractResultAction;
import hudson.plugins.analysis.core.HealthDescriptor;
import hudson.plugins.analysis.core.PluginDescriptor;


public class BanditResultAction extends AbstractResultAction<BanditResult> {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = 1005L;

    /**
     * Creates a new instance of <code>BanditResultAction</code>.
     *
     * @param owner
     *            the associated build of this action
     * @param healthDescriptor
     *            health descriptor to use
     * @param result
     *            the result in this build
     */
    public BanditResultAction(final Run<?, ?> owner, final HealthDescriptor healthDescriptor, final BanditResult result) {
        super(owner, new BanditHealthDescriptor(healthDescriptor), result);
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Bandit_ProjectAction_Name();
    }

    /** {@inheritDoc} */
    @Override
    protected PluginDescriptor getDescriptor() {
        return BanditPublisher.BANDIT_DESCRIPTOR;
    }

    /** {@inheritDoc} */
    @Override
    public String getMultipleItemsTooltip(final int numberOfItems) {
        return Messages.Bandit_ResultAction_MultipleWarnings(numberOfItems);
    }

    /** {@inheritDoc} */
    @Override
    public String getSingleItemTooltip() {
        return Messages.Bandit_ResultAction_OneWarning();
    }
}
